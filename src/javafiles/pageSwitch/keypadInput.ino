#include <Keypad.h>

// Define the key matrix
const byte ROWS = 4;
const byte COLS = 4;
char keys[ROWS][COLS] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};

// Define the keypad matrix
byte rowPins[ROWS] = {12, 11, 7, 6 }; // Connect to row pinouts of the keypad
byte colPins[COLS] = {5, 4, 3, 2}; // Connect to column pinouts of the keypad
Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, ROWS, COLS);

// Define a class to handle keypad input
class KeypadHandler {
  private:
    int codeIndex;
    char code[5];

  public:
    KeypadHandler() {
      codeIndex = 0;
      memset(code, 0, sizeof(code)); // initialize code array to all zeros
    }

    void readKeypad() {
      char key = keypad.getKey();

// Check if a numeric key was pressed
      if (key >= '0' && key <= '9') {
        if (codeIndex < 4) {
          // Add the pressed key to the code
          code[codeIndex++] = key;
          char buttonPress[] = "plus";
          Serial.println(buttonPress); // sends a \n with text
        }
        // Check if the * key was pressed
      } else if (key == '*') {
        // Remove the last digit entered
        if (codeIndex > 0) {
          code[--codeIndex] = 0;
          char buttonPress[] = "minus";
          Serial.println(buttonPress); // sends a \n with text
        }
      }

      // Check if the # key was pressed and the code is complete
      if (codeIndex == 4 && key == '#') {
        // Code has been entered, check if it is correct
        if (strcmp(code, "1234") == 0) {
          char buttonPress[] = "correct";
          Serial.println(buttonPress); // sends a \n with text
        } else {
          char buttonPress[] = "wrong";
          Serial.println(buttonPress); // sends a \n with text
        }
        memset(code, 0, sizeof(code)); // reset code array to all zeros
        codeIndex = 0;
      }
    }
};

KeypadHandler keypadHandler;

void setup() {
  Serial.begin(9600);
}

void loop() {
  keypadHandler.readKeypad();
}
