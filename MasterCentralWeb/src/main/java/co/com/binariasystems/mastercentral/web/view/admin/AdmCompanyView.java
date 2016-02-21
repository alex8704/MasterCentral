package co.com.binariasystems.mastercentral.web.view.admin;

import java.util.Map;

import co.com.binariasystems.commonsmodel.enumerated.PayrollPeriodType;
import co.com.binariasystems.fmw.vweb.mvp.annotation.Init;
import co.com.binariasystems.fmw.vweb.mvp.annotation.NoConventionString;
import co.com.binariasystems.fmw.vweb.mvp.annotation.View;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewBuild;
import co.com.binariasystems.fmw.vweb.mvp.annotation.validation.NullValidator;
import co.com.binariasystems.fmw.vweb.mvp.views.AbstractView;
import co.com.binariasystems.fmw.vweb.uicomponet.AddressEditorField;
import co.com.binariasystems.fmw.vweb.uicomponet.FormPanel;
import co.com.binariasystems.fmw.vweb.uicomponet.MessageDialog;
import co.com.binariasystems.fmw.vweb.uicomponet.SearcherField;
import co.com.binariasystems.fmw.vweb.uicomponet.builders.ButtonBuilder;
import co.com.binariasystems.fmw.vweb.uicomponet.builders.TextFieldBuilder;
import co.com.binariasystems.mastercentral.shared.business.dto.AddressDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.BankDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.BusinessGroupDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.CityDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.CompanyDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.EconomicActivityDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.TaxpayerTypeDTO;
import co.com.binariasystems.mastercentral.web.controller.admin.AdmCompanyViewController;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;

@View(url="/company", viewStringsByConventions= true,
controller=AdmCompanyViewController.class)
public class AdmCompanyView extends AbstractView{
	private FormPanel form;
	@NullValidator
	@PropertyId("businessName")
	private TextFieldBuilder businessNameTxt;
	@PropertyId("taxIdentification")
	private TextFieldBuilder taxIdentificationTxt;
	@PropertyId("checkDigit")
	private TextFieldBuilder checkDigitTxt;
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
	@PropertyId("payrollPeriodType")
	private ComboBox payrollPeriodTypeCmb;
	@PropertyId("taxpayerType")
	private SearcherField<TaxpayerTypeDTO> taxpayerTypeTxt;
	@PropertyId("businessGroup")
	private SearcherField<BusinessGroupDTO> businessGroupTxt;
	@PropertyId("payrollBank")
	private SearcherField<BankDTO> payrollBankTxt;
	@PropertyId("economicActivity")
	private SearcherField<EconomicActivityDTO> economicActivityTxt;
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
	
	
	private BeanFieldGroup<CompanyDTO> fieldGroup;
    private CompanyDTO company = new CompanyDTO();
    
    private AdmCompanyViewEventListener eventListener;
	private Map<String, String>	notificationMsgMapping;
	private Map<Button, MessageDialog> confirmMsgDialogMapping;
	
	@ViewBuild
	public Component build(){
		
		return form;
	}
	
	private void addDataBinding(){
		eventListener = new AdmCompanyViewEventListener();
	}
	
	@Init
	public void init(){
		
	}
	
	private class AdmCompanyViewEventListener {
		
	}
}
