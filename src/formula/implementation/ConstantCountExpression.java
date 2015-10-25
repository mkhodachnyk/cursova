package formula.implementation;

import java.util.ArrayList;

import exceptions.MyException;
import formula.*;
import formula.node.TreeBuilder;
import formula.token.IToken;
import formula.token.TokenType;


public class ConstantCountExpression implements IConstantExpression{
    private String myText;
    private double myValue;

    public ConstantCountExpression(String text) {
        myText = text;

        ArrayList<IToken> list = TreeBuilder.tokenize(text);
        for (IToken token : list) {
            if (token.getTypeOfToken() != TokenType.NUMBER){
                throw new MyException("string is not correct: ConstantCountExpression - not number");
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
