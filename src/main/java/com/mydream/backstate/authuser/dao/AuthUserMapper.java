package com.mydream.backstate.authuser.dao;

import java.util.List;

import com.mydream.backstate.authuser.entity.AuthUser;

/**
 * 用户信息表的持久层
 * 
 * @author LiPengWei
 * @date 2018-09-06
 */
public interface AuthUserMapper {
	/**
	 * 新增用户
	 * 
	 * @author LiPengWei
	 * @date 2018-09-06
	 * @param authUser
	 * @return AuthUser
	 */
	void addAuthUser(AuthUser authUser);
	
	/**
	 * 删除用户
	 * 
	 * @author LiPengWei
	 * @date 2018-09-06
	 * @param authUser
	 */
	void delAuthUser(AuthUser authUser);
	
	/**
	 * 修改用户信息
	 * 
	 * @author LiPengWei
	 * @date 2018-09-06
	 * @param authUser
	 * @return AuthUser
	 */
	AuthUser updateUser(AuthUser authUser);
	
	/**
	 * 根据输入信息查询用户信息
	 * 
	 * @author LiPengWei
	 * @date 2018-09-06
	 * @param authUser
	 * @return List<AuthUser>
	 */
	List<AuthUser> findUser(AuthUser authUser);
	
	/**
	 * 根据输入的信息查询单个用户信息
	 * 
	 * @param authUser
	 * @return
	 */
	AuthUser findUserOne(AuthUser authUser);

}
