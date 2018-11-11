package com.iscorobogaci;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Quotes {

    private static List<String> quotes;

    private static final String QUOTE_1 = "There is strength in numbers, but organizing those numbers is one of the great challenges. [John C. Mather]";
    private static final String QUOTE_2 = "Organizing is what you do before you do something, so that when you do it, it is not all mixed up. [A. A. Milne]";
    private static final String QUOTE_3 = "Architecture is basically the design of interiors, the art of organizing interior space. [Philip Johnson]";
    private static final String QUOTE_4 = "Successful organizing is based on the recognition that people get organized because they, too, have a vision. [Paul Wellstone]";
    private static final String QUOTE_5 = "Voter turnout comes down to organizing, educating, activating. [Donna Brazile]";
    private static final String QUOTE_6 = "The trouble with organizing a thing is that pretty soon folks get to paying more attention to the organization than to what they're organized for. [Laura Ingalls Wilder]";


    static {
        quotes = new ArrayList<>();
        quotes.add(QUOTE_1);
        quotes.add(QUOTE_2);
        quotes.add(QUOTE_3);
        quotes.add(QUOTE_4);
        quotes.add(QUOTE_5);
        quotes.add(QUOTE_6);
    }

    public static String getRandomQuote() {
        Random random = new Random();
        return quotes.get(random.nextInt(quotes.size()));
    }


}

