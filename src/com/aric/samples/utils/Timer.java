package com.aric.samples.utils;

public class Timer {
	private Long startTime;

	public Timer() {
		this.startTime = System.currentTimeMillis();
	}

	public void restart() {
		this.startTime = System.currentTimeMillis();
	}

	public long stop(boolean restart) {
		long result = System.currentTimeMillis() - this.startTime;
		if (restart) {
			this.startTime = System.currentTimeMillis();
		}
		return result;
	}

}
