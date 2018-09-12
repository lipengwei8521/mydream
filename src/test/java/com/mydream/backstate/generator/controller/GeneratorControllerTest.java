package com.mydream.backstate.generator.controller;

import com.mydream.backstate.connection.service.ConnectionService;
import com.mydream.backstate.generator.method.BuildFile;
import com.mydream.backstate.generator.method.GetTableDeatil;
import com.mydream.backstate.response.entity.ResponseBean;
import com.mydream.utils.ConnectUtil;
import com.mydream.utils.ConstantString;
import com.mydream.utils.StringUtil;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Package: com.mydream.backstate.generator.controller
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/614:47
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratorControllerTest {

    @Autowired
    private ConnectionService connectionService;

    /**
     * 获取所有的表名
     */
    @Test
    public void showAllTable(){

        String con_Ip = "127.0.0.1";
        String con_port = "3306";
        String con_dbName = "mydream";

        // 连接数据库驱动
        String dataBaseDriver = "com.mysql.jdbc.Driver";
        // 连接数据库的地址
        String dataBaseUrl = "jdbc:mysql://"+con_Ip+":"+con_port+"/"+con_dbName;
        // 连接数据库的用户名
        String dataBaseUsername = "root";
        // 连接数据库密码
        String dataBasePassword = "root";

        try {
            Connection connection = ConnectUtil.getConnection(dataBaseDriver, dataBaseUrl, dataBaseUsername, dataBasePassword);

            if (connection == null) {
                System.out.println("数据库连接失败");
            } else {
                System.out.println("数据库连接成功");
            }
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
            ResultSet tableSet = db.getTables(null, db.getUserName(), null, new String[]{"TABLE"});

            if (tableSet == null) {
                System.out.println("表不存在");
            }

            String tableComment = "";
            while (tableSet.next()) {
                tableComment = tableSet.getString(3);
                System.out.println("tableComment = [" + tableComment + "]");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 获取表的所有字段并生成文件
     * @throws Exception
     */
    @Test
    public void genneratorCode() throws Exception {

        String tableName = "student";
        String conname = "mydream";
        ResponseBean responseBean = new ResponseBean();

        Connection connection =null;
        try {
            // 创建的代码存放的路径
            String basePath="C:\\Users\\ASUS\\Desktop";
            // 是否生成相对应的代码层
            boolean isCreatEntity=true;
            boolean isCreatDao = true;
            boolean isCreatMapper = true;
            boolean isCreatService = true;
            boolean isCreatController = true;
            boolean isCreatHtml = true;
            boolean isCreatJs = true;

            com.mydream.backstate.connection.entity.Connection  myCon = connectionService.getConnectionByConname(conname);
            // 连接数据库驱动
            String dataBaseDriver = "com.mysql.jdbc.Driver";
            // 连接数据库的地址
            String dataBaseUrl = "jdbc:mysql://"+myCon.getConip()+":"+myCon.getConport()+"/"+myCon.getCondbname();
            // 连接数据库的用户名
            String dataBaseUsername = myCon.getConusername();
            // 连接数据库密码
            String dataBasePassword = myCon.getConpassword();

            connection = ConnectUtil.getConnection(dataBaseDriver, dataBaseUrl, dataBaseUsername, dataBasePassword);

            if (connection == null) {
                responseBean.setState(ConstantString.FAILE);
                responseBean.setMes("数据库连接失败！");
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
                ResultSet tableSet = db.getTables(null, db.getUserName(), tableName, new String[]{"TABLE"});

                if(tableSet == null){
                    System.out.println("[*********]==该表不存在");
                }

                String tableComment="";
                while (tableSet.next()) {
                    tableComment=tableSet.getString("REMARKS");
                }
                System.out.println("tableComment ===== [" + tableComment + "]" );

                // 获取表主键
                ResultSet keySet=db.getPrimaryKeys(null, null, tableName);
                Set primarySet = new HashSet<String>();
                if(keySet!=null){
                    while(keySet.next()){
                        primarySet.add(keySet.getString(4));
                        System.out.println("主键的名称为========="+primarySet);
                    }
                }
                /**
                 * 获取可在指定类别中使用的表列的描述。
                 * 方法原型:ResultSet getColumns(String catalog,String schemaPattern,String tableNamePattern,String columnNamePattern)
                 * catalog - 表所在的类别名称;""表示获取没有类别的列,null表示获取所有类别的列。
                 * schema - 表所在的模式名称(oracle中对应于Tablespace);""表示获取没有模式的列,null标识获取所有模式的列; 可包含单字符通配符("_"),或多字符通配符("%");
                 * tableNamePattern - 表名称;可包含单字符通配符("_"),或多字符通配符("%");
                 * columnNamePattern - 列名称; ""表示获取列名为""的列(当然获取不到);null表示获取所有的列;可包含单字符通配符("_"),或多字符通配符("%");
                 */
                net.sf.json.JSONArray jsonArray = new net.sf.json.JSONArray();
                ResultSet columnSet=db.getColumns(null, null, tableName, null);
                // 主键个数
                int flag=0;
                if(columnSet != null){
                    // 循环列
                    while (columnSet.next()) {
                        // 获取列信息,列名
                        String columnName = columnSet.getString("COLUMN_NAME");
                        // 列说明
                        String columnComment = columnSet.getString("REMARKS");
                        // 列类型
                        String sqlType = columnSet.getString("TYPE_NAME");
                        // 列长度
                        String columnSize = columnSet.getString("COLUMN_SIZE");
                        // 小数位精度
                        String decimalDigits = columnSet.getString("DECIMAL_DIGITS");
                        // 是否非空约束
                        String isNullable = columnSet.getString("IS_NULLABLE").equals("YES")?"true":"false";
                        // 将列信息保存在JSONObject中
                        net.sf.json.JSONObject tableLine=new net.sf.json.JSONObject();
                        tableLine.put("columnName", columnName);
                        tableLine.put("isNullAble", isNullable);
                        tableLine.put("columnJavaName", StringUtil.formatDBNameToVarName(columnName));
                        tableLine.put("columnComment", columnComment);
                        tableLine.put("columnType", sqlType);
                        tableLine.put("columnSize", columnSize);
                        tableLine.put("columnJavaType", StringUtil.getJavaType(sqlType, columnSize, decimalDigits));
                        tableLine.put("tableColumnsDecimal", decimalDigits);

                        System.out.println("tableLine========="+tableLine);

                        //当前列是否是主键
                        if(primarySet.contains(columnName)){
                            tableLine.put("isPrimaryKey","true");
                            flag+=1;
                        }else{
                            tableLine.put("isPrimaryKey","false");
                        }
                        jsonArray.add(tableLine);
                    }
                }
                String author = "李鹏伟";
                BuildFile.buildFile(tableName,tableComment,basePath,isCreatEntity,isCreatDao,isCreatMapper,
                        isCreatService,isCreatController,isCreatHtml,isCreatJs,jsonArray,flag+"",author);

                System.out.println("代码生成成功-------------");
            }}catch (Exception e){

            e.printStackTrace();
        }finally {

            ConnectUtil.closeConnection(connection);
        }
    }

    @Test
    public void testPath(){
        String aa = this.getClass().getClassLoader().getResource(".").getPath();
        System.out.println("========= = [" + aa );
        String a = StringUtils.substringBefore(aa,"target");
        String [] a1= a.split("/");
        String newStr = "";
        int num = 0;
        for (String str:a1) {
            if(num!=0){
                newStr += str +"/";
            }
            num++;
        }

        System.out.println("新的路径为==== [" + newStr );



        System.out.println("&&&&&&&&&& [" + a );
    }

}