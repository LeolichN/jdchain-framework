package com.jd.blockchain.ledger;

import com.jd.blockchain.transaction.DataAccountChameleonOnceCheck;
import utils.crypto.chameleon.ChameleonUtils;

import java.math.BigInteger;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-05-15 22:23
 * @since 0.1.0
 **/
public class ChameleonOnceCheck implements DataAccountChameleonOnceCheck {

    @Override
    public byte[] hashDataOnce(byte[] data, byte[] pubKey) {
        return ChameleonUtils.sign(data, BigInteger.ONE,pubKey);
    }
}
