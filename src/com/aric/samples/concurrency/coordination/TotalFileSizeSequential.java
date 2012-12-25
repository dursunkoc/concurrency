/**
 * 
 */
package com.aric.samples.concurrency.coordination;

import java.io.File;

import com.aric.samples.utils.Timer;

/**
 * @author TTDKOC
 * 
 */
public class TotalFileSizeSequential {
	public static long getTotalSizeOfFilesInDir(final File file) {
		if (file.isFile())
			return file.length();
		final File[] children = file.listFiles();
		long total = 0l;
		if (children != null)
			for (final File child : children) {
				total += getTotalSizeOfFilesInDir(child);
			}
		return total;
	}

	public static void main(String[] args) {
		Timer timer = new Timer();
		long totalSizeOfFilesInDir = getTotalSizeOfFilesInDir(new File("C:\\Windows\\system32"));
		long stop = timer.stop(true);
		System.out.println(totalSizeOfFilesInDir+" in "+stop+" milis");
	}

}
