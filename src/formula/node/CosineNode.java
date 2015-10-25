package formula.node;

import formula.token.IToken;

class CosineNode extends UnaryNode {
    CosineNode(IToken token, INode child) {
        super(token, child);
    }

    protected double calculate(double childValue) {
        return Math.cos(childValue);
    }
}
