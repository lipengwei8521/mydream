package com.mydream.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydream.backstate.response.entity.*;

/**
 * Created by  lpw'ASUS on 2018/7/27.
 */
public class ResponseUtil {

    public static String getJsonResponseBean(ResponseBean responseBean){
        try {
            ObjectMapper mapper = new ObjectMapper();
          return mapper.writeValueAsString(responseBean);

        } catch (Exception e) {
            e.printStackTrace();
            return "转化失败";
        }
    }

}
