package kr.co.ikosmo.mvc.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ikosmo.mvc.vo.TBoardVO;

@Controller
public class FormExampleController {


	@RequestMapping(value="/form1")
	public String viewform1() {
		return "form1";
	}
	//submit������ �Ķ���͸�  ���� �޼��� ����
	// request.getParameter("val1")�� �������� �ڵ� ���� ���ش�.
	@RequestMapping(value = "/ins1",method = RequestMethod.POST)
	public ModelAndView ins1(String val1) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("val1"); // viewName
		StringBuffer sb = new StringBuffer();
		sb.append("<p style='color:pink'>");
		sb.append(val1);
		sb.append("</p>");
		mav.addObject("val1", sb.toString()); //���� �� 
		return mav;
	}
	// ins2 �� form2 �߰�
	@RequestMapping(value="/form2")
	public String viewform2() {
		return "form2";
	}
	// spring interceptor�� �˾Ƽ� VO�� �Ķ���� ���� setter�� �������ش�.
	// @ModelAttribute("tvo") �� ����ϸ� mav.addObject("tvo",vo); �����ϴ�.
	@RequestMapping(value = "/ins2",method = RequestMethod.POST)
	public ModelAndView ins2(@ModelAttribute("tvo") TBoardVO vo) {
		System.out.println("Ȯ�� :"+vo.getWriter());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("val2");
		//mav.addObject("tvo",vo);
		return mav;
	}
	
	@RequestMapping(value="/upform1")
	public String upform1() {
		return "upform1";
	}
	// ���ε� ó�� 
	//1. enctype="multipart/form-data"  => commons-fileupload.jar�� ���ؼ� �������� Ŭ������ ����Ѵ�.
	//2. CommonsMultipartResolver �� ������ �ؾ� �Ѵ�. *******
	//3. �޾Ƽ� ó���� ���� MultipartFile ��ü�� ���ڷ� �޾��ش�.
	//* => HttpServletRequest, HttpServletResponse���� ���ڷ� �����ϸ� �������� �˾Ƽ� �־��ش�.
	@RequestMapping(value="/upsave1", method = RequestMethod.POST)
	public ModelAndView save1(MultipartFile mfile, HttpServletRequest request) {
		//�̹����� ����� ���� ��θ� ���� 
		ModelAndView mav = new ModelAndView("success");//view�� �̸��� �������� ���ڰ����� ���� 
		HttpSession session = request.getSession();
		String r_path = session.getServletContext().getRealPath("/");
		System.out.println("Path :"+r_path);
		String img_path ="resources\\upload\\";
		System.out.println("imgPath :"+r_path);
		StringBuffer path = new StringBuffer();
		path.append(r_path).append(img_path);
		//�̹��� �̸��� ���� 
		String oriFn = mfile.getOriginalFilename(); // ���ε� �� �̹��� �̸�
		path.append(oriFn);
		System.out.println("FullPath :"+path);
		// ���� ���ε� ���� 
		File f = new File(path.toString()); // ���� �̹����� ����� ���
		try {
			mfile.transferTo(f); // �������� transferTo�� ȣ���ؼ� �̹����� ������ҿ� ���� 
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mav.addObject("imgName",oriFn);
		return mav;
	}
	
	

}







