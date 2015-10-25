package view;

import java.awt.Color;
import javax.swing.*;

abstract class FormulaTextField extends JTextField {
    private View myView;
    private boolean myIsCorrect = true;
    FormulaTextField(String content, int size, View view) {
        super(content, size);
        myView = view;
    }

    void check() {
        this.setForeground(isCorrect() ? Color.black : Color.red);
        if ((isCorrect()) && (!myIsCorrect)) {
            myView.decreaseWrongFieldCounter();
            myIsCorrect = true;
        }
        if ((myIsCorrect) && (!isCorrect())){
            myView.increaseWrongFieldCounter();
            myIsCorrect = false;
        }
    }

    abstract boolean isCorrect();
}
