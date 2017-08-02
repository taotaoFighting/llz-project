package com.pm.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CustomExceptionResolver implements HandlerExceptionResolver{

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handle, Exception ex) {

		ex.printStackTrace();
		
		CustomException customException = null;
		
		//如果抛出的是系统自定义的异常 直接【抛出
		if(ex instanceof CustomException){
			
			customException = (CustomException) ex;
		}else {
			
			//如果不是系统自定义的异常就自己写个异常格式抛出
			customException = new CustomException("内部错误");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		//modelAndView.addObject("message",customException.getMessage());
		modelAndView.setViewName("/404");
		
		return modelAndView;
	}

}
