package com.sabre;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextIndexerTest {
    private Map<Character, Set<String>> result;
    private TextIndexer textIndexer;

    @BeforeEach
    public void clearResult() {
        result = null;
    }

    @BeforeEach
    public void initializeTextIndexer() {
        textIndexer = new TextIndexer();
    }

    private Map<Character, Set<String>> initializeExpectedResult() {
        Map<Character, Set<String>> expected = new TreeMap<>();
        expected.put('a', new TreeSet<>(Arrays.asList("aaa")));
        expected.put('b', new TreeSet<>(Arrays.asList("bbb")));
        expected.put('c', new TreeSet<>(Arrays.asList("ccc")));
        expected.put('e', new TreeSet<>(Arrays.asList("eee")));
        expected.put('x', new TreeSet<>(Arrays.asList("xyz")));
        expected.put('y', new TreeSet<>(Arrays.asList("xyz")));
        expected.put('z', new TreeSet<>(Arrays.asList("xyz")));

        return expected;
    }

    @Test
    public void testNullInput() {
        assertThrows(InputMismatchException.class, () -> textIndexer.indexText(null));
    }

    @Test
    public void testVeryShortInput() {
        String input = "a";
        Map<Character, Set<String>> expected = new TreeMap<>();
        expected.put('a', new TreeSet<>());
        expected.get('a').add(input);

        result = textIndexer.indexText(input);

        assertEquals(expected, result);
    }

    @Test
    public void testLongInput() {
        String input = "aaa xyz eee ccc bbb";
        Map<Character, Set<String>> expected = initializeExpectedResult();

        result = textIndexer.indexText(input);

        assertEquals(expected, result);
    }

    @Test
    public void testCaseInsensitivity() {
        String input = "Aaa xYz eeE CCC BbB";
        Map<Character, Set<String>> expected = initializeExpectedResult();

        result = textIndexer.indexText(input);

        assertEquals(expected, result);
    }

    @Test
    public void testInputWithRepeatingWords() {
        String input = "XyZ aaa xyz  aaa eee ccc xyz bbb BBB";
        Map<Character, Set<String>> expected = initializeExpectedResult();
        result = textIndexer.indexText(input);

        assertEquals(expected, result);
    }

    @Test
    public void testInputWithPunctuationMarks() {
        String input = "!aaa ,./ xyz{} []eee ...ccc ??? bbb!";
        Map<Character, Set<String>> expected = initializeExpectedResult();

        result = textIndexer.indexText(input);

        assertEquals(expected, result);
    }

    @Test
    public void testInputWhereCharacterOccursInMoreThanOneWords() {
        String input = "aaa xyz eee ccc bbb yyz zzz ";
        Map<Character, Set<String>> expected = initializeExpectedResult();
        expected.get('y').add("yyz");
        expected.get('z').addAll(Arrays.asList("yyz", "zzz"));

        result = textIndexer.indexText(input);

        assertEquals(expected, result);
    }
}