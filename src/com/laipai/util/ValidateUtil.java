/**
 * 
 */
package com.laipai.util;

/**
 * @author luzi
 *
 */
public class ValidateUtil {
    /**
     * 校验空字符串 null 或者 空
     * @param str
     * @return
     */
    public static boolean validateEmptyString(String str){
        
        if(str==null || "".equals(str.trim())){
            
            return false;
        }
        
        return true;
    }
    /**
     * 校验字符串长度
     * @param str
     * @param minLength
     * @param maxLength
     * @return
     */
    public static boolean validateStringLength(String str, int minLength, int maxLength){
    	
    	if(str==null){
    		return false;
    	}
    	
    	if(str.length()>=minLength && str.length()<=maxLength){
    		return true;
    	}
    	
    	return false;
    }
    /**
     * 校验整型值的范围
     * @param number
     * @param minVal
     * @param maxVal
     * @return
     */
    public static boolean validateInt(int number, int minVal, int maxVal){
        
        if(number<minVal || number>maxVal){
            
            return false;
        }
        
        return true;
    }
    
    public static boolean validateDouble(double number, int minVal, int maxVal){
        
        if(number<minVal || number>maxVal){
            
            return false;
        }
        
        return true;
    }
    
    public static boolean validateLong(long number, long minVal, long maxVal){
        
        if(number<minVal || number>maxVal){
            
            return false;
        }
        
        return true;
    }
    /**
     * 比较两个值大小
     * @param expectedMin 小
     * @param expectedMax 大
     * @return
     */
    public static boolean validateNumOrder(long expectedMin, long expectedMax){
        
        return expectedMin < expectedMax;
    }
}
