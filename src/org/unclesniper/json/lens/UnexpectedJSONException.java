package org.unclesniper.json.lens;

import java.util.Deque;
import java.util.LinkedList;
import org.unclesniper.json.j8.ObjectSink;

public abstract class UnexpectedJSONException extends Exception {

	public interface JSONPath {

		void renderPath(StringBuilder sink);

	}

	private class PathSegmentFirstSink implements ObjectSink<JSONPath> {

		public PathSegmentFirstSink() {}

		@Override
		public void putObject(JSONPath segment) {
			addPathSegmentFirst(segment);
		}

	}

	private final Deque<JSONPath> path = new LinkedList<JSONPath>();

	private String cachedPath;

	public UnexpectedJSONException(String message) {
		super(message);
	}

	public void addPathSegmentFirst(JSONPath segment) {
		if(segment == null)
			return;
		path.addFirst(segment);
		cachedPath = null;
	}

	public void addPathSegmentLast(JSONPath segment) {
		if(segment == null)
			return;
		path.addLast(segment);
		cachedPath = null;
	}

	public ObjectSink<JSONPath> getPathSegmentFirstSink() {
		return new PathSegmentFirstSink();
	}

	public String getPath() {
		if(cachedPath == null) {
			StringBuilder builder = new StringBuilder();
			for(JSONPath segment : path)
				segment.renderPath(builder);
			cachedPath = builder.toString();
			if(cachedPath.length() == 0)
				cachedPath = "<top>";
		}
		return cachedPath;
	}

}
