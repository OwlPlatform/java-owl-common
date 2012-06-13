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

import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for the LRUCache class.
 * @author Robert Moore
 *
 */
public class LRUCacheTest {

  /**
   * Set of Integer keys to use in the cache.
   */
  private static final Integer[] keys = new Integer[] { Integer.valueOf(0),
      Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(4),
      Integer.valueOf(3), Integer.valueOf(6), Integer.valueOf(5),
      Integer.valueOf(8) };
  /**
   * A set of String values to use in the cache.
   */
  private static final String[] values = new String[] { "Zero", "Two", "One",
      "Four", "Three", "Six", "Five", "Eight" };

  /**
   * Tests the LRUCache class.
   */
  @Test
  public void test() {

    // Test insertion order
    LRUCache<Integer, String> cache = new LRUCache<Integer, String>(10);
    int i;
    for (i = 0; i < keys.length; ++i) {
      cache.put(keys[i], values[i]);
    }

    i = 0;
    for (Iterator<Integer> iter = cache.keySet().iterator(); iter.hasNext();) {
      Integer key = iter.next();
      Assert.assertEquals(keys[i], key);
      ++i;
    }

    i = 0;
    for (Iterator<String> iter = cache.values().iterator(); iter.hasNext();) {
      String value = iter.next();
      Assert.assertEquals(values[i], value);
      ++i;
    }

    // Access in reverse order and check
    for (i = keys.length - 1; i >= 0; --i) {
      cache.get(keys[i]);
    }
    i = keys.length;
    for (Iterator<Integer> iter = cache.keySet().iterator(); iter.hasNext();) {
      --i;
      Integer key = iter.next();
      Assert.assertEquals(keys[i], key);
    }

    i = keys.length;
    for (Iterator<String> iter = cache.values().iterator(); iter.hasNext();) {
      --i;
      String value = iter.next();
      Assert.assertEquals(values[i], value);
    }

    // Access in forward order and check
    for (i = 0; i < keys.length; ++i) {
      cache.get(keys[i]);
    }
    i = 0;
    for (Iterator<Integer> iter = cache.keySet().iterator(); iter.hasNext();) {
      Integer key = iter.next();
      Assert.assertEquals(keys[i], key);
      ++i;
    }

    i = 0;
    for (Iterator<String> iter = cache.values().iterator(); iter.hasNext();) {
      String value = iter.next();
      Assert.assertEquals(values[i], value);
      ++i;
    }
  }
}
