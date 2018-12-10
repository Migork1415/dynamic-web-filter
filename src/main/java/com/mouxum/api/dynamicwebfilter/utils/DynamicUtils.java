package com.mouxum.api.dynamicwebfilter.utils;

import com.mouxum.api.dynamicwebfilter.registry.infrastructure.converters.ConverterFactory;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.springframework.util.ClassUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @author Miguel Miranda
 */
//@UtilityClass
public class DynamicUtils {

	/**
	 * Retrieves true if the {@link Class} given if a Date:
	 * {@link ZonedDateTime}, {@link LocalDate}, {@link LocalDateTime}
	 *
	 * @param clazz class to check
	 * @return true if the {@link Class} given if a Date
	 */
	public static boolean isDateField( Class<?> clazz ) {
		return ClassUtils.isAssignable( clazz, ZonedDateTime.class ) || ClassUtils.isAssignable( clazz, LocalDate.class ) || ClassUtils.isAssignable( clazz, LocalDateTime.class );
	}

	/**
	 * Attempts to convert a given value into a required object.
	 * Handle {@link Class} properties using {@link ConvertUtilsBean2}
	 *
	 * @param clazz class to convert to
	 * @param value value to be converted into clazz
	 * @return a instance of clazz holding the given value
	 */
	public static <T> T cast( Class<T> clazz, Object value ) {
		return ConverterFactory.getInstance( clazz ).convert( clazz, value );
	}
}
