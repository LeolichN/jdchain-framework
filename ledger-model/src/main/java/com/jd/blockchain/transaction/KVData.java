package com.jd.blockchain.transaction;

import com.jd.blockchain.ledger.BytesValue;
import com.jd.blockchain.ledger.DataAccountKVSetOperation.KVWriteEntry;

public class KVData implements KVWriteEntry {

	private String key;

	private BytesValue value;

	private long expectedVersion;

	private boolean chameleonHash;

	public KVData(String key, BytesValue value, long expectedVersion) {
		this.key = key;
		this.value = value;
		this.expectedVersion = expectedVersion;
		this.chameleonHash = false;
	}

	public KVData(String key, BytesValue value, long expectedVersion,boolean chameleonHash) {
		this.key = key;
		this.value = value;
		this.expectedVersion = expectedVersion;
		this.chameleonHash = chameleonHash;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public BytesValue getValue() {
		return value;
	}

	@Override
	public long getExpectedVersion() {
		return expectedVersion;
	}

	@Override
	public boolean chameleonHash() {
		return chameleonHash;
	}
}