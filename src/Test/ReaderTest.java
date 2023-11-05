package Test;

import Main.Language.Evaluator.Evaluator;
import Main.Language.Reader.Reader;
import Main.Language.Types.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReaderTest {

    @Test
    public void testList() throws ErrorType {
        String code = "(1 2 3)";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();
        l.add(new NumberType(1));
        l.add(new NumberType(2));
        l.add(new NumberType(3));

        assertEquals(new ListType(l).toString(), ast.get(0).toString());
    }

    @Test
    public void testNestedList() throws ErrorType {
        String code = "(1 2 (1 2))";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();
        l.add(new NumberType(1));
        l.add(new NumberType(2));
        l.add(new ListType(
                List.of(
                        new NumberType(1),
                        new NumberType(2)
                )
        ));

        assertEquals(new ListType(l).toString(), ast.get(0).toString());
    }


    @Test
    public void testEmptyCode() throws ErrorType {
        String code = "";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();

        assertEquals("[]", ast.toString());
    }

    @Test
    public void testAdd() throws ErrorType {
        String code = "(+ 2 3)";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();
        l.add(new SymbolType("+"));
        l.add(new NumberType(2));
        l.add(new NumberType(3));

        assertEquals(new ListType(l).toString(), ast.get(0).toString());
    }

    @Test
    public void testFunctionDeclaration() throws ErrorType {
        String code = "(fn a (b) (+ b 1))";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();
        l.add(new SymbolType("fn"));
        l.add(new SymbolType("a"));
        l.add(new ListType(List.of(new SymbolType("b"))));
        l.add(new ListType(List.of(
                new SymbolType("+"),
                new SymbolType("b"),
                new NumberType(1)
        )));

        assertEquals( new ListType(l).toString(), ast.get(0).toString());
    }

    @Test
    public void testFunctionDeclarationMultipleParams() throws ErrorType {
        String code = "(fn a (b c) (+ b c 1))";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();
        l.add(new SymbolType("fn"));
        l.add(new SymbolType("a"));
        l.add(new ListType(List.of(
                new SymbolType("b"),
                new SymbolType("c")
        )));
        l.add(new ListType(List.of(
                new SymbolType("+"),
                new SymbolType("b"),
                new SymbolType("c"),
                new NumberType(1)
        )));

        assertEquals( new ListType(l).toString(), ast.get(0).toString());
    }

    @Test
    public void testFunctionWithArgs() throws ErrorType {
        String code = "(fn a (b) (+ b 1)) 1";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();
        l.add(new SymbolType("fn"));
        l.add(new SymbolType("a"));
        l.add(new ListType(List.of(new SymbolType("b"))));
        l.add(new ListType(List.of(
                new SymbolType("+"),
                new SymbolType("b"),
                new NumberType(1)
        )));

        List<Type> lt = new ArrayList<>();
        lt.add(new ListType(l));
        lt.add(new NumberType(1));

        assertEquals( new ListType(lt).toString(), ast.toString());
    }

    @Test
    public void testDo() throws ErrorType {
        String code = "do a 100 (+ a 1)";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();
        l.add(new SymbolType("do"));
        l.add(new SymbolType("a"));
        l.add(new NumberType(100));
        l.add(new ListType(List.of(
                new SymbolType("+"),
                new SymbolType("a"),
                new NumberType(1)
        )));

        assertEquals(new ListType(l).toString(), ast.toString());
    }

    @Test(expected = ErrorType.class)
    public void testUnclosedParens() throws ErrorType {
        new Reader("(a").createAst();
    }

    @Test(expected = ErrorType.class)
    public void testExtraClosedParens() throws ErrorType {
        new Reader("(a))").createAst();
    }
}
