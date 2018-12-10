package com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource;

import com.mouxum.api.dynamicwebfilter.registry.DictionaryRegistry;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.Filter;

import java.lang.annotation.*;

/**
 * Searchable annotated fields will be loaded into the {@link DictionaryRegistry} beans
 * to be able to search them as a endpoint {@link Filter}.
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface Searchable {

	/**
	 * The filter value sent as request parameter when calling a filtered endpoint
	 *
	 * @return key filter value expected key when receiving the request parameter in the endpoint
	 */
	String field();

	/**
	 * The table column name correspondent to the filter field sent as request parameter when calling a filtered endpoint.
	 * If database used has inner field search such as JSONB in postgres database then (when {@link Searchable#type()} is equal to {@link QueryType#INNER_FIELD})
	 * it should be filled with dot notation:
	 * <pre>known_state.apply_at</pre> where known_state is the column name and apply_at is the inner field field.
	 *
	 * @return key filter value expected key when receiving the request parameter in the endpoint
	 */
	String fieldColumn();

	/**
	 * The class type of the entity parameter that will be searched ( ex: String.class )
	 *
	 * @return the entity property class that will be filtered
	 */
	Class<?> targetClass();

	/**
	 * The query type that this filter can be used
	 *
	 * @return the query type that this filter should be used
	 */
	QueryType type() default QueryType.DEFAULT;

	/**
	 * The table name of the entity to search for (ex: devices)
	 * This only should be filled when the field doesn't belong to the resource
	 *
	 * @return The table name
	 */
	String tableName() default "";

}
