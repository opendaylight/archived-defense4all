package org.opendaylight.defense4all.odl.pojos;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;

public class FlowStatisticsTest {

	
	private static String json = 
	"{\"node\":{\"id\":\"00:00:00:50:56:a3:1c:0b\",\"type\":\"OF\"},\"flowStatistic\":[{\"flow\":{\"match\":{\"matchField\":[{\"value\":\"2048\",\"type\":\"DL_TYPE\"},{\"value\":\"OF|1@OF|00:00:00:50:56:a3:1c:0b\",\"type\":\"IN_PORT\"},{\"value\":\"0\",\"type\":\"DL_VLAN_PR\"},{\"mask\":\"255.255.255.255\",\"value\":\"101.2.1.202\",\"type\":\"NW_DST\"},{\"value\":\"6\",\"type\":\"NW_PROTO\"}]},\"actions\":[{\"type\":\"OUTPUT\",\"port\":{\"node\":{\"id\":\"00:00:00:50:56:a3:1c:0b\",\"type\":\"OF\"},\"id\":\"3\",\"type\":\"OF\"}}],\"priority\":34,\"idleTimeout\":0,\"hardTimeout\":0,\"id\":60755},\"tableId\":0,\"durationSeconds\":159,\"durationNanoseconds\":835000000,\"packetCount\":0,\"byteCount\":0},{\"flow\":{\"match\":{\"matchField\":[{\"value\":\"2048\",\"type\":\"DL_TYPE\"},{\"value\":\"OF|1@OF|00:00:00:50:56:a3:1c:0b\",\"type\":\"IN_PORT\"},{\"value\":\"0\",\"type\":\"DL_VLAN_PR\"},{\"mask\":\"255.255.255.255\",\"value\":\"101.2.1.202\",\"type\":\"NW_DST\"},{\"value\":\"17\",\"type\":\"NW_PROTO\"}]},\"actions\":[{\"type\":\"OUTPUT\",\"port\":{\"node\":{\"id\":\"00:00:00:50:56:a3:1c:0b\",\"type\":\"OF\"},\"id\":\"3\",\"type\":\"OF\"}}],\"priority\":33,\"idleTimeout\":0,\"hardTimeout\":0,\"id\":48896},\"tableId\":0,\"durationSeconds\":159,\"durationNanoseconds\":820000000,\"packetCount\":0,\"byteCount\":0},{\"flow\":{\"match\":{\"matchField\":[{\"value\":\"2048\",\"type\":\"DL_TYPE\"},{\"value\":\"OF|1@OF|00:00:00:50:56:a3:1c:0b\",\"type\":\"IN_PORT\"},{\"value\":\"0\",\"type\":\"DL_VLAN_PR\"},{\"mask\":\"255.255.255.255\",\"value\":\"101.2.1.202\",\"type\":\"NW_DST\"}]},\"actions\":[{\"type\":\"OUTPUT\",\"port\":{\"node\":{\"id\":\"00:00:00:50:56:a3:1c:0b\",\"type\":\"OF\"},\"id\":\"3\",\"type\":\"OF\"}}],\"priority\":31,\"idleTimeout\":0,\"hardTimeout\":0,\"id\":11703},\"tableId\":0,\"durationSeconds\":159,\"durationNanoseconds\":794000000,\"packetCount\":0,\"byteCount\":0},{\"flow\":{\"match\":{\"matchField\":[{\"value\":\"2048\",\"type\":\"DL_TYPE\"},{\"value\":\"OF|1@OF|00:00:00:50:56:a3:1c:0b\",\"type\":\"IN_PORT\"},{\"value\":\"0\",\"type\":\"DL_VLAN_PR\"},{\"mask\":\"255.255.255.255\",\"value\":\"101.2.1.202\",\"type\":\"NW_DST\"},{\"value\":\"1\",\"type\":\"NW_PROTO\"}]},\"actions\":[{\"type\":\"OUTPUT\",\"port\":{\"node\":{\"id\":\"00:00:00:50:56:a3:1c:0b\",\"type\":\"OF\"},\"id\":\"3\",\"type\":\"OF\"}}],\"priority\":32,\"idleTimeout\":0,\"hardTimeout\":0,\"id\":51528},\"tableId\":0,\"durationSeconds\":159,\"durationNanoseconds\":807000000,\"packetCount\":0,\"byteCount\":0},{\"flow\":{\"match\":{\"matchField\":[{\"value\":\"2054\",\"type\":\"DL_TYPE\"},{\"value\":\"OF|6@OF|00:00:00:50:56:a3:1c:0b\",\"type\":\"IN_PORT\"},{\"value\":\"0\",\"type\":\"DL_VLAN_PR\"}]},\"actions\":[{\"type\":\"DROP\"}],\"priority\":13,\"idleTimeout\":0,\"hardTimeout\":0,\"id\":48092},\"tableId\":0,\"durationSeconds\":540,\"durationNanoseconds\":570000000,\"packetCount\":0,\"byteCount\":0},{\"flow\":{\"match\":{\"matchField\":[{\"value\":\"2054\",\"type\":\"DL_TYPE\"},{\"value\":\"OF|5@OF|00:00:00:50:56:a3:1c:0b\",\"type\":\"IN_PORT\"},{\"value\":\"0\",\"type\":\"DL_VLAN_PR\"}]},\"actions\":[{\"type\":\"DROP\"}],\"priority\":12,\"idleTimeout\":0,\"hardTimeout\":0,\"id\":55392},\"tableId\":0,\"durationSeconds\":540,\"durationNanoseconds\":585000000,\"packetCount\":0,\"byteCount\":0}]}";
	
	@Test
	public void jsonReadTest() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignore unknownfields
		TypeReference<?> typeRef = new TypeReference<FlowStatistics>(){};		
		FlowStatistics flowStatistics = objectMapper.readValue(json, typeRef);
		System.out.println(flowStatistics);
	}


}
