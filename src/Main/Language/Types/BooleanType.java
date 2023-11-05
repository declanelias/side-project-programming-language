package Main.Language.Types;

public class BooleanType extends Type{

    private final boolean value;

    public BooleanType(String value) {
        this.value = Boolean.parseBoolean(value);
    }

    public boolean isTrue() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value).toUpperCase();
    }
}
