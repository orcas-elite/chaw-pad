package com.example.evaluation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class EvaluationCalculator {

	private static String basePath = "/home/duelle/repositories/git/thesis-document/Thesis/dataset/third/experiment/";
	private static List<Anomaly> anomalies = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		// Define the known real anomalies
		anomalies.add(new Anomaly("portal", "getIndex", 1483213067545000000L, 1483213097545000000L));
		anomalies.add(new Anomaly("logic", "", 1483213207576000000L, 1483213232576000000L));
		anomalies.add(new Anomaly("portal", "getOrder", 1483213327595000000L, 1483213357595000000L));
		anomalies.add(new Anomaly("database", "", 1483213467603000000L, 1483213497603000000L));

		String[] thresholds = { "0.1", "0.15", "0.2", "0.25", "0.3", "0.35", "0.4", "0.45", "0.5", "0.55", "0.6",
				"0.65", "0.7", "0.75", "0.8", "0.85", "0.9", "0.95" };
		String[] types = { "opad-remapped", "rancorr", "eaar" };

		PrintWriter writer = new PrintWriter(basePath + "results.csv", "UTF-8");
		PrintWriter rocOut = new PrintWriter(basePath + "roc.csv", "UTF-8");
		
		
		writer.println("type;threshold;TP;TN;FP;FN");
		rocOut.println("type;threshold;as;pred;an");
		for (final String th : thresholds) {
			for (String type : types) {
				final String csvFile = basePath + th + "/0-" + type + ".log";

				int tp = 0;
				int tn = 0;
				int fp = 0;
				int fn = 0;

				String line;
				final String cvsSplitChar = ";";
				String id;
				long ts;
				double as;
				double threshold;

				final BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
				while ((line = bufferedReader.readLine()) != null) {
					String[] columns = line.split(cvsSplitChar);

					if (type.equals("opad-remapped")) {
						id = columns[1];
						ts = Long.valueOf(columns[3]);
						as = Double.valueOf(columns[5]);
						threshold = Double.valueOf(columns[6]);
					} else if(type.equals("rancorr")) {
						id = columns[1];
						ts = Long.valueOf(columns[0]);
						as = Double.valueOf(columns[2]);
						threshold = Double.valueOf(th);
					} else  {
						id = columns[1];
						ts = Long.valueOf(columns[0]);
						as = Double.valueOf(columns[2]);
						threshold = Double.valueOf(columns[3]);
					}

					final Item it = new Item(id, ts, (as > threshold));
					final boolean isReal = isRealAnomaly(it);
					
					rocOut.println(type + ";" + th + ";" + as + ";" + it.getAn() + ";" + isReal);
					
					if (isReal) {
						if (it.getAn()) {
							tp++;
						} else {
							fn++;
						}
					} else {
						if (it.getAn()) {
							fp++;
						} else {
							tn++;
						}
					}
				}
				writer.println(type + ";" + th + ";" + tp + ";" + tn + ";" + fp + ";" + fn);
			}
		}
		writer.close();
		rocOut.close();
	}

	public static class Item {
		private String id;
		private long ts;
		private boolean an;

		public Item(final String id, final long ts, final boolean an) {
			this.id = id;
			this.ts = ts;
			this.an = an;
		}

		public String getId() {
			return id;
		}

		public long getTs() {
			return ts;
		}

		public boolean getAn() {
			return an;
		}
	}

	public static class Anomaly {
		private String type;

		public String getType() {
			return type;
		}

		public String getMethod() {
			return method;
		}

		public long getStart() {
			return start;
		}

		public long getEnd() {
			return end;
		}

		private String method;
		private long start;
		private long end;

		public Anomaly(String type, String method, long start, long end) {
			this.type = type;
			this.method = method;
			this.start = start;
			this.end = end;
		}

	}

	private static boolean isRealAnomaly(Item item) {
		for (Anomaly a : anomalies) {
			if (item.id.contains(a.type) && item.id.contains(a.method)) {
				if (a.start < item.getTs() && item.getTs() > a.end) {
					return true;
				}
			}
		}
		return false;
	}
}
