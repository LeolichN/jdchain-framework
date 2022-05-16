package com.jd.blockchain.ledger;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.PrimitiveType;
import com.jd.blockchain.consts.DataCodes;

import com.jd.blockchain.transaction.DataAccountChameleonOnceCheck;
import utils.Bytes;

@DataContract(code= DataCodes.TX_OP_DATA_ACC_SET)
public interface DataAccountKVSetOperation extends Operation {
	
	@DataField(order=2, primitiveType=PrimitiveType.BYTES)
	Bytes getAccountAddress();
	
	@DataField(order=3, list=true, refContract=true)
	KVWriteEntry[] getWriteSet();
	
	
	@DataContract(code=DataCodes.TX_OP_DATA_ACC_SET_KV)
	public static interface KVWriteEntry{

		@DataField(order=1, primitiveType=PrimitiveType.TEXT)
		String getKey();

		@DataField(order=2, refContract = true)
		BytesValue getValue();

		@DataField(order=3, primitiveType=PrimitiveType.INT64)
		long getExpectedVersion();
		
		@DataField(order = 4,primitiveType = PrimitiveType.BOOLEAN)
		boolean chameleonHash();
	}

}
