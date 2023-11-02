package Language.Types;

public class ErrorType extends Type{

    private final String code;

    public ErrorType(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ErrorType{" +
                "code='" + code + '\'' +
                '}';
    }
}
