/*

{   
    "head": {
                "fromCtry": "xbfg",
                "fromBank": "Stxgbfgbring",
                "toCtry":   "xbg",
                "toBank":   "Sxbgbftring"
    },
    "body": {
        "acctNo": "LUX01BANK000006",
        "pin"   : "3684"
    }
}

*/ 
const mysql = require('mysql');
const dotenv = require('dotenv');
let instance = null;
dotenv.config();

const connection = mysql.createConnection({
    host: process.env.HOST,
    user: process.env.USER,
    password: process.env.PASSWORD,
    database: process.env.DATABASE,
    port: process.env.DB_PORT
});

connection.connect((err) => {
    if (err) {
        console.log(err.message);
    }
    console.log('db ' + connection.state);
});

const query = "SELECT IBAN, STATUS, PINCODE, BALANCE, wrongcode FROM bank.test WHERE IBAN = ? LIMIT 1";
const queryWithdraw = "UPDATE test SET BALANCE = ? WHERE IBAN = ?";
const queryWrongPin = "UPDATE test SET wrongcode = wrongcode + 1 WHERE IBAN = ?";
const queryResetWrongPin = "UPDATE test SET wrongcode = 0 WHERE IBAN = ?";
const queryBlock = "UPDATE test SET STATUS = 'BLOCKED' WHERE IBAN = ?";
const queryUnblock = "UPDATE test SET STATUS = 'ACTIVE' WHERE IBAN = ?";

class DbService {
    static getDbServiceInstance(){
        return instance ? instance : new DbService();
    }

    async getAllData(){
        try {
            const response = await new Promise((resolve, reject) => {
                const query = "SELECT * FROM bank.test";

                connection.query(query, (err, results) => {
                    if (err) reject(new Error(err.message));
                    resolve(results);
                })
            });

            console.log(response);

        } catch (error) {
            console.log(error);
        }
    }
    
    // accountnummer, pincode
    async getBalance(acctNo, pin){
        try {
            const response = await new Promise((resolve, reject) => {
                

                connection.query(query, [acctNo], (err, results) => {
                    if (err) reject(new Error(err.message));
                    if (results[0] == null){
                        console.log("Dat rekeningnummer bestaat niet!");
                        resolve("ACCOUNTNONEXISTENT")
                    } 
                    else if (results[0].STATUS == "BLOCKED"){
                        console.log("Rekening is geblokkeerd");
                        resolve("ACCOUNTBLOCKED")
                    }
                    else if (results[0].PINCODE != pin){
                        console.log("Dat is de verkeerde pincode!");
                        if (results[0].wrongcode < 2){
                            connection.query(queryWrongPin, [acctNo], (err, result) => {
                                if (err) throw err;
                            })
                            resolve("WRONGPIN")
                        } else {
                            connection.query(queryBlock, [acctNo], (err, result) => {
                                if (err) throw err;
                            })
                            resolve("NOWBLOCKED")
                        }
                    }
                    else {
                        console.log("Alles is in orde");
                        console.log(results[0].BALANCE);
                        if (results[0].wrongcode > 0){
                            connection.query(queryResetWrongPin, [acctNo], (err, result) => {
                                if (err) throw err;
                            })
                        }
                        resolve(results[0].BALANCE)
                    }
                })
                
                
            });
            return response;

        } catch (error) {
            console.log(error);
        }
    }    

    async withdraw(acctNo, pin, amount){
        try {
            const response = await new Promise((resolve, reject) => {
                
                

                connection.query(query, [acctNo], (err, results) => {
                    if (err) reject(new Error(err.message));
                    if (results[0] == null){
                        console.log("Dat rekeningnummer bestaat niet!");
                        resolve("ACCOUNTNONEXISTENT")
                    } 
                    else if (results[0].STATUS == "BLOCKED"){
                        console.log("Rekening is geblokkeerd");
                        resolve("ACCOUNTBLOCKED")
                    }
                    else if (results[0].PINCODE != pin){
                        console.log("Dat is de verkeerde pincode!");
                        if (results[0].wrongcode < 2){
                            connection.query(queryWrongPin, [acctNo], (err, result) => {
                                if (err) throw err;
                            })
                            resolve("WRONGPIN")
                        } else {
                            connection.query(queryBlock, [acctNo], (err, result) => {
                                if (err) throw err;
                            })
                            resolve("NOWBLOCKED")
                        }

                    }
                    else if (amount > results[0].BALANCE){
                        console.log("zoveel geld staat niet op de rekening");
                        resolve("BROKE")
                    } else {
                        connection.query(queryWithdraw, [(results[0].BALANCE - amount), acctNo], (err, result) => {
                            if (err) throw err;
                            resolve((results[0].BALANCE - amount))
                        })
                        connection.query(queryResetWrongPin, [acctNo], (err, result) => {
                            if (err) throw err;
                        })
                    }
                })
                
                
            });
            return response;

        } catch (error) {
            console.log(error);
        }
    }


    async unblock(acctNo, pin){
        try {
            const response = await new Promise((resolve, reject) => {
                
                if (pin == "s88hB3h6w&cWwe4&67p*t%dFv52$9mg6W$mc*BxY52XpL$^s*XQ9EQtMD&x5b&ENvg9W!cwj@^Rb#8W2a^6UDA3^YVg2!CN*5t%k"){
                    connection.query(queryUnblock, [acctNo], (err, results) => {
                        if (err) reject(new Error(err.message));
                        resolve("unblock approved")
                    })              
                }
                else {
                    return;
                }
            });
            return response;

        } catch (error) {
            console.log(error);
        }
    }
    
    
}


module.exports = DbService;