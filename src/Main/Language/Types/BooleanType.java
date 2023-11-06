package Main.Language.Types;

import java.util.Objects;

public class BooleanType extends Type{

    private final boolean value;

    public BooleanType(String value) {
        this.value = Boolean.parseBoolean(value);
    }

    public BooleanType(boolean value) {
        this.value = value;
    }

    public boolean isTrue() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value).toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanType that = (BooleanType) o;
        return value == that.value;
    }
}
