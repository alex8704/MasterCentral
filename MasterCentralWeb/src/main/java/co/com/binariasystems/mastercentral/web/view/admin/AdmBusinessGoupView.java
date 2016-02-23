package co.com.binariasystems.mastercentral.web.view.admin;

import java.util.HashMap;
import java.util.List;
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
import co.com.binariasystems.fmw.vweb.uicomponet.PageDataTarget;
import co.com.binariasystems.fmw.vweb.uicomponet.Pager;
import co.com.binariasystems.fmw.vweb.uicomponet.Pager.PagerMode;
import co.com.binariasystems.fmw.vweb.uicomponet.SearcherField;
import co.com.binariasystems.fmw.vweb.uicomponet.builders.ButtonBuilder;
import co.com.binariasystems.fmw.vweb.uicomponet.builders.TextFieldBuilder;
import co.com.binariasystems.fmw.vweb.util.VWebUtils;
import co.com.binariasystems.mastercentral.shared.business.dto.AddressDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.BusinessGroupDTO;
import co.com.binariasystems.mastercentral.shared.business.dto.CityDTO;
import co.com.binariasystems.mastercentral.web.controller.admin.AdmBusinessGoupViewController;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;

@View(url="/businessGroup", viewStringsByConventions= true,
controller=AdmBusinessGoupViewController.class)
public class AdmBusinessGoupView extends AbstractView{
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
    private Pager<BusinessGroupDTO, BusinessGroupDTO> pager;
    
    private BeanFieldGroup<BusinessGroupDTO> fieldGroup;
    private BusinessGroupDTO businessGroup = new BusinessGroupDTO();
    
	
	private AdmBusinessGroupViewEventListener eventListener;
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
		addressTxt = new AddressEditorField<AddressDTO>(AddressDTO.class);
		pager = new Pager<BusinessGroupDTO, BusinessGroupDTO>(PagerMode.ITEM);
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
		.add(addressTxt, 2, Dimension.percent(100))
		.addEmptyRow()
		.addCenteredOnNewRow(Dimension.percent(100), pager)
		.addEmptyRow()
		.addCenteredOnNewRow(saveBtn, editBtn, deleteBtn, searchBtn, cleanBtn);
		
		addDataBinding();
		
		return form;
	}
	
	private void addDataBinding(){
		eventListener = new AdmBusinessGroupViewEventListener();
		fieldGroup = new BeanFieldGroup<BusinessGroupDTO>(BusinessGroupDTO.class);
		notificationMsgMapping = new HashMap<String, String>();
		confirmMsgDialogMapping = new HashMap<Button, MessageDialog>();
		
		pager.setMaxCachedPages(10);
		pager.setPageDataTarget(eventListener);
		
		fieldGroup.setItemDataSource(businessGroup);
		fieldGroup.setBuffered(false);
		fieldGroup.bindMemberFields(this);
	}
	
	@Init
	public void init(){		
		saveBtn.withData("create");
		
		editBtn.withData("edit")
		.disable();
		
		deleteBtn.withData("delete")
		.disable();
		
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
		editBtn.setEnabled(businessGroup.getBusinessGroupId() != null);
		deleteBtn.setEnabled(businessGroup.getBusinessGroupId() != null);
	}
	
	private class AdmBusinessGroupViewEventListener implements ClickListener, PageDataTarget<BusinessGroupDTO>{
		@Override public void buttonClick(ClickEvent event) {
			if(confirmMsgDialogMapping.containsKey(event.getButton()))
				confirmMsgDialogMapping.get(event.getButton()).show();
		}

		@Override
		public void refreshPageData(List<BusinessGroupDTO> pageData) {
			if(pageData.isEmpty()) return;
			VWebUtils.resetBeanItemDS(fieldGroup.getItemDataSource(), pageData.get(0));
			toggleActionButtonsState();
		}
		
	}
}
