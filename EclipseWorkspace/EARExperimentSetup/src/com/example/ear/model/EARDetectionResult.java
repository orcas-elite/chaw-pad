package com.example.ear.model;

public class EARDetectionResult {
	private final long timestamp;
	private final Double anomalyScore;
	private final double anomalyThreshold;
	private final String name;
	
	public EARDetectionResult(final long timestamp, final Double anomalyScore, final double anomalyThreshold, final String name) {
		this.timestamp = timestamp;
		this.anomalyScore = anomalyScore;
		this.anomalyThreshold = anomalyThreshold;
		this.name = name;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
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
	public double getAnomalyThreshold() {
		return anomalyThreshold;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.timestamp);
		sb.append(";");
		sb.append(this.name);
		sb.append(";");
		sb.append(this.anomalyScore);
		sb.append(";");
		sb.append(this.anomalyThreshold);
		return sb.toString();
	}
}
