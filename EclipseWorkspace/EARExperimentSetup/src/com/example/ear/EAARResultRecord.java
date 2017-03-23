package com.example.ear;

public class EAARResultRecord {
	private final long timestamp;
	private final String name;
	private final Double anomalyScore;
	private final Double anomalyThreshold;

	public EAARResultRecord(final long timestamp, final String name, final Double anomalyScore, final Double anomalyThreshold) {
		this.timestamp = timestamp;
		this.name = name;
		this.anomalyScore = anomalyScore;
		this.anomalyThreshold = anomalyThreshold;
	}
	
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the anomalyScore
	 */
	public Double getAnomalyScore() {
		return anomalyScore;
	}

	/**
	 * @return the anomalyThreshold
	 */
	public Double getAnomalyThreshold() {
		return anomalyThreshold;
	}
	
	public String getKey() {
		return this.timestamp + ";" + this.name + ";" + this.anomalyScore;
	}
	
	public String toString() {
		return this.timestamp + ";" + this.name + ";" + this.anomalyScore + ";" + this.anomalyThreshold;
	}

}
