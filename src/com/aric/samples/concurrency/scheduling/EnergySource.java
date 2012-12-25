/**
 * 
 */
package com.aric.samples.concurrency.scheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author TTDKOC
 * 
 */
public class EnergySource {
	private final long MAXLEVEL = 100;
	private final AtomicLong level = new AtomicLong(MAXLEVEL);
	private static final ScheduledExecutorService replenishService = Executors
			.newScheduledThreadPool(10, new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					thread.setDaemon(false);
					return thread;
				}
			});

	private ScheduledFuture<?> replenishTask;

	private EnergySource() {
	}

	private void init() {
		replenishTask = replenishService.scheduleAtFixedRate(new Runnable() {
			public void run() {
				replenish();
			}
		}, 0, 1, TimeUnit.SECONDS);
	}
	
	public static EnergySource create(){
		final EnergySource energySource = new EnergySource();
		energySource.init();
		return energySource;
	}
	
	public long getUnitsAvaliable(){return this.level.get();}
	
	public boolean useEnergy(final long units){
		final long currentLevel = level.get();
		if(units > 0 && currentLevel >=units){
			return level.compareAndSet(currentLevel, currentLevel-units);
		}
		return false;
	}
	
	public synchronized void stopEnergySource(){
		replenishTask.cancel(false);
	}
	
	public static void shutdown(){
		replenishService.shutdown();
	}

	private void replenish() {
		if(level.get()<MAXLEVEL) level.incrementAndGet();
	}
	
	public static void main(String[] args) throws InterruptedException {
		EnergySource e = EnergySource.create();
		System.out.println("Current Level: "+e.getUnitsAvaliable());
		e.useEnergy(40);
		System.out.println("Used 40.");
		System.out.println("Current Level: "+e.getUnitsAvaliable());
		System.out.println("Sleeping...");
		Thread.sleep(10000);
		System.out.println("Current Level: "+e.getUnitsAvaliable());
		EnergySource.shutdown();
	}

}
