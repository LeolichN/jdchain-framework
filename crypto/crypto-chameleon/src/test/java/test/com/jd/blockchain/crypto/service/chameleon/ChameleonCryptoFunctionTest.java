package test.com.jd.blockchain.crypto.service.chameleon;

import com.jd.blockchain.crypto.AsymmetricKeypair;
import com.jd.blockchain.crypto.Crypto;
import com.jd.blockchain.crypto.CryptoAlgorithm;
import com.jd.blockchain.crypto.SignatureFunction;
import org.junit.Test;
import utils.security.RandomUtils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-05-05 19:16
 * @since 0.1.0
 **/
public class ChameleonCryptoFunctionTest {


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
