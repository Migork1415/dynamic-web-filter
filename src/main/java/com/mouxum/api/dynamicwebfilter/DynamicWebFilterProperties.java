package com.mouxum.api.dynamicwebfilter;

import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource.Resource;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource.Searchable;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties( prefix = "dynamic.web.filters" )
public class DynamicWebFilterProperties {

	/**
	 * Package path to @{@link Resource} and {@link Searchable} resources.
	 * This resources will be loaded by reflection to check the variables lookup on rest calls
	 */
	private String packagePath;

	/**
	 * Max pageable size that a rest method can fetch
	 */
	private int maxPageableSize = 10;

	/**
	 * The delimiter for filters on web calls , example: <ip>:<port>/<path>?something__eq=2,5,6
	 */
	private WebDelimiter webDelimiter = new WebDelimiter();

	@Data
	public class WebDelimiter{

		private String filterDelimiter = "__";

		private String listDelimiter = ",";

	}
}
