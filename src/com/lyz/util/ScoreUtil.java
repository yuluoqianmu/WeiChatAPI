package com.lyz.util;

import java.util.Date;

public class ScoreUtil {
	/**
	 * 根据顶踩、时间、评论、浏览量综合计算评分
	 * @param createTime 实体创建时间
	 * @param startTime 起始时间，可以认为第一个实体创建的时间
	 * @param likeNum 喜欢数
	 * @param viewNum 浏览量
	 * @param commentNum 评论数
	 * @param unLikeNum 踩
	 * @return
	 */
	public static double getScore(long createTime, long startTime, int likeNum, int unLikeNum,int viewNum, int commentNum){
		
		
		Date date = new Date(createTime);
		/*获取时分秒数*/
		int hour = date.getHours();
		int min = date.getMinutes();
		int sec = date.getSeconds();
    	
//    	int secs = hour*3600+min*60+sec;
    	long t = (createTime-startTime)/1000;
    	   	
    	int likeAbility = (likeNum+viewNum+commentNum) - unLikeNum;
    	
    	//作品集的受肯定（否定）的程度
    	int order = (int) Math.log10(Math.max(Math.abs(likeAbility),1));
    	//是一个符号变量，表示对作品集的总体看法
    	int sign = (likeAbility>0?1:0);

    	t = t- startTime/1000;
    	
    	double zhScore = Math.round(sign * order + t/ 45000);
    	
    	return zhScore;
	}
	
	public static double getScore(){
		
		return ScoreUtil.getScore(System.currentTimeMillis(), BaseTypeUtil.getTime("2015-04-01 00:00:00", "yyyy-MM-dd HH:mm:ss"), 0, 0, 0, 0);
	}
	
	
	public static void main(String args[]){
		
		System.out.println(ScoreUtil.getScore(System.currentTimeMillis(), BaseTypeUtil.getTime("2015-04-01 00:00:00", "yyyy-MM-dd HH:mm:ss"), 0, 0, 0, 0));
	}
}
