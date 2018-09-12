package com.mydream.backstate.authuser.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.mydream.backstate.authuser.service.Test_AuthUserService;
import com.mydream.utils.EncryptUtil;
import com.mydream.backstate.authuser.entity.AuthUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_AuthUserService {
	
	@Autowired
	private AuthUserService authUserService;
	
	
	
	/**
	 * 测试新增用户方法
	 */
	@Test
	public void addAuthUser() {
		AuthUser authUser = new AuthUser();
		authUser.setName("张三");
		authUser.setUsername("zhangsan");
		authUser.setPassword("123456");
		authUser.setCreateTime("2018-09-07");
		
		String jamistr = EncryptUtil.stringToBASE64("123456");
		System.out.println("jiami="+jamistr);
		
		String jiemistr = EncryptUtil.base64ToString(jamistr);
		System.out.println("jiemi="+jiemistr);
		
//		authUserService.addAuthUser(authuser);
	}
	
	@Test
	public void selectAuthUser() {
		AuthUser authUser = new AuthUser();
		authUser.setUsername("zhangsan");
		AuthUser authUser2 =authUserService.findUserOne(authUser);
		System.out.println("&&&&***"+authUser2);
	}
	

}
