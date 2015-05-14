package com.sanluan.cms.views.method;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import com.sanluan.common.base.BaseMethod;

import freemarker.template.TemplateModelException;

/**
 * @author zhangxd
 *
 */
@Component
public class DateMethod extends BaseMethod {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModelEx#exec(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		Date date;
		if (arguments.size() >= 2) {
			date = getDate(1, arguments);
		} else {
			date = new Date();
		}
		if (arguments.size() >= 1) {
			String type = getString(0, arguments);
			if ("thisSunday".equals(type))
				date = getThisSunday(date);
			else if ("thisMonday".equals(type))
				date = getThisMonday(date);
			else if ("lastMonday".equals(type))
				date = getLastMonday(date);
			else if ("lastSunday".equals(type))
				date = getLastSunday(date);
		}
		return date;
	}

	Date getThisMonday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.addDays(date, -1));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	Date getThisSunday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.addDays(date, 6));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

	Date getLastMonday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.addDays(date, -8));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	Date getLastSunday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.addDays(date, -1));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

}
