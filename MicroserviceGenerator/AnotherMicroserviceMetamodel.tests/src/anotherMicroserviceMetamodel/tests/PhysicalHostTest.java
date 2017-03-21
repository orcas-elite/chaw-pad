/**
 */
package anotherMicroserviceMetamodel.tests;

import junit.textui.TestRunner;
import microserviceMetamodel.AnotherMicroserviceMetamodelFactory;
import microserviceMetamodel.PhysicalHost;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Physical Host</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PhysicalHostTest extends HostTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PhysicalHostTest.class);
	}

	/**
	 * Constructs a new Physical Host test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalHostTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Physical Host test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected PhysicalHost getFixture() {
		return (PhysicalHost)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AnotherMicroserviceMetamodelFactory.eINSTANCE.createPhysicalHost());
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

} //PhysicalHostTest
