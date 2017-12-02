package com.github.novel.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.github.novel.common.PageUtil;

public class PageFilter implements Filter {

	@Override
	public void destroy() {

	}
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request =(HttpServletRequest) arg0;
		HttpServletResponse response =(HttpServletResponse) arg1;
		 String startRow= request.getParameter("startRow");
		 if(StringUtils.isNotBlank(startRow)){
		   int	  start= Integer.parseInt(startRow);
			  PageUtil.setStartRow(start);
		 }
		 String ps= request.getParameter("pageSize");
		 if(StringUtils.isNotBlank(ps)){//请求中有传入pageSize
			int  pageSize= Integer.parseInt(ps);
			 request.getSession().setAttribute("pageSize", pageSize);
			 PageUtil.setPageSize(pageSize);
		 }else{//请求中没有传入pageSize
			Integer  pageSize =(Integer) request.getSession().getAttribute("pageSize");
			 if(pageSize==null||pageSize.intValue()==0){//当session也没有
				 request.getSession().setAttribute("pageSize", 10);
				 pageSize=10;
				 PageUtil.setPageSize(pageSize);
			 }
		 }
		 
		 arg2.doFilter(request, response);//放行
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
