package com.itsa.conn.bluetooth;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import com.itsa.conn.BluetoothConnection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class AndroidBluetoothConnection extends	BluetoothConnection<BluetoothDevice> {

	private BluetoothAdapter adpter;
	
	BluetoothSocket btSocket;
	public AndroidBluetoothConnection() {
		adpter = BluetoothAdapter.getDefaultAdapter();
	}
	
	@Override
	public boolean checkDeviceAvailability() {
		return adpter != null;
	}

	@Override
	public boolean isEnabled() {
		return adpter.isEnabled();
	}

	@Override
	public Set<BluetoothDevice> getPairedDevices() {
		return adpter.getBondedDevices();
	}

	@Override
	public void connect(String mac) throws IOException {
		connect(adpter.getRemoteDevice(mac), 1);
	}

	@Override
	public void connect(BluetoothDevice device, int porta) throws IOException {
		try {
			Method m;
			m = device.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
			btSocket = (BluetoothSocket) m.invoke(device, 1);
			cancelDiscovery();
			btSocket.connect();
			output = btSocket.getOutputStream();
			input =  btSocket.getInputStream();
		} catch (NoSuchMethodException e) {
			throw new IOException("Couldn't connect", e);
		} catch (IllegalAccessException e) {
			throw new IOException("Couldn't connect", e);
		} catch (IllegalArgumentException e) {
			throw new IOException("Couldn't connect", e);
		} catch (InvocationTargetException e) {
			throw new IOException("Couldn't connect", e);
		}
	}

	@Override
	public void discoverDevices() {
		adpter.startDiscovery();
	}

	@Override
	public void cancelDiscovery() {
		adpter.cancelDiscovery();

	}

	@Override
	public boolean isConnected() {
		return btSocket.isConnected();
	}

	@Override
	public String getAddress() {
		return adpter.getAddress();
	}

	@Override
	public String getName() {
		return adpter.getName();
	}

}