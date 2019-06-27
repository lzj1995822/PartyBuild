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

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        final Runnable beeper = new Runnable() {
            public void run() {
                System.out.println("beep");
            }
        };


    public Result startCron1(String idStr) {
        timeMap.put(idStr,scheduler.scheduleAtFixedRate(beeper, 0, 10, TimeUnit.SECONDS));

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
