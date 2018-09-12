package com.mydream.backstate.authuser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydream.backstate.authuser.dao.AuthUserMapper;
import com.mydream.backstate.authuser.entity.AuthUser;
/**
 * AuthUser的业务处理层
 * 
 * @author LiPengWei 
 * @date 2018年9月6日
 */
@Service
public class AuthUserServiceImpl implements AuthUserService{
	
	@Autowired
	private AuthUserMapper authUserMapper;

	@Override
	public void addAuthUser(AuthUser authUser) {
		// TODO Auto-generated method stub
		authUserMapper.addAuthUser(authUser);
	}

	@Override
	public AuthUser findUserOne(AuthUser authUser) {
		// TODO Auto-generated method stub
		return authUserMapper.findUserOne(authUser);
	}

}
