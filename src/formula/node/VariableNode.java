package formula.node;

import formula.token.IToken;

class VariableNode extends AbstractNode {

    VariableNode(IToken token) {
        super(token);
    }

    public double evaluate(double u, double t) {
        return (getMyToken().toString().equals("u")) ? u : t;
    }

    public INode[] children() {
        return new INode[0];
    }
}
