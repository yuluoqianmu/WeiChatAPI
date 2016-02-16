package com.laipai.app.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 请求HTTP数据
 * 
 * @author ts
 * 
 */
public class HttpDataTools {

	private HttpDataTools() {

	}

	/**
	 * 以流的方式发送and读取远程URL内容，并返回 json 对像
	 * 
	 * @param values
	 *            要发送的对像
	 * @return
	 * @throws Exception
	 */
	// public static JSONObject sendData(String values) throws Exception {
	// return sendData(Var.REMOTE_DATA_URL, Var.ENCODE, values);
	// }
	/**
	 * 发送and读取远程URL内容 ,并返回 json对像
	 * 
	 * @param values
	 *            要发送的对像
	 * @return
	 * @throws Exception
	 */
	// public static JSONObject sendDataPost(int requestId, String values)
	// throws Exception {
	// return sendDataPost(Var.REMOTE_DATA_URL + requestId, Var.ENCODE, values);
	// }
	/**
	 * 发送and读取远程URL内容 ,并返回 json对像
	 * 
	 * @param strURL
	 * @param encode
	 * @param values
	 *            要发送的对像
	 * @return
	 * @throws Exception
	 */
	public static JSONObject sendDataPost(String strURL, String encode, String values) throws Exception {
		JSONObject json = null;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		ByteArrayOutputStream baos = null;
		InputStream in = null;
		InputStreamReader inReader = null;
		BufferedReader reader = null;
		try {

			URL url = new URL(strURL);
			conn = (HttpURLConnection) url.openConnection();
			// conn.setRequestProperty("Charset", encode);
			// conn.setRequestProperty("Content-Length",
			// String.valueOf(values.getBytes().length));
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// application/octet-stream
			// application/x-www-form-urlencoded
			conn.setConnectTimeout(10000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.connect();

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(values);
			dos.flush();
			dos.close();

			String encoding = conn.getContentEncoding();

			in = conn.getInputStream();

			int len = -1;
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			byte[] data = baos.toByteArray();

			String value = null;
			if (encoding != null) {
				value = new String(data, encoding);
			} else {
				value = new String(data, encode);

			}
			if (Global.DEBUG) {
				// System.out.println(value);
			}
			json = JSONObject.fromObject(value); // JSONTools.string2JSONObject(value);

		} catch (Exception e) {
			throw e;
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			if (inReader != null) {
				try {
					inReader.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (conn != null) {
				conn.disconnect();
			}

		}

		return json;
	}

	/**
	 * 以流的方式发送and读取远程URL内容 ,并返回 json对像
	 * 
	 * @param strURL
	 * @param encode
	 * @param values
	 *            要发送的对像
	 * @return
	 * @throws Exception
	 */
	public static JSONObject sendData(String strURL, String encode, String values) throws Exception {
		JSONObject json = null;
		if (values == null) return json;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		ByteArrayOutputStream baos = null;
		InputStream in = null;
		InputStreamReader inReader = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(strURL);
			conn = (HttpURLConnection) url.openConnection();
			if (encode != null) conn.setRequestProperty("Charset", encode);
			conn.setRequestProperty("Content-Length", String.valueOf(values.getBytes().length));
			conn.setRequestProperty("Content-Type", "application/octet-stream");// application/octet-stream
			// application/x-www-form-urlencoded
			conn.setConnectTimeout(10000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.connect();

			if (Global.DEBUG) {
				System.out.println(values);
			}

			dos = new DataOutputStream(conn.getOutputStream());
			if (encode != null) {
				dos.write(values.getBytes(encode));
			} else {
				dos.write(values.getBytes());
			}
			dos.flush();
			dos.close();

			String encoding = conn.getContentEncoding();

			in = conn.getInputStream();

			int len = -1;
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			byte[] data = baos.toByteArray();

			String value = null;
			// if (encoding != null) {
			// value = new String(data,encoding);
			// } else {
			// value = new String(data);
			//
			// }

			if (encode != null) {
				value = new String(data, encode);
			} else {
				value = new String(data);
			}
			// value = new String(data,"utf-8");
			if (Global.DEBUG) {
				System.out.println(value);
			}
			json = JSONObject.fromObject(value);

		} catch (Exception e) {
			throw e;
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			if (inReader != null) {
				try {
					inReader.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (conn != null) {
				conn.disconnect();
			}

		}

		return json;
	}

	public static String sendDataReturnString(String strURL, String encode, String values) throws Exception {
		String result = null;
		if (values == null) return result;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		ByteArrayOutputStream baos = null;
		InputStream in = null;
		InputStreamReader inReader = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(strURL);
			conn = (HttpURLConnection) url.openConnection();
			if (encode != null) conn.setRequestProperty("Charset", encode);
			conn.setRequestProperty("Content-Length", String.valueOf(values.getBytes().length));
			conn.setRequestProperty("Content-Type", "application/octet-stream");// application/octet-stream
			// application/x-www-form-urlencoded
			conn.setConnectTimeout(10000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.connect();

			if (Global.DEBUG) {
				System.out.println(values);
			}

			dos = new DataOutputStream(conn.getOutputStream());
			if (encode != null) {
				dos.write(values.getBytes(encode));
			} else {
				dos.write(values.getBytes());
			}
			dos.flush();
			dos.close();

			String encoding = conn.getContentEncoding();

			in = conn.getInputStream();

			int len = -1;
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			byte[] data = baos.toByteArray();

			String value = null;
			// if (encoding != null) {
			// value = new String(data,encoding);
			// } else {
			// value = new String(data);
			//
			// }

			if (encode != null) {
				result = new String(data, encode);
			} else {
				result = new String(data);
			}
			// value = new String(data,"utf-8");
			if (Global.DEBUG) {
				System.out.println(result);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			if (inReader != null) {
				try {
					inReader.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (conn != null) {
				conn.disconnect();
			}

		}

		return result;
	}

	public static JSONObject sendFileData(String url, File file) {

		JSONObject result = null;

		if (!file.exists() || !file.isFile()) {
			return null;
		}

		HttpURLConnection con = null;
		BufferedReader reader = null;
		OutputStream out = null;
		FileInputStream fis = null;
		DataInputStream in = null;
		try {

			URL urlObj = new URL(url);
			// 连接
			con = (HttpURLConnection) urlObj.openConnection();

			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);

			// 设置请求头信息
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", Global.DATA_ENCODE);

			// 设置边界
			String boundary = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			// 请求正文信息

			// 第一部分：
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");

			byte[] head = sb.toString().getBytes(Global.DATA_ENCODE);

			// 获得输出流
			out = new DataOutputStream(con.getOutputStream());
			// 输出表头
			out.write(head);

			// 文件正文
			fis = new FileInputStream(file);
			in = new DataInputStream(fis);
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}

			// 结尾部分
			byte[] foot = ("\r\n--" + boundary + "--\r\n").getBytes(Global.DATA_ENCODE);

			out.write(foot);
			out.flush();

			StringBuffer buffer = new StringBuffer();

			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (Global.DEBUG) {
				System.out.println(buffer);
			}
			result = JSONObject.fromObject(buffer.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			if (con != null) {
				con.disconnect();
			}

		}

		return result;

	}

	private static String map2JSON(Map<String, Object> values) throws UnsupportedEncodingException {
		JSONObject json = JSONObject.fromObject(values);// JSONTools.map2JSONObject(values);
		return json.toString();
	}

	private static String map2String(Map<String, Object> values, String encode) throws UnsupportedEncodingException {
		StringBuilder bd = new StringBuilder();
		Iterator<String> it = values.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object value = values.get(key);
			if (value != null) {
				bd.append(URLEncoder.encode(key, encode));
				bd.append("=");
				bd.append(URLEncoder.encode(value.toString(), encode));
				bd.append("&");
			}
		}
		return bd.toString();
	}

	/**
	 * 读取远程URL的内容
	 * 
	 * @param strURL
	 * @param urlCode
	 *            被读取页面的编码
	 * @return
	 * @throws Exception
	 */
	public static String sendData(String strURL, String urlCode) throws Exception {

		StringBuffer value = new StringBuffer();

		URLConnection conn = null;
		InputStream in = null;
		InputStreamReader inReader = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(strURL);
			conn = url.openConnection();
			conn.setConnectTimeout(6000);
			in = conn.getInputStream();

			inReader = new InputStreamReader(in, urlCode);
			reader = new BufferedReader(inReader);

			String line = reader.readLine();

			while (line != null) {
				value.append(line);
				line = reader.readLine();
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			if (inReader != null) {
				try {
					inReader.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}

		}

		return value.toString();
	}

}
