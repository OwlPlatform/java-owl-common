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

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit Test to ensure that the NumericUtils class correctly converts values to Strings.
 * @author Robert Moore
 *
 */
public class NumericUtilsTest {

  /**
   * The output byte[] from STRING_INVALID when called with NumericUtils.fromHexString().
   */
  private static final byte[] INVALID = new byte[] {0,0,0,0};
  
  /**
   * A string containing invalid hex characters
   */
  private static final String STRING_INVALID = "OXOXOXO";
  
  /**
   * byte[] for STRING_ONE_DIGIT
   */
  private static final byte[] ONE_DIGIT = new byte[]{13};
  
  /**
   * byte[] for STRING_TWO_DIGIT
   */
  private static final byte[] TWO_DIGIT = new byte[]{(byte)0xde};
  
  /**
   * byte[] for STRING_THREE_DIGIT
   */
  private static final byte[] THREE_DIGIT = new byte[]{(byte)0xd, (byte)0xe1};
  
  /**
   * byte[] for STRING_FOUR_DIGIT
   */
  private static final byte[] FOUR_DIGIT = new byte[]{(byte)0x2d, (byte)0xe1};
  
  /**
   * A 1-digit test string.
   */
  private static final String STRING_ONE_DIGIT = "D";
  
  /**
   * A 2-digit test string
   */
  private static final String STRING_TWO_DIGIT = "DE";
  
  /**
   * A 3-digit test string
   */
  private static final String STRING_THREE_DIGIT = "DE1";
  /**
   * A 4-digit test string
   */
  private static final String STRING_FOUR_DIGIT = "2DE1";
  
  /**
   * byte[] data with leading 0 values.
   */
  private static final byte[] LEADING_ZEROS = new byte[] {0,0,1,2,3,0x0E};
  
  /**
   * byte[] data with no leading 0 values.
   */
  private static final byte[] NO_LEADING_ZEROS = new byte[] {1,2,3,0x0E};
  
  /**
   * byte[] with length of 0.
   */
  private static final byte[] ZERO_LENGTH = new byte[0];
  
  /**
   * Expected string for LEADING_ZEROS printed without leading zeros.
   */
  private static final String STRING_NO_LEADING = "0x0102030E";
  
  /**
   * Expected string for LEADING_ZEROS printed with leading zeros.
   */
  private static final String STRING_LEADING_ZEROS = "0x00000102030E";
  
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
    Assert.assertEquals(STRING_EMPTY_NULL, NumericUtils.toEmptyHexString(ZERO_LENGTH));
  }
  
  /**
   * Test conversion from hexadecimal Strings to byte[].
   */
  @Test
  public void testFromHexString(){
    Assert.assertTrue(Arrays.equals(LEADING_ZEROS,NumericUtils.fromHexString(STRING_LEADING_ZEROS)));
    Assert.assertTrue(Arrays.equals(NO_LEADING_ZEROS,NumericUtils.fromHexString(STRING_NO_LEADING)));
    Assert.assertTrue(Arrays.equals(INVALID,NumericUtils.fromHexString(STRING_INVALID)));
    Assert.assertTrue(Arrays.equals(ONE_DIGIT, NumericUtils.fromHexString(STRING_ONE_DIGIT)));
    Assert.assertTrue(Arrays.equals(TWO_DIGIT, NumericUtils.fromHexString(STRING_TWO_DIGIT)));
    Assert.assertTrue(Arrays.equals(THREE_DIGIT, NumericUtils.fromHexString(STRING_THREE_DIGIT)));
    Assert.assertTrue(Arrays.equals(FOUR_DIGIT, NumericUtils.fromHexString(STRING_FOUR_DIGIT)));
    
    Assert.assertNull(NumericUtils.fromHexString(null));
    Assert.assertNull(NumericUtils.fromHexString(STRING_EMPTY_NULL));
    Assert.assertNull(NumericUtils.fromHexString(STRING_NULL));
  }
}
