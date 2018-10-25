package org.inm.store;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.dizitart.no2.Nitrite;

class Configuration {
	
	private AbstractStoreBase<?> store;
	
	Configuration(AbstractStoreBase<?> store) {
		this.store = store;
	}
	
	Nitrite initialize(boolean inMemory) {
		if (inMemory) {
			return initialize();
		} else {
			return initialize(getFilePath());
		}
	}

	private Nitrite initialize() {
		return Nitrite.builder().autoCommitBufferSize(1).openOrCreate();
	}
	
	private Nitrite initialize(String dbFilePath) {
	
		try {
			File dbFile = new File(dbFilePath);
			FileUtils.forceMkdir(dbFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	
		return Nitrite.builder().filePath(dbFilePath).autoCommitBufferSize(1).openOrCreate();
	
	}

	private String getFilePath() {
		return getDirectory() + File.separator + getFileName();
	}
	
	private String getDirectory() {
		return "db";
	}

	private String getFileName() {
		return this.store.getStoreClass().getSimpleName().toLowerCase() + ".db";
	}

}
