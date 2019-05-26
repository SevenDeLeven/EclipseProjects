package com.sevendeleven.testproject.util;

import java.nio.ByteBuffer;

import com.sevendeleven.testproject.main.Main;
import com.sevendeleven.testproject.renderer.Camera;
import com.sevendeleven.testproject.world.Chunk;

public class Util {
	
	private static ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
	
	public static Vec2i translateScreenPosToBlockPos(int screenX, int screenY) {
		Camera cam = Main.getMain().getCamera();
		float x = (((float)cam.getX()/(float)Main.getBlockSize()) + (float)screenX/(float)Main.getBlockSize());
		float y = ((float)Chunk.HEIGHT-((float)cam.getY()/(float)Main.getBlockSize())) + ((float)screenY-4.0f)/(float)Main.getBlockSize() - 32f;
		y = Chunk.HEIGHT - y;
		return new Vec2i((int)Math.floor(x), (int)Math.floor(y));
	}
	
	public static Vec2f translateScreenPosToWorldPos(int screenX, int screenY) {
		Camera cam = Main.getMain().getCamera();
		float x = (((float)cam.getX()/(float)Main.getBlockSize()) + (float)screenX/(float)Main.getBlockSize());
		float y = ((float)Chunk.HEIGHT-((float)cam.getY()/(float)Main.getBlockSize())) + ((float)screenY-4.0f)/(float)Main.getBlockSize() - 32f;
		y = (float)Chunk.HEIGHT - y;
		return new Vec2f(x, y);
	}
	
	public static byte[] longToBytes(Long l) {
		b.putLong(l);
		return b.array();
	}
	
	public static long bytesToLong(byte[] a) {
		b.put(a);
		b.flip();
		return b.getLong();
	}
	
}
