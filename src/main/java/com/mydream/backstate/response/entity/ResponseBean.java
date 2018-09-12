package com.mydream.backstate.response.entity;

/**
 * Created by  lpw'ASUS on 2018/7/23.
 */
public class ResponseBean {

    // 返回提示信息
    private String mes;
    // 返回状态，成功或失败
    private String state;
    // 返回数据信息
    private Object resData;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getResData() {
        return resData;
    }

    public void setResData(Object resData) {
        this.resData = resData;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "mes='" + mes + '\'' +
                ", state='" + state + '\'' +
                ", resData='" + resData + '\'' +
                '}';
    }
}
