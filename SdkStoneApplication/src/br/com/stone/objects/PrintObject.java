package br.com.stone.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class PrintObject implements Parcelable {
	
	public static final Integer LEFT   = 0;
	public static final Integer CENTER = 1;
	public static final Integer RIGHT  = 2;
	
	public static final Integer SMALL  = 0;
	public static final Integer MEDIUM = 2;
	public static final Integer BIG    = 6;
	
	public static final Integer TAG_PRINT = 99;
	
	private String message;
	private Integer size;
	private Integer align;
	
	public PrintObject() {
		super();
	}
	
	public PrintObject(String message, Integer size, Integer align) {
		this.message = message;
		this.size = size;
		this.align = align;
	}
	
	public PrintObject(Parcel source) {
		this.message = source.readString();
		this.size = source.readInt();
		this.align = source.readInt();
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.message);
		dest.writeInt(this.size);
		dest.writeInt(this.align);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getAlign() {
		return align;
	}

	public void setAlign(Integer align) {
		this.align = align;
	}

	public String toString(){
		return "Message: " + getMessage() + "\nSize: " + getSize() + "\nAlign: " + getAlign();
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

		public PrintObject createFromParcel(Parcel source) {
			return new PrintObject(source);
		}

		public PrintObject[] newArray(int size) {
			return new PrintObject[size];
		}
	};
	
}