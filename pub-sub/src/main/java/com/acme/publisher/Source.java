package com.acme.publisher;


import com.acme.util.Pair;

public interface Source<E> {

    Pair<String, E> getNext();

}
