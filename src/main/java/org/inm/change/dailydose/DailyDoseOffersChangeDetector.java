package org.inm.change.dailydose;

import java.util.Iterator;

import org.inm.change.AbstractChangeDetector;
import org.inm.change.ChunkedIterator;
import org.inm.change.ChunkedIterator.ChunkProvider;
import org.inm.interests.Interest;


public class DailyDoseOffersChangeDetector extends AbstractChangeDetector
		implements ChunkProvider<Interest> {

	private int offset = 0;

	public DailyDoseOffersChangeDetector() {
		super();
		this.setReader(new DailyDoseOffersReader());
		this.setTransformator(new DailyDoseOffersTransformator());
		this.setURL(getCurrentUrlString());
	}

	private String getCurrentUrlString() {
		return Constants.BASE_URL + "index.php?catid=25&order=siteid&way=&do_search=0&searchword=&user_siteid=&offset="
				+ offset;
	}

	@Override
	protected Iterator<Interest> readAndTransform() {
		return new ChunkedIterator<Interest>(this);
	}

	protected void stepForward() {
		this.offset = this.offset + 20;
		this.setURL(getCurrentUrlString());
	}

	@Override
	public Iterator<Interest> getNextChunk() {
		Iterator<Interest> chunk = super.readAndTransform();
		stepForward();
		return chunk;
	}

}
