package com.mouxum.api.dynamicwebfilter.registry;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of {@link DictionaryRegistry} to hold all the searchable fields
 * of the application resource.
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
@Slf4j
public class ResourceDictionaryRegistry implements DictionaryRegistry {

	private final Map<Class<?>, Map<String, DictionaryResolver>> dictionary = new HashMap<>();

	/**
	 * Retrieves a {@link DictionaryResolver} from registry for a given key and field
	 *
	 * @param key   resource class
	 * @param field field inside the entity to filter from
	 * @return an {@link Optional} of {@link DictionaryResolver} if a key exists in context
	 */
	@Override
	public Optional<DictionaryResolver> get( Class key, String field ) {

		Map<String, DictionaryResolver> dictionaryEntry = this.dictionary.get( key );
		if ( dictionaryEntry == null ) {
			throw new MissingEntryDictionaryException( "No dictionary resolver found", key.getSimpleName() );
		}
		return Optional.ofNullable( dictionaryEntry.get( field ) );
	}

	/**
	 * Loads the given map using the given class as key in the dictionary registry
	 *
	 * @param key   resource class
	 * @param value resolver map
	 */
	@Override
	public void put( Class<?> key, Map<String, DictionaryResolver> value ) {
		this.dictionary.put( key, value );
	}

}
