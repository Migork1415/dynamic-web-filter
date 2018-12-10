package com.mouxum.api.dynamicwebfilter.registry.infrastructure.converters;

import java.lang.reflect.InvocationTargetException;

/**
 * Will handle the cast of value objects into {@link Enum}
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
public class EnumConverter implements Converter {

	private static final String ENUM_CONVERTER_METHOD = "valueOf";

	private static final Class<?>[] ENUM_CONVERTER_PARAMETER = new Class<?>[] { String.class };

	/**
	 * Casts enum object value into the required enum class
	 *
	 * @param clazz class to convert to
	 * @param value value to be converted into clazz
	 * @return a instance of enum clazz holding the given value
	 */
	@Override
	public <T> T convert( Class<?> clazz, Object value ) {
		try {
//			TODO: find a better way to convert enums from string
			return (T) clazz
				.getMethod( ENUM_CONVERTER_METHOD, ENUM_CONVERTER_PARAMETER )
				.invoke( null, (String) value );
		}
		catch ( IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
			throw new UnableToCastException( "Impossible to convert " + value + " into enum " + clazz.getSimpleName() );
		}
	}

}
