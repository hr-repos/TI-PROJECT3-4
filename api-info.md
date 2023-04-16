# API INFO
De api kan 2 requesten ontvangen.  
Met POST /balance kan de balans op een rekening opgevaargd worden.  
Met POST /withdraw kan er geld opgenomen worden.

**<u>Al deze functie maken gebruik van het json format!</u>**

## Registreren met GET (?????)

```json
// info die de api terug stuurd
{
    'head': {
        'fromCtry': String,
        'fromBank': String,
        'toCtry':   String,
        'toBank':   String
    },
    'body': {
        'acctNo': String,
        'pin'   : String
    }
}
```



## Bedrag op de rekening geven met POST

```json
// info die de api ontvangt
{   
    'head': {
            'fromCtry': String,
            'fromBank': String,
            'toCtry':   String,
            'toBank':   String
        },
    'body': {
        'acctNo': String,
        'pin'   : String
    }
}

// info die de api terug stuurd
{
    'head': {
        'fromCtry': String,
        'fromBank': String,
        'toCtry':   String,
        'toBank':   String
    },
    'body': {
        'acctNo' : String,
        'balance': Integer
    }
}
```

## Geld opnemen met POST

```json
// info die de api ontvangt
{   
    'head': {
            'fromCtry': String,
            'fromBank': String,
            'toCtry':   String,
            'toBank':   String
        },
    'body': {
        'acctNo': String,
        'pin'   : String,
        'amount': Integer
    }
}

// info die de api terug stuurd
{
    'head': {
        'fromCtry': String,
        'fromBank': String,
        'toCtry':   String,
        'toBank':   String
    },
    'body': {
        'success': Bool,
        'acctNo' : String,
        'balance': Integer
    }
}
```