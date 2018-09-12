package com.mydream.backstate.testhtml;

import com.mydream.backstate.response.entity.ResponseBean;
import com.mydream.utils.ConstantString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Package: com.mydream.backstate.testhtml
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/1710:53
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
@Controller
@RequestMapping("/testhtml")
public class TestHtmlController {

    @RequestMapping("error")
    public String error(Model model){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setState(ConstantString.SUCCESS);
        responseBean.setMes("zheshi ceshi yemian");
        responseBean.setResData("正确错误正确错误");
//        model.addAttribute("responseBean",responseBean);
        return "error/error/";
    }
    

    @RequestMapping("success")
    public String success(){

        return "success/success/";
    }
    @RequestMapping("log")
    public String log(){
        return "logs/log";
    }
    @RequestMapping("thelog")
    public String theLog(String date){
        return "logs/MyDream.log."+date;
    }
}
