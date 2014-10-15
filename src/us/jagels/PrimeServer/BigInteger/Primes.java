package us.jagels.PrimeServer.BigInteger;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import us.jagels.PrimeServer.In;
import us.jagels.PrimeServer.NumberTooLargeException;
import us.jagels.PrimeServer.Out;

public class Primes extends Thread {
	public Socket listener = null;
	private static List<BigInteger> primes;
	private static PrimeFiller pf;

	public Primes(Socket clientSocket, List<BigInteger> p) {
		listener = clientSocket;
		primes = p;
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException {
		int port = 4444;
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Started serverSocket on " + port);
		primes = Collections.synchronizedList(new ArrayList<BigInteger>());
		primes.add(new BigInteger("2"));
		// pf = new PrimeFiller(primes, (int) Math.sqrt(Long.MAX_VALUE));
		pf = new PrimeFiller(primes, new BigInteger("2000000000"));
		pf.setPriority(MIN_PRIORITY);
		pf.start();
		boolean running = true;
		while (running) {
			Socket clientSocket = serverSocket.accept();
			new Primes(clientSocket, primes).start();
		}
		serverSocket.close();
	}

	private boolean divisibleByPrime(BigInteger k)
			throws NumberTooLargeException {
		if (k.compareTo(BigInteger.ONE) == 0)
			return false;
		BigInteger tmp = new BigInteger("0");
		System.out.println(primes.size() + "'th prime is "
				+ primes.get(primes.size() - 1));
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

	@Override
	public void run() {
		handleConnection();
	}

	private void handleConnection() {
		System.out.println("Got connection from " + listener.getInetAddress()
				+ "!");
		Out out = new Out(listener);
		In in = new In(listener);
		out.println("Welcome To Prime Number Server!");
		String s;
		BigInteger k;
		while ((s = in.readLine()) != null) {
			try {
				k = new BigInteger(s);
				out.println(divisibleByPrime(k));
			} catch (NumberTooLargeException e) {
				out.println(e.getMessage());
			}
		}
		System.out.println("Connection severed");
		out.close();
		in.close();
		try {
			listener.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
