package com.mouxum.api.dynamicwebfilter.registry.infrastructure.converters;

import org.apache.commons.lang.ClassUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeParseException;

/**
 * Will handle the cast of value objects into {@link java.time.temporal.Temporal} classes
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
public class TemporalConverter implements Converter {

	private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * Casts temporal object value into the required class
	 *
	 * @param clazz class to convert to
	 * @param value value to be converted into clazz
	 * @return a instance of temporal clazz holding the given value
	 */
	@Override
	public <T> T convert( Class<?> clazz, Object value ) {
		//TODO: find a better way to convert dates from string
		if ( ClassUtils.isAssignable( clazz, LocalTime.class ) ) {
			return (T) LocalTime.parse( value.toString() );
		}
		else if ( ClassUtils.isAssignable( clazz, LocalDate.class ) ) {
			return (T) LocalDate.parse( value.toString() );
		}
		else if ( ClassUtils.isAssignable( clazz, LocalDateTime.class ) ) {
			try {
				return (T) LocalDateTime.parse( value.toString() );
			}
			catch ( DateTimeParseException parseException ) {
				return processDateTimeException( value );
			}
		}
		else if ( ClassUtils.isAssignable( clazz, ZonedDateTime.class ) ) {
			return (T) ZonedDateTime.parse( value.toString() );
		}

		throw new RuntimeException( "Temporal class not implemented!" );
	}

	/**
	 * TODO: remove this when entities have the same date structure as resources
	 *
	 * @param value value to be converted into clazz
	 * @return a instance of temporal clazz holding the given value
	 */
	private <T> T processDateTimeException( Object value ) {
		try {
			return (T) LocalDateTime.ofInstant(
				Instant.ofEpochMilli( new SimpleDateFormat( DATE_TIME_FORMATTER ).parse( value.toString() ).getTime() ),
				ZoneId.systemDefault()
			);
		}
		catch ( ParseException e ) {
			throw new RuntimeException( e.getCause() );
		}
	}

}
