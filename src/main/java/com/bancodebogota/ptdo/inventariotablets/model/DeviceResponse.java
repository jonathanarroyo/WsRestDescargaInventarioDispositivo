package com.bancodebogota.ptdo.inventariotablets.model;

import java.util.List;

/**
 * @author drojas6
 *
 */
public class DeviceResponse {

	private String status;
	private String message;
	private List<Device> filteredDevicesDataList;
	private String totalCount;
	private String matchedRecords;

	public DeviceResponse() {
		super();
	}

	public DeviceResponse(String status, String message, List<Device> filteredDevicesDataList, String totalCount,
			String matchedRecords) {
		super();
		this.status = status;
		this.message = message;
		this.filteredDevicesDataList = filteredDevicesDataList;
		this.totalCount = totalCount;
		this.matchedRecords = matchedRecords;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Device> getFilteredDevicesDataList() {
		return filteredDevicesDataList;
	}

	public void setFilteredDevicesDataList(List<Device> filteredDevicesDataList) {
		this.filteredDevicesDataList = filteredDevicesDataList;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getMatchedRecords() {
		return matchedRecords;
	}

	public void setMatchedRecords(String matchedRecords) {
		this.matchedRecords = matchedRecords;
	}

}
