package us.jagels.PrimeServer;

import java.util.List;

public class PrimeFiller extends Thread {

	private List<Long> primes;
	private int upper;

	public PrimeFiller(List<Long> p, int upper) {
		primes = p;
		this.upper = upper;
	}

	@Override
	public void run() {
		long p = 3;
		for (int i = 0; i < this.upper; i++) {
			boolean isPrime;
			try {
				isPrime = divisibleByPrime(p);
			} catch (NumberTooLargeException e) {
				System.out.println("Using slow method for: " + p);
				isPrime = isPrime(p);
			}
			if (isPrime) {
				primes.add(p);
			}
			p++;
			if (p > Math.sqrt(Long.MAX_VALUE)) {
				System.out.println("Reached " + p);
				break;
			}
		}
	}

	private synchronized boolean isPrime(long k) {
		for (long i = 2; i <= Math.sqrt(k); i++)
			if (k % i == 0)
				return false;
		return true;
	}

	private boolean divisibleByPrime(long k) throws NumberTooLargeException {
		if (k == 1)
			return false;
		Long tmp = (long) 0;
		for (int index = 0; index < primes.size(); index++) {
			Long i = primes.get(index);
			if (i > Math.sqrt(k) || i == k)
				return true;
			if (k % i == 0) {
				return false;
			}
			tmp = i;
		}
		if (tmp < Math.sqrt(k)) {
			throw new NumberTooLargeException(
					"Number is too big!  Try again later.");
		}
		return true;
	}

}
