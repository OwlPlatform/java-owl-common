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

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the Pair class.
 * @author Robert Moore
 *
 */
public class PairTest {

  /**
   * An Integer value for testing.
   */
  private static final Integer INT_VALUE1 = Integer.valueOf(1);
  /**
   * An Integer value for testing.
   */
  private static final Integer INT_VALUE2 = Integer.valueOf(2);

  /**
   * A String value for testing.
   */
  private static final String STRING_VALUE1 = "One";
  /**
   * A String value for testing.
   */
  private static final String STRING_VALUE2 = "Two";
  
  /**
   * Expected string representation for INT_VALUE1 and STRING_VALUE1.
   */
  private static final String INT1_STR1_AS_STRING = "(1, One)";
  /**
   * Expected string representation for INT_VALUE2 and STRING_VALUE2.
   */
  private static final String STR2_INT2_AS_STRING = "(Two, 2)";

  /**
   * Tests value 1.
   */
  @Test
  public void testValue1() {
    Pair<String,Integer> pair = new Pair<String, Integer>();
    pair.setValue1(STRING_VALUE1);
    Assert.assertEquals(STRING_VALUE1, pair.getValue1());
    
  }

  /**
   * Tests value 2.
   */
  @Test
  public void testValue2() {
    Pair<String,Integer> pair = new Pair<String,Integer>();
    pair.setValue2(INT_VALUE1);
    Assert.assertEquals(INT_VALUE1,pair.getValue2());
  }

  /**
   * Tests conversion to a String.
   */
  @Test
  public void testToString() {
    Pair<Integer,String> pair1 = new Pair<Integer,String>();
    pair1.setValue1(INT_VALUE1);
    pair1.setValue2(STRING_VALUE1);
    Pair<String,Integer> pair2 = new Pair<String,Integer>();
    pair2.setValue1(STRING_VALUE2);
    pair2.setValue2(INT_VALUE2);
    
    Assert.assertEquals(INT1_STR1_AS_STRING,pair1.toString());
    Assert.assertEquals(STR2_INT2_AS_STRING,pair2.toString());
  }

}
