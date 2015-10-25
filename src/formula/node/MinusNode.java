package formula.node;

import formula.token.IToken;

class MinusNode extends UnaryNode{
    MinusNode(IToken token, INode child) {
        super(token, child);
    }

    protected double calculate(double childValue) {
        return -childValue;
    }

}
