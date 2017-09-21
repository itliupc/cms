package com.wafer.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.wafer.service.CarService;

@Configuration
@EnableScheduling
public class ScheduledTask {

  @Autowired
  CarService carService;

  Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

  /**
   * 每天4点执行任务
   */
  @Scheduled(cron = "0 0 4 0/1 * ?")
  public void scheduledJobTest() {
    logger.info("时间到了！");
  }

}
