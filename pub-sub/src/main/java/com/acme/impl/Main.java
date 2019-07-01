package com.acme.impl;

import com.acme.publisher.Publisher;
import com.acme.subscriber.Subscriber;

public class Main {

    /*
    the following application creates two subscribers which listen to random words from two different quotes and prints
    them out as they come. The program will terminate after 60 seconds. A sample output is like this:
Subscriber-2: received an event: help
Subscriber-1: received an event: tried
Subscriber-1: received an event: it
Subscriber-2: received an event: of
Subscriber-1: received an event: not
Subscriber-2: received an event: explained
Subscriber-1: received an event: I

    Unfortunately there are at least nine subtle bugs in the implementation which can cause problems.
    Please identify and fix them. Propose and implement any other change that would improve performance under the assumption that:
    - subscribers can subscribe/unsubscribe at any time.
    - subscribers can take an undetermined amount of time to process the event, their processing time should not impact other subscribers.

    Next we would like to change the subscribers in such a way that they will only run until they have acquired all the
    words for their quote and print them in the right order, once they've done so, they unsubscribe and the programs terminates. Not before then.
    The expected output of the program would then be:
Subscriber-1: Beware of bugs in the above code; I have only proved it correct, not tried it.
Subscriber-2: Any inaccuracies in this index may be explained by the fact that it has been sorted with the help of a computer
    or:
Subscriber-2: Any inaccuracies in this index may be explained by the fact that it has been sorted with the help of a computer
Subscriber-1: Beware of bugs in the above code; I have only proved it correct, not tried it.

    You will have to augment different components in order to have enough information in the subscribers to be able to know how
    to sort the words.

    Be aware that it is likely that words in the quote will change in the future and we do not know upfront what could they be.
    I.e. it could be requested that quote will change to "This code is well ironed out. No reason to refactor it" or anything else.
    Your solution should support also other quotes with another set of words.
    */

    public static void main(String [] args) throws InterruptedException {

        QuoteSource source = new QuoteSource();
        Publisher<String> publisher = new Publisher<String>(source);

        Thread t = new Thread(publisher);
        t.start();
        new Subscriber<String>("Subscriber-1", "Quote_1", publisher);
        new Subscriber<String>("Subscriber-2", "Quote_2", publisher);
        Thread.currentThread().join(60*1000);
        System.exit(0);
    }
}
