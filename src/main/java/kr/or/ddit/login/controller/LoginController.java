package kr.or.ddit.login.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.encrypt.kisa.sha256.KISA_SHA256;
import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.service.IUserService;

@Controller
public class LoginController {

	@Resource(name = "userService")
	private IUserService userService;
	
	
	@Resource(name = "boardService")
	IBoardService boardService;

	/**
	 * Method : loginView 작성자 : PC24 변경이력 :
	 * 
	 * @param session
	 * @return Method 설명 : 사용자 로그인 화면 요청
	 */
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String loginView(HttpSession session) {
		if (session.getAttribute("USER_INFO") != null) {
			return "tiles.main"; // /WEB-INF/views/main.jsp"
		} else {
			return "login/login"; // /login/login.jsp
		}
	}
	
	
	/**
	 * Method : loginProcess 작성자 : PC24 변경이력 :
	 * 
	 * @return Method 설명 : 사용자 로그인 요청 처리
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String loginProcess(String userId, String password, String rememberme, 
								HttpServletResponse response, HttpSession session, HttpServletRequest request) {
		
		String encryptPassword = KISA_SHA256.encrypt(password);
		UserVo userVo = userService.getUser(userId);
		if (userVo != null && encryptPassword.equals(userVo.getPass())) {
			remembermeCookie(userId, rememberme, response);

			session.setAttribute("USER_INFO", userVo);
			
			ServletContext ctx = request.getServletContext(); 
			
			ctx.setAttribute("boardList", boardService.getYBoard());
			
			String url = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=치매";
			   Document doc = null;
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			Elements imgs = doc.select("#main_pack .type01 dl");
			   
			String src = imgs.outerHtml();
			System.out.println(src);
			session.setAttribute("news", src);   

			return "tiles.main";

		} else {

			return "login/login";
		}

	}

	/**
	* Method : remembermeCookie
	* 작성자 : PC24
	* 변경이력 :
	* @param userId
	* @param rememberme
	* @param response
	* Method 설명 : rememberme 쿠키를 응답으로 생성
	*/
	private void remembermeCookie(String userId, String rememberme, HttpServletResponse response) {
		int cookieMaxAge = 0;
		if (rememberme != null) {
			cookieMaxAge = 60 * 60 * 24 * 30;
		}
		Cookie userIdCookie = new Cookie("userId", userId);
		userIdCookie.setMaxAge(cookieMaxAge);

		Cookie rememberMeCookie = new Cookie("rememberme", "true");
		rememberMeCookie.setMaxAge(cookieMaxAge);

		response.addCookie(userIdCookie);
		response.addCookie(rememberMeCookie);
		
	}
	
	@RequestMapping(path = "/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "login/login";
	}
	
	
}
