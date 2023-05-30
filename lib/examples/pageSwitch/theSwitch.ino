

#define button1 2
#define button2 3

int buttonState1 = 0;
int buttonState2 = 0;

void setup()
{
  pinMode(button1, INPUT);
  pinMode(button2, INPUT);
  Serial.begin(9600); // opens serial port, sets data rate to 9600 bps 8N1

}

void loop()
{
  buttonPressed();
  delay(1000);
}

void buttonPressed(){
  buttonState1 = digitalRead(button1);
  buttonState2 = digitalRead(button2);
  
      if (buttonState1 == HIGH){
        char buttonPress[] = "a";
        Serial.println(buttonPress); // sends a \n with text
        }
      else if(buttonState2 == HIGH){
        char buttonPress[] = "b";
        Serial.println(buttonPress); // sends a \n with text
        }
      else {
        
        }
  }