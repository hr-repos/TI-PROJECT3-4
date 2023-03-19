

void setup()
{
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);
}

void loop()
{
  byte inByte;
  if(Serial.available())
  {
    inByte = Serial.read();
    for(byte i=1; i<=inByte*2; i++)
    {
      digitalWrite(LED_BUILTIN, !digitalRead(LED_BUILTIN));
      delay(200);
    }
  }
}
