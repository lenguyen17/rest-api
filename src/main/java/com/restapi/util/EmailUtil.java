package com.restapi.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailUtil {
    @Autowired
    private static JavaMailSender emailSender;

    public String sendSimpleMessage(
            String to, String subject, String content) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try{
            log.info("Start send email");
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("noreply@hopee.com.vn");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            emailSender.send(mimeMessage);
            log.info("Send email to " + to);
            return "success";
        }catch (MessagingException | MailException e) {
            log.warn(e.getMessage());
            return "error";
        }
    }

    public String getTemplateBirthday(String name){
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html dir=\"ltr\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "    <title></title>\n" +
                "    <!--[if (mso 16)]>\n" +
                "    <style type=\"text/css\">\n" +
                "    a {text-decoration: none;}\n" +
                "    </style>\n" +
                "    <![endif]-->\n" +
                "    <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]-->\n" +
                "    <!--[if gte mso 9]>\n" +
                "<xml>\n" +
                "    <o:OfficeDocumentSettings>\n" +
                "    <o:AllowPNG></o:AllowPNG>\n" +
                "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "    </o:OfficeDocumentSettings>\n" +
                "</xml>\n" +
                "<![endif]-->\n" +
                "    <!--[if !mso]><!-- -->\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i\" rel=\"stylesheet\">\n" +
                "    <!--<![endif]-->\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div dir=\"ltr\" class=\"es-wrapper-color\">\n" +
                "        <!--[if gte mso 9]>\n" +
                "\t\t\t<v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\n" +
                "\t\t\t\t<v:fill type=\"tile\" color=\"#f6f6f6\"></v:fill>\n" +
                "\t\t\t</v:background>\n" +
                "\t\t<![endif]-->\n" +
                "        <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td class=\"esd-email-paddings\" valign=\"top\">\n" +
                "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content esd-header-popover\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"es-adaptive esd-stripe\" esd-custom-block-id=\"3705\" align=\"center\">\n" +
                "                                        <table class=\"es-content-body\" style=\"background-color: transparent;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p10\" esd-general-paddings-checked=\"true\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"580\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td align=\"center\" class=\"es-infoblock esd-block-text\">\n" +
                "                                                                                        <p>Put your preheader text here. <a href=\"https://viewstripo.email/\" class=\"view\" target=\"_blank\">View in browser</a></p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-header\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"3706\" align=\"center\">\n" +
                "                                        <table class=\"es-header-body\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p10t es-p10b es-p10r es-p10l\" esd-general-paddings-checked=\"true\" align=\"left\">\n" +
                "                                                        <!--[if mso]><table width=\"580\" cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"280\" valign=\"top\"><![endif]-->\n" +
                "                                                        <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"es-m-p20b esd-container-frame\" width=\"280\" align=\"left\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-image\" align=\"left\" style=\"font-size:0\"><a href=\"https://viewstripo.email/\" target=\"_blank\"><img src=\"https://tlr.stripocdn.email/content/guids/CABINET_3fbb8bf308f8e498e4c46325acba2e8e/images/35881510837185621.png\" alt=\"Real Estate logo\" title=\"Real Estate logo\" width=\"165\"></a></td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                        <!--[if mso]></td><td width=\"20\"></td><td width=\"280\" valign=\"top\"><![endif]-->\n" +
                "                                                        <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"280\" align=\"left\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-p10t\" align=\"right\">\n" +
                "                                                                                        <p><a target=\"_blank\" href=\"tel:123456789\">123456789</a></p>\n" +
                "                                                                                        <p><a target=\"_blank\" href=\"mailto:your@mail.com\">your@mail.com</a></p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                        <!--[if mso]></td></tr></table><![endif]-->\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                        <table class=\"es-content esd-footer-popover\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"esd-stripe\" esd-custom-block-id=\"3707\" align=\"center\">\n" +
                "                                        <table class=\"es-content-body\" style=\"background-color: #bef6ff;\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#bef6ff\" align=\"center\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"600\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-banner\" style=\"position: relative;\" align=\"center\" esdev-config=\"h13\"><a target=\"_blank\" href=\"https://viewstripo.email/\"><img class=\"adapt-img esdev-stretch-width esdev-banner-rendered\" src=\"https://demo.stripocdn.email/content/guids/bannerImgGuid/images/image17028302196662056.png\" alt=\"Happy Hanukkah from Real Estate company\" title=\"Happy Hanukkah from Real Estate company\" width=\"600\" style=\"display: block;\"></a></td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure es-p10t es-p30b es-p40r es-p40l\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"520\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-p10t es-p10r es-p10l\" align=\"center\">\n" +
                "                                                                                        <p style=\"font-family: lora, georgia, times new roman, serif; color: #24bad3; font-size: 16px;\"><em>Dear</em>&nbsp;&nbsp;"+name+"</p>\n" +
                "                                                                                        <p style=\"font-family: lora, georgia, times new roman, serif; color: #24bad3; font-size: 16px;\"><em>May this Festival of Lights bring blessings upon you and all your loved ones for happiness, for health, and for spiritual and material wealth, and may the lights of Chanukah usher in the light of Moshiach and a better world for all of humankind.</em></p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-text es-p15t es-p10r es-p10l\" align=\"center\">\n" +
                "                                                                                        <p style=\"font-family: lora, georgia, times new roman, serif; color: #24bad3; font-size: 16px;\"><em>&nbsp;In warm appreciation of our association, we extend our best wishes for a happy holiday season and give you a small gift!</em></p>\n" +
                "                                                                                    </td>\n" +
                "                                                                                </tr>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-button es-p20t es-p10r es-p10l\" align=\"center\"><span class=\"es-button-border\" style=\"background: #bef6ff;\"><a href=\"https://viewstripo.email/\" class=\"es-button\" target=\"_blank\" style=\"color: #24bad3; padding: 10px 15px; background: #bef6ff; mso-border-alt: 10px solid #bef6ff\">Click Here</a></span></td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td class=\"esd-structure\" style=\"background-color: #54c6d1;\" bgcolor=\"#54c6d1\" align=\"left\">\n" +
                "                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td class=\"esd-container-frame\" width=\"600\" valign=\"top\" align=\"center\">\n" +
                "                                                                        <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                                                            <tbody>\n" +
                "                                                                                <tr>\n" +
                "                                                                                    <td class=\"esd-block-image es-p15b\" align=\"center\" style=\"font-size: 0px;\"><a target=\"_blank\"><img class=\"adapt-img\" src=\"https://tlr.stripocdn.email/content/guids/CABINET_6e83b30ec4e10f3ca6377fa4e44b8eda/images/847915130638433562_2Lp.gif\" alt width=\"600\" style=\"display: block;\"></a></td>\n" +
                "                                                                                </tr>\n" +
                "                                                                            </tbody>\n" +
                "                                                                        </table>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "    <div class=\"ddict_btn\" style=\"top: 795px; left: 584.523px;\"><img src=\"chrome-extension://bpggmmljdiliancllaapiggllnkbjocb/logo/48.png\"></div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }
}
