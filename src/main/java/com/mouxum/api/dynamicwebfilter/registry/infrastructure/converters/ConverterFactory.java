package com.mouxum.api.dynamicwebfilter.registry.infrastructure.converters;

import org.apache.commons.lang.ClassUtils;

import java.time.temporal.Temporal;

/**
 * {@link Converter} instance factory manager.
 * Will handle which converter is assign to deal with the a single class.
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
public class ConverterFactory {

	private static final EnumConverter enumConverter = new EnumConverter();

	private static final TemporalConverter temporalConverter = new TemporalConverter();

	private static final OtherConverter otherConverter = new OtherConverter();

	/**
	 * Returns an instance of {@link Converter} responsible to handle the conversion of the given {@link Class}
	 *
	 * @param clazz class to convert to
	 * @return an instance of {@link Converter}
	 */
	public static Converter getInstance( Class<?> clazz ) {
		if ( ClassUtils.isAssignable( clazz, Temporal.class ) ) {
			return temporalConverter;
		}

		return clazz.isEnum()  ? enumConverter : otherConverter;
	}

}
