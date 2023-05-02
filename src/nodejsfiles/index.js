const express = require('express');
const app = express();
const PORT = 8443
const country = "LU"
const bank = "BANK"
const dotenv = require('dotenv');
const messages = require('./messages.json');
const r = messages.bank;
dotenv.config();

// Betere methode gevonden: https://youtu.be/vrj9AohVhPA?t=1457
const dbService = require('./dbService');

app.use(express.json())
 
app.listen (
    PORT,
    () => console.log(`It's alive on http://localhost:${PORT}`)
)

app.post('/balance' ,(req, res) => {
    if (!req.is('application/json')){
        console.log(r.expectedJSONError.message + wysd.sanityCheck)
        res.status(r.expectedJSONError.code).send(r.expectedJSONError.message);
        return;
    }    
    const db = dbService.getDbServiceInstance();
    const result = db.getBalance(req.body.body.acctNo, req.body.body.pin);

    result
    .then(response => {
        if (response == "ACCOUNTNONEXISTENT"){
            res.status(r.accountnonexistent.code).send(r.accountnonexistent.message);
            return;
        } 
        else if (response == "ACCOUNTBLOCKED"){
            res.status(r.accountblocked.code).send(r.accountblocked.message);
            return;
        } 
        else if (response == "WRONGPIN"){
            res.status(r.wrongpin.code).send(r.wrongpin.message);
            return;
        } else if (response == "NOWBLOCKED"){
            res.status(r.nowblocked.code).send(r.nowblocked.message);
            return;
        } 
        else {
            const retObj = JSON.stringify({
                'head': {
                    'fromCtry': country,
                    'fromBank': bank,
                    'toCtry': req.body.head.fromCtry,
                    'toBank': req.body.head.fromBank
                },
                'body': {
                    'acctNo': req.body.body.acctNo,
                    'balance': response
                }
            });
            res.status(200).send(retObj);
        }
    })
    .catch((error) => {
        res.status(r.somethingHappened.code).send(r.somethingHappened.message);
        console.log(error);
    })
});

app.post('/withdraw' ,(req, res) => {
    if (!req.is('application/json')){
        console.log(r.expectedJSONError.message + wysd.sanityCheck)
        res.status(r.expectedJSONError.code).send(r.expectedJSONError.message);
        return;
    }    
    const db = dbService.getDbServiceInstance();
    const result = db.withdraw(req.body.body.acctNo, req.body.body.pin, req.body.body.amount);

    result
    .then(response => {
        if (response == "ACCOUNTNONEXISTENT"){
            res.status(r.accountnonexistent.code).send(r.accountnonexistent.message);
            return;
        } 
        else if (response == "ACCOUNTBLOCKED"){
            res.status(r.accountblocked.code).send(r.accountblocked.message);
            return;
        } 
        else if (response == "WRONGPIN"){
            res.status(r.wrongpin.code).send(r.wrongpin.message);
            return;
        } 
        else if (response == "NOWBLOCKED"){
            res.status(r.nowblocked.code).send(r.nowblocked.message);
            return;
        } 
        else if (response == "BROKE"){
            res.status(r.broke.code).send(r.broke.message);
            return;
        }
        else {
            const retObj = JSON.stringify({
                'head': {
                    'fromCtry': country,
                    'fromBank': bank,
                    'toCtry': req.body.head.fromCtry,
                    'toBank': req.body.head.fromBank
                },
                'body': {
                    'succes': true,
                    'acctNo': req.body.body.acctNo,
                    'balance': response
                }
            });
            res.status(200).send(retObj);
        }
    })
    .catch((error) => {
        res.status(r.somethingHappened.code).send(r.somethingHappened.message);
        console.log(error);
    })

});

// testfuncties hieronder
// Probeer uit met localhost:9999/testpin/1234
app.get('/testpin/:password', (req, res) => {
    const { password } = req.params;
    
    if (password == 1234) {
        res.status(200).send({message: 'Correct'})
    } 
    else {
        res.status(418).send({message: 'Incorrect'})
    }
});

// Print alle rijen en laat de kolommen ID en IBAN zien uit de database in de console
app.get('/readDB', (req, res) => {
    const db = dbService.getDbServiceInstance(); 
    console.log(db.getAllData());
});