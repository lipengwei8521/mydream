package com.mydream.backstate.login.service;

import com.mydream.backstate.authuser.entity.AuthUser;

public interface LoginService {
	
	/**
	 * 验证用户名密码是否正确
	 * @param authUser
	 * @return
	 */
	AuthUser checkAythUser(AuthUser authUser);
}
