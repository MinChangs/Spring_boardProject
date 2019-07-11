package kr.or.ddit.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import kr.or.ddit.user.dao.IUserDao;
import kr.or.ddit.user.model.UserVo;

@Repository
public class UserService implements IUserService {
	@Resource(name="userDao")
	IUserDao userDao;
	
	
	/**
	 * Method : getUser 작성자 : PC24 변경이력 :
	 * 
	 * @param userId
	 * @return Method 설명 : 사용자 정보
	 */
	@Override
	public UserVo getUser(String userId) {

		return userDao.getUser(userId);
	}


}
