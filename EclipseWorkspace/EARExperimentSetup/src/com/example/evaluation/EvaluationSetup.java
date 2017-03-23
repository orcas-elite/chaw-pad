package com.example.evaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import com.example.ear.EARBasic;
import com.example.ear.EAARResultRecord;
import com.example.ear.model.AnomalyScope;

import kieker.analysis.AnalysisController;
import kieker.analysis.IAnalysisController;
import kieker.analysis.exception.AnalysisConfigurationException;
import kieker.analysis.plugin.filter.forward.ListCollectionFilter;
import kieker.analysis.plugin.filter.forward.TeeFilter;
import kieker.analysis.plugin.reader.filesystem.FSReader;
import kieker.analysis.repository.AbstractRepository;
import kieker.common.configuration.Configuration;
import kieker.common.logging.Log;
import kieker.common.logging.LogFactory;
import kieker.tools.dataexchange.AnomalyRecord;
import kieker.tools.opad.filter.AnomalyDetectionFilter;
import kieker.tools.opad.filter.AnomalyScoreCalculationFilter;
import kieker.tools.opad.filter.ExtractionFilter;
import kieker.tools.opad.filter.ForecastingFilter;
import kieker.tools.opad.filter.NamedDoubleTimeSeriesPointSorterFilter;
import kieker.tools.opad.filter.TimeSeriesPointAggregatorFilter;
import kieker.tools.opad.record.ExtendedStorableDetectionResult;
import kieker.tools.opad.timeseries.ForecastMethod;
import kieker.tools.rancorr.MessageTraceDependencyExtractor;
import kieker.tools.rancorr.util.DependencyItem;
import kieker.tools.rancorr.util.DependencyPath;
import kieker.tools.rancorr.util.RanCorrAnomalyDetectionResult;
import kieker.tools.traceAnalysis.filter.executionRecordTransformation.ExecutionRecordTransformationFilter;
import kieker.tools.traceAnalysis.filter.traceReconstruction.TraceReconstructionFilter;
import kieker.tools.traceAnalysis.systemModel.repository.SystemModelRepository;

public class EvaluationSetup {
	private static final Log LOG = LogFactory.getLog(EvaluationSetup.class);

	public static void main(String[] args) throws IllegalStateException, AnalysisConfigurationException, IOException {
		
		// Base path where the output should be stored.
		final String path = "/tmp/experiment/";

		new File(path).mkdir();
		final String currentValue = "0.95";
		final String currentPath = path + currentValue + "/";
		new File(currentPath).mkdir();

		try {
			runEvaluation(Double.valueOf(currentValue), currentPath);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void runEvaluation(final double threshold, final String path)
			throws IllegalStateException, AnalysisConfigurationException, IOException {
		System.out.println("Threshold: " + threshold);
		System.out.println("Path:" + path);

		IAnalysisController controller = new AnalysisController();

		final AbstractRepository systemModelRepository = new SystemModelRepository(new Configuration(), controller);

		final Configuration fsReaderConfig = new Configuration();
		
		// Path of the monitoring data that is used for the analysis
		fsReaderConfig.setProperty(FSReader.CONFIG_PROPERTY_NAME_INPUTDIRS,
				"kieker-20161031-193642988-UTC-monitoringserver-p1krn-KIEKER");
		final FSReader fsReader = new FSReader(fsReaderConfig, controller);

		final ExtractionFilter extractionFilter = new ExtractionFilter(new Configuration(), controller);

		final NamedDoubleTimeSeriesPointSorterFilter sorterFilter = new NamedDoubleTimeSeriesPointSorterFilter(
				new Configuration(), controller);

		final Configuration tsafConfig = new Configuration();
		tsafConfig.setProperty(TimeSeriesPointAggregatorFilter.CONFIG_PROPERTY_NAME_AGGREGATION_SPAN, "5000");
		tsafConfig.setProperty(TimeSeriesPointAggregatorFilter.CONFIG_PROPERTY_NAME_AGGREGATION_TIMEUNIT,
				"MILLISECONDS");
		final TimeSeriesPointAggregatorFilter timeSeriesAggregatorFilter = new TimeSeriesPointAggregatorFilter(
				tsafConfig, controller);

		final Configuration ffConfig = new Configuration();
		ffConfig.setProperty(ForecastingFilter.CONFIG_PROPERTY_NAME_FC_METHOD, ForecastMethod.MEANJAVA.name());
		final ForecastingFilter forecastingFilter = new ForecastingFilter(ffConfig, controller);

		final AnomalyScoreCalculationFilter anomalyScoreCalculationFilter = new AnomalyScoreCalculationFilter(
				new Configuration(), controller);

		final Configuration anomalyDetectionFilterConfiguration = new Configuration();
		anomalyDetectionFilterConfiguration.setProperty(AnomalyDetectionFilter.CONFIG_PROPERTY_NAME_THRESHOLD,
				String.valueOf(threshold));
		final AnomalyDetectionFilter anomalyDetectionFilter = new AnomalyDetectionFilter(
				anomalyDetectionFilterConfiguration, controller);

		final ExecutionRecordTransformationFilter executionRecordTransformationFilter = new ExecutionRecordTransformationFilter(
				new Configuration(), controller);
		controller.connect(executionRecordTransformationFilter,
				ExecutionRecordTransformationFilter.REPOSITORY_PORT_NAME_SYSTEM_MODEL, systemModelRepository);

		final Configuration trfConfig = new Configuration();
		trfConfig.setProperty(TraceReconstructionFilter.CONFIG_PROPERTY_NAME_MAX_TRACE_DURATION, "5");
		trfConfig.setProperty(TraceReconstructionFilter.CONFIG_PROPERTY_NAME_TIMEUNIT, "SECONDS");
		final TraceReconstructionFilter traceReconstructionFilter = new TraceReconstructionFilter(trfConfig,
				controller);
		controller.connect(traceReconstructionFilter, TraceReconstructionFilter.REPOSITORY_PORT_NAME_SYSTEM_MODEL,
				systemModelRepository);

		controller.connect(executionRecordTransformationFilter,
				ExecutionRecordTransformationFilter.OUTPUT_PORT_NAME_EXECUTIONS, traceReconstructionFilter,
				TraceReconstructionFilter.INPUT_PORT_NAME_EXECUTIONS);

		final Configuration opadOutputLoggerConfig = new Configuration();
		opadOutputLoggerConfig.setProperty(TeeFilter.CONFIG_PROPERTY_NAME_STREAM, path + "0-opad.log");
		opadOutputLoggerConfig.setProperty(TeeFilter.CONFIG_PROPERTY_NAME_APPEND, "false");
		final TeeFilter opadOutputLogger = new TeeFilter(opadOutputLoggerConfig, controller);

		/**
		final MessageTraceDependencyExtractor mtde = new MessageTraceDependencyExtractor(new Configuration(),
				controller);

		final ListCollectionFilter<ExtendedStorableDetectionResult> detectionResults = new ListCollectionFilter<>(
				new Configuration(), controller);

		// System Discovery

		// FSReader --> ExecutionRecordTransformationFilter
		controller.connect(fsReader, FSReader.OUTPUT_PORT_NAME_RECORDS, executionRecordTransformationFilter,
				ExecutionRecordTransformationFilter.INPUT_PORT_NAME_RECORDS);

		// ExecutionRecordTransformationFilter --> TraceReconstructionFilter
		controller.connect(executionRecordTransformationFilter,
				ExecutionRecordTransformationFilter.OUTPUT_PORT_NAME_EXECUTIONS, traceReconstructionFilter,
				TraceReconstructionFilter.INPUT_PORT_NAME_EXECUTIONS);

		controller.connect(traceReconstructionFilter, TraceReconstructionFilter.OUTPUT_PORT_NAME_MESSAGE_TRACE, mtde,
				MessageTraceDependencyExtractor.INPUT_PORT_NAME_MESSAGE_TRACES);

		// OPAD(x)

		controller.connect(mtde, MessageTraceDependencyExtractor.OUTPUT_PORT_NAME_NAMED_DOUBLE_RECORDS,
				extractionFilter, ExtractionFilter.INPUT_PORT_NAME_VALUE);

		controller.connect(extractionFilter, ExtractionFilter.OUTPUT_PORT_NAME_VALUE, sorterFilter,
				NamedDoubleTimeSeriesPointSorterFilter.INPUT_PORT_NAME_VALUE);

		controller.connect(sorterFilter, NamedDoubleTimeSeriesPointSorterFilter.OUTPUT_PORT_NAME_VALUE,
				timeSeriesAggregatorFilter, TimeSeriesPointAggregatorFilter.INPUT_PORT_NAME_TSPOINT);

		// AggregationFilter --> ForecastingFilter
		controller.connect(timeSeriesAggregatorFilter,
				TimeSeriesPointAggregatorFilter.OUTPUT_PORT_NAME_AGGREGATED_TSPOINT, forecastingFilter,
				ForecastingFilter.INPUT_PORT_NAME_TSPOINT);

		// ForecastingFilter --> AnomalyScoreCalculator
		controller.connect(forecastingFilter, ForecastingFilter.OUTPUT_PORT_NAME_FORECASTED_AND_MEASURED,
				anomalyScoreCalculationFilter, AnomalyScoreCalculationFilter.INPUT_PORT_CURRENT_FORECAST_PAIR);

		// AnomalyScoreCalculator --> AnomalyDetectionFilter
		controller.connect(anomalyScoreCalculationFilter, AnomalyScoreCalculationFilter.OUTPUT_PORT_ANOMALY_SCORE,
				anomalyDetectionFilter, AnomalyDetectionFilter.INPUT_PORT_ANOMALY_SCORE);

		// LOG opad output
		controller.connect(anomalyDetectionFilter, AnomalyDetectionFilter.OUTPUT_PORT_ALL, opadOutputLogger,
				TeeFilter.INPUT_PORT_NAME_EVENTS);

		controller.connect(anomalyDetectionFilter, AnomalyDetectionFilter.OUTPUT_PORT_ALL, detectionResults,
				ListCollectionFilter.INPUT_PORT_NAME);

		controller.run();

		controller.terminate();

		// Read the results of OPAD and store them in a list and write them as a csv file
		List<ExtendedStorableDetectionResult> resultingList = detectionResults.getList();

		// Remap the anomaly scores of the OPAD results
		PrintWriter opadRemappingsWriter = new PrintWriter(path + "0-opad-remapped.log", "UTF-8");
		for (final ExtendedStorableDetectionResult sdr : resultingList) {
			final double currentAnomalyValue = sdr.getScore();
			final Double newAnomalyValue = mapToPropabilityRange(rescaleAnomalyScore(currentAnomalyValue));
			ExtendedStorableDetectionResult newSdr = new ExtendedStorableDetectionResult(sdr.getApplication(),
					sdr.getValue(), sdr.getTimestamp(), sdr.getForecast(), newAnomalyValue, sdr.getAnomalyThreshold());
			opadRemappingsWriter.println(newSdr.toString());
		}
		opadRemappingsWriter.close();

		assignAnomalyValues(resultingList);

		for (List<DependencyItem> depItemList : DependencyPath.getDependencyItems()) {

			// Sort anomaly records
			for (DependencyItem depItem : depItemList) {
				Collections.sort(depItem.getAnomalyRecords());
			}

			// Acquire the first item in a dependency path
			final DependencyItem startItem;
			if (!depItemList.isEmpty()) {
				startItem = getFirst(depItemList.get(0));
			} else {
				continue;
			}

			calculateRanCorrScores(startItem);

		}

		HashMap<String, List<RanCorrAnomalyDetectionResult>> completeResults = new HashMap<>();
		PrintWriter writer = new PrintWriter(path + "0-rancorr.log", "UTF-8");
		for (RanCorrAnomalyDetectionResult rcad : RanCorrAnomalyDetectionResult.getDetectionResults()) {
			List<RanCorrAnomalyDetectionResult> rancorrResults = completeResults.getOrDefault(rcad.getName(),
					new ArrayList<RanCorrAnomalyDetectionResult>());

			rancorrResults.add(rcad);
			completeResults.putIfAbsent(rcad.getName(), rancorrResults);

			writer.println(rcad.getTimestamp() + ";" + rcad.getName() + ";" + rcad.getAnomalyScore());
		}
		writer.close();

		// File that contains the timestamps of the events that are known
		HashMap<AnomalyScope, TreeSet<Long>> events = readUpdateEvents(
				"events.log");

		for (String identifier : completeResults.keySet()) {
			final TreeMap<Long, Double> anomalyEntries = new TreeMap<>();
			for (AnomalyScope as : events.keySet()) {
				if (as.isApplicableFor(identifier)) {
					for (RanCorrAnomalyDetectionResult rcad : completeResults.get(identifier)) {
						anomalyEntries.put(rcad.getTimestamp(), rcad.getAnomalyScore());
					}
				}

				// Apply the EAR approach
				EARBasic eaar = new EARBasic(identifier, events.get(as), anomalyEntries, threshold);
				eaar.compileInput();
				eaar.applyLimits();
			}

		}

		// Collect the results and remove duplicates
		HashMap<String, EAARResultRecord> resultCollection = new HashMap<>();
		for (EAARResultRecord rr : EARBasic.getResults()) {
			final String key = rr.getKey();
			if (resultCollection.containsKey(key)) {
				EAARResultRecord existing = resultCollection.get(key);
				if (rr.getAnomalyScore() > existing.getAnomalyThreshold()) {
					resultCollection.put(key, rr);
				}
			} else {
				resultCollection.put(key, rr);
			}
		}

		// Write the results of the EAR approach
		writer = new PrintWriter(path + "0-eaar.log", "UTF-8");
		for (EAARResultRecord rr : resultCollection.values()) {
			writer.println(rr.toString());
		}
		writer.close();
		**/
	}

	private static HashMap<AnomalyScope, TreeSet<Long>> readUpdateEvents(String csvFile) throws IOException {
		final HashMap<AnomalyScope, TreeSet<Long>> result = new HashMap<>();
		String line = "";
		final String cvsSplitChar = ";";
		BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
		while ((line = bufferedReader.readLine()) != null) {
			String[] columns = line.split(cvsSplitChar);
			final long timestampNanos = TimeUnit.NANOSECONDS.convert(Long.valueOf(columns[0]), TimeUnit.MILLISECONDS);
			final AnomalyScope as = AnomalyScope.anomalyScopeFactory(columns[1]);

			TreeSet<Long> timestampSet = result.get(as);
			if (null == timestampSet) {
				timestampSet = new TreeSet<Long>();
			}
			timestampSet.add(timestampNanos);

			result.put(as, timestampSet);
		}
		bufferedReader.close();
		return result;
	}

	/**
	 * Iterates to the first item of the DependencyPath
	 * 
	 * @param depItem
	 * @return
	 */
	private static DependencyItem getFirst(final DependencyItem depItem) {
		if (depItem == null) {
			return null;
		}

		DependencyItem current = depItem;

		while (current.getIncoming() != null) {
			current = current.getIncoming();
		}

		return current;
	}

	/**
	 * Assigns the anomaly scores to the dependency information items
	 * @param resultList List of anomaly detection results from OPAD
	 */
	private static void assignAnomalyValues(List<ExtendedStorableDetectionResult> resultList) {
		for (ExtendedStorableDetectionResult esdr : resultList) {
			final String applicationString = esdr.getApplication();
			final int pos = applicationString.indexOf(':');
			final Long id = Long.valueOf(applicationString.substring(0, pos));

			if (null != id) {
				final List<DependencyItem> diList = DependencyPath.getExecutionPath(id);
				if (null != diList) {
					for (final DependencyItem di : diList) {
						if (di.getIdentifier().equals(applicationString)) {
							final List<AnomalyRecord> anomalyRecordList = di.getAnomalyRecords();

							final AnomalyRecord anomalyRecord = esdr.getAnomalyRecord();
							final double rescaledAnomalyScore = rescaleAnomalyScore(anomalyRecord.getAnomalyScore());
							anomalyRecord.setAnomalyScore(rescaledAnomalyScore);
							anomalyRecordList.add(anomalyRecord);
							di.setAnomalyRecords(anomalyRecordList);
							break;
						}
					}
				}
			}
		}
	}

	private static void calculateRanCorrScores(final DependencyItem depItem) {
		for (AnomalyRecord anomalyRecord : depItem.getAnomalyRecords()) {
			getForwardAnomalyScore(anomalyRecord.getTimestamp(), 5000, depItem);
		}
	}

	
	//Implements the basic forward dependency anomaly calculation based on the idea of the RanCorr approach
	private static Double getForwardAnomalyScore(final long timestamp, final long varianceMilliseconds,
			final DependencyItem depItem) {
		Double result = null;

		if (null != depItem) {
			final AnomalyRecord currentAnomalyRecord = depItem.getAnomalyRecordFor(timestamp, varianceMilliseconds);

			Double localAnomalyScore;
			long newTimestamp;
			if (null != currentAnomalyRecord) {
				newTimestamp = currentAnomalyRecord.getTimestamp();
				localAnomalyScore = currentAnomalyRecord.getAnomalyScore();
			} else {
				newTimestamp = timestamp;
				localAnomalyScore = null;
			}
			final Double forwardAnomalyScore = getForwardAnomalyScore(newTimestamp, varianceMilliseconds,
					depItem.getOutgoing());

			if (null != forwardAnomalyScore && null != localAnomalyScore && forwardAnomalyScore >= localAnomalyScore) {
				result = 0.5 * (localAnomalyScore - 1);
			} else if (null == forwardAnomalyScore) {
				result = localAnomalyScore;
			} else {
				result = Double.max(localAnomalyScore, forwardAnomalyScore);
			}

			RanCorrAnomalyDetectionResult.addDetectionResult(newTimestamp, depItem.getIdentifier(),
					mapToPropabilityRange(result));
		}
		return result;
	}

	private static double rescaleAnomalyScore(final Double anomalyScore) {
		double initialValue = 0.0;

		if (!Double.isNaN(anomalyScore)) {
			initialValue = anomalyScore;
		}
		final double returnValue = (initialValue * 2) - 1;
		LOG.debug("Rescaled anomaly score of " + anomalyScore + " to " + returnValue);
		return returnValue;
	}

	/**
	 * Maps the anomaly ranking range (-1.0 to +1.0) to a probability range (0
	 * to +1.0).
	 *
	 * @param anomalyRanking
	 *            - Double value which should be in range between -1.0 and +1.0
	 * @return mapped double value, null if parameter was in wrong range
	 */
	private static Double mapToPropabilityRange(final Double anomalyRanking) {
		if ((anomalyRanking == null) || (anomalyRanking < -1.0) || (anomalyRanking > 1.0)) {
			return null;
		}
		return (anomalyRanking + 1.0) / 2.0;
	}
}
