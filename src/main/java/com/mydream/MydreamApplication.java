package com.mydream;


import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import com.github.pagehelper.PageHelper;

import java.util.Properties;

/**
 * 项目的主配置类
 * @ServletComponentScan 这个使用来扫描@WebFilter 的让@WebFilter起作用。也可以写在实现了Filter的类上面，最好写在Apllication这个上面，通用配置。
 * @author LiPengWei 
 * @date 2018年9月7日
 */
@SpringBootApplication
@MapperScan("com.mydream.backstate.*.dao")
@ServletComponentScan
public class MydreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydreamApplication.class, args);
	}

	// 配置 mybatis 的分页插件pageHelper
	@Bean
	public PageHelper pageHelper(){
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum","true");
		properties.setProperty("rowBoundsWithCount","true");
		properties.setProperty("reasonable","true");
		properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
		pageHelper.setProperties(properties);
		
		return pageHelper;
	}

}
