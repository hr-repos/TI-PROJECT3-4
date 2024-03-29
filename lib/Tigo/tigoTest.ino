
#include <SPI.h>//include the SPI bus library
#include <MFRC522.h>//include the RFID reader library
#include <Keypad.h>
#include "Adafruit_Thermal.h"
#include "adalogo.h"
#include "adaqrcode.h"

#include "SoftwareSerial.h"

#define SS_PIN 9  //slave select pin
#define RST_PIN 8  //reset pin
#define TX_PIN 29 // Arduino transmit  YELLOW WIRE  labeled RX on printer
#define RX_PIN 28 // Arduino receive   GREEN WIRE   labeled TX on printer

SoftwareSerial mySerial(RX_PIN, TX_PIN); // Declare SoftwareSerial obj first
Adafruit_Thermal printer(&mySerial);     // Pass addr to printer constructor

// Define the key matrix
const byte ROWS = 4;
const byte COLS = 4;
char keys[ROWS][COLS] = {
  {'1','2','3','A'},
  {'4','5','r 6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};

// Define the keypad matrix
byte rowPins[ROWS] = {12, 11, 7, 6 }; // Connect to row pinouts of the keypad
byte colPins[COLS] = {5, 4, 3, 2}; // Connect to column pinouts of the keypad
Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, ROWS, COLS);

MFRC522 mfrc522(SS_PIN, RST_PIN);        // instatiate a MFRC522 reader object.
MFRC522::MIFARE_Key key;//create a MIFARE_Key struct named 'key', which will hold the card information

#define button1 24
#define button2 25
#define button3 26
#define button4 27

#define IN1 40
#define IN2 41
#define IN3 42
#define IN4 43
#define IN5 44
#define IN6 45

int buttonState1;
int buttonState2;
int buttonState3;
int buttonState4;

bool interactionWithATM = true;
bool passwordIsTrue = false;

byte readbackblock[18];
String iban= "";
String land= "";
String bank = "";
String receivedSerialData = "";

void setup() {

  pinMode(button1, INPUT_PULLUP);
  pinMode(button2, INPUT_PULLUP);
  pinMode(button3, INPUT_PULLUP);
  pinMode(button4, INPUT_PULLUP);

  pinMode(7, OUTPUT); 
  digitalWrite(7, LOW);

  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);
  
  
  // Initialize motor control pins to LOW
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, LOW);

  mySerial.begin(19200);  // Initialize SoftwareSerial
  Serial.begin(19200);
  SPI.begin();               // Init SPI bus
  printer.begin();        // Init printer (same regardless of serial type)
  mfrc522.PCD_Init();        // Init MFRC522 card (in case you wonder what PCD means: proximity coupling device)
        
  for (byte i = 0; i < 6; i++) {
           key.keyByte[i] = 0xFF;//keyByte is defined in the "MIFARE_Key" 'struct' definition in the .h file of the library
  }
}

void loop() {
  passwordIsTrue = false;
  interactionWithATM = true;
  cardScanner();
}

void cardScanner(){
   // Reset arrays
  memset(readbackblock, 0, sizeof(readbackblock));
  iban = "";
  land = "";
  bank = "";
  // Look for new cards
  if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    char buttonPress[] = "pass found";
    delay(300);
    Serial.println(buttonPress);
    
    readBlock(6, readbackblock);//read the block back
    for (int j=0 ; j<16 ; j++)//print the block contents
    {

      iban += (char)readbackblock[j];

    }
    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();
    memset(readbackblock, 0, sizeof(readbackblock));
    if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    readBlock(4, readbackblock);//read the block back
    for (int l=0 ; l<16 ; l++)//print the block contents
    {

      land += (char)readbackblock[l];

    }
   mfrc522.PICC_HaltA();
   mfrc522.PCD_StopCrypto1();
   if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    memset(readbackblock, 0, sizeof(readbackblock));
    readBlock(5, readbackblock);//read the block back
    for (int i=0 ; i<16 ; i++)//print the block contents
    {

      bank += (char)readbackblock[i];

    }

  char ibanArray[20];
  char landArray[20];
  char bankArray[20];
  iban.toCharArray(ibanArray, 20); // copy the string into the character array
  land.toCharArray(landArray, 20); // copy the string into the character array
  bank.toCharArray(bankArray, 20); // copy the string into the character array
  Serial.println(ibanArray); // print the character array 
  Serial.println(landArray); // print the character array
  Serial.println(bankArray); // print the character array

memset(readbackblock, 0, sizeof(readbackblock));
    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();
    buttonPressed();
        }
      }
    }
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
    // Determine actual size of data read
    byte dataSize = (size == 18) ? 16 : 0;
    for (int j = 0; j < dataSize; j++) {
      readbackblock[j] = buffer[j];
    }
  }
  else {
    Serial.print("MIFARE_Read() failed: ");
    Serial.println(mfrc522.GetStatusCodeName(status));
  }

  // Stop encryption on the reader
  mfrc522.PCD_StopCrypto1();
}


 void readKeypadNumber() {
  char key;

    key = keypad.getKey();
    if (key) {
      if (key >= '0' && key <= '9') {
        Serial.println(key);
        delay(300);
      } else if (key == '#') {
        Serial.println("check");
        delay(300);
    }   else if (key == '*') {
        Serial.println("back");
        delay(300);
    } 
    else{};
  }
}

  void buttonPressed() {
  while (interactionWithATM) {
    buttonState1 = digitalRead(button1);
    buttonState2 = digitalRead(button2);
    buttonState3 = digitalRead(button3);
    buttonState4 = digitalRead(button4);

   while (Serial.available()) {
  String incomingByte = Serial.readStringUntil('\n');
    if (incomingByte == "done") {
      interactionWithATM = false;
       String money = Serial.readStringUntil('\n');
       int amount = money.toInt();
       giveMoney(amount);
    } else if (incomingByte == "receipt") {
      interactionWithATM = false;
      String currentTime = Serial.readStringUntil('\n');
      
      String currentAmount = Serial.readStringUntil('\n');
      int amount = currentAmount.toInt();
      giveMoney(amount);
      printer.wake();
      printReceipt(iban, currentTime, currentAmount);
    }
  }
   
    if (buttonState1 == LOW) {
      char buttonPress[] = "a";
      Serial.println(buttonPress);
      delay(300);
    } else if (buttonState2 == LOW) {
      char buttonPress[] = "b";
      Serial.println(buttonPress);
      delay(300);
    } else if (buttonState3 == LOW) {
      char buttonPress[] = "c";
      Serial.println(buttonPress);
      delay(300);
    } else if (buttonState4 == LOW) {
      char buttonPress[] = "d";
      Serial.println(buttonPress);
      delay(300);
    } else {
      readKeypadNumber();
    }
  }
}

void printReceipt(String iban, String currentTime, String currentAmount){
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
    printer.print("Amount : "); 
    printer.println(currentAmount); 
    printer.println("ATM# : Bank bank");  
    printer.println("________________");

    printer.feed(1);
    printer.justify('C');
    printer.boldOn();

    printer.println("Fijne dag nog");
    printer.println(currentTime);
    printer.feed(3);

    printer.sleep();      // Tell printer to sleep
    delay(3000L);         // Sleep for 3 seconds
    printer.wake();       // MUST wake() before printing again, even if reset
    printer.setDefault(); // Restore printer to defaults
  }

  void giveMoney(int amount){
    // Calculate the number of each denomination
    fiftiesCount = amount / 50;
    amount %= 50;
    twentiesCount = amount / 20;
    amount %= 20;
    tensCount = amount / 10;

    // Print the counts to the Serial Monitor
    printCounts();
    
    // Rotate the motors
    rotateMotors();

     // Motor 1
  for (int i = 0; i < tensCount; i++) {
    digitalWrite(IN1, HIGH);
    digitalWrite(IN2, LOW);
    Serial.println("ten");
    delay(1000);
    digitalWrite(IN1, LOW);
    delay(1000);
  }
  
  // Motor 2
  for (int i = 0; i < twentiesCount; i++) {
    digitalWrite(IN3, HIGH);
    digitalWrite(IN4, LOW);
    Serial.println("twenty");
    delay(1000);
    digitalWrite(IN3, LOW);
    delay(1000);
  }

  // Motor 3
  for (int i = 0; i < fiftiesCount; i++){
    digitalWrite(IN5, HIGH);
    digitalWrite(IN6, LOW);
    Serial.println("fifty");
    delay(1000);
    digitalWrite(IN5, LOW);
    delay(1000);
    }
  }