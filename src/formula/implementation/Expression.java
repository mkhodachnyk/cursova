package formula.implementation;

import exceptions.MyException;
import formula.*;
import formula.node.INode;
import formula.node.TreeBuilder;
import formula.token.IToken;
import formula.token.TokenFactory;

public class Expression implements IExpression {
    private String myText;
    private INode mySyntaxTree;

    public Expression(String text) {
        myText = text;
        mySyntaxTree = TreeBuilder.buildSyntaxTree(myText);
    }

    public String getText() {
        return myText;
    }

    public double evaluate(double u, double t) {
        return mySyntaxTree.evaluate(u, t);
    }
}
