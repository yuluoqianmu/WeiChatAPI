package com.laipai.app.common;

public class WebRootPath {

	private static String path;
	private static String contextPath;

	public static String getPath() {
		return path;
	}

	public static void setPath(String p) {
		if (path == null) {
			path = p;
		}
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static void setContextPath(String contextPath) {
		if (contextPath == null) {
			WebRootPath.contextPath = contextPath;
		}
	}

}
