/**
 */
package anotherMicroserviceMetamodel.tests;

import junit.textui.TestRunner;
import microserviceMetamodel.AnotherMicroserviceMetamodelFactory;
import microserviceMetamodel.MicroserviceOperationTimeSeriesPoint;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Microservice Operation Time Series Point</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class MicroserviceOperationTimeSeriesPointTest extends TimeSeriesPointTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MicroserviceOperationTimeSeriesPointTest.class);
	}

	/**
	 * Constructs a new Microservice Operation Time Series Point test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MicroserviceOperationTimeSeriesPointTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Microservice Operation Time Series Point test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected MicroserviceOperationTimeSeriesPoint getFixture() {
		return (MicroserviceOperationTimeSeriesPoint)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AnotherMicroserviceMetamodelFactory.eINSTANCE.createMicroserviceOperationTimeSeriesPoint());
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

} //MicroserviceOperationTimeSeriesPointTest
