package com.github.novel.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.Charset;
import java.util.Map;


public class SystemUtils {
	
	/**
	 * 系统默认字符集。
	 */
	public static final Charset DEFAULT_CHARSET = Charset.forName("utf-8");
	
	//内部引用，保证初始化。
	private static String lp;
	
	static{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(2);
		PrintWriter writer = new PrintWriter(bos,true);
		writer.println();
		lp = new String(bos.toByteArray());
		writer.close();
		if(lp == null){
			lp = new String(new byte[]{'\r', '\n'});
		}
	}
	
	/**
	 * 换行符。
	 */
	public static final String LINESEPARATOR = lp;
	
	/**
	 * 获取当前JVM名称。
	 * 
	 * @return
	 *      当前JVM名称。
	 */
	public static String getJavaVMName(){
		return System.getProperty("java.vm.name");
	}
	
	/**
	 * 获取当前JVM版本。
	 * 
	 * @return
	 *      当前JVM版本。
	 */
	public static String getJavaVMVersion(){
		return System.getProperty("java.vm.version");
	}
	
	/**
	 * 获取当前JVM厂商。
	 * 
	 * @return
	 *      当前JVM厂商。
	 */
	public static String getJavaVMVendor(){
		return System.getProperty("java.vm.vendor");
	}
	
	/**
	 * 获取当前Java版本。
	 * 
	 * @return
	 *      当前Java版本。
	 */
	public static String getJavaVersion(){
		return System.getProperty("java.version");
	}
	
	/**
	 * 获取当前临时目录。
	 * 
	 * @return
	 *      系统当前临时目录。
	 */
	public static String getTempDir(){
		return System.getProperty("java.io.tmpdir");
	}
	
	/**
	 * 获取当前操作系统名称。
	 * 
	 * @return
	 *      当前操作系统名称。
	 */
	public static String getOsName(){
		return System.getProperty("os.name");
	}
	
	/**
	 * 获取当前操作系统版本。
	 * 
	 * @return
	 *      当前操作系统版本。
	 */
	public static String getOsVersion(){
		return System.getProperty("os.version");
	}
	
	/**
	 * 获取当前系统用户名称。
	 * 
	 * @return
	 *      当前系统用户名称。
	 */
	public static String getUserName(){
		return System.getProperty("user.name");
	}
	
	/**
	 * 获取当前JavaHome。
	 * 
	 * @return
	 *      当前JavaHome，如果没有返回null。
	 */
	public static String getJavaHome(){
		return System.getProperty("java.home");
	}
	
	/**
	 * 获取CPU数据模型(32位还是64位，即寻址空间大小)。
	 * 
	 * @return
	 *      CPU数据模型(32位还是64位)。
	 */
	public static String getArchDataModel(){
		return System.getProperty("sun.arch.data.model");
	}
	
	/**
	 * 获取当前语言环境。
	 * 
	 * @return
	 *      当前语言环境，如：zh。
	 */
	public static String getUserLanguage(){
		return System.getProperty("user.language");
	}
	
	/**
	 * 获取文件分隔符。
	 * 
	 * @return
	 *      文件分隔符。
	 */
	public static String getFileSeparator(){
		return System.getProperty("file.separator");
	}
	
	/**
	 * 获取处理器数量(CPU核心数量)。
	 * 
	 * @return
	 *      处理器数量。
	 */
	public static int getProcessorCount(){
		return Runtime.getRuntime().availableProcessors();
	}
	
	/**
	 * 获取当前JVM空闲内存。
	 * 
	 * @return
	 *      当前JVM空闲内存，单位(字节)。
	 */
	public static long getFreeMemory(){
		return Runtime.getRuntime().freeMemory();
	}
	
	/**
	 * 获取当前JVM最大内存
	 * 
	 * @return
	 *      当前JVM最大内存，单位(字节)。
	 */
	public static long getMaxMemory(){
		return Runtime.getRuntime().maxMemory();
	}
	
	/**
	 * 
	 * <p>获取当前JVM总内存
	 * <p>总内存可以不等于最大内存，取决于JVM的设置 (参见-Xms -Xmx等参数)，当
	 * 最小内存不等于最大内存时，内存空间可扩展。
	 * 
	 * @return
	 *      当前JVM总内存，单位(字节)。
	 */
	public static long getTotalMemory(){
		return Runtime.getRuntime().totalMemory();
	}
	
	/**
	 * 打印当前所有线程栈追踪信息。
	 */
	public static void printAllStackTrack(){
		Map<Thread, StackTraceElement[]> stMap = Thread.getAllStackTraces();
		for(Thread thread : stMap.keySet()){
			StackTraceElement[] elements = stMap.get(thread);
			System.out.println(thread);
			for(StackTraceElement element : elements){
				System.out.println("	"+element);
			}
		}
	}
	
	/**
	 * 获取当前JVM运行时间(从启动到当前的时间)。
	 * 
	 * @return
	 *      当前JVM运行时间，单位(毫秒)。
	 */
	public static long getJavaVMUptime(){
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		if(runtimeMXBean != null){
			return runtimeMXBean.getUptime();
		}
		//can't happen
		return -1;
	}
	
	/**
	 * 获取当前JVM启动时间。
	 * 
	 * @return
	 *      当前JVM启动时间，单位(毫秒)。
	 */
	public static long getJavaVMStartTime(){
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		if(runtimeMXBean != null){
			return runtimeMXBean.getStartTime();
		}
		//can't happen
		return -1;
	}

	private SystemUtils(){}
	
}
