package formula.node;

import formula.token.*;

import java.io.IOException;
import java.io.Writer;

abstract class AbstractNode implements INode {
    private IToken myToken;

    AbstractNode() {
    }

    AbstractNode(IToken token) {
        myToken = token;
    }

    protected IToken getMyToken(){
        return myToken;
    }

    public abstract double evaluate(double u, double t);

    public String toString() {
        return myToken.toString();
    }

}
