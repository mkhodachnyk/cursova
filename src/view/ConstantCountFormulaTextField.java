package view;

import exceptions.*;
import formula.implementation.*;

public class ConstantCountFormulaTextField extends FormulaTextField {
    ConstantCountFormulaTextField(String content, int size, View view) {
        super(content, size, view);
    }

    boolean isCorrect() {
        try {
            new ConstantCountExpression(this.getText());
        } catch(MyException e){
            return false;
        }
        return true;
    }

}
