/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 */
package com.itsa.traffic.packet;

import java.nio.ByteBuffer;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.itsa.conn.Connection;
import com.itsa.conn.packet.AbstractReadablePacket;
import com.itsa.traffic.element.Car;
import com.itsa.traffic.handler.TrafficManager;

/**
 * Created on: Jan 27, 2015
 * 
 * @author Alisson Oliveira
 *
 */
public class R_VehiclePacket extends AbstractReadablePacket<TrafficManager> {
	
	public static final short OPCODE = 0x03;
	
	protected int id;
	protected int service;
	protected String serviceContext;
	protected double longitude;
	protected double latitude;
	protected double altitude;
	
	/* (non-Javadoc)
	 * @see com.itsa.conn.packet.ReadablePacket#read(com.itsa.conn.Connection, java.nio.ByteBuffer)
	 */
	@Override
	public void read(Connection conn, ByteBuffer buf) {
		id = buf.getInt();
		service = buf.getInt();
		serviceContext = readString(buf);
		longitude = buf.getDouble();
		latitude = buf.getDouble();
		altitude = buf.getDouble();
	}

	/* (non-Javadoc)
	 * @see com.itsa.conn.packet.ReadablePacket#process(com.itsa.conn.Connection, com.itsa.conn.Manager)
	 */
	@Override
	public void process(Connection conn, final TrafficManager manager) {
		Log.i("Vehicle", "service " + service);
		manager.addCar(new Car(id, latitude, longitude, service, serviceContext));
		(new Handler(Looper.getMainLooper())).post(new Runnable() {
			
			@Override
			public void run() {
				manager.updateTraffic();
			}
		});

	}

}
