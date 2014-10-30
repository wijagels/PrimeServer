package us.jagels.PrimeServer.BigInteger;

import java.math.BigInteger;
import java.util.TreeSet;

public class GenerationThread extends Thread {
	
	private TreeSet<BigInteger> primes;
	private BigInteger upper, lower;
	
	public GenerationThread(TreeSet<BigInteger> primes, BigInteger maxValue,BigInteger minValue) {
		this.primes = primes;
		this.upper = maxValue;
		this.lower = minValue;
	}


	@Override
	public void run() {
		super.run();
	}
	
	private void generate() {
		for(BigInteger i = lower;i.compareTo(upper) < 0;i = i.add(BigInteger.ONE)) {
			
		}
	}
		
	
}
