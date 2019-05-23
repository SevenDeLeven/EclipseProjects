package com.sevendeleven.terrilla.world;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL15;

import com.sevendeleven.terrilla.main.Main;
import com.sevendeleven.terrilla.main.Register;
import com.sevendeleven.terrilla.render.Model;
import com.sevendeleven.terrilla.util.Loader;
import com.sevendeleven.terrilla.util.Transform;

public class RenderChunk {
	
	private int[][] blocks = new int[32][1024];
	private int[][] backgrounds = new int[32][1024];
	
	private int chunkX;
	
	public RenderChunk(Chunk chunk) {
		this.chunkX = chunk.getXOffset();
	}
	
	public void draw() {
		
	}
	
	public void draw(Vector4f view) {
		if ((view.x+view.w) < chunkX*32 || view.x > chunkX*32+32 || view.y + view.z < 0 || view.y > 1024) {
			return;
		}
		int minX = view.x > chunkX*32 ? (int)Math.floor(view.x%32.0f) : 0;
		int minY = (int) Math.floor(view.y);
		if (minY < 0) minY = 0;
		int maxX = view.x+view.z < (chunkX+1)*Chunk.CHUNK_WIDTH ? (int)Math.ceil((view.x+view.z)%32.0f) : 32;
		int maxY = (int) Math.floor(view.y);
		if (maxY > 1024) maxY = 1024;
		
		
		Map<Integer, List<int[]>> blockTypesInArea = new HashMap<>();
		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				int b = blocks[x][y];
				if (!blockTypesInArea.containsKey(b)) {
					blockTypesInArea.put(b, new ArrayList<int[]>());
				}
				blockTypesInArea.get(b).add(new int[]{x, y});
			}
		}
		
		System.out.println("test");
		for (int bType : blockTypesInArea.keySet()) {
			beginBlockRender(bType);
			for (int[] pos : blockTypesInArea.get(bType)) {
				renderBlock(pos[0],pos[1]);
			}
			endBlockRender(bType);
		}
	}
	
	public void renderBlock(int x, int y) {
		glUniformMatrix4fv(Loader.foregroundShader.getTransformUniform(), false, Transform.getTransformMatrix(x*Main.getBlockSize()+(chunkX*Chunk.CHUNK_WIDTH), y*Main.getBlockSize(), Main.getBlockSize(), Main.getBlockSize()));
		glDrawElements(GL_TRIANGLES, Model.getQuad().getIndexCount(), GL_UNSIGNED_BYTE, 0);
	}
	
	public void beginBlockRender(int id) {
		Tile tile = Register.getTile(id);
		glBindTexture(GL15.GL_TEXTURE_2D, tile.getSprite().getTexture().getID());
	}
	
	public void endBlockRender(int id) {
		
	}
	
	public void updateBlocks(int[][] newBlocks) {
		if (newBlocks == null) {
			System.err.println("Chunk was attempted to be set to null");
			return;
		}
		if (newBlocks.length == Chunk.CHUNK_WIDTH && newBlocks[0].length == Chunk.CHUNK_HEIGHT) {
			this.blocks = newBlocks.clone();
		} else {
			System.err.println("Tried to set blocks to an array not of size " + Chunk.CHUNK_WIDTH + "x" + Chunk.CHUNK_HEIGHT);
		}
	}
	
	public void updateBlock(int newId, int localX, int localY) {
		blocks[localX][localY] = newId;
	}
	
	public int getChunkX() {
		return this.chunkX;
	}
	
	public int getChunkLeft() {
		return this.chunkX*Chunk.CHUNK_WIDTH;
	}
	
	public int getChunkRight() {
		return (this.chunkX+1)*Chunk.CHUNK_WIDTH;
	}
	
}
