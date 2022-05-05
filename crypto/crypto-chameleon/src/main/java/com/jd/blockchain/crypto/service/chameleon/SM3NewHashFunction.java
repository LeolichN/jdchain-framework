package com.jd.blockchain.crypto.service.chameleon;

import com.jd.blockchain.crypto.*;
import com.jd.blockchain.crypto.base.AlgorithmUtils;
import com.jd.blockchain.crypto.base.DefaultCryptoEncoding;
import com.jd.blockchain.crypto.base.EncodedHashDigester;
import utils.crypto.chameleon.sm3new.SM3NewUtils;
import utils.security.Hasher;

import java.util.Arrays;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-03-21 16:42
 * @since 0.1.0
 **/
public class SM3NewHashFunction implements HashFunction {
    private static final CryptoAlgorithm SM3_NEW = ChameleonAlgorithm.SM3_NEW;

    private static final int DIGEST_BYTES = 256 / 8;

    private static final int DIGEST_LENGTH = CryptoAlgorithm.CODE_SIZE + DIGEST_BYTES;

    @Override
    public CryptoAlgorithm getAlgorithm() {
        return SM3_NEW;
    }

    @Override
    public <T extends CryptoBytes> boolean support(Class<T> cryptoDataType, byte[] encodedCryptoBytes) {
        return (HashDigest.class == cryptoDataType && supportHashDigest(encodedCryptoBytes))
                ;
    }

    @Override
    public HashDigest hash(byte[] data) {
        if (data == null) {
            throw new CryptoException("data is null!");
        }
        byte[] digestBytes = SM3NewUtils.hash(data);
        return DefaultCryptoEncoding.encodeHashDigest(SM3_NEW, digestBytes);
    }

    @Override
    public HashDigest hash(byte[] data, int offset, int len) {
        if (data == null) {
            throw new CryptoException("data is null!");
        }

        byte[] digestBytes = SM3NewUtils.hash(data, offset, len);
        return DefaultCryptoEncoding.encodeHashDigest(SM3_NEW, digestBytes);
    }

    @Override
    public boolean verify(HashDigest digest, byte[] data) {
        HashDigest hashDigest = hash(data);
        return Arrays.equals(hashDigest.toBytes(), digest.toBytes());
    }

    @Override
    public boolean supportHashDigest(byte[] digestBytes) {
        // 验证输入字节数组长度=算法标识长度+摘要长度，以及算法标识；
        return AlgorithmUtils.match(SM3_NEW, digestBytes) && DIGEST_LENGTH == digestBytes.length;
    }

    @Override
    public HashDigest resolveHashDigest(byte[] digestBytes) {
        if (supportHashDigest(digestBytes)) {
            return DefaultCryptoEncoding.createHashDigest(SM3_NEW.code(),digestBytes);
        } else {
            throw new CryptoException("digestBytes is invalid!");
        }
    }

    @Override
    public HashDigester beginHash() {
        return new SM3NewHashDigester(SM3NewUtils.beginHash());
    }

    private static class SM3NewHashDigester extends EncodedHashDigester {

        public SM3NewHashDigester(Hasher hasher) {
            super(hasher);
        }

        @Override
        protected HashDigest encodeHashDigest(byte[] rawHashDigestBytes) {
            return DefaultCryptoEncoding.encodeHashDigest(SM3_NEW, rawHashDigestBytes);
        }

    }
}
