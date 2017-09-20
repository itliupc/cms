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
   * 每天删除上一天产生的垃圾数据
   */
  @Scheduled(cron = "0 0 4 0/1 * ?")
  public void scheduledJobTest() {
    /*List<Object[]> list = carService.getDateBySql();
    List<Long> ids = new ArrayList<Long>();
    for (Object[] obj : list) {
      ids.add(Long.valueOf(obj[0].toString()));
    }
    if (ids.size() > 0) {
      carService.deleteCarByIds(ids);
    }
    logger.info("清理垃圾数据,共{}条.", list.size());*/
  }

}
