package com.bancodebogota.ptdo.inventariotablets.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author drojas6
 *
 */
public class Device {

	@Id
	public ObjectId _id;

	private String id;
	private String userName;
	private String costCenter;
	private String serialNumber;
	@Indexed(unique = true)
	private String imeiOrMeid;
	private String wifiMacAddress;
	private String osVersion;
	private String deviceModel;
	private Date lastUpdateDate;

	public Device() {
		super();
	}

	public Device(String id, String userName, String costCenter, String serialNumber, String imeiOrMeid,
			String wifiMacAddress, String osVersion, String deviceModel, Date lastUpdateDate) {
		super();
		this.id = id;
		this.userName = userName;
		this.costCenter = costCenter;
		this.serialNumber = serialNumber;
		this.imeiOrMeid = imeiOrMeid;
		this.wifiMacAddress = wifiMacAddress;
		this.osVersion = osVersion;
		this.deviceModel = deviceModel;
		this.lastUpdateDate = lastUpdateDate;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getImeiOrMeid() {
		return imeiOrMeid;
	}

	public void setImeiOrMeid(String imeiOrMeid) {
		this.imeiOrMeid = imeiOrMeid;
	}

	public String getWifiMacAddress() {
		return wifiMacAddress;
	}

	public void setWifiMacAddress(String wifiMacAddress) {
		this.wifiMacAddress = wifiMacAddress;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
