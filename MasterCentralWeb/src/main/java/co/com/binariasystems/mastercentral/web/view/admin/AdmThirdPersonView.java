package co.com.binariasystems.mastercentral.web.view.admin;

import co.com.binariasystems.fmw.vweb.mvp.annotation.Init;
import co.com.binariasystems.fmw.vweb.mvp.annotation.View;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewBuild;
import co.com.binariasystems.fmw.vweb.mvp.views.AbstractView;
import co.com.binariasystems.fmw.vweb.uicomponet.FormPanel;
import co.com.binariasystems.mastercentral.web.controller.admin.AdmThirdPersonViewController;

import com.vaadin.ui.Component;

@View(url="/thirdPerson", viewStringsByConventions= true,
controller=AdmThirdPersonViewController.class)
public class AdmThirdPersonView extends AbstractView{
	private FormPanel form;
	
	private AdmThirdPersonViewEventListener eventListener;
	
	@ViewBuild
	public Component build(){
		
		return form;
	}
	
	private void addDataBinding(){
		eventListener = new AdmThirdPersonViewEventListener();
	}
	
	@Init
	public void init(){
		
	}
	
	private class AdmThirdPersonViewEventListener {
		
	}
}
