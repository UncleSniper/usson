package org.unclesniper.json.lens;

import java.util.Deque;
import java.util.LinkedList;

public abstract class UnexpectedJSONException extends Exception {

	public interface JSONPath {

		void renderPath(StringBuilder sink);

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
