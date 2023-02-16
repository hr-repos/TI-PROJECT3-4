#include <Arduino.h>

Button knop1 = Button(1);


void setup(){
    Serial.begin(9600);
}

void loop(){
    Serial.println(knop1.ReturnButtonStatus());
}