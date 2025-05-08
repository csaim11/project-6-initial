package com.example.aggregator.service;

import com.example.aggregator.client.AggregatorRestClient;
import com.example.aggregator.model.Entry;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class AggregatorService {

    private final AggregatorRestClient aggregatorRestClient;

    //private RestTemplate restTemplate = new RestTemplate();
    //private AggregatorRestClient restClient = new AggregatorRestClient(restTemplate);

    public AggregatorService(AggregatorRestClient aggregatorRestClient) {
        this.aggregatorRestClient = aggregatorRestClient;
    }

    public Entry getDefinitionFor(String word) {
        return aggregatorRestClient.getDefinitionFor(word);
    }

    public List<Entry> getWordsThatContainSuccessiveLettersAndStartsWith(String chars) {

        List<Entry> wordsThatStartWith = aggregatorRestClient.getWordsStartingWith(chars);
        List<Entry> wordsThatContainSuccessiveLetters = aggregatorRestClient.getWordsThatContainConsecutiveLetters();

        // stream API version
        List<Entry> common = wordsThatStartWith.stream()
                .filter(wordsThatContainSuccessiveLetters::contains)
                .toList();

        /*List<Entry> common = new ArrayList<>(wordsThatStartWith);
        common.retainAll(wordsThatContainSuccessiveLetters);*/

        return common;

    }

    public List<Entry> getWordsThatContain(String chars) {

        return aggregatorRestClient.getWordsThatContain(chars);

    }

    // add a method to get all Palindrome words
    public List<Entry> getAllPalindromes() {

        final List<Entry> candidates = new ArrayList<>();

        //RestTemplate restTemplate = new RestTemplate();
        //AggregatorRestClient restClient = new AggregatorRestClient(restTemplate);

        //AggregatorRestClient restClient = new AggregatorRestClient();
        // Iterate from a to z
        IntStream.range('a', '{')
                .mapToObj(i -> Character.toString(i))
                .forEach(c -> {

                    // get words starting and ending with character
                    //AggregatorRestClient restClient = null;

                    //AggregatorRestClient restClient = new AggregatorRestClient();

                    List<Entry> startsWith = aggregatorRestClient.getWordsStartingWith(c);
                    List<Entry> endsWith = aggregatorRestClient.getWordsEndingWith(c);

                    // keep entries that exist in both lists
                    List<Entry> startsAndEndsWith = new ArrayList<>(startsWith);
                    startsAndEndsWith.retainAll(endsWith);

                    // store list with existing entries
                    candidates.addAll(startsAndEndsWith);

                });

        // test each entry for palindrome, sort and return
        return candidates.stream()
                .filter(entry -> {
                    String word = entry.getWord();
                    String reverse = new StringBuilder(word).reverse()
                            .toString();
                    return word.equals(reverse);
                })
                .sorted()
                .collect(Collectors.toList());
    }


    /* rewrite the Palindrome service with no Stream, but loops and/or if statements
       return the same type of list as the original method

    Algorithm:
    1. Iterate the lower case alphabet from 'a' to 'z';
    2. Get the words that start with 'a' to 'z', respectively;
    3. Get the words that end with 'a' to 'z', respectively;
    4. If the word exists in both lists (i.e. intersection), add it to the candidates list;
    5. For each candidate, check if it is a palindrome;
    6. If it is a palindrome, add it to the palindromes list;
    7. Sort the palindromes list by word lexicographically; and
    8. Return the palindromes list.


    public List<Entry> getAllPalindromes() {
        List<Entry> candidates = new ArrayList<>();

        // Initialize the rest client once
        AggregatorRestClient restClient = new AggregatorRestClient();

        // Iterate from 'a' to 'z'
        for (char ch = 'a'; ch <= 'z'; ch++) {
            String c = Character.toString(ch);

            // Get words starting and ending with the character
            List<Entry> startsWith = restClient.getWordsStartingWith(c);
            List<Entry> endsWith = restClient.getWordsEndingWith(c);

            // Keep only entries that exist in both lists
            for (Entry entry : startsWith) {
                if (endsWith.contains(entry)) {
                    candidates.add(entry);
                }
            }
        }

        // Filter for palindromes
        List<Entry> palindromes = new ArrayList<>();
        for (Entry entry : candidates) {
            String word = entry.getWord();
            if (isPalindrome(word)) {
                palindromes.add(entry);
            }
        }

        // Sort the list by word lexicographically
        for (int i = 0; i < palindromes.size() - 1; i++) {
            for (int j = i + 1; j < palindromes.size(); j++) {
                String word1 = palindromes.get(i).getWord();
                String word2 = palindromes.get(j).getWord();
                if (word1.compareTo(word2) > 0) {
                    // Swap
                    Entry temp = palindromes.get(i);
                    palindromes.set(i, palindromes.get(j));
                    palindromes.set(j, temp);
                }
            }
        }

        return palindromes;
    }

    private boolean isPalindrome(String word) {
        int left = 0;
        int right = word.length() - 1;
        while (left < right) {
            if (word.charAt(left) != word.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    */
}
