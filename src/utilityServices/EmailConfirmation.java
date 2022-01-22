package utilityServices;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailConfirmation {

	public static String genererCode() {
		Random r  = new Random();
		StringBuilder code =  new StringBuilder();
		code.append(String.valueOf((char) (r.nextInt(26) + 'a')));
		code.append(r.nextInt(9));
		code.append((char) (r.nextInt(26) + 'a'));
		code.append(r.nextInt(9));
		code.append((char) (r.nextInt(26) + 'a'));
		code.append(r.nextInt(9));

		return code.toString();
	}

	public static boolean envoiEmail(String recipient,String confirmationCode) {

		boolean send =  false;
		final String senderEmail  = "Jeeprojectemailadress@gmail.com";
		final String senderPassword =  "@j2eproject/";
		String body =  getEmailTemplate(confirmationCode);

		Properties prop  = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session =  Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});
		try {
			Message message  = new MimeMessage(session);
			message.setFrom(new InternetAddress("ProjetJavaEE"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject("Email de cofirmation");
			message.setContent(body,"text/html");
			Transport.send(message);
			send =  true;
		} catch (MessagingException e) {
			e.printStackTrace();
			send  = false;
		}

		return send;
	}
	public static String getEmailTemplate(String code) {
		String template = "<!doctype html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "<meta name=\"viewport\" content=\"width=device-width\" />\r\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n"
				+ "<title>Simple Transactional Email</title>\r\n"
				+ "<style>\r\n"
				+ "img {\r\n"
				+ "	border: none;\r\n"
				+ "	-ms-interpolation-mode: bicubic;\r\n"
				+ "	max-width: 100%;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "body {\r\n"
				+ "	background-color: #f6f6f6;\r\n"
				+ "	font-family: sans-serif;\r\n"
				+ "	-webkit-font-smoothing: antialiased;\r\n"
				+ "	font-size: 14px;\r\n"
				+ "	line-height: 1.4;\r\n"
				+ "	margin: 0;\r\n"
				+ "	padding: 0;\r\n"
				+ "	-ms-text-size-adjust: 100%;\r\n"
				+ "	-webkit-text-size-adjust: 100%;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "table {\r\n"
				+ "	border-collapse: separate;\r\n"
				+ "	mso-table-lspace: 0pt;\r\n"
				+ "	mso-table-rspace: 0pt;\r\n"
				+ "	width: 100%;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "table td {\r\n"
				+ "	font-family: sans-serif;\r\n"
				+ "	font-size: 14px;\r\n"
				+ "	vertical-align: top;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".body {\r\n"
				+ "	background-color: #f6f6f6;\r\n"
				+ "	width: 100%;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".container {\r\n"
				+ "	display: block;\r\n"
				+ "	margin: 0 auto !important;\r\n"
				+ "	max-width: 580px;\r\n"
				+ "	padding: 10px;\r\n"
				+ "	width: 580px;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".content {\r\n"
				+ "	box-sizing: border-box;\r\n"
				+ "	display: block;\r\n"
				+ "	margin: 0 auto;\r\n"
				+ "	max-width: 580px;\r\n"
				+ "	padding: 10px;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "\r\n"
				+ ".main {\r\n"
				+ "	background: #ffffff;\r\n"
				+ "	border-radius: 3px;\r\n"
				+ "	width: 100%;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".wrapper {\r\n"
				+ "	box-sizing: border-box;\r\n"
				+ "	padding: 20px;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".content-block {\r\n"
				+ "	padding-bottom: 10px;\r\n"
				+ "	padding-top: 10px;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".footer {\r\n"
				+ "	clear: both;\r\n"
				+ "	margin-top: 10px;\r\n"
				+ "	text-align: center;\r\n"
				+ "	width: 100%;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".footer td, .footer p, .footer span, .footer a {\r\n"
				+ "	color: #999999;\r\n"
				+ "	font-size: 12px;\r\n"
				+ "	text-align: center;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "h1, h2, h3, h4 {\r\n"
				+ "	color: #000000;\r\n"
				+ "	font-family: sans-serif;\r\n"
				+ "	font-weight: 400;\r\n"
				+ "	line-height: 1.4;\r\n"
				+ "	margin: 0;\r\n"
				+ "	margin-bottom: 30px;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "h1 {\r\n"
				+ "	font-size: 35px;\r\n"
				+ "	font-weight: 300;\r\n"
				+ "	text-align: center;\r\n"
				+ "	text-transform: capitalize;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "p, ul, ol {\r\n"
				+ "	font-family: sans-serif;\r\n"
				+ "	font-size: 14px;\r\n"
				+ "	font-weight: normal;\r\n"
				+ "	margin: 0;\r\n"
				+ "	margin-bottom: 15px;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "p li, ul li, ol li {\r\n"
				+ "	list-style-position: inside;\r\n"
				+ "	margin-left: 5px;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "a {\r\n"
				+ "	color: #3498db;\r\n"
				+ "	text-decoration: underline;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "\r\n"
				+ ".btn {\r\n"
				+ "	box-sizing: border-box;\r\n"
				+ "	width: 100%;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".btn>tbody>tr>td {\r\n"
				+ "	padding-bottom: 15px;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".btn table {\r\n"
				+ "	width: auto;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".btn table td {\r\n"
				+ "	background-color: #ffffff;\r\n"
				+ "	border-radius: 5px;\r\n"
				+ "	text-align: center;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".btn a {\r\n"
				+ "	background-color: #ffffff;\r\n"
				+ "	border: solid 1px #3498db;\r\n"
				+ "	border-radius: 5px;\r\n"
				+ "	box-sizing: border-box;\r\n"
				+ "	color: #3498db;\r\n"
				+ "	cursor: pointer;\r\n"
				+ "	display: inline-block;\r\n"
				+ "	font-size: 14px;\r\n"
				+ "	font-weight: bold;\r\n"
				+ "	margin: 0;\r\n"
				+ "	padding: 12px 25px;\r\n"
				+ "	text-decoration: none;\r\n"
				+ "	text-transform: capitalize;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".btn-primary table td {\r\n"
				+ "	background-color: #3498db;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".btn-primary a {\r\n"
				+ "	background-color: #3498db;\r\n"
				+ "	border-color: #3498db;\r\n"
				+ "	color: #ffffff;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "\r\n"
				+ ".last {\r\n"
				+ "	margin-bottom: 0;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".first {\r\n"
				+ "	margin-top: 0;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".align-center {\r\n"
				+ "	text-align: center;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".align-right {\r\n"
				+ "	text-align: right;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".align-left {\r\n"
				+ "	text-align: left;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".clear {\r\n"
				+ "	clear: both;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".mt0 {\r\n"
				+ "	margin-top: 0;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".mb0 {\r\n"
				+ "	margin-bottom: 0;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".preheader {\r\n"
				+ "	color: transparent;\r\n"
				+ "	display: none;\r\n"
				+ "	height: 0;\r\n"
				+ "	max-height: 0;\r\n"
				+ "	max-width: 0;\r\n"
				+ "	opacity: 0;\r\n"
				+ "	overflow: hidden;\r\n"
				+ "	mso-hide: all;\r\n"
				+ "	visibility: hidden;\r\n"
				+ "	width: 0;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ ".powered-by a {\r\n"
				+ "	text-decoration: none;\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "hr {\r\n"
				+ "	border: 0;\r\n"
				+ "	border-bottom: 1px solid #f6f6f6;\r\n"
				+ "	margin: 20px 0;\r\n"
				+ "}\r\n"
				+ "@media only screen and (max-width: 620px) {\r\n"
				+ "	table[class=body] h1 {\r\n"
				+ "		font-size: 28px !important;\r\n"
				+ "		margin-bottom: 10px !important;\r\n"
				+ "	}\r\n"
				+ "	table[class=body] p, table[class=body] ul, table[class=body] ol, table[class=body] td,\r\n"
				+ "		table[class=body] span, table[class=body] a {\r\n"
				+ "		font-size: 16px !important;\r\n"
				+ "	}\r\n"
				+ "	table[class=body] .wrapper, table[class=body] .article {\r\n"
				+ "		padding: 10px !important;\r\n"
				+ "	}\r\n"
				+ "	table[class=body] .content {\r\n"
				+ "		padding: 0 !important;\r\n"
				+ "	}\r\n"
				+ "	table[class=body] .container {\r\n"
				+ "		padding: 0 !important;\r\n"
				+ "		width: 100% !important;\r\n"
				+ "	}\r\n"
				+ "	table[class=body] .main {\r\n"
				+ "		border-left-width: 0 !important;\r\n"
				+ "		border-radius: 0 !important;\r\n"
				+ "		border-right-width: 0 !important;\r\n"
				+ "	}\r\n"
				+ "	table[class=body] .btn table {\r\n"
				+ "		width: 100% !important;\r\n"
				+ "	}\r\n"
				+ "	table[class=body] .btn a {\r\n"
				+ "		width: 100% !important;\r\n"
				+ "	}\r\n"
				+ "	table[class=body] .img-responsive {\r\n"
				+ "		height: auto !important;\r\n"
				+ "		max-width: 100% !important;\r\n"
				+ "		width: auto !important;\r\n"
				+ "	}\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "@media all {\r\n"
				+ "	.ExternalClass {\r\n"
				+ "		width: 100%;\r\n"
				+ "	}\r\n"
				+ "	.ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font,\r\n"
				+ "		.ExternalClass td, .ExternalClass div {\r\n"
				+ "		line-height: 100%;\r\n"
				+ "	}\r\n"
				+ "	.apple-link a {\r\n"
				+ "		color: inherit !important;\r\n"
				+ "		font-family: inherit !important;\r\n"
				+ "		font-size: inherit !important;\r\n"
				+ "		font-weight: inherit !important;\r\n"
				+ "		line-height: inherit !important;\r\n"
				+ "		text-decoration: none !important;\r\n"
				+ "	}\r\n"
				+ "	#MessageViewBody a {\r\n"
				+ "		color: inherit;\r\n"
				+ "		text-decoration: none;\r\n"
				+ "		font-size: inherit;\r\n"
				+ "		font-family: inherit;\r\n"
				+ "		font-weight: inherit;\r\n"
				+ "		line-height: inherit;\r\n"
				+ "	}\r\n"
				+ "	.btn-primary table td:hover {\r\n"
				+ "		background-color: #34495e !important;\r\n"
				+ "	}\r\n"
				+ "	.btn-primary a:hover {\r\n"
				+ "		background-color: #34495e !important;\r\n"
				+ "		border-color: #34495e !important;\r\n"
				+ "	}\r\n"
				+ "}\r\n"
				+ "</style>\r\n"
				+ "</head>\r\n"
				+ "<body class=\"\">\r\n"
				+ "	<table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n"
				+ "		class=\"body\">\r\n"
				+ "		<tr>\r\n"
				+ "			<td>&nbsp;</td>\r\n"
				+ "			<td class=\"container\">\r\n"
				+ "				<div class=\"content\">\r\n"
				+ "\r\n"
				+ "					<table role=\"presentation\" class=\"main\">\r\n"
				+ "\r\n"
				+ "						<tr>\r\n"
				+ "							<td class=\"wrapper\">\r\n"
				+ "								<table role=\"presentation\" border=\"0\" cellpadding=\"0\"\r\n"
				+ "									cellspacing=\"0\">\r\n"
				+ "									<tr>\r\n"
				+ "										<td>\r\n"
				+ "											<h2 style=\"text-align:center;\">Message de Confirmation !</h2>\r\n"
				+ "											<p>Bonjour</p>\r\n"
				+ "											<p> Cher(e) utilisateur(rice)<br>\r\n"
				+ "												Pour activer votre compte , nous devons v�rifier votre adresse e-mail.<br> Activez votre compte � l'aide du code ci-dessous.</p>\r\n"
				+ "											<table style=\\\"text-align:center;\\\" role=\"presentation\" border=\"0\" cellpadding=\"0\"\r\n"
				+ "												cellspacing=\"0\" class=\"btn btn-primary\">\r\n"
				+ "												<tbody>\r\n"
				+ "													<tr>\r\n"
				+ "														<td align=\"left\">\r\n"
				+ "															<table role=\"presentation\" border=\"0\" cellpadding=\"0\"\r\n"
				+ "																cellspacing=\"0\">\r\n"
				+ "																<tbody style=\\\"text-align:center;\\\">\r\n"
				+ "																	<tr style=\\\"text-align:center;\\\">\r\n"
				+ "																		<td style=\\\"text-align:center;\\\"><a id=\"code\">"
				+code
				+"</a></td>\r\n"
				+ "																	</tr>\r\n"
				+ "																</tbody>\r\n"
				+ "															</table>\r\n"
				+ "														</td>\r\n"
				+ "													</tr>\r\n"
				+ "												</tbody>\r\n"
				+ "											</table>\r\n"
				+ "											<p>Copiez ce code et collez-le sur la page de v�rification de l'adresse e-mail.<br>\r\n"
				+ "												Merci :-)</p><br><br>\r\n"										
				+ "										</td>\r\n"
				+ "									</tr>\r\n"
				+ "								</table>\r\n"
				+ "							</td>\r\n"
				+ "						</tr>\r\n"
				+ "					</table>\r\n"
				+ "					<div class=\"footer\">\r\n"
				+ "						<table role=\"presentation\" border=\"0\" cellpadding=\"0\"\r\n"
				+ "							cellspacing=\"0\">\r\n"
				+ "							<tr>\r\n"
				+ "								<td class=\"content-block\"><span class=\"apple-link\">&copy; Copyright 2022 &middot; ESP\r\n"
				+ "									Dakar &middot; master 2 GLSI</span> <br>\r\n"
				+ "								</td>\r\n"
				+ "							</tr>\r\n"
				+ "						</table>\r\n"
				+ "					</div>\r\n"
				+ "\r\n"
				+ "				</div>\r\n"
				+ "			</td>\r\n"
				+ "			<td>&nbsp;</td>\r\n"
				+ "		</tr>\r\n"
				+ "	</table>\r\n"
				+ "</body>\r\n"
				+ "</html>\r\n"
				+ "";
		return template;
	}
}
