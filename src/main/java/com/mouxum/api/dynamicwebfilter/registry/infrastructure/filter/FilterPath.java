package com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter;

import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource.QueryType;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * @author Miguel Miranda
 * @since 0.0.1
 */
@EqualsAndHashCode( callSuper = true )
@Value
public class FilterPath<T> extends Filter<T> {

	private final String table;

	private final String column;

	private final Class<?> valueClass;

	private final QueryType queryType;

	public FilterPath( String name, String table, String column, Operation operation, Class<?> valueClass, T value, QueryType queryType ) {
		super( name, operation, value );
		this.column = column;
		this.table = table;
		this.valueClass = valueClass;
		this.queryType = queryType;
	}
}
