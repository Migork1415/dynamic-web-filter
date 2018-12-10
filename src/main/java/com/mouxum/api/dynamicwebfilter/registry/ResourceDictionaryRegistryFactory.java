package com.mouxum.api.dynamicwebfilter.registry;

import com.mouxum.api.dynamicwebfilter.registry.DictionaryRegistry.DictionaryResolver;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter.Filter;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resolvers.DynamicFiltersHandlerMethodArgumentResolver;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource.Resource;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource.Searchable;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * Custom FactoryBean to load {@link DictionaryResolver} into a {@link DictionaryRegistry }.
 * </p>
 * <p>
 * The lookup will be done using the resource classes annotated with {@link Resource} and
 * the fields annotated with {@link Searchable}
 * </p>
 * <p>
 * A path must be provided to the package where the {@link Resource} and @{@link Searchable} annotated classes are
 * </p>
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
@Slf4j
public class ResourceDictionaryRegistryFactory implements FactoryBean<ResourceDictionaryRegistry> {

	private final Reflections reflections;

	public ResourceDictionaryRegistryFactory( String path ) {
		this.reflections = new Reflections( path );
	}

	@Override
	public ResourceDictionaryRegistry getObject() {
		return fillResources( new ResourceDictionaryRegistry() );
	}

	@Override
	public Class<?> getObjectType() {
		return ResourceDictionaryRegistry.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * Will load the map of {@link DictionaryResolver} of the given {@link DictionaryRegistry} bean.
	 *
	 * @param registry instance of {@link DictionaryRegistry} in which will be loaded the {@link DictionaryResolver}
	 *                 used to create {@link Filter} in {@link DynamicFiltersHandlerMethodArgumentResolver}
	 * @return DictionaryRegistry bean
	 */
	private ResourceDictionaryRegistry fillResources( ResourceDictionaryRegistry registry ) {
		log.debug( "Filling dictionary registry of {}", registry.getClass().getSimpleName() );

		for ( Class<?> clazz : reflections.getTypesAnnotatedWith( Resource.class ) ) {
			log.debug( "Loading searchable fields from {}", clazz.getSimpleName() );

			Resource resourceAnnotation = clazz.getAnnotation( Resource.class );

			Map<String, DictionaryResolver> resolverMap =
				Arrays.stream( resourceAnnotation.value() )
					.collect( Collectors.toMap( Searchable::field, searchable ->
						new DictionaryResolver(
							searchable.targetClass(),
							searchable.fieldColumn(),
							StringUtils.hasText( searchable.tableName() ) ? searchable.tableName() : resourceAnnotation.tableName(),
							searchable.type()
						), ( field, resolver ) -> resolver ) );

			registry.put( clazz, resolverMap );
		}

		return registry;
	}
}
