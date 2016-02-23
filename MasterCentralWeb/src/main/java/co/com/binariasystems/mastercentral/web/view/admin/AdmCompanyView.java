package co.com.binariasystems.mastercentral.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import co.com.binariasystems.fmw.vweb.mvp.annotation.Init;
import co.com.binariasystems.fmw.vweb.mvp.annotation.NoConventionString;
import co.com.binariasystems.fmw.vweb.mvp.annotation.View;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewBuild;
import co.com.binariasystems.fmw.vweb.mvp.annotation.validation.AddressValidator;
import co.com.binariasystems.fmw.vweb.mvp.annotation.validation.NullValidator;
import co.com.binariasystems.fmw.vweb.mvp.views.AbstractView;
import co.com.binariasystems.fmw.vweb.uicomponet.AddressEditorField;
import co.com.binariasystems.fmw.vweb.uicomponet.Dimension;
import co.com.binariasystems.fmw.vweb.uicomponet.FormPanel;
import co.com.binariasystems.fmw.vweb.uicomponet.MessageDialog;
import co.com.binariasystems.fmw.vweb.uicomponet.MessageDialog.Type;
import co.com.binariasystems.fmw.vweb.uicomponet.Pager;
import co.com.binariasystems.fmw.vweb.uicomponet.SearcherField;
import co.com.binariasystems.fmw.vweb.uicomponet.builders.ButtonBuilder;
import co.com.binariasystems.fmw.vweb.uicomponet.builders.TextFieldBuilder;
import co.com.binariasystems.fmw.vweb.util.GridUtils.GenericStringPropertyGenerator;
import co.com.binariasystems.mastercentral.shared.business.dto.AddressDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.BankDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.BusinessGroupDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.CityDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.CompanyDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.EconomicActivityDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.TaxpayerTypeDTO;
import co.com.binariasystems.mastercentral.web.controller.admin.AdmCompanyViewController;
import co.com.binariasystems.mastercentral.web.utils.CompanyTaxIdColumnGenerator;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;

@View(url="/company", viewStringsByConventions= true,
controller=AdmCompanyViewController.class)
public class AdmCompanyView extends AbstractView{
	private FormPanel form;
	@NullValidator
	@PropertyId("businessName")
	private TextFieldBuilder businessNameTxt;
	@NullValidator
	@PropertyId("taxIdentification")
	private TextFieldBuilder taxIdentificationTxt;
	@NullValidator
	@PropertyId("checkDigit")
	private TextFieldBuilder checkDigitTxt;
	@NullValidator
	@PropertyId("city")
	private SearcherField<CityDTO> cityTxt;
	@PropertyId("pbxNumber")
	private TextFieldBuilder pbxNumberTxt;
	@PropertyId("phoneNumber1")
	private TextFieldBuilder phoneNumber1Txt;
	@PropertyId("phoneNumber2")
	private TextFieldBuilder phoneNumber2Txt;
	@PropertyId("emailAdress")
	private TextFieldBuilder emailAdressTxt;
	@NullValidator
	@PropertyId("payrollPeriodType")
	private ComboBox payrollPeriodTypeCmb;
	@NullValidator
	@PropertyId("taxpayerType")
	private SearcherField<TaxpayerTypeDTO> taxpayerTypeTxt;
	@PropertyId("businessGroup")
	private SearcherField<BusinessGroupDTO> businessGroupTxt;
	@PropertyId("payrollBank")
	private SearcherField<BankDTO> payrollBankTxt;
	@NullValidator
	@PropertyId("economicActivity")
	private SearcherField<EconomicActivityDTO> economicActivityTxt;
	@AddressValidator
	@PropertyId("address")
	private AddressEditorField<AddressDTO> addressTxt;
	@NoConventionString(permitDescription=true)
    private ButtonBuilder saveBtn;
    @NoConventionString(permitDescription=true)
    private ButtonBuilder editBtn;
    @NoConventionString(permitDescription=true)
    private ButtonBuilder deleteBtn;
    @NoConventionString(permitDescription=true)
    private ButtonBuilder searchBtn;
    @NoConventionString(permitDescription=true)
    private ButtonBuilder cleanBtn;
	
    private GeneratedPropertyContainer gridContainer;
    private Grid companiesGrid;
    private Pager<CompanyDTO, CompanyDTO> pager;
	
	private BeanFieldGroup<CompanyDTO> fieldGroup;
    private CompanyDTO company = new CompanyDTO();
    
    private AdmCompanyViewEventListener eventListener;
	private Map<String, String>	notificationMsgMapping;
	private Map<Button, MessageDialog> confirmMsgDialogMapping;
	
	@ViewBuild
	public Component build(){
		form = new FormPanel(2);
		businessNameTxt = new TextFieldBuilder();
		taxIdentificationTxt = new TextFieldBuilder();
		checkDigitTxt = new TextFieldBuilder();
		cityTxt = new SearcherField<CityDTO>(CityDTO.class);
		pbxNumberTxt = new TextFieldBuilder();
		phoneNumber1Txt = new TextFieldBuilder();
		phoneNumber2Txt = new TextFieldBuilder();
		emailAdressTxt = new TextFieldBuilder();
		payrollPeriodTypeCmb = new ComboBox();
		taxpayerTypeTxt = new SearcherField<TaxpayerTypeDTO>(TaxpayerTypeDTO.class);
		businessGroupTxt = new SearcherField<BusinessGroupDTO>(BusinessGroupDTO.class);
		payrollBankTxt = new SearcherField<BankDTO>(BankDTO.class);
		economicActivityTxt = new SearcherField<EconomicActivityDTO>(EconomicActivityDTO.class);
		addressTxt = new AddressEditorField<AddressDTO>(AddressDTO.class);
		gridContainer = new GeneratedPropertyContainer(new BeanItemContainer<CompanyDTO>(CompanyDTO.class));
		companiesGrid = new Grid();
		pager = new Pager<CompanyDTO, CompanyDTO>();
		saveBtn = new ButtonBuilder(FontAwesome.SAVE);
		editBtn = new ButtonBuilder(FontAwesome.EDIT);
		deleteBtn = new ButtonBuilder(FontAwesome.TRASH);
		searchBtn = new ButtonBuilder(FontAwesome.SEARCH);
		cleanBtn = new ButtonBuilder(FontAwesome.ERASER);
		
		form.add(businessNameTxt, 2, Dimension.percent(100))
		.add(taxIdentificationTxt, Dimension.percent(100))
		.add(checkDigitTxt, Dimension.percent(100))
		.add(cityTxt, Dimension.percent(100))
		.add(pbxNumberTxt, Dimension.percent(100))
		.add(phoneNumber1Txt, Dimension.percent(100))
		.add(phoneNumber2Txt, Dimension.percent(100))
		.add(emailAdressTxt, Dimension.percent(100))
		.add(payrollPeriodTypeCmb, Dimension.percent(100))
		.add(taxpayerTypeTxt, Dimension.percent(100))
		.add(businessGroupTxt, Dimension.percent(100))
		.add(payrollBankTxt, Dimension.percent(100))
		.add(economicActivityTxt, Dimension.percent(100))
		.add(addressTxt, 2, Dimension.percent(100))
		.addCenteredOnNewRow(saveBtn, editBtn, deleteBtn, searchBtn, cleanBtn)
		.addCenteredOnNewRow(Dimension.fullPercent(), companiesGrid)
		.addCenteredOnNewRow(Dimension.fullPercent(), pager);
		
		addDataBinding();
		return form;
	}
	
	private void addDataBinding(){
		eventListener = new AdmCompanyViewEventListener();
		fieldGroup = new BeanFieldGroup<CompanyDTO>(CompanyDTO.class);
		notificationMsgMapping = new HashMap<String, String>();
		confirmMsgDialogMapping = new HashMap<Button, MessageDialog>();
		
		fieldGroup.setItemDataSource(company);
		fieldGroup.setBuffered(false);
		fieldGroup.bindMemberFields(this);
		
		gridContainer.addGeneratedProperty("taxIdentification", new CompanyTaxIdColumnGenerator());
		gridContainer.addGeneratedProperty("city", new GenericStringPropertyGenerator());
		gridContainer.addGeneratedProperty("businessGroup", new GenericStringPropertyGenerator());
		companiesGrid.setContainerDataSource(gridContainer);
		companiesGrid.setColumns("businessName",
				"taxIdentification",
				"city",
				"pbxNumber",
				"emailAdress",
				"businessGroup");
		
		pager.setPageDataTargetForGrid(companiesGrid);
	}
	
	@Init
	public void init(){
		saveBtn.withData("create");
		
		editBtn.withData("edit")
		.disable();
		
		deleteBtn.withData("delete")
		.disable();
		
		emailAdressTxt.withoutUpperTransform();
		
		companiesGrid.setHeightMode(HeightMode.ROW);
		
		notificationMsgMapping.put((String)saveBtn.getData(), getText("common.message.success_complete_creation.notification"));
		notificationMsgMapping.put((String)editBtn.getData(), getText("common.message.success_complete_edition.notification"));
		notificationMsgMapping.put((String)deleteBtn.getData(), getText("common.message.success_complete_deletion.notification"));
		
		confirmMsgDialogMapping.put(saveBtn, new MessageDialog(getText("common.message.creation_confirmation.title"), 
				getText("common.message.creation_confirmation.question"), Type.QUESTION));
		
		confirmMsgDialogMapping.put(editBtn, new MessageDialog(getText("common.message.edition_confirmation.title"), 
				getText("common.message.edition_confirmation.question"), Type.QUESTION));
		
		confirmMsgDialogMapping.put(deleteBtn, new MessageDialog(getText("common.message.deletion_confirmation.title"), 
				getText("common.message.deletion_confirmation.question"), Type.QUESTION));
		
		for(Button button : confirmMsgDialogMapping.keySet())
			button.addClickListener(eventListener);
	}
	
	private void toggleActionButtonsState(){
		editBtn.setEnabled(company.getCompanyId() != null);
		deleteBtn.setEnabled(company.getCompanyId() != null);
	}
	
	private class AdmCompanyViewEventListener implements ClickListener{
		@Override public void buttonClick(ClickEvent event) {
			if(confirmMsgDialogMapping.containsKey(event.getButton()))
				confirmMsgDialogMapping.get(event.getButton()).show();
		}
		
	}
}
