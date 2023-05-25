#include <Arduino.h>
#define LED1 7
#define LED2 8

void setup(){
    Serial.begin(9600);
    pinMode(LED1, OUTPUT);
    pinMode(LED2, OUTPUT);
}

void loop(){
  if (Serial.available() > 0) {
    String incomingByte = Serial.readStringUntil('\n');
    if (incomingByte = "1") {
      digitalWrite(7, HIGH);
    }
    else if (incomingByte = "2") {
      digitalWrite(8, HIGH);
    }
    else if (incomingByte = "3") {
      digitalWrite(7, LOW);
      digitalWrite(8, LOW);
    }
  }}                                         