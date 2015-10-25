package formula.node;

import formula.token.*;

class AdditionNode extends BinaryNode {
    AdditionNode(IToken token, INode leftChild, INode rightChild) {
        super(token, leftChild, rightChild);
    }

    protected double calculate(double leftChildValue, double rightChildValue) {
        return leftChildValue + rightChildValue;
    }
}

