package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class ComplexTask {
    private AtomicInteger atomicInteger;

    public ComplexTask() {
        this.atomicInteger = new AtomicInteger(0);
    }

    public void execute() {
        atomicInteger.incrementAndGet();
    }

    public int getAtomicInteger() {
        return atomicInteger.get();
    }
}
