package com.mydream.backstate.authuser.service;

import com.mydream.backstate.authuser.entity.AuthUser;

/**
 * AuthUser的业务处理层
 * 
 * @author LiPengWei 
 * @date 2018年9月6日
 */
public interface AuthUserService {
	
	/**
	 * 新增用户
	 * @param authUser
	 */
	void addAuthUser(AuthUser authUser);
	
	/**
	 * 根据输入的信息查询单个用户信息
	 * 
	 * @param authUser
	 * @return
	 */
	AuthUser findUserOne(AuthUser authUser);
}
