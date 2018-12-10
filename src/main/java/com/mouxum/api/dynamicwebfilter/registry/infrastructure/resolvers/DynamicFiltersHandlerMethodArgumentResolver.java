package com.mouxum.api.dynamicwebfilter.registry.infrastructure.resolvers;

import com.mouxum.api.dynamicwebfilter.DynamicWebFilterProperties.WebDelimiter;
import com.mouxum.api.dynamicwebfilter.registry.DictionaryRegistry;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.Allowed;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.Filter;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.FilterList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashSet;
import java.util.Set;

import static com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.AllowedSupport.isAllowed;
import static java.util.stream.Collectors.toSet;

/**
 * Implementation of {@link HandlerMethodArgumentResolver} to create a {@link FilterList} for every
 * request that requires a {@link FilterList} using the request parameters.
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
@Slf4j
public class DynamicFiltersHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	private static final Set<String> EXCLUDED_PARAMETERS = new HashSet<>();

	private final WebDelimiter webDelimiter;

	private final DictionaryRegistry registry;

	static final String PAGE_PARAMETER = "page";

	static final String SIZE_PARAMETER = "size";

	static final String SORT_PARAMETER = "sort";

	static {
		EXCLUDED_PARAMETERS.add( PAGE_PARAMETER );
		EXCLUDED_PARAMETERS.add( SIZE_PARAMETER );
		EXCLUDED_PARAMETERS.add( SORT_PARAMETER );
	}

	public DynamicFiltersHandlerMethodArgumentResolver( DictionaryRegistry registry, WebDelimiter webDelimiter ) {
		super();
		this.registry = registry;
		this.webDelimiter = webDelimiter;
	}

	@Override
	public boolean supportsParameter( MethodParameter parameter ) {
		return FilterList.class.isAssignableFrom( parameter.getParameterType() );
	}

	@Override
	public FilterList resolveArgument( MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
		NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory ) {
		return new FilterList( filters( nativeWebRequest, methodParameter ) );
	}

	/**
	 * Iterates over the request parameters for every request that requires filtering and creates a List of {@link Filter}
	 * {@inheritDoc}
	 *
	 * @return List of {@link Filter} to filter query requests
	 */
	private Set<Filter> filters( NativeWebRequest nativeWebRequest, MethodParameter methodParameter ) {

		Allowed allowed = methodParameter.getMethodAnnotation( Allowed.class );

		return nativeWebRequest.getParameterMap()
			.entrySet()
			.stream()
			.map( entry -> new SingleValueParameterWrapper( entry.getKey(), entry.getValue(), webDelimiter.getFilterDelimiter(), webDelimiter.getListDelimiter() ) )
			.filter( wrapper -> wrapper.isValid() && !EXCLUDED_PARAMETERS.contains( wrapper.field() ) )
			.filter( wrapper -> isAllowed( methodParameter, allowed, wrapper.field() ) )
			.map( singleValueParameterWrapper -> singleValueParameterWrapper.toFilter( allowed.resource(), registry ) )
			.collect( toSet() );
	}

}
