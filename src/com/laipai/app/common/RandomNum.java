package com.laipai.app.common;

import java.util.Random;

import org.springframework.beans.factory.support.ManagedArray;

public class RandomNum {

	public static String genRandomNum(int pwd_len) {

		final int maxNum = 10;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			pwd.append(str[i]);
			count++;
		}
		return pwd.toString();
	}

	public static void main(String[] args) {
		// for(int i=0;i<100;i++){
		// System.out.println(genRandomNum(9));
		// }
		String s = getRandomNum();
		System.out.println(s);
		Integer.parseInt(s);
	}

	public static String getRandomNum() {
		StringBuffer bd = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < 9; i++) {
			int item = r.nextInt(9);
			if (i == 0 && item == 0) {
				item = 8;
			}
			bd.append(item);
		}

		return bd.toString();
	}
}
