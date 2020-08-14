package com.jd.blockchain.binaryproto;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.jd.blockchain.binaryproto.impl.DataContractContext;
import com.jd.blockchain.binaryproto.impl.HeaderEncoder;
import com.jd.blockchain.utils.io.BytesOutputBuffer;
import com.jd.blockchain.utils.io.BytesSlice;
import com.jd.blockchain.utils.io.BytesUtils;

public class BinaryProtocol {

	/**
	 * 返回指定的数据契约类型的映射；
	 * 
	 * @param contractType
	 * @return
	 */
	public static DataTypeMapping getDataType(Class<?> contractType) {
		// TODO: Not implemented!;
		throw new IllegalStateException("Not implemented!");
	}

	/**
	 * 返回指定的数据契约类型的映射；
	 * 
	 * @param contractType
	 * @return
	 */
	public static DataTypeMapping getDataType(int code, long version) {
		// TODO: Not implemented!;
		throw new IllegalStateException("Not implemented!");
	}

	/**
	 * 返回全部的数据契约类型的映射；
	 * 
	 * @return
	 */
	public static List<DataTypeMapping> getDataTypes() {
		// TODO: Not implemented!;
		throw new IllegalStateException("Not implemented!");
	}

	public static void encode(Object data, Class<?> contractType, OutputStream out) {
		DataContractEncoder encoder = DataContractContext.resolve(contractType);
		if (encoder == null) {
			throw new IllegalArgumentException("Contract Type not exist!--" + contractType.getName());
		}
		BytesOutputBuffer buffer = new BytesOutputBuffer();
		encoder.encode(data, buffer);
		buffer.writeTo(out);
	}

	public static byte[] encode(Object data, Class<?> contractType) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		encode(data, contractType, out);
		return out.toByteArray();
	}

	public static <T> T decode(InputStream in) {
		byte[] bytes = BytesUtils.copyToBytes(in);
		return decode(bytes);
	}

	public static <T> T decode(byte[] dataSegment) {
		BytesSlice bytes = new BytesSlice(dataSegment, 0, dataSegment.length);
		int code = HeaderEncoder.resolveCode(bytes);
		long version = HeaderEncoder.resolveVersion(bytes);

		DataContractEncoder encoder = DataContractContext.ENCODER_LOOKUP.lookup(code, version);
		if (encoder == null) {
			throw new DataContractException(
					String.format("No data contract was registered with code[%s] and version[%s]!", code, version));
		}
		return encoder.decode(bytes.getInputStream());
	}

	public static <T> T decode(byte[] dataSegment, Class<T> contractType) {
		return decode(dataSegment, contractType, true);
	}

	public static <T> T decode(byte[] dataSegment, Class<T> contractType, boolean autoRegister) {
		DataContractEncoder encoder = DataContractContext.ENCODER_LOOKUP.lookup(contractType);
		if (encoder == null) {
			if (autoRegister) {
				encoder = DataContractContext.resolve(contractType);
			} else {
				throw new DataContractException("Contract type is not registered! --" + contractType.toString());
			}
		}
		BytesSlice bytes = new BytesSlice(dataSegment, 0, dataSegment.length);
		return encoder.decode(bytes.getInputStream());
	}

	/**
	 * 不推荐使用；替换为 {@link #decode(byte[], Class)};
	 * 
	 * @param <T>
	 * @param dataSegment
	 * @param contractType
	 * @return
	 */
	@Deprecated
	public static <T> T decodeAs(byte[] dataSegment, Class<T> contractType) {
		return decode(dataSegment, contractType, true);
	}

	/**
	 * 不推荐使用；替换为 {@link #decode(byte[], Class, boolean)};
	 * 
	 * @param <T>
	 * @param dataSegment
	 * @param contractType
	 * @param autoRegister
	 * @return
	 */
	@Deprecated
	public static <T> T decodeAs(byte[] dataSegment, Class<T> contractType, boolean autoRegister) {
		return decode(dataSegment, contractType, autoRegister);
	}

}
