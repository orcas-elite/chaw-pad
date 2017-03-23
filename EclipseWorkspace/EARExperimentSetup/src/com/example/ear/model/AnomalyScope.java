package com.example.ear.model;

public class AnomalyScope {
	private final String hostname;
	private final String method;

	public AnomalyScope(final String hostname, final String method) {
		this.hostname = hostname;
		this.method = method;
	}

	public static AnomalyScope anomalyScopeFactory(String hostInstanceMethod) {
		String hostname = "";
		String method = null;
		if (hostInstanceMethod.contains(":")) {
			String[] itemArray = hostInstanceMethod.split(":");
			if (itemArray.length >= 2) {
				hostname = itemArray[0];
				method = itemArray[1];
			} else if (itemArray.length == 1) {
				hostname = itemArray[0];
			}
		}
		return new AnomalyScope(hostname, method);
	}

	public boolean isApplicableFor(String anomalyIdentifier) {
		String instanceName = "";
		String methodName = null;
		if (anomalyIdentifier.contains(":")) {
			String[] itemArray = anomalyIdentifier.split(":");
			if (itemArray.length >= 2) {
				instanceName = itemArray[1];
				methodName = itemArray[2];
			} else if (itemArray.length == 2) {
				instanceName = itemArray[0];
				methodName = itemArray[1];
			}
			
			if(instanceName.startsWith(hostname)) {
				if(null == method) {
					return true;
				} else if(methodName.equals(method) || method.isEmpty()) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		} else {
			System.out.println(anomalyIdentifier + " is not applicable for " + this.toString());
			return false;
		}
	}

	@Override
	public String toString() {
		return this.hostname + ":" + this.method;
	}

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	
	@Override
	public boolean equals (Object o) {
		AnomalyScope as = (AnomalyScope)o;
		if(this.hostname.equals(as.getHostname())) {
				if(this.method == null && as.getMethod() == null) {
					return true;
				}
				
				if(null != this.method && null != as.getMethod() && this.method.equals(as.getMethod())) {
					return true;
				}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 1;
		hashCode += 41 * hostname.hashCode();
		hashCode += (null == method)? 13 : method.hashCode();
		return hashCode;
	}
}
