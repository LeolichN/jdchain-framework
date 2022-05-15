package com.jd.blockchain.crypto.service.chameleon;

import com.jd.blockchain.crypto.*;
import com.jd.blockchain.crypto.base.AlgorithmUtils;
import com.jd.blockchain.crypto.base.DefaultCryptoEncoding;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import utils.crypto.chameleon.ChameleonPrivateKeyParameters;
import utils.crypto.chameleon.ChameleonPublicKeyParameters;
import utils.crypto.chameleon.ChameleonUtils;
import utils.io.BytesUtils;

import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import static com.jd.blockchain.crypto.CryptoKeyType.PRIVATE;
import static com.jd.blockchain.crypto.CryptoKeyType.PUBLIC;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-03-21 16:43
 * @since 0.1.0
 **/
public class ChameleonCryptoFunction implements SignatureFunction, CASignatureFunction,AsymmetricEncryptionFunction {
    private static final CryptoAlgorithm CHAMELEON = ChameleonAlgorithm.CHAMELEON;
    private static final int ECPOINT_SIZE = 130;
    private static final int PRIVKEY_SIZE = 64;
    private static final int SIGNATUREDIGEST_SIZE = 64;
    private static final int HASHDIGEST_SIZE = 32;


    private static final int PUBKEY_LENGTH = CryptoAlgorithm.CODE_SIZE + CryptoKeyType.TYPE_CODE_SIZE + ECPOINT_SIZE;
    private static final int PRIVKEY_LENGTH = CryptoAlgorithm.CODE_SIZE + CryptoKeyType.TYPE_CODE_SIZE + PRIVKEY_SIZE;
    private static final int SIGNATUREDIGEST_LENGTH = CryptoAlgorithm.CODE_SIZE + SIGNATUREDIGEST_SIZE;

    ChameleonCryptoFunction() {
    }

    @Override
    public SignatureDigest sign(PrivKey privKey, byte[] data) {
        return null;
    }

    @Override
    public boolean verify(SignatureDigest digest, PubKey pubKey, byte[] data) {
        return false;
    }

    @Override
    public byte[] encrypt(PubKey pubKey, byte[] data) {
        byte[] rawPubKeyBytes = pubKey.getRawKeyBytes();
        // 验证原始公钥长度为65字节
        if (rawPubKeyBytes.length != ECPOINT_SIZE) {
            throw new CryptoException("This key has wrong format!");
        }
        if (pubKey.getAlgorithm() != CHAMELEON.code()){
            throw new CryptoException("The is not chameleon public key!");
        }
        return new byte[0];
    }

    @Override
    public byte[] decrypt(PrivKey privKey, byte[] cipherBytes) {
        return new byte[0];
    }

    @Override
    public PubKey retrievePubKey(PrivKey privKey) {
        byte[] rawPrivKeyBytes = privKey.getRawKeyBytes();
        byte[] rawPubKeyBytes = ChameleonUtils.retrievePublicKey(rawPrivKeyBytes);
        return DefaultCryptoEncoding.encodePubKey(CHAMELEON,rawPubKeyBytes);
    }

    @Override
    public boolean supportPrivKey(byte[] privKeyBytes) {
        return privKeyBytes.length == PRIVKEY_LENGTH && AlgorithmUtils.match(CHAMELEON, privKeyBytes)
                && privKeyBytes[CryptoAlgorithm.CODE_SIZE] == PRIVATE.CODE;
    }

    @Override
    public PrivKey resolvePrivKey(byte[] privKeyBytes) {
        if (supportPrivKey(privKeyBytes)) {
            return DefaultCryptoEncoding.createPrivKey(CHAMELEON.code(), privKeyBytes);
        } else {
            throw new CryptoException("privKeyBytes are invalid!");
        }
    }

    @Override
    public boolean supportPubKey(byte[] pubKeyBytes) {
        // 验证输入字节数组长度=算法标识长度+密钥类型长度+椭圆曲线点长度，密钥数据的算法标识对应SM2算法，并且密钥类型是公钥
        return pubKeyBytes.length == PUBKEY_LENGTH && AlgorithmUtils.match(CHAMELEON, pubKeyBytes)
                && pubKeyBytes[CryptoAlgorithm.CODE_SIZE] == PUBLIC.CODE;
    }

    @Override
    public PubKey resolvePubKey(byte[] pubKeyBytes) {
        if (supportPubKey(pubKeyBytes)) {
            return DefaultCryptoEncoding.createPubKey(CHAMELEON.code(), pubKeyBytes);
        } else {
            throw new CryptoException("pubKeyBytes are invalid!");
        }
    }

    @Override
    public boolean supportDigest(byte[] digestBytes) {
        return digestBytes.length == SIGNATUREDIGEST_LENGTH && AlgorithmUtils.match(CHAMELEON, digestBytes);
    }

    @Override
    public SignatureDigest resolveDigest(byte[] digestBytes) {
        if (supportDigest(digestBytes)) {
            return DefaultCryptoEncoding.createSignatureDigest(CHAMELEON.code(), digestBytes);
        } else {
            throw new CryptoException("digestBytes are invalid!");
        }
    }

    @Override
    public PubKey resolvePubKey(X509Certificate certificate) {
        return null;
    }

    @Override
    public PrivKey parsePrivKey(String privateKey) {
        return null;
    }

    @Override
    public PrivKey parsePrivKey(String privateKey, char[] password) {
        return null;
    }

    @Override
    public PubKey resolvePubKey(PKCS10CertificationRequest csr) {
        return null;
    }

    @Override
    public PrivateKey retrievePrivateKey(PrivKey privKey) {
        return null;
    }

    @Override
    public PrivateKey retrievePrivateKey(PrivKey privKey, PubKey pubKey) {
        return null;
    }

    @Override
    public PublicKey retrievePublicKey(PubKey pubKey) {
        return null;
    }

    @Override
    public CryptoAlgorithm getAlgorithm() {
        return CHAMELEON;
    }

    @Override
    public AsymmetricKeypair generateKeypair() {
        return generateKeypair(new SecureRandom());
    }

    @Override
    public AsymmetricKeypair generateKeypair(byte[] seed) {
        return generateKeypair(new SecureRandom(seed));
    }

    public AsymmetricKeypair generateKeypair(SecureRandom random) {
        // 调用ED25519算法的密钥生成算法生成公私钥对priKey和pubKey，返回密钥对
        AsymmetricCipherKeyPair keyPair = ChameleonUtils.generateKeyPair(random);
        ChameleonPrivateKeyParameters privKeyParams = (ChameleonPrivateKeyParameters) keyPair.getPrivate();
        ChameleonPublicKeyParameters pubKeyParams = (ChameleonPublicKeyParameters) keyPair.getPublic();

        byte[] privKeyBytesK = privKeyParams.getK().toByteArray();
        byte[] privKeyBytesX = privKeyParams.getX().toByteArray();
        byte[] privKeyBytes = BytesUtils.concat(updateByteArray(privKeyBytesK,32),updateByteArray(privKeyBytesX,32));

        byte[] pubKeyBytesK = pubKeyParams.getK().getEncoded(false);
        byte[] pubKeyBytesY = pubKeyParams.getY().getEncoded(false);
        byte[] pubKeyBytes = BytesUtils.concat(pubKeyBytesK,pubKeyBytesY);

        PrivKey privKey = DefaultCryptoEncoding.encodePrivKey(CHAMELEON, privKeyBytes);
        PubKey pubKey = DefaultCryptoEncoding.encodePubKey(CHAMELEON, pubKeyBytes);

        return new AsymmetricKeypair(pubKey, privKey);
    }

    private byte[] updateByteArray(byte[] sourceArray,int arraySize){
        byte[] finalArray = new byte[arraySize];
        if (sourceArray.length > arraySize) {
            System.arraycopy(sourceArray, sourceArray.length - arraySize, finalArray, 0, arraySize);
        } else {
            System.arraycopy(sourceArray, 0, finalArray, arraySize - sourceArray.length, sourceArray.length);
        }
        return finalArray;
    }

    @Override
    public <T extends CryptoBytes> boolean support(Class<T> cryptoDataType, byte[] encodedCryptoBytes) {
        return (SignatureDigest.class == cryptoDataType && supportDigest(encodedCryptoBytes))
                || (PubKey.class == cryptoDataType && supportPubKey(encodedCryptoBytes))
                || (PrivKey.class == cryptoDataType && supportPrivKey(encodedCryptoBytes));
    }
}
