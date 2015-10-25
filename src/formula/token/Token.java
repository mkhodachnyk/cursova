package formula.token;


class Token implements IToken {
    private final String myText;
    private final int myPriority;
    private final TokenType myTokenType;

    public Token(TokenType type, String text, int priority) {
        myTokenType = type;
        myText = text;
        myPriority = priority;
    }

    public int getPriority() {
        return myPriority;
    }

    public String toString() {
        return myText;
    }

    public TokenType getTypeOfToken() {
        return myTokenType;
    }
}
