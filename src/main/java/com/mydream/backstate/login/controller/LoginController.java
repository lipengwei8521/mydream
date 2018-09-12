package com.mydream.backstate.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mydream.backstate.authuser.entity.AuthUser;
import com.mydream.backstate.login.service.LoginService;
import com.mydream.backstate.response.entity.ResponseBean;

@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	/**
	 * 跳转到登陆页面
	 * @作者 李鹏伟
	 * @时间 2018-09-05
	 * @return login登录页
	 */
	@RequestMapping("login")
	public String toLogin() {
		return "login";
	}
	
	/**
	 *   验证提交的用户信息，成功则跳转到Index页面
	 * @作者 李鹏伟
	 * @时间 2018-09-05
	 * @return 跳转到index界面
	 */
	@RequestMapping("toMain")
	public String toMain(AuthUser authUser, Model model, HttpServletRequest request) {
		ResponseBean responseBean =new ResponseBean();
		
		// 判断用户名和密码是否正确
		AuthUser authUser2 = loginService.checkAythUser(authUser);
		
		if(authUser2 == null) {
			responseBean.setMes("用户名或者密码错误！");
			model.addAttribute("responseBean", responseBean);
			return "login";
		}else {
			request.getSession().setAttribute("authuser", authUser2);
			return "main";
		}
		
		
		
		
		
		
		
	}
	
	
}
