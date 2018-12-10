package com.mouxum.api.dynamicwebfilter.registry.infrastructure.converters;

import org.apache.commons.beanutils.ConvertUtilsBean2;

/**
 * Will handle every type of value given into the class, this is a default case
 * when no specific implementation exists for some given class.
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
public class OtherConverter implements Converter {

	@Override
	public <T> T convert( Class<?> clazz, Object value ) {
		return (T) new ConvertUtilsBean2().convert( value, clazz );
	}

}
