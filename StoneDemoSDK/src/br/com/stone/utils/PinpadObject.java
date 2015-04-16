package br.com.stone.utils;

import java.io.Serializable;

import android.bluetooth.BluetoothClass.Device;

public class PinpadObject extends Device implements Serializable {

	private static final long serialVersionUID = -9176651367580907283L;
	private String  name;
	private String  mac_address;
	private boolean print_support;

	public boolean isPrint_support() {
		return print_support;
	}

	public void setPrint_support(boolean print_support) {
		this.print_support = print_support;
	}

	public String getNamePinpad() {
		return name;
	}

	public void setNamePinpad(String name) {
		this.name = name;
	}

	public String getMacAddressPinpad() {
		return mac_address;
	}

	public void setMacAddressPinpad(String mac_address) {
		this.mac_address = mac_address;
	}
	
	public String toString() {
		
		String pinpad = "\nPinpad name: "      + this.getNamePinpad()       +
						"\nPinpad address: " + this.getMacAddressPinpad() +
						"\nPinpad print: "   + this.isPrint_support();
		return pinpad;
	}

}
