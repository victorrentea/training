package com.acme.impl;

import com.acme.publisher.Source;
import com.acme.util.Pair;

public class QuoteSource implements Source<String> {

    private final String[][] quotes = new String[][] {
        new String [] { "Beware", "of", "bugs", "in", "the", "above", "code;", "I", "have", "only", "proved", "it", "correct,", "not", "tried", "it." },
        new String [] {"Any", "inaccuracies", "in", "this", "index", "may", "be", "explained", "by", "the", "fact", "that", "it", "has", "been", "sorted", "with", "the", "help", "of", "a", "computer"}};

    public QuoteSource() {
    }

    @Override
    public Pair<String, String> getNext() {
        try {
            long sleepTime =(long)(Math.random()*2000);
            Thread.sleep(sleepTime);
            int chooseQuote = Math.random() < 0.5 ? 0 : 1;
            int index = ((int)(Math.random()*quotes[chooseQuote].length))%quotes[chooseQuote].length;
            return new Pair<String, String >("Quote_" + (chooseQuote+1), quotes[chooseQuote][index]);
            } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Pair<String, String>(null, null);
    }
}
