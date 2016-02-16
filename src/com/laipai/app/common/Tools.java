package com.laipai.app.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

/**
 * 工具类
 * 
 * @author ts
 */
public class Tools {

	/**
	 * 对比股票属性后返回对应的颜色
	 * 
	 * @param currentArr
	 * @param yesterdayArr
	 * @param index
	 * @return
	 */
	public static String getStockInfoColor(String[] currentArr, String[] yesterdayArr, int index) {
		String color = "red";
		if (currentArr == null || yesterdayArr == null || currentArr.length != yesterdayArr.length || index >= currentArr.length) return color;

		double currentPrice = Double.parseDouble(currentArr[index]);// 当前价
		double yesterdayPrice = Double.parseDouble(yesterdayArr[index]);// 昨日收盘价

		if (currentPrice > yesterdayPrice) {
			return color;
		}
		color = "green";
		return color;
	}

	public static void settingResult(Model model, int result, String... msg) {
		if (msg != null && msg.length == 1) {
			model.addAttribute("message", msg[0]);
		}
		model.addAttribute("result", result);
	}

	/**
	 * 得到cookie 不存在返回 null
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		Cookie[] arr = request.getCookies();
		if (arr != null) {
			for (Cookie c : arr) {
				if (c.getName().equals(key)) {
					String v = c.getValue();
					try {
						return java.net.URLDecoder.decode(v, Global.DATA_ENCODE);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	public static void setCookie(HttpServletResponse response, String key, String value, int time) {
		String str = "";
		try {
			str = java.net.URLEncoder.encode(value, Global.DATA_ENCODE);
		} catch (UnsupportedEncodingException e) {
		}
		Cookie cookie = new Cookie(key, str);
		cookie.setMaxAge(time);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 删除cookie
	 * 
	 * @param response
	 * @param key
	 * @param value
	 * @param time
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		Cookie cookie = new Cookie(key, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void out(Writer out, String str) {
		try {
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把一个类的字段转成静态变量
	 * 
	 * @param o
	 * @return
	 */
	public static Map staticVarToMap(Object o) {
		Map result = new HashMap();
		Class c = o.getClass();
		Field[] fields = c.getFields();
		for (Field f : fields) {
			try {
				result.put(f.getName(), f.get(o));
			} catch (Exception e) {
			}
		}
		return result;
	}

	public static String textCut(String text, int len, String end) {
		if (text == null || text.length() <= len) return text;
		return text.substring(0, len) + end;
	}

	/**
	 * 格式化文件大小
	 * 
	 * @param size
	 * @return
	 */
	public static String formatFileSiZe(Long size) {
		if (size == null || size == 0) return "0";
		long m = 1024 * 1024;
		if (size < m) {
			Double d = new Double(size) / 1024D;
			return formatDouble(d) + "KB";
		} else {
			Double d = new Double(size) / new Double(m);
			return formatDouble(d) + "M";
		}
	}

	public static String formatDouble(Double d) {
		if (d == null) return "";
		return new java.text.DecimalFormat("#.00").format(d);
	}

	public static Integer stringToInteger(Map map, String key, Integer... arg) {
		try {
			String s = (String) map.get(key);
			if (s == null) {
				if (arg.length == 1) {
					return arg[0];
				}
				return null;
			}
			return Integer.parseInt(s);
		} catch (Exception e) {
			if (arg.length == 1) {
				return arg[0];
			}
		}
		return null;
	}

	/**
	 * 返回integer
	 * 
	 * @param s
	 * @param arg
	 *            默认值
	 * @return
	 */
	public static Integer stringToInteger(String s, Integer... arg) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			if (arg.length == 1) {
				return arg[0];
			}
		}
		return null;
	}

	/**
	 * 返回long
	 * 
	 * @param s
	 * @param arg
	 *            默认值
	 * @return
	 */
	public static Long stringToLong(String s, Long... arg) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			if (arg.length == 1) {
				return arg[0];
			}
		}
		return null;
	}

	/**
	 * 字符串检测，如果为null则返回0长度字符
	 * 
	 * @param s
	 * @param arg
	 * @return
	 */
	public static String stringFormat(String s, String... arg) {
		if (s == null) {
			if (arg != null && arg.length == 1) return arg[0];
			return "";
		}
		return s;
	}

	public static Float stringToFloat(String s, Float... arg) {
		try {
			return Float.parseFloat(s);
		} catch (Exception e) {
			if (arg.length == 1) {
				return arg[0];
			}
		}
		return null;
	}

	public static String filename(String filepath) {
		String fileName = "";
		if (filepath == "" || filepath == null) {
			return fileName;
		}
		File tempFile = new File(filepath.trim());

		fileName = tempFile.getName();
		return fileName;
	}

	/**
	 * 在编辑时对html进行编码
	 * 
	 * @param html
	 * @return
	 */
	public static String htmlEncodeByEdit(String html) {
		html = htmlDecode(html);
		html = htmlEncode(html);
		return html;
	}

	/**
	 * html编码
	 * 
	 * @param html
	 * @return
	 */
	public static String htmlEncode(String html) {
		if (html == null) return html;
		html = html.replaceAll("&", "&amp;");
		html = html.replaceAll("<", "&lt;");
		html = html.replaceAll(">", "&gt;");
		html = html.replaceAll("\"", "&quot;");
		return html;
	}

	/**
	 * velocity 页面替换html等，需要在js页面更显示内容
	 * 
	 * @param html
	 * @return
	 */
	public static String htmlEncodeByPageView(String html) {
		if (html == null) return html;
		html = html.replaceAll("\r\n", " ");
		html = html.replaceAll("\r", " ");
		html = html.replaceAll("\n", " ");
		html = html.replaceAll("\"", "&quot;");
		return html;
	}

	/**
	 * velocity 页面替换html等，需要在js页面更显示内容
	 * 
	 * @param html
	 * @return
	 */
	public static String htmlEncodeByEditor(String html) {
		if (html == null) return html;
		html = html.replaceAll("\r\n", " ");
		html = html.replaceAll("\r", " ");
		html = html.replaceAll("\n", " ");
		html = html.replaceAll("\"", "\\\\\"");
		return html;
	}

	/**
	 * 替找回车换行符为 <br>
	 * 
	 * @param str
	 * @return
	 */
	public static String contentFormat(String str) {
		str = str.replaceAll("\r\n", "<br/>");
		str = str.replaceAll("\r", "<br/>");
		str = str.replaceAll("\n", "<br/>");
		str = str.replaceAll(" ", "&nbsp;");
		return str;
	}

	/**
	 * 截取字符，替换为...
	 * 
	 * @param str
	 * @return
	 */
	public static String cutStr(String str) {
		int length = str.length();
		if (length > 18) {
			str = str.substring(0, 16) + "…";
		}
		return str;
	}

	/**
	 * 替找<br>
	 * 为回车换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String contentDeFormat(String str) {
		str = str.replaceAll("<br/>", "\r\n");
		str = str.replaceAll("&nbsp;", " ");
		return str;
	}

	/**
	 * html解码
	 * 
	 * @param html
	 * @return
	 */
	public static String htmlDecode(String html) {
		if (html == null) return html;
		html = html.replaceAll("&amp;", "&");
		html = html.replaceAll("&lt;", "<");
		html = html.replaceAll("&gt;", ">");
		html = html.replaceAll("&quot;", "\"");
		return html;
	}

	public static void printObjectValue(Object o) {
		Method[] methods = o.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith("get") && m.getReturnType() != Void.class && m.getParameterTypes().length == 0) {
				try {
					Object value = m.invoke(o);
					System.out.println(m.getName() + "=" + value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String timeFormatToString(Long time, String format) {
		if (time == null) return "";
		// 2003-02-28
		// 02:40:48yyyy-MM-dd HH:mm:ss
		// HH:mm:ss
		if (format == null) format = "yyyy-MM-dd HH:mm";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Date date = new Date(time);
			format = String.valueOf(formatter.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return format;
	}

	public static String timeFormatToString(String format) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Date date = new Date();
			return String.valueOf(formatter.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String timeFormatToString() {
		return timeFormatToString("yyyy-MM-dd HH:mm");
	}

	public static Date stringToDate(String str, String format) {
		// yyyy-MM-dd HH:mm:ss
		if (str == null || str.length() == 0) return null;
		if (format == null) format = "yyyy-MM-dd HH:mm";
		java.text.SimpleDateFormat da = new java.text.SimpleDateFormat(format);

		Date date = null;

		try {
			date = da.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Exception 以字符的方式输出
	 * 
	 * @param e
	 * @return 错误信息string
	 */
	public static String exceptionToString(Throwable e) {
		Writer writer = new StringWriter();
		PrintWriter print = new PrintWriter(writer);
		e.printStackTrace(print);
		String result = writer.toString();
		try {
			writer.close();
		} catch (IOException e1) {
		} finally {
			if (print != null) {
				print.close();
			}
		}
		return result;
	}

	public static Properties loadProperties(String path, String encode) {
		FileInputStream in = null;
		InputStreamReader inRead = null;
		Properties config = new Properties();
		try {
			in = new FileInputStream(path);
			inRead = new InputStreamReader(in, encode);
			config.load(inRead);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (inRead != null) {
				try {
					inRead.close();
				} catch (IOException e) {
				}
			}
		}
		return config;
	}

	/**
	 * 加载properties文件
	 * 
	 * @param path
	 * @return
	 */
	public static Properties loadProperties(String path) {
		Properties properties = new Properties();
		// System.out.println(Tools.class.getResource(path).getPath());
		InputStream in = Tools.class.getResourceAsStream(path);

		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return properties;
	}

	public static String readFileByStream(String path) {

		File f = new File(path);
		if (!f.exists() || !f.isFile()) {
			return null;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(f);

			int len = -1;
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			byte[] data = baos.toByteArray();

			return new String(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}

		}

		return null;

	}

	public static String readFile(String path) {

		StringBuffer sbf = new StringBuffer();

		File f = new File(path);
		if (!f.exists() || !f.isFile()) {
			return null;
		}
		FileReader fr = null;
		BufferedReader bf = null;
		try {
			fr = new FileReader(f);
			bf = new BufferedReader(fr);
			String line = bf.readLine();
			while (line != null) {
				sbf.append(line).append("\r\n");
				line = bf.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
				}
			}
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
				}
			}
		}

		return sbf.toString();

	}

	/**
	 * 读取一个指定位置的Properties文件
	 * 
	 * @param path
	 * @return
	 */
	public static Properties readProperties(String path) {
		FileInputStream in = null;
		Properties properties = new Properties();
		try {
			in = new FileInputStream(path);
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return properties;
	}

	/**
	 * 以默认的方式读一个文件
	 * 
	 * @param path
	 * @param encode
	 * @return
	 * @throws MessageException
	 */
	public static String readFile(String path, String encode) {

		StringBuffer sbf = new StringBuffer();

		File f = new File(path);
		if (!f.exists() || !f.isFile()) {
			return null;
		}
		FileInputStream in = null;
		InputStreamReader inRead = null;
		BufferedReader bf = null;
		try {

			in = new FileInputStream(f);
			inRead = new InputStreamReader(in, encode);
			bf = new BufferedReader(inRead);
			String line = bf.readLine();
			while (line != null) {
				sbf.append(line).append("\r\n");
				line = bf.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (inRead != null) {
				try {
					inRead.close();
				} catch (IOException e) {
				}
			}
			if (bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
				}
			}
		}

		return sbf.toString();

	}

	/**
	 * 写文件，如果文件不存在则创建一个
	 * 
	 * @param path
	 * @param value
	 * @param encode
	 * @return
	 * @throws MessageException
	 */
	public static void writeFile(String path, String value, String encode) {

		FileOutputStream stream = null;
		OutputStreamWriter out = null;
		try {
			stream = new FileOutputStream(path);
			out = new OutputStreamWriter(stream, encode);
			out.write(value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {

				}

			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}

		}
	}

	/**
	 * 写入文件
	 * 
	 * @param path
	 * @param in
	 * @throws Exception
	 */
	public static void writeFile(String path, InputStream in) throws Exception {
		if (in == null) return;
		BufferedInputStream input = null;
		OutputStream out = null;
		try {

			input = new BufferedInputStream(in);
			out = new FileOutputStream(new File(path));
			int len;
			byte[] cache = new byte[1024];

			while ((len = input.read(cache)) > 0) {
				out.write(cache, 0, len);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
				}

			}

			in.close();

		}
	}

	/**
	 * 
	 * @param path
	 * @param value
	 * @param append
	 *            如果为 true，则将数据写入文件末尾处，而不是写入文件开始处
	 * @return
	 */
	public static boolean writeFile(String path, String value, boolean append) {
		FileWriter writer = null;
		PrintWriter print = null;
		try {
			writer = new FileWriter(path, append);
			print = new PrintWriter(writer);
			print.write(value);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
			if (print != null) {
				print.close();
			}
		}
		return true;
	}

	public static String selectWeiXinHeadImage(String value, String size) {
		if (value == null) return "";
		int index = value.lastIndexOf("/");
		if (index != -1) {
			return value.substring(0, index) + "/" + size;
		}
		return "";
	}

	public static String suBString(String value) {
		return value.substring(0, 20);
	}

	/**
	 * 显示分页
	 * 
	 * @param pageCount
	 * @param page
	 * @param showCount
	 */
	public static String showPageNumber(int pageCount, int page, int showCount, String... arg) {
		StringBuilder bd = new StringBuilder();
		if (page > pageCount) page = 1;
		int _pageCount = (pageCount + showCount - 1) / showCount;
		int _page = (page + showCount - 1) / showCount;

		int startPage = (_page - 1) * showCount, endPage = startPage + showCount;
		// if (startPage < 1) startPage = 1;
		if (endPage > pageCount) endPage = pageCount;

		int prePage = _page - 1, nextPage = _page + 1;
		if (prePage < 1) prePage = 1;
		if (nextPage > _pageCount) nextPage = _pageCount;

		String jsMethod = arg != null && arg.length == 1 ? arg[0] : "gotoPage";

		// 计算偏移量
		if (_pageCount > 1) {
			int offsetSize = 3;
			int offset = page - startPage;
			if (offset < offsetSize) {

				if (endPage - startPage < showCount) {
					startPage = endPage - showCount;
				} else {
					startPage = startPage - offsetSize;
					endPage = endPage - offsetSize;
					if (startPage < 0) {
						startPage = 0;
					}

					if (endPage - startPage < showCount) {
						endPage = endPage + (showCount - (endPage - startPage));
					}
				}

			} else {
				offset = endPage - page;
				if (offset < offsetSize) {
					startPage = startPage + offsetSize;
					endPage = endPage + offsetSize;
					if (endPage > pageCount) {
						endPage = pageCount;
					}
					if (endPage - startPage < showCount) {
						startPage = startPage - (showCount - (endPage - startPage));
					}
				}
			}
		}

		if (startPage + 1 > 1) {
			bd.append("<li><a href=\"#\" onClick=\"" + jsMethod + "(1)\"> 1 </a></li>");
			// System.out.print(1 + "   ");
		}

		if (_page > 1) {
			bd.append("<li><a href=\"#\" onClick=\"" + jsMethod + "(" + startPage + ")\"> < </a></li>");
			// System.out.print(startPage + "....");
		}

		for (int i = startPage + 1; i <= endPage; i++) {
			String current = i == page ? " class=\"active\" " : "";
			bd.append("<li " + current + " ><a href=\"#\" onClick=\"" + jsMethod + "(" + i + ")\"> " + i + " </a></li>");
			// System.out.print(" " + i);
		}

		if (_page < _pageCount && endPage != pageCount) {
			// System.out.print("...." + (endPage + 1));
			bd.append("<li><a href=\"#\" onClick=\"" + jsMethod + "(" + (endPage + 1) + ")\"> > </a></li>");
		}

		if (_pageCount > 1 && _page < _pageCount && endPage != pageCount) {
			bd.append("<li><a href=\"#\" onClick=\"" + jsMethod + "(" + pageCount + ")\"> " + pageCount + " </a></li>");
			// System.out.print("   " + pageCount);
		}

		// System.out.println("");
		// System.out.println(_page + ",start=" + startPage + ",end=" +
		// endPage);
		return bd.toString();
	}
}
