package com.mydream.backstate.generator.method;

import com.mydream.backstate.response.entity.ResponseBean;
import com.mydream.utils.ConnectUtil;
import com.mydream.utils.ConstantString;
import com.mydream.utils.StringUtil;
import net.sf.json.JSONObject;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

/**
 * Package: com.mydream.backstate.generator.method
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/714:37
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class GetTableDeatil {

    /**
     * 获取数据库中所有的表名
     *
     * @param dataBaseDriver 数据库驱动
     * @param dataBaseUrl 数据库连接的url
     * @param dataBaseUsername 连接数据库的用户名
     * @param dataBasePassword 连接数据库的密码
     * @return
     * @throws Exception
     */
    public static ResponseBean getAllTables(String dataBaseDriver, String dataBaseUrl, String dataBaseUsername, String dataBasePassword) throws Exception {

        // 数据库连接
        Connection connection = null;
        // 存放数据库表名
        List<String> strList = new ArrayList<String>();
        // 返回信息实体类
        ResponseBean responseBean = new ResponseBean();

        ResultSet tableSet = getMytableSet(dataBaseDriver,dataBaseUrl,dataBaseUsername,dataBasePassword,null);

        if (tableSet == null) {
            responseBean.setState(ConstantString.FAILE);
            responseBean.setMes("数据库中无任何内容！");
        }

        String tableComment = "";
        while (tableSet.next()) {
            tableComment = tableSet.getString(3);
            strList.add(tableComment);
        }

        responseBean.setResData(strList);
        responseBean.setState(ConstantString.SUCCESS);

        return responseBean;
    }

    /**
     * 获取当前表的所有字段信息
     *
     * @param dataBaseDriver 数据库驱动
     * @param dataBaseUrl 数据库连接的url
     * @param dataBaseUsername 连接数据库的用户名
     * @param dataBasePassword 连接数据库的密码
     * @return
     * @throws Exception
     */
    public static JSONObject getColumnDetail(String dataBaseDriver, String dataBaseUrl, String dataBaseUsername, String dataBasePassword, String tableName) throws Exception{

        DatabaseMetaData db = getMyDatabaseMetaData(dataBaseDriver,dataBaseUrl,dataBaseUsername,dataBasePassword);

        ResultSet tableSet = db.getTables(null, db.getUserName(), tableName, new String[]{"TABLE"});

        //将列信息保存在JSONObject中
        JSONObject tableLine =new JSONObject();

        if (tableSet == null) {
            throw new Exception("该表不存在");
        }else{

            String tableComment = "";
            while (tableSet.next()) {
                tableComment = tableSet.getString(3);
                System.out.println("tableComment====" + tableComment);
            }

            ResultSet keySet = db.getPrimaryKeys(null, null, tableName);

            Set primarySet = new HashSet<String>();
            if (keySet != null) {
                while (keySet.next()) {
                    primarySet.add(keySet.getString(4));
                    System.out.println("primarySet====" + primarySet);
                }
            }


            ResultSet columnSet = db.getColumns(null, null, tableName, null);
            int flag = 0;//主键个数



            if (columnSet != null) {

                while (columnSet.next()) {//循环列
                    //获取列信息
                    String columnName = columnSet.getString("COLUMN_NAME");//列名
                    String columnComment = columnSet.getString("REMARKS");//列说明
                    String sqlType = columnSet.getString("TYPE_NAME");//列类型
                    String columnSize = columnSet.getString("COLUMN_SIZE");//列长度
                    String decimalDigits = columnSet.getString("DECIMAL_DIGITS");//小数位精度
                    String isNullable = columnSet.getString("IS_NULLABLE").equals("YES") ? "true" : "false";//是否非空约束

                    //将列信息保存在JSONObject中
                    tableLine = new JSONObject();
                    tableLine.put("tableColumnsName", columnName);
                    tableLine.put("isNullAble", isNullable);
                    tableLine.put("tableColumnsNameTf", StringUtil.formatDBNameToVarName(columnName));
                    tableLine.put("tableColumnsComment", columnComment);
                    tableLine.put("tableColumnsType", sqlType);
                    tableLine.put("tableColumnsSize", columnSize);
                    tableLine.put("tableColumnsJavaType", StringUtil.getJavaType(sqlType, columnSize, decimalDigits));
                    tableLine.put("tableColumnsDecimal", decimalDigits);

                    //当前列是否是主键
                    if (primarySet.contains(columnName)) {
                        tableLine.put("isPrimaryKey", "true");
                        flag += 1;
                    } else {
                        tableLine.put("isPrimaryKey", "false");
                    }

                    System.out.println("tableLine====" + tableLine);

                }
            }

        }

        return tableLine;
    }



    /**
     * 获取自己的tableSet
     *
     *@param dataBaseDriver 数据库驱动
     * @param dataBaseUrl 数据库连接的url
     * @param dataBaseUsername 连接数据库的用户名
     * @param dataBasePassword 连接数据库的密码
     * @param tableName 为null时查询所有表名，传入摸一个表名的时候获得表的所有信息
     * @return
     * @throws Exception
     */
    public static ResultSet getMytableSet(String dataBaseDriver, String dataBaseUrl, String dataBaseUsername, String dataBasePassword,String tableName) throws Exception {
        // 数据库连接
        Connection connection = ConnectUtil.getConnection(dataBaseDriver, dataBaseUrl, dataBaseUsername, dataBasePassword);
        ResultSet tableSet = null;
        if (connection == null) {
            throw new Exception("连接数据库失败！");
        } else {
            // 获取数据库元数据相关信息对象
            DatabaseMetaData db = connection.getMetaData();

            /**
             * 获取给定类别中使用的表的描述。
             * 方法原型:ResultSet getTables(String catalog,String schemaPattern,String tableNamePattern,String[] types);
             * catalog - 表所在的类别名称;""表示获取没有类别的列,null表示获取所有类别的列。
             * schema - 表所在的模式名称(oracle中对应于Tablespace);""表示获取没有模式的列,null标识获取所有模式的列; 可包含单字符通配符("_"),或多字符通配符("%");
             * tableNamePattern - 表名称;可包含单字符通配符("_"),或多字符通配符("%");为空的时候查出数据库所有的表名
             * types - 表类型数组; "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM";null表示包含所有的表类型;可包含单字符通配符("_"),或多字符通配符("%");
             */
            tableSet = db.getTables(null, db.getUserName(), tableName, new String[]{"TABLE"});
        }
        return tableSet;
    }

    /**
     * 获取我的keySet
     *
     * @param dataBaseDriver 数据库驱动
     * @param dataBaseUrl 数据库连接的url
     * @param dataBaseUsername 连接数据库的用户名
     * @param dataBasePassword 连接数据库的密码
     * @param tableName 为null时查询所有表名，传入摸一个表名的时候获得表的所有信息
     * @return
     * @throws Exception
     */
    public static ResultSet getMyKeySet(String dataBaseDriver, String dataBaseUrl, String dataBaseUsername, String dataBasePassword,String tableName) throws Exception {
        // 数据库连接
        Connection connection = ConnectUtil.getConnection(dataBaseDriver, dataBaseUrl, dataBaseUsername, dataBasePassword);
        ResultSet keySet = null;
        if (connection == null) {
            throw new Exception("连接数据库失败！");
        } else {
            // 获取数据库元数据相关信息对象
            DatabaseMetaData db = connection.getMetaData();

            /**
             * 获取给定类别中使用的表的描述。
             * 方法原型:ResultSet getTables(String catalog,String schemaPattern,String tableNamePattern,String[] types);
             * catalog - 表所在的类别名称;""表示获取没有类别的列,null表示获取所有类别的列。
             * schema - 表所在的模式名称(oracle中对应于Tablespace);""表示获取没有模式的列,null标识获取所有模式的列; 可包含单字符通配符("_"),或多字符通配符("%");
             * tableNamePattern - 表名称;可包含单字符通配符("_"),或多字符通配符("%");为空的时候查出数据库所有的表名
             * types - 表类型数组; "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM";null表示包含所有的表类型;可包含单字符通配符("_"),或多字符通配符("%");
             */
            keySet = db.getTables(null, db.getUserName(), tableName, new String[]{"TABLE"});
        }
        return keySet;
    }

    /**
     * 获取我的数据库元数据相关信息对象
     *
     * @param dataBaseDriver 数据库驱动
     * @param dataBaseUrl 数据库连接的url
     * @param dataBaseUsername 连接数据库的用户名
     * @param dataBasePassword 连接数据库的密码
     * @return
     * @throws Exception
     */
    public static DatabaseMetaData getMyDatabaseMetaData(String dataBaseDriver, String dataBaseUrl, String dataBaseUsername, String dataBasePassword) throws Exception {
        // 数据库连接
        Connection connection = ConnectUtil.getConnection(dataBaseDriver, dataBaseUrl, dataBaseUsername, dataBasePassword);
        DatabaseMetaData db = null;
        if (connection == null) {
            throw new Exception("连接数据库失败！");
        } else {
            // 获取数据库元数据相关信息对象
            db = connection.getMetaData();

        }
        return db;
    }


}
