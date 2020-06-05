package kr.co.ikosmo.mvc.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ikosmo.mvc.dao.MemberDao;
//JSON or Ŀ���� �並 ���� �� ��� ���� 
@RestController
public class IdCheckRestController {
	@Autowired
	private MemberDao memberDao;
	
	@RequestMapping(value="/idChk")
	public int idChk(String id) {
		int cnt = memberDao.memberIdchk(id);
		return cnt;
	}

}
