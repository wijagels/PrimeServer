package us.jagels.PrimeServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Primes extends Thread {

	public Socket listener = null;
	private static List<Long> primes;
	private static PrimeFiller pf;

	public Primes(Socket clientSocket, List<Long> p) {
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
		primes = Collections.synchronizedList(new ArrayList<Long>());
		primes.add((long) 2);
		//pf = new PrimeFiller(primes, (int) Math.sqrt(Long.MAX_VALUE));
		pf = new PrimeFiller(primes, Integer.MAX_VALUE);
		pf.setPriority(MIN_PRIORITY);
		pf.start();
		boolean running = true;
		while (running) {
			Socket clientSocket = serverSocket.accept();
			new Primes(clientSocket, primes).start();
		}
		serverSocket.close();
	}

	private boolean divisibleByPrime(long k) throws NumberTooLargeException {
		if (k == 1)
			return false;
		Long tmp = (long) 0;
		System.out.println(primes.size() + "'th prime is " + primes.get(primes.size()-1));
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
		long k;
		while ((s = in.readLine()) != null) {
			try {
				k = Long.parseLong(s);
				out.println(divisibleByPrime(k));
			} catch (NumberFormatException e) {
				out.println("Number Format Exception, use a proper long!");
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
