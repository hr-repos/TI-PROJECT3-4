#include "Adafruit_Thermal.h"
#include "adalogo.h"
#include "adaqrcode.h"
#include <SPI.h>//include the SPI bus library
#include <MFRC522.h>//include the RFID reader library

#include "SoftwareSerial.h"
#define SS_PIN 9  //slave select pin
#define RST_PIN 8  //reset pin
#define TX_PIN 23 // Arduino transmit  YELLOW WIRE  labeled RX on printer
#define RX_PIN 22 // Arduino receive   GREEN WIRE   labeled TX on printer

SoftwareSerial mySerial(RX_PIN, TX_PIN); // Declare SoftwareSerial obj first
Adafruit_Thermal printer(&mySerial);     // Pass addr to printer constructor

MFRC522 mfrc522(SS_PIN, RST_PIN);        // instatiate a MFRC522 reader object.
MFRC522::MIFARE_Key key;//create a MIFARE_Key struct named 'key', which will hold the card information

byte readbackblock[18];
int block = 4;
int block2 = 5;
int block3 = 6;
String iban= "";

void setup() {

  pinMode(7, OUTPUT); 
  digitalWrite(7, LOW);

  // NOTE: SOME PRINTERS NEED 9600 BAUD instead of 19200, check test page. 
  mySerial.begin(19200);  // Initialize SoftwareSerial
  Serial.begin(9600);
  SPI.begin();               // Init SPI bus
  printer.begin();        // Init printer (same regardless of serial type)
  mfrc522.PCD_Init();        // Init MFRC522 card (in case you wonder what PCD means: proximity coupling device)
  Serial.println("Scan a MIFARE Classic card");
        
  for (byte i = 0; i < 6; i++) {
           key.keyByte[i] = 0xFF;//keyByte is defined in the "MIFARE_Key" 'struct' definition in the .h file of the library
  }
}

void loop() {

  // Reset arrays
  memset(readbackblock, 0, sizeof(readbackblock));
  iban = "";
  // Look for new cards
  if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    
    Serial.println("card selected");
    
    readBlock(6, readbackblock);//read the block back
    Serial.println("read block 6: ");
    for (int j=0 ; j<16 ; j++)//print the block contents
    {
      Serial.write (readbackblock[j]);//Serial.write() transmits the ASCII numbers as human readable characters to serial monitor
      iban += (char)readbackblock[j];
    }
    Serial.println("");
    Serial.println("iban: " + iban);
    printReceipt(iban);
    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();
    delay(1000);
  }
}

void printReceipt(String iban){
    printer.justify('C');
    printer.boldOn();
    printer.setSize('L');

    printer.println("De Bank bank");
    printer.println("________________");

    printer.justify('L');
    printer.setSize('S');
    printer.boldOff();          

    printer.println("________________");

    printer.print("Account : ");
    printer.println(iban);
    printer.println("Amount : "); 
    printer.println("Transaction# : 1"); 
    printer.println("ATM# : Bank bank");  
    printer.println("________________");

    printer.feed(1);
    printer.justify('C');
    printer.boldOn();

    printer.println("Fijne dag nog");
    printer.feed(3);

    printer.sleep();      // Tell printer to sleep
    delay(3000L);         // Sleep for 3 seconds
    printer.wake();       // MUST wake() before printing again, even if reset
    printer.setDefault(); // Restore printer to defaults
  }

void readBlock(byte blockNumber, byte readbackblock[])
{
  MFRC522::StatusCode status;
  byte buffer[18];
  byte size = sizeof(buffer);
  
  // Authenticate using key A
  status = mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, blockNumber, &key, &(mfrc522.uid));
  if (status != MFRC522::STATUS_OK) {
    Serial.print("PCD_Authenticate() failed: ");
    Serial.println(mfrc522.GetStatusCodeName(status));
    return;
  }

  // Read block
  status = mfrc522.MIFARE_Read(blockNumber, buffer, &size);
  if (status == MFRC522::STATUS_OK) {
    memcpy(readbackblock, buffer, 16);
  }
  else {
    Serial.print("MIFARE_Read() failed: ");
    Serial.println(mfrc522.GetStatusCodeName(status));
  }
  
  // Stop encryption on the reader
  mfrc522.PCD_StopCrypto1();
}