/**
 * Copyright Verticon, Inc. 2014 All rights reserved.
 */
package com.verticon.treatment.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>treatment</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class TreatmentTests extends TestSuite
{

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final String copyright = "Copyright Verticon, Inc. 2014 All rights reserved.";

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static void main(String[] args)
  {
    TestRunner.run(suite());
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static Test suite()
  {
    TestSuite suite = new TreatmentTests("treatment Tests");
    suite.addTestSuite(PersonTest.class);
    suite.addTestSuite(EnterPhaseTest.class);
    suite.addTestSuite(SelfHelpTest.class);
    suite.addTestSuite(TreatmentTest.class);
    suite.addTestSuite(GroupTreatmentTest.class);
    suite.addTestSuite(IndivdualTreatmentTest.class);
    suite.addTestSuite(IncentiveTest.class);
    suite.addTestSuite(SanctionTest.class);
    suite.addTestSuite(StatusTest.class);
    suite.addTestSuite(BreathalyzerTest.class);
    suite.addTestSuite(UrinalysisTest.class);
    suite.addTestSuite(OffenseTest.class);
    suite.addTestSuite(ViolationTest.class);
    suite.addTestSuite(RelapseTest.class);
    suite.addTestSuite(InCustodyTest.class);
    suite.addTestSuite(ReleaseTest.class);
    return suite;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TreatmentTests(String name)
  {
    super(name);
  }

} //TreatmentTests
