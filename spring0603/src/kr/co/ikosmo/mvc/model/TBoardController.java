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
//사용자의 form으로 입력받은 데이터를 Dao를 사용해서 테이블에 자료를 입력 하기 위한 모델 
@Controller
public class TBoardController {
	
	// Dao를 주입받기 : tvoDao 로 클래스 이름과 동일하게 하면 byName
	@Autowired
	private TvoDao tvoDao;
	
	private static final int BUFFER_SIZE = 4096;
	@RequestMapping(value="/upform2")
	public String upform2() {
		return "upform2";
	}
	@RequestMapping(value="/upsave2", method = RequestMethod.POST)
	public ModelAndView save2(TBoardVO vo, HttpServletRequest request) {

		
		//이미지가 저장될 절대 경로를 지정 
		ModelAndView mav = new ModelAndView("redirect:tvolist");//view의 이름을 생성자의 인자값으로 지정 
		
		HttpSession session = request.getSession();
		String r_path = session.getServletContext().getRealPath("/");
		System.out.println("Path :"+r_path);
		String img_path ="resources\\upload\\";
		System.out.println("imgPath :"+r_path);
		StringBuffer path = new StringBuffer();
		path.append(r_path).append(img_path);
		MultipartFile mfiles =vo.getMfile();
		//이미지 이름을 연결 
		String oriFn = mfiles.getOriginalFilename(); // 업로드 된 이미지 이름
		path.append(oriFn);
		// vo에 이미지 이름을 저장한다.
		vo.setImgfile(oriFn);
		System.out.println("FullPath :"+path);
		// 파일 업로드 실행 
		File f = new File(path.toString()); // 실제 이미지가 저장될 경로
		try {
			mfiles.transferTo(f); // 스프링의 transferTo를 호출해서 이미지를 저장장소에 복사 
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mav.addObject("imgName",oriFn);
		//Dao에 값을 전달 
		tvoDao.addTvo(vo);
		return mav;
	}
	// 파일 다운로드 
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
		// 그 경로로 파일객체를 생성 : 추상경로로도 생성이 가능하다.
		File downloadFile = new File(path.toString());
		// FileInputStream 스트림으로 읽어옴 : 이미지, 영상 등 바이너리 파이을 읽어 오기 위한 자바의 스트림 생성
		FileInputStream fi = new FileInputStream(downloadFile);
		// 요청객체로부터 연결될 브라우저의 마임타입(MimeType)을 얻어냄  브라우저 통신간에 약속 
		String mimeType = context.getMimeType(path.toString());
		// 만약 마임타입값이 없으면 그냥 디폴트로 스트림으로 연결
		if (mimeType == null) {
			mimeType = "application/octet-stream"; // 브라우저로 다운로드를 진행하겠다는 마임타입을 설정
		}
		// 지정된 마임타입 세팅
		// setContentType("text/html; charset=euc-kr") //html로 브라우저가 응답 준비 
		response.setContentType(mimeType);
		// 다운로드될 파일의 길이 세팅
		response.setContentLength((int) downloadFile.length());
		// 다운로드 Type을 설정함
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
		// 위의 다운로드 타입의 정보를 해더로 설정해서 브라우저로 전송하면 브라우저가 해당 창에 맞게 다운로드 view
		response.setHeader(headerKey, headerValue);
		// 브라우저로부터 스트림을 연결
		OutputStream outStream = response.getOutputStream();
		// 버퍼를 끼워서 빠르게 전달 목적
		byte[] buffer = new byte[BUFFER_SIZE];
		// 이제 브라우저로 보내고 , 자원 닫으면 끝
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
		List<TBoardVO> list = tvoDao.listTvo();// dao로부터 select결과
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
		String dbpwd = tvoDao.pwdChk(num);//비밀번호 비교
		if(pwd.equals(dbpwd)) {
			mav.setViewName("tdetail");
			TBoardVO vo = tvoDao.detailTvo(num);//상세페이지
			mav.addObject("vo", vo);
		}else {
			mav.setViewName("chkpwderror");
		}
		return mav;
	}
	
	
	
	
	
	
	
	
	
}
