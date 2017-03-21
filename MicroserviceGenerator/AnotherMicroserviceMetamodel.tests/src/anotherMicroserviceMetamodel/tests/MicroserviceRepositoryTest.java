/**
 */
package anotherMicroserviceMetamodel.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;
import microserviceMetamodel.AnotherMicroserviceMetamodelFactory;
import microserviceMetamodel.MicroserviceRepository;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Microservice Repository</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class MicroserviceRepositoryTest extends TestCase {

	/**
	 * The fixture for this Microservice Repository test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MicroserviceRepository fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MicroserviceRepositoryTest.class);
	}

	/**
	 * Constructs a new Microservice Repository test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MicroserviceRepositoryTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Microservice Repository test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(MicroserviceRepository fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Microservice Repository test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MicroserviceRepository getFixture() {
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
		setFixture(AnotherMicroserviceMetamodelFactory.eINSTANCE.createMicroserviceRepository());
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

} //MicroserviceRepositoryTest
