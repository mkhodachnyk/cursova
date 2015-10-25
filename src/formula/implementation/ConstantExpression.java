package formula.implementation;

import java.util.ArrayList;

import exceptions.MyException;
import formula.*;
import formula.node.*;
import formula.token.*;

public class ConstantExpression implements IConstantExpression {
    private String myText;
    private double myValue;

    public ConstantExpression(String text) {
        myText = text;

        ArrayList<IToken> list = TreeBuilder.tokenize(text);
        for (IToken token : list) {
            if (token.getTypeOfToken() == TokenType.VAR) {
                throw new MyException("string is not correct: ConstantExpression - var");
            }
        }
        myValue = TreeBuilder.buildSyntaxTree(myText).evaluate(0, 0);
    }

    public String getText() {
        return myText;
    }

    public double value() {
        return myValue;
    }
}
