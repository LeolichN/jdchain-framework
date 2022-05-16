package com.jd.blockchain.sdk.client;

import com.jd.blockchain.crypto.HashDigest;
import com.jd.blockchain.ledger.BlockchainKeypair;
import com.jd.blockchain.ledger.CryptoSetting;
import com.jd.blockchain.ledger.DigitalSignature;
import com.jd.blockchain.ledger.TransactionRequest;
import com.jd.blockchain.ledger.TransactionResponse;
import com.jd.blockchain.sdk.BlockchainService;
import com.jd.blockchain.sdk.BlockchainServiceFactory;
import com.jd.blockchain.sdk.proxy.HttpBlockchainBrowserService;
import com.jd.blockchain.transaction.SignatureUtils;
import com.jd.blockchain.transaction.TransactionService;
import com.jd.blockchain.transaction.TxRequestMessage;
import com.jd.httpservice.agent.HttpServiceAgent;
import com.jd.httpservice.agent.ServiceConnection;
import com.jd.httpservice.agent.ServiceConnectionManager;
import com.jd.httpservice.agent.ServiceEndpoint;
import utils.net.SSLSecurity;
import utils.net.NetworkAddress;

import java.io.Closeable;

public class GatewayServiceFactory implements BlockchainServiceFactory, Closeable {

    private ServiceConnectionManager httpConnectionManager;

    private BlockchainKeypair userKey;

    private BlockchainService blockchainService;

    protected GatewayServiceFactory(ServiceEndpoint gatewayEndpoint, BlockchainKeypair userKey) {
        httpConnectionManager = new ServiceConnectionManager(gatewayEndpoint.isSecure(), gatewayEndpoint.getSslSecurity());
        this.userKey = userKey;

        HttpBlockchainBrowserService queryService = createQueryService(gatewayEndpoint);
        TransactionService txProcSrv = createConsensusService(gatewayEndpoint);

        HashDigest[] ledgerHashs = queryService.getLedgerHashs();
        CryptoSetting[] cryptoSettings = new CryptoSetting[ledgerHashs.length];
        for (int i = 0; i < cryptoSettings.length; i++) {
            cryptoSettings[i] = queryService.getLedgerCryptoSetting(ledgerHashs[i]);
        }
        this.blockchainService = new GatewayBlockchainServiceProxy(ledgerHashs, cryptoSettings, txProcSrv,
                queryService);
    }

    /**
     * 连接网关节点；
     *
     * @param gatewayAddress 网关节点的网络地址；
     * @return 网关服务工厂的实例；
     */
    public static GatewayServiceFactory connect(NetworkAddress gatewayAddress) {
        return connect(gatewayAddress.getHost(), gatewayAddress.getPort(), gatewayAddress.isSecure(), null, new SSLSecurity());
    }

    /**
     * 网关服务工厂的实例；
     *
     * @param gatewayAddress 网关节点的网络地址；
     * @param userKey        自动交易签名的用户密钥；可选参数，如果不为 null，则在提交交易时，自动以参数指定的密钥签署交易；
     * @return 网关服务工厂的实例；
     */
    public static GatewayServiceFactory connect(NetworkAddress gatewayAddress, BlockchainKeypair userKey) {
        return connect(gatewayAddress.getHost(), gatewayAddress.getPort(), gatewayAddress.isSecure(), userKey);
    }

    /**
     * 连接网关节点；
     *
     * @param gatewayHost 网关节点的地址；
     * @param gatewayPort 网关节点的端口；
     * @param secure      是否采用安全的通讯协议(HTTPS)；
     * @return 网关服务工厂的实例；
     */
    public static GatewayServiceFactory connect(String gatewayHost, int gatewayPort, boolean secure) {
        return connect(gatewayHost, gatewayPort, secure, null, new SSLSecurity());
    }

    /**
     * 连接网关节点；
     *
     * @param gatewayHost 网关节点的地址；
     * @param gatewayPort 网关节点的端口；
     * @param secure      是否采用安全的通讯协议(HTTPS)；
     * @param userKey     自动对交易签名的用户密钥；这是可选参数，如果不为 null，则在提交交易时，自动以该用户密钥签署交易；
     * @return 网关服务工厂的实例；
     */
    public static GatewayServiceFactory connect(String gatewayHost, int gatewayPort, boolean secure,
                                                BlockchainKeypair userKey) {
        return connect(gatewayHost, gatewayPort, secure, userKey, new SSLSecurity());
    }

    /**
     * 连接网关节点；
     *
     * @param gatewayHost 网关节点的地址；
     * @param gatewayPort 网关节点的端口；
     * @param secure      是否采用安全的通讯协议(HTTPS)；
     * @param security    SSL配置
     * @return 网关服务工厂的实例；
     */
    public static GatewayServiceFactory connect(String gatewayHost, int gatewayPort, boolean secure, SSLSecurity security) {
        return connect(gatewayHost, gatewayPort, secure, null, security);
    }

    /**
     * 连接网关节点；
     *
     * @param gatewayHost 网关节点的地址；
     * @param gatewayPort 网关节点的端口；
     * @param secure      是否采用安全的通讯协议(HTTPS)；
     * @param userKey     自动对交易签名的用户密钥；这是可选参数，如果不为 null，则在提交交易时，自动以该用户密钥签署交易；
     * @param security    SSL配置
     * @return 网关服务工厂的实例；
     */
    public static GatewayServiceFactory connect(String gatewayHost, int gatewayPort, boolean secure,
                                                BlockchainKeypair userKey, SSLSecurity security) {
        ServiceEndpoint gatewayEndpoint = new ServiceEndpoint(gatewayHost, gatewayPort, secure);
        gatewayEndpoint.setSslSecurity(security);
        GatewayServiceFactory factory = new GatewayServiceFactory(gatewayEndpoint, userKey);
        factory.setMaxConnections(100);
        // TODO: 未实现网关对用户的认证；
        // TODO: 未实现加载不同账本的密码算法配置；
        return factory;
    }

    @Override
    public BlockchainService getBlockchainService() {
        return blockchainService;
    }

    public void setMaxConnections(int maxCount) {
        httpConnectionManager.setMaxTotal(maxCount).setDefaultMaxPerRoute(maxCount);
    }

    private TransactionService createConsensusService(ServiceEndpoint gatewayEndpoint) {
        ServiceConnection connection = httpConnectionManager.create(gatewayEndpoint);
        TransactionService gatewayConsensusService = HttpServiceAgent.createService(HttpConsensusService.class,
                connection, null);
        if (userKey != null) {
            gatewayConsensusService = new EndpointAutoSigner(gatewayConsensusService, userKey);
        }
        return gatewayConsensusService;
    }

    private HttpBlockchainBrowserService createQueryService(ServiceEndpoint gatewayEndpoint) {
        ServiceConnection conn = httpConnectionManager.create(gatewayEndpoint);
        return HttpServiceAgent.createService(HttpBlockchainBrowserService.class, conn, null);
    }

    @Override
    public void close() {
        httpConnectionManager.close();
    }

    private static class EndpointAutoSigner implements TransactionService {

        private TransactionService innerService;

        private BlockchainKeypair userKey;

        public EndpointAutoSigner(TransactionService innerService, BlockchainKeypair userKey) {
            this.innerService = innerService;
            this.userKey = userKey;
        }

        @Override
        public TransactionResponse process(TransactionRequest txRequest) {
            TxRequestMessage reqMsg = (TxRequestMessage) txRequest;
            // TODO: 未实现按不同的账本的密码参数配置，采用不同的哈希算法和签名算法；
            if (!reqMsg.containsEndpointSignature(userKey.getAddress())) {
                // TODO: 优化上下文对此 TransactionContent 的多次序列化带来的额外性能开销；
                DigitalSignature signature = SignatureUtils.sign(txRequest.getTransactionHash(), userKey);
                reqMsg.addEndpointSignatures(signature);
            }
            return innerService.process(txRequest);
        }
    }

}
