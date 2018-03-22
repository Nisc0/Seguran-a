package exceptions;

public class NoSuchPhotoException extends Exception {

    public NoSuchPhotoException() {
    }

    public NoSuchPhotoException(String s) {
        super(s);
    }
}
