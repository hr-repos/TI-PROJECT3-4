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
                    if (err) rejects(new Error(err.message));
                    resolve(results);
                })
            });

            console.log(response);

        } catch (error) {
            console.log(error.message);
        }
    }
}


module.exports = DbService;