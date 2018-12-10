package com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter;

/**
 * Available operations sent to filter the database, this operations will be held in
 * a {@link Filter} object
 *
 * @author Miguel Miranda
 * @see Filter
 * @since 0.0.1
 */
public enum Operation {

	CONTAINS( "icontains" ), EQUALS( "equals" ), GT( "gt" ), GTE( "gte" ), LT( "lt" ), LTE( "lte" ), IS_NULL( "isnull" );

	private final String identifier;

	Operation( String operation ) {
		this.identifier = operation;
	}

	private String operation() {
		return identifier;
	}

	/**
	 * Using a given string returns the associated operation, used if the string value is lowercase
	 *
	 * @param value Operation name received in API
	 * @return Operation enum if valid.
	 * @throws FilterOperationNotSupportedException if operation does not exists
	 */
	public static Operation resolve( String value ) {
		Operation operation = null;
		for ( Operation op : Operation.values() ) {
			if ( op.operation().equalsIgnoreCase( value ) ) {
				operation = op;
				break;
			}
		}

		if ( operation == null ) {
			throw new FilterOperationNotSupportedException( value );
		}

		return operation;
	}

}
