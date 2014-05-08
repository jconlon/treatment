package com.verticon.treatment.views;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.columnChooser.command.DisplayColumnChooserCommandHandler;
import org.eclipse.nebula.widgets.nattable.command.StructuralRefreshCommand;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.export.ILayerExporter;
import org.eclipse.nebula.widgets.nattable.extension.poi.HSSFExcelExporter;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultColumnHeaderDataLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupModel;
import org.eclipse.nebula.widgets.nattable.group.config.ColumnGroupMenuItemProviders;
import org.eclipse.nebula.widgets.nattable.hideshow.ColumnHideShowLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.reorder.ColumnReorderLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.menu.HeaderMenuConfiguration;
import org.eclipse.nebula.widgets.nattable.ui.menu.PopupMenuAction;
import org.eclipse.nebula.widgets.nattable.ui.menu.PopupMenuBuilder;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.verticon.treatment.Person;
import com.verticon.treatment.Program;
import com.verticon.treatment.presentation.TreatmentEditor;
import com.verticon.treatment.provider.PersonItemProvider;
import com.verticon.treatment.provider.TreatmentItemProviderAdapterFactory;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class PeopleView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.verticon.treatment.views.PeopleView";

	private static final String[] HEADER = {
			"Account", // 0
			"Start Date",
			"Days in Program", // 1,2 Program
			"Current Phase", "Days in Phase",
			"Entered Phase", // 3,4,5 Phase
			"Current Sober Days",
			"Total Sober Days", // "Sobriety", 6, 7
			// new
			"Last Incentive", "Total Incentives",
			"Last Sanction",
			"Total Sanctions",// 8,9,10,11
			//
			"Days in Custody", "Last Relapse", "Relapses", "Last Violation",
			"Other Violations", // "Violations", 12, 13, 14, 15, 16
			"Comments" // 17
	};


	private NatTable natTable;
	private final ColumnGroupModel columnGroupModel = new ColumnGroupModel();
	private final BodyDataProvider programDataProvider = new BodyDataProvider();
	private BodyLayerStack bodyLayer;

	public class BodyLayerStack extends AbstractLayerTransform {

		private final SelectionLayer selectionLayer;
		final ColumnHideShowLayer columnHideShowLayer;

		public BodyLayerStack(IDataProvider dataProvider) {
			DataLayer bodyDataLayer = new DataLayer(dataProvider);
			ColumnReorderLayer columnReorderLayer = new ColumnReorderLayer(
					bodyDataLayer);
			columnHideShowLayer = new ColumnHideShowLayer(
					columnReorderLayer);
			selectionLayer = new SelectionLayer(columnHideShowLayer);
			ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);
			setUnderlyingLayer(viewportLayer);
		}

		public SelectionLayer getSelectionLayer() {
			return selectionLayer;
		}
	}



	public class ColumnHeaderLayerStack extends AbstractLayerTransform {
		final ColumnGroupHeaderLayer columnGroupHeaderLayer;
		final ColumnHeaderLayer columnHeaderLayer;
		final DefaultColumnHeaderDataLayer columnHeaderDataLayer;
		public ColumnHeaderLayerStack(IDataProvider dataProvider) {
			// DataLayer dataLayer = new DataLayer(dataProvider);
			columnHeaderDataLayer = new DefaultColumnHeaderDataLayer(
					dataProvider);
			columnHeaderLayer = new ColumnHeaderLayer(columnHeaderDataLayer,
					bodyLayer, bodyLayer.getSelectionLayer());

			columnGroupHeaderLayer = new ColumnGroupHeaderLayer(
					columnHeaderLayer, bodyLayer.getSelectionLayer(),
					columnGroupModel);
			columnGroupHeaderLayer.addColumnsIndexesToGroup("Program", 1, 2);
			columnGroupHeaderLayer.addColumnsIndexesToGroup("Phase", 3, 4, 5);
			columnGroupHeaderLayer.addColumnsIndexesToGroup("Sobriety", 6, 7);
			columnGroupHeaderLayer.addColumnsIndexesToGroup("Incentives", 8, 9,
					10, 11);
			columnGroupHeaderLayer.addColumnsIndexesToGroup("Violations", 12,
					13, 14, 15, 16);
			columnGroupHeaderLayer.setStaticColumnIndexesByGroup("Phase", 3);
			// columnGroupHeaderLayer.setGroupUnbreakable(1);
			// columnGroupHeaderLayer.setGroupUnbreakable(3);
			// columnGroupHeaderLayer.setGroupUnbreakable(6);
			// columnGroupHeaderLayer.setGroupUnbreakable(8);

			setUnderlyingLayer(columnGroupHeaderLayer);
		}
	}

	class RowHeaderLayerStack extends AbstractLayerTransform {

		public RowHeaderLayerStack(IDataProvider dataProvider) {
			DataLayer dataLayer = new DataLayer(dataProvider, 50, 30);
			RowHeaderLayer rowHeaderLayer = new RowHeaderLayer(dataLayer,
					bodyLayer, bodyLayer.getSelectionLayer());
			setUnderlyingLayer(rowHeaderLayer);
		}
	}

	class BodyDataProvider implements IDataProvider {

		private Program program = null;

		AdapterFactory adapterFactory = new TreatmentItemProviderAdapterFactory();
		PersonItemProvider cp = null;
		AdapterFactoryEditingDomain ed = null;

		/**
		 * @param program
		 *            the program to set
		 */
		void setProgram(Program program) {
			this.program = program;
			if (program != null) {
				ed = (AdapterFactoryEditingDomain) AdapterFactoryEditingDomain
						.getEditingDomainFor(program);
			} else {
				ed = null;
			}
		}

		@Override
		public Object getDataValue(int columnIndex, int rowIndex) {
			Person person = program.getPersons().get(rowIndex);
			ITableItemLabelProvider adapter = (ITableItemLabelProvider) adapterFactory
					.adapt(person, ITableItemLabelProvider.class);
			return adapter.getColumnText(person, columnIndex);
		}

		@Override
		public void setDataValue(int columnIndex, int rowIndex, Object newValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getColumnCount() {
			return HEADER.length;
		}

		@Override
		public int getRowCount() {
			return program != null ? program.getPersons().size() : 0;
		}

	}

	ISelectionListener selectionListener = new ISelectionListener() {

		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			if (!(part instanceof TreatmentEditor)) {
				return;
			}

			EditingDomain editingDomain = ((IEditingDomainProvider) part
					.getAdapter(IEditingDomainProvider.class))
					.getEditingDomain();

			Resource r = editingDomain.getResourceSet().getResources().get(0);
			EObject o = r.getContents().get(0);

			if (o instanceof Program) {
				programDataProvider.setProgram((Program) o);

			} else {
				programDataProvider.setProgram(null);
			}
			// Send a refresh to the nattable
			natTable.doCommand(new StructuralRefreshCommand());
		}
	};

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {

		getSite().getPage().addSelectionListener(selectionListener);

		// Create the help context id for the viewer's control
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(natTable.getControl(),
		// "com.verticon.treatment.views.viewer");
		// makeActions();
		// hookContextMenu();
		// hookDoubleClickAction();
		// contributeToActionBars();

		DefaultColumnHeaderDataProvider colHeaderDataProvider = new DefaultColumnHeaderDataProvider(
				HEADER);
		DefaultRowHeaderDataProvider rowHeaderDataProvider = new DefaultRowHeaderDataProvider(
				programDataProvider);

		bodyLayer = new BodyLayerStack(programDataProvider);
		ColumnHeaderLayerStack columnHeaderLayerStack = new ColumnHeaderLayerStack(
				colHeaderDataProvider);
		RowHeaderLayerStack rowHeaderLayer = new RowHeaderLayerStack(
				rowHeaderDataProvider);

		DefaultCornerDataProvider cornerDataProvider = new DefaultCornerDataProvider(
				colHeaderDataProvider, rowHeaderDataProvider);
		CornerLayer cornerLayer = new CornerLayer(new DataLayer(
				cornerDataProvider), rowHeaderLayer, columnHeaderLayerStack);

		GridLayer gridLayer = new GridLayer(bodyLayer, columnHeaderLayerStack,
				rowHeaderLayer, cornerLayer);
		natTable = new NatTable(parent, gridLayer, false);

		// Register create column group command handler

		// Register column chooser
		DisplayColumnChooserCommandHandler columnChooserCommandHandler = new DisplayColumnChooserCommandHandler(
				bodyLayer.getSelectionLayer(),
				bodyLayer.columnHideShowLayer,
				columnHeaderLayerStack.columnHeaderLayer,
				columnHeaderLayerStack.columnHeaderDataLayer,
				columnHeaderLayerStack.columnGroupHeaderLayer, columnGroupModel);

		bodyLayer.registerCommandHandler(columnChooserCommandHandler);

		natTable.addConfiguration(new DefaultNatTableStyleConfiguration());
		natTable.addConfiguration(new HeaderMenuConfiguration(natTable) {
			@Override
			protected PopupMenuBuilder createColumnHeaderMenu(NatTable natTable) {
				return super.createColumnHeaderMenu(natTable)
						.withColumnChooserMenuItem();
			}
		});
		natTable.addConfiguration(new AbstractRegistryConfiguration() {
			@Override
			public void configureRegistry(IConfigRegistry configRegistry) {
				configRegistry.registerConfigAttribute(
						ILayerExporter.CONFIG_ATTRIBUTE,
						new HSSFExcelExporter());
			}
		});

		// Column group header menu
		final Menu columnGroupHeaderMenu = new PopupMenuBuilder(natTable)
				.withMenuItemProvider(
						ColumnGroupMenuItemProviders
								.renameColumnGroupMenuItemProvider())
				.withMenuItemProvider(
						ColumnGroupMenuItemProviders
								.removeColumnGroupMenuItemProvider()).build();

		natTable.addConfiguration(new AbstractUiBindingConfiguration() {
			@Override
			public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {
				uiBindingRegistry.registerFirstMouseDownBinding(
						new MouseEventMatcher(SWT.NONE,
								GridRegion.COLUMN_GROUP_HEADER,
								MouseEventMatcher.RIGHT_BUTTON),
						new PopupMenuAction(columnGroupHeaderMenu));
			}
		});

		natTable.configure();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		natTable.setFocus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		getSite().getPage().removeSelectionListener(selectionListener);
		natTable.dispose();
		natTable = null;
		super.dispose();
	}
	// private void hookContextMenu() {
	// MenuManager menuMgr = new MenuManager("#PopupMenu");
	// menuMgr.setRemoveAllWhenShown(true);
	// menuMgr.addMenuListener(new IMenuListener() {
	// @Override
	// public void menuAboutToShow(IMenuManager manager) {
	// PeopleView.this.fillContextMenu(manager);
	// }
	// });
	// // Menu menu = menuMgr.createContextMenu(viewer.getControl());
	// // viewer.getControl().setMenu(menu);
	// // getSite().registerContextMenu(menuMgr, viewer);
	// }
	//
	// private void contributeToActionBars() {
	// IActionBars bars = getViewSite().getActionBars();
	// fillLocalPullDown(bars.getMenuManager());
	// fillLocalToolBar(bars.getToolBarManager());
	// }

	// private void fillLocalPullDown(IMenuManager manager) {
	// manager.add(action1);
	// manager.add(new Separator());
	// manager.add(action2);
	// }
	//
	// private void fillContextMenu(IMenuManager manager) {
	// manager.add(action1);
	// manager.add(action2);
	// // Other plug-ins can contribute there actions here
	// manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	// }
	//
	// private void fillLocalToolBar(IToolBarManager manager) {
	// manager.add(action1);
	// manager.add(action2);
	// }

	// private void makeActions() {
	// action1 = new Action() {
	// @Override
	// public void run() {
	// showMessage("Action 1 executed");
	// }
	// };
	// action1.setText("Action 1");
	// action1.setToolTipText("Action 1 tooltip");
	// action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
	// getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	//
	// action2 = new Action() {
	// @Override
	// public void run() {
	// showMessage("Action 2 executed");
	// }
	// };
	// action2.setText("Action 2");
	// action2.setToolTipText("Action 2 tooltip");
	// action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
	// getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	// doubleClickAction = new Action() {
	// @Override
	// public void run() {
	// ISelection selection = viewer.getSelection();
	// Object obj = ((IStructuredSelection)selection).getFirstElement();
	// showMessage("Double-click detected on "+obj.toString());
	// }
	// };
	// }

	// private void hookDoubleClickAction() {
	// viewer.addDoubleClickListener(new IDoubleClickListener() {
	// @Override
	// public void doubleClick(DoubleClickEvent event) {
	// doubleClickAction.run();
	// }
	// });
	// }
	// private void showMessage(String message) {
	// MessageDialog.openInformation(
	// viewer.getControl().getShell(),
	// "People View",
	// message);
	// }
}