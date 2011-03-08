package com.cablevision.vo;

import java.io.Serializable;

import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;

public class SolrQueryVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String queryStr;
	private String orderField;
	private ORDER order;
	private boolean facet;
	private String[] facetFields;
	private String facetSort;
	private int start;
	private int rows;
	private QueryResponse queryResponse;
	private int numResults;
	
	public SolrQueryVO() {}

	public SolrQueryVO(String queryStr, String orderField, ORDER order,
			boolean facet, String[] facetFields, String facetSort, 
			int start, int rows, QueryResponse queryResponse,
			int numResults) {
		super();
		this.queryStr = queryStr;
		this.orderField = orderField;
		this.order = order;
		this.facet = facet;
		this.facetFields = facetFields;
		this.facetSort = facetSort;
		this.start = start;
		this.rows = rows;
		this.queryResponse = queryResponse;
		this.numResults = numResults;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public ORDER getOrder() {
		return order;
	}

	public void setOrder(ORDER order) {
		this.order = order;
	}

	public boolean isFacet() {
		return facet;
	}

	public void setFacet(boolean facet) {
		this.facet = facet;
	}

	public String[] getFacetFields() {
		return facetFields;
	}

	public void setFacetFields(String[] facetFields) {
		this.facetFields = facetFields;
	}

	public String getFacetSort() {
		return facetSort;
	}

	public void setFacetSort(String facetSort) {
		this.facetSort = facetSort;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public QueryResponse getQueryResponse() {
		return queryResponse;
	}

	public void setQueryResponse(QueryResponse queryResponse) {
		this.queryResponse = queryResponse;
	}

	public int getNumResults() {
		return numResults;
	}

	public void setNumResults(int numResults) {
		this.numResults = numResults;
	}
}