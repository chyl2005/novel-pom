package com.github.novel.common;

/**
 * 帮助线程关联线程局部变量的工具类
 * @author Administrator
 *
 */
public class PageUtil {

	private static ThreadLocal<Integer> START_ROW=new ThreadLocal<Integer>();
	private static ThreadLocal<Integer> PAGE_SIZE=new ThreadLocal<Integer>();
	
	public static void setStartRow(int startRow){
		START_ROW.set(startRow);
	}
	public static void setPageSize(int ps){
		PAGE_SIZE.set(ps);
	}
	
	public static Integer getStartRow(){
		if(START_ROW.get()!=null) {
            return START_ROW.get();
        }
		return 0;
	}
	
	public static Integer getPageSize(){
		if(PAGE_SIZE.get()!=null) {
            return PAGE_SIZE.get();
        }
		return 10;
	}
	
}
