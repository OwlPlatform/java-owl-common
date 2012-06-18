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

package com.owlplatform.common;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.owlplatform.common.SampleMessage;

/**
 * Test Unit class for the {@code com.owlplatform.common.SampleMessage} class.
 * 
 * @author Robert Moore
 * 
 */
public class SampleMessageTest {

  /**
   * A SampleMessage object to use during testing.
   */
  private SampleMessage basicSample;

  /**
   * A SampleMessage object to use during testing.
   */
  private SampleMessage testSample;

  /**
   * Example unknown physical layer type.
   */
  private static final byte PHY_UNKNOWN = (byte) 0xFF;

  /**
   * The expected length of a Sensor-Aggregator SampleMessage object with no
   * data.
   */
  private static final int BASE_LENGTH = 45;

  /**
   * Sample data {@code byte[]} with length 0.
   */
  private static final byte[] TEST_DATA_0BYTE = new byte[] {};

  /**
   * Sample 4-byte data {@code byte[]}.
   */
  private static final byte[] TEST_DATA_4BYTE = new byte[] { 0, 1, 2, 3 };

  /**
   * Sample 8-byte data {@code byte[]}.
   */
  private static final byte[] TEST_DATA_8BYTE = new byte[] { (byte) 0xCA, 0x11,
      (byte) 0xAB, 0x1E, (byte) 0xB0, 0x1D, (byte) 0xFA, (byte) 0xCE };

  /**
   * Sample 24-byte data {@code byte[]}.
   */
  private static final byte[] TEST_DATA_24BYTE = new byte[] { (byte) 0xCA,
      0x11, (byte) 0xAB, 0x1E, (byte) 0xB0, 0x1D, (byte) 0xFA, (byte) 0xCE,
      (byte) 0xCA, 0x11, (byte) 0xAB, 0x1E, (byte) 0xB0, 0x1D, (byte) 0xFA,
      (byte) 0xCE, (byte) 0xCA, 0x11, (byte) 0xAB, 0x1E, (byte) 0xB0, 0x1D,
      (byte) 0xFA, (byte) 0xCE };

  /**
   * Sample RSSI value.
   */
  private static final float RSSI1 = -50f;
  /**
   * Sample RSSI value.
   */
  private static final float RSSI2 = -99f;

  /**
   * Sample RSSI value.
   */
  private static final float RSSI3 = 100f;

  /**
   * A timestamp at a fixed point in time.
   */
  private static final long TIMESTAMP_FIXED = -500l;

  /**
   * A timestamp that varies with the time the test is run.
   */
  private static final long TIMESTAMP_NOW = System.currentTimeMillis();

  /**
   * A sample device id value.
   */
  private static final byte[] TEST_DEVICE_ID1 = new byte[] { 0, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 0, 0, 0, 0, 0x12, 0x34 };

  /**
   * A sample device id value.
   */
  private static final byte[] TEST_DEVICE_ID2 = new byte[] { 0, 1, 2, 3, 4, 5,
      6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF };

  /**
   * The expected result from a call to {@code toString()} on a Sample with
   * default values.
   */
  private static final String TO_STRING_PLAIN = "Sample (-1, 0x, 0x): 0.0 @ 0";

  /**
   * The expected result from a call to {@code toString()} on a Sample with some
   * of the test data values.
   */
  private static final String TO_STRING_DATA = "Sample (2, 0x00000000000000000000000000001234, 0x000102030405060708090A0B0C0D0E0F): -50.0 @ -500 [0x00010203]";

  /**
   * Creates the two samples used for testing.
   */
  @Before
  public void createSamples() {
    this.basicSample = new SampleMessage();
    this.testSample = SampleMessage.getTestMessage();
  }

  /**
   * Tests that the physical layer value is correctly handled by the
   * SampleMessage class.
   */
  @Test
  public void testPhysicalLayer() {
    Assert.assertEquals(SampleMessage.PHYSICAL_LAYER_UNDEFINED, this.basicSample.getPhysicalLayer());

    this.basicSample.setPhysicalLayer(SampleMessage.PHYSICAL_LAYER_PIPSQUEAK);
    Assert.assertEquals(SampleMessage.PHYSICAL_LAYER_PIPSQUEAK,
        this.basicSample.getPhysicalLayer());

    this.basicSample.setPhysicalLayer(SampleMessage.PHYSICAL_LAYER_WIFI);
    Assert.assertEquals(SampleMessage.PHYSICAL_LAYER_WIFI,
        this.basicSample.getPhysicalLayer());

    this.basicSample.setPhysicalLayer(PHY_UNKNOWN);
    Assert.assertEquals(PHY_UNKNOWN, this.basicSample.getPhysicalLayer());
  }

  /**
   * Test to ensure that using {@link SampleMessage#PHYSICAL_LAYER_ALL} throws
   * an {@code IllegalArgumentException}.
   */
  @Test(expected=IllegalArgumentException.class)
  public void testInvalidPhysicalLayer() {
    this.basicSample.setPhysicalLayer(SampleMessage.PHYSICAL_LAYER_ALL);
  }

  /**
   * Tests that the length prefix values are correctly returned by the
   * SampleMessage class.
   */
  @Test
  public void testLengthPrefix() {
    // Default sensor->aggregator length is 45 bytes
    Assert.assertEquals(BASE_LENGTH, this.basicSample.getLengthPrefixSensor());
    // +1 byte for the message type
    Assert.assertEquals(BASE_LENGTH + 1,
        this.basicSample.getLengthPrefixSolver());

    this.basicSample.setSensedData(TEST_DATA_4BYTE);
    Assert.assertEquals(BASE_LENGTH + 4,
        this.basicSample.getLengthPrefixSensor());
    Assert.assertEquals(BASE_LENGTH + 5,
        this.basicSample.getLengthPrefixSolver());

    this.basicSample.setSensedData(TEST_DATA_8BYTE);
    Assert.assertEquals(BASE_LENGTH + 8,
        this.basicSample.getLengthPrefixSensor());
    Assert.assertEquals(BASE_LENGTH + 9,
        this.basicSample.getLengthPrefixSolver());

    this.basicSample.setSensedData(TEST_DATA_0BYTE);
    Assert.assertEquals(BASE_LENGTH, this.basicSample.getLengthPrefixSensor());
    Assert.assertEquals(BASE_LENGTH + 1,
        this.basicSample.getLengthPrefixSolver());

    this.basicSample.setSensedData(null);
    Assert.assertEquals(BASE_LENGTH, this.basicSample.getLengthPrefixSensor());
    Assert.assertEquals(BASE_LENGTH + 1,
        this.basicSample.getLengthPrefixSolver());
  }

  /**
   * Tests that the device ID values are handled correctly within SampleMessage.
   */
  @Test
  public void testDeviceId() {
    Assert.assertNull(this.basicSample.getDeviceId());

    this.basicSample.setDeviceId(TEST_DEVICE_ID1);
    Assert.assertTrue(Arrays.equals(TEST_DEVICE_ID1,
        this.basicSample.getDeviceId()));

    this.basicSample.setDeviceId(TEST_DEVICE_ID2);
    Assert.assertTrue(Arrays.equals(TEST_DEVICE_ID2,
        this.basicSample.getDeviceId()));
  }

  /**
   * Tests that setting a null device ID will throw a RuntimeException.
   */
  @Test(expected = RuntimeException.class)
  public void testDeviceIdNull() {
    this.basicSample.setDeviceId(null);
  }

  /**
   * Tests that setting a device ID shorter than 16 bytes will throw a
   * RuntimeException.
   */
  @Test(expected = RuntimeException.class)
  public void testDeviceIdShort() {
    this.basicSample.setDeviceId(TEST_DATA_4BYTE);
  }

  /**
   * Tests that setting a device ID longer than 16 bytes will throw a
   * RuntimeException.
   */
  @Test(expected = RuntimeException.class)
  public void testDeviceIdLong() {
    this.basicSample.setDeviceId(TEST_DATA_24BYTE);
  }

  /**
   * Tests to ensure that receiver ID values are treated correctly within the
   * SampleMessage class.
   */
  @Test
  public void testReceiverId() {
    Assert.assertNull(this.basicSample.getReceiverId());

    this.basicSample.setReceiverId(TEST_DEVICE_ID1);
    Assert.assertTrue(Arrays.equals(TEST_DEVICE_ID1,
        this.basicSample.getReceiverId()));

    this.basicSample.setReceiverId(TEST_DEVICE_ID2);
    Assert.assertTrue(Arrays.equals(TEST_DEVICE_ID2,
        this.basicSample.getReceiverId()));
  }

  /**
   * Ensures that a null receiver ID will throw a RuntimeException.
   */
  @Test(expected = RuntimeException.class)
  public void testReceiverIdNull() {
    this.basicSample.setReceiverId(null);
  }

  /**
   * Ensures that setting a receiver ID shorter than 16 bytes will throw a
   * RuntimeException.
   */
  @Test(expected = RuntimeException.class)
  public void testReceiverIdShort() {
    this.basicSample.setReceiverId(TEST_DATA_4BYTE);
  }

  /**
   * Ensures that setting a receiver ID long than 16 bytes will throw a
   * RuntimeException.
   */
  @Test(expected = RuntimeException.class)
  public void testReceiverIdLong() {
    this.basicSample.setReceiverId(TEST_DATA_24BYTE);
  }

  /**
   * Tests to ensure that sample timestamps are set correctly.
   */
  @Test
  public void testReceiverTimeStamp() {
    Assert.assertEquals(0l, this.basicSample.getReceiverTimeStamp());

    this.basicSample.setReceiverTimeStamp(TIMESTAMP_FIXED);
    Assert.assertEquals(TIMESTAMP_FIXED,
        this.basicSample.getReceiverTimeStamp());

    this.basicSample.setReceiverTimeStamp(TIMESTAMP_NOW);
    Assert.assertEquals(TIMESTAMP_NOW, this.basicSample.getReceiverTimeStamp());
  }

  /**
   * Tests to ensure that RSSI values are handled correctly.
   */
  @Test
  public void testRssi() {
    Assert.assertEquals(0f, this.basicSample.getRssi(), 0.001f);

    this.basicSample.setRssi(RSSI1);
    Assert.assertEquals(RSSI1, this.basicSample.getRssi(), 0.001f);

    this.basicSample.setRssi(RSSI2);
    Assert.assertEquals(RSSI2, this.basicSample.getRssi(), 0.001f);

    this.basicSample.setRssi(RSSI3);
    Assert.assertEquals(RSSI3, this.basicSample.getRssi(), 0.001f);
  }

  /**
   * Tests to ensure that sensed data values are handled correctly.
   */
  @Test
  public void testSensedData() {
    Assert.assertNull(this.basicSample.getSensedData());

    this.basicSample.setSensedData(TEST_DATA_0BYTE);
    Assert.assertTrue(Arrays.equals(TEST_DATA_0BYTE,
        this.basicSample.getSensedData()));

    this.basicSample.setSensedData(TEST_DATA_4BYTE);
    Assert.assertTrue(Arrays.equals(TEST_DATA_4BYTE,
        this.basicSample.getSensedData()));

    this.basicSample.setSensedData(TEST_DATA_8BYTE);
    Assert.assertTrue(Arrays.equals(TEST_DATA_8BYTE,
        this.basicSample.getSensedData()));
  }

  /**
   * Tests the clone method to ensure that cloned objects are appropriately
   * equivalent.
   * 
   * @throws CloneNotSupportedException
   */
  @Test
  public void testClone() throws CloneNotSupportedException {
    SampleMessage clone = this.basicSample.clone();

    Assert.assertEquals(this.basicSample.getLengthPrefixSensor(),
        clone.getLengthPrefixSensor());
    Assert.assertEquals(this.basicSample.getLengthPrefixSolver(),
        clone.getLengthPrefixSolver());
    Assert.assertEquals(this.basicSample.getCreationTimestamp(),
        clone.getCreationTimestamp());
    Assert.assertEquals(this.basicSample.getPhysicalLayer(),
        clone.getPhysicalLayer());
    Assert.assertEquals(this.basicSample.getReceiverTimeStamp(),
        clone.getReceiverTimeStamp());
    Assert.assertEquals(this.basicSample.getRssi(), clone.getRssi(), 0.001f);
    Assert.assertTrue(Arrays.equals(this.basicSample.getDeviceId(),
        clone.getDeviceId()));
    Assert.assertTrue(Arrays.equals(this.basicSample.getReceiverId(),
        clone.getReceiverId()));
    Assert.assertTrue(Arrays.equals(this.basicSample.getSensedData(),
        clone.getSensedData()));

    this.testSample.setSensedData(TEST_DATA_4BYTE);
    clone = this.testSample.clone();

    Assert.assertEquals(this.testSample.getLengthPrefixSensor(),
        clone.getLengthPrefixSensor());
    Assert.assertEquals(this.testSample.getLengthPrefixSolver(),
        clone.getLengthPrefixSolver());
    Assert.assertEquals(this.testSample.getCreationTimestamp(),
        clone.getCreationTimestamp());
    Assert.assertEquals(this.testSample.getPhysicalLayer(),
        clone.getPhysicalLayer());
    Assert.assertEquals(this.testSample.getReceiverTimeStamp(),
        clone.getReceiverTimeStamp());
    Assert.assertEquals(this.testSample.getRssi(), clone.getRssi(), 0.001f);
    Assert.assertTrue(Arrays.equals(this.testSample.getDeviceId(),
        clone.getDeviceId()));
    Assert.assertTrue(Arrays.equals(this.testSample.getReceiverId(),
        clone.getReceiverId()));
    Assert.assertTrue(Arrays.equals(this.testSample.getSensedData(),
        clone.getSensedData()));
  }

  /**
   * Tests the toString() method of the SampleMessage class.
   */
  @Test
  public void testToString() {
    Assert.assertEquals(TO_STRING_PLAIN, this.basicSample.toString());

    this.basicSample.setDeviceId(TEST_DEVICE_ID1);
    this.basicSample.setReceiverId(TEST_DEVICE_ID2);
    this.basicSample.setRssi(RSSI1);
    this.basicSample.setPhysicalLayer(SampleMessage.PHYSICAL_LAYER_WIFI);
    this.basicSample.setReceiverTimeStamp(TIMESTAMP_FIXED);
    this.basicSample.setSensedData(TEST_DATA_4BYTE);

    Assert.assertEquals(TO_STRING_DATA, this.basicSample.toString());
  }

}
