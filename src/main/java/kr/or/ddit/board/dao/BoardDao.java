package kr.or.ddit.board.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.board.model.AttachmentVo;
import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.model.PostVo;
import kr.or.ddit.board.model.ReplyVo;
import kr.or.ddit.paging.model.PageVo;

@Repository
public class BoardDao implements IBoardDao {
	@Resource(name = "sqlSession")
	private SqlSessionTemplate sqlSession;
	

	/**
	* Method : getYBoard
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 : 활성화된 게시판 가져오는 메서드
	*/
	@Override
	public List<BoardVo> getYBoard() {
		return sqlSession.selectList("board.getYBoard");
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
		
		return sqlSession.selectList("board.getAllBoard");
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
		
		return sqlSession.selectOne("board.getOneBoard",board_id);
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
		return sqlSession.update("board.updateBoard",boardVo);
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
		return sqlSession.insert("board.insertBoard",boardVo);
	}



	/**
	* Method : postPagingList
	* 작성자 : PC24
	* 변경이력 :
	* @param pageVo
	* @return
	* Method 설명 : 게시판별 페이징처리
	*/
	@Override
	public List<PostVo> postPagingList(PageVo pageVo) {
		return sqlSession.selectList("board.postPagingList", pageVo);
	}


	/**
	* Method : postCnt
	* 작성자 : PC24
	* 변경이력 :
	* @return
	* Method 설명 : 게시판별 게시글 전체수 조회
	*/
	@Override
	public int postCnt(int boardId) {
		return sqlSession.selectOne("board.postCnt",boardId);
	}




	/**
	* Method : getOnePost
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @return
	* Method 설명 :하나의 게시글 가져오기
	*/
	@Override
	public PostVo getOnePost(int post_seq) {
		return sqlSession.selectOne("board.getOnePost",post_seq);
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
		return sqlSession.selectList("board.getReply", post_seq);
	}



	/**
	* Method : insertReply
	* 작성자 : PC24
	* 변경이력 :
	* @param replyVo
	* @return
	* Method 설명 : 댓글등록
	*/
	@Override
	public int insertReply(ReplyVo replyVo) {
		return sqlSession.insert("board.insertReply",replyVo);
	}


	
	/**
	* Method : insertAttachment
	* 작성자 : PC24
	* 변경이력 :
	* @param attachmentVo
	* @return
	* Method 설명 : 첨부파일 등록
	*/
	@Override
	public int insertAttachment(AttachmentVo attachmentVo) {
		return sqlSession.insert("board.insertAttachment",attachmentVo);
	}



	@Override
	public int postSeqCurrval() {
		return sqlSession.selectOne("board.postSeqCurrval");
	}



	/**
	* Method : insertPost
	* 작성자 : PC24
	* 변경이력 :
	* @param postVo
	* @return
	* Method 설명 : 게시물 추가 메서드
	*/
	@Override
	public int insertPost(PostVo postVo) {
		return sqlSession.insert("board.insertPost",postVo);
	}


	
	/**
	* Method : updatePost
	* 작성자 : PC24
	* 변경이력 :
	* @param postVo
	* @return
	* Method 설명 : 게시물 수정 메서드
	*/
	@Override
	public int updatePost(PostVo postVo) {
		return sqlSession.insert("board.updatePost",postVo);
	}



	/**
	* Method : getAttachment
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @return
	* Method 설명 : 게시물별 첨부파일 찾기
	*/
	@Override
	public List<AttachmentVo> getAttachment(int post_seq) {
		return sqlSession.selectList("board.getAttachment", post_seq);
	}



	/**
	* Method : getAttachmentPath
	* 작성자 : PC24
	* 변경이력 :
	* @param attachment_seq
	* @return
	* Method 설명 : 파일 경로 가져오기
	*/
	@Override
	public String getAttachmentPath(int attachment_seq) {
		return sqlSession.selectOne("board.getAttachmentPath",attachment_seq);
	}



	@Override
	public int deleteAttachment(int attachment_seq) {
		return sqlSession.update("board.deleteAttachment",attachment_seq);
	}



	@Override
	public int deletePost(int post_seq) {
		return sqlSession.update("board.deletePost",post_seq);
	}



	@Override
	public int insertReplyPost(PostVo postVo) {
		return sqlSession.insert("board.insertReplyPost",postVo);
	}



	@Override
	public int deleteReply(int reply_seq) {
		return sqlSession.update("board.deleteReply",reply_seq);
	}



	@Override
	public List<PostVo> postSearch(Map<String, Object> map) {
		return sqlSession.selectList("board.postSearch",map);
	}



	@Override
	public int postSearchCnt(Map<String, Object> map) {
		return sqlSession.selectOne("board.postSearchCnt",map);
	}


}
