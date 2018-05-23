package exceptions;

public class NotValidAddressException extends Exception{

    public NotValidAddressException() {
    }

    public NotValidAddressException(String s) {
        super(s);
    }
}
