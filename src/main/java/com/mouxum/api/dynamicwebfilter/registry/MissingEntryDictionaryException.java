package com.mouxum.api.dynamicwebfilter.registry;

/**
 * @author Miguel Miranda
 * @since 0.0.1
 */
public class MissingEntryDictionaryException extends RuntimeException {

	private static final long serialVersionUID = -2135546238660528035L;

	final String missing;

	public MissingEntryDictionaryException( String message, String missing ) {
		super( message );
		this.missing = missing;
	}
}
