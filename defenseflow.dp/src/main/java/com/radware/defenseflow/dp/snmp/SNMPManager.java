package com.radware.defenseflow.dp.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.radware.defenseflow.dp.MonitoredTraffic;

/**
 * 
 * @author SnirC
 * 
 */
public class SNMPManager {
	private static Logger log = LoggerFactory.getLogger(MonitoredTraffic.class);
	private Snmp snmp = null;
	private String address = null;

	/**
	 * Constructor
	 * 
	 * @param add
	 */
	public SNMPManager(String add) {
		address = add;
		try {
			start();
		} catch (Throwable e) {
			log.error("Faild to start snmp transport, "+e.getMessage());
		}
	}

	/**
	 * Start the Snmp session. If you forget the listen() method you will not
	 * get any answers because the communication is asynchronous and the
	 * listen() method listens for answers.
	 * 
	 * @throws IOException
	 */
	private void start() throws Throwable {
		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		// Do not forget this line!
		transport.listen();
	}

	/**
	 * Method which takes a single OID and returns the response from the agent
	 * as a String.
	 * 
	 * @param oid
	 * @return
	 * @throws IOException
	 */
	public String getAsString(DPOID oid) throws Throwable {
		ResponseEvent event = getNext(oid.getOID());
		return event.getResponse().get(0).getVariable().toString();
	}
	
	public String getAsString(DPOID oid, int index) throws Throwable {
		ResponseEvent event = getNext(oid.getNextOID(index));
		return event.getResponse().get(0).getVariable().toString();
	}

	public Collection<String> getAsTable(DPOID oid) throws Throwable{
		String value = "$$$";
		ArrayList<String> values = new ArrayList<String>();
		int i = 0;
		do{
			i++;
			value = getAsString(oid,+i);
			if (!value.equals(oid.getEndIndex()))
				values.add(value);
			
		}while (oid.getEndIndex() != null && !value.equals(oid.getEndIndex()));
			
		
		return values;
	}
	
	public String getNextAsString(DPOID oid) throws IOException {
		ResponseEvent event = getNext(oid.getOID());
		return event.getResponse().get(0).getVariable().toString();
	}
	
	/**
	 * This method is capable of handling multiple OIDs
	 * 
	 * @param oids
	 * @return
	 * @throws IOException
	 */
	public ResponseEvent get(OID oids[]) throws IOException {
		PDU pdu = new PDU();
		for (OID oid : oids) {
			pdu.add(new VariableBinding(oid));
		}
		pdu.setType(PDU.GET);
		ResponseEvent event = snmp.send(pdu, getTarget(), null);
		if (event != null) {
			return event;
		}
		throw new RuntimeException("GET timed out");
	}

	
	public ResponseEvent getNext(OID oids[]) throws IOException {
	    TransportMapping transport = new DefaultUdpTransportMapping();
	    transport.listen();
		PDU pdu = new PDU();
		for (OID oid : oids) {
			pdu.add(new VariableBinding(oid));
		}
		pdu.setType(PDU.GETNEXT);

		ResponseEvent response = snmp.getNext(pdu, getTarget());
		
		return response;


	}

	/**
	 * This method returns a Target, which contains information about where the
	 * data should be fetched and how.
	 * 
	 * @return
	 */
	private Target getTarget() {
		Address targetAddress = GenericAddress.parse(address);
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		return target;
	}

}
