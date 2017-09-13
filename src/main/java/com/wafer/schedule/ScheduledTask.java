package com.wafer.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTask {

  Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

  public static final long DAY_TIME_OF_7 = 7 * 24 * 60 * 60 * 1000;

  /**
   * 每天删除上一天产生的所有文件
   */
  @Scheduled(cron = "0 0 0 0/1 * ?")
  public void scheduledJobTest() {
    logger.debug("时间到了，{}", new Date());
  }

}
