package co.com.binariasystems.mastercentral.web.view.admin;

import co.com.binariasystems.fmw.vweb.mvp.annotation.Init;
import co.com.binariasystems.fmw.vweb.mvp.annotation.View;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewBuild;
import co.com.binariasystems.fmw.vweb.mvp.views.AbstractView;
import co.com.binariasystems.fmw.vweb.uicomponet.FormPanel;
import co.com.binariasystems.mastercentral.web.controller.admin.AdmCompanyViewController;

import com.vaadin.ui.Component;

@View(url="/company", viewStringsByConventions= true,
controller=AdmCompanyViewController.class)
public class AdmCompanyView extends AbstractView{
	private FormPanel form;
	
	private AdmCompanyViewEventListener eventListener;
	
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
