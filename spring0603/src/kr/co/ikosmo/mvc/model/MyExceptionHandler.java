package kr.co.ikosmo.mvc.model;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
// ���ܸ� ó���ϱ� ���� Advice -> AOP�� ���
@ControllerAdvice
public class MyExceptionHandler {
	// UnsatisfiedServletRequestParameterException
	@ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
	public ModelAndView paramsException(UnsatisfiedServletRequestParameterException e) {
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("emsg", e);
		return mav;
	}
	//MissingServletRequestParameterException
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView paramsException(MissingServletRequestParameterException e) {
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("emsg", e);
		return mav;
	}
	@ExceptionHandler(Exception.class)
	public ModelAndView paramsException2(Exception e) {
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("emsg", e);
		return mav;
	}
}
