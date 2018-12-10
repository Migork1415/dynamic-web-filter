package com.mouxum.api.dynamicwebfilter.registry.infrastructure.resolvers;

import com.mouxum.api.dynamicwebfilter.registry.DictionaryRegistry;
import com.mouxum.api.dynamicwebfilter.registry.MissingEntryDictionaryResolverException;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.Filter;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.FilterPath;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.Operation;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.mouxum.api.dynamicwebfilter.utils.DynamicUtils.cast;

/**
 * Filter parameter wrapper class
 * Handle HTTP Request parameter and parse for expected format: {@link Filter} instance
 * This implementation assumes that only has one value for the parameter.
 * Original HTTP Parameter follows the pattern <key>__<operation>=[value]
 *
 * @author Miguel Miranda
 * @since <next-version>
 */
class SingleValueParameterWrapper {

	private static final String FILTER_DELIMITER = "__";

	private static final String LIST_DELIMITER = ",";

	String[] data;

	String value;

	SingleValueParameterWrapper( String raw, String[] value ) {
		this.data = raw.split( FILTER_DELIMITER );
		this.value = value[0];
	}

	String field() {
		return this.data[0];
	}

	String operation() {
		return this.data[1];
	}

	boolean isValid() {
		return data != null && value != null && StringUtils.hasText( data[0] );
	}

	Filter toFilter( Class resource, DictionaryRegistry registry ) {
		Operation operation = data.length > 1 ? Operation.resolve( operation() ) : Operation.EQUALS;

		DictionaryRegistry.DictionaryResolver resolver = registry.get( resource, field() )
			.orElseThrow( () -> new MissingEntryDictionaryResolverException( "Filter", field() ) );

		return
			new FilterPath<>(
				field(),
				resolver.getTable(),
				resolver.getColumn(),
				operation,
				resolver.getType(),
				resolveValue( resolver.getType(), operation ),
				resolver.getQueryType()
			);
	}

	/**
	 * Transform this.value according to operation and {@link Class>} type given.
	 * If operation is {@link Operation#IS_NULL} it converts to a boolean object.
	 * If operation is {@link Operation#EQUALS} it tries to check if is a multiple value list and creates and array with all objects transformed to {@link Class<>} type.
	 * Multiple value list pattern <key>=[value1],[value2],[value3]
	 * For other operations this.value  is transformed to {@link Class} type.
	 *
	 * @param type      {@link Class} type to convert to
	 * @param operation {@link Operation}
	 * @return the value transformed according to operation and {@link Class} type given.
	 */
	private Object resolveValue( Class<?> type, Operation operation ) {
		switch ( operation ) {
			case IS_NULL:
				return BooleanUtils.toBoolean( this.value );
			case EQUALS:
				String[] values = this.value.trim().split( LIST_DELIMITER );
				return values.length > 1 ? Arrays.stream( values ).map( val -> cast( type, val ) ).collect( Collectors.toList() ) : cast( type, this.value );

			default:
				return cast( type, this.value );
		}

	}

}
