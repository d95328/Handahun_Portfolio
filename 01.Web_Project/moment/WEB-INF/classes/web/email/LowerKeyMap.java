package web.email;

import org.apache.commons.collections.map.ListOrderedMap;

public class LowerKeyMap extends ListOrderedMap{

	public Object put(Object key, Object value) {
		return super.put(  ((String)key).toLowerCase(), value );
	}
}
