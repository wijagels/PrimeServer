package us.jagels.PrimeServer;

import java.math.BigInteger;
import java.util.Collections;
import java.util.TreeSet;

public class ThreadedPrimeFiller extends Thread {
    private final int threads = 6;
    private TreeSet<BigInteger> primes;
    private BigInteger upper;

	public ThreadedPrimeFiller(BigInteger maxValue) {
		this.upper = maxValue;
		TreeSet<BigInteger> primes = (TreeSet<BigInteger>) Collections.synchronizedSortedSet(new TreeSet<BigInteger>());
		this.primes = primes;
		primes.add(new BigInteger("2"));
	}
	
	private void startGenerator(BigInteger lower, BigInteger upper) {
		
	}

}
