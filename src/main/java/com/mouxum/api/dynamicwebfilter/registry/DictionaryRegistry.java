package com.mouxum.api.dynamicwebfilter.registry;

import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource.QueryType;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource.Resource;
import com.mouxum.api.dynamicwebfilter.registry.infrastructure.resource.Searchable;

import java.util.Map;
import java.util.Optional;

/**
 * Dictionary registry classes will have their Dictionary map loaded at runtime by fields annotated with {@link Searchable}
 * of {@link Resource} annotated classes
 *
 * @author Miguel Miranda
 * @since 0.0.1
 */
public interface DictionaryRegistry {

	/**
	 * Retrieves a {@link DictionaryResolver} from registry for a given key and field
	 *
	 * @param key   resource class
	 * @param field field inside the entity to filter from
	 * @return an optional of dictionaryResolver if a key exists in context
	 */
	Optional<DictionaryResolver> get( Class<?> key, String field );

	/**
	 * Loads the given map using the given class as key in the dictionary registry
	 *
	 * @param key   resource class
	 * @param value resolver map
	 */
	void put( Class<?> key, Map<String, DictionaryResolver> value );

	class DictionaryResolver {

		private final Class<?> type;

		private final String column;

		private final String table;

		private final QueryType queryType;

		public DictionaryResolver( Class<?> type, String column, String table, QueryType queryType ) {
			this.type = type;
			this.column = column;
			this.table = table;
			this.queryType = queryType;
		}

		public Class<?> getType() {
			return type;
		}

		public String getColumn() {
			return column;
		}

		public String getTable() {
			return table;
		}

		public QueryType getQueryType() {
			return queryType;
		}
	}
}
