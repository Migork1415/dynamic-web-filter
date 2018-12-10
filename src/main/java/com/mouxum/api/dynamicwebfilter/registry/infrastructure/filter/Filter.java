package com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Filter format received by the endpoint to use filters in the database
 *
 * @author Miguel Miranda
 * @see FilterList
 * @since 0.0.1
 */
@EqualsAndHashCode( of = { "name", "operation" } )
@Getter
public class Filter<T> {

	private final String name;

	private final Operation operation;

	private final T value;

	public Filter( String name, Operation operation, T value ) {
		this.name = name;
		this.operation = operation;
		this.value = value;
	}

}
