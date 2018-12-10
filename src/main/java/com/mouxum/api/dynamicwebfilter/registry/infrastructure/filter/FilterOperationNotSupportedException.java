package com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter;

import static java.lang.String.format;

/**
 * @author Miguel Miranda
 * @since 0.0.1
 */

public class FilterOperationNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = -2135546238660528035L;

	private static final String DETAIL = "Filter operation %s is not supported!";

	public FilterOperationNotSupportedException( String operation ) {
		super(
			format( DETAIL, operation )
		);
	}
}
