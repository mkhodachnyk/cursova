package formula.node;

import formula.token.IToken;

class DivisionNode extends BinaryNode {
    DivisionNode(IToken token, INode leftChild, INode rightChild) {
        super(token, leftChild, rightChild);
    }

    protected double calculate(double leftChildValue, double rightChildValue) {
        return leftChildValue / rightChildValue;
    }
}
