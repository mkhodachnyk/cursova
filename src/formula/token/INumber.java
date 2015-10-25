package formula.token;

import formula.token.*;

public interface INumber extends IToken{

    /** @return возвращает значение числового токена или константы.
     */
    double getValue();
}
