package formula.node;

import java.util.*;
import exceptions.MyException;
import formula.token.*;

public class TreeBuilder {

    public static ArrayList<IToken> tokenize(String string) {
        ArrayList<IToken> tokens = new ArrayList<IToken>();
        CharType state = CharType.SPACE;

        int startOfToken = 0;
        int len = string.length();

        for(int i = 0; i < len; i++){
            char ch = string.charAt(i);
            CharType currentState = defineToken(ch);
            if (ch == ' '){
                if(state != CharType.SPACE){
                    tokens.add(TokenFactory.create(string.substring(startOfToken, i)));
                    state = CharType.SPACE;
                }
                startOfToken = i + 1;
            } else {
                if((currentState != state) || ((currentState == CharType.LBRACE)
                        || (currentState == CharType.RBRACE))){
                    if ((startOfToken != i)){
                        tokens.add(TokenFactory.create(string.substring(startOfToken, i)));
                    }
                    state = currentState;
                    startOfToken = i;
                }
            }
        }
        if ((startOfToken < string.length())){
            tokens.add(TokenFactory.create(string.substring(startOfToken, len)));
        }

        return tokens;
    }

    private static CharType defineToken(char ch){
        CharType result = CharType.START;
        int numericChar = (int)ch;
        if (Character.isLetter(numericChar) && Character.isLowerCase(numericChar)) {
            result = CharType.LETTER;
        } else if ((Character.isDigit(ch)) || (ch == '.')){
            result = CharType.NUMBER;
        } else if (ch == '(') {
            result = CharType.LBRACE;
        } else if (ch == ')') {
            result = CharType.RBRACE;
        } else if ((ch == '*') || (ch == '/') || (ch == '+') || (ch == '-'))
            result = CharType.SIGN;
        else if (ch == ' ')
            result = CharType.SPACE;
        else if(ch != ' ')
            throw new MyException("String is not valid: unknown char");

        return result;
    }

    public static INode buildSyntaxTree(String expression){
        ArrayList<IToken> tokens = tokenize(expression);
        return createNode(tokens);
    }

    private static INode createNode(List<IToken> list){
        if (list.size() <= 0){
            throw new MyException("string is not correct: length of expression < 0");
        }

        int count = -1;
        int minPriority = 100;
        int countOfBrace = 0;
        IToken mainOperation = null;

        for (int i = 0; i < list.size(); i++) {
            IToken currentToken = (IToken)list.get(i);
            if (currentToken.getTypeOfToken() == TokenType.LEFTBRACE)
                countOfBrace++;
            else if (currentToken.getTypeOfToken() == TokenType.RIGHTBRACE)
                countOfBrace--;
            else if (countOfBrace == 0){
                int priorityOfCurrentToken = currentToken.getPriority();
                if (priorityOfCurrentToken <= minPriority){
                    mainOperation = currentToken;
                    minPriority = priorityOfCurrentToken;
                    count = i;
                }
            }
        }
        if (countOfBrace != 0) {
            throw new MyException("string is not valid: braces");
        }
        if (count < 0){
            return createNode(list.subList(1, list.size() - 1));
        } else {
            if ((minPriority >= 3) && (count != 0)) {
                throw new MyException("error in function or var, count != 0");
            }
            String name = mainOperation.toString();
//			System.out.println("mainOperation = " + mainOperation + " position = " + (count + 1));
            switch(mainOperation.getTypeOfToken()) {
                case SINE:
                    return new SineNode(mainOperation, createNode(list.subList(1, list.size())));
                case COSINE:
                    return new CosineNode(mainOperation, createNode(list.subList(1, list.size())));
                case RADICAL:
                    return new RadicalNode(mainOperation, createNode(list.subList(1, list.size())));
                case VAR:
                    return new VariableNode(mainOperation);
                case PI:
                    return new ConstantNode(mainOperation);
                case NUMBER:
                    return new ConstantNode(mainOperation);
                case ADDITION:
                {
                    INode left = createNode(list.subList(0, count));
                    INode right = createNode(list.subList(count + 1, list.size()));
                    return new AdditionNode(mainOperation,left, right);
                }
                case DIVISION:
                {
                    INode left = createNode(list.subList(0, count));
                    INode right = createNode(list.subList(count + 1, list.size()));
                    return new DivisionNode(mainOperation, left, right);
                }
                case SUBTRACTION:
                {
                    INode right = createNode(list.subList(count + 1, list.size()));
                    if (count != 0) {
                        INode left = createNode(list.subList(0, count));
                        return new SubtractionNode(mainOperation, left, right);
                    } else {
                        return new MinusNode(mainOperation, right);
                    }
                }
                case MULTIPLICATION:
                {
                    INode left = createNode(list.subList(0, count));
                    INode right = createNode(list.subList(count + 1, list.size()));
                    return new MultiplicationNode(mainOperation, left, right);
                }
                case LEFTBRACE:
                case RIGHTBRACE:
                    throw new MyException("error in ReadString.createNode: braces");
                default:
                    throw new MyException("not yet implement: node");
            }
        }
    }

    public static double evaluate(INode head, double u, double t){
        return head.evaluate(u, t);
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
//		String allChars = new String(" 100 * sin(-5*(-2-3-4*(u+1)))  ");
//		String allChars = new String("4 + 5 * (sin(cos(u * t)))    ");
//		String allChars = new String("2 * pi + t - u");
//		String allChars = new String("cos(8   5 -    3)");
        String allChars = new String(" ");


        double u = 3;
        double t = 4;

        System.out.println(allChars);
//		tokenize(allChars);
        INode head = buildSyntaxTree(allChars);

        System.out.println("evaluate = " + evaluate(head, u, t));
//		System.out.println("evaluate = " + (100*Math.sin(-5*(-2-3-4*(u+1)))));
        System.out.println("evaluate = " + (Math.sin((t )) ));
    }


}
