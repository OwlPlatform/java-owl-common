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
 * A collection of simple little numeric utilities.
 * 
 * @author Robert Moore
 * 
 */
public class NumericUtils {

  /**
   * List of hexadecimal (base 16) characters for printing.
   */
  public static char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  /**
   * Returns a String representing the byte[] as a sequence of uppercase
   * hexadecimal characters, starting with "0x". Omitting any leading 0-value
   * bytes.
   * 
   * @param bytes
   *          the byte array to convert.
   * @return a String representing the value of {@code bytes} as hexadecimal
   *         characters, starting with "0x". If {@code bytes} is null or has a
   *         length of 0, then the empty String ("") is returned.
   */
  public static String toEmptyHexString(byte[] bytes) {
    if (bytes == null || bytes.length == 0) {
      return "";
    }
    return toHexShortString(bytes);
  }

  /**
   * Returns a String representing the byte[] as a sequence of uppercase
   * hexadecimal characters, starting with "0x".
   * 
   * @param bytes
   *          the byte array to convert.
   * @return a String representing the value of {@code bytes} as hexadecimal
   *         characters, starting with "0x". If {@code bytes} is null or has a
   *         length of 0, then the String "0x" is returned.
   */
  public static String toHexString(byte[] bytes) {
    StringBuffer sb = new StringBuffer("0x");

    if (bytes != null) {
      for (byte b : bytes) {
        sb.append(HEX_CHARS[0x0F & (b >> 4)]).append(HEX_CHARS[b & 0x0F]);
      }
    }
    return sb.toString();
  }

  /**
   * Returns a String representing the byte[] as a sequence of uppercase
   * hexadecimal characters, starting with "0x". Omitting any leading 0-value
   * bytes.
   * 
   * @param bytes
   *          the byte array to convert.
   * @return a String representing the value of {@code bytes} as hexadecimal
   *         characters, starting with "0x". If {@code bytes} is null or has a
   *         length of 0, then the String "0x" is returned.
   */
  public static String toHexShortString(byte[] bytes) {
    StringBuffer sb = new StringBuffer("0x");

    boolean leadingZeros = true;
    if (bytes != null) {
      for (byte b : bytes) {
        if (leadingZeros && b == 0)
          continue;
        leadingZeros = false;
        sb.append(HEX_CHARS[0x0F & (b >> 4)]).append(HEX_CHARS[b & 0x0F]);
      }
    }
    return sb.toString();
  }

  /**
   * Returns the value of {@code b} as a hexadecimal string, starting with "0x".
   * 
   * @param b
   *          the byte
   * @return the string representation of {@code b} in hexadecimal, with a
   *         leading "0x".
   */
  public static String toHexString(byte b) {
    StringBuffer sb = new StringBuffer(4);
    sb.append("0x").append(HEX_CHARS[0x0F & (b >> 4)])
        .append(HEX_CHARS[b & 0x0F]);
    return sb.toString();
  }

  /**
   * Converts the provided String of hexadecimal numbers (with optional leading
   * "0x") into a {@code byte[]} of the same value. Invalid characters (outside
   * the range 0-F) will be given the value 0.
   * 
   * @param hexString
   *          the input string containing a hexadecimal value.
   * @return a {@code byte[]} of the same value, or {@code null} if
   *         {@code hexString} is {@code null} or contains no characters.
   */
  public static byte[] fromHexString(final String hexString) {
    if (hexString == null) {
      return null;
    }

    String str = hexString.trim().toLowerCase();

    // Strip any leading "0x"
    if (str.startsWith("0x")) {
      str = str.substring(2);
    }

    int numChars = str.length();
    if (numChars == 0) {
      return null;
    }
    char[] chars = str.toCharArray();
    byte[] retVal = new byte[(numChars + 1) / 2];
    int byteIndex = 0;
    int i = 0;
    if ((chars.length & 0x01) == 1) {
      retVal[byteIndex++] = fromChar(chars[i++]);
    }
    for (; i <chars.length; i += 2, ++byteIndex) {
      retVal[byteIndex] = (byte) ((fromChar(chars[i]) << 4) | fromChar(chars[i+1]));
    }
    return retVal;
  }

  /**
   * Converts a single hexadecimal character into a byte. The value
   * of the character is stored in the lower 4 bits of the returned byte.
   * @param nibble the hexadecimal character to convert.
   * @return a byte containing the binary equivalent of the hexadecimal character.
   */
  private static byte fromChar(final char nibble) {
    if (nibble >= '0' && nibble <= '9') {
      return (byte) (nibble - '0');
    }
    if (nibble >= 'a' && nibble <= 'f') {
      return (byte) (nibble - 'a' + 10);
    }

    return 0;
  }
}
