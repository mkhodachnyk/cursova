package formula.token;

import exceptions.MyException;
import formula.token.*;

public class TokenFactory {
    private static final String S_LBRACE = "(".intern();
    private static final String S_RBRACE = ")".intern();
    private static final String S_MULT = "*".intern();
    private static final String S_PLUS = "+".intern();
    private static final String S_MINUS = "-".intern();
    private static final String S_DIV = "/".intern();
    private static final String S_SINE = "sin".intern();
    private static final String S_COSINE = "cos".intern();
    private static final String S_ROOT = "sqrt".intern();
    private static final String S_PI = "pi".intern();
    private static final String S_VARU = "u".intern();
    private static final String S_VART = "t".intern();

    public static IToken create(String tokenString) {
        tokenString = tokenString.intern();
        if (tokenString == S_LBRACE) {
            return new Token(TokenType.LEFTBRACE, tokenString, 100);
        } else if (tokenString == S_RBRACE) {
            return new Token(TokenType.RIGHTBRACE, tokenString, 100);
        } else if (tokenString == S_MULT) {
            return new Token(TokenType.MULTIPLICATION, tokenString, 2);
        } else if (tokenString == S_PLUS) {
            return new Token(TokenType.ADDITION, tokenString, 1);
        } else if (tokenString == S_MINUS) {
            return new Token(TokenType.SUBTRACTION, tokenString, 1);
        } else if (tokenString == S_DIV) {
            return new Token(TokenType.DIVISION, tokenString, 2);
        } else if (tokenString == S_SINE) {
            return new Token(TokenType.SINE, tokenString, 3);
        } else if (tokenString == S_COSINE) {
            return new Token(TokenType.COSINE, tokenString, 3);
        } else if (tokenString == S_ROOT) {
            return new Token(TokenType.RADICAL, tokenString, 3);
        } else if (tokenString == S_PI) {
            return new PiToken();
        } else if ((tokenString == S_VARU) || (tokenString == S_VART)) {
            return new Token(TokenType.VAR, tokenString, 4);
        } else {
            try {
                return new NumberToken(tokenString);
            } catch (NumberFormatException e) {
                throw new MyException("Unknown token: \"" + tokenString + "\"");
            }
        }
    }
}
