package com.mouxum.api.dynamicwebfilter.registry.infrastructure.resolvers;

import com.mouxum.api.dynamicwebfilter.registry.DictionaryRegistry;
import com.mouxum.api.dynamicwebfilter.registry.DictionaryRegistry.DictionaryResolver;
import com.mouxum.api.dynamicwebfilter.registry.MissingEntryDictionaryResolverException;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.Allowed;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.AllowedSupport;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link SortHandlerMethodArgumentResolver} to create a {@link Sort} for every
 * request that requires a {@link Pageable} using custom sort request parameter values.
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
public class DynamicSortHandlerMethodArgumentResolver extends SortHandlerMethodArgumentResolver {

	private final DictionaryRegistry registry;

	public DynamicSortHandlerMethodArgumentResolver( DictionaryRegistry registry ) {
		super();
		this.registry = registry;
	}

	@Override
	public Sort resolveArgument( MethodParameter methodParameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory ) {

		Sort orders = resolveOrderArgument( methodParameter, mavContainer, webRequest, binderFactory );

		return orders == null ? null : convertSortProperties( orders, methodParameter );
	}

	/**
	 * Calling super resolveArgument
	 */
	protected Sort resolveOrderArgument( MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory ) {
		return super.resolveArgument( parameter, mavContainer, webRequest, binderFactory );
	}

	/**
	 * Converts Properties in {@link Sort} to respective properties if is {@link Allowed}
	 *
	 * @param orders          {@link Sort} to convert properties
	 * @param methodParameter {@link MethodParameter} to get annotations to convert properties
	 * @return a {@link Sort} with converted properties that {@link Class} resource knows
	 */
	private Sort convertSortProperties( Sort orders, MethodParameter methodParameter ) {

		Allowed allowed = methodParameter.getMethodAnnotation( Allowed.class );

		List<Sort.Order> allOrders = new ArrayList<>();
		orders.forEach( order ->
		{
			if ( AllowedSupport.isAllowed( methodParameter, allowed, order.getProperty() ) ) {
				addConvertedOrder( allOrders, order, allowed.resource() );
			}
		} );
		return allOrders.isEmpty() ? null : Sort.by( allOrders );
	}

	/**
	 * Tries to convert {@link Sort.Order} property in known {@link Class} resource property, using {@link DictionaryResolver}
	 * and the given {@link Class}, and adds the converted {@link Sort.Order} to the given {@link List<Sort.Order>}
	 *
	 * @param allOrders list to add converted {@link Sort.Order}
	 * @param order     {@link Sort.Order} to convert
	 * @param resource  {@link Class} resource that {@link DictionaryResolver}  use to resolve the property
	 */
	private void addConvertedOrder( List<Sort.Order> allOrders, Sort.Order order, Class resource ) {

		DictionaryResolver resolver = registry.get( resource, order.getProperty() )
			.orElseThrow( () -> new MissingEntryDictionaryResolverException( "Sort", order.getProperty() ) );

		allOrders.add(
			new CustomOrder(
				order.getDirection(),
				resolver.getTable(),
				resolver.getColumn(),
				resolver.getQueryType(),
				order.getProperty()
			) );
	}

}
