/**
 * 
 */
package test.java;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import us.jagels.PrimeServer.NumberTooLargeException;
import us.jagels.PrimeServer.PrimeFiller;
import us.jagels.PrimeServer.PrimeMath;

/**
 * @author William Jagels
 *
 */
public class AllTests {

	private final BigInteger largePrime = new BigInteger("99999999977");
	private final BigInteger largeComposite = new BigInteger("14432823441");
	private final BigInteger largePseudoPrime = new BigInteger("2152302898747");
	
	@Test
	public void checkPrimes() {
		PrimeFiller pf = initializeFiller(new BigInteger("999999"));
		List<BigInteger> primes = pf.getPrimes();
		Path path = FileSystems.getDefault().getPath("primes");
		Stream<String> s = null;
		try {
			s = Files.lines(path);
		} catch (IOException e) {
			fail("Could not read primes file");
		}
		Object[] sArray = s.toArray();
		List<BigInteger> verifiedPrimes = new ArrayList<BigInteger>();
		for (Object o : sArray) {
			verifiedPrimes.add(new BigInteger((String) o));
		}
		assertEquals("Generated primes must equal verified primes", primes,
				verifiedPrimes);
	}

	@Test
	public void checkMath() {
		PrimeFiller pf = initializeFiller(new BigInteger("999999"));
		List<BigInteger> primes = pf.getPrimes();
		try {
			assertEquals("This should be marked as prime", true,
					PrimeMath.divisibleByPrime(largePrime, primes));
			assertEquals("This should be marked as composite", false,
					PrimeMath.divisibleByPrime(largeComposite, primes));
		} catch (NumberTooLargeException e) {
			fail("Couldn't compute!");
		}
	}

	@Test
	public void checkPseudoPrime() {
		PrimeFiller pf = initializeFiller(new BigInteger("1467074"));
		List<BigInteger> primes = pf.getPrimes();
		try {
			assertEquals("This should be marked as composite", false,
					PrimeMath.divisibleByPrime(largePseudoPrime, primes));
		} catch (NumberTooLargeException e) {
			fail("Couldn't compute");
		}
	}

	private static PrimeFiller initializeFiller(BigInteger upper) {
		List<BigInteger> primes = Collections
				.synchronizedList(new ArrayList<BigInteger>());
		primes.add(new BigInteger("2"));
		PrimeFiller pf = new PrimeFiller(primes, upper);
		pf.run();
		while (pf.isAlive()) {
		}
		return pf;
	}

}