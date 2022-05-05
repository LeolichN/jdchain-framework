package com.jd.blockchain.consensus;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.PrimitiveType;
import com.jd.blockchain.consts.DataCodes;

/**
 * 会话凭证；
 * <p>
 * 表示共识客户端中用于建立和共识网络的通讯连接通道时可能用到的一类受认证分配的凭证信息；
 * 
 * @author huanghaiquan
 *
 */
@DataContract(code = DataCodes.CLIENT_SESSION_CREDENTIAL)
public interface SessionCredential {

    @DataField(order = 0, primitiveType = PrimitiveType.INT32)
    int consensusProviderType();

}
