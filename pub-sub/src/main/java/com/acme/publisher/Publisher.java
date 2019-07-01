package com.acme.publisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.acme.util.Pair;

public class Publisher<E> implements Runnable {

    private final Source<E> source;
    HashMap<String, List<Listener<E>>> listeners;

    public Publisher(Source<E> source) {
        this.source = source;
    }


    public synchronized void subscribe(String channel, Listener<E> listener) {
        listeners.get(channel).add(listener);
    }

    public synchronized void unsubscribe(String channel, Listener<E> listener) {
        List<Listener<E>> lst = listeners.get(channel);
        lst.remove(listener);
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {
                Pair<String, E> next = source.getNext();
                synchronized (next) {
                    for(Listener<E> listener: listeners.get(next.first())) {
                        listener.onEvent(next.first(), next.second());
                    }
                }
            } catch(Throwable t) {
                //ignoring it
            }
        }
    }

}
