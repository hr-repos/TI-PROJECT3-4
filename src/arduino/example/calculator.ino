// Pin Definitions

// H-bridge
#define IN1 40
#define IN2 41
#define IN3 42
#define IN4 43
#define IN5 44
#define IN6 45

int tensCount = 0;
int twentiesCount = 0;
int fiftiesCount = 0;

void setup() {
  Serial.begin(9600);  // Initialize Serial Monitor
  // Set pin modes
  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);
  
  
  // Initialize motor control pins to LOW
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, LOW);
}

void loop() {
  if (Serial.available()) {
    String input = Serial.readString();  // Read input from Serial Monitor as a string

    int amount = input.toInt();  // Convert the string to an integer

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
  }
}

void printCounts() {
  Serial.print("10s: ");
  Serial.print(tensCount);
  Serial.print(", 20s: ");
  Serial.print(twentiesCount);
  Serial.print(", 50s: ");
  Serial.println(fiftiesCount);
}

void rotateMotors() {

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
