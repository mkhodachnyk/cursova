package formula.node;

import formula.token.IToken;

class SineNode extends UnaryNode {
    SineNode(IToken token, INode child) {
        super(token, child);
    }

    protected double calculate(double childValue) {
        return Math.sin(childValue);
    }
}
