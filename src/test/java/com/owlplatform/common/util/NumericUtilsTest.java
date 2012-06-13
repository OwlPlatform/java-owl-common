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
 * Unit Test to ensure that the NumericUtils class correctly converts values to Strings.
 * @author Robert Moore
 *
 */
public class NumericUtilsTest {

  /**
   * byte[] data with leading 0 values.
   */
  private static final byte[] LEADING_ZEROS = new byte[] {0,0,1,2,3,4};
  
  /**
   * byte[] data with no leading 0 values.
   */
  private static final byte[] NO_LEADING_ZEROS = new byte[] {1,2,3,4};
  
  /**
   * Expected string for LEADING_ZEROS printed without leading zeros.
   */
  private static final String STRING_NO_LEADING = "0x01020304";
  
  /**
   * Expected string for LEADING_ZEROS printed with leading zeros.
   */
  private static final String STRING_LEADING_ZEROS = "0x000001020304";
  
  /**
   * Expected string for a single byte with value 1.
   */
  private static final String STRING_1 = "0x01";
  
  /**
   * Expected string for a null byte[].
   */
  private static final String STRING_NULL = "0x";
  
  /**
   * Expected string for a null byte[] that omits the leading "0x".
   */
  private static final String STRING_EMPTY_NULL = "";
  
  /**
   * Tests conversion with leading zeros.
   */
  @Test
  public void testToHexStringByteArray() {
    Assert.assertEquals(STRING_LEADING_ZEROS, NumericUtils.toHexString(LEADING_ZEROS));
    Assert.assertEquals(STRING_NO_LEADING, NumericUtils.toHexString(NO_LEADING_ZEROS));
    Assert.assertEquals(STRING_NULL, NumericUtils.toHexString(null));
  }

  /**
   * Tests conversion without leading zeros.
   */
  @Test
  public void testToHexShortString() {
    Assert.assertEquals(STRING_NO_LEADING, NumericUtils.toHexShortString(LEADING_ZEROS));
    Assert.assertEquals(STRING_NO_LEADING, NumericUtils.toHexShortString(NO_LEADING_ZEROS));
    Assert.assertEquals(STRING_NULL, NumericUtils.toHexShortString(null));
  }

  /**
   * Tests conversion of a single byte.
   */
  @Test
  public void testToHexStringByte() {
    Assert.assertEquals(STRING_1, NumericUtils.toHexString(NO_LEADING_ZEROS[0]));
  }

  /**
   * Tests conversion without leading zeros, with null byte[] as empty strings.
   */
  @Test
  public void testToEmptyHexString() {
    Assert.assertEquals(STRING_NO_LEADING, NumericUtils.toEmptyHexString(LEADING_ZEROS));
    Assert.assertEquals(STRING_NO_LEADING, NumericUtils.toEmptyHexString(NO_LEADING_ZEROS));
    Assert.assertEquals(STRING_EMPTY_NULL, NumericUtils.toEmptyHexString(null));
  }
}
