package com.jd.blockchain.crypto.service.sm;

import com.jd.blockchain.crypto.base.HashBaseSecureRandom;
import com.jd.blockchain.crypto.utils.sm.SM3Utils;

/**
 * 采用基于 SHA256 的“哈希法”生成伪随机数；
 * 
 * @author huanghaiquan
 *
 */
public class SM3SecureRandom extends HashBaseSecureRandom {

	private static final long serialVersionUID = 5750528439654395936L;

	public SM3SecureRandom(byte[] seed) {
		super(seed);
	}

	@Override
	protected byte[] hash(byte[] bytes) {
		return SM3Utils.hash(bytes);
	}
}