package com.jd.blockchain.transaction;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.PrimitiveType;
import com.jd.blockchain.consts.DataCodes;
import com.jd.blockchain.ledger.BytesValue;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-05-15 21:37
 * @since 0.1.0
 **/
@DataContract(code = DataCodes.TX_OP_DATA_ACC_CHECK_ONCE)
public interface DataAccountChameleonOnceCheck {

    @DataField(order = 0,primitiveType = PrimitiveType.BYTES)
    BytesValue hashDataOnce(byte[] data,byte[] pubKey);
}
