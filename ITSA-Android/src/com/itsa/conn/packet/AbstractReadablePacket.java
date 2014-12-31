/**
 * 
 */
package com.itsa.conn.packet;

import java.nio.ByteBuffer;

import com.itsa.conn.Connection;

/**
 * @author Alisson Oliveira
 *
 */
public abstract class AbstractReadablePacket<T extends Connection> implements ReadablePacket<T> {
	
	protected String readString(ByteBuffer buf) {
		char ch;
		StringBuilder sb = new StringBuilder();
		while ((ch = buf.getChar()) != '\000') {
			sb.append(ch);
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "Readable Packet " + getClass().getSimpleName();
	}

}