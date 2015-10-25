package view;

import exceptions.MyException;
import formula.implementation.ConstantExpression;

class ConstantFormulaTextField extends FormulaTextField {
    ConstantFormulaTextField(String content, int size, View view) {
        super(content, size, view);
    }

    boolean isCorrect() {
        try {
            new ConstantExpression(this.getText());
        } catch(MyException e){
            return false;
        }
        return true;
    }
}
