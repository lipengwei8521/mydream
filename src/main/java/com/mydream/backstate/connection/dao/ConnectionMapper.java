package com.mydream.backstate.connection.dao;

import com.mydream.backstate.connection.entity.Connection;

import java.util.List;

/**
 * 数据库信息对象的数据库访问层
 *
 * Created by  lpw'ASUS on 2018/7/27.
 */
public interface ConnectionMapper {
	/**
     * 新增connection对象
     *
     * @param connection
     */
     void insertConnert(Connection connection);

    /**
     * 获取所有的数据库连接信息
     *
     * @return
     */
     List<Connection> getAllConnection();

    /**
     * 统计数据库连接信息的总数
     *
     * @return
     */
    int countConnection();

    /**
     * 根据用户名查找Connection
     *
     * @param conname
     * @return
     */
    Connection findConnectionByConName(String conname);

    /**
     * 根据传入的参数查询Connection集合
     * @param connection
     * @return
     */
    List<Connection> findConnection(Connection connection);

    /**
     * 根据id删除connection对象
     *
     * @param conid
     */
    void delConnection(Integer conid);

}
