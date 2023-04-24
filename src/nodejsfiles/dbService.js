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
                
                const query = "SELECT IBAN, STATUS, PINCODE, BALANCE FROM bank.test WHERE IBAN = ? LIMIT 1";

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
                        resolve("WRONGPIN")
                    }
                    else {
                        console.log("Alles is in orde");
                        console.log(results[0].BALANCE);
                        resolve(results[0].BALANCE)
                    }
                })
                
                
            });
            return response;

        } catch (error) {
            console.log(error);
        }
    }    
}


module.exports = DbService;