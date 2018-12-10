package com.mouxum.api.dynamicwebfilter.registry.infrastructure.filter;

import java.lang.annotation.*;

/**
 * <p>
 * This class will be used to control the allowed fields that can be sorted and filtered
 * </p>
 * <p>
 * This class can be used in a method endpoint by placing it before a method endpoint
 * </p>
 * <p>
 * If all parameter is set to false then only the fields will be take in consideration for filtering or sorting
 * all others will be discarded.
 * </p>
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface Allowed {

	/**
	 * If annotated as true then all filter or sort values sent in request are valid to be processed
	 * otherwise if set to false then only values in fields variable will be taken in consideration
	 *
	 * @return flag if is to use all available fields in endpoint request
	 */
	boolean all() default true;

	/**
	 * If all is set to false only these fields will be taken in consideration when creating the filter and sorts
	 *
	 * @return Array of allowed values
	 */
	String[] fields() default {};

	/**
	 * Resource class that correspond the fields to be filtered or ordered
	 * This field is required for any filtered and ordered endpoint
	 *
	 * @return Resource class
	 */
	Class resource();

}
