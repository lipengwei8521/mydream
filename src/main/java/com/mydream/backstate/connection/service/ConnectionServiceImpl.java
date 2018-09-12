package com.mydream.backstate.connection.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mydream.backstate.connection.dao.ConnectionMapper;
import com.mydream.backstate.connection.entity.Connection;
import com.mydream.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据库连接信息业务层的实现类
 *
 * Created by  lpw'ASUS on 2018/7/27.
 */
@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private ConnectionMapper connectionMapper;

    /**
     * 添加连接对象
     *
     * @param connection
     */
    @Override
    public void insertConnection(Connection connection) {
        connectionMapper.insertConnert(connection);
    }

    /**
     * 获取数据库连接信息
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Connection> findConnection(Integer currentPage, Integer pageSize,Connection connection) {
    	
    	if(currentPage <=0 || currentPage == null) {
    		currentPage = 1;
    	}
    	 
        // 发现sqlmapper.xml中if不能识别null
        if(connection.getConname() == null || "".equals(connection.getConname())){
            connection.setConname("");
        }
        
        
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        Page<Connection> tPagee = PageHelper.startPage(currentPage, pageSize);
        System.out.println("tPagee****"+tPagee);
        // 全部的数据库连接信息
        List<Connection> connectionList = connectionMapper.findConnection(connection);
        
        System.out.println("这里的数量有多少"+connectionList.size());
        //统计数据库连接信息的数量
        int count = (int)tPagee.getTotal();
        PageBean<Connection> pageData = new PageBean(currentPage,pageSize,count);
        pageData.setItems(connectionList);
        
        System.out.println("pagedata===="+pageData);
        
        return pageData;
    }

    /**
     * 验证连接名称是否重复
     *
     * @param conName 连接名称
     * @return Boolean true 说明Connection为null，conname 可以使用，false表示conname已经存在
     */
    @Override
    public Boolean isConName(String conName) {
        Connection connection = connectionMapper.findConnectionByConName(conName);
        if(connection == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据id删除connection对象
     *
     * @param conid
     */
    @Override
    public void delConnection(Integer conid) {
        connectionMapper.delConnection(conid);
    }

    /**
     * 获取所有连接信息
     *
     * @return
     */
    @Override
    public List<Connection> getALlConnection() {
        return connectionMapper.getAllConnection();
    }

    /**
     * 根据连接名称查找connection
     *
     * @param conname
     * @return
     */
    @Override
    public Connection getConnectionByConname(String conname) {
        return connectionMapper.findConnectionByConName(conname);
    }
}
