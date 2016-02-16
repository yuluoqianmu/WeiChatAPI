package com.lyz.util;

public class SleepUtil {
	
	public static final int maxWaitTime = 1000;
	
	public static final int retryTimes = 5;

	/**
	 * sleep for a certain time, compute next time with pre time
	 * @param preInterval millisecond
	 * @return nextInterval
	 */
	public static int sleep(int preInterval){
				
		if(preInterval <=0){
			preInterval = 100;
		}else{
			if(preInterval+100 >= maxWaitTime){
				preInterval = maxWaitTime;
			}else{
				preInterval = preInterval+100;
			}
		}
		
		try {
			Thread.sleep(preInterval);
		} catch (InterruptedException e) {
			return preInterval;
		}
		
		return preInterval;
	}
	
	public static void sleepForCertainTime(long milliSeconds){		
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			
		}
	}

}
