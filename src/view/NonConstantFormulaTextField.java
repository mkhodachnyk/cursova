package view;

import exceptions.*;
import formula.implementation.*;

class NonConstantFormulaTextField extends FormulaTextField {
    NonConstantFormulaTextField(String content, int size, View view) {
        super(content, size, view);
    }

    boolean isCorrect() {
        try {
            new Expression(this.getText());
        } catch(MyException e){
            return false;
        }
        return true;
    }
}
