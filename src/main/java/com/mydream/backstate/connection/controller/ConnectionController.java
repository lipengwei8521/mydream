package com.mydream.backstate.connection.controller;

import com.mydream.backstate.connection.entity.Connection;
import com.mydream.backstate.connection.service.ConnectionService;
import com.mydream.backstate.response.entity.ResponseBean;
import com.mydream.utils.ConnectUtil;
import com.mydream.utils.ConstantString;
import com.mydream.utils.PageBean;
import com.mydream.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *  * 有关数据库连接信息的控制层
 *
 * Created by  lpw'ASUS on 2018/7/27.
 */
@Controller
@RequestMapping("/connection")
public class ConnectionController {
    /**
     * logger日志
     */
    private static Logger logger = LoggerFactory.getLogger(ConnectionController.class);

    @Autowired
    private ConnectionService connectionService;

    /**
     * 跳转到connection_list.html
     *
     * @return
     */
    @RequestMapping("toConnection")
    public String toConnection(Model model,Connection connection,Integer currentPage){

        try{
        	

           

            // 默认每页显示的数据条数
            int pageSize = 2;
            PageBean<Connection> pageBean = connectionService.findConnection(currentPage,pageSize,connection);
            model.addAttribute("pageBean",pageBean);
            model.addAttribute("connection",connection);
            return "connection/connection_list";

        } catch (Exception e){

            ResponseBean responseBean = new ResponseBean();
            responseBean.setMes("查询用户信息失败！");
            responseBean.setState("0");
            responseBean.setResData(e.getMessage());
            model.addAttribute("responseBean",responseBean);
            return "error/error";

        }

    }

    /**
     * 跳转到connection_form.html
     *
     * @return
     */
    @RequestMapping("toConnectionForm")
    public String toConnectionForm(){
        return "connection/connection_form";
    }

    /**
     * 判断连接名称是否被占用
     *
     * @param conname
     * @return
     */
//    @RequestMapping("isConName")
//    @ResponseBody
//    public String isConName(@RequestParam("conname") String conname){
//
//        ResponseBean responseBean = new ResponseBean();
//
//        try{
//
//            Boolean isConName = connectionService.isConName(conname);
//
//            if(isConName){
//
//                return "{'valid',true}";
//
//            }else {
//
//                return "{'valid',false}";
//
//            }
//        }catch (Exception e){
//
//            return "{'valid',false}";
//        }
//
//    }
//
//    /**
//     * 测试是否可以成功连接到数据库
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping("isConnect")
//    @ResponseBody
//    public String isConnect(HttpServletRequest request){
//
//        // 返回信息实体类
//        ResponseBean responseBean = new ResponseBean();
//
//        String conname = request.getParameter("conname");
//        String con_dbName = request.getParameter("con_dbName");
//        // 1 Mysql，2 Oracle,
//        String con_type = request.getParameter("contype");
//        String con_Ip = request.getParameter("conip");
//        String con_port = request.getParameter("conport");
//        String con_username = request.getParameter("conusername");
//        String con_password = request.getParameter("conpassword");
//
//        // 连接数据库驱动
//        String dataBaseDriver = "";
//        String dataBaseUrl = "";
//        // 连接数据库的用户名
//        String dataBaseUsername = con_username;
//        // 连接数据库密码
//        String dataBasePassword = con_password;
//
//        if("1".equals(con_type)){
//            dataBaseDriver ="com.mysql.jdbc.Driver";
//            dataBaseUrl = "jdbc:mysql://"+con_Ip+":"+con_port+"/"+con_dbName;
//        }
//
//        try{
//
//            Boolean isConName = connectionService.isConName(conname);
//
//            // isConName =true,表示连接名称未被占用
//            if(isConName){
//                java.sql.Connection connection = ConnectUtil.getConnection(dataBaseDriver,dataBaseUrl,dataBaseUsername,dataBasePassword);
//                if(connection == null){
//                    responseBean.setState(ConstantString.FAILE);
//                    responseBean.setMes("连接信息有误，请重新确认！");
//                }else{
//                    responseBean.setState(ConstantString.SUCCESS);
//                }
//            }else {
//                responseBean.setState(ConstantString.FAILE);
//                responseBean.setMes("连接名称已被占用请重新填写！");
//            }
//
//        }catch (Exception e){
//
//            e.printStackTrace();
//
//            StringWriter sw=new StringWriter();
//            e.printStackTrace(new PrintWriter(sw,true));
//            logger.error("isConnect 的错误信息：{}",sw.toString());
//
//            responseBean.setState(ConstantString.FAILE);
//            responseBean.setMes("系统异常，请查看日志");
//        }
//
//        return ResponseUtil.getJsonResponseBean(responseBean);
//
//    }
//
    /**
     * 新增数据库连接信息
     *
     * @param request
     * @return
     */
    @RequestMapping("insertConnection")
    public String insertConnection(HttpServletRequest request,Model model){

        // 连接名称
        String conname = request.getParameter("conname");
        // 数据库名称
        String con_dbName = request.getParameter("condbname");
        // 连接类型
        String con_type = request.getParameter("contype");
        // IP 地址
        String con_Ip = request.getParameter("conip");
        // 端口
        String con_port = request.getParameter("conport");
        // 用户名
        String con_username = request.getParameter("conusername");
        // 密码
        String con_password = request.getParameter("conpassword");

        try {

            Connection connection = new Connection(conname,con_type,con_Ip,con_port,con_username,con_password,con_dbName);

            connectionService.insertConnection(connection);
            return toConnection(model,new Connection(),1);

        }catch (Exception e){

            e.printStackTrace();

            ResponseBean responseBean = new ResponseBean();
            responseBean.setMes("添加连接数据库信息失败！");
            responseBean.setState(ConstantString.FAILE);
            responseBean.setResData(e.getMessage());
            model.addAttribute("responseBean",responseBean);
            return "error/error";

        }

    }

    /**
     * 删除链接信息
     * @param conid
     * @param model
     * @return
     */
    @RequestMapping("delConnection")
    public String delConnection(Integer conid,Model model){

        ResponseBean responseBean = new ResponseBean();

        try{

            connectionService.delConnection(conid);
            responseBean.setState(ConstantString.SUCCESS);
            responseBean.setMes("删除连接数据库信息成功！");

        }catch (Exception e){

            responseBean.setMes("删除连接数据库信息失败！");
            responseBean.setState(ConstantString.FAILE);
            responseBean.setResData(e.getMessage());

            return "error/error";
        }

        model.addAttribute("responseBean",responseBean);
        return toConnection(model,new Connection(),1);

    }

}
