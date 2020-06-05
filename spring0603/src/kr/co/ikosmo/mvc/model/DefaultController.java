package kr.co.ikosmo.mvc.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//@Controller ������̼��� ������ scanning ����� �𵨷� ��ϵǵ��� �����Ѵ�.
import org.springframework.web.servlet.ModelAndView;
@Controller
public class DefaultController {
	// value {} �������� ���� 
	@RequestMapping(value= {"/","/index"})
	public String indexPage() {
		// ���� �ѱ� �ʿ䰡 ���� �� String ��ȯ���� viewName
		return "main/index";
	}
	
	
	
	
	
	
	
	
	
	
	
	// �ش� ���� ������ HandlerMapping �̴�.
	// ��û��, ��û��� 
	@RequestMapping(value="/myhello",method = RequestMethod.GET)
	public ModelAndView hello() {
		//ActionForward + requestScope ��ģ ���� , forward�� �⺻�� 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("hello"); // ���� �̸��� ���� 
		mav.addObject("msg","�ȳ��ϼ��� ���� ù ������ MVC�Դϴ�."); // request��
		return mav;
	}
}
