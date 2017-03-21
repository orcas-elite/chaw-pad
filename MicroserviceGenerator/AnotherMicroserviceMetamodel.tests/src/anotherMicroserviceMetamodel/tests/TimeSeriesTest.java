/**
 */
package anotherMicroserviceMetamodel.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;
import microserviceMetamodel.AnotherMicroserviceMetamodelFactory;
import microserviceMetamodel.TimeSeries;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Time Series</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class TimeSeriesTest extends TestCase {

	/**
	 * The fixture for this Time Series test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TimeSeries fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(TimeSeriesTest.class);
	}

	/**
	 * Constructs a new Time Series test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeSeriesTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Time Series test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(TimeSeries fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Time Series test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TimeSeries getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AnotherMicroserviceMetamodelFactory.eINSTANCE.createTimeSeries());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //TimeSeriesTest
