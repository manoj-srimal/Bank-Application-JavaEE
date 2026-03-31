package lk.assignment.bankapp.core.mail;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;

import java.util.Base64;

public class VerificationMail extends Mailable{
    private String to;
    private String verificationCode;

    public VerificationMail(String to, String verificationCode) {
        this.to = to;
        this.verificationCode = verificationCode;
    }

    // Inside your VerificationMail.java class

    @Override
    public void build(Message message) throws Exception {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Verify Your Bank Account");


        String link = "http://localhost:8080/bank-application/verify?vc=" + verificationCode;


        String htmlContent = "<body style=\"margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;\">"
                + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "        <tr>"
                + "            <td style=\"padding: 20px 0;\">"
                + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse; border: 1px solid #cccccc; background-color: #ffffff;\">"
                + "                    "
                + "                    <tr>"
                + "                        <td align=\"center\" style=\"background-color: #0d6efd; padding: 30px 0; color: #ffffff; font-size: 28px; font-weight: bold;\">"
                + "                            MyBank Account Verification"
                + "                        </td>"
                + "                    </tr>"
                + "                    "
                + "                    <tr>"
                + "                        <td style=\"padding: 40px 30px;\">"
                + "                            <h1 style=\"font-size: 24px; margin: 0;\">Welcome!</h1>"
                + "                            <p style=\"font-size: 16px; line-height: 1.5; margin: 20px 0;\">"
                + "                                Thank you for registering with MyBank. To complete your registration and activate your account, please click the button below."
                + "                            </p>"
                + "                            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "                                <tr>"
                + "                                    <td align=\"center\">"
                + "                                        "
                + "                                        <a href=\"" + link + "\" target=\"_blank\" style=\"background-color: #0d6efd; color: #ffffff; padding: 15px 25px; text-decoration: none; border-radius: 5px; display: inline-block; font-size: 16px; font-weight: bold;\">"
                + "                                            Verify My Account"
                + "                                        </a>"
                + "                                    </td>"
                + "                                </tr>"
                + "                            </table>"
                + "                            <p style=\"font-size: 14px; line-height: 1.5; margin: 30px 0 0;\">"
                + "                                If the button above doesn't work, please copy and paste the following link into your web browser:"
                + "                                <br>"
                + "                                <a href=\"" + link + "\" target=\"_blank\" style=\"color: #0d6efd;\">" + link + "</a>"
                + "                            </p>"
                + "                        </td>"
                + "                    </tr>"
                + "                    "
                + "                    <tr>"
                + "                        <td align=\"center\" style=\"background-color: #eeeeee; padding: 20px 30px; color: #666666; font-size: 12px;\">"
                + "                            <p>If you did not create an account, no further action is required.</p>"
                + "                            <p>&copy; 2025 MyBank. All rights reserved.</p>"
                + "                        </td>"
                + "                    </tr>"
                + "                </table>"
                + "            </td>"
                + "        </tr>"
                + "    </table>"
                + "</body>";


        message.setContent(htmlContent, "text/html; charset=utf-8");
    }
}