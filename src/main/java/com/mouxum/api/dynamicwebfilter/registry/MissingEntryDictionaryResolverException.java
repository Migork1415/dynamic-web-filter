package com.mouxum.api.dynamicwebfilter.registry;

import com.mouxum.api.dynamicwebfilter.registry.DictionaryRegistry.DictionaryResolver;

import static java.lang.String.format;

/**
 * When filters or sorts are not supported according to {@link DictionaryResolver}
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
public class MissingEntryDictionaryResolverException extends RuntimeException {

	private static final String DETAIL = "%s by %s is not supported!";

	private static final long serialVersionUID = 4244502907150109121L;

	public MissingEntryDictionaryResolverException( String title, String parameterName ) {
		super(
			format( DETAIL, title, parameterName )
		);
	}
}
