package Main.Language.Types;

import java.util.Objects;

public class NumberType extends Type {

    private final double value;


    public NumberType(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    public double getValue() {
        return value;
    }

    public NumberType add(NumberType n) {
        return new NumberType(value + n.getValue());
    }

    public NumberType subtract(NumberType n) {
        return new NumberType(value - n.getValue());
    }

    public NumberType multiply(NumberType n) {
        return new NumberType(value * n.getValue());
    }

    public NumberType divide(NumberType n) {
        return new NumberType(value / n.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberType that = (NumberType) o;
        return Double.compare(value, that.value) == 0;
    }

}
