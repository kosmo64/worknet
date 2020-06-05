package kr.co.ikosmo.mvc.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ParameterDemo {
	    // params�� �Ӽ��� �Ķ���Ͱ��� �м��� �Ӽ��̴�. param1?code=A �� ��û �Ǿ�� ��
		@RequestMapping(value = "/param1", params = "code=A")
		public ModelAndView paramDemo1(String code) {
			ModelAndView m = new ModelAndView();
			m.setViewName("template/templateA");
			m.addObject("code", code);
			return m;
		}
		@RequestMapping(value = "/param1", params = "code=B")
		public ModelAndView paramDemo2(String code) {
			ModelAndView m = new ModelAndView();
			m.setViewName("template/templateB");
			m.addObject("code", code);
			return m;
		}
		// ��û �Ķ���Ͱ� test�� �ƴ� �͸� �ش���� 
		// param2?mycode=A,B,C, or test 
		@RequestMapping(value = "/param2", params = "mycode!=test")
		public ModelAndView aparamDemo3(String mycode) {
			ModelAndView m = new ModelAndView();
			try{  
				m.setViewName("template/templateC");
				String code="�⺻������ ";
				if(mycode.equals("A")) {
					code="A������ �Դϴ�.";
				}else if(mycode.equals("B")) {
					code="B������ �Դϴ�.";
				}
				m.addObject("code", code);
			} catch (Exception e) {
				m.setViewName("error");
				System.out.println("���۾ȵ�?");
			}
			return m;
		}
		
		// RequestParam(value="id") �Ķ���Ͱ��� ������ �� �ִ� ���
		// �⺻���� required=true �̱� ������ �Ķ���Ͱ� ���� ���
		// MissingServletRequestParameterException �� �߻��Ѵ�.
		// required=false : �Ķ���� ���� ���� ���ܸ� �߻����� �ʴ´�.
		// defaultValue="" �Ķ���Ͱ��� ���� ��, �⺻������ ����

		// --���� : code, @RequestParam�� ����ؼ� id �缳��
		@RequestMapping(value = "/param3")
		public ModelAndView paramDemo7(@RequestParam(
				value = "id", required = true) String code) {
			System.out.println("code:" + code);
			ModelAndView m = new ModelAndView();
			m.setViewName("msg");
			m.addObject("msg", code);
			return m;
		}
		
		@RequestMapping(value = "/param5")
		public ModelAndView paramDemo5(@RequestParam(
				value = "id", required = false, 
				defaultValue = "defaultTest") String code) {
			System.out.println("code:" + code);
			ModelAndView m = new ModelAndView();
			m.setViewName("msg");
			m.addObject("msg", code);
			return m;
		}
		
		
		
		
		
		@RequestMapping(value = "/param4")
		public ModelAndView paramDemo6(String id) {
			String code = null;
			if(id==null) {
				code = "�⺻��";
			}else {
				code= id;
			}
		
			System.out.println("code:" + code);
			ModelAndView m = new ModelAndView();
			m.setViewName("msg");
			m.addObject("msg", code);
			return m;
		}

}
