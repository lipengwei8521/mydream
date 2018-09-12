package com.mydream.backstate.generator.template;

import com.mydream.utils.FileUtil;
import com.mydream.utils.StringUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Package: com.mydream.backstate.generator.template
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/1016:16
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class MapperTemplate {


    /**
     * 生成dao类
     *
     * @param tableName     表名
     * @param tableComment  字段名称
     * @param filePath      文件路径
     * @param charset       编码格式
     * @param daoPackage    dao类的路径
     * @param author        作者名称
     * @param entityPackage entity类的路径
     * @param json          java类属性的信息
     * @param keyNum        主键的个数
     * @param author        作者名称
     * @return
     * @throws Exception
     */
    public static void creatDao(String tableName, String tableComment, String filePath, String charset,
                                String daoPackage, String entityPackage, JSONArray json, String keyNum, String author) throws Exception {

        // 错误日志
        Logger logger = LoggerFactory.getLogger(MapperTemplate.class);

        // 创建文件夹
        if (!FileUtil.mkdirs(filePath)) {

            logger.error("文件夹[" + filePath + "创建不成功");
            System.out.println("文件夹[" + filePath + "创建不成功");

        }
        boolean hasKey = Integer.parseInt(keyNum) > 0 ? true : false;
        // 转换dao名称
        // 实体对象名称
        String entity = StringUtil.formatDBNameToVarName(tableName);
        // 实体类名称
        String entityClassName = StringUtil.firstBig(entity);
        // dao名称
        String className = entityClassName + "Mapper";
        // 生成文件的名称
        String fileName = className + ".java";
        // 创建文件输出流
        File file = new File(filePath + File.separator + fileName);
        PrintWriter print = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
        // 用来保存信息
        StringBuilder builder = new StringBuilder();

        //包名
        builder.append("package " + daoPackage + ";").append("\r\n");

        //增加import
        builder.append("import java.util.List;").append("\r\n");
        builder.append("import " + entityPackage + "." + entityClassName + ";").append("\r\n");

        //增加类注释
        builder.append("/**").append("\r\n");
        tableComment = tableComment != null && !"".equals(tableComment) ? tableComment : entityClassName + "操作";
        builder.append(" * " + tableComment).append("\r\n");
        builder.append(" * " + entityClassName + "的数据持久层").append("\r\n");
        builder.append(" * 对应数据库表" + tableName).append("\r\n");
        builder.append(" * @Author " + author).append("\r\n");
        // 注释中添加创建时间
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.append(" * @Date " + df.format(date)).append("\r\n");
        builder.append(" */").append("\r\n");

        //增加类名
        builder.append("public interface " + className + "{").append("\r\n");

        /**
         * 新增方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 新增" + entityClassName).append("\r\n");
        builder.append("     * @param " + entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    void insert" + entityClassName + "(" + entityClassName + " " + entity + ");").append("\r\n").append("\r\n");

        /**
         * 获取所有信息方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 查询所有" + entityClassName + "信息").append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    List<" + entityClassName + "> getAll" + entityClassName + "();").append("\r\n").append("\r\n");

        /**
         * 根据查询信息查询信息方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 根据信息查询" + entityClassName + "信息").append("\r\n");
        builder.append("     * @param " + entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    List<" + entityClassName + "> find" + entityClassName + "(" + entityClassName + " " + entity + ");").append("\r\n").append("\r\n");

        /**
         * 删除方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 删除" + entityClassName + "信息").append("\r\n");
        builder.append("     * @param " + entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    void del" + entityClassName + "(" + entityClassName + " " + entity + ");").append("\r\n").append("\r\n");

        /**
         * 更新方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 更新" + entityClassName + "信息").append("\r\n");
        builder.append("     * @param " + entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    void update" + entityClassName + "(" + entityClassName + " " + entity + ");").append("\r\n").append("\r\n");

        builder.append("}").append("\r\n");
        print.println(builder.toString());
        print.close();

        System.out.println("创建Dao成功!");

    }

}
