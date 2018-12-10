package com.mouxum.api.dynamicwebfilter.registry.infrastructure.resolvers;

import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource.QueryType;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.data.domain.Sort;

/**
 * <p>
 * This class extends {@link Sort.Order} to add two more fields: column (database column name) and propertyPath (path to field in object to sort).
 * The column is used in jooq and propertyPath is used in hibernate for sort in joins between tables.
 * </p>
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
@EqualsAndHashCode( callSuper = true )
@Value
public class CustomOrder extends Sort.Order {

	private static final long serialVersionUID = -3640474213134916985L;

	private String column;

	private QueryType queryType;

	private String table;

	private String propertyName;

	/**
	 * Creates a new {@link Sort.Order} instance. if order is {@literal null} then order defaults to
	 * {@link Sort#DEFAULT_DIRECTION}
	 *
	 * @param direction    can be {@literal null}, will default to {@link Sort#DEFAULT_DIRECTION}
	 * @param table        table name in database
	 * @param column       column name in database
	 * @param queryType    {@link QueryType} related to this sort
	 * @param propertyName name of the property, typically the name of sort given in pageable
	 */
	public CustomOrder( Sort.Direction direction, String table, String column, QueryType queryType, String propertyName ) {
		super( direction, column );
		this.table = table;
		this.column = column;
		this.queryType = queryType;
		this.propertyName = propertyName;
	}
}
