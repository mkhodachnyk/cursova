package formula.node;

import formula.token.IToken;

public class RadicalNode extends UnaryNode{
    RadicalNode(IToken token, INode child) {
        super(token, child);
    }

    protected double calculate(double childValue) {
        return Math.sqrt(childValue);
    }
}
