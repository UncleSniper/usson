package org.unclesniper.json.lens;

import org.unclesniper.json.j8.IntP;
import org.unclesniper.json.j8.LongP;
import org.unclesniper.json.j8.FloatP;
import org.unclesniper.json.j8.ShortP;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.DoubleP;
import org.unclesniper.json.j8.IntBound;
import org.unclesniper.json.j8.IntGetter;
import org.unclesniper.json.j8.LongGetter;
import org.unclesniper.json.j8.FloatGetter;
import org.unclesniper.json.j8.IntIterable;
import org.unclesniper.json.j8.ShortGetter;
import org.unclesniper.json.j8.DoubleGetter;
import org.unclesniper.json.j8.LongIterable;
import org.unclesniper.json.j8.ObjectGetter;
import org.unclesniper.json.j8.BooleanGetter;
import org.unclesniper.json.j8.FloatIterable;
import org.unclesniper.json.j8.ShortIterable;
import org.unclesniper.json.j8.OrderRelation;
import org.unclesniper.json.j8.DoubleIterable;
import org.unclesniper.json.j8.BooleanIterable;

public class JSONizers {

	private JSONizers() {}

	public static <ValueT> JSONizer<ValueT> array(SequenceJSONizer<? super ValueT>... generators) {
		return JSONizers.array(null, generators);
	}

	public static <ValueT> JSONizer<ValueT> array(ObjectP<? super ValueT> needed,
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

	public static final JSONizer<BooleanIterable> booleanArray = new BooleanArrayJSONizer(null, null);

	public static JSONizer<BooleanIterable> booleanArray(ObjectP<? super BooleanIterable> needed) {
		return new BooleanArrayJSONizer(needed, null);
	}

	public static JSONizer<BooleanIterable> booleanArray(StaticJSON ifEmpty) {
		return new BooleanArrayJSONizer(null, ifEmpty);
	}

	public static JSONizer<BooleanIterable> booleanArray(ObjectP<? super BooleanIterable> needed,
			StaticJSON ifEmpty) {
		return new BooleanArrayJSONizer(needed, ifEmpty);
	}

	public static final JSONizer<Boolean> booleanValue = BooleanJSONizer.instance;

	public static JSONizer<Boolean> booleanValue(ObjectP<? super Boolean> needed) {
		return new BooleanJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> booleanProperty(String name, BooleanGetter<? super BaseT> getter) {
		return new BooleanPropertyInnerJSONizer<BaseT>(name, getter, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> booleanProperty(String name, BooleanGetter<? super BaseT> getter,
			ObjectP<? super BaseT> needed) {
		return new BooleanPropertyInnerJSONizer<BaseT>(name, getter, needed);
	}

	public static <BaseT> JSONizer<BaseT> booleanProperty(BooleanGetter<? super BaseT> getter) {
		return new BooleanPropertyJSONizer<BaseT>(getter);
	}

	public static final JSONizer<DoubleIterable> doubleArray = new DoubleArrayJSONizer(null, null, null);

	public static JSONizer<DoubleIterable> doubleArray(ObjectP<? super DoubleIterable> needed) {
		return new DoubleArrayJSONizer(needed, null, null);
	}

	public static JSONizer<DoubleIterable> doubleArray(StaticJSON ifEmpty) {
		return new DoubleArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<DoubleIterable> doubleArray(DoubleP filter) {
		return new DoubleArrayJSONizer(null, null, filter);
	}

	public static JSONizer<DoubleIterable> doubleArray(ObjectP<? super DoubleIterable> needed,
			StaticJSON ifEmpty) {
		return new DoubleArrayJSONizer(needed, ifEmpty, null);
	}

	public static JSONizer<DoubleIterable> doubleArray(ObjectP<? super DoubleIterable> needed, DoubleP filter) {
		return new DoubleArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<DoubleIterable> doubleArray(StaticJSON ifEmpty, DoubleP filter) {
		return new DoubleArrayJSONizer(null, ifEmpty, filter);
	}

	public static JSONizer<DoubleIterable> doubleArray(ObjectP<? super DoubleIterable> needed,
			StaticJSON ifEmpty, DoubleP filter) {
		return new DoubleArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<Double> doubleValue = DoubleJSONizer.instance;

	public static JSONizer<Double> doubleValue(ObjectP<? super Double> needed) {
		return new DoubleJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> doubleProperty(String name, DoubleGetter<? super BaseT> getter) {
		return new DoublePropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> doubleProperty(String name, DoubleGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded) {
		return new DoublePropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> doubleProperty(String name, DoubleGetter<? super BaseT> getter,
			DoubleP innerNeeded) {
		return new DoublePropertyInnerJSONizer<BaseT>(name, getter, null, innerNeeded);
	}

	public static <BaseT> InnerJSONizer<BaseT> doubleProperty(String name, DoubleGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, DoubleP innerNeeded) {
		return new DoublePropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> doubleProperty(DoubleGetter<? super BaseT> getter) {
		return new DoublePropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> doubleProperty(DoubleGetter<? super BaseT> getter, DoubleP needed) {
		return new DoublePropertyJSONizer<BaseT>(getter, needed);
	}

	public static final StaticJSON emptyArray = EmptyArray.instance;

	public static <ValueT> SequenceJSONizer<ValueT> emptySequence() {
		return new EmptySequenceJSONizer<ValueT>();
	}

	public static final JSONizer<FloatIterable> floatArray = new FloatArrayJSONizer(null, null, null);

	public static JSONizer<FloatIterable> floatArray(ObjectP<? super FloatIterable> needed) {
		return new FloatArrayJSONizer(needed, null, null);
	}

	public static JSONizer<FloatIterable> floatArray(StaticJSON ifEmpty) {
		return new FloatArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<FloatIterable> floatArray(FloatP filter) {
		return new FloatArrayJSONizer(null, null, filter);
	}

	public static JSONizer<FloatIterable> floatArray(ObjectP<? super FloatIterable> needed, StaticJSON ifEmpty) {
		return new FloatArrayJSONizer(needed, ifEmpty, null);
	}

	public static JSONizer<FloatIterable> floatArray(ObjectP<? super FloatIterable> needed, FloatP filter) {
		return new FloatArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<FloatIterable> floatArray(StaticJSON ifEmpty, FloatP filter) {
		return new FloatArrayJSONizer(null, ifEmpty, filter);
	}

	public static JSONizer<FloatIterable> floatArray(ObjectP<? super FloatIterable> needed,
			StaticJSON ifEmpty, FloatP filter) {
		return new FloatArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<Float> floatValue = FloatJSONizer.instance;

	public static JSONizer<Float> floatValue(ObjectP<? super Float> needed) {
		return new FloatJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> floatProperty(String name, FloatGetter<? super BaseT> getter) {
		return new FloatPropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> floatProperty(String name, FloatGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded) {
		return new FloatPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> floatProperty(String name, FloatGetter<? super BaseT> getter,
			FloatP innerNeeded) {
		return new FloatPropertyInnerJSONizer<BaseT>(name, getter, null, innerNeeded);
	}

	public static <BaseT> InnerJSONizer<BaseT> floatProperty(String name, FloatGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, FloatP innerNeeded) {
		return new FloatPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> floatProperty(FloatGetter<? super BaseT> getter) {
		return new FloatPropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> floatProperty(FloatGetter<? super BaseT> getter, FloatP needed) {
		return new FloatPropertyJSONizer<BaseT>(getter, needed);
	}

	public static final JSONizer<IntIterable> intArray = new IntegerArrayJSONizer(null, null, null);

	public static JSONizer<IntIterable> intArray(ObjectP<? super IntIterable> needed) {
		return new IntegerArrayJSONizer(needed, null, null);
	}

	public static JSONizer<IntIterable> intArray(StaticJSON ifEmpty) {
		return new IntegerArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<IntIterable> intArray(IntP filter) {
		return new IntegerArrayJSONizer(null, null, filter);
	}

	public static JSONizer<IntIterable> intArray(ObjectP<? super IntIterable> needed, StaticJSON ifEmpty) {
		return new IntegerArrayJSONizer(needed, ifEmpty, null);
	}

	public static JSONizer<IntIterable> intArray(ObjectP<? super IntIterable> needed, IntP filter) {
		return new IntegerArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<IntIterable> intArray(StaticJSON ifEmpty, IntP filter) {
		return new IntegerArrayJSONizer(null, ifEmpty, filter);
	}

	public static JSONizer<IntIterable> intArray(ObjectP<? super IntIterable> needed,
			StaticJSON ifEmpty, IntP filter) {
		return new IntegerArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<Integer> intValue = IntegerJSONizer.instance;

	public static JSONizer<Integer> intValue(ObjectP<? super Integer> needed) {
		return new IntegerJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> intProperty(String name, IntGetter<? super BaseT> getter) {
		return new IntegerPropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> intProperty(String name, IntGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded) {
		return new IntegerPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> intProperty(String name, IntGetter<? super BaseT> getter,
			IntP innerNeeded) {
		return new IntegerPropertyInnerJSONizer<BaseT>(name, getter, null, innerNeeded);
	}

	public static <BaseT> InnerJSONizer<BaseT> intProperty(String name, IntGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, IntP innerNeeded) {
		return new IntegerPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> intProperty(IntGetter<? super BaseT> getter) {
		return new IntegerPropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> intProperty(IntGetter<? super BaseT> getter, IntP needed) {
		return new IntegerPropertyJSONizer<BaseT>(getter, needed);
	}

	public static final JSONizer<LongIterable> longArray = new LongArrayJSONizer(null, null, null);

	public static JSONizer<LongIterable> longArray(ObjectP<? super LongIterable> needed) {
		return new LongArrayJSONizer(needed, null, null);
	}

	public static JSONizer<LongIterable> longArray(StaticJSON ifEmpty) {
		return new LongArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<LongIterable> longArray(LongP filter) {
		return new LongArrayJSONizer(null, null, filter);
	}

	public static JSONizer<LongIterable> longArray(ObjectP<? super LongIterable> needed, StaticJSON ifEmpty) {
		return new LongArrayJSONizer(needed, ifEmpty, null);
	}

	public static JSONizer<LongIterable> longArray(ObjectP<? super LongIterable> needed, LongP filter) {
		return new LongArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<LongIterable> longArray(StaticJSON ifEmpty, LongP filter) {
		return new LongArrayJSONizer(null, ifEmpty, filter);
	}

	public static JSONizer<LongIterable> longArray(ObjectP<? super LongIterable> needed,
			StaticJSON ifEmpty, LongP filter) {
		return new LongArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<Long> longValue = LongJSONizer.instance;

	public static JSONizer<Long> longValue(ObjectP<? super Long> needed) {
		return new LongJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> longProperty(String name, LongGetter<? super BaseT> getter) {
		return new LongPropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> longProperty(String name, LongGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded) {
		return new LongPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> longProperty(String name, LongGetter<? super BaseT> getter,
			LongP innerNeeded) {
		return new LongPropertyInnerJSONizer<BaseT>(name, getter, null, innerNeeded);
	}

	public static <BaseT> InnerJSONizer<BaseT> longProperty(String name, LongGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, LongP innerNeeded) {
		return new LongPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> longProperty(LongGetter<? super BaseT> getter) {
		return new LongPropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> longProperty(LongGetter<? super BaseT> getter, LongP needed) {
		return new LongPropertyJSONizer<BaseT>(getter, needed);
	}

	public static <ValueT> JSONizer<ValueT> nullValue() {
		return new NullJSONizer<ValueT>();
	}

	public static <ElementT> JSONizer<Iterable<? extends ElementT>>
	objectArray(JSONizer<? super ElementT> jsonizer) {
		return new ObjectArrayJSONizer<ElementT>(jsonizer, null, null, null);
	}

	public static <ElementT> JSONizer<Iterable<? extends ElementT>>
	objectArray(JSONizer<? super ElementT> jsonizer, ObjectP<? super Iterable<? extends ElementT>> needed,
			ObjectP<? super ElementT> filter) {
		return new ObjectArrayJSONizer<ElementT>(jsonizer, needed, null, filter);
	}

	public static <ElementT> JSONizer<Iterable<? extends ElementT>>
	objectArray(JSONizer<? super ElementT> jsonizer, StaticJSON ifEmpty) {
		return new ObjectArrayJSONizer<ElementT>(jsonizer, null, ifEmpty, null);
	}

	public static <ElementT> JSONizer<Iterable<? extends ElementT>>
	objectArray(JSONizer<? super ElementT> jsonizer, ObjectP<? super Iterable<? extends ElementT>> needed,
			StaticJSON ifEmpty, ObjectP<? super ElementT> filter) {
		return new ObjectArrayJSONizer<ElementT>(jsonizer, needed, ifEmpty, filter);
	}

	public static <BaseT> JSONizer<BaseT> object(InnerJSONizer<? super BaseT>... properties) {
		return new ObjectJSONizer<BaseT>(properties);
	}

	public static <BaseT> JSONizer<BaseT> object(ObjectP<? super BaseT> needed,
			InnerJSONizer<? super BaseT>... properties) {
		return new ObjectJSONizer<BaseT>(needed, properties);
	}

	public static <BaseT, PropertyT> InnerJSONizer<BaseT> objectProperty(String name,
			ObjectGetter<? super BaseT, ? extends PropertyT> getter, JSONizer<? super PropertyT> jsonizer) {
		return new ObjectPropertyInnerJSONizer<BaseT, PropertyT>(name, getter, null, null, jsonizer);
	}

	public static <BaseT, PropertyT> InnerJSONizer<BaseT> objectProperty(String name,
			ObjectGetter<? super BaseT, ? extends PropertyT> getter, ObjectP<? super BaseT> outerNeeded,
			ObjectP<? super PropertyT> innerNeeded, JSONizer<? super PropertyT> jsonizer) {
		return new ObjectPropertyInnerJSONizer<BaseT, PropertyT>(name, getter, outerNeeded, innerNeeded, jsonizer);
	}

	public static <BaseT, PropertyT> JSONizer<BaseT>
	objectProperty(ObjectGetter<? super BaseT, ? extends PropertyT> getter, JSONizer<? super PropertyT> jsonizer) {
		return new ObjectPropertyJSONizer<BaseT, PropertyT>(getter, null, jsonizer);
	}

	public static <BaseT, PropertyT> JSONizer<BaseT>
	objectProperty(ObjectGetter<? super BaseT, ? extends PropertyT> getter, ObjectP<? super BaseT> needed,
			JSONizer<? super PropertyT> jsonizer) {
		return new ObjectPropertyJSONizer<BaseT, PropertyT>(getter, needed, jsonizer);
	}

	public static <BaseT> InnerJSONizer<BaseT> property(String name, JSONizer<? super BaseT> property) {
		return new PropertyInnerJSONizer<BaseT>(name, property);
	}

	public static <BaseT> InnerJSONizer<BaseT> property(String name, ObjectP<? super BaseT> needed,
			JSONizer<? super BaseT> property) {
		return new PropertyInnerJSONizer<BaseT>(name, property, needed);
	}

	public static final JSONizer<ShortIterable> shortArray = new ShortArrayJSONizer(null, null, null);

	public static JSONizer<ShortIterable> shortArray(ObjectP<? super ShortIterable> needed) {
		return new ShortArrayJSONizer(needed, null, null);
	}

	public static JSONizer<ShortIterable> shortArray(StaticJSON ifEmpty) {
		return new ShortArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<ShortIterable> shortArray(ShortP filter) {
		return new ShortArrayJSONizer(null, null, filter);
	}

	public static JSONizer<ShortIterable> shortArray(ObjectP<? super ShortIterable> needed, StaticJSON ifEmpty) {
		return new ShortArrayJSONizer(needed, ifEmpty, null);
	}

	public static JSONizer<ShortIterable> shortArray(ObjectP<? super ShortIterable> needed, ShortP filter) {
		return new ShortArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<ShortIterable> shortArray(StaticJSON ifEmpty, ShortP filter) {
		return new ShortArrayJSONizer(null, ifEmpty, filter);
	}

	public static JSONizer<ShortIterable> shortArray(ObjectP<? super ShortIterable> needed,
			StaticJSON ifEmpty, ShortP filter) {
		return new ShortArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<Short> shortValue = ShortJSONizer.instance;

	public static JSONizer<Short> shortValue(ObjectP<? super Short> needed) {
		return new ShortJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> shortProperty(String name, ShortGetter<? super BaseT> getter) {
		return new ShortPropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> shortProperty(String name, ShortGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded) {
		return new ShortPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> shortProperty(String name, ShortGetter<? super BaseT> getter,
			ShortP innerNeeded) {
		return new ShortPropertyInnerJSONizer<BaseT>(name, getter, null, innerNeeded);
	}

	public static <BaseT> InnerJSONizer<BaseT> shortProperty(String name, ShortGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, ShortP innerNeeded) {
		return new ShortPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> shortProperty(ShortGetter<? super BaseT> getter) {
		return new ShortPropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> shortProperty(ShortGetter<? super BaseT> getter, ShortP needed) {
		return new ShortPropertyJSONizer<BaseT>(getter, needed);
	}

	public static final JSONizer<Iterable<String>> stringArray = new StringArrayJSONizer(null, null, null);

	public static JSONizer<Iterable<String>> stringArray(ObjectP<? super Iterable<String>> needed,
			ObjectP<? super String> filter) {
		return new StringArrayJSONizer(needed, null, filter);
	}

	public static JSONizer<Iterable<String>> stringArray(StaticJSON ifEmpty) {
		return new StringArrayJSONizer(null, ifEmpty, null);
	}

	public static JSONizer<Iterable<String>> stringArray(ObjectP<? super Iterable<String>> needed,
			StaticJSON ifEmpty, ObjectP<? super String> filter) {
		return new StringArrayJSONizer(needed, ifEmpty, filter);
	}

	public static final JSONizer<String> string = StringJSONizer.instance;

	public static JSONizer<String> string(ObjectP<? super String> needed) {
		return new StringJSONizer(needed);
	}

	public static <BaseT> InnerJSONizer<BaseT> stringProperty(String name,
			ObjectGetter<? super BaseT, String> getter) {
		return new StringPropertyInnerJSONizer<BaseT>(name, getter, null, null);
	}

	public static <BaseT> InnerJSONizer<BaseT> stringProperty(String name,
			ObjectGetter<? super BaseT, String> getter, ObjectP<? super BaseT> outerNeeded,
			ObjectP<? super String> innerNeeded) {
		return new StringPropertyInnerJSONizer<BaseT>(name, getter, outerNeeded, innerNeeded);
	}

	public static <BaseT> JSONizer<BaseT> stringProperty(ObjectGetter<? super BaseT, String> getter) {
		return new StringPropertyJSONizer<BaseT>(getter, null);
	}

	public static <BaseT> JSONizer<BaseT> stringProperty(ObjectGetter<? super BaseT, String> getter,
			ObjectP<? super String> needed) {
		return new StringPropertyJSONizer<BaseT>(getter, needed);
	}

	public static <ValueT> InnerJSONizer<ValueT> ifVersion(IntP predicate,
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

	public static <ValueT> JSONizer<ValueT> ifVersionElseNull(IntP predicate,
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

	public static <ValueT> InnerJSONizer<ValueT> versionSplit(IntP predicate,
			InnerJSONizer<? super ValueT> ifFalse, InnerJSONizer<? super ValueT> ifTrue) {
		return new VersionSplitInnerJSONizer<ValueT>(predicate, ifFalse, ifTrue);
	}

	public static <ValueT> InnerJSONizer<ValueT> versionSplit(int threshold,
			InnerJSONizer<? super ValueT> ifLower, InnerJSONizer<? super ValueT> ifHigherOrEqual) {
		return new VersionSplitInnerJSONizer<ValueT>(new IntBound(threshold, OrderRelation.GE),
				ifLower, ifHigherOrEqual);
	}

	public static <ValueT> JSONizer<ValueT> versionSplit(IntP predicate,
			JSONizer<? super ValueT> ifFalse, JSONizer<? super ValueT> ifTrue) {
		return new VersionSplitJSONizer<ValueT>(predicate, ifFalse, ifTrue);
	}

	public static <ValueT> JSONizer<ValueT> versionSplit(int threshold,
			JSONizer<? super ValueT> ifLower, JSONizer<? super ValueT> ifHigherOrEqual) {
		return new VersionSplitJSONizer<ValueT>(new IntBound(threshold, OrderRelation.GE),
				ifLower, ifHigherOrEqual);
	}

}
