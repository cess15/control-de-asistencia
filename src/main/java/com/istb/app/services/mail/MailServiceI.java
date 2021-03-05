
package com.istb.app.services.mail;

import java.util.Map;

public interface MailServiceI {

	/**
	 * Envia un correo haciendo uso de un mensaje en texto plano
	 * <p>
	 * El cuerpo del mensaje es una cadena de caracteres
	 * @param messaje texto del mensaje
	 * @param email correo destinatario
	 * @param subject asunto del correo
	 */
	void sendEmailMessage(String message, String email, String subject);

	/**
	 * Envia un correo haciendo uso de una plantilla html
	 * <p>
	 * El cuerpo del mensaje es una plantilla definida por el usuario en la
	 * carpeta templates/notifications, será renderizada con Thymeleaf
	 * @param template nombre del template sin la extension .html
	 * @param email correo destinatario
	 * @param subject asunto del correo
	 */
	void sendEmailTemplate(String template, String email, String subject);

	/**
	 * Envia un correo haciendo uso de una plantilla html
	 * a la que se pueden vincular datos
	 * <p>
	 * El cuerpo del mensaje es una plantilla definida por el usuario en la
	 * carpeta templates/notifications, será renderizada con Thymeleaf
	 * @param template nombre del template sin la extension .html
	 * @param data coleccion clave-valor con los datos vinculados al template
	 * @param email correo destinatario
	 * @param subject asunto del correo
	 */
	void sendEmailTemplate(String template, 
    Map<String, Object> data, String email, String subject);
    
} 
