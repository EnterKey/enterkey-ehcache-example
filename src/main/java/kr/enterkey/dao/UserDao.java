package kr.enterkey.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import kr.enterkey.bean.User;

@Component
public interface UserDao {
	void selectUserByUserId(@Param("userId") Long userId);

	List<User> selectUsers();

}
