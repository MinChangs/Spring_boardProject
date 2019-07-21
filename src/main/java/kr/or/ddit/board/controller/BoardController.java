package kr.or.ddit.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.board.model.AttachmentVo;
import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.model.PostVo;
import kr.or.ddit.board.model.ReplyVo;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.util.PartUtil;

@RequestMapping("/board")
@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Resource(name = "boardService")
	IBoardService boardService;

	/**
	 * Method : createGetBoard 작성자 : PC24 변경이력 :
	 * 
	 * @param model
	 * @return Method 설명 : 게시판 등록 화면 처리
	 */
	@RequestMapping(path = "/createBoard", method = RequestMethod.GET)
	public String createGetBoard(Model model) {
		model.addAttribute("allList", boardService.getAllBoard());
		return "tiles.createBoard";
	}

	/**
	 * Method : createPostBoard 작성자 : PC24 변경이력 :
	 * 
	 * @param boardCreate
	 * @param usecheck
	 * @param model
	 * @param request
	 * @return Method 설명 : 게시판 등록 처리
	 */
	@RequestMapping(path = "/createBoard", method = RequestMethod.POST)
	public String createPostBoard(String boardCreate, String usecheck, Model model,	HttpServletRequest request,RedirectAttributes redirectAttributes) {
		UserVo userVo = (UserVo) request.getSession().getAttribute("USER_INFO");

		BoardVo boardVo = new BoardVo();
		boardVo.setName(boardCreate);
		boardVo.setUserId(userVo.getUserId());
		boardVo.setUse_yn(usecheck);

		logger.debug("boardVo.setName(boardCreate) : {} ", boardCreate);
		logger.debug("boardVo.setName(boardCreate) : {} ", userVo.getUserId());
		logger.debug("boardVo.setName(boardCreate) : {} ", usecheck);

		List<BoardVo> boardList = boardService.getAllBoard();

		for (BoardVo vo : boardList) {
			if (vo.getName().equals(boardVo.getName())) {
				redirectAttributes.addFlashAttribute("msg", "게시판이름이 중복됩니다.");
				return "redirect:/board/createBoard";
			}
		}
		int insertCnt = boardService.insertBoard(boardVo);
		if (insertCnt == 1) {
			List<BoardVo> refresh = boardService.getYBoard();
			redirectAttributes.addFlashAttribute("msg", "게시판 추가완료.");
			request.getServletContext().setAttribute("boardList", refresh);
			return "redirect:/board/createBoard";
		}else {
			redirectAttributes.addFlashAttribute("msg", "게시판 추가실패.");
			return "redirect:/board/createBoard";
			
		}
	}

	/**
	 * Method : updateBoard 작성자 : PC24 변경이력 :
	 * 
	 * @param board
	 * @param usecheck
	 * @param boardId
	 * @param model
	 * @param request
	 * @return Method 설명 : 게시판 수정 처리
	 */
	@RequestMapping(path = "/updateBoard", method = RequestMethod.POST)
	public String updateBoard(String board, String usecheck, String boardId, Model model,HttpServletRequest request,
					RedirectAttributes redirectAttributes) {
		BoardVo boardVo = new BoardVo();
		boardVo.setName(board);
		boardVo.setUse_yn(usecheck);
		boardVo.setBoard_id(Integer.parseInt(boardId));

		int updateCnt = boardService.updateBoard(boardVo);
		if (updateCnt == 1) {
			List<BoardVo> refresh = boardService.getYBoard();
			request.getServletContext().setAttribute("boardList", refresh);
			model.addAttribute("allList", boardService.getAllBoard());
			redirectAttributes.addFlashAttribute("msg", "게시판 수정완료.");
		}
		return "redirect:/board/createBoard";
	}


	/**
	* Method : boardPagingView
	* 작성자 : PC24
	* 변경이력 :
	* @param model
	* @param board_id
	* @param pageVo
	* @return
	* Method 설명 : tiles를 이용한 페이징화면
	*/
	@RequestMapping(path = "/boardPagingView", method = RequestMethod.GET)
	public String boardPagingView(Model model, int board_id, PageVo pageVo) {
		model.addAttribute("board_id",board_id);
		BoardVo boardVo = boardService.getOneBoard(board_id);
		model.addAttribute("boardVo",boardVo);
		return "tiles.boardPagingView";
	}
	
	
	/**
	* Method : boardPagingAjax
	* 작성자 : PC24
	* 변경이력 :
	* @param board_id
	* @param pageVo
	* @param model
	* @return
	* Method 설명 : ajax를 이용한 페이징 처리
	*/
	@RequestMapping(path = "/boardPagingAjaxHtml")
	public String boardPagingAjax(int board_id, PageVo pageVo, Model model) {
		logger.debug("pageVo : {} ", pageVo);
		
		
		Map<String, Object> resultMap = boardService.postPagingList(pageVo, board_id);

		List<PostVo> postPagingList = (List<PostVo>) resultMap.get("postPagingList");

		for (PostVo vo : postPagingList) {
			vo.setTitle(vo.getTitle().replace(" ", "&nbsp;"));
		}
		int paginationSize = (int) resultMap.get("paginationSize");

		model.addAttribute("postPagingList", postPagingList);
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo",pageVo);
		model.addAttribute("board_id",board_id);
		
		return "board/boardpagingListAjaxHtml";
		
	}

	/**
	 * Method : insertGetPost 작성자 : PC24 변경이력 :
	 * 
	 * @param board_id
	 * @param model
	 * @return Method 설명 : 게시물 등록 화면
	 */
	@RequestMapping(path = "/insertPost", method = RequestMethod.GET)
	public String insertGetPost(String board_id, Model model) {
		logger.debug("등록 board_id : {}", board_id);
		model.addAttribute("board_id", board_id);
		return "tiles.insertPost";
	}

	/**
	* Method : insertpostPost
	* 작성자 : PC24
	* 변경이력 :
	* @param board_id
	* @param model
	* @param request
	* @param title
	* @param smarteditor
	* @param files
	* @return
	* Method 설명 : 게시물 등록 처리
	*/
	@RequestMapping(path = "/insertPost", method = RequestMethod.POST)
	public String insertpostPost(String board_id, Model model,	HttpServletRequest request, String title,
									String smarteditor,@RequestPart(required = false) MultipartFile[] files) {

		UserVo userVo = (UserVo) request.getSession().getAttribute("USER_INFO");
		logger.debug("등록 board_id : {}", board_id);
		logger.debug("등록 userVo : {}", userVo.getName());
		logger.debug("등록 userVo : {}", userVo.getUserId());
		
		PostVo postVo = new PostVo();
		postVo.setUserid(userVo.getUserId());
		postVo.setTitle(title);
		postVo.setContent(smarteditor);
		postVo.setBoard_id(Integer.parseInt(board_id));
		
		int cnt = boardService.insertPost(postVo);
		
		if (cnt == 1) {
			for(int i=0; i<files.length; i++) {
				if (files[i] != null && files[i].getSize() > 0) {
					
					AttachmentVo attachmentVo = new AttachmentVo();
					String path = PartUtil.getUploadPath();
					String ext = PartUtil.getExt(files[i].getOriginalFilename());
					String fileName = UUID.randomUUID().toString();
					File uploadfile = new File(path+ File.separator +fileName+ext);
					
					attachmentVo.setPath(uploadfile.getPath());
					attachmentVo.setFilename(files[i].getOriginalFilename());
					int post_seq=boardService.postSeqCurrval();
					attachmentVo.setPost_seq(post_seq);
					
					logger.debug("setPath :{}", attachmentVo.getPath());
					logger.debug("setFilename :{}", attachmentVo.getFilename());
					logger.debug("setPost_seq :{}", attachmentVo.getPost_seq());
					
					try {
						files[i].transferTo(uploadfile);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
				
					boardService.insertAttachment(attachmentVo);
				}
				
			}
			
		}
		model.addAttribute("post_seq",boardService.postSeqCurrval());
		logger.debug("boardService.postSeqCurrval() : {}", boardService.postSeqCurrval());
		return "redirect:/board/post";
	}
	
	
	
	/**
	* Method : getPost
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @param model
	* @return
	* Method 설명 : 게시물 상세 화면
	*/
	@RequestMapping(path = "/post", method = RequestMethod.GET)
	public String getPost(int post_seq, Model model) {
		logger.debug("post_seq@@@@ : {}", post_seq);
		PostVo postVo = boardService.getOnePost(post_seq);
		List<ReplyVo> replyList = boardService.getReply(post_seq);
		List<AttachmentVo> attachmentList = boardService.getAttachment(post_seq);

		model.addAttribute("post", postVo);
		model.addAttribute("replyList", replyList);
		model.addAttribute("attachmentList", attachmentList);
		
		return"tiles.post";
	}
	
	
	
	
	/**
	* Method : ajaxgetPost
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @param model
	* @return
	* Method 설명 : ajax 댓글 불러오기
	*/
	@RequestMapping(path = "/ajaxPost", method = RequestMethod.GET)
	public String ajaxgetPost(int post_seq, Model model) {
		List<ReplyVo> replyList = boardService.getReply(post_seq);
		PostVo postVo = boardService.getOnePost(post_seq);
		model.addAttribute("replyList", replyList);
		model.addAttribute("post", postVo);
		
		
		return"board/ajaxPostHtml";
	}
	
	
	
	/**
	* Method : ajaxpostPost
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @param reply
	* @param model
	* @param request
	* @param userId
	* @return
	* Method 설명 : ajax로 댓글 등록 처리
	*/
	@RequestMapping(path = "/ajaxPost", method = RequestMethod.POST)
	public String ajaxpostPost(int post_seq, String reply, Model model, HttpServletRequest request,
			 String userId) {
		UserVo userVo = (UserVo) request.getSession().getAttribute("USER_INFO");
		ReplyVo replyVo = new ReplyVo();
		replyVo.setUserid(userVo.getUserId());
		replyVo.setReply_content(reply);
		replyVo.setPost_seq(post_seq);
		int cnt = boardService.insertReply(replyVo);
		if(cnt==1){
			return "redirect:/board/ajaxPost?post_seq="+post_seq;
		}else {
			return "redirect:/board/ajaxPost";
			
		}
	}
	
	
	/**
	* Method : ajaxdeleteReply
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @param reply_seq
	* @param model
	* @return
	* Method 설명 : ajax 댓글 삭제
	*/
	@RequestMapping(path = "/ajaxdeleteReply", method = RequestMethod.GET)
	public String ajaxdeleteReply(int post_seq, String reply_seq, Model model) {
		
		int cnt = boardService.deleteReply(Integer.parseInt(reply_seq));
		if(cnt==1){
			PostVo vo = boardService.getOnePost(post_seq);
			List<ReplyVo> replyList = boardService.getReply(post_seq);
			List<AttachmentVo> attachmentList = boardService.getAttachment(post_seq);
			
			model.addAttribute("post", vo);
			model.addAttribute("replyList", replyList);
			model.addAttribute("attachmentList", attachmentList);
			return "board/ajaxPostHtml";
		}else {
			return "board/ajaxPostHtml";
		}
	}

	
	
	
	/**
	* Method : fileDownLoad
	* 작성자 : PC24
	* 변경이력 :
	* @param fileNo
	* @param downName
	* @param request
	* @param response
	* @throws IOException
	* Method 설명 : 파일 다운로드 처리
	*/
	@RequestMapping(path ="/fileDownLoad", method = RequestMethod.GET)
	public void fileDownLoad(int fileNo, String downName, HttpServletRequest request,HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		String path = boardService.getAttachmentPath(fileNo);
		logger.debug("downName!!!!!:{}",downName);
		File file = new File(path);
		String mimeType = request.getServletContext().getMimeType(file.toString());
		if (mimeType == null) {
			response.setContentType("application/octet-stream");
		}
		
		
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ downName + "\";");
		
		FileInputStream fileInputStream = new FileInputStream(file);
		ServletOutputStream servletOutputStream = response.getOutputStream();
		
		byte b[] = new byte[1024];
		int data = 0;

		while ((data = (fileInputStream.read(b, 0, b.length))) != -1) {
			servletOutputStream.write(b, 0, data);
		}
		servletOutputStream.flush();
		servletOutputStream.close();
		fileInputStream.close();
	}
	
	

	/**
	* Method : getPostModify
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @param model
	* @return
	* Method 설명 : 게시물 수정 화면
	*/
	@RequestMapping(path = "/postModify", method=RequestMethod.GET)
	public String getPostModify(int post_seq, Model model ) {
		PostVo postVo = boardService.getOnePost(post_seq);
		model.addAttribute("postVo", postVo);
		List<AttachmentVo> attachmentList = boardService.getAttachment(post_seq);
		model.addAttribute("attachmentList", attachmentList);
		return"tiles.postModify";
		
	}
	
	
	
	/**
	* Method : postPostModify
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @param model
	* @param files
	* @param request
	* @param title
	* @param smarteditor
	* @param redirectAttributes
	* @return
	* Method 설명 :게시물 수정 처리
	*/
	@RequestMapping(path = "/postModify", method = RequestMethod.POST)
	public String postPostModify(int post_seq, Model model, @RequestPart(required = false) MultipartFile[] files,
			HttpServletRequest request, String title, String smarteditor, RedirectAttributes redirectAttributes) {
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		UserVo userVo = (UserVo) request.getSession().getAttribute("USER_INFO");
		
		PostVo postVo = new PostVo();
		postVo.setUserid(userVo.getUserId());
		postVo.setTitle(title);
		postVo.setContent(smarteditor);
		postVo.setPost_seq(post_seq);
		
		int cnt =boardService.updatePost(postVo);
		
		if (cnt == 1) {
			for(int i=0; i<files.length;i++) {
				if (files[i] != null && files[i].getSize() > 0) {
					
					AttachmentVo attachmentVo = new AttachmentVo();
					String path = PartUtil.getUploadPath();
					String ext = PartUtil.getExt(files[i].getOriginalFilename());
					String fileName = UUID.randomUUID().toString();
					File uploadfile = new File(path+ File.separator +fileName+ext);
					
					attachmentVo.setPath(uploadfile.getPath());
					attachmentVo.setFilename(files[i].getOriginalFilename());
					attachmentVo.setPost_seq(post_seq);
					
					try {
						files[i].transferTo(uploadfile);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
				
					boardService.insertAttachment(attachmentVo);
				}
				
			}
			
			PostVo vo = boardService.getOnePost(post_seq);
			List<ReplyVo> replyList = boardService.getReply(post_seq);
			List<AttachmentVo> attachmentList = boardService.getAttachment(post_seq);
			
			model.addAttribute("post", vo);
			model.addAttribute("replyList", replyList);
			model.addAttribute("attachmentList", attachmentList);
			redirectAttributes.addFlashAttribute("msg", "게시물 수정 완료.");
			return "redirect:/board/post?post_seq="+post_seq;
		}else {
			
			redirectAttributes.addFlashAttribute("msg", "게시물 수정 실패.");
			return "redirect:/board/post?post_seq="+post_seq;
			
		}
			

		
	}

	/**
	* Method : attachmentGet
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @param model
	* @return
	* Method 설명 : ajax 기존 파일 불러오기
	*/
	@RequestMapping(path = "/attachmentGet", method =RequestMethod.GET)
	public String attachmentGet(int post_seq, Model model) {
		
		List<AttachmentVo> attachmentList = boardService.getAttachment(post_seq);
		model.addAttribute("attachmentList", attachmentList);
		return "jsonView";
	}
	
	
	
	/**
	* Method : attachmentDeleteAjax
	* 작성자 : PC24
	* 변경이력 :
	* @param attach
	* @param post_seq
	* @param model
	* @return
	* Method 설명 : ajax 파일 삭제
	*/
	@RequestMapping(path = "/attachmentDeleteAjax", method = RequestMethod.GET)
	public String attachmentDeleteAjax(int attach, int post_seq, Model model) {
		boardService.deleteAttachment(attach);
		PostVo postVo = boardService.getOnePost(post_seq);
		model.addAttribute("postVo", postVo);
		List<AttachmentVo> attachmentList = boardService.getAttachment(post_seq);
		model.addAttribute("attachmentList", attachmentList);
		
		return"jsonView";
	}
	
	
	/**
	* Method : postDelete
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @param boardId
	* @param redirectAttributes
	* @return
	* Method 설명 : 게시글 삭제
	*/
	@RequestMapping(path = "/postDelete",method=RequestMethod.GET)
	public String postDelete(int post_seq, int boardId, RedirectAttributes redirectAttributes) {
		int cnt = boardService.deletePost(post_seq);
		
		if(cnt ==1){
			redirectAttributes.addFlashAttribute("msg", "게시물 삭제 완료.");
			return"redirect:boardPagingView?board_id="+boardId;
			
		}else{
			redirectAttributes.addFlashAttribute("msg", "게시물 삭제 실패");
			return"redirect:boardPagingView?board_id="+boardId;
		}
	}
	
	
	/**
	* Method : getReplyPost
	* 작성자 : PC24
	* 변경이력 :
	* @param post_seq
	* @param model
	* @return
	* Method 설명 : 답글 화면처리
	*/
	@RequestMapping(path = "/replyPost", method = RequestMethod.GET)
	public String getReplyPost(int post_seq, Model model) {
		PostVo postVo = boardService.getOnePost(post_seq);
		model.addAttribute("postVo", postVo);
		return "board/replyPost";
		
	}
	
	
	/**
	* Method : postReplyPost
	* 작성자 : PC24
	* 변경이력 :
	* @param board_id
	* @param post_seq
	* @param group_seq
	* @param model
	* @param title
	* @param smarteditor
	* @param files
	* @param request
	* @return
	* Method 설명 : 답글 처리
	*/
	@RequestMapping(path = "/replyPost", method = RequestMethod.POST)
	public String postReplyPost(int board_id, int post_seq, int group_seq,  Model model, String title, String smarteditor,
			@RequestPart(required = false) MultipartFile[] files, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		UserVo userVo = (UserVo) request.getSession().getAttribute("USER_INFO");

		PostVo postVo= new PostVo();
		postVo.setUserid(userVo.getUserId());
		postVo.setTitle(title);
		postVo.setContent(smarteditor);
		postVo.setBoard_id(board_id);
		postVo.setParent_seq(post_seq);
		postVo.setGroup_seq(group_seq);
		int cnt =boardService.insertReplyPost(postVo);
		
		if (cnt == 1) {
			for(int i=0; i<files.length; i++) {
				if (files[i] != null && files[i].getSize() > 0) {
					
					AttachmentVo attachmentVo = new AttachmentVo();
					String path = PartUtil.getUploadPath();
					String ext = PartUtil.getExt(files[i].getOriginalFilename());
					String fileName = UUID.randomUUID().toString();
					File uploadfile = new File(path+ File.separator +fileName+ext);
					
					attachmentVo.setPath(uploadfile.getPath());
					attachmentVo.setFilename(files[i].getOriginalFilename());
					attachmentVo.setPost_seq(boardService.postSeqCurrval());
					
					try {
						files[i].transferTo(uploadfile);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
				
					boardService.insertAttachment(attachmentVo);
				}
				
			}
			

		}
		return "redirect:post?post_seq="+boardService.postSeqCurrval();
		
	}
	
	@RequestMapping(path = "/ajaxpostSearch", method = RequestMethod.GET)
	public String ajaxpostSearch(String searchSelect, String searchTxt, PageVo pageVo, int board_id, Model model) {
		
		logger.debug("!!!!searchSelect : {}", searchSelect);
		logger.debug("!!!!searchTxt : {}", searchTxt);
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("searchSelect", searchSelect);
		searchMap.put("searchTxt", searchTxt);
		searchMap.put("page", pageVo.getPage());
		searchMap.put("pageSize", pageVo.getPageSize());
		searchMap.put("board_id", board_id);
		
		
		Map<String, Object> resultMap = boardService.postSearch(searchMap, pageVo, board_id);

		List<PostVo> postPagingList = (List<PostVo>) resultMap.get("postPagingList");

		for (PostVo vo : postPagingList) {
			vo.setTitle(vo.getTitle().replace(" ", "&nbsp;"));
		}
		int paginationSize = (int) resultMap.get("paginationSize");

		model.addAttribute("postPagingList", postPagingList);
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo",pageVo);
		model.addAttribute("board_id",board_id);
		
		return "board/boardpagingListAjaxHtml";
	}

}
