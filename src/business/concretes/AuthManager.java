package business.concretes;

import java.util.Scanner;
import java.util.regex.Pattern;

import business.abstracts.AuthService;
import business.abstracts.UserService;
import core.adapters.AuthServiceAdapter;
import core.emailVerification.EmailService;
import entities.concretes.User;

public class AuthManager implements AuthService {
	
	private UserService userService;
	private AuthServiceAdapter authServiceAdapter;
	private EmailService emailService;
	
	
	public AuthManager(UserService userService, AuthServiceAdapter authServiceAdapter, EmailService emailService) {
		this.userService = userService;
		this.authServiceAdapter = authServiceAdapter;
		this.emailService = emailService;
	}

	@Override
	public void register(int id, String firstName, String lastName, String email, String password) {
		
		User registerUser = new User(id,firstName,lastName,email,password);
		
		if (!fillAllInformation(registerUser.getFirstName(), registerUser.getLastName(), registerUser.getEmail(),
				registerUser.getPassword())) {
			return;
		}
		if (!nameLengthValid(registerUser.getFirstName(), registerUser.getLastName())) {
			return;
		} else if (!checkEmailFormat(registerUser.getEmail())) {
			return;
		} else if (!exsistEmail(registerUser.getEmail())) {
			return;
		} else if (!passwordLengthValid(registerUser.getPassword())) {
			return;
		} else {
			this.emailService.sendVerificationMail(registerUser.getEmail());

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.println("E-mail adresinizi 'Doğrula' yazarak doğrulayabilirsiniz.");
			String option = scanner.nextLine();

			if (this.emailService.isVerified(option)) {
				this.userService.add(registerUser);
			} else {
				System.out.println("Kayıt işlemi başarısız!");
			}
		}
	}
		
		
	

	@Override
	public void login(String email, String password) {
		
		User loginUser = userService.getUserByEmail(email);
		if (loginUser == null) {
			System.out.println("Bu mail adresiyle kullanıcı bulunamadı!");
		} else if (email.length() <= 0 || password.length() <= 0) {
			System.out.println("Tüm alanlar doldurulmalıdır!");
		} else if (loginUser.getPassword() != password) {
			System.out.println("Þifre hatalý!");
		} else {
			System.out.println(
					"Sisteme başarıyla giriş yapıldı: " + loginUser.getFirstName() + " " + loginUser.getLastName());
		}
		
		
	}

	@Override
	public void loginWithOutlook(String email) {
		
		authServiceAdapter.login(email);
	}
	
	public boolean nameLengthValid(String firstName, String lastName) {
		if (firstName.length() >= 2 && lastName.length() >= 2) {
			return true;
		}
		System.out.println("Ad ve soyadınız en az 2 karakter olmalı");
		return false;
	}

	public boolean checkEmailFormat(String email) {
		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(regex);

		if (pattern.matcher(email).matches()) {
			return true;
		}
		System.out.println("Mail adresi geçersiz!");
		return false;
	}

	public boolean exsistEmail(String mail) {
		if (userService.getUserByEmail(mail) == null) {
			return true;
		}
		System.out.println("Bu mail adresi daha önce kullanıldı!");
		return false;
	}

	public boolean passwordLengthValid(String password) {
		if (password.length() >= 6) {
			return true;
		}
		System.out.println("Şifreniz en az 6 karakter olmalıdır.");
		return false;
	}

	public boolean fillAllInformation(String firstName, String lastName, String email, String password) {
		if (firstName.length() > 0 && lastName.length() > 0 && email.length() > 0 && password.length() > 0) {
			return true;
		}
		System.out.println("Tüm alanlar doldurulmalıdır!");
		return false;
	}
}
