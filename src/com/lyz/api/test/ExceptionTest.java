package com.lyz.api.test;

public class ExceptionTest {
	
	public static void test(){
		
		try {
			System.out.print(1/0);
		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("java.lang.ArithmeticException: / by zero\n\tat com.lyz.api.test.ExceptionTest.test(ExceptionTest.java:8)\n\tat com.lyz.api.test.ExceptionTest.main(ExceptionTest.java:19)");
		}
	}
	
	public static void main(String args[]){
		long startTime = System.currentTimeMillis();
		for(int i=0; i<10000; i++){
			ExceptionTest.test();
		}
		long endTime = System.currentTimeMillis();
		
		System.out.print(endTime-startTime);
	}
}
