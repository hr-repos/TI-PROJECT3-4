int tensCount = 0;
int twentiesCount = 0;
int fiftiesCount = 0;

void setup() {
  Serial.begin(9600);  // Initialize Serial Monitor
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