package exceptions;

public class DuplicatePhotoException extends Exception {

    public DuplicatePhotoException() {
    }

    public DuplicatePhotoException(String s) {
        super(s);
    }
}
