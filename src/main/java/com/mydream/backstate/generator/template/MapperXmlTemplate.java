package com.mydream.backstate.generator.template;

import com.mydream.utils.FileUtil;
import com.mydream.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Package: com.mydream.backstate.generator.template
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/1310:57
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class MapperXmlTemplate {
    /**
     * * @param tableName 表名
     *
     * @param filePath      文件路径
     * @param charset       编码格式
     * @param daoPackage    dao类的路径
     * @param entityPackage entity类的路径
     * @param json          数据库信息
     * @param keyNum        主键个数
     * @return
     * @throws Exception
     */
    public static void creatMapper(String tableName, String filePath, String charset,
                                           String daoPackage, String entityPackage, JSONArray json, String keyNum) throws Exception {

        // 错误日志
        Logger logger = LoggerFactory.getLogger(MapperXmlTemplate.class);

        // 创建文件夹
        if (!FileUtil.mkdirs(filePath)) {

            logger.error("文件夹[" + filePath + "创建不成功");
            System.out.println("文件夹[" + filePath + "创建不成功");

        }
        boolean hasKey = Integer.parseInt(keyNum) > 0 ? true : false;
        // 转换名称
        // 实体对象名称
        String entity = StringUtil.formatDBNameToVarName(tableName);
        // 实体类名称
        String entityClassName = StringUtil.firstBig(entity);
        // dao名称
        String daoName = entityClassName + "Dao";
        String mapperName = entityClassName + "Mapper";
        String fileName = mapperName + ".xml";//生成文件的名称
        // 创建文件输出流
        File file = new File(filePath + File.separator + fileName);
        PrintWriter print = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
        // 用来保存信息
        StringBuilder builder = new StringBuilder();

        // 文件头
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\r\n");
        builder.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").append("\r\n");
        builder.append("<mapper namespace=\"" + daoPackage + "." + daoName + "\">").append("\r\n").append("\r\n");

        //resultMap配置
        builder.append("    <resultMap id=\"BaseResultMap\" type=\"" + entityPackage + "." + entityClassName + "\">").append("\r\n");
        int count = json.size();//字段个数
        for (int i = 0; i < count; i++) {
            JSONObject obj = json.getJSONObject(i);
            // 数据库的列名称
            String columnName = obj.getString("columnName");
            // java类的属性名
            String columnJavaName = obj.getString("columnJavaName");
            // 数据库的列的类型
            String columnType = obj.getString("columnType");
            // 是否是主键
            boolean isPrimaryKey = new Boolean(obj.getString("isPrimaryKey"));
            // true时为主键
            if (isPrimaryKey) {
                builder.append("		<id column=\"" + columnName + "\" property=\"" + columnJavaName + "\" jdbcType=\"" + StringUtil.getJdbcType(columnType) + "\"/>").append("\r\n");
            } else {
                builder.append("		<result column=\"" + columnName + "\" property=\"" + columnJavaName + "\" jdbcType=\"" + StringUtil.getJdbcType(columnType) + "\"/>").append("\r\n");
            }
        }
        builder.append("    </resultMap>").append("\r\n").append("\r\n");

        // sql配置
        builder.append("    <sql id=\"Base_Column_List\">").append("\r\n");
        builder.append("		");
        for (int i = 0; i < count; i++) {
            JSONObject obj = json.getJSONObject(i);
            //数据库的列的名称
            String columnName = obj.getString("columnName");
            builder.append(columnName);
            if (i != count - 1) {
                builder.append(",");
            }
        }
        builder.append("\r\n");
        builder.append("    </sql>").append("\r\n").append("\r\n");

        /**
         * 增加方法
         */
        builder.append("    <insert id=\"insert" + entityClassName + "\" parameterType=\"" + entityPackage + "." + entityClassName + "\" >").append("\r\n");
        builder.append("        insert into " + tableName).append("\r\n");
        builder.append("        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">").append("\r\n");
        for (int i = 0; i < count; i++) {
            JSONObject obj = json.getJSONObject(i);
            // 数据库的列名称
            String columnName = obj.getString("columnName");
            // java类的属性名
            String columnJavaName = obj.getString("columnJavaName");
            builder.append("            <if test=\"" + columnJavaName + " != null \">").append("\r\n");
            builder.append("                " + columnName + ",").append("\r\n");
            builder.append("            </if>").append("\r\n");
        }
        builder.append("        </trim>").append("\r\n");
        builder.append("        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">").append("\r\n");
        for (int i = 0; i < count; i++) {
            JSONObject obj = json.getJSONObject(i);
            // java类的属性名
            String columnNameTf = obj.getString("columnJavaName");
            builder.append("            <if test=\"" + columnNameTf + " != null \">").append("\r\n");
            builder.append("                #{" + columnNameTf + "},").append("\r\n");
            builder.append("            </if>").append("\r\n");
        }
        builder.append("        </trim>").append("\r\n");
        builder.append("    </insert>").append("\r\n").append("\r\n");

        /**
         * 删除方法
         */
        builder.append("    <delete id=\"del" + entityClassName + "\" parameterType=\"" + entityPackage + "." + entityClassName + "\" >").append("\r\n");
        builder.append("        delete from " + tableName).append("\r\n");
        builder.append("        where 1=1").append("\r\n");
        for (int i = 0; i < count; i++) {
            JSONObject obj = json.getJSONObject(i);
            // 数据库的列名称
            String columnName = obj.getString("columnName");
            // java类的属性名
            String columnJavaName = obj.getString("columnJavaName");
            builder.append("            <if test=\"" + columnJavaName + " != null and " + columnJavaName + " != '' \">").append("\r\n");
            builder.append("                and " + columnName + " = #{" + columnJavaName + "}").append("\r\n");
            builder.append("            </if>").append("\r\n");
        }
        builder.append("    </delete>").append("\r\n").append("\r\n");

        /**
         * 修改方法
         */
        if (hasKey) {
            builder.append("    <update id=\"update" + entityClassName + "\" parameterType=\"" + entityPackage + "." + entityClassName + "\" >").append("\r\n");
            builder.append("        update " + tableName).append("\r\n");
            builder.append("        <set>").append("\r\n");
            for (int i = 0; i < count; i++) {
                JSONObject obj = json.getJSONObject(i);
                // 数据库的列名称
                String columnName = obj.getString("columnName");
                // java类的属性名
                String columnJavaName = obj.getString("columnJavaName");
                // 是否是主键
                boolean isPrimaryKey = new Boolean(obj.getString("isPrimaryKey"));
                if (!isPrimaryKey) {
                    builder.append("            <if test=\"" + columnJavaName + " != null\">").append("\r\n");
                    builder.append("                " + columnName + " = #{" + columnJavaName + "} ,").append("\r\n");
                    builder.append("            </if>").append("\r\n");
                }
            }
            builder.append("        </set>").append("\r\n");
            builder.append("        where 1=1").append("\r\n");
            for (int i = 0; i < count; i++) {
                JSONObject obj = json.getJSONObject(i);
                // 数据库的列名称
                String columnName = obj.getString("columnName");
                // java类的属性名
                String columnJavaName = obj.getString("columnJavaName");
                // 是否是主键
                boolean isPrimaryKey = new Boolean(obj.getString("isPrimaryKey"));
                if (isPrimaryKey) {
                    builder.append("            and " + columnName + " = #{" + columnJavaName + "}").append("\r\n");
                }
            }
            builder.append("    </update>").append("\r\n").append("\r\n");
        }

        /**
         * 查询方法
         */
        builder.append("    <select id=\"find" + entityClassName + "\" parameterType=\"" + entityPackage + "." + entityClassName + "\" >").append("\r\n");
        builder.append("        select").append("\r\n");
        builder.append("            <include refid=\"Base_Column_List\" />").append("\r\n");
        builder.append("        from " + tableName + "").append("\r\n");
        builder.append("        <where>").append("\r\n");
        for (int i = 0; i < count; i++) {
            JSONObject obj = json.getJSONObject(i);
            // 数据库的列名称
            String columnName = obj.getString("columnName");
            // java类的属性名
            String columnJavaName = obj.getString("columnJavaName");
            boolean isPrimaryKey = new Boolean(obj.getString("isPrimaryKey"));//是否是主键
            builder.append("            <if test=\"" + columnJavaName + " != null and " + columnJavaName + " != ''\">").append("\r\n");
            builder.append("                and " + columnName + " = #{" + columnJavaName + "}").append("\r\n");
            builder.append("            </if>").append("\r\n");
        }
        builder.append("        </where>").append("\r\n");
        // 增加主键，以字段排序为序

        builder.append("    </select>").append("\r\n").append("\r\n");

        /**
         * 查询所有的方法
         */
        builder.append("    <select id=\"getAll" + entityClassName + "\" >").append("\r\n");
        builder.append("        SELECT").append("\r\n");
        builder.append("            <include refid=\"Base_Column_List\" />").append("\r\n");
        builder.append("        from " + tableName + "").append("\r\n");
        builder.append("    </select>").append("\r\n").append("\r\n");

        builder.append("</mapper>").append("\r\n");
        print.println(builder.toString());
        print.close();

        System.out.println("Entity创建成功");

    }

}
