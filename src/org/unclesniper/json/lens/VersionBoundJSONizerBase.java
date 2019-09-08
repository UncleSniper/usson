package org.unclesniper.json.lens;

public abstract class VersionBoundJSONizerBase<ValueT> {

	protected int version;

	protected OrderRelation order;

	protected InnerJSONizer<? super ValueT> jsonizer;

	public VersionBoundJSONizerBase(int version, OrderRelation order, InnerJSONizer<? super ValueT> jsonizer) {
		this.version = version;
		this.order = order;
		this.jsonizer = jsonizer;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public OrderRelation getOrder() {
		return order;
	}

	public void setOrder(OrderRelation order) {
		this.order = order;
	}

	public InnerJSONizer<? super ValueT> getJSONizer() {
		return jsonizer;
	}

	public void setJSONizer(InnerJSONizer<? super ValueT> jsonizer) {
		this.jsonizer = jsonizer;
	}

}
