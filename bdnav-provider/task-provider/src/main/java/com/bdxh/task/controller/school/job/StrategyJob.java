package com.bdxh.task.controller.school.job;

import com.bdxh.task.controller.strategy.StrategyToPush;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: Quartz学校策略定时任务执行类
 * @Author: Kang
 * @Date: 2019/5/16 10:21
 */
public class StrategyJob implements Job {

    /**
     * 执行逻辑。
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String result = jobExecutionContext.getMergedJobDataMap().getString("site");
        //打印当前的执行时间
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("result:" + result + "现在的时间是：" + sf.format(date));
        //具体的业务逻辑
        //StrategyToPush.pushStrategy(String schoolCode,Long );
        //更改策略状态
        System.out.println("Hello Quartz");
    }
}
