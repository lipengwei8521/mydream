package com.mydream.backstate.connection.controller;

import com.mydream.backstate.connection.entity.Connection;
import com.mydream.backstate.connection.service.ConnectionService;
import com.mydream.backstate.response.entity.ResponseBean;
import com.mydream.utils.ConnectUtil;
import com.mydream.utils.ConstantString;
import com.mydream.utils.PageBean;
import com.mydream.utils.ResponseUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

/**
 * Package: com.mydream.backstate.connection.controller
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/216:17
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionControllerTest {

    @Autowired
    private ConnectionService connectionService;

    /**
     * 测试connection的显示页
     */
    @Test
    public void toConnection(){
        int currentPage = 1;
        // 默认每页显示的数据条数
        int pageSize = 2;
        PageBean<Connection> pageBean = connectionService.findConnection(currentPage,pageSize,new Connection());
        System.out.println("list长度"+pageBean.getItems().size());
        for (Connection a:pageBean.getItems()){
            System.out.println("内容==-=-=【"+a.getConip()+"奥术大师大多"+a.getConname());
        }
        System.out.println("====="+pageBean);
        System.out.println("集合**********"+pageBean.getItems());
    }

    @Test
    public void isConnection(){

        String con_dbName = "mydream";
        // 1 Mysql，2 Oracle,
        String con_type = "1";
        String con_Ip = "127.0.0.1";
        String con_port = "3306";
        String con_username = "root";
        String con_password = "root";

        // 连接数据库驱动
        String dataBaseDriver = "";
        // 连接数据库的地址
        String dataBaseUrl = "jdbc:mysql://"+con_Ip+":"+con_port+"/"+con_dbName;
        // 连接数据库的用户名
        String dataBaseUsername = con_username;
        // 连接数据库密码
        String dataBasePassword = con_password;

        if("1".equals(con_type)){

            dataBaseDriver ="com.mysql.jdbc.Driver";

        }

        java.sql.Connection connection = ConnectUtil.getConnection(dataBaseDriver,dataBaseUrl,dataBaseUsername,dataBasePassword);

        if(connection == null){
            System.out.println("连接信息有误，请重新确认！");


        }else{
            System.out.println("成功连接！");

        }


    }

}