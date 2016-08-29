package kr.enterkey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.enterkey.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping(method=RequestMethod.GET)
	public String getUsers() {
		userService.getUsers();
		return null;
	}

	@RequestMapping(value="/{userId}", method=RequestMethod.GET)
	public String getUser(
		@PathVariable("userId") Long userId
	) {
		userService.getUser(userId);
		return null;
	}

}
