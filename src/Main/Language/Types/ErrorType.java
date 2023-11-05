package Main.Language.Types;

public class ErrorType extends Throwable{

    private final String msg;

    public ErrorType(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ERROR: " + msg;
    }
}
