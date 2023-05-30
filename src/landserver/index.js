const express = require('express');
const axios = require('axios');
const app = express();
const PORT = 8443;
const messages = require('./messages.json');
const Joi = require("joi");
const r = messages.bank;
const w = messages.wysd;

app.use(express.json())

app.listen (
    PORT,
    () => console.log(`It's alive on http://localhost:${PORT}`)
    )
    
    const withdrawValidator = Joi.object({
        head: {
        fromCtry: Joi.string().required(),
        fromBank: Joi.string().required(),
    },
    body: {
        acctNo: Joi.string().required(),
        pin: Joi.string().required(),
        amount: Joi.number().required(),
    },
});

const balanceValidator = Joi.object({
    head: {
        fromCtry: Joi.string().required(),
        fromBank: Joi.string().required(),
    },
    body: {
        acctNo: Joi.string().required(),
        pin: Joi.string().required(),
    },
});

const options = {
    allowUnknown: true
};

const bankListBalance = {
    "noob": "http://145.24.222.82:8443/api/balance",
    "BK":   "http://145.24.222.188:8443/balance",
    "MOB":  "http://145.24.222.171:8888/balance"
};

const bankListWithdraw = {
    "noob-server":  "http://145.24.222.82:8443/api/balance",
    "BK":           "http://145.24.222.188:8443/balance",
    "MOB":          "http://145.24.222.171:8888/balance"
};


app.post('/balance' ,(req, res) => {
    const {error, value} = balanceValidator.validate(req.body, options);
    // variabel type check => error wanneer het niet klopt
    console.log("test==============", req.body);
    if (error){
        console.log(error)
        return res.status(r.wrongVariableType.code).send(r.wrongVariableType.message);
    }
    
    // request type check => error wanneer het niet klopt
    if (!req.is('application/json')){
        console.log(r.expectedJSONError.message + w.sanityCheck)
        res.status(r.expectedJSONError.code).send(r.expectedJSONError.message);
        return;
    }
    
    // wanneer de gevraagde bank niet in onze lijst staat moet het verzoek naar noob gestuurd worden
    if (!bankListBalance[req.body.head.toBank]){
        console.log("noob code block");
        return;
    } 
    else {
        axios.post(bankListBalance[req.body.head.toBank], req.body)
        .then((goodresponse) => {
            console.log("good response");
            // console.log(goodresponse.data);
            return res.status(200).send(goodresponse.data);
        })
        .catch((error) => { 
            return res.status(error.response.status).send(error.response.data);
        });
    }
});

app.post('/withdraw' ,(req, res) => {
    const {error, value} = withdrawValidator.validate(req.body, options);
    if (error){
        console.log(error)
        return res.status(r.wrongVariableType.code).send(r.wrongVariableType.message);
    }
    
    if (!req.is('application/json')){
        console.log(r.expectedJSONError.message + w.sanityCheck)
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




var http_options = {
    host: "145.24.222.82",
    port: 8443,
    path: "/api/register",
    key: options.key,
    cert: options.cert,
    ca: [fs.readFileSync('./certs/noob-root.pem'),
         fs.readFileSync('./certs/country-ca.pem')],
};


app.get('/registerland', (req, res) => {
    https.get(http_options, (response) => {
        let data = '';
        response.on('data', (chunk) => {
            data += chunk;
        });
   
        response.on('end', () => {
            console.log(data);
        })
    })
    .on('error', (error) => {
        console.log(error)
   });
   return;
});