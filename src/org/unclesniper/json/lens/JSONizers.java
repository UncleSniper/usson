package org.unclesniper.json.lens;

import org.unclesniper.json.j8.IOIntP;
import org.unclesniper.json.j8.IOLongP;
import org.unclesniper.json.j8.IntBound;
import org.unclesniper.json.j8.IOFloatP;
import org.unclesniper.json.j8.IOShortP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IODoubleP;
import org.unclesniper.json.j8.IOIntGetter;
import org.unclesniper.json.j8.IOLongGetter;
import org.unclesniper.json.j8.OrderRelation;
import org.unclesniper.json.j8.IOIntIterable;
import org.unclesniper.json.j8.IOFloatGetter;
import org.unclesniper.json.j8.IOShortGetter;
import org.unclesniper.json.j8.IOLongIterable;
import org.unclesniper.json.j8.IOObjectGetter;
import org.unclesniper.json.j8.IODoubleGetter;
import org.unclesniper.json.j8.IOFloatIterable;
import org.unclesniper.json.j8.IOShortIterable;
import org.unclesniper.json.j8.IOBooleanGetter;
import org.unclesniper.json.j8.IOObjectIterable;
import org.unclesniper.json.j8.IODoubleIterable;
import org.unclesniper.json.j8.IOBooleanIterable;

public class JSONizers {

	private JSONizers() {}

	public static <ValueT> JSONizer<ValueT> array(SequenceJSONizer<? super ValueT>... generators) {
		return JSONizers.array(null, generators);
	}

	public static <ValueT> JSONizer<ValueT> array(IOObjectP<? super ValueT> needed,
			SequenceJSONizer<? super ValueT>... generators) {
		SequenceJSONizer<? super ValueT> generator;
		if(generators == null || generators.length == 0)
			generator = null;
		else if(generators.length == 1)
			generator = generators[0];
		else
			generator = new MultiSequenceJSONizer<ValueT>(generators);
		return new ArrayJSONizer<ValueT>(generator, needed);
	}

	public static final JSONizer<IOBooleanIterable> booleanArray = new BooleanArrayJSONizer(null, null);

	public static JSONizer<IOBooleanIterable> booleanArray(IOObjectP<? super IOBooleanIterable> needed) {
		return new BooleanArrayJSONizer(needed, null);
	}

	public static JSONizer<IOBooleanIterable> booleanArray(StaticJSON ifEmpty) {
		return new BooleanArrayJSONizer(null, ifEmpty);
	}

	public static JSONizer<IOBooleanIterable> booleanArray(IOObjectP<? super IOBooleanIterable> needed,
			StaticJSON ifEmpty) {
		return new BooleanArrayJSONizer(needed, ifEmpty);
	}

	public static final JSONizer<Boolean> booleanValue = BooleanJSONizer.instance;

	public static JSONizer<Boolean> booleanValue(IOObjectP<? super Boolean> needed) {
		return new BooleanJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> booleanProperty(String name, IOBooleanGetter<? super BaseT> getter) {
		return new BooleanPropertyInnerJSONizer<BaseT>(name, getter, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> booleanProperty(String name, IOBooleanGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> needed) {
		return new BooleanPropertyInnerJSONizer<BaseT>(name, getter, needed);
	}

	public static <BaseT> JSONizer<BaseT> booleanProperty(IOBooleanGetter<? super BaseT> getter) {
		return new BooleanPropertyJSONizer<BaseT>(getter);
	}

	public static final JSONizer<IODoubleIterable> doubleArray = new DoubleArrayJSONizer(null, null, null);

	public static JSONizer<IODoubleIterable> doubleArray(IOObjectP<? super IODoubleIterable> needed) {
		return new DoubleArrayJSONizer(needed, null, null);
	}

	public static JSONizer<IODoubleIterable> doubleArray(StaticJSON ifEmpty) {
		return new DoubleArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<IODoubleIterable> doubleArray(IODoubleP filter) {
		return new DoubleArrayJSONizer(null, null, filter);
	}

	public static JSONizer<IODoubleIterable> doubleArray(IOObjectP<? super IODoubleIterable> needed,
			StaticJSON ifEmpty) {
		return new DoubleArrayJSONizer(needed, ifEmpty, null);
	}

	public static JSONizer<IODoubleIterable> doubleArray(IOObjectP<? super IODoubleIterable> needed,
			IODoubleP filter) {
		return new DoubleArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<IODoubleIterable> doubleArray(StaticJSON ifEmpty, IODoubleP filter) {
		return new DoubleArrayJSONizer(null, ifEmpty, filter);
	}

	public static JSONizer<IODoubleIterable> doubleArray(IOObjectP<? super IODoubleIterable> needed,
			StaticJSON ifEmpty, IODoubleP filter) {
		return new DoubleArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<Double> doubleValue = DoubleJSONizer.instance;

	public static JSONizer<Double> doubleValue(IOObjectP<? super Double> needed) {
		return new DoubleJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> doubleProperty(String name, IODoubleGetter<? super BaseT> getter) {
		return new DoublePropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> doubleProperty(String name, IODoubleGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded) {
		return new DoublePropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> doubleProperty(String name, IODoubleGetter<? super BaseT> getter,
			IODoubleP innerNeeded) {
		return new DoublePropertyInnerJSONizer<BaseT>(name, getter, null, innerNeeded);
	}

	public static <BaseT> InnerJSONizer<BaseT> doubleProperty(String name, IODoubleGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IODoubleP innerNeeded) {
		return new DoublePropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> doubleProperty(IODoubleGetter<? super BaseT> getter) {
		return new DoublePropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> doubleProperty(IODoubleGetter<? super BaseT> getter, IODoubleP needed) {
		return new DoublePropertyJSONizer<BaseT>(getter, needed);
	}

	public static final StaticJSON emptyArray = EmptyArray.instance;

	public static <ValueT> SequenceJSONizer<ValueT> emptySequence() {
		return new EmptySequenceJSONizer<ValueT>();
	}

	public static final JSONizer<IOFloatIterable> floatArray = new FloatArrayJSONizer(null, null, null);

	public static JSONizer<IOFloatIterable> floatArray(IOObjectP<? super IOFloatIterable> needed) {
		return new FloatArrayJSONizer(needed, null, null);
	}

	public static JSONizer<IOFloatIterable> floatArray(StaticJSON ifEmpty) {
		return new FloatArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<IOFloatIterable> floatArray(IOFloatP filter) {
		return new FloatArrayJSONizer(null, null, filter);
	}

	public static JSONizer<IOFloatIterable> floatArray(IOObjectP<? super IOFloatIterable> needed,
			StaticJSON ifEmpty) {
		return new FloatArrayJSONizer(needed, ifEmpty, null);
	}

	public static JSONizer<IOFloatIterable> floatArray(IOObjectP<? super IOFloatIterable> needed, IOFloatP filter) {
		return new FloatArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<IOFloatIterable> floatArray(StaticJSON ifEmpty, IOFloatP filter) {
		return new FloatArrayJSONizer(null, ifEmpty, filter);
	}

	public static JSONizer<IOFloatIterable> floatArray(IOObjectP<? super IOFloatIterable> needed,
			StaticJSON ifEmpty, IOFloatP filter) {
		return new FloatArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<Float> floatValue = FloatJSONizer.instance;

	public static JSONizer<Float> floatValue(IOObjectP<? super Float> needed) {
		return new FloatJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> floatProperty(String name, IOFloatGetter<? super BaseT> getter) {
		return new FloatPropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> floatProperty(String name, IOFloatGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded) {
		return new FloatPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> floatProperty(String name, IOFloatGetter<? super BaseT> getter,
			IOFloatP innerNeeded) {
		return new FloatPropertyInnerJSONizer<BaseT>(name, getter, null, innerNeeded);
	}

	public static <BaseT> InnerJSONizer<BaseT> floatProperty(String name, IOFloatGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IOFloatP innerNeeded) {
		return new FloatPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> floatProperty(IOFloatGetter<? super BaseT> getter) {
		return new FloatPropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> floatProperty(IOFloatGetter<? super BaseT> getter, IOFloatP needed) {
		return new FloatPropertyJSONizer<BaseT>(getter, needed);
	}

	public static final JSONizer<IOIntIterable> intArray = new IntegerArrayJSONizer(null, null, null);

	public static JSONizer<IOIntIterable> intArray(IOObjectP<? super IOIntIterable> needed) {
		return new IntegerArrayJSONizer(needed, null, null);
	}

	public static JSONizer<IOIntIterable> intArray(StaticJSON ifEmpty) {
		return new IntegerArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<IOIntIterable> intArray(IOIntP filter) {
		return new IntegerArrayJSONizer(null, null, filter);
	}

	public static JSONizer<IOIntIterable> intArray(IOObjectP<? super IOIntIterable> needed, StaticJSON ifEmpty) {
		return new IntegerArrayJSONizer(needed, ifEmpty, null);
	}

	public static JSONizer<IOIntIterable> intArray(IOObjectP<? super IOIntIterable> needed, IOIntP filter) {
		return new IntegerArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<IOIntIterable> intArray(StaticJSON ifEmpty, IOIntP filter) {
		return new IntegerArrayJSONizer(null, ifEmpty, filter);
	}

	public static JSONizer<IOIntIterable> intArray(IOObjectP<? super IOIntIterable> needed,
			StaticJSON ifEmpty, IOIntP filter) {
		return new IntegerArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<Integer> intValue = IntegerJSONizer.instance;

	public static JSONizer<Integer> intValue(IOObjectP<? super Integer> needed) {
		return new IntegerJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> intProperty(String name, IOIntGetter<? super BaseT> getter) {
		return new IntegerPropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> intProperty(String name, IOIntGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded) {
		return new IntegerPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> intProperty(String name, IOIntGetter<? super BaseT> getter,
			IOIntP innerNeeded) {
		return new IntegerPropertyInnerJSONizer<BaseT>(name, getter, null, innerNeeded);
	}

	public static <BaseT> InnerJSONizer<BaseT> intProperty(String name, IOIntGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IOIntP innerNeeded) {
		return new IntegerPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> intProperty(IOIntGetter<? super BaseT> getter) {
		return new IntegerPropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> intProperty(IOIntGetter<? super BaseT> getter, IOIntP needed) {
		return new IntegerPropertyJSONizer<BaseT>(getter, needed);
	}

	public static final JSONizer<IOLongIterable> longArray = new LongArrayJSONizer(null, null, null);

	public static JSONizer<IOLongIterable> longArray(IOObjectP<? super IOLongIterable> needed) {
		return new LongArrayJSONizer(needed, null, null);
	}

	public static JSONizer<IOLongIterable> longArray(StaticJSON ifEmpty) {
		return new LongArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<IOLongIterable> longArray(IOLongP filter) {
		return new LongArrayJSONizer(null, null, filter);
	}

	public static JSONizer<IOLongIterable> longArray(IOObjectP<? super IOLongIterable> needed, StaticJSON ifEmpty) {
		return new LongArrayJSONizer(needed, ifEmpty, null);
	}

	public static JSONizer<IOLongIterable> longArray(IOObjectP<? super IOLongIterable> needed, IOLongP filter) {
		return new LongArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<IOLongIterable> longArray(StaticJSON ifEmpty, IOLongP filter) {
		return new LongArrayJSONizer(null, ifEmpty, filter);
	}

	public static JSONizer<IOLongIterable> longArray(IOObjectP<? super IOLongIterable> needed,
			StaticJSON ifEmpty, IOLongP filter) {
		return new LongArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<Long> longValue = LongJSONizer.instance;

	public static JSONizer<Long> longValue(IOObjectP<? super Long> needed) {
		return new LongJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> longProperty(String name, IOLongGetter<? super BaseT> getter) {
		return new LongPropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> longProperty(String name, IOLongGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded) {
		return new LongPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> longProperty(String name, IOLongGetter<? super BaseT> getter,
			IOLongP innerNeeded) {
		return new LongPropertyInnerJSONizer<BaseT>(name, getter, null, innerNeeded);
	}

	public static <BaseT> InnerJSONizer<BaseT> longProperty(String name, IOLongGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IOLongP innerNeeded) {
		return new LongPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> longProperty(IOLongGetter<? super BaseT> getter) {
		return new LongPropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> longProperty(IOLongGetter<? super BaseT> getter, IOLongP needed) {
		return new LongPropertyJSONizer<BaseT>(getter, needed);
	}

	public static <ValueT> JSONizer<ValueT> nullValue() {
		return new NullJSONizer<ValueT>();
	}

	public static <ElementT> JSONizer<IOObjectIterable<? extends ElementT>>
	objectArray(JSONizer<? super ElementT> jsonizer) {
		return new ObjectArrayJSONizer<ElementT>(jsonizer, null, null, null);
	}

	public static <ElementT> JSONizer<IOObjectIterable<? extends ElementT>>
	objectArray(JSONizer<? super ElementT> jsonizer, IOObjectP<? super IOObjectIterable<? extends ElementT>> needed,
			IOObjectP<? super ElementT> filter) {
		return new ObjectArrayJSONizer<ElementT>(jsonizer, needed, null, filter);
	}

	public static <ElementT> JSONizer<IOObjectIterable<? extends ElementT>>
	objectArray(JSONizer<? super ElementT> jsonizer, StaticJSON ifEmpty) {
		return new ObjectArrayJSONizer<ElementT>(jsonizer, null, ifEmpty, null);
	}

	public static <ElementT> JSONizer<IOObjectIterable<? extends ElementT>>
	objectArray(JSONizer<? super ElementT> jsonizer, IOObjectP<? super IOObjectIterable<? extends ElementT>> needed,
			StaticJSON ifEmpty, IOObjectP<? super ElementT> filter) {
		return new ObjectArrayJSONizer<ElementT>(jsonizer, needed, ifEmpty, filter);
	}

	public static <BaseT> JSONizer<BaseT> object(InnerJSONizer<? super BaseT>... properties) {
		return new ObjectJSONizer<BaseT>(properties);
	}

	public static <BaseT> JSONizer<BaseT> object(IOObjectP<? super BaseT> needed,
			InnerJSONizer<? super BaseT>... properties) {
		return new ObjectJSONizer<BaseT>(needed, properties);
	}

	public static <BaseT, PropertyT> InnerJSONizer<BaseT> objectProperty(String name,
			IOObjectGetter<? super BaseT, ? extends PropertyT> getter, JSONizer<? super PropertyT> jsonizer) {
		return new ObjectPropertyInnerJSONizer<BaseT, PropertyT>(name, getter, null, null, jsonizer);
	}

	public static <BaseT, PropertyT> InnerJSONizer<BaseT> objectProperty(String name,
			IOObjectGetter<? super BaseT, ? extends PropertyT> getter, IOObjectP<? super BaseT> outerNeeded,
			IOObjectP<? super PropertyT> innerNeeded, JSONizer<? super PropertyT> jsonizer) {
		return new ObjectPropertyInnerJSONizer<BaseT, PropertyT>(name, getter, outerNeeded, innerNeeded, jsonizer);
	}

	public static <BaseT, PropertyT> JSONizer<BaseT>
	objectProperty(IOObjectGetter<? super BaseT, ? extends PropertyT> getter, JSONizer<? super PropertyT> jsonizer) {
		return new ObjectPropertyJSONizer<BaseT, PropertyT>(getter, null, jsonizer);
	}

	public static <BaseT, PropertyT> JSONizer<BaseT>
	objectProperty(IOObjectGetter<? super BaseT, ? extends PropertyT> getter, IOObjectP<? super BaseT> needed,
			JSONizer<? super PropertyT> jsonizer) {
		return new ObjectPropertyJSONizer<BaseT, PropertyT>(getter, needed, jsonizer);
	}

	public static <BaseT> InnerJSONizer<BaseT> property(String name, JSONizer<? super BaseT> property) {
		return new PropertyInnerJSONizer<BaseT>(name, property);
	}

	public static <BaseT> InnerJSONizer<BaseT> property(String name, IOObjectP<? super BaseT> needed,
			JSONizer<? super BaseT> property) {
		return new PropertyInnerJSONizer<BaseT>(name, property, needed);
	}

	public static final JSONizer<IOShortIterable> shortArray = new ShortArrayJSONizer(null, null, null);

	public static JSONizer<IOShortIterable> shortArray(IOObjectP<? super IOShortIterable> needed) {
		return new ShortArrayJSONizer(needed, null, null);
	}

	public static JSONizer<IOShortIterable> shortArray(StaticJSON ifEmpty) {
		return new ShortArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<IOShortIterable> shortArray(IOShortP filter) {
		return new ShortArrayJSONizer(null, null, filter);
	}

	public static JSONizer<IOShortIterable> shortArray(IOObjectP<? super IOShortIterable> needed,
			StaticJSON ifEmpty) {
		return new ShortArrayJSONizer(needed, ifEmpty, null);
	}

	public static JSONizer<IOShortIterable> shortArray(IOObjectP<? super IOShortIterable> needed, IOShortP filter) {
		return new ShortArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<IOShortIterable> shortArray(StaticJSON ifEmpty, IOShortP filter) {
		return new ShortArrayJSONizer(null, ifEmpty, filter);
	}

	public static JSONizer<IOShortIterable> shortArray(IOObjectP<? super IOShortIterable> needed,
			StaticJSON ifEmpty, IOShortP filter) {
		return new ShortArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<Short> shortValue = ShortJSONizer.instance;

	public static JSONizer<Short> shortValue(IOObjectP<? super Short> needed) {
		return new ShortJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> shortProperty(String name, IOShortGetter<? super BaseT> getter) {
		return new ShortPropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> shortProperty(String name, IOShortGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded) {
		return new ShortPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> shortProperty(String name, IOShortGetter<? super BaseT> getter,
			IOShortP innerNeeded) {
		return new ShortPropertyInnerJSONizer<BaseT>(name, getter, null, innerNeeded);
	}

	public static <BaseT> InnerJSONizer<BaseT> shortProperty(String name, IOShortGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IOShortP innerNeeded) {
		return new ShortPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> shortProperty(IOShortGetter<? super BaseT> getter) {
		return new ShortPropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> shortProperty(IOShortGetter<? super BaseT> getter, IOShortP needed) {
		return new ShortPropertyJSONizer<BaseT>(getter, needed);
	}

	public static final JSONizer<IOObjectIterable<String>> stringArray = new StringArrayJSONizer(null, null, null);

	public static JSONizer<IOObjectIterable<String>> stringArray(IOObjectP<? super IOObjectIterable<String>> needed,
			IOObjectP<? super String> filter) {
		return new StringArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<IOObjectIterable<String>> stringArray(StaticJSON ifEmpty) {
		return new StringArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<IOObjectIterable<String>> stringArray(IOObjectP<? super IOObjectIterable<String>> needed,
			StaticJSON ifEmpty, IOObjectP<? super String> filter) {
		return new StringArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<String> string = StringJSONizer.instance;

	public static JSONizer<String> string(IOObjectP<? super String> needed) {
		return new StringJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> stringProperty(String name,
			IOObjectGetter<? super BaseT, String> getter) {
		return new StringPropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> stringProperty(String name,
			IOObjectGetter<? super BaseT, String> getter, IOObjectP<? super BaseT> outerNeeded,
			IOObjectP<? super String> innerNeeded) {
		return new StringPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> stringProperty(IOObjectGetter<? super BaseT, String> getter) {
		return new StringPropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> stringProperty(IOObjectGetter<? super BaseT, String> getter,
			IOObjectP<? super String> needed) {
		return new StringPropertyJSONizer<BaseT>(getter, needed);
	}

	public static <ValueT> InnerJSONizer<ValueT> ifVersion(IOIntP predicate,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundInnerJSONizer<ValueT>(predicate, jsonizer);
	}

	public static <ValueT> InnerJSONizer<ValueT> versionIs(int threshold,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundInnerJSONizer<ValueT>(new IntBound(threshold, OrderRelation.EQ), jsonizer);
	}

	public static <ValueT> InnerJSONizer<ValueT> versionBelow(int threshold,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundInnerJSONizer<ValueT>(new IntBound(threshold, OrderRelation.LT), jsonizer);
	}

	public static <ValueT> InnerJSONizer<ValueT> versionAtMost(int threshold,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundInnerJSONizer<ValueT>(new IntBound(threshold, OrderRelation.LE), jsonizer);
	}

	public static <ValueT> InnerJSONizer<ValueT> versionAbove(int threshold,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundInnerJSONizer<ValueT>(new IntBound(threshold, OrderRelation.GT), jsonizer);
	}

	public static <ValueT> InnerJSONizer<ValueT> versionAtLeast(int threshold,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundInnerJSONizer<ValueT>(new IntBound(threshold, OrderRelation.GE), jsonizer);
	}

	public static <ValueT> JSONizer<ValueT> ifVersionElseNull(IOIntP predicate,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundJSONizer<ValueT>(predicate, jsonizer);
	}

	public static <ValueT> JSONizer<ValueT> versionIsElseNull(int threshold,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundJSONizer<ValueT>(new IntBound(threshold, OrderRelation.EQ), jsonizer);
	}

	public static <ValueT> JSONizer<ValueT> versionBelowElseNull(int threshold,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundJSONizer<ValueT>(new IntBound(threshold, OrderRelation.LT), jsonizer);
	}

	public static <ValueT> JSONizer<ValueT> versionAtMostElseNull(int threshold,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundJSONizer<ValueT>(new IntBound(threshold, OrderRelation.LE), jsonizer);
	}

	public static <ValueT> JSONizer<ValueT> versionAboveElseNull(int threshold,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundJSONizer<ValueT>(new IntBound(threshold, OrderRelation.GT), jsonizer);
	}

	public static <ValueT> JSONizer<ValueT> versionAtLeastElseNull(int threshold,
			InnerJSONizer<? super ValueT> jsonizer) {
		return new VersionBoundJSONizer<ValueT>(new IntBound(threshold, OrderRelation.GE), jsonizer);
	}

	public static <ValueT> InnerJSONizer<ValueT> versionSplit(IOIntP predicate,
			InnerJSONizer<? super ValueT> ifFalse, InnerJSONizer<? super ValueT> ifTrue) {
		return new VersionSplitInnerJSONizer<ValueT>(predicate, ifFalse, ifTrue);
	}

	public static <ValueT> InnerJSONizer<ValueT> versionSplit(int threshold,
			InnerJSONizer<? super ValueT> ifLower, InnerJSONizer<? super ValueT> ifHigherOrEqual) {
		return new VersionSplitInnerJSONizer<ValueT>(new IntBound(threshold, OrderRelation.GE),
				ifLower, ifHigherOrEqual);
	}

	public static <ValueT> JSONizer<ValueT> versionSplit(IOIntP predicate,
			JSONizer<? super ValueT> ifFalse, JSONizer<? super ValueT> ifTrue) {
		return new VersionSplitJSONizer<ValueT>(predicate, ifFalse, ifTrue);
	}

	public static <ValueT> JSONizer<ValueT> versionSplit(int threshold,
			JSONizer<? super ValueT> ifLower, JSONizer<? super ValueT> ifHigherOrEqual) {
		return new VersionSplitJSONizer<ValueT>(new IntBound(threshold, OrderRelation.GE),
				ifLower, ifHigherOrEqual);
	}

}
