package co.com.binariasystems.mastercentral.web.controller.admin;

import co.com.binariasystems.fmw.vweb.mvp.annotation.Init;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewController;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewController.OnLoad;
import co.com.binariasystems.fmw.vweb.mvp.annotation.ViewController.OnUnLoad;
import co.com.binariasystems.fmw.vweb.mvp.controller.AbstractViewController;

@ViewController
public class AdmThirdPersonViewController extends AbstractViewController {
	private AdmThirdPersonViewEventListener eventListener;
	
	
	@Init
	public void init(){
		eventListener = new AdmThirdPersonViewEventListener();
	}
	
	@OnLoad
	public void onLoad(){
		
	}
	
	
	@OnUnLoad
	public void onUnLoad(){
		
	}
	
	private class AdmThirdPersonViewEventListener{
		
	}
}
