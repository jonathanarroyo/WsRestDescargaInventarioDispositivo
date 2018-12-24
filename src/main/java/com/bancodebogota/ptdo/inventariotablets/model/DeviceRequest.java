package com.bancodebogota.ptdo.inventariotablets.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author drojas6
 *
 */
@JsonInclude(Include.NON_NULL)
public class DeviceRequest {
	// @JsonInclude(Include.NON_NULL)
	private String start;
	// @JsonInclude(Include.NON_NULL)
	private String limit;
	// @JsonInclude(Include.NON_NULL)
	private String sortOrder;
	// @JsonInclude(Include.NON_NULL)
	private String sortColumn;
	// @JsonInclude(Include.NON_NULL)
	private String search;
	// @JsonInclude(Include.NON_NULL)
	private String enableCount;
	// @JsonInclude(Include.NON_NULL)
	private String filterIds;

	public DeviceRequest() {
		super();
	}

	public DeviceRequest(String start, String limit, String sortOrder, String sortColumn, String search,
			String enableCount, String filterIds) {
		super();
		this.start = start;
		this.limit = limit;
		this.sortOrder = sortOrder;
		this.sortColumn = sortColumn;
		this.search = search;
		this.enableCount = enableCount;
		this.filterIds = filterIds;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getEnableCount() {
		return enableCount;
	}

	public void setEnableCount(String enableCount) {
		this.enableCount = enableCount;
	}

	public String getFilterIds() {
		return filterIds;
	}

	public void setFilterIds(String filterIds) {
		this.filterIds = filterIds;
	}

}
