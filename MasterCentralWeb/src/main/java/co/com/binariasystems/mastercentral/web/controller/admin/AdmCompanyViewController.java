package co.com.binariasystems.mastercentral.web.controller.admin;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.binariasystems.commonsmodel.enumerated.PayrollPeriodType;
import co.com.binariasystems.fmw.annotation.Dependency;
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
import co.com.binariasystems.mastercentral.shared.business.bean.CompanyBean;
import co.com.binariasystems.mastercentral.shared.business.dto.CompanyDTO;
import co.com.binariasystems.mastercentral.shared.business.utils.Utils;
import co.com.binariasystems.orion.model.dto.AccessTokenDTO;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@ViewController
public class AdmCompanyViewController extends AbstractViewController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdmCompanyViewController.class);
	@ViewField private FormPanel form;
	@ViewField private ButtonBuilder saveBtn;
	@ViewField private ButtonBuilder editBtn;
	@ViewField private ButtonBuilder deleteBtn;
	@ViewField private ButtonBuilder searchBtn;
	@ViewField private ButtonBuilder cleanBtn;
	@ViewField private Pager<CompanyDTO, CompanyDTO> pager;
	@ViewField private Map<String, String>	notificationMsgMapping;
	@ViewField private Map<Button, MessageDialog> confirmMsgDialogMapping;
	@ViewField private ComboBox payrollPeriodTypeCmb;
	@ViewField private Grid companiesGrid;
    
	@ViewField private BeanFieldGroup<CompanyDTO> fieldGroup;
	@ViewField private CompanyDTO company;
	
	@Dependency
	private CompanyBean companyBean;
	@Dependency
	private AuditoryDataProvider<AccessTokenDTO> auditoryDataProvider;
	private AdmCompanyViewEventListener eventListener;
	
	@Init
	public void init(){
		eventListener = new AdmCompanyViewEventListener();
		searchBtn.addClickListener(eventListener);
		cleanBtn.addClickListener(eventListener);
		pager.setPageChangeHandler(eventListener);
		companiesGrid.addSelectionListener(eventListener);
		for(MessageDialog messageDialog : confirmMsgDialogMapping.values())
			messageDialog.addYesClickListener(eventListener);
	}
	
	@OnLoad
	public void onLoad(){
		payrollPeriodTypeCmb.addItems(PayrollPeriodType.values());
		findAll();
		form.initFocus();
	}
	@OnUnLoad
	public void onUnLoad(){
		cleanForm();
		payrollPeriodTypeCmb.removeAllItems();
		toggleActionButtonsState();
	}
	
	private void findAll(){
		pager.setFilterDto(new CompanyDTO());
	}
	
	private void cleanForm(){
		VWebUtils.resetBeanItemDS(fieldGroup.getItemDataSource(), null);
	}
	
	private void saveBtnClick() throws FormValidationException {
		form.validate();
		company.setCompanyId(null);
		setAuditoryData(true);
		CompanyDTO saved = companyBean.save(company);
		findAll();
		VWebUtils.resetBeanItemDS(fieldGroup.getItemDataSource(), saved);
		showSuccessOperationNotification((String)saveBtn.getData());
	}
	
	private void editBtnClick() throws FormValidationException {
		form.validate();
		setAuditoryData(false);
		CompanyDTO saved = companyBean.save(company);
		findAll();
		VWebUtils.resetBeanItemDS(fieldGroup.getItemDataSource(), saved);
		showSuccessOperationNotification((String)editBtn.getData());
	}
	
	private void deleteBtnClick() {
		companyBean.delete(company);
		cleanForm();
		findAll();
		showSuccessOperationNotification((String)deleteBtn.getData());
	}
	
	private void searchBtnClick() {
		pager.setFilterDto(company);
	}

	private void cleanBtnClick() {
		cleanForm();
	}
	
	private void companiesGridSelect(){
		VWebUtils.resetBeanItemDS(fieldGroup.getItemDataSource(), (CompanyDTO)companiesGrid.getSelectedRow());
		toggleActionButtonsState();
	}
	
	private void setAuditoryData(boolean isNew){
		AccessTokenDTO securityPrincipal = auditoryDataProvider.getCurrenAuditoryUser(VWebUtils.getCurrentHttpRequest());
		if(isNew){
			company.setCreationUser(securityPrincipal.getUser().getLoginAlias());
			company.setCreationDate(auditoryDataProvider.getCurrentDate());
		}
		company.setModificationUser(securityPrincipal.getUser().getLoginAlias());
		company.setModificationDate(auditoryDataProvider.getCurrentDate());
	}
	
	private ListPage<CompanyDTO> pagerLoadPage(PageChangeEvent<CompanyDTO> event) {
		return Utils.pageToListPage(companyBean.findAll(event.getFilterDTO(), event.getPageRequest()));
	}
	
	private void toggleActionButtonsState(){
		editBtn.setEnabled(company.getCompanyId() != null);
		deleteBtn.setEnabled(company.getCompanyId() != null);
	}
	
	private void showSuccessOperationNotification(String action){
		new Notification(FontAwesome.THUMBS_UP.getHtml(), notificationMsgMapping.get(action), Type.TRAY_NOTIFICATION, true).show(Page.getCurrent());
	}
	
	private class AdmCompanyViewEventListener implements ClickListener, SelectionListener, PageChangeHandler<CompanyDTO, CompanyDTO>{
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
		public ListPage<CompanyDTO> loadPage(PageChangeEvent<CompanyDTO> event){
			return event.getFilterDTO() != null ? pagerLoadPage(event) : new ListPage<CompanyDTO>();
		}
		@Override public void select(SelectionEvent event) {
			if(companiesGrid.equals(event.getSource()))
				companiesGridSelect();
		}
		
	}
}
