/*
 * Owl Platform Common Library for Java
 * Copyright (C) 2012 Robert Moore and the Owl Platform
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.owlplatform.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple implementation of an LRU cache based on LinkedHashMap.  Idea provided by Hank Gay on StackOverflow.com
 * 
 * Sourced from http://stackoverflow.com/questions/221525/how-would-you-implement-an-lru-cache-in-java-6
 * 
 * @author <a href="http://stackoverflow.com/users/4203/hank-gay">Hank Gay</a>
 * @author Robert Moore II
 *
 * @param <K> 
 * @param <V>
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
	
	/**
	 * To be updated when the class members change.
	 */
  private static final long serialVersionUID = 5148706907508646895L;
 
  /**
	 * Maximum capacity of the cache.
	 */
	private final int capacity;
	
	/**
	 * Creates a new LRU cache with the specified capacity.
	 * @param capacity the maximum capacity for this cache.
	 */
	public LRUCache(int capacity)
	{
		super(capacity+1, 1.0f, true);
		this.capacity = capacity;
	}

	@Override
	protected boolean removeEldestEntry(final Map.Entry<K, V> entry)
	{
		return super.size() > this.capacity;
	}
}
