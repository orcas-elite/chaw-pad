/**
 */
package anotherMicroserviceMetamodel.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;
import microserviceMetamodel.AnotherMicroserviceMetamodelFactory;
import microserviceMetamodel.RESTOperation;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>REST Operation</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class RESTOperationTest extends TestCase {

	/**
	 * The fixture for this REST Operation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RESTOperation fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RESTOperationTest.class);
	}

	/**
	 * Constructs a new REST Operation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RESTOperationTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this REST Operation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RESTOperation fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this REST Operation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RESTOperation getFixture() {
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
		setFixture(AnotherMicroserviceMetamodelFactory.eINSTANCE.createRESTOperation());
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

} //RESTOperationTest
