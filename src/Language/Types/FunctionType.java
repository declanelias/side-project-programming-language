package Language.Types;

import Language.Environment;

import java.util.List;
import java.util.function.Function;

public abstract class FunctionType extends Type {

    public abstract Type apply(ListType list);

    @Override
    public String toString() {
        return "#<function>";
    }

    //    private ListType ast;
//    private Environment environment;
//    private Function<List<Type>, Type> function;

//    public FunctionType(Function<List<Type>, Type> function){
//        this.function = function;
//    }

//    public Type apply(List<Type> t) {
//        return function.apply(t);
//    }

//    public FunctionType(ListType ast, Environment environment) {
//        this.ast = ast;
//        this.environment = environment;
//    }

//    private void createFunction() {
//        Environment funcEnv = new Environment(environment);
////        funcEnv.set();
//    }

}
