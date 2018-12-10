package com.mouxum.api.dynamicwebfilter.registry.infrastructure.converters;

/**
 * Will handle the cast of a value into the class type
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
public interface Converter {

	/**
	 * Attempts to convert a given value into a required object
	 *
	 * @param clazz class to convert to
	 * @param value value to be converted into clazz
	 * @return a instance of clazz holding the given value
	 */
	<T> T convert( Class<?> clazz, Object value );

}
