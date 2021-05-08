package core.adapters;

import outlookAuth.OutlookAuthManager;

public class OutlookAuthManagerAdapter implements AuthServiceAdapter {

	@Override
	public void login(String email) {
		
		OutlookAuthManager outlookAuthManager = new OutlookAuthManager();
		outlookAuthManager.login(email);
		
	}

}
