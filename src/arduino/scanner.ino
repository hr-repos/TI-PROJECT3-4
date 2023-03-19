#include <SPI.h>
#include <MFRC522.h>
#include <arduinio.h>
 
#define SS_PIN 10
#define RST_PIN 9
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.
 
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
  int i;
  int tries = 0;
  
  int code = 1234;
  while(tries < 3){
  
  cout << "Please enter your pin: ";
  cin >> i;
  if(i == code && tries < 3){
  cout << "The value you entered is " << i;
  Serial.println(" \nAuthorized access");
  return 0;
  }

  else{
  Serial.println("Try again\n");
  tries++;
  int triesLeft = 3 - tries;
  Serial.print("You have ");
  Serial.print(triesLeft);
  Serial.print(" tries left\n" );
    }
  }
    Serial.println("You tried too many times");
    return 0;
    }