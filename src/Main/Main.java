package Main;

import business.abstracts.AuthService;
import business.concretes.AuthManager;
import business.concretes.UserManager;

import core.adapters.OutlookAuthManagerAdapter;
import core.emailVerification.EmailManager;
import dataAccess.concretes.InMemoryUserDao;


public class Main {
	
	public static void main(String[] args) {
		AuthService authService = new AuthManager(new UserManager(new InMemoryUserDao()),
				new OutlookAuthManagerAdapter(), new EmailManager());

		System.out.println("E-Ticaret Sistemi");
		
		System.out.println("----------------------------------");

		System.out.println("Outlook hesabıyla yapıldı");
		String outlookAccount = "timur@outlook.com";
		authService.loginWithOutlook(outlookAccount);
		
		
		
		System.out.println("----------------------------------");

		System.out.println("E-Ticaret Sistemine Kullanıcı Oluşturma");
		authService.register(1, "Timur", "Bahri", "timur@timur.com", "123456789");
		
		System.out.println("E - Ticaret Sistemine Giriş");
		authService.login("timur@timur.com", "123456789");
		
		
	}

}
