/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lyz.validate;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Validate the @int annotation
 * 
 * @author jiangtch
 */
public class ValidateLongHandler implements Handler {
	private static final Logger LOGGER = Logger.getLogger(ValidateLongHandler.class.getName());

	public void validate(AnnotationValidable validatedObj, Field field)
			throws ValidateException {
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.entering(this.getClass().getName(), "validate()");
		}
		if (field.isAnnotationPresent(ValidateLong.class)) {
			checkLong(validatedObj, field);
		}
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.exiting(this.getClass().getName(), "validate()");
		}
	}

	/**
	 * validate the int type
	 * 
	 * @param filter
	 *            validated object
	 * @param field
	 *            validated field or property
	 * @throws ValidateException
	 */
	private void checkLong(AnnotationValidable filter, Field field)
			throws ValidateException {
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.entering(this.getClass().getName(), "checkLong()");
		}
		ValidateLong annotation = field.getAnnotation(ValidateLong.class);
		long min = annotation.min();
		long max = annotation.max();
		String message = annotation.message();

		Long destValue = null;
		try {
			Object obj = GetFiledValue
			.getField(filter, field.getName());
			if(obj instanceof Long){
				destValue = ((Long)obj).longValue();
			}else if(obj instanceof Integer){
				destValue = ((Integer)obj).longValue();
			}else if(obj instanceof Short){
				destValue = ((Short)obj).longValue();
			}
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE,
					"Get field value or cast value error. Error message: "
							+ ex.getMessage(), ex);
			throw new ValidateException(ex.getMessage(), ex);
		}

                if(destValue == null) {
                    return; //NULL value is allowed.
                }
                long value = destValue.longValue();

                if (value < min) {
                        LOGGER.log(Level.SEVERE, "Validate fail. Error message: validate value is:{0},min value is:{1}", new Long[]{value, min});
                        throw new ValidateException(message + ",值为:" + value
                                        + ",最小值应该为:" + min);
                }

                if (value > max) {
                        LOGGER.log(Level.SEVERE, "Validate fail. Error message: validate value is:{0},max value is:{1}", new Long[]{value, max});
                        throw new ValidateException(message + ",值为:" + value
                                        + ",最大值应该为:" + max);
                }

                if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.exiting(this.getClass().getName(), "checkInt()");
		}
	}

}
