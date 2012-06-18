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

import com.owlplatform.common.util.NumericUtils;

/**
 * Represents a sample sent to or from an Aggregator as defined in the Owl
 * Platform Aggregator-Solver and Sensor-Aggregator protocols.
 * 
 * @author Robert Moore II
 * 
 */
public class SampleMessage implements Cloneable {

  /**
   * The size, in octets, of the device identifier.
   */
  public static final int DEVICE_ID_SIZE = 16;

  /**
   * The identifier used for this message.
   */
  public static final byte MESSAGE_TYPE = 6;

  /**
   * An undefined physical layer type.
   */
  public static final byte PHYSICAL_LAYER_UNDEFINED = (byte)0xFF;
  
  /**
   * Physical layer for filtering any physical layer type. This should not be
   * used as the type of a {@code SampleMessage}.
   */
  public static final byte PHYSICAL_LAYER_ALL = 0;
  /**
   * Physical layer for Pipsqueak devices.
   */
  public static final byte PHYSICAL_LAYER_PIPSQUEAK = 1;

  /**
   * Physical layer for WiFi devices.
   */
  public static final byte PHYSICAL_LAYER_WIFI = 2;

  /**
   * Physical layer for WINS devices.
   */
  public static final byte PHYSICAL_LAYER_WINS = 3;

  /**
   * The physical layer type of the transmitter/receiver identified in this
   * message.
   */
  private byte physicalLayer = PHYSICAL_LAYER_UNDEFINED;

  /**
   * Returns the physical layer type for the transmitter/receiver identified in
   * this message.
   * 
   * @return the physical layer type for the transmitter/receiver identified in
   *         this message.
   */
  public byte getPhysicalLayer() {
    return this.physicalLayer;
  }

  /**
   * Sets the physical layer type for the transmitter/receiver identified in
   * this message.
   * 
   * @param physicalLayer
   *          the physical layer type for the transmitter/receiver identified in
   *          this message.
   * @throws IllegalArgumentException if the value of {@code physicalLayer}
   *  is equal to {@link #PHYSICAL_LAYER_ALL}.
   */
  public void setPhysicalLayer(byte physicalLayer) {
    if (physicalLayer == PHYSICAL_LAYER_ALL) {
      throw new IllegalArgumentException("Invalid physical layer type. "
          + PHYSICAL_LAYER_ALL + " is reserved for filtering only.");
    }
    this.physicalLayer = physicalLayer;
  }

  /**
   * The device identifier for the transmitter referenced in this message.
   */
  private byte[] deviceId;

  /**
   * The device identifier for the receiver referenced in this message.
   */
  private byte[] receiverId;

  /**
   * The UNIX timestamp indicating when this sample was observed by the
   * receiver.
   */
  private long receiverTimeStamp;

  /**
   * The Received Signal Strength Indicator (RSSI) for this sample.
   */
  private float rssi;

  /**
   * The time when this message object was created.
   */
  private final long creationTimestamp;

  /**
   * The raw sensed data provided by the transmitter or receiver for this
   * sample.
   */
  private byte[] sensedData = null;

  /**
   * Creates a Sample message with the current time as the timestamp. No other
   * fields are set.
   */
  public SampleMessage() {
    this.creationTimestamp = System.currentTimeMillis();
  }

  /**
   * Creates a Sample message with the specified creation timestamp. No other
   * fields are set.
   * 
   * @param timestamp
   *          the time at which this sample was created.
   */
  public SampleMessage(final long timestamp) {
    this.creationTimestamp = timestamp;
  }

  /**
   * The length of this message, in bytes, as encoded according to the
   * Sensor-Aggregator protocol.
   * 
   * @return the length of this message.
   */
  public int getLengthPrefixSensor() {
    // physicalLayer, devId, recvId, timestamp, rssi
    int length = 1 + DEVICE_ID_SIZE * 2 + 8 + 4;
    if (this.sensedData != null) {
      length += this.sensedData.length;
    }
    return length;
  }

  /**
   * The length of this message, in bytes, as encoded according to the
   * Aggregator-Solver protocol.
   * 
   * @return the length of this message.
   */
  public int getLengthPrefixSolver() {
    // physicalLayer, messageId, devId, recvId, timestamp, rssi
    int length = 2 + DEVICE_ID_SIZE * 2 + 8 + 4;
    if (this.sensedData != null) {
      length += this.sensedData.length;
    }
    return length;
  }

  /**
   * Returns the device identifier for the transmitter referenced in this
   * sample.
   * 
   * @return the device identifier for the transmitter referenced in this
   *         sample.
   */
  public byte[] getDeviceId() {
    return this.deviceId;
  }

  /**
   * Sets the device identifier for the transmitter referenced in this sample.
   * 
   * @param deviceId
   *          the device identifier for the transmitter referenced in this
   *          sample.
   */
  public void setDeviceId(byte[] deviceId) {
    if (deviceId == null) {
      throw new RuntimeException("Device ID cannot be null.");
    }
    if (deviceId.length != DEVICE_ID_SIZE) {
      throw new RuntimeException(String.format(
          "Device ID must be %d bytes long.", Integer.valueOf(DEVICE_ID_SIZE)));
    }
    this.deviceId = deviceId;
  }

  /**
   * Returns the device identifier for the receiver referenced in this sample.
   * 
   * @return the device identifier for the receiver referenced in this sample.
   */
  public byte[] getReceiverId() {
    return this.receiverId;
  }

  /**
   * Sets the device identifier for the receiver referenced in this sample.
   * 
   * @param receiverId
   *          the device identifier for the receiver referenced in this sample.
   */
  public void setReceiverId(byte[] receiverId) {
    if (receiverId == null) {
      throw new RuntimeException("Receiver ID cannot be null.");
    }
    if (receiverId.length != DEVICE_ID_SIZE) {
      throw new RuntimeException(
          String.format("Receiver ID must be %d bytes long.",
              Integer.valueOf(DEVICE_ID_SIZE)));
    }
    this.receiverId = receiverId;
  }

  /**
   * Returns the UNIX timestamp indicating when this sample was received by the
   * receiver.
   * 
   * @return the UNIX timestamp indicating when this sample was received by the
   *         receiver.
   */
  public long getReceiverTimeStamp() {
    return this.receiverTimeStamp;
  }

  /**
   * Sets the UNIX timestamp indicating when this sample was received by the
   * receiver.
   * 
   * @param receiverTimeStamp
   *          the UNIX timestamp indicating when this sample was received by the
   *          receiver.
   */
  public void setReceiverTimeStamp(long receiverTimeStamp) {
    this.receiverTimeStamp = receiverTimeStamp;
  }

  /**
   * Returns the Received Signal Strength Indicator (RSSI) observed by the
   * receiver for this sample.
   * 
   * @return the Received Signal Strength Indicator (RSSI) observed by the
   *         receiver for this sample.
   */
  public float getRssi() {
    return this.rssi;
  }

  /**
   * Sets the Received Signal Strength Indicator (RSSI) observed by the receiver
   * for this sample.
   * 
   * @param rssi
   *          the Received Signal Strength Indicator (RSSI) observed by the
   *          receiver for this sample.
   */
  public void setRssi(float rssi) {
    this.rssi = rssi;
  }

  /**
   * Returns the raw sensed data sent by the transmitter and observed by the
   * receiver for this sample.
   * 
   * @return the raw sensed data sent by the transmitter and observed by the
   *         receiver for this sample.
   */
  public byte[] getSensedData() {
    return this.sensedData;
  }

  /**
   * Sets the raw sensed data sent by the transmitter and observed by the
   * receiver for this sample.
   * 
   * @param sensedData
   *          the raw sensed data sent by the transmitter and observed by the
   *          receiver for this sample.
   */
  public void setSensedData(byte[] sensedData) {
    this.sensedData = sensedData;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("Sample (").append(this.getPhysicalLayer());
    sb.append(", ");
    sb.append(NumericUtils.toHexString(this.getDeviceId()));
    sb.append(", ");
    sb.append(NumericUtils.toHexString(this.getReceiverId()));
    sb.append("): ");
    sb.append(this.getRssi());
    sb.append(" @ ");
    sb.append(this.getReceiverTimeStamp());
    if (this.getSensedData() != null) {
      sb.append(" [").append(NumericUtils.toHexString(this.getSensedData()));
      sb.append(']');
    }

    return sb.toString();
  }

  /**
   * Returns a timestamp indicating when this Sample was created.
   * 
   * @return the creation timestamp of this Sample.
   * @see #SampleMessage()
   * @see #SampleMessage(long)
   */
  public long getCreationTimestamp() {
    return this.creationTimestamp;
  }

  /**
   * Generates a generic message with the following values.
   * <ul>
   * <li>DeviceId = 0000 0000 0000 0000 0000 0000 0000 0001</li>
   * <li>ReceiverId = 0000 0000 0000 0000 0000 0000 0000 0001</li>
   * <li>Physical layer = 01</li>
   * <li>Rssi = -50</li>
   * <li>Data = 0000</li>
   * <li>Timestamp = current time</li>
   * </ul>
   * 
   * @return the created message.
   */
  public static SampleMessage getTestMessage() {
    SampleMessage message = new SampleMessage();
    message.setDeviceId(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 1 });
    message.setReceiverId(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 1 });
    message.setPhysicalLayer((byte) 1);
    message.setRssi(-50f);
    message.setSensedData(new byte[] { 0, 0 });
    return message;
  }

  @Override
  public SampleMessage clone() throws CloneNotSupportedException {
    SampleMessage clone = (SampleMessage) super.clone();
    if (this.sensedData != null) {
      clone.setSensedData(Arrays
          .copyOf(this.sensedData, this.sensedData.length));
    }
    return clone;
  }
}
