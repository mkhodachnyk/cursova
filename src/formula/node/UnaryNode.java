package formula.node;

import java.io.IOException;
import java.io.Writer;

import formula.token.IToken;

abstract class UnaryNode extends AbstractNode {
    private INode myChild;

    UnaryNode(IToken token, INode child) {
        super(token);
        myChild = child;
    }

    public double evaluate(double u, double t) {
        return calculate(myChild.evaluate(u, t));
    }

    abstract protected double calculate(double childValue);

    public INode[] children() {
        return new INode[] { myChild };
    }
}
