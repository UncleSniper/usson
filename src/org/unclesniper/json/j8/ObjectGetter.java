package org.unclesniper.json.j8;

public interface ObjectGetter<BaseT, PropertyT> {

	PropertyT getProperty(BaseT base);

}
