package com.jd.blockchain.crypto.service.chameleon;

import com.jd.blockchain.crypto.*;
import com.jd.blockchain.crypto.base.DefaultCryptoEncoding;
import utils.provider.NamedProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-03-21 16:33
 * @since 0.1.0
 **/
@NamedProvider("CHAMELEON-SOFTWARE")
public class ChameleonCryptoService implements CryptoService{

    public static final SM3NewHashFunction SM3_NEW_HASH_FUNCTION = new SM3NewHashFunction();

    public static final ChameleonCryptoFunction CHAMELEON_CRYPTO_FUNCTION = new ChameleonCryptoFunction();

    private static final CryptoFunction[] ALL_FUNCTIONS = { SM3_NEW_HASH_FUNCTION, CHAMELEON_CRYPTO_FUNCTION};

    private static final Collection<CryptoFunction> FUNCTIONS;

    private static final ChameleonCryptoEncoding ENCODING = new ChameleonCryptoEncoding();

    static {
        List<CryptoFunction> funcs = Arrays.asList(ALL_FUNCTIONS);
        FUNCTIONS = Collections.unmodifiableList(funcs);
    }

    @Override
    public Collection<CryptoFunction> getFunctions() {
        return FUNCTIONS;
    }

    @Override
    public CryptoEncoding getEncoding() {
        return ENCODING;
    }

    public static class ChameleonCryptoEncoding extends DefaultCryptoEncoding {

        @Override
        protected <T extends CryptoBytes> boolean supportCryptoBytes(short algorithmCode, Class<T> cryptoDataType, byte[] encodedCryptoBytes) {
            for (CryptoFunction func : ALL_FUNCTIONS) {
                if (func.getAlgorithm().code() == algorithmCode && func.support(cryptoDataType, encodedCryptoBytes)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isRandomAlgorithm(CryptoAlgorithm algorithm) {
            return false;
        }

        @Override
        public boolean isHashAlgorithm(CryptoAlgorithm algorithm) {
            return isHashAlgorithm(algorithm.code());
        }

        @Override
        public boolean isHashAlgorithm(short algorithmCode) {
            if(ChameleonAlgorithm.SM3_NEW.code() == algorithmCode){
                return true;
            }
            return false;
        }

        @Override
        public boolean isSignatureAlgorithm(short algorithmCode) {
            if(ChameleonAlgorithm.CHAMELEON.code() == algorithmCode){
                return true;
            }
            return false;
        }

        @Override
        public boolean isSignatureAlgorithm(CryptoAlgorithm algorithm) {
            return isSignatureAlgorithm(algorithm.code());
        }

        @Override
        public boolean isEncryptionAlgorithm(short algorithmCode) {
            return false;
        }

        @Override
        public boolean isEncryptionAlgorithm(CryptoAlgorithm algorithm) {
            return false;
        }

        @Override
        public boolean isExtAlgorithm(CryptoAlgorithm algorithm) {
            return false;
        }

        @Override
        public boolean hasAsymmetricKey(CryptoAlgorithm algorithm) {
            return isSignatureAlgorithm(algorithm);
        }

        @Override
        public boolean hasSymmetricKey(CryptoAlgorithm algorithm) {
            return isSymmetricEncryptionAlgorithm(algorithm);
        }

        @Override
        public boolean isSymmetricEncryptionAlgorithm(CryptoAlgorithm algorithm) {
            return false;
        }

        @Override
        public boolean isAsymmetricEncryptionAlgorithm(CryptoAlgorithm algorithm) {
            return false;
        }
    }
}
