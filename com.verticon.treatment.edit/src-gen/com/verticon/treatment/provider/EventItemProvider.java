/**
 * Copyright Verticon, Inc. 2014 All rights reserved.
 */
package com.verticon.treatment.provider;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
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
import com.verticon.treatment.TreatmentPackage;

/**
 * This is the item provider adapter for a {@link com.verticon.treatment.Event} object.
 * <!-- begin-user-doc -->
 * @implements ITableItemLabelProvider
 * <!-- end-user-doc -->
 * @generated
 */
public class EventItemProvider
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

	final static DateFormat df = new SimpleDateFormat("MM-dd-yy");
  
  /**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EventItemProvider(AdapterFactory adapterFactory)
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

			addPersonPropertyDescriptor(object);
			addDatePropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
			addCommentsPropertyDescriptor(object);
			addEnteredByPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

  /**
	 * This adds a property descriptor for the Person feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addPersonPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Event_person_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Event_person_feature", "_UI_Event_type"),
				 TreatmentPackage.Literals.EVENT__PERSON,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

  /**
	 * This adds a property descriptor for the Date feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addDatePropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Event_date_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Event_date_feature", "_UI_Event_type"),
				 TreatmentPackage.Literals.EVENT__DATE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

  /**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addDescriptionPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Event_description_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Event_description_feature", "_UI_Event_type"),
				 TreatmentPackage.Literals.EVENT__DESCRIPTION,
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
				 getString("_UI_Event_comments_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Event_comments_feature", "_UI_Event_type"),
				 TreatmentPackage.Literals.EVENT__COMMENTS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

  /**
	 * This adds a property descriptor for the Entered By feature.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void addEnteredByPropertyDescriptor(Object object)
  {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_Event_enteredBy_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_Event_enteredBy_feature", "_UI_Event_type"),
				 TreatmentPackage.Literals.EVENT__ENTERED_BY,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 new String[] {
					"org.eclipse.ui.views.properties.expert"
				 }));
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
    Date labelValue = ((Event)object).getDate();
		String label = labelValue == null ? null : df.format(labelValue);

    return label == null || label.length() == 0 ?
      getString("_UI_Event_type") :
      getString("_UI_Event_type") + " " + label;
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

		switch (notification.getFeatureID(Event.class)) {
			case TreatmentPackage.EVENT__DATE:
			case TreatmentPackage.EVENT__DESCRIPTION:
			case TreatmentPackage.EVENT__COMMENTS:
			case TreatmentPackage.EVENT__ENTERED_BY:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

  /**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
  {
		super.collectNewChildDescriptors(newChildDescriptors, object);
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

  /**
	 * Adds table support
	 * 
	 */
	@Override
	public String getColumnText(Object object, int columnIndex) // 14.2.2
	{
		Event event = (Event)object;
		switch (columnIndex){
		case 0: //Date of Event
			if(event.getDate()!=null){
				return df.format(event.getDate());
			}
			return "";
			
		case 1: //Event Type
			String simpleName = event.getClass().getSimpleName();
			return simpleName.substring(0,simpleName.indexOf("Impl"));
			
		case 2: //Person
			
			if(event.getPerson()==null){
  			return "";
  		}
  		Person person = event.getPerson();
  		
  		IItemLabelProvider itemLabelProvider = (IItemLabelProvider)adapterFactory.adapt(person, IItemLabelProvider.class);
  		return itemLabelProvider.getText(person);
  	
		case 3: //Description
			return event.getDescription()!=null?event.getDescription():"";

		case 4: //Publisher Name
			return event.getEnteredBy();
		case 6: //Comments
			return event.getComments();
		default :
			StringBuilder sb2 = new StringBuilder("unknown column index ");
			sb2.append(columnIndex);
			return sb2.toString();
		}
	}
}
