package kr.or.ddit.board.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.board.model.AttachmentVo;
import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.model.CalendarVo;
import kr.or.ddit.board.model.PostVo;
import kr.or.ddit.board.model.ReplyVo;
import kr.or.ddit.paging.model.PageVo;

public interface IBoardDao {
	
	/**
	* Method : getYBoard
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 : 활성화된 게시판 가져오는 메서드
	*/
	List<BoardVo> getYBoard();
	

	/**
	* Method : getAllBoard
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 :전체 게시판 가져오는 메서드
	*/
	List<BoardVo> getAllBoard();
	
	/**
	* Method : getOneBoard
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 : 게시판 아이디값을 받아서 하나의 게시판 정보를 출력
	*/
	BoardVo getOneBoard(int board_id);
	

	/**
	* Method : updateBoard
	* 작성자 : PC24
	* 변경이력 :
	* @param boardVo
	* @return
	* Method 설명 : 게시판 수정 메서드
	*/
	int updateBoard(BoardVo boardVo);
	
	/**
	* Method : insertBoard
	* 작성자 : PC24
	* 변경이력 :
	* @param boardVo
	* @return
	* Method 설명 : 게시판 추가 메서드
	*/
	int insertBoard(BoardVo boardVo);
	
	
	/**
	* Method : boardPagingList
	* 작성자 : PC24
	* 변경이력 :
	* @param pageVo
	* @return
	* Method 설명 : 게시판별 페이징 처리
	*/
	List<PostVo> postPagingList(PageVo pageVo);
	
	
	
	/**
	* Method : boardCnt
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 : 게시판별 게시글 전체수 조회
	*/
	int postCnt(int boardId);
	
	

	/**
	* Method : getOnePost
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @return
	* Method 설명 : 하나의 게시글 가져오기
	*/
	PostVo getOnePost(int post_seq);
	
	
	
	
	/**
	* Method : getReply
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @return
	* Method 설명 : 하나의 게시물의 댓글 가져오기
	*/
	List<ReplyVo> getReply(int post_seq);
	
	/**
	* Method : insertReply
	* 작성자 : PC24
	* 변경이력 :
	* @param replyVo
	* @return
	* Method 설명 : 댓글 등록
	*/
	int insertReply(ReplyVo replyVo);
	
	/**
	* Method : insertAttachment
	* 작성자 : PC24
	* 변경이력 :
	* @param attachmentVo
	* @return
	* Method 설명 : 첨부파일 등록
	*/
	int insertAttachment(AttachmentVo attachmentVo);
	
	
	/**
	* Method : seqCurrval
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 : 현재 postSeq값
	*/
	int postSeqCurrval();
	
	
	/**
	* Method : insertPost
	* 작성자 : PC24
	* 변경이력 :
	* @param postVo
	* @return
	* Method 설명 : 게시물 추가 메서드
	*/
	int insertPost(PostVo postVo);
	
	
	/**
	* Method : updatePost
	* 작성자 : PC24
	* 변경이력 :
	* @param postVo
	* @return
	* Method 설명 : 게시물 수정 메서드
	*/
	int updatePost(PostVo postVo);
	
	
	/**
	* Method : getAttachment
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @return
	* Method 설명 :게시물별 첨부파일
	*/
	List<AttachmentVo> getAttachment(int post_seq);
	
	
	/**
	* Method : getAttachmentPath
	* 작성자 : PC24
	* 변경이력 :
	* @param attachment_seq
	* @return
	* Method 설명 : 파일 경로 가져오기 
	*/
	String getAttachmentPath(int attachment_seq);
	
	/**
	* Method : deleteAttachment
	* 작성자 : PC24
	* 변경이력 :
	* @param attachment_seq
	* @return
	* Method 설명 :파일 삭제
	*/
	int deleteAttachment(int attachment_seq);
	
	/**
	* Method : deletePost
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @return
	* Method 설명 : 게시물 삭제
	*/
	int deletePost(int post_seq);
	
	/**
	* Method : insertReplyPost
	* 작성자 : PC24
	* 변경이력 :
	* @param postVo
	* @return
	* Method 설명 : 답글 등록
	*/
	int insertReplyPost(PostVo postVo);
	
	/**
	* Method : deleteReply
	* 작성자 : PC24
	* 변경이력 :
	* @param reply_seq
	* @return
	* Method 설명 : 댓글삭제
	*/
	int deleteReply(int reply_seq);
	
	
	/**
	* Method : postSearch
	* 작성자 : PC24
	* 변경이력 :
	* @param map
	* @return
	* Method 설명 : 게시글 검색
	*/
	List<PostVo> postSearch(Map<String, Object> map);
	
	int postSearchCnt(Map<String, Object> map);
	
	int insertCalendar(CalendarVo vo) ;
	List<CalendarVo> getCalendar() ;
	int updateCalendar(CalendarVo vo) ;
	
	
	int deleteCalendar(int c_id);
	
	
	
	
	
	
}
