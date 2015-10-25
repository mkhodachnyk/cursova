package exceptions;

public class MyException extends RuntimeException{
    public MyException(String str){
        super(str);
    }
    public MyException(){
        super();
    }
}
