package com.laipai.message.util;


import org.json.JSONObject;

import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.XingeApp;

public class Test {
	
	public static void main(String args[]){
		/*android*/
		JSONObject json1 = XingeApp.pushTokenAndroid(2100103106, "a751797641654477eb9b067ce1102909", "hello1", "hello1", "5c5dd1fab8856ae84134ab607f5f715155837915");
		System.out.println(json1);
//		JSONObject json = XingeApp.pushTokenIos(2200095367l, "57b2f6d5917800fb7bf714eee7d43fa6", "hello", "e7dd19e291f29a0c20d61e4f77c836caf79de95dc02c99cfbca2dac7e724c245", XingeApp.IOSENV_DEV);
//		MessageIOS mess = new MessageIOS(); //$mess = new MessageIOS();
//		//完善Message消息
//		mess.setAlert("hello");
//		mess.setCategory("5");
//		XingeApp push = new XingeApp(2200095367l, "57b2f6d5917800fb7bf714eee7d43fa6");
//		JSONObject json = push.pushSingleDevice("e7dd19e291f29a0c20d61e4f77c836caf79de95dc02c99cfbca2dac7e724c245", mess,XingeApp.IOSENV_DEV);
//		System.out.println(json.toString());
	}
}
