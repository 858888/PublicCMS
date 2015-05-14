package com.sanluan.cms.logic.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhangxd
 * 
 */
@Component
public class ScheduledTasks {
	protected Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * @[*] 表示所有值 [?] 表示不指定值 [-] 表示区间[,] 表示指定多个值[/] 用于递增触发[L]
	 *    表示最后的意思[W]表示离指定日期的最近那个工作日[#] 序号(表示每月的第几个周几)
	 * 
	 * @秒 [, - * /] 分 [, - * /] 小时[, - * /] 日[, - * ? / L W] 月[, - * /] 周[, - *
	 *    ? / L #] 年[非必填][, - * / ]
	 */
	@Scheduled(cron = "0 0 0 ? * *")
	public void writeLog() {
		log.info("I am alive!");
	}
}