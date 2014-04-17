package parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DefaultParserTest {

    private DefaultParser underTest;

    @Before
    public void setup() {
        underTest = new DefaultParser(new DefaultItemFactory());
    }

    @Test
    public void parseItemShouldReturnItem() throws IOException {
        List<Item> result = underTest.parser(new StringReader(" (A {})"));

        assertEquals("A", result.get(0).getName());
    }

    @Test(expected = ParserException.class)
    public void parseShouldThrowParserExceptionIfEndingBracketMissing() throws IOException {
        underTest.parser(new StringReader("(A {}"));
    }

    @Test(expected = ParserException.class)
    public void parseShouldThrowParserExceptionIfDependenciesMissing() throws IOException {
        underTest.parser(new StringReader("(A)"));
    }

    @Test(expected = ParserException.class)
    public void parseShouldThrowParserExceptionIfStartingBracketMissing() throws IOException {
        underTest.parser(new StringReader("A {})"));
    }

    @Test(expected = ParserException.class)
    public void parseShouldThrowParserExceptionIfStartingCurlyBracketMissing() throws IOException {
        underTest.parser(new StringReader("(A })"));
    }

    @Test(expected = ParserException.class)
    public void parseShouldThrowParserExceptionIfEndingCurlyBracketMissing() throws IOException {
        underTest.parser(new StringReader("(A {)"));
    }

    @Test
    public void parseShouldReturnItemWithDependency() throws IOException {
        List<Item> items = underTest.parser(new StringReader("(A {B })"));
        assertEquals("A", items.get(0).getName());
        assertEquals("[B]", items.get(0).getDirectDependencies().toString());
    }

    @Test(expected = ParserException.class)
    public void parseShouldThrowParserExceptionIfCharsAfterDepepndencies() throws IOException {
        underTest.parser(new StringReader("(A {} illegal)"));
    }

    @Test(expected = ParserException.class)
    public void parseShouldThrowParserExceptionIfCharsBetweenItems() throws IOException {
        underTest.parser(new StringReader("(A {}) illegal (B {})"));
    }

    @Test(expected = ParserException.class)
    public void parseShouldThrowParserExceptionIfNoName() throws IOException {
        underTest.parser(new StringReader("({A})"));
    }

    @Test(expected = ParserException.class)
    public void parseShouldThrowParserExceptionIfBadlyFormatted() throws IOException {
        underTest.parser(new StringReader("({A ("));
    }

    @Test
    public void parseShouldReturnItems() throws IOException {
        List<Item> items = underTest.parser(new StringReader("(A {B }) (B {})"));
        assertEquals("A", items.get(0).getName());
        assertEquals("[B]", items.get(0).getDirectDependencies().toString());
        assertEquals("B", items.get(1).getName());
        assertEquals("[]", items.get(1).getDirectDependencies().toString());
    }

}
