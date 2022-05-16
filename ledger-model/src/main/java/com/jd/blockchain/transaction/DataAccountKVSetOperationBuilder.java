package com.jd.blockchain.transaction;

import com.jd.blockchain.ledger.BytesValue;
import com.jd.blockchain.ledger.DataAccountKVSetOperation;

import utils.Bytes;

/**
 * @author huanghaiquan
 *
 */
public interface DataAccountKVSetOperationBuilder {

	/**
	 * 数据账户的KV写入操作；
	 * 
	 * @return
	 */
	DataAccountKVSetOperation getOperation();

	/**
	 * 写入键值；
	 * 
	 * @return
	 */
	/**
	 * @param key        键；
	 * @param value      值；
	 * @param expVersion 预期的当前版本；如果版本不匹配，则写入失败；
	 * @return
	 */
	DataAccountKVSetOperationBuilder set(String key, BytesValue value, long expVersion);

	/**
	 * 写入字节数组；
	 * 
	 * @param key        键；
	 * @param value      值；byte[]格式
	 * @param expVersion 预期的当前版本；如果版本不匹配，则写入失败；
	 * @return
	 */
	DataAccountKVSetOperationBuilder setBytes(String key, byte[] value, long expVersion);

	/**
	 * 写入字节数组；
	 *
	 * @param key        键；
	 * @param value      值；Bytes格式
	 * @param expVersion 预期的当前版本；如果版本不匹配，则写入失败；
	 * @return
	 */
	DataAccountKVSetOperationBuilder setBytes(String key, Bytes value, long expVersion);

	/**
	 * 写入键值；
	 *
	 * @param key        键；
	 * @param value      值；String格式
	 * @param expVersion 预期的当前版本；如果版本不匹配，则写入失败；
	 * @return
	 */
	DataAccountKVSetOperationBuilder setImage(String key, byte[] value, long expVersion);

//	/**
//	 * 写入文本键值；
//	 *
//	 * @param key
//	 *            键；
//	 * @param value
//	 *            值；String格式
//	 * @param expVersion
//	 *            预期的当前版本；如果版本不匹配，则写入失败；
//	 * @return
//	 */
//	@Deprecated
//	DataAccountKVSetOperationBuilder set(String key, String value, long expVersion);

	/**
	 * 写入文本键值；
	 *
	 * @param key        键；
	 * @param value      值；String格式
	 * @param expVersion 预期的当前版本；如果版本不匹配，则写入失败；
	 * @return
	 */
	DataAccountKVSetOperationBuilder setText(String key, String value, long expVersion);

	/**
	 * 写入文本键值；
	 *
	 * @param key        键；
	 * @param value      值；String格式
	 * @param expVersion 预期的当前版本；如果版本不匹配，则写入失败；
	 * @param isChameleonHash 是否参与账户值的哈希计算
	 * @return
	 */
	DataAccountKVSetOperationBuilder setText(String key, String value, long expVersion,boolean isChameleonHash);

	/**
	 * 写入JSON键值；
	 *
	 * @param key        键；
	 * @param value      值；String格式
	 * @param expVersion 预期的当前版本；如果版本不匹配，则写入失败；
	 * @return
	 */
	DataAccountKVSetOperationBuilder setJSON(String key, String value, long expVersion);

	/**
	 * 写入XML键值；
	 *
	 * @param key        键；
	 * @param value      值；String格式
	 * @param expVersion 预期的当前版本；如果版本不匹配，则写入失败；
	 * @return
	 */
	DataAccountKVSetOperationBuilder setXML(String key, String value, long expVersion);

	/**
	 * 写入64位整数；
	 *
	 * @param key        键；
	 * @param value      值；long格式
	 * @param expVersion 预期的当前版本；如果版本不匹配，则写入失败；
	 * @return
	 */
	DataAccountKVSetOperationBuilder setInt64(String key, long value, long expVersion);

	/**
	 * 写入时间戳；
	 *
	 * @param key        键；
	 * @param value      值；long格式
	 * @param expVersion 预期的当前版本；如果版本不匹配，则写入失败；
	 * @return
	 */
	DataAccountKVSetOperationBuilder setTimestamp(String key, long value, long expVersion);

}
