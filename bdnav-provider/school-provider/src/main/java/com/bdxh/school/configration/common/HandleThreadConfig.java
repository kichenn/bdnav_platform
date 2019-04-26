package com.bdxh.school.configration.common;


import java.util.List;

/**
 * @Description: 集合多线程处理
 * @Author: Kang
 * @Date: 2019/4/25 11:53
 */
public class HandleThreadConfig implements Runnable {

    /**
     * 线程名称
     */
    private String threadName;
    /**
     * 总集合
     */
    private List<String> data;
    /**
     * 处理的集合的开始下标
     */
    private int start;
    /**
     * 处理集合的结束下标
     */
    private int end;

    public HandleThreadConfig(String threadName, List<String> data, int start, int end) {
        this.threadName = threadName;
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        List<String> subList = data.subList(start, end)/*.add("^&*")*/;
        System.out.println(threadName + "处理了" + subList.size() + "条！");
    }
}
