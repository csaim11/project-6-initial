package com.example.aggregator.client;

import com.example.aggregator.model.Entry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AggregatorRestClient {

    private RestTemplate restTemplate;

    public AggregatorRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Entry getDefinitionFor(String word) {

        //String uri = "http://localhost:9091/getWord/" + word;
        String uri = "http://localhost:9090/getWord/" + word;
        Entry result = restTemplate.getForObject(uri, Entry.class);

        return result;
    }

    public List<Entry> getWordsStartingWith(String chars) {

        //String uri = "http://localhost:9091/getWordsStartingWith/" + chars;
        String uri = "http://localhost:9090/getWordsStartingWith/" + chars;
        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);

        Entry[] entryArray = responseEntity.getBody();
        return Arrays.stream(entryArray)
                .collect(Collectors.toList());
    }

    public List<Entry> getWordsThatContain(String chars) {

        //String uri = "http://localhost:9091/getWordsThatContain/" + chars;
        String uri = "http://localhost:9090/getWordsThatContain/" + chars;
        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);

        Entry[] entryArray = responseEntity.getBody();
        return Arrays.stream(entryArray)
                .collect(Collectors.toList());
    }

    public List<Entry> getWordsThatContainConsecutiveLetters() {

        //String uri = "http://localhost:9091/getWordsThatContainConsecutiveLetters";
        String uri = "http://localhost:9090/getWordsThatContainConsecutiveLetters";
        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);

        Entry[] entryArray = responseEntity.getBody();
        return Arrays.stream(entryArray)
                .collect(Collectors.toList());

    }

    // add a method to get words that end with a given string
    public List<Entry> getWordsEndingWith(String chars) {

        //String uri = "http://localhost:9091/getWordsEndingWith/" + chars;
        String uri = "http://localhost:9090/getWordsEndingWith/" + chars;
        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);

        Entry[] entryArray = responseEntity.getBody();
        return Arrays.stream(entryArray)
                .collect(Collectors.toList());
    }

}
