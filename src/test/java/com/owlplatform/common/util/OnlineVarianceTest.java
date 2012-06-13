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
import org.junit.Before;
import org.junit.Test;

/**
 * Unit Tests for the OnlineVariance class.
 * 
 * @author Robert Moore
 * 
 */
public class OnlineVarianceTest {

  /**
   * Variance input values.
   */
  private static final int[] VALUES = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9,
      10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };

  /**
   * The expected variance after 20 values.
   */
  private static final float VALUES_VARIANCE_HISTORY_20 = 35f;

  /**
   * The expected variance after 10 values.
   */
  private static final float VALUES_VARIANCE_HISTORY_10 = 9.1666f;

  /**
   * The expected variance after 5 values.
   */
  private static final float VALUES_VARIANCE_HISTORY_5 = 2.5f;

  /**
   * The expected variance after 2 values.
   */
  private static final float VALUES_VARIANCE_HISTORY_2 = .5f;

  /**
   * The expected variance after the age gap is exceeded.
   */
  private static final float VALUES_VARIANCE_AGE_10MS = 0f;

  /**
   * Defines a history length of 20 values.
   */
  private static final int HISTORY_LENGTH_20 = 20;
  /**
   * Defines a history length of 10 values.
   */
  private static final int HISTORY_LENGTH_10 = 10;
  /**
   * Defines a history length of 5 values.
   */
  private static final int HISTORY_LENGTH_5 = 5;
  /**
   * Defines a history length of 2 values.
   */
  private static final int HISTORY_LENGTH_2 = 2;

  /**
   * Defines an age of 10 milliseconds.
   */
  private static final long AGE_10MS = 10l;
  /**
   * Defines an age of 100 milliseconds.
   */
  private static final long AGE_100MS = 100l;
  /**
   * Defines an age of 500 milliseconds.
   */
  private static final long AGE_500MS = 500l;

  /**
   * The variance object used in each test.
   */
  private OnlineVariance variance;

  /**
   * Creates a new variance object before each test.
   */
  @Before
  public void createVariance() {
    this.variance = new OnlineVariance();
  }

  /**
   * Tests the current value of the variance after multiple insertions.
   */
  @Test
  public void testCurrentVariance() {

    this.variance.setMaxHistory(HISTORY_LENGTH_20);
    for (int i = 0; i < VALUES.length; ++i) {
      this.variance.addValue(VALUES[i]);
    }
    Assert.assertEquals(VALUES_VARIANCE_HISTORY_20,
        this.variance.getCurrentVariance(), 0.01f);

    this.variance.reset();

    this.variance.setMaxHistory(HISTORY_LENGTH_10);
    for (int i = 0; i < VALUES.length; ++i) {
      this.variance.addValue(VALUES[i]);
    }
    Assert.assertEquals(VALUES_VARIANCE_HISTORY_10,
        this.variance.getCurrentVariance(), 0.01f);

    this.variance.reset();

    this.variance.setMaxHistory(HISTORY_LENGTH_5);
    for (int i = 0; i < VALUES.length; ++i) {
      this.variance.addValue(VALUES[i]);
    }
    Assert.assertEquals(VALUES_VARIANCE_HISTORY_5,
        this.variance.getCurrentVariance(), 0.01f);

    this.variance.reset();

    this.variance.setMaxHistory(HISTORY_LENGTH_2);
    for (int i = 0; i < VALUES.length; ++i) {
      this.variance.addValue(VALUES[i]);
    }
    Assert.assertEquals(VALUES_VARIANCE_HISTORY_2,
        this.variance.getCurrentVariance(), 0.01f);

    this.variance.reset();
  }

  /**
   * Tests that the maximum history is observed correctly.
   */
  @Test
  public void testMaxHistory() {
    this.variance.setMaxHistory(HISTORY_LENGTH_20);
    Assert.assertEquals(HISTORY_LENGTH_20, this.variance.getMaxHistory());

    this.variance.setMaxHistory(HISTORY_LENGTH_10);
    Assert.assertEquals(HISTORY_LENGTH_10, this.variance.getMaxHistory());

    this.variance.setMaxHistory(HISTORY_LENGTH_5);
    Assert.assertEquals(HISTORY_LENGTH_5, this.variance.getMaxHistory());

    this.variance.setMaxHistory(HISTORY_LENGTH_2);
    Assert.assertEquals(HISTORY_LENGTH_2, this.variance.getMaxHistory());
  }

  /**
   * Tests that the age gap is handled correctly.
   */
  @Test
  public void testAgeGap() {
    this.variance.setAgeGap(AGE_500MS);
    Assert.assertEquals(AGE_500MS, this.variance.getAgeGap());

    this.variance.setAgeGap(AGE_100MS);
    Assert.assertEquals(AGE_100MS, this.variance.getAgeGap());

    this.variance.setAgeGap(AGE_10MS);
    Assert.assertEquals(AGE_10MS, this.variance.getAgeGap());

    for (int i = 0; i < VALUES.length; ++i) {
      this.variance.addValue(VALUES[i]);
      try {
        Thread.sleep(AGE_10MS * 2);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    Assert.assertEquals(VALUES_VARIANCE_AGE_10MS,
        this.variance.getCurrentVariance(), 0.01f);
  }

  /**
   * Tests that cloning works correctly.
   * @throws CloneNotSupportedException
   */
  @Test
  public void testClone() throws CloneNotSupportedException {
    for (int i = 0; i < VALUES.length; ++i) {
      this.variance.addValue(VALUES[i]);
    }
    OnlineVariance clone = this.variance.clone();
    Assert.assertEquals(this.variance.getCurrentVariance(),
        clone.getCurrentVariance(), 0.01f);
  }

}
