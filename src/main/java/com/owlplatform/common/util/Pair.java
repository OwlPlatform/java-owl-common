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

/**
 * A simple generic pair class.
 * 
 * @author Robert Moore
 * 
 * @param <T1>
 * @param <T2>
 */
public class Pair<T1, T2> {

  /**
   * The first value.
   */
  protected T1 value1;

  /**
   * The second value.
   */
  protected T2 value2;

  /**
   * Returns the first value of this pair.
   * 
   * @return the first value.
   */
  public T1 getValue1() {
    return this.value1;
  }

  /**
   * Sets the first value of this pair.
   * 
   * @param value1
   *          the new first value.
   */
  public void setValue1(T1 value1) {
    this.value1 = value1;
  }

  /**
   * Returns the second value of this pair.
   * 
   * @return the second value.
   */
  public T2 getValue2() {
    return this.value2;
  }

  /**
   * Sets the second value of this pair.
   * 
   * @param value2
   *          the new second value.
   */
  public void setValue2(T2 value2) {
    this.value2 = value2;
  }

  /**
   * Returns a String representing this Pair object. The two values are
   * surrounded by parentheses and separated by a comma and space. For example,
   * if the first value is the Integer 1 and the second value is the String
   * "abc", then "(1,&nbsp;abc)" will be returned.
   */
  @Override
  public String toString() {
    return "(" + String.valueOf(this.value1) + ", "
        + String.valueOf(this.value2) + ")";
  }
}
