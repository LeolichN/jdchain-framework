package com.jd.blockchain.crypto.service.classic.chameleon;

import com.jd.blockchain.crypto.*;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-03-21 16:43
 * @since 0.1.0
 **/
public class ChameleonCryptoFunction implements SignatureFunction, CASignatureFunction {
    private static final CryptoAlgorithm CHAMELEON = ChameleonAlgorithm.CHAMELEON;

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
    public PubKey retrievePubKey(PrivKey privKey) {
        return null;
    }

    @Override
    public boolean supportPrivKey(byte[] privKeyBytes) {
        return false;
    }

    @Override
    public PrivKey resolvePrivKey(byte[] privKeyBytes) {
        return null;
    }

    @Override
    public boolean supportPubKey(byte[] pubKeyBytes) {
        return false;
    }

    @Override
    public PubKey resolvePubKey(byte[] pubKeyBytes) {
        return null;
    }

    @Override
    public boolean supportDigest(byte[] digestBytes) {
        return false;
    }

    @Override
    public SignatureDigest resolveDigest(byte[] digestBytes) {
        return null;
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
        return null;
    }

    @Override
    public AsymmetricKeypair generateKeypair() {
        return null;
    }

    @Override
    public AsymmetricKeypair generateKeypair(byte[] seed) {
        return null;
    }

    @Override
    public <T extends CryptoBytes> boolean support(Class<T> cryptoDataType, byte[] encodedCryptoBytes) {
        return false;
    }
}
