package com.mydream.backstate.connection.dao;

import com.mydream.backstate.connection.entity.Connection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Package: com.mydream.backstate.connection.dao
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/216:11
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionMapperTest {

    @Autowired
    private ConnectionMapper connectionMapper;

    @Test
    public void findConnection(){
        List<Connection> connectionList = connectionMapper.findConnection(new Connection());
        System.out.println("===="+connectionList);
        System.out.println("list长度"+connectionList.size());
    }
}
