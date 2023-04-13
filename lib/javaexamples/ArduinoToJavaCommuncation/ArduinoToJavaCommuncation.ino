void setup()
{
  Serial.begin(9600); // opens serial port, sets data rate to 9600 bps 8N1

}

void loop()
{
  char TextToSend[] = "Hello to Java from Arduino UNO";
  Serial.println(TextToSend); // sends a \n with text
  delay(1000);
}