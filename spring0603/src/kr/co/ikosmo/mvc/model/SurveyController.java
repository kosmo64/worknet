package kr.co.ikosmo.mvc.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.ikosmo.mvc.dao.SurveyDao;
import kr.co.ikosmo.mvc.service.SurveyService;
import kr.co.ikosmo.mvc.vo.SubveyContentVO;
import kr.co.ikosmo.mvc.vo.SubveyView2VO;
import kr.co.ikosmo.mvc.vo.SurveyVO;

@Controller
public class SurveyController {
	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private SurveyDao surveyDao;
	
	@RequestMapping(value="/surveyForm")
	public String surform() {
		return "survey/surveyAddform";
	}
	@RequestMapping(value="/addsurvey",method = RequestMethod.POST)
	public ModelAndView addsurvey(SurveyVO vo,HttpServletRequest request) {
		System.out.println(vo.getSub());
		String[] surveytitle = request.getParameterValues("surveytitle");
		// parameter���� �迭�� ���۵ǰ�, �װ��� ���� ���� �����Ҷ� List �����Ѵ�.
		List<SubveyContentVO> list = new ArrayList<>();
		int i=0;
		char stype='A';//A���� 
		for(String e : surveytitle) {
			if(i < vo.getCode()) {
			SubveyContentVO sv = new SubveyContentVO();
			sv.setSurveytype(String.valueOf(stype)); //A�� ����
			System.out.println(stype);
			sv.setSurveytitle(e);
			list.add(sv);
			}else {
				break;
			}
			stype++; //A++ => B
			i++;
		}
		System.out.println("Ȯ�� :"+list.size());
		surveyService.addSurvey(vo, list);		
		ModelAndView mav = new ModelAndView("redirect:surveylist");
		return mav;
	}
	//  ������ ����Ʈ 
	@RequestMapping(value="/surveylist")
	public ModelAndView surveyList() {
		ModelAndView mav = new ModelAndView("survey/surveyList");
		List<SurveyVO> list = surveyDao.adminList();
		mav.addObject("list", list);
		return mav;
	}
	// ������ detail������ ����
	@RequestMapping(value="/surveyDetail")
	public ModelAndView surveyDetail(int num) {
		ModelAndView mav = new ModelAndView("survey/surveyDetail");
		List<SubveyView2VO> list = surveyDao.adminDetail(num);
		mav.addObject("list", list);
		return mav;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}







