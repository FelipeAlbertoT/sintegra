package br.com.canalvpsasul.sintegra.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CollectionHelper {

	public static <K, V> Map<K, V> listAsMap(Collection<V> sourceList, ListToMapConverter<K, V> converter) {
	    Map<K, V> newMap = new HashMap<K, V>();
	    for (V item : sourceList) {
	        newMap.put( converter.getKey(item), item );
	    }
	    return newMap;
	}

	public static interface ListToMapConverter<K, V> {
	    public K getKey(V item);
	}
	
}
