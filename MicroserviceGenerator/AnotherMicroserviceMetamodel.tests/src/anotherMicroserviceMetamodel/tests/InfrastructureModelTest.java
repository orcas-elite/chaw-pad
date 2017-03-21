/**
 */
package anotherMicroserviceMetamodel.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;
import microserviceMetamodel.AnotherMicroserviceMetamodelFactory;
import microserviceMetamodel.InfrastructureModel;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Infrastructure Model</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfrastructureModelTest extends TestCase {

	/**
	 * The fixture for this Infrastructure Model test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfrastructureModel fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InfrastructureModelTest.class);
	}

	/**
	 * Constructs a new Infrastructure Model test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfrastructureModelTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Infrastructure Model test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(InfrastructureModel fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Infrastructure Model test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfrastructureModel getFixture() {
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
		setFixture(AnotherMicroserviceMetamodelFactory.eINSTANCE.createInfrastructureModel());
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

} //InfrastructureModelTest
