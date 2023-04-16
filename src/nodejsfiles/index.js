const express = require('express');
const {createPool} = require('mysql')
const app = express();
const PORT = 9999

// Betere methode gevonden: https://youtu.be/vrj9AohVhPA?t=1457
const pool = createPool({
    host: "145.24.222.188",
    port: "",
    user: "",
    password: "",
    connectionLimit: 10
})

app.use(express.json())



app.listen (
    PORT,
    () => console.log(`It's alive on http://localhost:${PORT}`)
)

app.post('/balance' ,(req, res) => {


});

app.post('/withdraw' ,(req, res) => {


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

// Print alle rijen en laat de kolommen ID en IBAN zien uit de database
app.get('/testdb', (req, res) => {
    
    pool.query(`select ID, IBAN from bank.test`, (err, res) => {
        return console.log(res)
    })
});