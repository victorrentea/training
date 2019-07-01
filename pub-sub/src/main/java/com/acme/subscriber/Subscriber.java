package com.acme.subscriber;


import com.acme.publisher.Listener;
import com.acme.publisher.Publisher;

public class Subscriber<E> implements Listener<E> {

    String name;

    public Subscriber(String name, String channel, Publisher<E> publisher) {
        this.name = name;
        publisher.subscribe(channel, this);
    }

    @Override
    public void onEvent(final String channel, final E e) {
        System.out.println(name + ": received an event: " + e);
    }
}
