package com.sanluan.common.base;

import static com.sanluan.common.constants.CommonConstants.FULL_DATE_FORMAT;
import static com.sanluan.common.constants.CommonConstants.FULL_DATE_LENGTH;
import static com.sanluan.common.constants.CommonConstants.SHORT_DATE_FORMAT;
import static com.sanluan.common.constants.CommonConstants.SHORT_DATE_LENGTH;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

/**
 * @author zhangxd
 * 
 */
public abstract class BaseMethod implements TemplateMethodModelEx {
	/**
	 * @param index
	 * @param arguments
	 * @return
	 * @throws TemplateModelException
	 */
	public static String getString(int index, List<TemplateModel> arguments) throws TemplateModelException {
		TemplateModel model = arguments.get(index);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateScalarModel) {
			return ((TemplateScalarModel) model).getAsString();
		} else if ((model instanceof TemplateNumberModel)) {
			return ((TemplateNumberModel) model).getAsNumber().toString();
		} else {
			return null;
		}
	}

	/**
	 * @param index
	 * @param arguments
	 * @return
	 * @throws TemplateModelException
	 */
	public static TemplateHashModelEx getHashModel(int index, List<TemplateModel> arguments) throws TemplateModelException {
		TemplateModel model = arguments.get(index);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateHashModelEx) {
			return (TemplateHashModelEx) model;
		} else {
			return null;
		}
	}

	/**
	 * @param index
	 * @param arguments
	 * @return
	 * @throws TemplateModelException
	 */
	public static Integer getInt(int index, List<TemplateModel> arguments) throws TemplateModelException {
		TemplateModel model = arguments.get(index);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateNumberModel) {
			return ((TemplateNumberModel) model).getAsNumber().intValue();
		} else if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			if (StringUtils.isBlank(s)) {
				return null;
			}
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * @param index
	 * @param arguments
	 * @return
	 * @throws TemplateModelException
	 */
	public static Long getLong(int index, List<TemplateModel> arguments) throws TemplateModelException {
		TemplateModel model = arguments.get(index);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateNumberModel) {
			return ((TemplateNumberModel) model).getAsNumber().longValue();
		} else if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			if (StringUtils.isBlank(s)) {
				return null;
			}
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * @param index
	 * @param arguments
	 * @return
	 * @throws TemplateModelException
	 */
	public static Integer[] getIntArray(int index, List<TemplateModel> arguments) throws TemplateModelException {
		String[] arr = getStringArray(index, arguments);
		if (null != arr) {
			Integer[] ids = new Integer[arr.length];
			int i = 0;
			try {
				for (String s : arr) {
					ids[i++] = Integer.valueOf(s);
				}
				return ids;
			} catch (NumberFormatException e) {
				return null;
			}
		} else
			return null;

	}

	/**
	 * @param index
	 * @param arguments
	 * @return
	 * @throws TemplateModelException
	 */
	public static Long[] getLongArray(int index, List<TemplateModel> arguments) throws TemplateModelException {
		String[] arr = getStringArray(index, arguments);
		if (null != arr) {
			Long[] ids = new Long[arr.length];
			int i = 0;
			try {
				for (String s : arr) {
					ids[i++] = Long.valueOf(s);
				}
				return ids;
			} catch (NumberFormatException e) {
				return null;
			}
		} else
			return null;

	}

	/**
	 * @param index
	 * @param arguments
	 * @return
	 * @throws TemplateModelException
	 */
	public static String[] getStringArray(int index, List<TemplateModel> arguments) throws TemplateModelException {
		String str = getString(index, arguments);
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return StringUtils.split(str, ',');
	}

	/**
	 * @param index
	 * @param arguments
	 * @return
	 * @throws TemplateModelException
	 */
	public static Boolean getBool(int index, List<TemplateModel> arguments) throws TemplateModelException {
		TemplateModel model = arguments.get(index);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateBooleanModel) {
			return ((TemplateBooleanModel) model).getAsBoolean();
		} else if (model instanceof TemplateNumberModel) {
			return !(0 == ((TemplateNumberModel) model).getAsNumber().intValue());
		} else if (model instanceof TemplateScalarModel) {
			String s = ((TemplateScalarModel) model).getAsString();
			if (StringUtils.isNotBlank(s)) {
				return !("0".equals(s) || "false".equalsIgnoreCase(s));
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * @param index
	 * @param arguments
	 * @return
	 * @throws TemplateModelException
	 */
	public static Date getDate(int index, List<TemplateModel> arguments) throws TemplateModelException {
		TemplateModel model = arguments.get(index);
		if (null == model) {
			return null;
		}
		if (model instanceof TemplateDateModel) {
			return ((TemplateDateModel) model).getAsDate();
		} else if (model instanceof TemplateScalarModel) {
			String temp = StringUtils.trimToEmpty(((TemplateScalarModel) model).getAsString());
			try {
				if (FULL_DATE_LENGTH == temp.length()) {
					return FULL_DATE_FORMAT.parse(temp);
				} else if (SHORT_DATE_LENGTH == temp.length()) {
					return SHORT_DATE_FORMAT.parse(temp);
				} else {
					return null;
				}
			} catch (ParseException e) {
				return null;
			}
		} else {
			return null;
		}
	}
}
