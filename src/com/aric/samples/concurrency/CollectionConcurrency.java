/**
 * 
 */
package com.aric.samples.concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TTDKOC
 *
 */
public class CollectionConcurrency {
	
	private static void addToMap(Map<String,String> map) throws Throwable{
		try {
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> next = iterator.next();
				System.out.println(next.getKey()+" : "+next.getValue());
				map.put("ali", "veli");
			}
		} catch (Exception e) {
			System.out.println("================================");
			Thread.sleep(1000);
			e.printStackTrace();
			Thread.sleep(1000);
			System.out.println("================================");
		}
	}
	private static void removeFromMap(Map<String,String> map) throws Throwable{
		try {
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> next = iterator.next();
				System.out.println(next.getKey()+" : "+next.getValue());
				map.remove("dursun");
			}
		} catch (Exception e) {
			System.out.println("================================");
			Thread.sleep(1000);
			e.printStackTrace();
			Thread.sleep(1000);
			System.out.println("================================");
		}
	}

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		Map<String, String> bareMap = new HashMap<String, String>();
		bareMap.put("dursun", "koc");
		bareMap.put("elif nisa", "koc");
		bareMap.put("yasemin", "koc");
		System.out.println("Add to Map");
		System.out.println("-------------Bare Map----------");
		addToMap(bareMap);
		System.out.println("-------------Sync Map----------");
		addToMap(Collections.synchronizedMap(bareMap));
		System.out.println("-------------Concurrent Map----------");
		addToMap(new ConcurrentHashMap<String, String>(bareMap));

		System.out.println("Remove from Map");
		System.out.println("-------------Bare Map----------");
		removeFromMap(bareMap);
		System.out.println("-------------Sync Map----------");
		removeFromMap(Collections.synchronizedMap(bareMap));
		System.out.println("-------------Concurrent Map----------");
		removeFromMap(new ConcurrentHashMap<String, String>(bareMap));
	}

}
