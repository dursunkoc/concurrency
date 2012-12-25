/**
 * 
 */
package com.aric.samples.concurrency.coordination;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.aric.samples.utils.Timer;

/**
 * @author TTDKOC
 * 
 */
public class NaivelyConcurrent {

	public static long getTotalSizeOfFilesInDir(
			final ExecutorService executorService, final File file)
			throws InterruptedException, ExecutionException, TimeoutException {
		if (file.isFile())
			return file.length();
		long total = 0;
		final File[] children = file.listFiles();
		if (children != null) {
			List<Future<Long>> partialTotalFutures = new ArrayList<Future<Long>>();
			for (final File child : children) {
				partialTotalFutures.add(executorService
						.submit(new Callable<Long>() {
							@Override
							public Long call() throws Exception {
								return getTotalSizeOfFilesInDir(
										executorService, child);
							}

						}));
			}
			for (final Future<Long> partialTotalFuture : partialTotalFutures) {
				total += partialTotalFuture.get(1000, TimeUnit.MILLISECONDS);
			}
		}
		return total;
	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		try {
			Timer timer = new Timer();
			long totalSizeOfFilesInDir = getTotalSizeOfFilesInDir(executorService, new File("C:\\Windows\\system32\\drivers"));
			long stop = timer.stop(true);
			System.out.println(totalSizeOfFilesInDir+" in "+stop+" milis");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			executorService.shutdown();
		}
	}

}
