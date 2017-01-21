package org.unclesniper.json.tool;

import java.util.Map;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import org.unclesniper.json.JSON;
import org.unclesniper.json.tool.values.Value;

public class TransformationContext {

	private static class Namespace {

		private final Map<String, Value> bindings = new HashMap<String, Value>();

		public Namespace() {}

		public Value get(String name) {
			return bindings.get(name);
		}

		public void put(String name, Value value) {
			bindings.put(name, value);
		}

	}

	private final Namespace rootNamespace = new Namespace();

	private final Deque<Namespace> scope = new LinkedList<Namespace>();

	private final LinkedList<JSON> trees = new LinkedList<JSON>();

	public TransformationContext() {
		scope.addFirst(rootNamespace);
	}

	public void pushScope() {
		scope.addFirst(new Namespace());
	}

	public void popScope() {
		scope.removeFirst();
	}

	public Value getConstant(String name) {
		for(Namespace level : scope) {
			Value value = level.get(name);
			if(value != null)
				return value;
		}
		return null;
	}

	public void setConstant(String name, Value value) {
		scope.getFirst().put(name, value);
	}

	public void pushTree(JSON tree) {
		trees.addFirst(tree);
	}

	public void popTree() {
		trees.removeFirst();
	}

	public void updateTree(JSON tree) {
		trees.removeFirst();
		trees.addFirst(tree);
	}

	public JSON getTree(int level) {
		return level < 0 || level >= trees.size() ? null : trees.get(level);
	}

}
