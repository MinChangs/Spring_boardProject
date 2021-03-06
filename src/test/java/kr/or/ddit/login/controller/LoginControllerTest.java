package kr.or.ddit.login.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.testenv.ControllerTestEnv;
import kr.or.ddit.user.model.UserVo;

public class LoginControllerTest extends ControllerTestEnv{


	/**
	* Method : loginViewNotLoginedTest
	* 작성자 : PC24
	* 변경이력 :
	* Method 설명 : 접속하지 않은 상황에서 loginView 요청 테스트
	 * @throws Exception 
	*/
	@Test
	public void loginViewNotLoginedTest() throws Exception {
		
		/***Given***/
		

		/***When***/
		MvcResult mvcResult =  mockMvc.perform(get("/login")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		String viewName = mav.getViewName();
		/***Then***/
		assertEquals("login/login",viewName);
	}
	
	
	
	/**
	* Method : loginViewLoginedTest
	* 작성자 : PC24
	* 변경이력 :
	* @throws Exception
	* Method 설명 : 로그인 한 상황에서 로그인 뷰 요청 테스트
	*/
	@Test
	public void loginViewLoginedTest() throws Exception {
		
		/***Given***/
		

		/***When***/
		MvcResult mvcResult =  mockMvc.perform(get("/login").sessionAttr("USER_INFO", new UserVo())).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		String viewName = mav.getViewName();
		/***Then***/
		assertEquals("tiles.main",viewName);
	}
	
	/**
	* Method : loginProcessSuccessTest
	* 작성자 : PC24
	* 변경이력 :
	* Method 설명 : 로그인 요청 처리 성공테스트
	 * @throws Exception 
	*/
	@Test
	public void loginProcessSuccessTest() throws Exception {
		/***Given***/
		String userId = "brown";
		String password = "brown1234";

		/***When***/
		
		MvcResult mvcResult= mockMvc.perform(post("/login").param("userId", userId).param("password", password)).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		HttpSession session= mvcResult.getRequest().getSession();
		String viewName = mav.getViewName();
		UserVo userVo = (UserVo)session.getAttribute("USER_INFO");

		/***Then***/
		assertEquals("tiles.main", viewName);
		assertEquals("브라운", userVo.getName());
	}
	
	
	
	/**
	* Method : loginProcessfailTest
	* 작성자 : PC24
	* 변경이력 :
	* @throws Exception
	* Method 설명 : 로그인 요청실팬
	*/
	@Test
	public void loginProcessfailTest() throws Exception {
		/***Given***/
		String userId = "brown";
		String password = "brown5678";

		/***When***/
		
		MvcResult mvcResult= mockMvc.perform(post("/login").param("userId", userId).param("password", password)).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		HttpSession session= mvcResult.getRequest().getSession();
		String viewName = mav.getViewName();
		UserVo userVo = (UserVo)session.getAttribute("USER_INFO");

		/***Then***/
		assertEquals("login/login", viewName);
	}
	
	@Test
	public void logoutTest() throws Exception {
		MvcResult mvcResult= mockMvc.perform(get("/logout")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		String viewName = mav.getViewName();
		assertEquals("login/login", viewName);
	}

}
