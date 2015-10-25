package formula.token;


public interface IToken {

    /** @return	возвращает число - приоритет, соответствующий этому токену.
     */
    int getPriority();

    /** @return	возвращает тип токена.
     */
    TokenType getTypeOfToken();

    /** @return	возвращает текст токена.
     */
    String toString();
}
