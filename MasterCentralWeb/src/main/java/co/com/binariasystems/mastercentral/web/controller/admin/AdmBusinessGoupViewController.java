package co.com.binariasystems.mastercentral.web.controller.admin;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.binariasystems.fmw.annotation.Dependency;
import co.com.binariasystems.fmw.exception.FMWUncheckedException;
import co.com.binariasystems.fmw.security.auditory.AuditoryDataProvider;
import co.com.binariasystems.fmw.util.pagination.ListPage;
import co.com.binariasystems.fmw.vweb.mvp.annotation.Init;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewController;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewController.OnLoad;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewController.OnUnLoad;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewField;
import co.com.binariasystems.fmw.vweb.mvp.controller.AbstractViewController;
import co.com.binariasystems.fmw.vweb.uicomponet.FormPanel;
import co.com.binariasystems.fmw.vweb.uicomponet.FormValidationException;
import co.com.binariasystems.fmw.vweb.uicomponet.MessageDialog;
import co.com.binariasystems.fmw.vweb.uicomponet.Pager;
import co.com.binariasystems.fmw.vweb.uicomponet.builders.ButtonBuilder;
import co.com.binariasystems.fmw.vweb.uicomponet.pager.PageChangeEvent;
import co.com.binariasystems.fmw.vweb.uicomponet.pager.PageChangeHandler;
import co.com.binariasystems.fmw.vweb.util.VWebUtils;
import co.com.binariasystems.mastercentral.shared.business.bean.BusinessGroupBean;
import co.com.binariasystems.mastercentral.shared.business.dto.BusinessGroupDTO;
import co.com.binariasystems.orion.model.dto.AccessTokenDTO;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@ViewController
public class AdmBusinessGoupViewController extends AbstractViewController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdmBusinessGoupViewController.class);
	@ViewField private FormPanel form;
	@ViewField private ButtonBuilder saveBtn;
	@ViewField private ButtonBuilder editBtn;
	@ViewField private ButtonBuilder deleteBtn;
	@ViewField private ButtonBuilder searchBtn;
	@ViewField private ButtonBuilder cleanBtn;
	@ViewField private Pager<BusinessGroupDTO, BusinessGroupDTO> pager;
	@ViewField private Map<String, String>	notificationMsgMapping;
	@ViewField private Map<Button, MessageDialog> confirmMsgDialogMapping;
    
	@ViewField private BeanFieldGroup<BusinessGroupDTO> fieldGroup;
	@ViewField private BusinessGroupDTO businessGroup;
	
	@Dependency
	private BusinessGroupBean businessGroupBean;
	@Dependency
	private AuditoryDataProvider<AccessTokenDTO> auditoryDataProvider;
	
	private AdmBusinessGoupViewEventListener eventListener;
	
	
	@Init
	public void init(){
		eventListener = new AdmBusinessGoupViewEventListener();
		searchBtn.addClickListener(eventListener);
		cleanBtn.addClickListener(eventListener);
		pager.setPageChangeHandler(eventListener);
		for(MessageDialog messageDialog : confirmMsgDialogMapping.values())
			messageDialog.addYesClickListener(eventListener);
	}
	
	@OnLoad
	public void onLoad(){
		findAll();
		form.initFocus();
	}
	
	@OnUnLoad
	public void onUnLoad(){
		cleanForm();
	}
	
	private void cleanForm(){
		VWebUtils.resetBeanItemDS(fieldGroup.getItemDataSource(), null);
		form.initFocus();
	}
	
	private void findAll(){
		pager.setFilterDto(null);
	}
	
	private void saveBtnClick() throws FormValidationException{
		form.validate();
		businessGroup.setBusinessGroupId(null);
		setAuditoryData(true);
		BusinessGroupDTO saved = businessGroupBean.save(businessGroup);
		findAll();
		VWebUtils.resetBeanItemDS(fieldGroup.getItemDataSource(), saved);
		showSuccessOperationNotification((String)saveBtn.getData());
	}
	
	private void editBtnClick() throws FormValidationException{
		form.validate();
		setAuditoryData(false);
		BusinessGroupDTO saved = businessGroupBean.save(businessGroup);
		findAll();
		VWebUtils.resetBeanItemDS(fieldGroup.getItemDataSource(), saved);
		showSuccessOperationNotification((String)editBtn.getData());
	}
	
	private void deleteBtnClick(){
		businessGroupBean.delete(businessGroup);
		findAll();
		cleanForm();
		showSuccessOperationNotification((String)deleteBtn.getData());
	}
	
	private void searchBtnClick(){
		pager.setFilterDto(businessGroup);
	}
	
	private void cleanBtnClick(){
		cleanForm();
	}
	
	private void setAuditoryData(boolean isNew){
		AccessTokenDTO securityPrincipal = auditoryDataProvider.getCurrenAuditoryUser(VWebUtils.getCurrentHttpRequest());
		if(isNew){
			businessGroup.setCreationUser(securityPrincipal.getUser().getLoginAlias());
			businessGroup.setCreationDate(auditoryDataProvider.getCurrentDate());
		}
		businessGroup.setModificationUser(securityPrincipal.getUser().getLoginAlias());
		businessGroup.setModificationDate(auditoryDataProvider.getCurrentDate());
	}
	
	private ListPage<BusinessGroupDTO> pagerLoadPage(PageChangeEvent<BusinessGroupDTO> event){
		return businessGroupBean.findAll(event.getFilterDTO(), event.getPage(), event.getRowsByPage() * event.getPagesPerGroup());
	}
	
	private void toggleActionButtonsState(){
		editBtn.setEnabled(businessGroup.getBusinessGroupId() != null);
		deleteBtn.setEnabled(businessGroup.getBusinessGroupId() != null);
	}
	
	private void showSuccessOperationNotification(String action){
		new Notification(FontAwesome.THUMBS_UP.getHtml(), notificationMsgMapping.get(action), Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
	}
	
	private class AdmBusinessGoupViewEventListener implements ClickListener, PageChangeHandler<BusinessGroupDTO, BusinessGroupDTO>{
		@Override public void buttonClick(ClickEvent event) {
			try{
				if(confirmMsgDialogMapping.get(saveBtn).yesButton().equals(event.getButton()))
					saveBtnClick();
				if(confirmMsgDialogMapping.get(editBtn).yesButton().equals(event.getButton()))
					editBtnClick();
				if(confirmMsgDialogMapping.get(deleteBtn).yesButton().equals(event.getButton()))
					deleteBtnClick();
				if(searchBtn.equals(event.getButton()))
					searchBtnClick();
				if(cleanBtn.equals(event.getButton()))
					cleanBtnClick();
			}catch(Exception ex){
				handleError(ex, LOGGER);
			}
			finally{
				toggleActionButtonsState();
			}
		}

		@Override
		public ListPage<BusinessGroupDTO> loadPage(PageChangeEvent<BusinessGroupDTO> event) throws FMWUncheckedException {
			return pagerLoadPage(event);
		}
		
	}
}
