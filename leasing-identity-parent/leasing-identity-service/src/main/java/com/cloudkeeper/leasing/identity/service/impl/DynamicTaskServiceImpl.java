package com.cloudkeeper.leasing.identity.service.impl;

import com.cloudkeeper.leasing.base.model.Result;
import com.cloudkeeper.leasing.identity.service.DynamicTaskService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DynamicTaskServiceImpl implements DynamicTaskService {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future1;

    private Map<String,ScheduledFuture<?>> timeMap = new HashMap<>();
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        return threadPoolTaskScheduler;
    }





    public Result startCron1(String idStr) {
        timeMap.put(idStr,scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Calendar cal=Calendar.getInstance();

                //用Calendar类提供的方法获取年、月、日、时、分、秒
                int year  =cal.get(Calendar.YEAR);   //年
                int month =cal.get(Calendar.MONTH)+1;  //月  默认是从0开始  即1月获取到的是0
                int day   =cal.get(Calendar.DAY_OF_MONTH);  //日，即一个月中的第几天
                int hour  =cal.get(Calendar.HOUR_OF_DAY);  //小时
                int minute=cal.get(Calendar.MINUTE);   //分
                int second=cal.get(Calendar.SECOND);  //秒

                //拼接成字符串输出
                String date=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;

                System.out.println("beep" + date);
            }
        }, 0, 10, TimeUnit.SECONDS));

        System.out.println("DynamicTask.startCron1()");
        System.out.println(timeMap);
        return Result.of(1);

    }

    public Result stopCron1(String idStr) {
        System.out.println(timeMap.get(idStr));
        if (timeMap.get(idStr) != null) {
            timeMap.get(idStr).cancel(true);
        }
        System.out.println("DynamicTask.stopCron1()");
        return Result.of(2);
    }

}
