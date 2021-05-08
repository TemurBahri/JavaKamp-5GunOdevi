package core.emailVerification;

public class EmailManager implements EmailService {

	@Override
	public void sendVerificationMail(String email) {
		System.out.println(email + " email adresine doğrulama gönderildi");
		
	}

	@Override
	public boolean isVerified(String option) {
		
		if (option.equals("Doğrula")) {
			System.out.println("E-mail adresiniz doğrulandı.");
			return true;
		}
		System.out.println("E-mail adresiniz doğrulanamadı!");
		return false;
		
		
		
		
	}

}
