package kr.co.ikosmo.mvc.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ikosmo.mvc.dao.TvoDao;
import kr.co.ikosmo.mvc.vo.TBoardVO;
//������� form���� �Է¹��� �����͸� Dao�� ����ؼ� ���̺� �ڷḦ �Է� �ϱ� ���� �� 
@Controller
public class TBoardController {
	
	// Dao�� ���Թޱ� : tvoDao �� Ŭ���� �̸��� �����ϰ� �ϸ� byName
	@Autowired
	private TvoDao tvoDao;
	
	private static final int BUFFER_SIZE = 4096;
	@RequestMapping(value="/upform2")
	public String upform2() {
		return "upform2";
	}
	@RequestMapping(value="/upsave2", method = RequestMethod.POST)
	public ModelAndView save2(TBoardVO vo, HttpServletRequest request) {

		
		//�̹����� ����� ���� ��θ� ���� 
		ModelAndView mav = new ModelAndView("redirect:tvolist");//view�� �̸��� �������� ���ڰ����� ���� 
		
		HttpSession session = request.getSession();
		String r_path = session.getServletContext().getRealPath("/");
		System.out.println("Path :"+r_path);
		String img_path ="resources\\upload\\";
		System.out.println("imgPath :"+r_path);
		StringBuffer path = new StringBuffer();
		path.append(r_path).append(img_path);
		MultipartFile mfiles =vo.getMfile();
		//�̹��� �̸��� ���� 
		String oriFn = mfiles.getOriginalFilename(); // ���ε� �� �̹��� �̸�
		path.append(oriFn);
		// vo�� �̹��� �̸��� �����Ѵ�.
		vo.setImgfile(oriFn);
		System.out.println("FullPath :"+path);
		// ���� ���ε� ���� 
		File f = new File(path.toString()); // ���� �̹����� ����� ���
		try {
			mfiles.transferTo(f); // �������� transferTo�� ȣ���ؼ� �̹����� ������ҿ� ���� 
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mav.addObject("imgName",oriFn);
		//Dao�� ���� ���� 
		tvoDao.addTvo(vo);
		return mav;
	}
	// ���� �ٿ�ε� 
	@RequestMapping(value = "/fileDown")
	public void fileDown(@RequestParam("fileName") String fileName,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ServletContext context = request.getServletContext();
		String r_path = session.getServletContext().getRealPath("/");
		System.out.println("Path :"+r_path);
		String img_path ="resources\\upload\\";
		System.out.println("imgPath :"+r_path);
		StringBuffer path = new StringBuffer();
		path.append(r_path).append(img_path);
		System.out.println("path:"+path);
		path.append(fileName);
		// �� ��η� ���ϰ�ü�� ���� : �߻��ηε� ������ �����ϴ�.
		File downloadFile = new File(path.toString());
		// FileInputStream ��Ʈ������ �о�� : �̹���, ���� �� ���̳ʸ� ������ �о� ���� ���� �ڹ��� ��Ʈ�� ����
		FileInputStream fi = new FileInputStream(downloadFile);
		// ��û��ü�κ��� ����� �������� ����Ÿ��(MimeType)�� ��  ������ ��Ű��� ��� 
		String mimeType = context.getMimeType(path.toString());
		// ���� ����Ÿ�԰��� ������ �׳� ����Ʈ�� ��Ʈ������ ����
		if (mimeType == null) {
			mimeType = "application/octet-stream"; // �������� �ٿ�ε带 �����ϰڴٴ� ����Ÿ���� ����
		}
		// ������ ����Ÿ�� ����
		// setContentType("text/html; charset=euc-kr") //html�� �������� ���� �غ� 
		response.setContentType(mimeType);
		// �ٿ�ε�� ������ ���� ����
		response.setContentLength((int) downloadFile.length());
		// �ٿ�ε� Type�� ������
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
		// ���� �ٿ�ε� Ÿ���� ������ �ش��� �����ؼ� �������� �����ϸ� �������� �ش� â�� �°� �ٿ�ε� view
		response.setHeader(headerKey, headerValue);
		// �������κ��� ��Ʈ���� ����
		OutputStream outStream = response.getOutputStream();
		// ���۸� ������ ������ ���� ����
		byte[] buffer = new byte[BUFFER_SIZE];
		// ���� �������� ������ , �ڿ� ������ ��
		int bytesRead = -1;
		while ((bytesRead = fi.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		fi.close();
		outStream.close();
	}
	@RequestMapping(value="/tvolist")
	public ModelAndView tvoList(@RequestHeader("User-Agent") String userAgent) {
		System.out.println("user-Agent :"+userAgent);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tlist");
		List<TBoardVO> list = tvoDao.listTvo();// dao�κ��� select���
		mav.addObject("tlist", list);
		return mav;
	}
	@RequestMapping(value="/pwdform")
	public ModelAndView pwdForm(int num) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("chkpwd");
		mav.addObject("num", num);
		return mav;
	}
	@RequestMapping(value="/tvoDetail")
	public ModelAndView tvoDetail(int num,@RequestParam(
			value = "pwd", required = true) String pwd) {
		ModelAndView mav = new ModelAndView();
		String dbpwd = tvoDao.pwdChk(num);//��й�ȣ ��
		if(pwd.equals(dbpwd)) {
			mav.setViewName("tdetail");
			TBoardVO vo = tvoDao.detailTvo(num);//��������
			mav.addObject("vo", vo);
		}else {
			mav.setViewName("chkpwderror");
		}
		return mav;
	}
	
	
	
	
	
	
	
	
	
}
