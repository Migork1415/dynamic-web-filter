package com.mouxum.api.dynamicwebfilter.registry.infrastructure.resolvers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Implementation of {@link PageableHandlerMethodArgumentResolver} to create a {@link Pageable} for every
 * request that requires a {@link Pageable} using custom pageable request parameter values.
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
@Slf4j
public class DynamicPageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {

	private final int maxPageSize;

	/**
	 * Constructs an instance of this resolver with the specified {@link SortHandlerMethodArgumentResolver}.
	 *
	 * @param sortResolver the sort resolver to use
	 */
	public DynamicPageableHandlerMethodArgumentResolver( SortHandlerMethodArgumentResolver sortResolver, int maxPageSize ) {
		super( sortResolver );
		this.maxPageSize = maxPageSize;
	}

	@Override
	public boolean supportsParameter( MethodParameter parameter ) {
		return Pageable.class.isAssignableFrom( parameter.getParameterType() );
	}

	@Override
	public Pageable resolveArgument( MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
		NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory ) {

		super.setMaxPageSize( maxPageSize );

		return super.resolveArgument( methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory );
	}
}
