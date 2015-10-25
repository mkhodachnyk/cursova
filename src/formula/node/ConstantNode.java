package formula.node;

import formula.token.*;

class ConstantNode extends AbstractNode {
    ConstantNode(IToken token) {
        super(token);
    }

    public double evaluate(double u, double t) {
        return ((INumber)getMyToken()).getValue();
    }

    public INode[] children() {
        return new INode[0];
    }
}
