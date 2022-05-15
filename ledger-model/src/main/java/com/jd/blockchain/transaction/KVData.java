package com.jd.blockchain.transaction;

import com.jd.blockchain.ledger.BytesValue;
import com.jd.blockchain.ledger.DataAccountKVSetOperation.KVWriteEntry;

public class KVData implements KVWriteEntry {

	private String key;

	private BytesValue value;

	private long expectedVersion;

	private boolean chameleonHash;

	private DataAccountChameleonOnceCheck checker;

	public KVData(String key, BytesValue value, long expectedVersion) {
		this.key = key;
		this.value = value;
		this.expectedVersion = expectedVersion;
		this.chameleonHash = false;
	}

	public KVData(String key, BytesValue value, long expectedVersion,boolean chameleonHash,DataAccountChameleonOnceCheck checker) {
		this.key = key;
		this.value = value;
		this.expectedVersion = expectedVersion;
		this.chameleonHash = chameleonHash;
		this.checker = checker;
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

	@Override
	public DataAccountChameleonOnceCheck hashChameleonOnce(){
		return checker;
	}
}