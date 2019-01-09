package com.bdxh.common.utils;

/**
 * @description: 线性hash工具类
 * @author: xuyuan
 * @create: 2019-01-09 13:21
 **/
public class LinearHashUtils {

    public static int getLinearHashCode(int partition,int data){
        int tmp= (int) Math.ceil(Math.log(partition)/Math.log(2));
        int V = (int)Math.pow(2,tmp);
        int N = data & (V - 1);
        for (;N>=partition;){
            V = (int)Math.ceil(V / 2);
            N = N & (V-1);
        }
        return N;
    }

}
