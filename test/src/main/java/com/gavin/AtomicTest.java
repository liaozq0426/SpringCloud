package com.gavin;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
	
	private static AtomicInteger counter = new AtomicInteger();
	
	
	static int incrementAndGetModulo(int modulo) {
        for (;;) {
            int current = counter.get();
            int next = (current + 1) % modulo;
            if (counter.compareAndSet(current, next))
                return next;
        }
	}
	
	
	public static void main(String[] args) {
		counter.incrementAndGet();
		System.out.println("current counter:" + counter);
		int next = incrementAndGetModulo(16);
		System.out.print("next:" + next);
	}
	
	
	
	
	
	
}
