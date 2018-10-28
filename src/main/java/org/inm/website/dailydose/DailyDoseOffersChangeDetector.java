package org.inm.website.dailydose;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.inm.interest.Interest;
import org.inm.website.AbstractChangeDetector;
import org.inm.website.ChunkedIterator;
import org.inm.website.ChunkedIterator.ChunkProvider;

public class DailyDoseOffersChangeDetector extends AbstractChangeDetector implements ChunkProvider<Interest> {

	private int offset = 0;
	private URL url;

	public DailyDoseOffersChangeDetector() {
		super();
		this.setReader(new DailyDoseOffersReader());
		this.setTransformator(new DailyDoseOffersTransformator());
	}

	private URL getCurrentUrlString() {
		try {
			return new URL(this.url.toExternalForm() + "&offset=" + offset);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Iterator<Interest> readAndTransform(URL url) {
		this.url = url;
		return new ChunkedIterator<Interest>(this);
	}

	protected void stepForward() {
		this.offset = this.offset + 20;
	}

	@Override
	public Iterator<Interest> getNextChunk() {
		Iterator<Interest> chunk = super.readAndTransform(getCurrentUrlString());
		stepForward();
		return chunk;
	}

}