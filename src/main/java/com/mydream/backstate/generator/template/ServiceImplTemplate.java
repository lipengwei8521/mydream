package com.mydream.backstate.generator.template;

import com.mydream.backstate.response.entity.ResponseBean;
import com.mydream.utils.ConstantString;
import com.mydream.utils.FileUtil;
import com.mydream.utils.StringUtil;
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
 * Date: Created in 2018/8/1016:16
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class ServiceImplTemplate {

    public static void creatServiceImpl(String tableName, String tableComment, String filePath, String charset,
                                        String servicePackage,String daoPackage, String entityPackage,String author) throws Exception{

        // 错误日志
        Logger logger = LoggerFactory.getLogger(ServiceImplTemplate.class);

        // 创建文件夹
        if(!FileUtil.mkdirs(filePath)){

            logger.error("文件夹["+filePath+"创建不成功");
            System.out.println("文件夹["+filePath+"创建不成功");

        }

        // 转换名称，将表名转换为对应的类名,实体对象名称
        String entity= StringUtil.formatDBNameToVarName(tableName);
        // 实体类名
        String entityClassName = StringUtil.firstBig(entity);
        // serviceImpl名称
        String className = entityClassName+"ServiceImpl";
        String fileName = className+".java";

        // 创建文件输出流
        File file = new File(filePath + File.separator+fileName);
        PrintWriter print = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file),charset));

        // 准备内容
        StringBuilder builder = new StringBuilder();
        // 包名
        builder.append("package "+servicePackage+";").append("\r\n").append("\r\n").append("\r\n");

        // 增加import
        builder.append("import com.github.pagehelper.Page;").append("\r\n");
        builder.append("import com.github.pagehelper.PageHelper;").append("\r\n");
        builder.append("import " + daoPackage + "." + entityClassName + "Mapper;").append("\r\n");
        builder.append("import " + entityPackage + "." + entityClassName + ";").append("\r\n");
        builder.append("import com.mydream.utils.PageBean;").append("\r\n");
        builder.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
        builder.append("import org.springframework.stereotype.Service;").append("\r\n").append("\r\n");
        builder.append("import java.util.List;").append("\r\n");

        // 增加类注释
        builder.append("/**").append("\r\n");
        tableComment=tableComment!=null&&!"".equals(tableComment)?tableComment:entityClassName+"操作";
        builder.append(" * "+tableComment).append("\r\n");
        builder.append(" * 对应数据库表"+tableName).append("\r\n");
        builder.append(" * @Author "+author).append("\r\n");
        // 注释中添加创建时间
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.append(" * @Date "+df.format(date) ).append("\r\n");
        builder.append(" */").append("\r\n");
        // 注解
        builder.append("@Service").append("\r\n");
        // 类名
        builder.append("public class " + entityClassName + "ServiceImpl implements " + entityClassName + "Service {").append("\r\n").append("\r\n");

        builder.append("    @Autowired").append("\r\n");
        builder.append("    private " + entityClassName + "Mapper " + entity + "Mapper;").append("\r\n").append("\r\n");

        /**
         * 新增方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 新增" + entityClassName).append("\r\n");
        builder.append("     * @param " + entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    @Override").append("\r\n");
        builder.append("    public void insert" + entityClassName + "(" + entityClassName + " " + entity + ") {").append("\r\n").append("\r\n");
        builder.append("        " + entity + "Mapper.insert" + entityClassName + "(" + entity + ");").append("\r\n").append("\r\n");
        builder.append("    }").append("\r\n").append("\r\n");

        /**
         * 删除方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 删除" + entityClassName).append("\r\n");
        builder.append("     * @param " + entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    @Override").append("\r\n");
        builder.append("    public void del" + entityClassName + "(" +entityClassName + " " + entity + ") {").append("\r\n").append("\r\n");
        builder.append("        " + entity + "Mapper.del" + entityClassName + "(" + entity + ");").append("\r\n").append("\r\n");
        builder.append("    }").append("\r\n").append("\r\n");

        /**
         * 修改方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 修改" + entityClassName).append("\r\n");
        builder.append("     * @param "+ entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    @Override").append("\r\n");
        builder.append("    public void update" + entityClassName + "(" + entityClassName + " " + entity + ") {").append("\r\n").append("\r\n");
        builder.append("        " + entity + "Mapper.update" + entityClassName + "(" + entity + ");").append("\r\n").append("\r\n");
        builder.append("    }").append("\r\n").append("\r\n");

        /**
         * 查询方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 查询"+entityClassName).append("\r\n");
        builder.append("     * @param currentPage").append("\r\n");
        builder.append("     * @param pageSize").append("\r\n");
        builder.append("     * @param " + entity).append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    @Override").append("\r\n");
        builder.append("    public PageBean<" + entityClassName + "> find" + entityClassName + "(int currentPage, int pageSize, " + entityClassName)
               .append(" " + entity + ") {").append("\r\n").append("\r\n");
        builder.append("        // 发现sqlmapper.xml中if不能识别null，所以需要把查询条件做null判断").append("\r\n").append("\r\n");
        builder.append("        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】").append("\r\n");
        builder.append("        Page<"+entityClassName+"> tPagee = PageHelper.startPage(currentPage, pageSize);").append("\r\n");
        builder.append("        // 全部的数据库连接信息").append("\r\n");
        builder.append("        List<" + entityClassName + "> " + entity + "List = " + entity + "Mapper.find" + entityClassName + "(" + entity + ");").append("\r\n");
        builder.append("        //统计数据库" + entityClassName + "的数量").append("\r\n");
        builder.append("        int count = (int)tPagee.getTotal();");
        builder.append("        PageBean<" + entityClassName + "> pageData = new PageBean(currentPage,pageSize,count);").append("\r\n");
        builder.append("        pageData.setItems(" + entity + "List);").append("\r\n");
        builder.append("        return pageData;").append("\r\n").append("\r\n");
        builder.append("    }").append("\r\n").append("\r\n");
        builder.append("}").append("\r\n");

        print.println(builder.toString());
        print.close();

        System.out.println("serviceImpl生成成功");

    }
}
