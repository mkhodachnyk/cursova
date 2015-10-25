package formula.node;

import formula.token.IToken;

class MultiplicationNode extends BinaryNode {
    MultiplicationNode(IToken token, INode leftChild, INode rightChild) {
        super(token, leftChild, rightChild);
    }

    protected double calculate(double leftChildValue, double rightChildValue) {
        return leftChildValue * rightChildValue;
    }
}
