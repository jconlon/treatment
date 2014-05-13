/**
 * Copyright Verticon, Inc. 2014 All rights reserved.
 */
package com.verticon.treatment.provider;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.verticon.naming.INameService;

/**
 * This is the central singleton for the Treatment edit plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class TreatmentEditPlugin extends EMFPlugin
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final String copyright = "Copyright Verticon, Inc. 2014 All rights reserved.";

  /**
   * Keep track of the singleton.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final TreatmentEditPlugin INSTANCE = new TreatmentEditPlugin();

  /**
   * Keep track of the singleton.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static Implementation plugin;

	private static ServiceTracker<INameService, INameService> nameServiceTracker;


  /**
   * Create the instance.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TreatmentEditPlugin()
  {
    super
      (new ResourceLocator [] 
       {
       });
  }

	String getName(String account) {
		if (nameServiceTracker != null
				&& nameServiceTracker.getService() != null) {
			INameService ns = nameServiceTracker.getService();
			return ns.getName(account);
		}
		return null;
	}

  /**
   * Returns the singleton instance of the Eclipse plugin.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the singleton instance.
   * @generated
   */
  @Override
  public ResourceLocator getPluginResourceLocator()
  {
    return plugin;
  }

  /**
   * Returns the singleton instance of the Eclipse plugin.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the singleton instance.
   * @generated
   */
  public static Implementation getPlugin()
  {
    return plugin;
  }

  /**
   * The actual implementation of the Eclipse <b>Plugin</b>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static class Implementation extends EclipsePlugin
  {
    /**
     * Creates an instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Implementation()
    {
      super();

      // Remember the static instance.
      //
      plugin = this;
    }

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext
		 * )
		 */
		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);
			// create a tracker and track the service
			nameServiceTracker = new ServiceTracker<INameService, INameService>(
					context, INameService.class, null);
			nameServiceTracker.open();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext
		 * )
		 */
		@Override
		public void stop(BundleContext context) throws Exception {
			nameServiceTracker.close();

			super.stop(context);
		}

  }

}
