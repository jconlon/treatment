/**
 * Copyright Verticon, Inc. 2014 All rights reserved.
 */
package com.verticon.treatment.provider;

import static com.verticon.treatment.provider.EventItemProvider.df;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.verticon.treatment.Event;
import com.verticon.treatment.Person;
import com.verticon.treatment.TreatmentFactory;
import com.verticon.treatment.TreatmentPackage;

/**
 * This is the item provider adapter for a {@link com.verticon.treatment.Person} object.
 * <!-- begin-user-doc -->
 * @implements ITableItemLabelProvider <!-- end-user-doc -->
 * @generated
 */
public class PersonItemProvider
  extends ItemProviderAdapter
  implements
    IEditingDomainItemProvider,
    IStructuredItemContentProvider,
    ITreeItemContentProvider,
    IItemLabelProvider,
 IItemPropertySource,
		ITableItemLabelProvider
{
  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static final String copyright = "Copyright Verticon, Inc. 2014 All rights reserved.";

  /**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public PersonItemProvider(AdapterFactory adapterFactory)
  {
		super(adapterFactory);
	}

  /**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
  {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addAccountPropertyDescriptor(object);
			addCommentsPropertyDescriptor(object);
			addStartDatePropertyDescriptor(object);
			addCurrentPhasePropertyDescriptor(object);
			addEnteredPhasePropertyDescriptor(object);
			addDaysInPhasePropertyDescriptor(object);
			addCurrentSoberDaysPropertyDescriptor(object);
			addTotalSoberDaysPropertyDescriptor(object);
			addDaysInCustodyPropertyDescriptor(object);
			addRelapsesPropertyDescriptor(object);
			addLastRelapsePropertyDescriptor(object);
			addOtherViolationsPropertyDescriptor(object);
			addLastViolationPropertyDescriptor(object);
			addDaysInProgramPropertyDescriptor(object);
			addLastIncentivePropertyDescriptor(object);
			addIncentivesPropertyDescriptor(object);
			addLastSanctionPropertyDescriptor(object);
			addSanctionsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

  /**
	 * This adds a property descriptor for the Account feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addAccountPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_account_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Person_account_feature", "_UI_Person_type"),
				 TreatmentPackage.Literals.PERSON__ACCOUNT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

  /**
	 * This adds a property descriptor for the Comments feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addCommentsPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_comments_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Person_comments_feature", "_UI_Person_type"),
				 TreatmentPackage.Literals.PERSON__COMMENTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

  /**
	 * This adds a property descriptor for the Start Date feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addStartDatePropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_startDate_feature"),
				 getString("_UI_Person_startDate_description"),
				 TreatmentPackage.Literals.PERSON__START_DATE,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_ProgramPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Current Phase feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addCurrentPhasePropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_currentPhase_feature"),
				 getString("_UI_Person_currentPhase_description"),
				 TreatmentPackage.Literals.PERSON__CURRENT_PHASE,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_PhasePropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Entered Phase feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addEnteredPhasePropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_enteredPhase_feature"),
				 getString("_UI_Person_enteredPhase_description"),
				 TreatmentPackage.Literals.PERSON__ENTERED_PHASE,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_PhasePropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Days In Phase feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addDaysInPhasePropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_daysInPhase_feature"),
				 getString("_UI_Person_daysInPhase_description"),
				 TreatmentPackage.Literals.PERSON__DAYS_IN_PHASE,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_PhasePropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Current Sober Days feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addCurrentSoberDaysPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_currentSoberDays_feature"),
				 getString("_UI_Person_currentSoberDays_description"),
				 TreatmentPackage.Literals.PERSON__CURRENT_SOBER_DAYS,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_SobrietyPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Total Sober Days feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addTotalSoberDaysPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_totalSoberDays_feature"),
				 getString("_UI_Person_totalSoberDays_description"),
				 TreatmentPackage.Literals.PERSON__TOTAL_SOBER_DAYS,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_SobrietyPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Days In Custody feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addDaysInCustodyPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_daysInCustody_feature"),
				 getString("_UI_Person_daysInCustody_description"),
				 TreatmentPackage.Literals.PERSON__DAYS_IN_CUSTODY,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_ViolationsPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Relapses feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addRelapsesPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_relapses_feature"),
				 getString("_UI_Person_relapses_description"),
				 TreatmentPackage.Literals.PERSON__RELAPSES,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_ViolationsPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Last Relapse feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addLastRelapsePropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_lastRelapse_feature"),
				 getString("_UI_Person_lastRelapse_description"),
				 TreatmentPackage.Literals.PERSON__LAST_RELAPSE,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_ViolationsPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Other Violations feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addOtherViolationsPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_otherViolations_feature"),
				 getString("_UI_Person_otherViolations_description"),
				 TreatmentPackage.Literals.PERSON__OTHER_VIOLATIONS,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_ViolationsPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Last Violation feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addLastViolationPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_lastViolation_feature"),
				 getString("_UI_Person_lastViolation_description"),
				 TreatmentPackage.Literals.PERSON__LAST_VIOLATION,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_ViolationsPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Days In Program feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addDaysInProgramPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_daysInProgram_feature"),
				 getString("_UI_Person_daysInProgram_description"),
				 TreatmentPackage.Literals.PERSON__DAYS_IN_PROGRAM,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_ProgramPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Last Incentive feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addLastIncentivePropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_lastIncentive_feature"),
				 getString("_UI_Person_lastIncentive_description"),
				 TreatmentPackage.Literals.PERSON__LAST_INCENTIVE,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_IncentivesPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Incentives feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addIncentivesPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_incentives_feature"),
				 getString("_UI_Person_incentives_description"),
				 TreatmentPackage.Literals.PERSON__INCENTIVES,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_IncentivesPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Last Sanction feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addLastSanctionPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_lastSanction_feature"),
				 getString("_UI_Person_lastSanction_description"),
				 TreatmentPackage.Literals.PERSON__LAST_SANCTION,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 getString("_UI_IncentivesPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This adds a property descriptor for the Sanctions feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addSanctionsPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Person_sanctions_feature"),
				 getString("_UI_Person_sanctions_description"),
				 TreatmentPackage.Literals.PERSON__SANCTIONS,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_IncentivesPropertyCategory"),
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
	}

  /**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object)
  {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(TreatmentPackage.Literals.PERSON__EVENTS);
		}
		return childrenFeatures;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  protected EStructuralFeature getChildFeature(Object object, Object child)
  {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

  /**
	 * This returns Person.gif.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public Object getImage(Object object)
  {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Person"));
	}

  	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
  @Override
  public String getText(Object object)
  {
		String account = ((Person) object).getAccount();
		if (account == null || account.length() == 0) {
			return "Account: ?";
		}

		String name = TreatmentEditPlugin.INSTANCE.getName(account);

		return name == null || name.length() == 0 ? account : name + " ("
				+ account + ')';
  }

  /**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public void notifyChanged(Notification notification)
  {
		updateChildren(notification);

		switch (notification.getFeatureID(Person.class)) {
			case TreatmentPackage.PERSON__ACCOUNT:
			case TreatmentPackage.PERSON__COMMENTS:
			case TreatmentPackage.PERSON__START_DATE:
			case TreatmentPackage.PERSON__CURRENT_PHASE:
			case TreatmentPackage.PERSON__ENTERED_PHASE:
			case TreatmentPackage.PERSON__DAYS_IN_PHASE:
			case TreatmentPackage.PERSON__CURRENT_SOBER_DAYS:
			case TreatmentPackage.PERSON__TOTAL_SOBER_DAYS:
			case TreatmentPackage.PERSON__DAYS_IN_CUSTODY:
			case TreatmentPackage.PERSON__RELAPSES:
			case TreatmentPackage.PERSON__LAST_RELAPSE:
			case TreatmentPackage.PERSON__OTHER_VIOLATIONS:
			case TreatmentPackage.PERSON__LAST_VIOLATION:
			case TreatmentPackage.PERSON__DAYS_IN_PROGRAM:
			case TreatmentPackage.PERSON__LAST_INCENTIVE:
			case TreatmentPackage.PERSON__INCENTIVES:
			case TreatmentPackage.PERSON__LAST_SANCTION:
			case TreatmentPackage.PERSON__SANCTIONS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case TreatmentPackage.PERSON__EVENTS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * Add the current date to all new events.
	 * 
	 * @NOT generated
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#createChildParameter(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	protected CommandParameter createChildParameter(Object feature, Object child) {
		if (child instanceof Event) {
			((Event) child).setDate(new Date());
		}
		return super.createChildParameter(feature, child);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
  {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createEnterPhase()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createTreatment()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createSelfHelp()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createGroupTreatment()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createIndivdualTreatment()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createIncentive()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createSanction()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createStatus()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createBreathalyzer()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createUrinalysis()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createOffense()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createViolation()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createRelapse()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createInCustody()));

		newChildDescriptors.add
			(createChildParameter
				(TreatmentPackage.Literals.PERSON__EVENTS,
				 TreatmentFactory.eINSTANCE.createRelease()));
	}

  /**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public ResourceLocator getResourceLocator()
  {
		return TreatmentEditPlugin.INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#getColumnImage(java
	 * .lang.Object, int)
	 * 
	 * @generated NOT
	 */
	@Override
	public Object getColumnImage(Object object, int columnIndex) {
		// TODO Auto-generated method stub
		return super.getColumnImage(object, columnIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#getColumnText(java.
	 * lang.Object, int) String[] headers = { "Account", "Start Date",
	 * "Days in Program", "Current Phase", "Days in Phase", "Entered Phase",
	 * "Current Sober Days", "Total Sober Days", "Days in Custody",
	 * "Last Relapse", "Last Violation", "Other Violations", "Relapses",
	 * "Comments" };
	 * 
	 * @generated NOT
	 */
	@Override
	public String getColumnText(Object object, int columnIndex) {
		Person person = (Person) object;
		switch (columnIndex) {
		case 0: // Account
			return getText(object);
		case 1: // Start Date
			return person.getStartDate() != null ? df.format(person
					.getStartDate())
					: "";
		case 2: // Days in Program
			return Integer.toString(person.getDaysInProgram());
		case 3: // Current Phase
			return person.getCurrentPhase().name();
		case 4:// Days in Phase
			return Integer.toString(person.getDaysInPhase());
		case 5:// Entered Phase
			return person.getEnteredPhase() != null ? df.format(person
					.getEnteredPhase()) : "";
		case 6:// Current Sober Days
			return Integer.toString(person.getCurrentSoberDays());
		case 7:// Total Sober Days
			return Integer.toString(person.getTotalSoberDays());

		case 8:// "Last Incentive",
			return person.getLastIncentive() != null ? df.format(person
					.getLastIncentive()) : "";
		case 9:// "Total Incentives",
			return Integer.toString(person.getIncentives());

		case 10:// "Last Sanction",
			return person.getLastSanction() != null ? df.format(person
					.getLastSanction()) : "";
		case 11:// "Total Sanctions"
			return Integer.toString(person.getSanctions());

		case 12:// Days in Custody
			return Integer.toString(person.getDaysInCustody());
		case 13:// Last Relapse
			return person.getLastRelapse() != null ? df.format(person
					.getLastRelapse()) : "";
		case 14:// Relapses
			return Integer.toString(person.getRelapses());

		case 15:// Last Violation
			return person.getLastViolation() != null ? df.format(person
					.getLastViolation()) : "";
		case 16:// Other Violations
			return Integer.toString(person.getOtherViolations());

		case 17: // Comments
			return person.getComments();
		default:
			StringBuilder sb2 = new StringBuilder("unknown column index ");
			sb2.append(columnIndex);
			return sb2.toString();
		}
	}
}
