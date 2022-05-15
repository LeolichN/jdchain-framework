package test.com.jd.blockchain.crypto.service.chameleon;

import com.jd.blockchain.crypto.*;
import com.jd.blockchain.crypto.base.AlgorithmUtils;
import org.junit.Test;
import utils.io.BytesUtils;
import utils.security.RandomUtils;

import static com.jd.blockchain.crypto.CryptoKeyType.PRIVATE;
import static com.jd.blockchain.crypto.CryptoKeyType.PUBLIC;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-05-05 19:16
 * @since 0.1.0
 **/
public class ChameleonCryptoFunctionTest {

    @Test
    public void generateKeyPairTest() {

        CryptoAlgorithm algorithm = Crypto.getAlgorithm("CHAMELEON");
        assertNotNull(algorithm);

        SignatureFunction signatureFunction = Crypto.getSignatureFunction(algorithm);

        AsymmetricKeypair keyPair = signatureFunction.generateKeypair();

        PubKey pubKey = keyPair.getPubKey();
        PrivKey privKey = keyPair.getPrivKey();

        assertEquals(PUBLIC.CODE, pubKey.getKeyType().CODE);
        assertEquals(130, pubKey.getRawKeyBytes().length);
        assertEquals(PRIVATE.CODE, privKey.getKeyType().CODE);
        assertEquals(64, privKey.getRawKeyBytes().length);

        assertEquals(algorithm.code(), pubKey.getAlgorithm());
        assertEquals(algorithm.code(), privKey.getAlgorithm());

        assertEquals(2 + 1 + 130, pubKey.toBytes().length);
        assertEquals(2 + 1 + 64, privKey.toBytes().length);

        byte[] algoBytes = AlgorithmUtils.getCodeBytes(algorithm);
        byte[] pubKeyTypeBytes = new byte[] { PUBLIC.CODE };
        byte[] privKeyTypeBytes = new byte[] { PRIVATE.CODE };
        byte[] rawPubKeyBytes = pubKey.getRawKeyBytes();
        byte[] rawPrivKeyBytes = privKey.getRawKeyBytes();
        assertArrayEquals(BytesUtils.concat(algoBytes, pubKeyTypeBytes, rawPubKeyBytes), pubKey.toBytes());
        assertArrayEquals(BytesUtils.concat(algoBytes, privKeyTypeBytes, rawPrivKeyBytes), privKey.toBytes());
    }

    @Test
    public void retrievePubKeyTest() {

        CryptoAlgorithm algorithm = Crypto.getAlgorithm("CHAMELEON");
        assertNotNull(algorithm);

        SignatureFunction signatureFunction = Crypto.getSignatureFunction(algorithm);

        AsymmetricKeypair keyPair = signatureFunction.generateKeypair();

        PubKey pubKey = keyPair.getPubKey();
        PrivKey privKey = keyPair.getPrivKey();

        PubKey retrievedPubKey = signatureFunction.retrievePubKey(privKey);

        assertEquals(pubKey.getKeyType(), retrievedPubKey.getKeyType());
        assertEquals(pubKey.getRawKeyBytes().length, retrievedPubKey.getRawKeyBytes().length);
        assertEquals(pubKey.getAlgorithm(), retrievedPubKey.getAlgorithm());
        assertArrayEquals(pubKey.toBytes(), retrievedPubKey.toBytes());
    }


    @Test
    public void generateKeyWithFixedSeedTest() {
        // 验证基于固定的种子是否能够生成相同密钥的操作；
        byte[] seed = RandomUtils.generateRandomBytes(32);

        CryptoAlgorithm algorithm = Crypto.getAlgorithm("CHAMELEON");
        assertNotNull(algorithm);

        SignatureFunction signatureFunction = Crypto.getSignatureFunction(algorithm);
        AsymmetricKeypair keypair1 = signatureFunction.generateKeypair(seed);
        AsymmetricKeypair keypair2 = signatureFunction.generateKeypair(seed);

        assertArrayEquals(keypair1.getPrivKey().toBytes(), keypair2.getPrivKey().toBytes());
        assertArrayEquals(keypair1.getPubKey().toBytes(), keypair2.getPubKey().toBytes());

        // 循环一万次验证结果；
        for (int i = 0; i < 10000; i++) {
            keypair1 = signatureFunction.generateKeypair(seed);
            keypair2 = signatureFunction.generateKeypair(seed);

            assertArrayEquals(keypair1.getPrivKey().toBytes(), keypair2.getPrivKey().toBytes());
            assertArrayEquals(keypair1.getPubKey().toBytes(), keypair2.getPubKey().toBytes());
        }
    }
}
