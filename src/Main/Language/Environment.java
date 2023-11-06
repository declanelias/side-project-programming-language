package Main.Language;

import Main.Language.Types.LanguageError;
import Main.Language.Types.ListType;
import Main.Language.Types.SymbolType;
import Main.Language.Types.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to store information about the environment. The environment stores all the variables and
 * functions declared during running of the code
 */

public final class Environment {
    private final Environment outer;
    private final Map<String, Type> symbolMap = new HashMap<>();

    public Environment() {
        this.outer = null;
    }
    public Environment(Environment outer) {
        this.outer = outer;
    }

    public Environment(Environment outer, ListType params, ListType args) {
        this.outer = outer;

        bindParamsToArgs(params, args);

    }

    /**
     * Used in function declaration. When a function is declared it creates a new environment, and
     * stores the arguments as a value to the parameters
     * @param params params to be entered as keys to environment
     * @param args arguments to be entered as values to corresponding keys
     */
    private void bindParamsToArgs(ListType params, ListType args) {

        for (int i = 0; i < params.size(); i++) {
            set(((SymbolType) params.get(i)).getName(), args.get(i));
        }
    }

    /**
     * Sets variable with value to environment
     * @param varName variable name
     * @param var value of variable
     */
    public void set(String varName, Type var) {
        symbolMap.put(varName, var);
    }

    /**
     * Finds the environment in which the symbol is contained
     * @param symbol symbol to be found
     * @return environment where symbol is held
     */
    public Environment find(SymbolType symbol) {
        if (symbolMap.containsKey(symbol.getName())) {
            return this;
        } else if (outer != null) {
            return outer.find(symbol);
        } else {
            return null;
        }
    }

    /**
     * Get value of symbol from env
     *
     * @param symbol symbol to be looked up
     * @return value of symbol
     * @throws LanguageError if symbol not found
     */
    public Type get(SymbolType symbol) throws LanguageError {
        Environment environment = find(symbol);
        if (environment == null) {
            // TODO change this to error
            throw new LanguageError(symbol + " not found");
        } else {
            return environment.symbolMap.get(symbol.getName());
        }
    }


    /**
     * Same as previous set function but with symbol as param instead of string
     */
    public void set(SymbolType symbol, Type value) {
        symbolMap.put(symbol.getName(), value);
    }

    @Override
    public String toString() {
        return symbolMap.toString();
    }
}
