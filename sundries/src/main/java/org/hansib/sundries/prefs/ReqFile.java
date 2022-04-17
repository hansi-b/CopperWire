package org.hansib.sundries.prefs;

import java.io.File;

public class ReqFile<K extends Enum<K>> extends ReqPrefClz<K, File> implements FileConverter {
	ReqFile(K key, Prefs<K> store) {
		super(key, store);
	}
}
