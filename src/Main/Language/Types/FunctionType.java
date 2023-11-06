package Main.Language.Types;

/**
 * Abstract class that holds information about function
 */
public abstract class FunctionType extends Type {

    /**
     * Apply function to be created by each function declaration and ran using the apply function
     * @param list list of arguments
     * @return executed code
     * @throws LanguageError throw error if issue with execution of code
     */
    public abstract Type apply(ListType list) throws LanguageError;

    @Override
    public String toString() {
        return "#<function>";
    }

}
