package Main.Language.Evaluator;

import Main.Language.Environment;
import Main.Language.Types.*;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {

    public Type evaluate(Type ast, Environment env) throws ErrorType {
        if (!(ast instanceof ListType astList)) {
            return checkForSymbol(ast, env);
        }

        Type first;
        try {
             first = astList.get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new ErrorType("Invalid syntax");
        }

        if (first instanceof SymbolType symbol) {
            return evaluateSymbol(symbol, astList, env);
        } else if (first instanceof FunctionType func) {
            return evaluateFunction(func, astList.restOfList(), env);
        } else if (first instanceof NumberType || first instanceof StringType) {
            return evaluateList(astList, env);
        } else {
//            if (astList.size() == 1) {
//                astList = (ListType) first;
//            }


//            return evaluate(evaluateList(astList, env), env);
            return evaluateList(astList, env);
        }
    }

    private Type evaluateList(ListType astList, Environment env) throws ErrorType {
        List<Type> list = new ArrayList<>();
        for (int i = 0; i < astList.size(); i++) {
            list.add(evaluate(astList.get(i), env));
        }

        return new ListType(list);
    }

    private Type evaluateSymbol(SymbolType symbol, ListType listType, Environment env) throws ErrorType {
        switch (symbol.getName()) {
            case "def" ->   {return declareVariable(listType.restOfList(), env);}
            case "if" ->    {return evaluateIf(listType.restOfList(), env);}
            case "fn" ->    {return declareFunction(listType.restOfList(), env);}
            case "do" ->    {return evaluateDo(listType.restOfList(), env);}
            default ->      {return getSymbolValue(symbol, listType.restOfList(), env);}
        }
    }

    private FunctionType declareFunction(ListType funcList, Environment env) throws ErrorType {
        // TODO error if not list types

        if (funcList.size() != 3) {
            throw new ErrorType("Incorrect function declaration");
        }

        try {
            SymbolType funcName = (SymbolType) funcList.get(0);
            ListType params = ((ListType) funcList.get(1));
            Type funcBody = funcList.get(2);

            FunctionType function = new FunctionType() {
                @Override
                public Type apply(ListType args) throws ErrorType {
                    return evaluate(funcBody, new Environment(env, params, args));
                }
            };

            env.set(funcName.getName(), function);

            return function;
        } catch (ClassCastException o) {
            throw new ErrorType("Incorrect function declaration");
        }
    }

    private Type evaluateDo(ListType listType, Environment env) throws ErrorType {

        SymbolType symbol = (SymbolType) listType.get(0);
        Type second = listType.get(1);
        NumberType doNum;
        if (second instanceof NumberType numberType) {
            doNum = numberType;
        } else if (second instanceof SymbolType symbolType) {
            try {
                doNum = (NumberType) env.get(symbolType);
            } catch (ErrorType e){
                throw e;
            }
        } else {
            throw new ErrorType(second + " of type " + second.getClass().toString() + " should be of type NumberType");
        }

        if (!(listType.get(1) instanceof NumberType)) {
            throw new ErrorType("Incorrect do declaration");
        }


        Type body = listType.get(2);
        Environment doEnv = new Environment(env);
        double num = doNum.getValue();

        Type returnVal = null;
        for (double i = 0; i < num; i ++) {
            doEnv.set(symbol.getName(), new NumberType(i));
            returnVal = evaluate(body, doEnv);
        }

        return returnVal;
    }

    private Type getSymbolValue(SymbolType symbol, ListType listType, Environment env) throws ErrorType {
        Type envSymbol = env.get(symbol);
        if (envSymbol instanceof FunctionType func) {
            return evaluateFunction(func, listType, env);
        } else {
            return envSymbol;
        }
    }

    private Type evaluateFunction(FunctionType func, ListType args, Environment env) throws ErrorType {
        List<Type> argsList = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            Type type = checkForSymbol(args.get(i), env);
            argsList.add(evaluate(type, env));
        }
        return func.apply(new ListType(argsList));
    }


    private Type evaluateIf(ListType listType, Environment env) throws ErrorType {
        if (listType.size() != 3) {
            throw new ErrorType("Wrong number of arguments to if");
        }

        Type firstArg = evaluate(listType.get(0), env);
        if (firstArg instanceof BooleanType condition) {
            if (condition.isTrue()) {
                return evaluate(listType.get(1), env);
            } else {
                return evaluate(listType.get(2), env);
            }
        } else {
            throw new ErrorType("Condition is not of type boolean");
        }
    }

    private Type declareVariable(ListType listType, Environment env) throws ErrorType {
        if (listType.size() != 2) {
            throw new ErrorType("wrong number of arguments in variable declaration");
        }
        Type type = evaluate(listType.get(1), env);
        SymbolType varName = (SymbolType) listType.get(0);
        env.set(varName, type);
        return type;
    }

    private Type checkForSymbol(Type type, Environment env) throws ErrorType {
        if (type instanceof SymbolType symbol) {
            return env.get(symbol);
        }
        return type;
    }
}
