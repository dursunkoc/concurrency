/**
 * 
 */
package com.aric.samples.concurrency;

/**
 * @author TTDKOC
 * 
 */
public class MemoryBarrier {
	private static boolean stop;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stop) {
				}
				System.out.println("Thread Completed!");
			}
		}).start();
		Thread.sleep(2000);
		stop = true;
		System.out.println("Main Completed.");
	}

}
