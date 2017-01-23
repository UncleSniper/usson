package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSON;
import org.unclesniper.json.JSONString;
import org.unclesniper.json.JSONInteger;
import org.unclesniper.json.JSONBoolean;
import org.unclesniper.json.JSONFraction;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.NotAJSONValueException;
import org.unclesniper.json.tool.TransformationException;

public abstract class Transform extends Syntax {

	public Transform(int offset) {
		super(offset);
	}

	public Value transform(TransformationContext context, Value input) throws TransformationException {
		JSON baked;
		if(requiresTuT()) {
			baked = Transform.bake(input, getDesiredTuTType(), prefersTuTArray());
			if(baked == null)
				throw new NotAJSONValueException(getOffset(), input, "transform value");
		}
		else
			baked = null;
		context.pushTree(baked);
		try {
			return doTransform(context, baked, input);
		}
		finally {
			context.popTree();
		}
	}

	public Value transform(TransformationContext context, JSON tree) throws TransformationException {
		context.pushTree(tree);
		try {
			return doTransform(context, tree, new JSONValue(tree));
		}
		finally {
			context.popTree();
		}
	}

	protected boolean requiresTuT() {
		return true;
	}

	protected abstract int getDesiredTuTType();

	protected boolean prefersTuTArray() {
		return false;
	}

	protected abstract Value doTransform(TransformationContext context, JSON tree, Value input)
			throws TransformationException;

	public static JSON bake(Value value, int desiredType, boolean preferArray) {
		switch(value.getType()) {
			case JSON:
				return ((JSONValue)value).getJSON();
			case PROPERTY_COMPONENT_SET:
				//TODO
			case INDEX_COMPONENT_SET:
				//TODO
			case MIXED_COMPONENT_SET:
				//TODO
			default:
				return null;
		}
	}

	public static String stringOf(JSON tree) {
		switch(tree.getJSONType()) {
			case JSON.TYPE_STRING:
				return ((JSONString)tree).stringValue();
			case JSON.TYPE_INTEGER:
				return String.valueOf(((JSONInteger)tree).longValue());
			case JSON.TYPE_FRACTION:
				return String.valueOf(((JSONFraction)tree).doubleValue());
			case JSON.TYPE_BOOLEAN:
				return String.valueOf(((JSONBoolean)tree).booleanValue());
			case JSON.TYPE_NULL:
				return "null";
			default:
				return null;
		}
	}

	public static String stringOf(Value value) {
		switch(value.getType()) {
			case JSON:
				return Transform.stringOf(((JSONValue)value).getJSON());
			default:
				return null;
		}
	}

}
