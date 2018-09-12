package com.mydream.backstate.generator.template;

import com.mydream.backstate.response.entity.ResponseBean;
import com.mydream.utils.ConstantString;
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
 * Date: Created in 2018/8/1016:15
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class ControllerTemplate {

    public static void creatControoller(String tableName, String tableComment, String filePath, String charset,
                                                String controllerPackage, String servicePackage, String entityPackage,
                                                String author) throws Exception{

        // 错误日志
        Logger logger = LoggerFactory.getLogger(ControllerTemplate.class);

        // 创建文件夹
        if(!FileUtil.mkdirs(filePath)){

            logger.error("文件夹["+filePath+"创建不成功");
            System.out.println("文件夹["+filePath+"创建不成功");

        }

        // 转换实体类名称,把名字转换为首字母小写的内容
        String sClassName = StringUtil.formatDBNameToVarName(tableName);
        String className = StringUtil.firstBig(sClassName);
        String fileName = className+"Controller.java";//生成文件的名称
        // 创建文件输出流
        File entityFile = new File(filePath + File.separator+fileName);
        PrintWriter print = new PrintWriter(new OutputStreamWriter(new FileOutputStream(entityFile),charset));

        // 用来保存信息
        StringBuilder builder = new StringBuilder();

        // 包名
        builder.append("package "+controllerPackage+";").append("\r\n").append("\r\n");

        // 导入的jar包
        builder.append("import " + entityPackage + "."+className + ";").append("\r\n");
        builder.append("import " + servicePackage + "."+className + "Service;").append("\r\n");
        builder.append("import com.mydream.backstate.response.entity.ResponseBean;").append("\r\n");
        builder.append("import com.mydream.utils.ConstantString;").append("\r\n");
        builder.append("import com.mydream.utils.PageBean;").append("\r\n");
        builder.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
        builder.append("import org.springframework.stereotype.Controller;").append("\r\n");
        builder.append("import org.springframework.ui.Model;").append("\r\n");
        builder.append("import org.springframework.web.bind.annotation.RequestMapping;").append("\r\n");

        // 增加类注释
        builder.append("/**").append("\r\n");
        tableComment=tableComment!=null&&!"".equals(tableComment)?tableComment:"";
        if(!"".equals(tableComment)){
            builder.append(" * "+tableComment).append("\r\n");
        }
        builder.append(" * " + className + "的控制层").append("\r\n");
        builder.append(" * @Author "+author).append("\r\n");
        // 注释中添加创建时间
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.append(" * @Date "+df.format(date) ).append("\r\n");
        builder.append(" */").append("\r\n");

        // 添加控制层类注解
        builder.append("@Controller").append("\r\n");
        builder.append("@RequestMapping(\"/" + sClassName +"\")" ).append("\r\n");

        // 添加控制层类
        builder.append("public class " + className + "Controller {").append("\r\n").append("\r\n");

        // 引入service层
        builder.append("    @Autowired").append("\r\n");
        builder.append("    private " + className + "Service " + sClassName + "Service;").append("\r\n").append("\r\n");

        /**
         * 跳转到list页面的方法
         */
        // 添加方法注释
        builder.append("    /**").append("\r\n");
        builder.append("     * 跳转到list页面").append("\r\n");
        builder.append("     */").append("\r\n");

        // 添加方法注解
        builder.append("    @RequestMapping(\"to" + className + "\")").append("\r\n");

        // 跳转到list页面方法
        builder.append("    public String to" + className + " (Model model, " + className + " " + sClassName + ", ")
                .append("Integer currentPage) {").append("\r\n").append("\r\n");
        builder.append("            ResponseBean responseBean = new ResponseBean();").append("\r\n").append("\r\n");
        builder.append("        try{").append("\r\n").append("\r\n");
        builder.append("            if(\"\".equals(currentPage) || currentPage == null){").append("\r\n");
        builder.append("                currentPage = 1;").append("\r\n");
        builder.append("            }").append("\r\n").append("\r\n");
        builder.append("            // 默认每页显示的数据条数").append("\r\n");
        builder.append("            int pageSize = 2;").append("\r\n");
        builder.append("            PageBean<" + className + "> pageBean = " + sClassName + "Service.find" + className + "(currentPage, pageSize, ")
                .append(sClassName + ");").append("\r\n");
        builder.append("            model.addAttribute(\"pageBean\",pageBean);").append("\r\n");
        builder.append("            model.addAttribute(\"" + sClassName + "\"," + sClassName + ");").append("\r\n");
        builder.append("            return \"" + sClassName + "/" + sClassName + "_list\";").append("\r\n").append("\r\n");
        builder.append("        } catch (Exception e){").append("\r\n").append("\r\n");

        builder.append("            responseBean.setMes(\"查询" + sClassName + "信息失败！\");").append("\r\n");
        builder.append("            return \"error/error\";").append("\r\n").append("\r\n");
        builder.append("        }").append("\r\n").append("\r\n");
        builder.append("    }").append("\r\n").append("\r\n");

        /**
         * 跳转到新增页面的方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 跳转到新增页面").append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    @RequestMapping(\"to" + className + "Form\")").append("\r\n");
        builder.append("    public String to" + className + "Form(){").append("\r\n").append("\r\n");
        builder.append("        return \"" + sClassName + "/" + sClassName + "_form\";").append("\r\n").append("\r\n");
        builder.append("    }").append("\r\n").append("\r\n");

        /**
         * 新增方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 新增" + className + "信息").append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    @RequestMapping(\"insert" + className + "\")").append("\r\n");
        builder.append("    public String insert" + className + "(" + className + " " + sClassName + ", Model model){").append("\r\n").append("\r\n");
        builder.append("            ResponseBean responseBean = new ResponseBean();").append("\r\n").append("\r\n");
        builder.append("        try {").append("\r\n").append("\r\n");
        builder.append("            " + sClassName + "Service.insert" + className + "(" + sClassName + ");").append("\r\n");
        builder.append("            return to" + className + "(model, new " + className + "(), 1);").append("\r\n").append("\r\n");
        builder.append("        }catch (Exception e){").append("\r\n").append("\r\n");
        builder.append("            responseBean.setState(ConstantString.FAILE);").append("\r\n");
        builder.append("            responseBean.setMes(\"添加" + tableComment + "信息失败！\");").append("\r\n");
        builder.append("            responseBean.setResData(e.getMessage());").append("\r\n");
        builder.append("            model.addAttribute(\"responseBean\",responseBean);").append("\r\n");
        builder.append("            return \"error/error\";").append("\r\n").append("\r\n");
        builder.append("        }").append("\r\n").append("\r\n");
        builder.append("    }").append("\r\n").append("\r\n");

        /*
         * 删除方法
         */
        builder.append("    /**").append("\r\n");
        builder.append("     * 删除" + className + "信息").append("\r\n");
        builder.append("     */").append("\r\n");
        builder.append("    @RequestMapping(\"del" + className + "\")").append("\r\n");
        builder.append("    public String del" + className + "(" + className + " "+ sClassName + ", Model model){").append("\r\n").append("\r\n");
        builder.append("        ResponseBean responseBean = new ResponseBean();").append("\r\n").append("\r\n");
        builder.append("        try{").append("\r\n").append("\r\n");
        builder.append("            " + sClassName + "Service.del" + className + "(" + sClassName + ");").append("\r\n");
        builder.append("            responseBean.setState(ConstantString.SUCCESS);").append("\r\n");
        builder.append("            responseBean.setMes(\"删除" + tableComment + "信息成功！\");").append("\r\n");
        builder.append("            model.addAttribute(\"responseBean\",responseBean);").append("\r\n");
        builder.append("            return to" + className + "(model, new " + className + "(), 1);").append("\r\n").append("\r\n");
        builder.append("        }catch (Exception e){").append("\r\n").append("\r\n");
        builder.append("            responseBean.setState(ConstantString.FAILE);").append("\r\n");
        builder.append("            responseBean.setMes(\"删除" + tableComment + "信息失败！\");").append("\r\n");
        builder.append("            responseBean.setResData(e.getMessage());").append("\r\n");
        builder.append("            model.addAttribute(\"responseBean\",responseBean);").append("\r\n");
        builder.append("            return \"error/error\";").append("\r\n").append("\r\n");
        builder.append("        }").append("\r\n").append("\r\n");


        builder.append("    }").append("\r\n").append("\r\n");

        builder.append("}").append("\r\n");

        print.println(builder.toString());
        print.close();

        System.out.println("Controller创建成功");
    }

}
