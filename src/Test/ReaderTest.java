package Test;

import Main.Language.Reader.Reader;
import Main.Language.Types.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReaderTest {

    @Test
    public void testList() throws LanguageError {
        String code = "(1 2 3)";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();
        l.add(new NumberType(1));
        l.add(new NumberType(2));
        l.add(new NumberType(3));

        assertEquals(new ListType(l), ast.get(0));
    }

    @Test
    public void testNestedList() throws LanguageError {
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

        assertEquals(new ListType(l), ast.get(0));
    }


    @Test
    public void testEmptyCode() throws LanguageError {
        String code = "";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();

        assertEquals(new ListType(new ArrayList<>()), ast);
    }

    @Test
    public void testAdd() throws LanguageError {
        String code = "(+ 2 3)";
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();

        List<Type> l = new ArrayList<>();
        l.add(new SymbolType("+"));
        l.add(new NumberType(2));
        l.add(new NumberType(3));

        assertEquals(new ListType(l), ast.get(0));
    }

    @Test
    public void testFunctionDeclaration() throws LanguageError {
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

        assertEquals( new ListType(l), ast.get(0));
    }

    @Test
    public void testFunctionDeclarationMultipleParams() throws LanguageError {
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

        assertEquals( new ListType(l), ast.get(0));
    }

    @Test
    public void testFunctionWithArgs() throws LanguageError {
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

        assertEquals( new ListType(lt), ast);
    }

    @Test
    public void testDo() throws LanguageError {
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

        assertEquals(new ListType(l), ast);
    }

    @Test(expected = LanguageError.class)
    public void testUnclosedParens() throws LanguageError {
        new Reader("(a").createAst();
    }

    @Test(expected = LanguageError.class)
    public void testExtraClosedParens() throws LanguageError {
        new Reader("(a))").createAst();
    }
}
