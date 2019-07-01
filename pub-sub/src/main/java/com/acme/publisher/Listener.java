package com.acme.publisher;

public interface Listener<E> {

    void onEvent(String channel, E e);

}
