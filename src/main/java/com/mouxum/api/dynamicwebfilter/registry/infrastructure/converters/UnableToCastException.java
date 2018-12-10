package com.mouxum.api.dynamicwebfilter.registry.infrastructure.converters;

/**
 * @author Miguel Miranda
 * @since 0.0.1
 */
public class UnableToCastException extends RuntimeException {

	public UnableToCastException( String message, Throwable e ) {
		super( message, e );
	}

	public UnableToCastException( String message ) {
		super( message );
	}
}