package org.hansib.sundries.prefs;

import java.io.File;
import java.math.BigDecimal;

class Initializer {

	static final Initializer INSTANCE = new Initializer();

	<V, K extends Enum<K>, P extends Pref<K, V>> P withInitial(P pref, V initialValue) {
		if (pref.store().get(pref) == null)
			pref.store().set(pref, initialValue);
		return pref;
	}
}

public interface Prefs<K extends Enum<K>> {

	<V> V get(Pref<K, V> pref);

	<V> void set(Pref<K, V> pref, V value);

	void remove(OptionalPref<K, ?> pref);

	/*
	 * factory methods
	 */

	default OptString<K> optionalString(K key) {
		return new OptString<>(key, this);
	}

	default ReqString<K> requiredString(K key, String initialValue) {
		return Initializer.INSTANCE.withInitial(new ReqString<>(key, this), initialValue);
	}

	default OptBoolean<K> optionalBoolean(K key) {
		return new OptBoolean<>(key, this);
	}

	default ReqBoolean<K> requiredBoolean(K key, boolean initialValue) {
		return Initializer.INSTANCE.withInitial(new ReqBoolean<>(key, this), initialValue);
	}

	default OptInteger<K> optionalInteger(K key) {
		return new OptInteger<>(key, this);
	}

	default ReqInteger<K> requiredInteger(K key, int initialValue) {
		return Initializer.INSTANCE.withInitial(new ReqInteger<>(key, this), initialValue);
	}

	default OptBigDecimal<K> optionalBigDecimal(K key) {
		return new OptBigDecimal<>(key, this);
	}

	default ReqBigDecimal<K> requiredBigDecimal(K key, BigDecimal initialValue) {
		return Initializer.INSTANCE.withInitial(new ReqBigDecimal<>(key, this), initialValue);
	}

	default <L extends Enum<L>> OptEnum<K, L> optionalEnum(K key, Class<L> valueClass) {
		return new OptEnum<>(key, valueClass, this);
	}

	default <L extends Enum<L>> ReqEnum<K, L> requiredEnum(K key, Class<L> valueClass, L initialValue) {
		return Initializer.INSTANCE.withInitial(new ReqEnum<>(key, valueClass, this), initialValue);
	}

	default OptFile<K> optionalFile(K key) {
		return new OptFile<>(key, this);
	}

	default ReqFile<K> requiredFile(K key, File initialValue) {
		return Initializer.INSTANCE.withInitial(new ReqFile<>(key, this), initialValue);
	}
}
