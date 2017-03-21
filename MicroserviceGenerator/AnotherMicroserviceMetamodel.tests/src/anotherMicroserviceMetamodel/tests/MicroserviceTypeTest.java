/**
 */
package anotherMicroserviceMetamodel.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;
import microserviceMetamodel.AnotherMicroserviceMetamodelFactory;
import microserviceMetamodel.MicroserviceType;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Microservice Type</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class MicroserviceTypeTest extends TestCase {

	/**
	 * The fixture for this Microservice Type test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MicroserviceType fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MicroserviceTypeTest.class);
	}

	/**
	 * Constructs a new Microservice Type test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MicroserviceTypeTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Microservice Type test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(MicroserviceType fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Microservice Type test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MicroserviceType getFixture() {
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
		setFixture(AnotherMicroserviceMetamodelFactory.eINSTANCE.createMicroserviceType());
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

} //MicroserviceTypeTest
