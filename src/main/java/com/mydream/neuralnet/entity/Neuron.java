package com.mydream.neuralnet.entity;

import java.util.ArrayList;

/**
 * Package: com.mydream.neuralnet.entity
 * Description: TODO
 * Author: 李鹏伟
 * Date: Created in 2018/8/1922:14
 * Company: 公司
 * Copyright: Copyright (C) 2018
 * Version: 0.0.1
 * Modified By: 修改者
 */
public class Neuron {

    // 一个实数ArrayList变量，代表输入权值的集合
    private ArrayList<Double> listOfWeightIn;
    // 一个实数ArrayList变量。代表输出权值的集合；
    private ArrayList<Double> listOfWeightOut;

    // 用伪随机数初始化listOfWeightIn和 listOfWeightIn
    public double initNeuron(){
        return Math.random();
    }


}
