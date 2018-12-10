package com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource;

import com.mouxum.api.dynamicwebfilter.registry.DictionaryRegistry;

import java.lang.annotation.*;

/**
 * Classes annotated by Resource will have their fields search for @{@link Searchable}
 * to load the {@link DictionaryRegistry} beans
 *
 * @author miguel.miranda
 * @since 0.0.1
 */
@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface Resource {

	Searchable[] value();

	/**
	 * The table name of the entity to search for (ex: devices)
	 *
	 * @return The table name
	 */
	String tableName();
}


