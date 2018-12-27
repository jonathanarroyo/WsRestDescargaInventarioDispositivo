package com.bancodebogota.ptdo.inventariotablets.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import com.bancodebogota.ptdo.inventariotablets.model.Device;
import com.bancodebogota.ptdo.inventariotablets.model.DeviceRequest;
import com.bancodebogota.ptdo.inventariotablets.model.DeviceResponse;
import com.bancodebogota.ptdo.inventariotablets.model.LoginRequest;
import com.bancodebogota.ptdo.inventariotablets.model.LoginResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author drojas6
 *
 */
@Repository
public class DevicesRestRepository {

	/**
	 * 
	 */
	@Autowired
	ResourceLoader resourceLoader;

	/**
	 * 
	 */
	@Value("${app.mainkeystore.path}")
	String mainKeystorePath;

	@Value("${app.mainkeystore.password}")
	String mainKeystorePassword;

	/**
	 * @param urlWSRestDevices
	 * @param authToken
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<Device> getAllDevices(String urlWSRestDevices, String authToken)
			throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
			CertificateException, FileNotFoundException, IOException {
		DeviceRequest deviceRequest = null;
		DeviceResponse deviceResponse = null;
		Response response = null;
		MultivaluedMap<String, Object> headers = null;
		String strResponse = null;
		ObjectMapper mapper = null;

		headers = new MultivaluedHashMap<String, Object>();
		headers.put("Auth_token", Arrays.asList(authToken));

		deviceRequest = new DeviceRequest();
		deviceRequest.setSortOrder("ASC");
		deviceRequest.setSortColumn("ID");
		deviceRequest.setEnableCount("false");

		response = ClientBuilder.newBuilder().sslContext(getSSLContextFromKeystore())
				.register(JacksonJsonProvider.class)
				// .property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT,
				// LoggingFeature.Verbosity.PAYLOAD_ANY)
				// .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT,
				// "WARNING").build()
				.build().target(urlWSRestDevices).request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(deviceRequest, MediaType.APPLICATION_JSON));

		if (response.getStatus() != 200) {
			throw new RuntimeException(
					String.format("Failed : HTTP error code %s - %s", response.getStatus(), response.getStatusInfo()));

		}
		strResponse = response.readEntity(String.class);
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		deviceResponse = mapper.readValue(strResponse, DeviceResponse.class);

		return deviceResponse.getFilteredDevicesDataList();
	}

	/**
	 * @param urlWSRestAuth
	 * @param mdmLogin
	 * @param mdmPassword
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public String getAuthToken(String urlWSRestAuth, String mdmLogin, String mdmPassword)
			throws FileNotFoundException, IOException, GeneralSecurityException {
		String authToken = null;
		LoginRequest login = null;
		Response response = null;
		LoginResponse loginResponse = null;

		login = new LoginRequest();
		login.setLogin(mdmLogin);
		login.setPassword(mdmPassword);

		response = ClientBuilder.newBuilder().sslContext(getSSLContextFromKeystore())
				.register(JacksonJsonProvider.class)
				// .property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT,
				// LoggingFeature.Verbosity.PAYLOAD_ANY)
				// .property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "WARNING")
				.build().target(urlWSRestAuth).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(login, MediaType.APPLICATION_JSON));

		if (response.getStatus() != 200) {
			throw new RuntimeException(
					String.format("Failed : HTTP error code %s - %s", response.getStatus(), response.getStatusInfo()));

		}
		loginResponse = response.readEntity(LoginResponse.class);
		authToken = loginResponse.getAuth_token();
		response.close();

		return authToken;
	}

	/**
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws KeyManagementException
	 */
	public SSLContext getSSLContextFromKeystore()
			throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException,
			FileNotFoundException, IOException, KeyManagementException {
		SSLContext sslContext = null;
		KeyManagerFactory keyMgrFactory = null;
		KeyStore keyStore = null;
		TrustManagerFactory tmf = null;

		sslContext = SSLContext.getInstance("TLS");
		// System.getProperties().put("https.proxyHost", "piscis01.bancodebogota.net"); // TODO
		// System.getProperties().put("https.proxyPort", "8003");

		keyMgrFactory = KeyManagerFactory.getInstance("SunX509");
		keyStore = KeyStore.getInstance("JKS");
		keyStore.load(new FileInputStream(getAbsolutePathResourceFile(mainKeystorePath)),
				mainKeystorePassword.toCharArray());
		keyMgrFactory.init(keyStore, mainKeystorePassword.toCharArray());

		tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(keyStore);

		sslContext.init(keyMgrFactory.getKeyManagers(), tmf.getTrustManagers(), null);
		return sslContext;
	}

	/**
	 * @param relativePathFile
	 * @return
	 */
	private String getAbsolutePathResourceFile(String relativePathFile) {
		String filePath = null;
		File file = null;
		// file = new File(getClass().getClassLoader().getResource(relativePathFile).getFile());
		// file = new File(getClass().getClassLoader().getResource(relativePathFile).getPath(), relativePathFile);
		filePath = getClass().getClassLoader().getResource(relativePathFile).getPath();
		file = new File(filePath);
		System.out.println(file.getAbsolutePath());
		System.out.println(file.exists());
		System.out.println(file.canRead());
		return filePath; 
	}

}
