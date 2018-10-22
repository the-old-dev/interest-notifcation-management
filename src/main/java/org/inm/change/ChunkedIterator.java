package org.inm.change;

import java.util.Iterator;

public class ChunkedIterator<E> implements Iterator<E> {

	public static interface ChunkProvider<E> {
		Iterator<E> getNextChunk();
	}

	private Iterator<E> currentChunk;
	private ChunkProvider<E> chunkProvider;

	public ChunkedIterator(ChunkProvider<E> chunkProvider) {
		this.chunkProvider = chunkProvider;
		nextChunk();
	}

	@Override
	public boolean hasNext() {
		proofChunk();
		return this.currentChunk.hasNext();
	}

	@Override
	public E next() {
		proofChunk();
		return this.currentChunk.next();
	}

	protected void proofChunk() {
		if (!this.currentChunk.hasNext()) {
			nextChunk();
		}
	}

	protected void nextChunk() {
		this.currentChunk = this.chunkProvider.getNextChunk();
	}

}
