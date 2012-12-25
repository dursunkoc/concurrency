/**
 * 
 */
package com.aric.samples.concurrency.coordination;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TTDKOC
 * 
 */
public class ConcurrentTotalFileSize {
	public class SubDirectoriesAndSize {
		final long size;
		final List<File> files;

		/**
		 * @param size
		 * @param files
		 */
		public SubDirectoriesAndSize(final long size, final List<File> files) {
			this.size = size;
			this.files = files;
		}

	}

	/**
	 * @param file
	 * @return
	 */
	public SubDirectoriesAndSize getTotalAndSubDirs(final File file) {
		long total = 0;
		final List<File> subDirectories = new ArrayList<File>();
		if (file.isDirectory()) {
			final File[] children = file.listFiles();
			if (children != null) {
				for (final File child : children) {
					if (child.isFile())
						total += child.length();
					else
						subDirectories.add(child);
				}
			}
		}
		return new SubDirectoriesAndSize(total, subDirectories);
	}

}
