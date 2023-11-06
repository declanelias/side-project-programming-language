package Test;

import Main.Language.Environment;
import Main.Language.Evaluator.Evaluator;
import Main.Language.Language;
import Main.Language.NameSpace;
import Main.Language.Reader.Reader;
import Main.Language.Types.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EvaluatorTest {

    private Type runCode(String code) throws LanguageError {
        NameSpace ns = new NameSpace(false);
        Environment env = ns.getEnv();

        Reader reader = new Reader(code);
        ListType ast = reader.createAst();
        Evaluator evaluator = new Evaluator();
        return evaluator.evaluate(ast, env);
    }

    @Test
    public void testAdd() throws LanguageError {
        String code = "(+ 1 2)";
        Type ranCode = runCode(code);

        assertEquals(new NumberType(3), ranCode);
    }

    @Test
    public void testAdd2() throws LanguageError {
        String code = "(+ 1 (+ 2 (+ 3 4)))";
        Type ranCode = runCode(code);

        assertEquals(new NumberType(10), ranCode);
    }

    @Test
    public void testSubtract() throws LanguageError {
        String code = "(- 1 2)";
        Type ranCode = runCode(code);

        assertEquals(new NumberType(-1), ranCode);
    }

    @Test
    public void testMultiplication() throws LanguageError {
        String code = "(* 1 2)";
        Type ranCode = runCode(code);

        assertEquals(new NumberType(2), ranCode);
    }

    @Test
    public void testDivide() throws LanguageError {
        String code = "(/ 1 2)";
        Type ranCode = runCode(code);

        assertEquals(new NumberType(.5), ranCode);
    }

    @Test
    public void testEqualities() throws LanguageError {
        String code = "(> 1 2)";
        Type ranCode = runCode(code);
        assertEquals(new BooleanType(false), ranCode);

        code = "(< 1 2)";
        ranCode = runCode(code);
        assertEquals(new BooleanType(true), ranCode);

        code = "(<= 2 2)";
        ranCode = runCode(code);
        assertEquals(new BooleanType(true), ranCode);

        code = "(<= 2 100)";
        ranCode = runCode(code);
        assertEquals(new BooleanType(true), ranCode);

        code = "(>= 2 2)";
        ranCode = runCode(code);
        assertEquals(new BooleanType(true), ranCode);

        code = "(>= 100 2)";
        ranCode = runCode(code);
        assertEquals(new BooleanType(true), ranCode);

        code = "(== 2 2)";
        ranCode = runCode(code);
        assertEquals(new BooleanType(true), ranCode);

        code = "(== 'a' 'a')";
        ranCode = runCode(code);
        assertEquals(new BooleanType(true), ranCode);
    }

    @Test
    public void testFunction() throws LanguageError {
        String code = "(fn a (a) (+ a 1)) (a 1)";
        Type ranCode = runCode(code);

        assertEquals(new NumberType(2), ranCode);
    }

    @Test
    public void testFunction2() throws LanguageError {
        String code = "(fn a (a) (+ a 1))";
        Type ranCode = runCode(code);

        assertEquals(new ListType(new ArrayList<>()), ranCode);

    }

    @Test
    public void testVariableDeclaration() throws LanguageError {
        String code = "(def a 3)";
        Type ranCode = runCode(code);
        assertEquals(new NumberType(3), ranCode);

    }

    @Test
    public void testVariableDeclaration2() throws LanguageError {
        String code = "(def a 3) (+ a 1)";
        Type ranCode = runCode(code);
        List<Type> l = new ArrayList<>();
        l.add(new NumberType(3));
        l.add(new NumberType(4));
        assertEquals(new ListType(l), ranCode);
    }

    @Test
    public void testDo() throws LanguageError {
        String code = "do a 100 a";
        Type ranCode = runCode(code);

        assertEquals(new NumberType(99), ranCode);
    }

    @Test
    public void testDo2() throws LanguageError {
        String code = "(do a (def b 100) a)";
        Type ranCode = runCode(code);

        assertEquals(new NumberType(99), ranCode);
    }

    @Test
    public void testDo3() throws LanguageError {
        String code = "(def b 100) (do a b a)";
        Type ranCode = runCode(code);
        List<Type> l = new ArrayList<>();
        l.add(new NumberType(100));
        l.add(new NumberType(99));

        assertEquals(new ListType(l), ranCode);
    }

    @Test
    public void testFuncWithIf() throws LanguageError {
        String code = "(fn test (a) (if (== a 10) 'right' 'wrong')) (test 10)";
        Type ranCode = runCode(code);

        assertEquals(new StringType("right"), ranCode);
    }

    @Test
    public void testStringFunction() throws LanguageError {
        String code = "append 'a' 'b'";
        Type ranCode = runCode(code);
        assertEquals(new StringType("ab"), ranCode);
    }

    @Test(expected = LanguageError.class)
    public void testStringFuncError() throws LanguageError {
        String code = "append 'a' 1";
        Type ranCode = runCode(code);
    }

    @Test(expected = LanguageError.class)
    public void testStringFuncError2() throws LanguageError {
        String code = "+ 'a' 1";
        Type ranCode = runCode(code);
    }

    @Test(expected = LanguageError.class)
    public void wrongNumFunctionParams() throws LanguageError {
        String code = "+ 1 1 1";
        Type ranCode = runCode(code);
    }

    @Test(expected = LanguageError.class)
    public void wrongNumFunctionParams2() throws LanguageError {
        String code = "(fn a (a) (+ 'a' 1)) (a 1 2)";
        Type ranCode = runCode(code);
    }

    @Test(expected = LanguageError.class)
    public void doError() throws LanguageError{
        String code = "(do a 'a' (a))";
        Type ranCode = runCode(code);
    }



}
