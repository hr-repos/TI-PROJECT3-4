#include <SPI.h>
#include <MFRC522.h>
#include <ArduinoJson.h>

// Define the RFID reader pins
#define SS_PIN 9
#define RST_PIN 8

// Create an instance of the RFID reader
MFRC522 mfrc522(SS_PIN, RST_PIN);

void setup() {
  Serial.begin(9600);
  while (!Serial);  // wait for serial port to connect

  SPI.begin();      // Init SPI bus
  mfrc522.PCD_Init(); // Init MFRC522
  Serial.println("Scan a RFID tag");
}

void loop() {
  // Wait for a RFID tag to be detected
  if (!mfrc522.PICC_IsNewCardPresent() || !mfrc522.PICC_ReadCardSerial()) {
    return;
  }

  // Define the data as a JSON object
  StaticJsonDocument<128> jsonDoc;
  jsonDoc["Ctry"] = "LU";
  jsonDoc["Bank"] = "BK";
  jsonDoc["acctNo"] = "LU01BK000000001";

  // Serialize the JSON object to a byte array
  byte data[16];
  serializeJson(jsonDoc, data, sizeof(data));

  // Write the data to block 1
  MFRC522::StatusCode status = mfrc522.MIFARE_Write(1, data, sizeof(data));
  if (status == MFRC522::STATUS_OK) {
    Serial.println("Data written to tag");
  } else {
    Serial.println("Write failed");
    Serial.print("Error code: ");
    Serial.println(status);
  }
}
