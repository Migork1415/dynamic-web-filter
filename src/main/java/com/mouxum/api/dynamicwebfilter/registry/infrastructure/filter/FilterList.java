package com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter;

import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resolvers.DynamicFiltersHandlerMethodArgumentResolver;

import java.util.Collection;
import java.util.HashSet;

/**
 * Group of filters used to do query filtering on database
 *
 * @author Miguel Miranda
 * @see DynamicFiltersHandlerMethodArgumentResolver
 * @since 0.0.1
 */
public class FilterList extends HashSet<Filter> {

	private static final long serialVersionUID = -3289560995699577918L;

	public FilterList( Collection<? extends Filter> c ) {
		super( c );
	}
}
