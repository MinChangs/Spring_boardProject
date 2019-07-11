package kr.or.ddit.user.dao;

import kr.or.ddit.user.model.UserVo;

public interface IUserDao {

	
	/**
	 * 
	* Method : getUser
	* 작성자 : PC24
	* 변경이력 :
	* @param userId
	* @return
	* Method 설명 : 사용자 정보
	 */
	UserVo getUser(String userId);
	
	
	
}
