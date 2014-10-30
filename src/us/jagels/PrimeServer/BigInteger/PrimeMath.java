package us.jagels.PrimeServer.BigInteger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.TreeSet;

import us.jagels.PrimeServer.NumberTooLargeException;

public class PrimeMath {

	public static BigDecimal sqrt(BigDecimal value) {
		BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
		return x.add(new BigDecimal(value.subtract(x.multiply(x)).doubleValue()
				/ (x.doubleValue() * 2.0)));
	}

	public static BigInteger sqrt(BigInteger value) {
		return sqrt(new BigDecimal(value.toString())).toBigInteger();
	}
	
	public static boolean divisibleByPrime(BigInteger k, List<BigInteger> primes)
			throws NumberTooLargeException {
		if (k.compareTo(BigInteger.ONE) == 0)
			return false;
		BigInteger tmp = new BigInteger("0");
		for (int index = 0; index < primes.size(); index++) {
			BigInteger i = primes.get(index);			
			if (i.compareTo(PrimeMath.sqrt(k)) == 1 || i == k)
				return true;
			if (k.mod(i) == BigInteger.ZERO) {
				return false;
			}
			tmp = i;
		}
		if (tmp.compareTo(PrimeMath.sqrt(k)) == -1) {
			throw new NumberTooLargeException(
					"Number is too big!  Try again later.");
		}
		return true;
	}
	
	public static boolean divisibleByPrime(BigInteger k, TreeSet<BigInteger> primes)
			throws NumberTooLargeException {
		if (k.compareTo(BigInteger.ONE) == 0)
			return false;
		BigInteger tmp = new BigInteger("0");
		for (BigInteger i : primes) {
			if (i.compareTo(PrimeMath.sqrt(k)) == 1 || i == k)
				return true;
			if (k.mod(i) == BigInteger.ZERO) {
				return false;
			}
			tmp = i;
		}
		if (tmp.compareTo(PrimeMath.sqrt(k)) == -1) {
			throw new NumberTooLargeException(
					"Number is too big!  Try again later.");
		}
		return true;
	}
}
