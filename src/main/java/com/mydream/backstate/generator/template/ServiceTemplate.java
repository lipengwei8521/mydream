package com.mydream.backstate.generator.template;

import com.mydream.backstate.response.entity.ResponseBean;
import com.mydream.utils.ConstantString;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Package: com.mydream.backstate.generator.template
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/1016:15
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class ServiceTemplate {

    public static void creatService(String tableName, String tableComment, String filePath, String charset,
                            String entityPackage, String servicePackage,String author) throws Exception{

        // 错误日志
        Logger logger = LoggerFactory.getLogger(ServiceTemplate.class);

        // 创建文件夹
        if(!FileUtil.mkdirs(filePath)){

            logger.error("文件夹["+filePath+"创建不成功");
            System.out.println("文件夹["+filePath+"创建不成功");

        }

        // 转换名称，将表名转换为对应的类名, 实体对象名称
        String entity=StringUtil.formatDBNameToVarName(tableName);

        // 实体类名
        String entityClassName = StringUtil.firstBig(entity);

        // service名称
        String className = entityClassName+"Service";
        String fileName = className+".java";

        // 创建文件输出流
        File file = new File(filePath + File.separator+fileName);
        PrintWriter print = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file),charset));

        // 存放内容
        StringBuilder builder = new StringBuilder();

        // 包名
        builder.append("package " + servicePackage + ";").append("\r\n").append("\r\n");

        // 添加import
        builder.append("import " + entityPackage + "." + entityClassName + ";").append("\r\n");
        builder.append("import com.mydream.utils.PageBean;").append("\r\n").append("\r\n");

        // 添加类的注释
        builder.append("/**").append("\r\n");
        if(!"".equals(tableComment)){
            builder.append(" * "+tableComment).append("\r\n");
        }
        builder.append(" * " + entityClassName + "的业务处理层").append("\r\n");
        builder.append(" * @Author "+author).append("\r\n");
        // 注释中添加创建时间
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.append(" * @Date "+df.format(date) ).append("\r\n");
        builder.append(" */").append("\r\n");

        //添加类
        builder.append("public interface " + className + " {").append("\r\n").append("\r\n");
        builder.append("").append("\r\n");

        /*
         * 添加方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 新增" + tableComment).append("\r\n");
        builder.append("     * @param " + entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    void insert"+entityClassName+"("+entityClassName+" "+entity+"); ").append("\r\n").append("\r\n");

        /**
         * 删除方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 删除" + tableComment).append("\r\n");
        builder.append("     * @param "+entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    void del" + entityClassName + "(" + entityClassName + " " + entity + ");").append("\r\n").append("\r\n");

        /**
         * 更新方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 更新" + tableComment).append("\r\n");
        builder.append("     * @param " + entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    void update" + entityClassName + "(" + entityClassName + " " + entity + ");").append("\r\n").append("\r\n");

        /**
         * 获取信息
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 查询"+tableComment+"信息").append("\r\n");
        builder.append("     * @param currentPage 当前页数").append("\r\n");
        builder.append("     * @param pageSize 每页显示的数量").append("\r\n");
        builder.append("     * @param " + entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    PageBean<" + entityClassName + "> find" + entityClassName + "(int currentPage,")
        .append(" int pageSize, " + entityClassName + " " + entity + ");").append("\r\n").append("\r\n");

        builder.append("}").append("\r\n");
        print.println(builder.toString());
        print.close();

        System.out.println("创建service成功");

    }

}
