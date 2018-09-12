package com.mydream.backstate.login.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mydream.backstate.authuser.entity.AuthUser;
import com.mydream.backstate.login.service.LoginService;
import com.mydream.utils.EncryptUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_LoginController {
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 测试检验用户名密码
	 */
	@Test
	public void testCheckAuthUser() {
		AuthUser authUser = new AuthUser();
		authUser.setUsername("zhangsan");
		authUser.setPassword(EncryptUtil.stringToMD5("123456"));
		
		AuthUser authUser2 = loginService.checkAythUser(authUser);
		System.out.println("======"+authUser2);
	}
}
