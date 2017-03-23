package com.example.ear;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class EARBasic {
	private static final List<EAARResultRecord> collectedResults = new ArrayList<>();

	private final double anomalyThreshold;

	private double anomalyToleranceFactor = 2.0;

	private long anomalyToleranceDurationMS = 15000;

	private final String name;
	private final TreeSet<Long> eventTimestamps;
	private final TreeSet<Long> measurementTimestamps;
	private final TreeMap<Long, Double> anomalyScores;
	private final TreeMap<Long, Double> scoreLimits = new TreeMap<Long, Double>();

	public EARBasic(final String name, TreeSet<Long> eventTimestamps, TreeMap<Long, Double> anomalyScores,
			final double anomalyThreshold) throws FileNotFoundException, UnsupportedEncodingException {
		this.name = name;
		this.eventTimestamps = eventTimestamps;
		this.anomalyScores = anomalyScores;
		this.measurementTimestamps = new TreeSet<>();
		this.anomalyThreshold = anomalyThreshold;
		measurementTimestamps.addAll(anomalyScores.keySet());
	}

	public void compileInput() {
		// acquire timestamps
		for (long ts : measurementTimestamps) {
			this.scoreLimits.put(ts, anomalyThreshold);
		}

		// apply event influence to limits
		for (long ts : eventTimestamps) {
			applyChangeEvent(ts);
		}

	}

	public static List<EAARResultRecord> getResults() {
		return collectedResults;
	}

	private void applyChangeEvent(final long timestamp) {
		final long start = timestamp;
		final long end = timestamp + TimeUnit.NANOSECONDS.convert(anomalyToleranceDurationMS, TimeUnit.MILLISECONDS);
		double limit = anomalyThreshold * anomalyToleranceFactor;
		
		// 1.0 is the maximum anomaly score value
		if (limit > 1.0) {
			limit = 1.0;
		}

		SortedMap<Long, Double> affectedEntries = this.scoreLimits.tailMap(start).headMap(end);
		for (Entry<Long, Double> entry : affectedEntries.entrySet()) {
			if (entry.getValue() < limit) {
				entry.setValue(limit);
			}
		}
	}

	public void applyLimits() {
		for (long ts : measurementTimestamps) {
			Double anomalyScore = this.anomalyScores.get(ts);
			Double threshold = this.scoreLimits.get(ts);
			final EAARResultRecord record = new EAARResultRecord(ts, name, anomalyScore, threshold);
			collectedResults.add(record);
			//System.out.println(record);
		}
	}
}
