package com.github.novel.common;



public class HexUtils {

	/**
	 * 用于构建十六进制的数据
	 */
	private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	/**
	 * 将一个字节数组转化成十六进制字符的字符串
	 * 
	 * @param data 要转化的字节数组
	 * @return
	 *      包含十六进制字符的字符串
	 * @throws IllegalArgumentException 如果data为null。
	 */
	public static String toHexString(byte[] data){
		Assert.notNull(data,"要转化为字符串(十六进制字符)的字节数组不能为空!");
		return new String(toHex(data));
	}
	
	/**
	 * 将一个字节数组转化成十六进制字符数组
	 * 
	 * @param data 要转化的字节数组
	 * @return
	 *      包含十六进制字符的字符数组
	 */
	private static char[] toHex(byte[] data) {
		//参考commons codec Hex
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }
	
	private HexUtils() {}
	
}
 