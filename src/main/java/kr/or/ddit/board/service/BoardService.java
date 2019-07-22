package kr.or.ddit.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.board.model.AttachmentVo;
import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.model.CalendarVo;
import kr.or.ddit.board.model.PostVo;
import kr.or.ddit.board.model.ReplyVo;
import kr.or.ddit.paging.model.PageVo;

@Repository
public class BoardService implements IBoardService {
	
	@Resource(name = "boardDao")
	private IBoardDao boardDao;
	
	/**
	* Method : getYBoard
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 : 활성화된 게시판 가져오는 메서드
	*/
	@Override
	public List<BoardVo> getYBoard() {
		return boardDao.getYBoard();
	}

	
	/**
	* Method : getAllBoard
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 :전체 게시판 가져오는 메서드
	*/
	@Override
	public List<BoardVo> getAllBoard() {
		return boardDao.getAllBoard();
	}
	
	/**
	* Method : getOneBoard
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 : 게시판 아이디값을 받아서 하나의 게시판 정보를 출력
	*/
	@Override
	public BoardVo getOneBoard(int board_id) {
		return boardDao.getOneBoard(board_id);
	}
	
	
	/**
	* Method : updateBoard
	* 작성자 : PC24
	* 변경이력 :
	* @param boardVo
	* @return
	* Method 설명 : 게시판 수정 메서드
	*/
	@Override
	public int updateBoard(BoardVo boardVo) {
		return boardDao.updateBoard(boardVo);
	}

	
	/**
	* Method : insertBoard
	* 작성자 : PC24
	* 변경이력 :
	* @param boardVo
	* @return
	* Method 설명 : 게시판 추가 메서드
	*/
	@Override
	public int insertBoard(BoardVo boardVo) {
		return boardDao.insertBoard(boardVo);
	}
	
	/**
	* Method : boardPagingList
	* 작성자 : PC24
	* 변경이력 :
	* @param pageVo
	* @return
	* Method 설명 : boardPaging처리
	*/
	@Override
	public Map<String, Object> postPagingList(PageVo pageVo, int boardId) {


		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("postPagingList", boardDao.postPagingList(pageVo));
		
		resultMap.put("board_id",boardId);
		int boardCnt = boardDao.postCnt(boardId);
		int paginationSize= (int) Math.ceil((double)boardCnt/pageVo.getPageSize());
		resultMap.put("paginationSize",paginationSize);
		
		return resultMap;
	}

	
	
	/**
	* Method : getOnePost
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 : 하나의 게시글 가져오기
	*/
	@Override
	public PostVo getOnePost(int post_seq) {
		return boardDao.getOnePost(post_seq);
	}

	
	/**
	* Method : getReply
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @return
	* Method 설명 : 하나의 게시물의 댓글 가져오기
	*/
	@Override
	public List<ReplyVo> getReply(int post_seq) {
		return boardDao.getReply(post_seq);
	}

	/**
	* Method : insertReply
	* 작성자 : PC24
	* 변경이력 :
	* @param replyVo
	* @return
	* Method 설명 : 댓글 등록
	*/
	@Override
	public int insertReply(ReplyVo replyVo) {
		return boardDao.insertReply(replyVo);
	}

	@Override
	public int insertAttachment(AttachmentVo attachmentVo) {
		return boardDao.insertAttachment(attachmentVo);
	}

	@Override
	public int postSeqCurrval() {
		return boardDao.postSeqCurrval();
	}

	@Override
	public int insertPost(PostVo postVo) {
		return boardDao.insertPost(postVo);
	}

	@Override
	public int updatePost(PostVo postVo) {
		return boardDao.updatePost(postVo);
	}

	@Override
	public List<AttachmentVo> getAttachment(int post_seq) {
		return boardDao.getAttachment(post_seq);
	}

	@Override
	public String getAttachmentPath(int attachment_seq) {
		return boardDao.getAttachmentPath(attachment_seq);
	}

	/**
	* Method : deleteAttachment
	* 작성자 : PC24
	* 변경이력 :
	* @param attachment_seq
	* @return
	* Method 설명 :파일 삭제
	*/
	@Override
	public int deleteAttachment(int attachment_seq) {
		return boardDao.deleteAttachment(attachment_seq);
	}

	@Override
	public int deletePost(int post_seq) {
		return boardDao.deletePost(post_seq);
	}

	@Override
	public int insertReplyPost(PostVo postVo) {
		return boardDao.insertReplyPost(postVo);
	}

	@Override
	public int deleteReply(int reply_seq) {
		return boardDao.deleteReply(reply_seq);
	}


	@Override
	public Map<String, Object> postSearch(Map<String, Object> map,PageVo pageVo ,int borad_id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("postPagingList", boardDao.postSearch(map));
		int boardCnt = boardDao.postSearchCnt(map);
		
		
		resultMap.put("board_id",borad_id);
		int paginationSize= (int) Math.ceil((double)boardCnt/pageVo.getPageSize());
		resultMap.put("paginationSize",paginationSize);
		
		return resultMap;
	}


	@Override
	public int insertCalendar(CalendarVo vo) {
		// TODO Auto-generated method stub
		return boardDao.insertCalendar(vo);
	}


	@Override
	public List<CalendarVo> getCalendar() {
		// TODO Auto-generated method stub
		return boardDao.getCalendar();
	}


	@Override
	public int updateCalendar(CalendarVo vo) {
		// TODO Auto-generated method stub
		return boardDao.updateCalendar(vo);
	}


	@Override
	public int deleteCalendar(int c_id) {
		return boardDao.deleteCalendar(c_id);
	}

	
	
	
}
