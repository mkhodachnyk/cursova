package formula.node;

import formula.token.IToken;

abstract class BinaryNode extends AbstractNode {
    private INode myLeftChild, myRightChild;

    BinaryNode(IToken token, INode leftChild, INode rightChild) {
        super(token);
        myLeftChild = leftChild;
        myRightChild = rightChild;
    }

    public double evaluate(double u, double t) {
        return calculate(myLeftChild.evaluate(u, t), myRightChild.evaluate(u, t));
    }

    abstract protected double calculate(double leftChildValue, double rightChildValue);

    public INode[] children() {
        return new INode[] { myLeftChild, myRightChild };
    }
}
