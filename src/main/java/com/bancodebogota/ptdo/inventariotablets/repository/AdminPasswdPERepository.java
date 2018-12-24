package com.bancodebogota.ptdo.inventariotablets.repository;

import java.util.HashMap;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author drojas6
 *
 */
@Repository
public class AdminPasswdPERepository {

	/**
	 * 
	 */
	@Value("${app.pe.urlrest-admin-password}")
	private String urlWSRestAdminPw;

	/**
	 * @param login
	 * @param domain
	 * @return
	 */
	public HashMap<String, String> getPasswordAdminPasswordPE(String login, String domain) {
		return ClientBuilder.newClient().target(urlWSRestAdminPw).path(login).path(domain)
				.request(MediaType.APPLICATION_JSON).get(new GenericType<HashMap<String, String>>() {
				});
	}

}
