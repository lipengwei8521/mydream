package com.mydream.backstate.connection.service;

import com.mydream.backstate.connection.entity.Connection;
import com.mydream.utils.PageBean;

import java.util.List;

/**
 * 数据库连接信息的业务层
 *
 * Created by  lpw'ASUS on 2018/7/27.
 */
public interface ConnectionService {

    /**
     * 添加连接对象
     *
     * @param connection
     */
    void insertConnection(Connection connection);

    /**
     * 获取数据库连接信息
     *
     * @param currentPage 当前页数
     * @param pageSize 每页显示的数量
     * @return
     */
    PageBean<Connection> findConnection(Integer currentPage, Integer pageSize, Connection connection);

    /**
     * 验证连接名称是否重复
     *
     * @param conName 连接名称
     * @return Boolean true 说明Connection为null，conname 可以使用，false表示conname已经存在
     */
    Boolean isConName(String conName);

    /**
     * 根据id删除connection对象
     *
     * @param conid
     */
    void delConnection(Integer conid);

    /**
     * 获取所有连接信息
     *
     * @return
     */
    List<Connection> getALlConnection();

    /**
     * 根据连接名称查找connection
     *
     * @param conname
     * @return
     */
    Connection getConnectionByConname(String conname);
}
