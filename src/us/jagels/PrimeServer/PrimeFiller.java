package us.jagels.PrimeServer;

import java.math.BigInteger;
import java.util.List;

public class PrimeFiller extends Thread {

	private List<BigInteger> primes;
	private BigInteger upper, lower;

	public PrimeFiller(List<BigInteger> seed, BigInteger maxValue) {
		this.primes = seed;
		this.upper = maxValue;
		this.lower = new BigInteger("2");// when not specified, start at the
											// beginning of all primes
	}

	public PrimeFiller(List<BigInteger> seed, BigInteger start, BigInteger stop) {
		this.primes = seed;
		this.upper = stop;
		this.lower = start;
	}

	@Override
	public void run() {
		for (BigInteger p = new BigInteger("3"); p.compareTo(upper) == -1; p = p
				.add(BigInteger.ONE)) {
			boolean isPrime = false;
			try {
				isPrime = PrimeMath.divisibleByPrime(p,primes);
			} catch (NumberTooLargeException e) {
				e.printStackTrace();
			}
			if (isPrime) {
				primes.add(p);
			}
			/*
			 * if (p > Math.sqrt(Long.MAX_VALUE)) {
			 * System.out.println("Reached " + p); break; }
			 */
		}
	}
}
