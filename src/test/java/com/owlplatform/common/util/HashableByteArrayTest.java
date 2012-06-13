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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.owlplatform.common.util.HashableByteArray;

/**
 * Test Unit class for {@code com.owlplatform.common.util.HashableByteArray}
 * @author Robert Moore
 *
 */
public class HashableByteArrayTest {

  /**
   * Sample byte[] value.
   */
  private static final byte[] DATA1 = new byte[]{0x01,0x23,0x45,0x67};
  /**
   * Sample byte[] value.
   */
  private static final byte[] DATA2 = new byte[]{(byte)0x89, (byte)0xAB, (byte)0xCD, (byte)0xEF};
  
  /**
   * String value of DATA1.
   */
  private static final String DATA1_STRING = "0x01234567";
  /**
   * String value of DATA2.
   */
  private static final String DATA2_STRING = "0x89ABCDEF";
  
  /**
   * Sample byte[] value.
   */
  private static final byte[] DATA_SHORT = new byte[]{(byte)0xFF};
  
  /**
   * {@code HashableByteArray} for testing.
   */
  private HashableByteArray array1;
  
  /**
   * {@code HashableByteArray} for testing.
   */
  private HashableByteArray array2;
  
  /**
   * Initializes the two {@code HashableByteArray} values to DATA1 and DATA2.
   */
  @Before
  public void createArrays(){
    this.array1 = new HashableByteArray(DATA1);
    this.array2 = new HashableByteArray(DATA2);
  }
  
  /**
   * Tests the hashcode() method.
   */
  @Test
  public void testHashCode() {
    Assert.assertEquals(Arrays.hashCode(DATA1),this.array1.hashCode());
    // Call twice, because after first time is cached
    Assert.assertEquals(Arrays.hashCode(DATA1),this.array1.hashCode());

    Assert.assertEquals(Arrays.hashCode(DATA2), this.array2.hashCode());
    Assert.assertEquals(Arrays.hashCode(DATA2), this.array2.hashCode());
  }

  /**
   * Tests data handling.
   */
  @Test
  public void testGetData() {
    Assert.assertTrue(Arrays.equals(DATA1,this.array1.getData()));
    Assert.assertTrue(Arrays.equals(DATA2,this.array2.getData()));
  }
  
  /**
   * Tests to ensure that creating an {@code HashableByteArray} object with null data throws an IllegalArgumentException.
   */
  @SuppressWarnings("unused")
  @Test(expected= IllegalArgumentException.class)
  public void testNullData(){
    HashableByteArray testArray = new HashableByteArray(null);
  }

  /**
   * Ensures that two equivalent {@code HashableByteArray} objects correctly return equals() values.
   */
  @Test
  public void testEqualsObject() {
    HashableByteArray another = new HashableByteArray(DATA1);
    Assert.assertTrue(this.array1.equals(another));
    Assert.assertTrue(another.equals(this.array1));
    
    another = new HashableByteArray(DATA2);
    Assert.assertTrue(this.array2.equals(another));
    Assert.assertTrue(another.equals(this.array2));
    
    Assert.assertFalse(this.array1.equals(another));
    Assert.assertFalse(another.equals(this.array1));
    
    Assert.assertFalse(this.array1.equals(DATA1));
  }

  /**
   * Tests comparison of {@code HashableByteArray} objects.
   */
  @Test
  public void testCompareTo() {
    // Self-equivalence
    Assert.assertEquals(0,this.array1.compareTo(this.array1));
    Assert.assertEquals(0,this.array2.compareTo(this.array2));
    
    // Equivalent HBAs
    HashableByteArray another = new HashableByteArray(DATA1);
    Assert.assertEquals(0,this.array1.compareTo(another));
    Assert.assertEquals(0,another.compareTo(this.array1));
    
    another = new HashableByteArray(DATA2);
    Assert.assertEquals(0,this.array2.compareTo(another));
    Assert.assertEquals(0,another.compareTo(this.array2));
    
    // Inequalities
    Assert.assertTrue(this.array1.compareTo(this.array2) < 0);
    Assert.assertTrue(this.array2.compareTo(this.array1) > 0);
    
    another = new HashableByteArray(DATA_SHORT);
    Assert.assertTrue(this.array1.compareTo(another) > 0);
    Assert.assertTrue(another.compareTo(this.array1) < 0);
  }
  
  /**
   * Tests {@code HashableByteArray#toString()}.
   */
  @Test
  public void toStringTest(){
    Assert.assertEquals(DATA1_STRING,this.array1.toString());
    Assert.assertEquals(DATA2_STRING, this.array2.toString());
  }

}
