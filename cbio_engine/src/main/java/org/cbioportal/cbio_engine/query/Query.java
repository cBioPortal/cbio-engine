package org.cbioportal.cbio_engine.query;

import com.fasterxml.jackson.annotation.*;

/**
 * Java representation of a query object.
 * @author stuartw
 *
 */
public class Query {
	
	/**
	 * The filtering part of a query.
	 */
	@JsonProperty(required = true)
	private QueryFilter filter;
	
	/**
	 * The partitioning part of a query.
	 */
	@JsonProperty(required = false)
	private QueryPartition partition;
	
	/**
	 * The transform part of a query.
	 */
	@JsonProperty(required = true)
	private QueryTransform transform;

	/**
	 * @return the filter
	 */
	public QueryFilter getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(QueryFilter filter) {
		this.filter = filter;
	}

	/**
	 * @return the partition
	 */
	public QueryPartition getPartition() {
		return partition;
	}

	/**
	 * @param partition the partition to set
	 */
	public void setPartition(QueryPartition partition) {
		this.partition = partition;
	}

	/**
	 * @return the transform
	 */
	public QueryTransform getTransform() {
		return transform;
	}

	/**
	 * @param transform the transform to set
	 */
	public void setTransform(QueryTransform transform) {
		this.transform = transform;
	}
	
	
}
