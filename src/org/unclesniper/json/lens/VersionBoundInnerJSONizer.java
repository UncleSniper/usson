package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.OMGWereDoomedError;

public class VersionBoundInnerJSONizer<ValueT>
		extends VersionBoundJSONizerBase<ValueT> implements InnerJSONizer<ValueT> {

	public VersionBoundInnerJSONizer(int version, OrderRelation order, InnerJSONizer<? super ValueT> jsonizer) {
		super(version, order, jsonizer);
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		switch(order) {
			case LT:
				if(version < this.version)
					jsonizer.jsonize(value, sink, version);
				break;
			case LE:
				if(version <= this.version)
					jsonizer.jsonize(value, sink, version);
				break;
			case EQ:
				if(version == this.version)
					jsonizer.jsonize(value, sink, version);
				break;
			case GE:
				if(version >= this.version)
					jsonizer.jsonize(value, sink, version);
				break;
			case GT:
				if(version > this.version)
					jsonizer.jsonize(value, sink, version);
				break;
			default:
				throw new OMGWereDoomedError("Unrecognized order relation: " + order.name());
		}
	}

}
