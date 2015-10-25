package formula.token;

class NumberToken implements INumber{
    private final String myString;
    private final double myValue;

    NumberToken(String string) throws NumberFormatException {
        myString = string;
        myValue = Double.parseDouble(myString);
    }

    public double getValue(){
        return myValue;
    }

    public int getPriority(){
        return 4;
    }

    public String toString(){
        return myString;
    }

    public TokenType getTypeOfToken(){
        return TokenType.NUMBER;
    }
}
