class Button {
    private:
        int Pin;

    public:
        Button(int Pin){
            this -> button = button;
            pinMode(button, INPUT_PULLUP);
        }

    bool ReturnButtonStatus(){
        return digitalRead(button);
    }
};
