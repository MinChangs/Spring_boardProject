package kr.or.ddit.board.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.board.model.AttachmentVo;
import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.model.PostVo;
import kr.or.ddit.board.model.ReplyVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.testenv.LogicTestEnv;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:kr/or/ddit/config/spring/root-context.xml",
//						"classpath:kr/or/ddit/config/spring/application-datasource.xml",
//					   "classpath:kr/or/ddit/config/spring/application-transaction.xml"})
public class BoardDaoTest extends LogicTestEnv {
	
	private static final Logger logger = LoggerFactory
			.getLogger(BoardDaoTest.class);
	@Resource(name = "boardDao")
	private IBoardDao boardDao;
	
	@Test
	public void getYBoardTest() {
		/***Given***/

		/***When***/
		List<BoardVo> boardList = boardDao.getYBoard();
		logger.debug("boardList.get(4).getBoard_id() : {} ", boardList.get(3).getBoard_id());

		/***Then***/
		assertNotNull(boardList);

	}
	
	@Test
	public void getAllBoardTest() {
		/***Given***/
		
		/***When***/
		List<BoardVo> boardList = boardDao.getAllBoard();
		
		/***Then***/
		assertNotNull(boardList);
		
	}
	
	@Test
	public void getOneBoardTest() {
		/***Given***/
		
		/***When***/
		BoardVo vo = boardDao.getOneBoard(3);
		
		logger.debug("vo.getName : {}", vo.getName());
		
		/***Then***/
		assertNotNull(vo.getName());
		
	}
	@Test
	public void updateBoardTest() {
		/***Given***/
		BoardVo vo = boardDao.getOneBoard(3);
		logger.debug("vo : {}", vo);
		
		
		
		/***When***/
		vo.setName("사내 경조사게시판");
		int updateCnt = boardDao.updateBoard(vo);
		
		/***Then***/
		assertEquals(1, updateCnt);
		
	}
	@Test
	public void insertBoardTest() {
		/***Given***/
		BoardVo vo = new BoardVo();
		
		/***When***/
		vo.setName("Test게시판2");
		vo.setUserId("brown");
		vo.setUse_yn("y");
		int cnt =boardDao.insertBoard(vo);
		
		logger.debug("vo : {}", vo);
		
		/***Then***/
		assertEquals(1, cnt);
		
	}
	
	

	
	
	@Test
	public void postPagingListTest() {
		/***Given***/
		PageVo pageVo =  new PageVo(1,10);
		
		/***When***/

		List<PostVo> list =boardDao.postPagingList(pageVo);
		
		logger.debug("list : {}", list);
		
		/***Then***/
		assertEquals(0, list.size());
		
	}
	
	
	@Test
	public void postCntTest() {
		/***Given***/
		BoardVo vo = new BoardVo();
		
		/***When***/

		int cnt =boardDao.postCnt(1);
		
		logger.debug("vo : {}", vo);
		
		/***Then***/
		assertEquals(10, cnt);
		
	}
	
	@Test
	public void getOnePostTest() {
		/***Given***/
		
		/***When***/
		
		PostVo postVo =boardDao.getOnePost(1);
		
		logger.debug("vo : {}", postVo);
		
		/***Then***/
		assertEquals(1, postVo.getPost_seq());
		
	}
	
	@Test
	public void getReplyTest() {
		/***Given***/
		
		/***When***/
		
		List<ReplyVo> postList =boardDao.getReply(1);
		
		logger.debug("vo : {}");
		
		/***Then***/
		assertNotNull(postList);
		
		
	}
	
	
	@Test
	public void insertReplyTest() {
		/***Given***/
		ReplyVo replyVo = new ReplyVo();
		/***When***/

		replyVo.setUserid("brown");
		replyVo.setReply_content("제임스 댓글");
		replyVo.setPost_seq(41);
		int cnt =boardDao.insertReply(replyVo);

		/***Then***/
		assertEquals(1, cnt);
		
	}
	@Test
	public void insertAttachmentTest() {
		/***Given***/
		AttachmentVo vo = new AttachmentVo();
		String path = "d:\\upload\\2019\\06\\2aae0f1a-073b-42fe-8875-6973f9a0e570.gif";
		/***When***/
		vo.setPost_seq(29);
		vo.setFilename("ddit.gif");
		vo.setPath(path);
		
		int cnt =boardDao.insertAttachment(vo);
		
		
		/***Then***/
		assertEquals(1, cnt);
		
	}
	
	
	@Test
	public void postSeqTest() {
		/***Given***/
		/***When***/
		
		int cnt =boardDao.postSeqCurrval();
		
		
		/***Then***/
		assertNotNull(cnt);
		
	}
	
	@Test
	public void updatePostTest(){
		/***Given***/
		PostVo postVo = new PostVo();
		postVo.setTitle("집에 가즈아");
		postVo.setContent("집가고 싶다!");
		postVo.setPost_seq(26);
		
		/***When***/
		
		int cnt = boardDao.updatePost(postVo);

		/***Then***/
		assertEquals(1, cnt);
		
	}
	@Test
	public void getAttachmentTest(){
		/***Given***/
		
		/***When***/
		
		List<AttachmentVo> list = boardDao.getAttachment(1);
		
		/***Then***/
		assertEquals(1, list.get(0).getPost_seq());
		assertEquals("ddit.gif", list.get(0).getFilename());
		
	}
	
	@Test
	public void getAttachmentPathTest(){
		/***Given***/
		
		/***When***/
		
		String path = boardDao.getAttachmentPath(1);
		
		/***Then***/
		assertNotEquals("d:\\upload\\2019\\06\\2aae0f1a-073b-42fe-8875-6973f9a0e570.gif", path);
		
	}
	
	@Test
	public void deleteAttachmentTest(){
		/***Given***/
		
		/***When***/
		
		int cnt = boardDao.deleteAttachment(5);
		
		/***Then***/
		assertEquals(1, cnt);
		
	}
	
	@Test
	public void deletePostTest(){
		/***Given***/
		
		/***When***/
		
		int cnt = boardDao.deletePost(32);
		
		/***Then***/
		assertEquals(1, cnt);
		
	}
	
	@Test
	public void insertReplyPostTest(){
		/***Given***/
		PostVo vo = new PostVo();
		
		/***When***/
		vo.setBoard_id(3);
		vo.setContent("aaaa");
		vo.setGroup_seq(6);
		vo.setParent_seq(28);
		vo.setTitle("답글 aaa");
		vo.setUserid("cony");
		
		int cnt = boardDao.insertReplyPost(vo);
		
		/***Then***/
		assertEquals(1, cnt);
		
	}
	
	
	@Test
	public void insertPostTest() {
		
		/***Given***/
		PostVo vo = new PostVo();
		
		/***When***/
		vo.setBoard_id(3);
		vo.setContent("aaaa");
		vo.setGroup_seq(16);
		vo.setTitle("aaa");
		vo.setUserid("cony");
		
		int cnt = boardDao.insertPost(vo);
		
		/***Then***/
		assertEquals(1, cnt);
	}
	
	
	@Test
	public void deleteReplyTest() {
		int cnt = boardDao.deleteReply(1);
		/***Then***/
		assertEquals(1, cnt);
		
	}



	@Test
	public void postSearchTest() {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		PageVo pageVo = new PageVo();
		searchMap.put("searchSelect", "title");
		searchMap.put("searchTxt", "안녕");
		searchMap.put("page", pageVo.getPage());
		searchMap.put("pageSize", pageVo.getPageSize());
		searchMap.put("board_id", 1);
		
		List<PostVo> list = boardDao.postSearch(searchMap);
		
		assertNotNull(list);
	}



	@Test
	public void postSearchCntTest() {
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		PageVo pageVo = new PageVo();
		searchMap.put("searchSelect", "title");
		searchMap.put("searchTxt", "안녕");
		searchMap.put("page", pageVo.getPage());
		searchMap.put("pageSize", pageVo.getPageSize());
		searchMap.put("board_id", 1);
		
		int cnt= boardDao.postSearchCnt(searchMap);
		
		assertNotNull(cnt);
		
	}
	
	
	
	
	
	

}
