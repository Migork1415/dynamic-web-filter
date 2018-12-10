package com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource;

/**
 * Query type enum represents how data should be queried in database.
 *
 * The table column name correspondent to the filter field sent as request parameter when calling a filtered endpoint.
 * @author miguel.miranda
 * @since 0.0.1
 */
public enum QueryType {


	DEFAULT,

	/**
	 * If database used has inner field search such as JSONB in postgres database then (when {@link Searchable#type()} is equal to {@link QueryType#INNER_FIELD})
	 * it should be filled with dot notation:
	 * <pre>known_state.apply_at</pre> where known_state is the column name and apply_at is the inner field field.
	 */
	INNER_FIELD

}
