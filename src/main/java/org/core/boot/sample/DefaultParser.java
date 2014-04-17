package org.core.boot.sample;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultParser implements Parser {

    private final ItemFactory factory;
    private final CharBuffer itemName = new CharBuffer();
    private final CharBuffer dependency = new CharBuffer();

    public DefaultParser(ItemFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Item> parser(Reader input) throws IOException {
        List<Item> items = new ArrayList<Item>();
        int i = -1;
        while ((i = input.read()) != -1) {
            char c = (char) i;
            if (c == ' ') {
                continue;
            }
            if (c == '(') {
                items.add(parseItem(input));
            } else {
                throw new ParserException("'(' expected!");
            }
        }
        return items;
    }

    public Item parseItem(Reader input) throws IOException {
        Item item = factory.create();
        int i = -1;
        char c;
        itemName.reset();
        while ((i = input.read()) != -1) {
            c = (char) i;
            if (c == ' ') {
                continue;
            }
            if (c == ')') {
                assertDependenciesParsed(item);
                break;
            }
            if (c == '{') {
                assertItemName(itemName);
                item.setName(itemName.toString());
                item.setDirectDependencies(parseDependencies(input));
            } else if (item.getDirectDependencies() == null && c != '}') {
                itemName.add(c);
            } else {
                throw new ParserException("')' expected!");
            }
        }
        assertBracketClosed(i);
        return item;
    }

    public Set<String> parseDependencies(Reader input) throws IOException {
        Set<String> deps = new HashSet<String>();
        int i = -1;
        dependency.reset();
        while ((i = input.read()) != -1) {
            char c = (char) i;
            if (c == '}') {
                addNotEmptyDependency(deps, dependency);
                break;
            }
            if (c == ' ') {
                addNotEmptyDependency(deps, dependency);
            } else if (c == ')' || c == '(' || c == '{') {
                throw new ParserException("'}' expected!");
            } else {
                dependency.add(c);
            }
        }

        return deps;
    }

    private void addNotEmptyDependency(Set<String> deps, CharBuffer dependency) {
        String dependencyName = dependency.toString();
        if (!"".equals(dependencyName.trim())) {
            deps.add(dependencyName);
            dependency.reset();
        }
    }

    private void assertBracketClosed(int i) {
        if (i == -1) {
            throw new ParserException("')' expected!");
        }
    }

    private void assertDependenciesParsed(Item item) {
        if (item.getDirectDependencies() == null) {
            throw new ParserException("'{' expected!");
        }
    }

    private void assertItemName(CharBuffer itemName) {
        if ("".equals(itemName.toString().trim())) {
            throw new ParserException("Name required!");
        }
    }

}
