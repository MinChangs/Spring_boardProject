package kr.or.ddit.board.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.or.ddit.board.model.AttachmentVo;
import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.model.PostVo;
import kr.or.ddit.board.model.ReplyVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.testenv.LogicTestEnv;
@RunWith(SpringJUnit4ClassRunner.class)

public class BoardServiceTest extends LogicTestEnv {
	
private static final Logger logger = LoggerFactory.getLogger(BoardServiceTest.class);	
	@Resource(name = "boardService")
	IBoardService service;
	@Test
	public void getYServiceTest() {
		/***Given***/
		

		/***When***/
		List<BoardVo> boardList = service.getYBoard();

		/***Then***/
		assertEquals(5, boardList.size());

	}
	@Test
	public void postPagingListTest() {
		/***Given***/
		
		PageVo pageVo = new PageVo();
		
		Map<String, Object> resultMap = service.postPagingList(pageVo, 1);

		List<PostVo> postPagingList = (List<PostVo>) resultMap.get("postPagingList");

		int paginationSize = (int) resultMap.get("paginationSize");
			
		/***Then***/
		assertNotNull(postPagingList);
		
	}
	
	@Test
	public void getOneBoardTest() {
		BoardVo vo = service.getOneBoard(1);
		assertNotNull(vo);
	}
	
	@Test
	public void updateBoardTest() {
		BoardVo vo = new BoardVo();
		vo.setBoard_id(1);
		vo.setUse_yn("y");
		vo.setName("xotnxn");
		vo.setUserId("brown");
		int cnt = service.updateBoard(vo);
		assertEquals(1, cnt);
	}
	@Test
	public void insertBoardTest() {
		BoardVo vo = new BoardVo();
		vo.setBoard_id(1);
		vo.setUse_yn("y");
		vo.setName("xotnxn");
		vo.setUserId("brown");
		int cnt = service.insertBoard(vo);
		assertEquals(1, cnt);
	}
	
	@Test
	public void getOnePostTest() {
		PostVo postVo = service.getOnePost(1);
		assertNotNull(postVo);
	}
	
	
	@Test
	public void getReplyTest() {
		List<ReplyVo> list= service.getReply(1);
		assertNotNull(list);
	}
	@Test
	public void insertReplyTest() {
		ReplyVo replyVo= new ReplyVo();
		replyVo.setPost_seq(1);
		replyVo.setReply_content("aaaa");
		replyVo.setReply_seq(150);
		replyVo.setUse_yn("y");
		replyVo.setUserid("brown");
		
		int cnt= service.insertReply(replyVo);
		assertEquals(1, cnt);
	}
	
	@Test
	public void insertAttachmentTest() {
		AttachmentVo attachmentVo= new AttachmentVo();
		attachmentVo.setAttachment_seq(100);
		attachmentVo.setFilename("aaa.jpg");
		attachmentVo.setPath("d:");
		attachmentVo.setPost_seq(1);
		
		int cnt= service.insertAttachment(attachmentVo);
		assertEquals(1, cnt);
	}
	
	@Test
	public void postSeqCurrvalTest() {
	
		int num= service.postSeqCurrval();
		assertEquals(42, num);
	}
	
	@Test
	public void insertPostTest() {
		PostVo postVo = new PostVo();
		postVo.setBoard_id(1);
		postVo.setContent("aaa");
		postVo.setGroup_seq(40);
		postVo.setPost_seq(50);
		postVo.setTitle("aaaa");
		postVo.setUse_yn("y");
		postVo.setLevel(1);
		postVo.setUserid("brown");
		int num= service.insertPost(postVo);
		assertEquals(1, num);
	}
	@Test
	public void updatePostTest() {
		
		PostVo postVo = new PostVo();
		
		
		postVo.setPost_seq(1);
		postVo.setContent("aaa");
		postVo.setTitle("aaaa");
		postVo.setUserid("brown");
		int num= service.updatePost(postVo);
		assertEquals(1, num);
	}
	@Test
	public void getAttachmentTest() {
		
		List<AttachmentVo>list= service.getAttachment(1);
		assertNotNull(list);
	}
	@Test
	public void getAttachmentPathTest() {
		
		String path= service.getAttachmentPath(2);
		assertEquals("d:\\upload\\2019\\06\\2aae0f1a-073b-42fe-8875-6973f9a0e570.gif", path);
	}
	
	@Test
	public void deleteAttachmentTest() {
		int cnt = service.deleteAttachment(1);
		assertEquals(1, cnt);
	}
	@Test
	public void deletePostTest() {
		int cnt = service.deletePost(1);
		assertEquals(1, cnt);
	}
	@Test
	public void insertReplyPostTest() {
		/***Given***/
		PostVo postVo= new PostVo();
	
		

		/***When***/
		postVo.setUserid("brown");
		postVo.setTitle("aaa");
		postVo.setContent("aaa");
		postVo.setBoard_id(1);
		postVo.setParent_seq(1);
		postVo.setGroup_seq(1);
		int cnt = service.insertReplyPost(postVo);

		/***Then***/
		assertEquals(1, cnt);
	}
	
	@Test
	public void deleteReplyTest() {
		int cnt = service.deleteReply(1);
		assertEquals(1, cnt);
	}
	
	@Test
	public void postSearchTest() {
		/***Given***/
		PageVo pageVo = new PageVo();
		pageVo.setPage(1);
		pageVo.setPageSize(10);
		Map<String, Object> searchMap = new HashMap<String, Object>();
		

		/***When***/
		searchMap.put("searchSelect", "title");
		searchMap.put("searchTxt", "123");
		searchMap.put("page", pageVo.getPage());
		searchMap.put("pageSize", pageVo.getPageSize());
		searchMap.put("board_id", 1);

		Map<String, Object> resultMap = service.postSearch(searchMap, pageVo, 1);
		List<PostVo> postPagingList = (List<PostVo>) resultMap.get("postPagingList");
		/***Then***/
		
		



		assertNotNull( postPagingList);
	}
}
