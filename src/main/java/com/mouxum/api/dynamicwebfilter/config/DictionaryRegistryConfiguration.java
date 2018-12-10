package com.mouxum.api.dynamicwebfilter.config;

import com.mouxum.api.dynamicwebfilter.DynamicWebFilterProperties;
import com.mouxum.api.dynamicwebfilter.registry.ResourceDictionaryRegistry;
import com.mouxum.api.dynamicwebfilter.registry.ResourceDictionaryRegistryFactory;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resolvers.DynamicFiltersHandlerMethodArgumentResolver;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resolvers.DynamicPageableHandlerMethodArgumentResolver;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resolvers.DynamicSortHandlerMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * Auto-Configuration beans
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties( DynamicWebFilterProperties.class )
public class DictionaryRegistryConfiguration {

	private final DynamicWebFilterProperties dynamicWebFilterProperties;

	/**
	 * Example of path "com.hydroko.hydrosense.core"
	 *
	 * @return a {@link ResourceDictionaryRegistryFactory}
	 */
	@Bean
	@ConditionalOnMissingBean( ResourceDictionaryRegistryFactory.class )
	@ConditionalOnProperty( prefix = "dynamic.web.filters", name = "packagePath" )
	public ResourceDictionaryRegistryFactory resourceDictionaryRegistryFactory() {
		return new ResourceDictionaryRegistryFactory( dynamicWebFilterProperties.getPackagePath() );
	}

	/**
	 * Example of path "com.hydroko.hydrosense.core"
	 *
	 * @return a {@link DynamicFiltersHandlerMethodArgumentResolver}
	 */
	@Bean
	@ConditionalOnMissingBean( HandlerMethodArgumentResolver.class )
	@ConditionalOnProperty( prefix = "dynamic.web.filters", name = "packagePath" )
	public DynamicFiltersHandlerMethodArgumentResolver dynamicFiltersHandlerMethodArgumentResolver( ResourceDictionaryRegistry resourceDictionaryRegistry ) {
		return new DynamicFiltersHandlerMethodArgumentResolver( resourceDictionaryRegistry, null );
	}

	/**
	 * Example of path "com.hydroko.hydrosense.core"
	 *
	 * @return a {@link DynamicPageableHandlerMethodArgumentResolver}
	 */
	@Bean
	@ConditionalOnMissingBean( PageableHandlerMethodArgumentResolver.class )
	@ConditionalOnProperty( prefix = "dynamic.web.filters", name = "packagePath" )
	public DynamicPageableHandlerMethodArgumentResolver dynamicPageableHandlerMethodArgumentResolver( SortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver ) {
		return new DynamicPageableHandlerMethodArgumentResolver( sortHandlerMethodArgumentResolver, dynamicWebFilterProperties.getMaxPageableSize() );
	}

	/**
	 * Example of path "com.hydroko.hydrosense.core"
	 *
	 * @return a {@link DynamicSortHandlerMethodArgumentResolver}
	 */
	@Bean
	@ConditionalOnMissingBean( SortHandlerMethodArgumentResolver.class )
	@ConditionalOnProperty( prefix = "dynamic.web.filters", name = "packagePath" )
	public DynamicSortHandlerMethodArgumentResolver dynamicSortHandlerMethodArgumentResolver( ResourceDictionaryRegistry resourceDictionaryRegistry ) {
		return new DynamicSortHandlerMethodArgumentResolver( resourceDictionaryRegistry );
	}
}
