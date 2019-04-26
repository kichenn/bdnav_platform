package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.bdxh.school.configration.common.HandleThreadConfig;
import org.apache.commons.lang3.ArrayUtils;

public class Test {
    /**
     * 多线程处理list
     *
     * @param data      数据list
     * @param threadNum 线程数
     */
    public synchronized void handleList(List<String> data, int threadNum) {
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
        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * tl;
            HandleThreadConfig thread = new HandleThreadConfig("线程[" + (i + 1) + "] ", data, i * tl, end > length ? length : end);
            executor.execute(thread);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }


    public static void main(String[] args) {
        Test test = new Test();
        // 准备数据
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 6666; i++) {
            data.add("item" + i);
        }
        test.handleList(data, 15);
    }
}