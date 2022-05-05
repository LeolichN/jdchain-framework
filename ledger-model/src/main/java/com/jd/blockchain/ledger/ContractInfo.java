package com.jd.blockchain.ledger;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.PrimitiveType;
import com.jd.blockchain.consts.DataCodes;

@DataContract(code = DataCodes.CONTRACT_ACCOUNT_HEADER)
public interface ContractInfo extends BlockchainIdentity, AccountSnapshot, PermissionAccount {

    @DataField(order = 4, primitiveType = PrimitiveType.BYTES)
    byte[] getChainCode();

    @DataField(order = 5, primitiveType = PrimitiveType.INT64)
    long getChainCodeVersion();

    @DataField(order = 6, refEnum = true)
    AccountState getState();

    @DataField(order = 7, refEnum = true)
    ContractLang getLang();
}
