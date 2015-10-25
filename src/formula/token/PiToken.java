package formula.token;

class PiToken implements INumber{

    public double getValue(){
        return Math.PI;
    }

    public int getPriority(){
        return 4;
    }

    public String toString(){
        return "pi";
    }

    public TokenType getTypeOfToken(){
        return TokenType.PI;
    }
}
