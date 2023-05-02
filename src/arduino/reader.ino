#include <SPI.h>//include the SPI bus library
#include <MFRC522.h>//include the RFID reader library


#define SS_PIN 9  //slave select pin
#define RST_PIN 8  //reset pin
MFRC522 mfrc522(SS_PIN, RST_PIN);        // instatiate a MFRC522 reader object.
MFRC522::MIFARE_Key key;//create a MIFARE_Key struct named 'key', which will hold the card information

byte readbackblock[18];
int block = 4;
int block2 = 5;
int block3 = 6;

void setup() {
        Serial.begin(9600);        // Initialize serial communications with the PC
        SPI.begin();               // Init SPI bus
        mfrc522.PCD_Init();        // Init MFRC522 card (in case you wonder what PCD means: proximity coupling device)
        Serial.println("Scan a MIFARE Classic card");
        
        for (byte i = 0; i < 6; i++) {
                key.keyByte[i] = 0xFF;//keyByte is defined in the "MIFARE_Key" 'struct' definition in the .h file of the library
        }

}

void loop() {

  // Reset arrays
  memset(readbackblock, 0, sizeof(readbackblock));
  // Look for new cards
  if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    
    Serial.println("card selected");
    
    readBlock(block, readbackblock);//read the block back
    Serial.println("read block 4: ");
    for (int j=0 ; j<16 ; j++)//print the block contents
    {
      Serial.write (readbackblock[j]);//Serial.write() transmits the ASCII numbers as human readable characters to serial monitor
    }
    Serial.println("");
    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();
    delay(1000);

    memset(readbackblock, 0, sizeof(readbackblock));

    if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
      readBlock(block2, readbackblock);//read the block back
      Serial.println("read block 5: ");
      for (int i=0 ; i<16 ; i++)//print the block contents
      {
        Serial.write (readbackblock[i]);//Serial.write() transmits the ASCII numbers as human readable characters to serial monitor
      }
      Serial.println("");
      mfrc522.PICC_HaltA();
      mfrc522.PCD_StopCrypto1();
      delay(1000);

      memset(readbackblock, 0, sizeof(readbackblock));
      
      if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
        readBlock(block3, readbackblock);//read the block back
        Serial.println("read block 6: ");
        for (int l=0 ; l<16 ; l++)//print the block contents
        {
          Serial.write (readbackblock[l]);//Serial.write() transmits the ASCII numbers as human readable characters to serial monitor
        }
        Serial.println("");
        mfrc522.PICC_HaltA();
        mfrc522.PCD_StopCrypto1();
        delay(1000);
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
    memcpy(readbackblock, buffer, 16);
  }
  else {
    Serial.print("MIFARE_Read() failed: ");
    Serial.println(mfrc522.GetStatusCodeName(status));
  }
  
  // Stop encryption on the reader
  mfrc522.PCD_StopCrypto1();
}
