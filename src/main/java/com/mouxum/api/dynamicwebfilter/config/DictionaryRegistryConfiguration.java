package com.mouxum.api.dynamicwebfilter.config;

import com.mouxum.api.dynamicwebfilter.registry.ResourceDictionaryRegistryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-Configuration beans
 */
@Configuration
public class DictionaryRegistryConfiguration {

	@Bean
	public ResourceDictionaryRegistryFactory resourceDictionaryRegistryFactory(){
		return new ResourceDictionaryRegistryFactory( "com.hydroko.hydrosense.core" );
	}

}
