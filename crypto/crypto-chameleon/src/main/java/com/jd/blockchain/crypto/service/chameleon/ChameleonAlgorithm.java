package com.jd.blockchain.crypto.service.chameleon;

import com.jd.blockchain.crypto.CryptoAlgorithm;
import com.jd.blockchain.crypto.base.CryptoAlgorithmDefinition;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-03-21 17:02
 * @since 0.1.0
 **/
public final class ChameleonAlgorithm {
    public static final CryptoAlgorithm SM3_NEW = CryptoAlgorithmDefinition.defineHash("SM3_NEW", (byte) 99);

    public static final CryptoAlgorithm CHAMELEON = CryptoAlgorithmDefinition.defineSignature("CHAMELEON", true, (byte) 98);

    private ChameleonAlgorithm(){}
}
