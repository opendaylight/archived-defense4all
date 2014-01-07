package com.radware.defenseflow.dp.snmp;

import org.snmp4j.smi.OID;

public enum DPOID {
	RSWSD_RESOURCE_UTILIZATION (".1.3.6.1.4.1.89.35.1.53"),
	SYSTEM_FANS_STATUS(".1.3.6.1.4.1.89.35.1.162.1.2", "0"),
	SYSTEM_DESCRIPTION(".1.3.6.1.2.1.1.1"),
	SYSTEM_NAME(".1.3.6.1.2.1.1.1"),	
	RDWR_TEMPERTURE_CPU_1(".1.3.6.1.4.1.89.35.1.150"),
	RDWR_TEMPERTURE_SHUTDOWN_THRESHOLD(".1.3.6.1.4.1.89.35.1.153"),
	RDWR_TEMPERTURE_WARNING_THRESHOLD(".1.3.6.1.4.1.89.35.1.152");
		
	private String oidStr;	
	private String endIndex = null;	
	
	private DPOID(String oidValue){
		this.oidStr = oidValue;
	}
	
	private DPOID(String oidValue, String endIndex){
		this.oidStr = oidValue;
		this.endIndex = endIndex;
	}
	public String getEndIndex(){
		return this.endIndex;
	}
	public OID[] getOID(){
		OID oid = new OID(oidStr);
		return new OID[] { oid };
	}
	public OID[] getNextOID(int index){
		OID oid = new OID(oidStr+ "." + index);
		return new OID[] { oid };
	}
}

