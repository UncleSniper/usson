package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.OMGWereDoomedError;

public class VersionBoundJSONizer<ValueT> extends VersionBoundJSONizerBase<ValueT> implements JSONizer<ValueT> {

	public VersionBoundJSONizer(int version, OrderRelation order, InnerJSONizer<? super ValueT> jsonizer) {
		super(version, order, jsonizer);
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		switch(order) {
			case LT:
				if(version < this.version)
					jsonizer.jsonize(value, sink, version);
				else
					sink.foundNull();
				break;
			case LE:
				if(version <= this.version)
					jsonizer.jsonize(value, sink, version);
				else
					sink.foundNull();
				break;
			case EQ:
				if(version == this.version)
					jsonizer.jsonize(value, sink, version);
				else
					sink.foundNull();
				break;
			case GE:
				if(version >= this.version)
					jsonizer.jsonize(value, sink, version);
				else
					sink.foundNull();
				break;
			case GT:
				if(version > this.version)
					jsonizer.jsonize(value, sink, version);
				else
					sink.foundNull();
				break;
			default:
				throw new OMGWereDoomedError("Unrecognized order relation: " + order.name());
		}
	}

}
