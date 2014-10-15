package us.jagels.PrimeServer.BigInteger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import us.jagels.PrimeServer.NumberTooLargeException;

public class PrimeFiller extends Thread {

	private List<BigInteger> primes;
	private BigInteger upper;

	public PrimeFiller(List<BigInteger> seed, BigInteger maxValue) {
		this.primes = seed;
		this.upper = maxValue;
	}

	@Override
	public void run() {
		BigInteger p = new BigInteger("3");
		for (BigInteger i = BigInteger.ZERO; i.compareTo(upper) == -1; i = i
				.add(BigInteger.ONE)) {
			boolean isPrime = false;
			try {
				isPrime = divisibleByPrime(p);
			} catch (NumberTooLargeException e) {
				e.printStackTrace();
			}
			if (isPrime) {
				primes.add(p);
			}
			p = p.add(BigInteger.ONE);
			/*
			 * if (p > Math.sqrt(Long.MAX_VALUE)) {
			 * System.out.println("Reached " + p); break; }
			 */
		}
	}

	private boolean divisibleByPrime(BigInteger k)
			throws NumberTooLargeException {
		if (k.compareTo(BigInteger.ONE) == 0)
			return false;
		BigInteger tmp = new BigInteger("0");
		for (int index = 0; index < primes.size(); index++) {
			BigInteger i = primes.get(index);
			if (i.compareTo(sqrt(k)) == 1 || i == k)
				return true;
			if (k.mod(i) == BigInteger.ZERO) {
				return false;
			}
			tmp = i;
		}
		if (tmp.compareTo(sqrt(k)) == -1) {
			throw new NumberTooLargeException(
					"Number is too big!  Try again later.");
		}
		return true;
	}

	public static BigDecimal sqrt(BigDecimal value) {
		BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
		return x.add(new BigDecimal(value.subtract(x.multiply(x)).doubleValue()
				/ (x.doubleValue() * 2.0)));
	}

	public static BigInteger sqrt(BigInteger value) {
		return sqrt(new BigDecimal(value.toString())).toBigInteger();
	}
}
