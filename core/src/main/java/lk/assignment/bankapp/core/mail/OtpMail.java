package lk.assignment.bankapp.core.mail;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;


public class OtpMail extends Mailable {

    private String to;
    private String otp;

    public OtpMail(String to, String otp) {
        this.to = to;
        this.otp = otp;
    }

    @Override
    public void build(Message message) throws Exception {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Your One-Time Password (OTP) for MyBank");

        String htmlContent = "<body style=\"margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;\">"
                + "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "        <tr>"
                + "            <td style=\"padding: 20px 0;\">"
                + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse; border: 1px solid #cccccc; background-color: #ffffff;\">"
                + "                    "
                + "                    <tr>"
                + "                        <td align=\"center\" style=\"background-color: #198754; padding: 30px 0; color: #ffffff; font-size: 28px; font-weight: bold;\">"
                + "                            MyBank Secure Login"
                + "                        </td>"
                + "                    </tr>"
                + "                    "
                + "                    <tr>"
                + "                        <td style=\"padding: 40px 30px;\">"
                + "                            <h1 style=\"font-size: 24px; margin: 0;\">Your One-Time Password</h1>"
                + "                            <p style=\"font-size: 16px; line-height: 1.5; margin: 20px 0;\">"
                + "                                Please use the following code to complete your login. This code is valid for 5 minutes."
                + "                            </p>"
                + "                            "
                + "                            <div style=\"text-align: center; padding: 20px; background-color: #e9ecef; border-radius: 5px; margin: 20px 0;\">"
                + "                                <p style=\"font-size: 32px; font-weight: bold; letter-spacing: 5px; margin: 0; color: #333;\">"
                + "                                    __OTP_CODE__"
                + "                                </p>"
                + "                            </div>"
                + "                            <p style=\"font-size: 14px; line-height: 1.5; text-align: center; color: #dc3545; font-weight: bold;\">"
                + "                                For your security, please do not share this code with anyone."
                + "                            </p>"
                + "                        </td>"
                + "                    </tr>"
                + "                    "
                + "                    <tr>"
                + "                        <td align=\"center\" style=\"background-color: #eeeeee; padding: 20px 30px; color: #666666; font-size: 12px;\">"
                + "                            <p>If you did not request this code, you can safely ignore this email.</p>"
                + "                            <p>&copy; 2025 MyBank. All rights reserved.</p>"
                + "                        </td>"
                + "                    </tr>"
                + "                </table>"
                + "            </td>"
                + "        </tr>"
                + "    </table>"
                + "</body>";

        String finalHtml = htmlContent.replace("__OTP_CODE__", this.otp);

        message.setContent(finalHtml, "text/html; charset=utf-8");
    }
}