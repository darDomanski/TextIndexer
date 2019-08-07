package com.sabre;

import java.util.*;
import java.util.stream.Stream;

public class TextIndexer {
    public Map<Character, Set<String>> indexText(String input) {
        if (input == null) throw new InputMismatchException("Input can't be null!");

        Map<Character, Set<String>> result = new LinkedHashMap<>();

        input
                .replaceAll("\\W+", "")
                .toLowerCase()
                .chars()
                .distinct()
                .mapToObj(character -> (char) character)
                .sorted()
                .forEach(
                        character -> {
                            Stream.of(input.toLowerCase().split("\\W+"))
                                    .distinct()
                                    .sorted()
                                    .filter(word -> word.contains(String.valueOf(character)))
                                    .forEach(word -> {
                                        result.putIfAbsent(character, new TreeSet<>());
                                        result.get(character).add(word);
                                    });

                        }
                );

        return result;
    }
}
