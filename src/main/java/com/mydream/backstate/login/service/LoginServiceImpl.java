package com.mydream.backstate.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydream.backstate.authuser.entity.AuthUser;
import com.mydream.backstate.authuser.service.AuthUserService;
import com.mydream.utils.EncryptUtil;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private AuthUserService authUserService;

	@Override
	public AuthUser checkAythUser(AuthUser authUser) {
		String thepsw =  EncryptUtil.stringToBASE64(authUser.getPassword());
		System.out.println("=====" +thepsw);  
		authUser.setPassword(thepsw);
		return authUserService.findUserOne(authUser);
	}

}
