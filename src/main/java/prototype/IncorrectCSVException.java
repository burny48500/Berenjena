package prototype;

public class IncorrectCSVException extends IllegalArgumentException{
    public IncorrectCSVException() {
    }

    public IncorrectCSVException(String s) {
        super(s);
    }
}
