package com.sevendeleven.chatprogram.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class DataTranslator {
	
	public static Integer readInteger(InputStream is) {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		try {
			for (int i = 0; i < Integer.BYTES; i++) {
					int read = is.read();
					if (read != -1) {
						buffer.put((byte)read);
					} else {
						System.err.println("Error processing integer: input stream unexpectedly closed");
					}
			}
		} catch (IOException e) {
			System.err.println("Error processing integer");
			System.exit(-1);
		}
		buffer.flip();
		return buffer.getInt();
	}
	
	public static Float readFloat(InputStream is) {
		ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
		try {
			for (int i = 0; i < Float.BYTES; i++) {
					int read = is.read();
					if (read != -1) {
						buffer.put((byte)read);
					} else {
						System.err.println("Error processing float: input stream unexpectedly closed");
					}
			}
		} catch (IOException e) {
			System.err.println("Error processing float");
			System.exit(-1);
		}
		buffer.flip();
		return buffer.getFloat();
	}
	
	public static Double readDouble(InputStream is) {
		ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
		try {
			for (int i = 0; i < Double.BYTES; i++) {
					int read = is.read();
					if (read != -1) {
						buffer.put((byte)read);
					} else {
						System.err.println("Error processing double: input stream unexpectedly closed");
					}
			}
		} catch (IOException e) {
			System.err.println("Error processing double");
			System.exit(-1);
		}
		buffer.flip();
		return buffer.getDouble();
	}
	
	public static Long readLong(InputStream is) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		try {
			for (int i = 0; i < Long.BYTES; i++) {
					int read = is.read();
					if (read != -1) {
						buffer.put((byte)read);
					} else {
						System.err.println("Error processing long: input stream unexpectedly closed");
					}
			}
		} catch (IOException e) {
			System.err.println("Error processing long");
			System.exit(-1);
		}
		buffer.flip();
		return buffer.getLong();
	}
	
	public static Short readShort(InputStream is) {
		ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
		try {
			for (int i = 0; i < Short.BYTES; i++) {
					int read = is.read();
					if (read != -1) {
						buffer.put((byte)read);
					} else {
						System.err.println("Error processing short: input stream unexpectedly closed");
					}
			}
		} catch (IOException e) {
			System.err.println("Error processing short");
			System.exit(-1);
		}
		buffer.flip();
		return buffer.getShort();
	}
	
	public static String readString(InputStream is) {
		String r = "";
		int read = 0;
		boolean escaped = false;
		try {
			while ((read = is.read()) != -1) {
				byte b = (byte) read;
				if (escaped) {
					r = r + (char)b;
					escaped = false;
				} else {
					if (b == PacketInformation.BYTE_STR_ESCAPE) {
						escaped = true;
					} else if (b == PacketInformation.BYTE_STR_END) {
						return r;
					} else {
						r = r + (char)b;
					}
				}
			}
			if (read == -1) {
				System.err.println("Error processing String: end of stream reached");
				System.exit(-1);
			}
		} catch (IOException e) {
			System.err.println("Error processing String: IOException occurred");
			System.exit(-1);
		}
		return r;
	}
	
	public static String readString(byte[] data) {
		String r = "";
		boolean escaped = false;
		for (int i = 1; i < data.length; i++) {
			byte b = data[i];
			if (escaped) {
				r = r + (char)b;
				escaped = false;
			} else {
				if (b == PacketInformation.BYTE_STR_ESCAPE) {
					escaped = true;
				} else if (b == PacketInformation.BYTE_STR_END) {
					return r;
				} else {
					r = r + (char)b;
				}
			}
		}
		return r;
	}

	public static void writeInteger(OutputStream os, Integer o) {
		ByteBuffer b = ByteBuffer.allocate(Integer.BYTES);
		b.putInt(o);
		try {
			os.write(b.array());
		} catch (IOException e) {
			System.err.println("Error writing Integer: io exception occurred");
			System.exit(-1);
		}
	}
	public static byte[] writeInteger(Integer o) {
		ByteBuffer b = ByteBuffer.allocate(Integer.BYTES);
		b.putInt(o);
		return b.array();
	}
	public static void writeFloat(OutputStream os, Float o) {
		ByteBuffer b = ByteBuffer.allocate(Float.BYTES);
		b.putFloat(o);
		try {
			os.write(b.array());
		} catch (IOException e) {
			System.err.println("Error writing Float: io exception occurred");
			System.exit(-1);
		}
	}
	public static void writeDouble(OutputStream os, Double o) {
		ByteBuffer b = ByteBuffer.allocate(Double.BYTES);
		b.putDouble(o);
		try {
			os.write(b.array());
		} catch (IOException e) {
			System.err.println("Error writing Double: io exception occurred");
			System.exit(-1);
		}
	}
	public static void writeLong(OutputStream os, Long o) {
		ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
		b.putLong(o);
		try {
			os.write(b.array());
		} catch (IOException e) {
			System.err.println("Error writing Long: io exception occurred");
			System.exit(-1);
		}
	}
	public static void writeShort(OutputStream os, Short o) {
		ByteBuffer b = ByteBuffer.allocate(Short.BYTES);
		b.putShort(o);
		try {
			os.write(b.array());
		} catch (IOException e) {
			System.err.println("Error writing Short: io exception occurred");
			System.exit(-1);
		}
	}
	public static void writeString(OutputStream os, String s) {
		try {
		os.write(PacketInformation.BYTE_STR_BEGIN);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ((byte)c==PacketInformation.BYTE_STR_END || (byte)c==PacketInformation.BYTE_STR_ESCAPE) { 
				os.write(PacketInformation.BYTE_STR_ESCAPE);
			}
			os.write((int)c);
		}
		os.write(PacketInformation.BYTE_STR_END);
		} catch (IOException e) {
			System.err.println("Error writing String: io exception occurred");
		}
	}
	public static byte[] writeString(String s) {
		String ret = "" + (char)PacketInformation.BYTE_STR_BEGIN;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ((byte)c==PacketInformation.BYTE_STR_END || (byte)c==PacketInformation.BYTE_STR_ESCAPE) { 
				ret = ret + ((char)PacketInformation.BYTE_STR_ESCAPE);
			}
			ret = ret + c;
		}
		ret = ret + ((char)PacketInformation.BYTE_STR_END);
		return ret.getBytes();
	}
	
	public static int intFromBytes(byte[] data) {
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.put(data);
		buffer.flip();
		return buffer.getInt();
	}
	
	public static Byte[] getBytes(byte[] bytes) {
		Byte[] data = new Byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			data[i] = bytes[i];
		}
		return data;
	}
	public static byte[] getBytes(Byte[] bytes) {
		byte[] data = new byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			data[i] = bytes[i];
		}
		return data;
	}
	
}
