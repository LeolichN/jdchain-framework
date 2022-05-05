package com.jd.blockchain.ledger;

import com.jd.binaryproto.DataContract;
import com.jd.binaryproto.DataField;
import com.jd.binaryproto.PrimitiveType;
import com.jd.blockchain.consts.DataCodes;
import com.jd.blockchain.crypto.HashDigest;

/**
 * {@link LedgerMetadata_V2} 是 {@link LedgerMetadata} 的升级版本，新增加了
 * {@link #getRolePrivilegesHash()} 属性；
 *
 * @author huanghaiquan
 *
 */
@DataContract(code = DataCodes.METADATA_V2, name = "LEDGER-METADATA-V2")
public interface LedgerMetadata_V2 extends LedgerMetadata {

	/**
	 * 角色权限集合的根哈希；；
	 *
	 * @return
	 */
	@DataField(order = 4, primitiveType = PrimitiveType.BYTES)
	HashDigest getRolePrivilegesHash();

	/**
	 * 用户角色授权集合的根哈希；
	 *
	 * @return
	 */
	@DataField(order = 5, primitiveType = PrimitiveType.BYTES)
	HashDigest getUserRolesHash();

	/**
	 * 账本结构版本号
	 *         若未配置，则返回-1
	 *
	 * @return
	 */
	@DataField(order = 6, primitiveType = PrimitiveType.INT64)
	long getLedgerStructureVersion();

	/**
	 * 身份认证模式
	 *
	 * @return
	 */
	@DataField(order = 7, refEnum = true)
	IdentityMode getIdentityMode();

	/**
	 * 根证书
	 *
	 * @return
	 */
	@DataField(order = 8, list = true, primitiveType = PrimitiveType.TEXT)
	String[] getLedgerCertificates();

	/**
	 * 初始用户信息
	 *
	 * @return
	 */
	@DataField(order = 9, list = true, refContract = true)
	GenesisUser[] getGenesisUsers();

	/**
	 * 合约运行时配置
	 * @return
	 */
	@DataField(order = 10,  refContract = true)
	ContractRuntimeConfig getContractRuntimeConfig();

}
