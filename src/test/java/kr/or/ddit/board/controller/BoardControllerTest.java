package kr.or.ddit.board.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.testenv.ControllerTestEnv;

public class BoardControllerTest extends ControllerTestEnv {

	@Test
	public void createGetBoardTest() throws Exception {
		
		/***Given***/
		

		/***When***/
		MvcResult mvcResult =  mockMvc.perform(get("/board/createBoard")).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		String viewName = mav.getViewName();
		/***Then***/
		assertEquals("board/createBoard",viewName);
	}
	
	
//	@Test
//	public void createPostBoardTest() throws Exception {
//		
//		UserVo userVo = new UserVo();
//		userVo.setUserId("brown");
//		mockMvc.perform(post("/board/createBoard")
//				.param("boardCreate", "userTest12301")
//				.param("usecheck", "y")
//				)
//				
//				.andExpect(view().name("board/createBoard"));
//	}

}
