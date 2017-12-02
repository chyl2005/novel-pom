package com.github.novel.common;

import java.util.Collection;
import java.util.Map;

/** 
 *   <B>说       明</B>: 断言工具
 * <p>此类用于工具类或其他类中的参数验证等。
 *   
 * <p>注：此类只检验参数合法性，并不保证参数合理性，具体的业务逻辑性验证请在具体项目
 * 中添加相应逻辑验证工具类。 
 *
 * @author  作  者  名：WuHong<br/>
 *		   E-mail ：wuhong@vrvmail.com.cn
 
 * @version 版   本  号：V1.0<br/>
 *          创建时间：2013-6-3 下午02:58:35 
 */
public final class Assert{
	
	/**
	 * 断言对象object不为空
	 * @param object 要判断的对象
	 * @throws IllegalArgumentException 如果object为null
	 */
	public static void notNull(Object object){
		notNull(object, "参数不能为空!");
	}

	/**
	 * 断言对象object不为空
	 * @param object 要判断的对象
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果object为null
	 */
	public static void notNull(Object object, String message){
		if(object == null){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个collection是否为空
	 * @param collection 要判断的collection
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果collection为null或者collection中不包含任何元素
	 */
	@SuppressWarnings("rawtypes")
	public static void notEmpty(Collection collection, String message){
		if(collection == null || collection.isEmpty()){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个map是否为空
	 * @param map 要判断的map
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果map为null或者map中不包含任何映射
	 */
	@SuppressWarnings("rawtypes")
	public static void notEmpty(Map map, String message){
		if(map == null || map.isEmpty()){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个字符串是否有内容
	 * @param text 要判断的字符串
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果text没有长度
	 */
	public static void hasLength(String text,String message){
		if(text == null || text.length() == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个字符串是否有内容，且内容不全是空白字符
	 * @param text 要判断的字符串
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果text为空或text中全部为空白字符
	 */
	public static void isNotBlank(String text,String message){
		if(text == null || text.trim().length() == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个数组是否为空
	 * @param array 要判断的数组
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果array为空或者array中不包含任何元素
	 */
	public static <T> void notEmpty(T[] array, String message){
		if(array == null || array.length == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个int数组是否为空
	 * @param array 要判断的数组
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果array为空或者array中不包含任何元素
	 */
	public static void notEmpty(int[] array, String message){
		if(array == null || array.length == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个long数组是否为空
	 * @param array 要判断的数组
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果array为空或者array中不包含任何元素
	 */
	public static void notEmpty(long[] array, String message){
		if(array == null || array.length == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个float数组是否为空
	 * @param array 要判断的数组
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果array为空或者array中不包含任何元素
	 */
	public static void notEmpty(float[] array, String message){
		if(array == null || array.length == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个double数组是否为空
	 * @param array 要判断的数组
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果array为空或者array中不包含任何元素
	 */
	public static void notEmpty(double[] array, String message){
		if(array == null || array.length == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个short数组是否为空
	 * @param array 要判断的数组
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果array为空或者array中不包含任何元素
	 */
	public static void notEmpty(short[] array, String message){
		if(array == null || array.length == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个char数组是否为空
	 * @param array 要判断的数组
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果array为空或者array中不包含任何元素
	 */
	public static void notEmpty(char[] array, String message){
		if(array == null || array.length == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个byte数组是否为空
	 * @param array 要判断的数组
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果array为空或者array中不包含任何元素
	 */
	public static void notEmpty(byte[] array, String message){
		if(array == null || array.length == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言一个boolean数组是否为空
	 * @param array 要判断的数组
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果array为空或者array中不包含任何元素
	 */
	public static void notEmpty(boolean[] array, String message){
		if(array == null || array.length == 0){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言条件为真
	 * 
	 * @param condition 要判断的条件
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果condition为false
	 * 
	 */
	public static void isTrue(boolean condition , String message){
		if(!condition){
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 断言条件不为真
	 * 
	 * @param condition 要判断的条件
	 * @param message 如果断言失败,产生的异常消息
	 * @throws IllegalArgumentException 如果condition为true
	 * 
	 */
	public static void isNotTrue(boolean condition , String message){
		if(condition){
			throw new IllegalArgumentException(message);
		}
	}
	
	private Assert(){}
	
}
 