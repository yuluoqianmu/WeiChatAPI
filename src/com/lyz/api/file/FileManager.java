package com.lyz.api.file;

import java.io.File;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

import com.lyz.api.config.ApiConfig;
/**
 * 负责文件写入器的管理，以及文件的切分
 * @author luyongzhao
 *
 */
public class FileManager {
	private static final Logger logger = Logger.getLogger(FileManager.class);
	
	private Map<String,FileTool> tag2File = null;
	
	private Map<String,Long> tag2TimeStamps = null;
	
	private ApiConfig config = null;
	
	private static AtomicReference<FileManager> instance = new AtomicReference<FileManager>();
	
	private FileManager(){
		tag2File = new HashMap<String, FileTool>();
		tag2TimeStamps = new HashMap<String, Long>();
		config = ApiConfig.getInstance();
		/*初始化文件写入器*/
		init();
	}
	
	public static FileManager getIntance(){
		if(instance.get() == null){
			instance.compareAndSet(null, new FileManager());
		}
		
		return instance.get();
	}
	
	private void init(){
		String tags[] = config.getConfigTags();
		for(String tag : tags){
			createFileTool(tag);
		}
	}
	/**
	 * 创建FileTool，并保存对应关系
	 * @param tag
	 */
	private FileTool createFileTool(String tag){
		FileTool tool = null;
		String[] tmp = this.createFileName(tag);
		/*创建父文件夹*/
		String fileDir = createFileDir(config.getFileDir(tag));
		/*创建文件访问工具*/
		tool = new FileTool(fileDir+"/"+tmp[0]);
		/*开启文件追加模式*/
//		tool.openWriter(true);
		tool.openOutputStream(true);
		/*保存对应关系*/
		tag2File.put(tag, tool);	
		tag2TimeStamps.put(tag, Long.parseLong(tmp[1]));
		
		return tool;
	}
	
	/**
	 * 创建文件夹，如果存在，则不创建
	 * @param fileDir
	 */
	private String createFileDir(String fileDir){
		if(fileDir==null || "".equals(fileDir.trim())){
			return null;
		}
		/*宏处理，支持动态生成目录*/
		Date now = new Date();
		int year = now.getYear()+1900;
		int month = now.getMonth()+1;
		int day = now.getDate();
		int hour = now.getHours();
		int minute = now.getMinutes();
		int seconds = now.getSeconds();
		fileDir = fileDir.replaceAll("\\{year\\}", ""+year)
				.replaceAll("\\{month\\}", getTime(month))
				.replaceAll("\\{day\\}", getTime(day))
				.replaceAll("\\{hour\\}", getTime(hour))
				.replaceAll("\\{minute\\}", getTime(minute))
				.replaceAll("\\{seconds\\}", getTime(seconds));
		
		/*创建目录*/
		try {
			new File(fileDir).mkdirs();
		} catch (Exception e) {
			logger.warn("fail to cread file directory:"+fileDir,e);
			return null;
		}
		
		return fileDir;
	}
	/**
	 * 写入日志
	 * @param log
	 * @param tag
	 */
	public void writeLog(String log, String tag){
		if(log==null || "".equals(log.trim())){
			return;
		}
		this.writeLog(log.getBytes(), 0, log.length(), tag);
	}
	/**
	 * 写日日志
	 * @param log
	 * @param offset
	 * @param length
	 * @param tag
	 */
	public void writeLog(byte[] log, int offset, int length,String tag){
		if(log==null || length<=0){
			return;
		}
		
		/*判断是否需要更换文件,不需要则继续写入*/
		FileTool tool = tag2File.get(tag);
		if(tag2TimeStamps.get(tag).longValue() < getTimeStamp(System.currentTimeMillis(), config.getSplitInterval(tag))){
			/*关闭先前的，以便切换文件*/
//			tool.closeWriter();
			tool.closeOutputStream();
			/*创建一个新的文件工具*/
			tool = createFileTool(tag);
		}
		/*写入日志*/
//		tool.writeNewLine(log);
		tool.writeNewBytes(log,offset,length);
	}
	
	/**
	 * 判断当前的tag是否被初始化
	 * @param tag
	 * @return
	 */
	public boolean isHasTag(String tag){
		return tag2File.get(tag.trim())!=null;
	}
	
	
	/**
	 * 获取一个时间戳，该时间戳是文件拆分间隔的整数倍
	 * 该值用于判断是否需要变更文件
	 * @param tag
	 * @return
	 */
	private Long getTimeStamp(long timeStamps,long splitInterval){
		return new Long(System.currentTimeMillis()/splitInterval*splitInterval);
	}
	
	
	/**
	 * 
	 * @param tag
	 * @return 出现错误，返回null
	 */
	private String[] createFileName(String tag){
		
		String unit = config.getSpliteIntervalUnit(tag).trim();
		int val = config.getSpliteIntevalVal(tag);
		String[] dates = createFormatDate(unit,val,config.getDateFormat(tag),config.getSplitInterval(tag));
		if(dates == null){
			return null;
		}
		/*组织文件名称*/
		String fileName = config.getFileStart(tag)+config.getFileSplit(tag)+dates[0]+config.getFileEndWith(tag);
		/*替换时间为文件名*/
		dates[0] = fileName;
		
		return dates;
	}
	/**
	 * 返回一个时间数组，共有两个元素，第一个为格式化的时间，第二个为时间戳
	 * @param unit
	 * @param val
	 * @param dateFormat
	 * @param splitInterval
	 * @return
	 */
	private String[] createFormatDate(String unit, int val, String dateFormat, long splitInterval){
		if(unit==null || val<0 || dateFormat==null || splitInterval<0){
			return null;
		}
		Date now = new Date();
		long timeStamp = getTimeStamp(now.getTime(), splitInterval);
		int year = now.getYear()+1900;
		int month = now.getMonth()+1;
		int day = "d".equals(unit)? now.getDate()/val*val:now.getDate();
		int hour = "h".equals(unit)?now.getHours()/val*val:now.getHours();
		int minute = "m".equals(unit)?now.getMinutes()/val*val:now.getMinutes();
		int seconds = "s".equals(unit)?now.getSeconds()/val*val:now.getSeconds();
		
		dateFormat = dateFormat.replaceAll("\\{year\\}", ""+year)
				.replaceAll("\\{month\\}", getTime(month))
				.replaceAll("\\{day\\}", getTime(day))
				.replaceAll("\\{hour\\}", getTime(hour))
				.replaceAll("\\{minute\\}", getTime(minute))
				.replaceAll("\\{seconds\\}", getTime(seconds));
		return new String[]{dateFormat,""+timeStamp};
	}
	
	private String getTime(int time){
		if(time<10){
			return "0"+time;
		}else{
			return ""+time;
		}
	}
	
	public static void main(String args[]){
//		long time = System.currentTimeMillis();
//		String out = BaseTypeUtil.getStrDateTime(time, "yyyy-MM-dd HH:mm");
//		System.out.println(out);
//		System.out.println(ServerConfig.getInstance().getSpliteIntervalUnit("vvlog"));
//		System.out.println(ServerConfig.getInstance().getSpliteIntevalVal("vvlog"));
		System.out.println(new FileManager().createFileName("vvlog"));
	}
	
	
}
