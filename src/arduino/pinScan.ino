#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>
 
#define SS_PIN 9
#define RST_PIN 8
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.

const int ROW_NUM = 4; //four rows
const int COLUMN_NUM = 4; //four columns

char keys[ROW_NUM][COLUMN_NUM] = {
  {'1','2','3', 'A'},
  {'4','5','6', 'B'},
  {'7','8','9', 'C'},
  {'*','0','#', 'D'}
};

byte pin_rows[ROW_NUM] = { 12, 11, 7, 6 }; //connect to the row pinouts of the keypad
byte pin_column[COLUMN_NUM] = { 5, 4, 3, 2 }; //connect to the column pinouts of the keypad

Keypad keypad = Keypad(makeKeymap(keys), pin_rows, pin_column, ROW_NUM, COLUMN_NUM);
 
void setup() 
{
  Serial.begin(19200);   // Initiate a serial communication
  SPI.begin();      // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522
  Serial.println("Approximate your card to the reader...");
  Serial.println();

}
void loop() 
{
  // Look for new cards
  if ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }
  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }
  //Show UID on serial monitor
  Serial.print("UID tag :");
  String content= "";
  byte letter;
  for (byte i = 0; i < mfrc522.uid.size; i++) 
  {
     Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
     Serial.print(mfrc522.uid.uidByte[i], HEX);
     content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
     content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  Serial.println();
  Serial.print("Message : ");
  content.toUpperCase();
  if (content.substring(1) == "93 0E BC 18") //change here the UID of the card/cards that you want to give access
  {
    code();
    Serial.println();
    delay(3000);
  }
 
 else   {
    Serial.println(" Access denied");
    delay(3000);
  }
} 

int code(){

  int tries = 0;
  int keysPressed;
  
  String code = "1234";
  while(tries < 3){
  
  Serial.println("Please enter your pin");
  
  String i = writeDigits();
  if(i == code && tries < 3){
  Serial.println(" The value you entered is " + i);
  Serial.println(" \nAuthorized access");
  return 0;
  }

  else{
  Serial.println("Try again\n");
  tries++;
  int triesLeft = 3 - tries;
  int keysPressed = 0;
  Serial.print("You have ");
  Serial.print(triesLeft);
  Serial.print(" tries left\n" );
    }
  }
    Serial.println("You tried too many times");
    return 0;
    }


 String writeDigits(){
     String i, temp;
     int j = 0;
     char key2; 
  while(j < 10){   
  char key1 = keypad.getKey();
   
  String back = i;
  if(key1){
    
    if (key1 == '#'){
      Serial.println(i);
      return i; 
      }
    else if(key1 == '*' && key2 == '*'){
        i = "";
        temp = i;
        Serial.println("Start from the beginning");
        j = 0;
      }
    else if (key1 == '*' && j > 0){
        i = temp;
        Serial.println("removed last digit");
        Serial.println(i);
        j--;
        key2 = key1;
      }
    else {
        temp = i;
        j++;
        i += key1;
        Serial.println(i);
        key2 = key1;
        }     
      }
    }
    Serial.println("Too many symbols");  
  }
