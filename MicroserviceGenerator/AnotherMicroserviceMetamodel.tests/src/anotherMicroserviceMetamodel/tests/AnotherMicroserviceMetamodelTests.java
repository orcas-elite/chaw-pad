/**
 */
package anotherMicroserviceMetamodel.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>anotherMicroserviceMetamodel</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class AnotherMicroserviceMetamodelTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new AnotherMicroserviceMetamodelTests("anotherMicroserviceMetamodel Tests");
		suite.addTestSuite(EndpointTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnotherMicroserviceMetamodelTests(String name) {
		super(name);
	}

} //AnotherMicroserviceMetamodelTests
