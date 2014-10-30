package us.jagels.PrimeServer.BigInteger;

import java.io.IOException;
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
	 * Starts the server
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
				out.println(PrimeMath.divisibleByPrime(k,primes));
				System.out.print(String.format("%,d",primes.size()));
				System.out.println(String.format("'th prime is %,d", primes.get(primes.size()-1)));
			} catch (NumberTooLargeException e) {
				out.println(e.getMessage());
			} catch (NumberFormatException e) {
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
