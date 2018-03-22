package exceptions;

public class WrongUserPasswordException extends Exception {

    public WrongUserPasswordException() {
    }

    public WrongUserPasswordException(String s) {
        super(s);
    }
}
