package com.bdxh.school.thread;


import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.baidu.yingyan.FenceUtils;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.helper.baidu.yingyan.request.CreateNewEntityRequest;
import com.bdxh.school.dto.FenceEntityDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AddFenceEntityThread {

    public synchronized void handleList(List<FenceEntityDto> data, int threadNum) {
        /**
         * corePoolSize：核心池的大小，当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
         * maximumPoolSize：线程池最大线程数它表示在线程池中最多能创建多少个线程；
         * keepAliveTime：表示线程没有任务执行时最多保持多久时间会终止。默认情况下，只有当线程池中的线程数大于corePoolSize时，keepAliveTime才会起作用，直到线程池中的线程数不大于corePoolSize，即当线程池中的线程数大于corePoolSize时，如果一个线程空闲的时间达到keepAliveTime，则会终止，直到线程池中的线程数不超过corePoolSize。但是如果调用了allowCoreThreadTimeOut(boolean)方法，在线程池中的线程数不大于corePoolSize时，keepAliveTime参数也会起作用，直到线程池中的线程数为0；
         * unit：参数keepAliveTime的时间单位，有7种取值，在TimeUnit类中有7种静态属性：
         * workQueue：一个阻塞队列，用来存储等待执行的任务
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5));
        int length = data.size();
        int tl = length % threadNum == 0 ? (length / threadNum) : (length / threadNum + 1);
        threadNum = threadNum >= data.size() ? data.size() : threadNum;
        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * tl;
            HandleThread thread = new HandleThread("线程[" + (i + 1) + "] ", data, i * tl, end > length ? length : end);
            executor.execute(thread);
//            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
//                    executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }


    /**
     * @Description: 集合多线程处理
     * @Author: Kang
     * @Date: 2019/4/25 11:53
     */
    class HandleThread implements Runnable {

        /**
         * 线程名称
         */
        private String threadName;
        /**
         * 总集合
         */
        private List<FenceEntityDto> data;
        /**
         * 处理的集合的开始下标
         */
        private int start;
        /**
         * 处理集合的结束下标
         */
        private int end;

        public HandleThread(String threadName, List<FenceEntityDto> data, int start, int end) {
            this.threadName = threadName;
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            List<FenceEntityDto> subList = data.subList(start, end);
            subList.forEach(e -> {
                //增加监控终端实体
                CreateNewEntityRequest entityRequest = new CreateNewEntityRequest();
                entityRequest.setAk(FenceConstant.AK);
                entityRequest.setService_id(FenceConstant.SERVICE_ID);
                entityRequest.setEntity_name(e.getId() + "_" + e.getName());
                entityRequest.setEntity_desc("");
                String entityResult = FenceUtils.createNewEntity(entityRequest);
                JSONObject entityJson = JSONObject.parseObject(entityResult);
                //此错误可以忽略，只是证明该监控实体是不是在百度鹰眼那
                if (entityJson.getInteger("status") != 0) {
                    log.error("增加监控终端实体失败，类型: " + e.getGroupTypeEnum().getValue() + "组名称:" + e.getClassName() + "，名称：" + e.getName() + "，失败,状态码" + entityJson.getInteger("status") + "，原因:" + entityJson.getString("message"));
                } else {
                    System.out.println(threadName + "增加监控终端实体成功:" + entityResult);
                }
            });
            System.out.println(threadName + "处理了" + subList.size() + "条！");
        }
    }
}

