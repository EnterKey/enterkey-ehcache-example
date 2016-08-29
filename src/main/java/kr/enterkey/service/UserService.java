package kr.enterkey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.enterkey.bean.User;
import kr.enterkey.dao.UserDao;
import kr.enterkey.util.LocalCacheHelper;

@Service
public class UserService {
	@Autowired
	UserDao userDao;

	@Autowired
	LocalCacheHelper localCacheHelper;

	public void getUsers() {
		// Ehcache key 생성
		Object key = LocalCacheHelper.createCacheKey();
		List<User> result = (List<User>) LocalCacheHelper.getCachedObject("testcache", key);

		// cache data 존재 유무에 따라 값 반영
		if(result == null) {
			result = userDao.selectUsers();
			LocalCacheHelper.putCacheObject("testcache", key, result);
		}
	}

	public void getUser(Long userId) {

	}
}
