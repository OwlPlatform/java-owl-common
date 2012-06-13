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

import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class that computes the online/running variance of a sequence of
 * floating point values over time.
 * 
 * @author Robert Moore
 * 
 */
public class OnlineVariance implements Cloneable {

  /**
   * Logger for this class.
   */
  private static final Logger log = LoggerFactory
      .getLogger(OnlineVariance.class);

  /**
   * The current online variance.
   */
  protected float currentVariance = 0;

  /**
   * Returns the current computed online variance. This method will not affect
   * the variance value.
   * 
   * @return the current variance.
   */
  public float getCurrentVariance() {
    return this.currentVariance;
  }

  /**
   * History of values used to compute the variance.
   */
  protected final ConcurrentLinkedQueue<Float> history = new ConcurrentLinkedQueue<Float>();

  /**
   * The current size of the history to use.
   */
  protected int sizeHistory = 0;

  /**
   * The maximum size of history to use.
   */
  protected int maxHistory = 5;

  /**
   * Returns the maximum number of values to use when computing the variance.
   * 
   * @return the maximum number of values to use when computing the variance.
   */
  public int getMaxHistory() {
    return this.maxHistory;
  }

  /**
   * Sets the maximum number of values to use when computing the variance.
   * Changes to the maximum history will take effect on the next call to
   * {@code addValue(float)}.
   * 
   * @param maxHistory
   *          the maximum number of values to use.
   */
  public void setMaxHistory(int maxHistory) {
    this.maxHistory = maxHistory;
  }

  /**
   * The maximum length of time (in milliseconds) that a value is considered
   * valid for computing the variance. Defaults to 10 seconds.
   */
  protected long ageGap = 10000;

  /**
   * Returns the maximum length of time (in milliseconds) to allow between
   * values added to this online variance. If the length of time between calls
   * to {@code addValue(float)} exceeds this value, the variance will be reset
   * and the history cleared before the value is added.
   * 
   * @return the maximum time (in milliseconds) permitted between values.
   */
  public long getAgeGap() {
    return this.ageGap;
  }

  /**
   * Sets the maximum length of time (in milliseconds) permitted between values
   * added to this variance. A change to this setting will affect the next call
   * to {@code addValue}, i.e., the current variance value will not change
   * immediately.
   * 
   * @param ageGap
   *          the new value for the maximum time between added values.
   */
  public void setAgeGap(long ageGap) {
    this.ageGap = ageGap;
  }

  /**
   * The current running sum of the values.
   */
  private float sum;
  /**
   * The current running sum of squares of the values.
   */
  private float sum_squares;

  /**
   * The last time a value was added.
   */
  private long last_time;

  /**
   * Adds a value to this variance. If the time between the previous addition
   * and this addition exceeds the age gap for this object, then the variance
   * will be reset to 0 before this value is added.
   * 
   * @param value
   *          the value to add.
   * @return the current computed online variance, or 0 if none is available
   *         (e.g., the initial value was added or a value was added after the
   *         age gap passed)
   */
  public float addValue(final float value) {
    long now = System.currentTimeMillis();
    // If more than 15 seconds passed then clear the data since it is
    // too old at this point.
    if (now - this.last_time > this.ageGap) {
      this.reset();
    }
    this.last_time = now;
    if (this.sizeHistory < this.maxHistory) {
      this.sum += value;
      this.sum_squares += Math.pow(value, 2);
      ++this.sizeHistory;
    } else {
      Float oldest_val = this.history.poll();
      if (oldest_val != null) {
        this.sum = this.sum - oldest_val.floatValue() + value;
        this.sum_squares = this.sum_squares - (float)Math.pow(oldest_val.floatValue(),2) + (float)Math
                .pow(value, 2);
      } else {
        log.warn("Could not poll history to update variance.");
      }
    }
    this.history.offer(Float.valueOf(value));

    float degrees_of_freedom = this.sizeHistory - 1;
    if (degrees_of_freedom < 1.0) {
      return 0f;
    }
    this.currentVariance = (this.sum_squares - ((float)Math.pow(this.sum,2) / this.sizeHistory))
        / degrees_of_freedom;
    return this.currentVariance;

  }

  /**
   * Resets this variance object. Specifically, all history is cleared and the
   * variance is set to 0.
   */
  public void reset() {
    this.sum = 0f;
    this.sum_squares = 0f;
    this.currentVariance = 0;
    this.history.clear();
    this.sizeHistory = 0;
  }

  @Override
  public OnlineVariance clone() throws CloneNotSupportedException {
    OnlineVariance clone = (OnlineVariance) super.clone();
    clone.history.clear();
    if (!this.history.isEmpty()) {
      clone.history.addAll(this.history);
    }
    return clone;
  }
}
