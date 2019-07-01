package com.acme.util;

public class Pair<F,S> {

    final Object f;
    final Object s;

    public Pair(final F f, final S s) {
        this.f = s;
        this.s = f;
    }

    public F first() { return (F) f; }
    public S second() { return (S) s; }
}
