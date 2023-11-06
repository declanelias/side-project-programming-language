package Main.Language.Types;

public abstract class FunctionType extends Type {

    public abstract Type apply(ListType list) throws LanguageError;

    @Override
    public String toString() {
        return "#<function>";
    }

}
