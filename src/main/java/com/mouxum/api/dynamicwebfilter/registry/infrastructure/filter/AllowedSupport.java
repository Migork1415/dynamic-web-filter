package com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter;

import org.springframework.core.MethodParameter;

import java.util.Arrays;

/**
 * Support class to hold utilities for Method Argument Resolvers
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
public final class AllowedSupport {

	/**
	 * Validates if the method allows for the current field to be filtered
	 *
	 * @param allowed         {@link Allowed} annotation instance with allowed values
	 * @param methodParameter {@link MethodParameter} instance provided by Spring
	 * @param key             Parameter name
	 * @return {@literal true} if field is available for filtering. {@literal false} otherwise
	 */
	public static boolean isAllowed( MethodParameter methodParameter, Allowed allowed, String key ) {
		return !methodParameter.hasMethodAnnotation( Allowed.class ) || allowed.all() || Arrays.asList( allowed.fields() ).contains( key );
	}

}
