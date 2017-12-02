package com.github.novel.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/** 
 *   <B>说       明</B>:基于MD5算法的工具类
 *   <p>更多消息摘要算法支持参见 {@link MessageDigestUtils}
 *   
 *   @see MessageDigestUtils
 *
 * @author  作  者  名：chenjinlong<br/>
 *		   E-mail： chenjinlong@163.com
 *
 * @version 版   本  号：V1.0<br/>
 *          创建时间：2013-9-17 下午02:46:47 
 */
public class MD5Utils {

	/**
	 * 对字符串数据进行MD5消息摘要。
	 * 
	 * @param input 进行MD5摘要的数据。
	 * @return
	 *      input的MD5摘要信息。
	 * @throws IllegalArgumentException 如果参数input为null或者JDK不支持MD5算法。
	 */
	public static String getMD5(String input){
		Assert.notNull(input, "进行MD5摘要的数据不能为空!");
		return MessageDigestUtils.getMD5StringDigest(input.getBytes(SystemUtils.DEFAULT_CHARSET));
	}

	/**
	 * 对数据进行MD5消息摘要。
	 * 
	 * @param input 进行MD5摘要的数据。
	 * @return
	 *      input的MD5摘要信息。
	 * @throws IllegalArgumentException 如果参数input为null或者JDK不支持MD5算法。
	 */
	public static String getMD5(byte[] input){
		return MessageDigestUtils.getMD5StringDigest(input);
	}
	
	/**
	 * 对输入流中的数据进行MD5消息摘要。
	 * 
	 * @param inputStream 进行MD5摘要的输入流。
	 * @return
	 *      inputStream中数据的MD5摘要。
	 * @throws IllegalArgumentException 如果参数inputStream为null或者JDK不支持MD5算法。
	 * @throws IllegalStateException 如果发生I/O异常。
	 */
	public static String getMD5(InputStream inputStream){
		try {
			return MessageDigestUtils.getMD5StringDigest(inputStream);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * 对文件进行MD5消息摘要。
	 * 
	 * @param file 进行MD5摘要的文件。
	 * @return
	 *      file的MD5摘要。
	 * @throws IllegalArgumentException 如果参数file为null或file对应的文件不存在或JDK不支持MD5算法。
	 * @throws IllegalStateException 如果发生I/O异常。
	 */
	public static String getMD5(File file){
		Assert.notNull(file, "进行MD5消息摘要的文件不能空!");
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			return getMD5(fileInputStream);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private MD5Utils() {}

}
