package com.jd.blockchain.consensus.service;

import com.jd.blockchain.consensus.ConsensusViewSettings;

import utils.Property;
import utils.io.Storage;
import utils.net.SSLSecurity;

import java.util.Properties;

/**
 * 共识节点服务器的工厂；
 * 
 * @author huanghaiquan
 *
 */
public interface NodeServerFactory {

	/**
	 * 构建一个共识节点的参数配置；
	 * 
	 * @param realmName          共识域的名称；
	 * @param viewSettings       共识配置；
	 * @param currentNodeAddress 共识节点的虚拟地址；必须是
	 *                           {@link ConsensusViewSettings#getNodes()} 中的一项；
	 * @return 共识节点的参数配置；
	 */
	ServerSettings buildServerSettings(String realmName, ConsensusViewSettings viewSettings, String nodeAddress);

	/**
	 * 构建一个共识节点的参数配置；
	 *
	 * @param realmName          共识域的名称；
	 * @param viewSettings       共识配置；
	 * @param nodeAddress		 共识节点的虚拟地址；必须是
	 *                           {@link ConsensusViewSettings#getNodes()} 中的一项；
	 * @param sslSecurity		 nodeAddress节点SSL通信配置
	 * @param extraProperties    差异化的额外参数
	 * @return 共识节点的参数配置；
	 */
	ServerSettings buildServerSettings(String realmName, ConsensusViewSettings viewSettings, String nodeAddress, SSLSecurity sslSecurity, Properties extraProperties);

	/**
	 * 创建一个节点服务器；
	 * 
	 * @param serverSettings         节点服务器配置；
	 * @param messageHandler         消息处理器；
	 * @param stateMachineReplicator 状态机复制器；
	 * @param runtimeStorage         共识协议公共的运行时目录；
	 * @return
	 */
	NodeServer setupServer(ServerSettings serverSettings, MessageHandle messageHandler,
			StateMachineReplicate stateMachineReplicator, Storage runtimeStorage);

}
