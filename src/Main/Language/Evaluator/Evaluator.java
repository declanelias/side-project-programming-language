package Main.Language.Evaluator;

import Main.Language.Environment;
import Main.Language.Types.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Evaluator class takes the AST from the Reader class and executes the code
 */
public class Evaluator {

    /**
     *
     * @param ast list of tokenized code
     * @param env base environment with predefined functions
     * @return executed code
     * @throws LanguageError if error is found when executing code
     */
    public Type evaluate(Type ast, Environment env) throws LanguageError {
        Type t = _evaluate(ast, env);
        if (t instanceof ListType l) {
            l = cleanList(l);
            if (l.size() == 1) {
                return l.get(0);
            }
        }
        return t;
    }

    /**
     * Cleans the list. When function is declared, an empty list is returned
     * so this function removes the empty list.
     * @param l list to be cleansed
     * @return cleaned list
     */
    private ListType cleanList(ListType l) {
        List<Type> returnList = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            Type t = l.get(i);
            if (t instanceof ListType list) {
                if (list.size() > 0) {
                    returnList.add(cleanList(list));
                }
            } else {
                returnList.add(t);
            }
        }

        return new ListType(returnList);
    }

    /**
     * Function that evaluates the code. Checks if first element of ast is a symbol or function.
     * If it is, evaluate the symbol or the function and repeat until only a single one or a list
     * of Numbers and Strings remain.
     * @param ast tokenized list of code
     * @param env current environment working in
     * @return executed code
     * @throws LanguageError if error encountered when executing code
     */
    private Type _evaluate(Type ast, Environment env) throws LanguageError {
        if (!(ast instanceof ListType astList)) {
            return checkForSymbol(ast, env);
        }

        Type first;
        try {
             first = astList.get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new LanguageError("Invalid syntax");
        }

        if (first instanceof SymbolType symbol) {
            return evaluateSymbol(symbol, astList, env);
        } else if (first instanceof FunctionType func) {
            return evaluateFunction(func, astList.restOfList(), env);
        } else if (first instanceof NumberType || first instanceof StringType) {
            return evaluateList(astList, env);
        } else {
            return evaluateList(astList, env);
        }
    }

    /**
     * Evaluates a list. Evaluates each element of list. If list of only single object, return that object
     * else evaluate the list and return it.
     * @param astList List of tokenized code
     * @param env current environment code is working in
     * @return evaluated list
     * @throws LanguageError if error encountered while evaluating the list
     */
    private Type evaluateList(ListType astList, Environment env) throws LanguageError {
        List<Type> list = new ArrayList<>();
        for (int i = 0; i < astList.size(); i++) {
            Type t = _evaluate(astList.get(i), env);
            if (i > 0 && t instanceof FunctionType) {
                throw new LanguageError("List can only contain Numbers or Strings");
            }
            list.add(t);
        }

        if (list.size() == 1) {
            return list.get(0);
        }

        ListType returnList = new ListType(list);


        Type first = returnList.get(0);
        if (first instanceof FunctionType func) {
            return evaluateFunction(func, returnList.restOfList(), env);
        }

        return returnList;
    }

    /**
     * Evaluates a symbol and either performs an action or returns the symbol.
     * @param symbol Symbol representing some type of data in the environment
     * @param listType rest of the list to be executed if special symbol
     * @param env environment currently working in
     * @return evaluated symbol
     * @throws LanguageError if symbol does not exist in the environment or other error encountered
     */
    private Type evaluateSymbol(SymbolType symbol, ListType listType, Environment env) throws LanguageError {
        switch (symbol.getName()) {
            case "def" ->   {return declareVariable(listType.restOfList(), env);}
            case "if" ->    {return evaluateIf(listType.restOfList(), env);}
            case "fn" ->    {
                declareFunction(listType.restOfList(), env);
                return new ListType(new ArrayList<>());
            }
            case "do" ->    {return evaluateDo(listType.restOfList(), env);}
            default ->      {return getSymbolValue(symbol, listType.restOfList(), env);}
        }
    }

    /**
     * Declares a function in the environment. The function returns a new FunctionType with an
     * implementation of the apply function which stores the function along with a new environment
     * of the function. It also stores the function in the base environment with the given name
     *
     * @param funcList list containing information about the function name, parameters, and body
     * @param env base environment for functino
     * @throws LanguageError if error encountered declaring the function
     */
    private void declareFunction(ListType funcList, Environment env) throws LanguageError {

        if (funcList.size() != 3) {
            throw new LanguageError("Incorrect function declaration");
        }

        try {
            SymbolType funcName = (SymbolType) funcList.get(0);
            ListType params = ((ListType) funcList.get(1));
            Type funcBody = funcList.get(2);

            FunctionType function = new FunctionType() {
                @Override
                public Type apply(ListType args) throws LanguageError {
                    return _evaluate(funcBody, new Environment(env, params, args));
                }
            };

            env.set(funcName.getName(), function);


        } catch (ClassCastException o) {
            throw new LanguageError("Incorrect function declaration");
        }
    }

    /**
     * Evaluates a function by first checking for symbol and evaluating the list of arguments, and then
     * passing the list of arguments to the apply function called on the given function
     *
     * @param func function to be executed
     * @param args arguments to be passed to the function
     * @param env base environemnt of function
     * @return return of executed function
     * @throws LanguageError if error encountered during execution
     */
    private Type evaluateFunction(FunctionType func, ListType args, Environment env) throws LanguageError {
        List<Type> argsList = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            Type type = checkForSymbol(args.get(i), env);
            argsList.add(_evaluate(type, env));
        }
        try {
            return func.apply(new ListType(argsList));
        } catch (ClassCastException e) {
            throw new LanguageError("Invalid arguments to function");
        } catch (IndexOutOfBoundsException e) {
            throw new LanguageError("Wrong number of arguments passed to function");
        }
    }

    /**
     * Evaluates do by getting the symbol storing the loop value, finding the amount of loops to be performed
     * based on the code, and execute the code block the amount of times specified.
     *
     * @param listType list storing instructions for do
     * @param env environment working in
     * @return what the do evaluates to
     * @throws LanguageError if there was an error during execution
     */
    private Type evaluateDo(ListType listType, Environment env) throws LanguageError {

        SymbolType symbol = (SymbolType) listType.get(0);
        Type second = listType.get(1);
        NumberType doNum;
        if (second instanceof NumberType numberType) {
            doNum = numberType;
        } else if (second instanceof SymbolType symbolType) {
            try {
                doNum = (NumberType) env.get(symbolType);
            } catch (LanguageError e){
                throw e;
            }
        } else if (second instanceof ListType l) {
            try {
                doNum = (NumberType) evaluate(l, env);
            } catch (LanguageError e) {
                throw e;
            }
        } else{
            throw new LanguageError(second + " of type " + second.getClass().toString() + " should be of type NumberType");
        }


        Type body = listType.get(2);
        Environment doEnv = new Environment(env);
        double num = doNum.getValue();

        Type returnVal = null;
        for (double i = 0; i < num; i ++) {
            doEnv.set(symbol.getName(), new NumberType(i));
            returnVal = _evaluate(body, doEnv);
        }

        return returnVal;
    }

    /**
     * Get the value of the symbol. If it is function execute it.
     * If not return the value in the environment
     *
     * @param symbol symbol to be found
     * @param listType if symbol is a function execute it
     * @param env environment to find
     * @return
     * @throws LanguageError
     */
    private Type getSymbolValue(SymbolType symbol, ListType listType, Environment env) throws LanguageError {
        Type envSymbol = env.get(symbol);
        if (envSymbol instanceof FunctionType func) {
            return evaluateFunction(func, listType, env);
        } else {
            return envSymbol;
        }
    }


    /**
     * Evaluate if in same manner as do
     *
     * @param listType list containing information about the if statement
     * @param env environment working in
     * @return evaluated if
     * @throws LanguageError if error encountered on execution
     */
    private Type evaluateIf(ListType listType, Environment env) throws LanguageError {
        if (listType.size() != 3) {
            throw new LanguageError("Wrong number of arguments to if");
        }

        Type firstArg = _evaluate(listType.get(0), env);
        if (firstArg instanceof BooleanType condition) {
            if (condition.isTrue()) {
                return _evaluate(listType.get(1), env);
            } else {
                return _evaluate(listType.get(2), env);
            }
        } else {
            throw new LanguageError("If Condition is not of type boolean");
        }
    }

    /**
     * Declare varaible by adding it to environment
     * @param listType list containing variable name and value
     * @param env environment working in
     * @return value of the variable declared
     * @throws LanguageError throw error if wrong number of arguments in variable declaration
     */
    private Type declareVariable(ListType listType, Environment env) throws LanguageError {
        if (listType.size() != 2) {
            throw new LanguageError("Wrong number of arguments in variable declaration");
        }
        Type type = _evaluate(listType.get(1), env);
        SymbolType varName = (SymbolType) listType.get(0);
        env.set(varName, type);
        return type;
    }

    /**
     * If type passed in is a symbol return the value in the env, if not return the value.
     * @param type type to be checked
     * @param env environment to check in
     * @return value of type
     * @throws LanguageError if symbol does not exist in the environment
     */
    private Type checkForSymbol(Type type, Environment env) throws LanguageError {
        if (type instanceof SymbolType symbol) {
            return env.get(symbol);
        }
        return type;
    }
}
