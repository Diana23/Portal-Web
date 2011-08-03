package com.cablevision.portal.sitemap.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic iterator that works against an array.
 */
public class ArrayIterator<T> implements Iterator<T> {

	private T[] array;

	private int index = 0;

	public ArrayIterator(T[] array) {
		if (array == null) {
			throw new IllegalArgumentException(
					"Cannot create iterator with a null array");
		}
		this.array = array;
	}

	public boolean hasNext() {
		return index < array.length;
	}

	public T next() {
		if (index >= array.length) {
			throw new NoSuchElementException("Current index is " + index
					+ " while only " + array.length + " elements are present ");
		}
		T result = array[index];
		index++;
		return result;
	}

	public void remove() {
		throw new UnsupportedOperationException(
				"This operation is not supported.");
	}
}
