class Button {
    private:
        int Pin;

    public:
        Button(int Pin){
            this -> pin = pin;
            pinMode(pin, INPUT_PULLUP);
        }

    bool ReturnButtonStatus(){
        return digitalRead(pin);
    }
};
