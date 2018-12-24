package com.bancodebogota.ptdo.inventariotablets.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bancodebogota.ptdo.inventariotablets.model.Device;
import com.bancodebogota.ptdo.inventariotablets.repository.AdminPasswdPERepository;
import com.bancodebogota.ptdo.inventariotablets.repository.DevicesDbRepository;
import com.bancodebogota.ptdo.inventariotablets.repository.DevicesRestRepository;

/**
 * @author drojas6
 *
 */
@Service
public class DevicesService {

	/**
	 * 
	 */
	@Autowired
	AdminPasswdPERepository adminPwRepository;

	@Autowired
	DevicesRestRepository devicesRestRepository;

	@Autowired
	private DevicesDbRepository devicesDbRepository;

	/**
	 * Parámetros de consulta al servicio de MDM
	 */
	@Value("${app.mdm.url-rest-getdevices}")
	private String urlWSRestDevices;

	@Value("${app.mdm.url-rest-authentication}")
	private String urlWSRestAuth;

	@Value("${app.mdm.login}")
	private String mdmLogin;

	/**
	 * Parámetros del administrador de contraseñas
	 */

	@Value("${app.pe.login-adm-pass}")
	private String loginAdminPass;

	@Value("${app.pe.login-adm-domain}")
	private String domainAdminPass;

	@Value("${app.keyfilter-device-tablet}")
	private String keyfilterDeviceTablet;

	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public List<Device> getDevicesFilteredByUserDevice()
			throws FileNotFoundException, IOException, GeneralSecurityException {
		String authToken = null;
		List<Device> lstDevices = null;
		
		/*
		authToken = devicesRestRepository.getAuthToken(urlWSRestAuth, mdmLogin,
				getPwAdminPasswordPE(loginAdminPass, domainAdminPass));
		*/
		
		authToken = devicesRestRepository.getAuthToken(urlWSRestAuth, mdmLogin,
				"1234567");
		
		lstDevices = devicesRestRepository.getAllDevices(urlWSRestDevices, authToken);

		lstDevices.forEach(p -> {
			try {
				p.setUserName(getFirstOccurrenceBetweenQuotes(p.getUserName()).trim());
				p.setCostCenter(p.getUserName().split(keyfilterDeviceTablet)[1].substring(0, 4));
			} catch (Exception ex) {
				p.setCostCenter(null);
			}
			p.setLastUpdateDate(new Date());
		});

		lstDevices = lstDevices.stream()
				.filter(p -> p.getCostCenter() != null && p.getUserName().startsWith(keyfilterDeviceTablet))
				.collect(Collectors.toList());

		return lstDevices;
	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public void getDevicesToDataBase() throws FileNotFoundException, IOException, GeneralSecurityException {
		List<Device> lstDevices = null;
		lstDevices = getDevicesFilteredByUserDevice();
		saveDevicesToDataBase(lstDevices);
	}

	/**
	 * @param login
	 * @param domain
	 * @return
	 */
	private String getPwAdminPasswordPE(String login, String domain) {
		return adminPwRepository.getPasswordAdminPasswordPE(login, domain).get("password");
	}

	/**
	 * @param string
	 * @return
	 */
	private String getFirstOccurrenceBetweenQuotes(String string) {
		String firstOccurrence = null;
		Matcher matcher = null;
		firstOccurrence = string;
		matcher = Pattern.compile("([^\"]*)").matcher(string);
		if (matcher.find()) {
			String[] array = null;
			StringUtils.join(array);
			firstOccurrence = matcher.group(0);
		}
		return firstOccurrence;
	}

	/**
	 * @param lstDevices
	 */
	private void saveDevicesToDataBase(List<Device> lstDevices) {
		Optional<List<Device>> lstDevicesByCc = null;
		Device auxDevice = null;
		Map<String, List<Device>> groupDevice = null;

		groupDevice = lstDevices.stream().collect(Collectors.groupingBy(Device::getCostCenter));
		for (Map.Entry<String, List<Device>> entryGrpDev : groupDevice.entrySet()) {
			lstDevicesByCc = devicesDbRepository.findByCostCenter(entryGrpDev.getKey());

			if (lstDevicesByCc.isPresent() && lstDevicesByCc.get().size() > 0) {
				for (Device devPersisted : lstDevicesByCc.get()) {
					auxDevice = entryGrpDev.getValue().stream()
							.filter(d -> d.getImeiOrMeid().equals(devPersisted.getImeiOrMeid())).findFirst()
							.orElse(null);
					if (auxDevice == null) {
						devicesDbRepository.delete(devPersisted);
					}
				}
				for (Device devObtained : entryGrpDev.getValue()) {
					auxDevice = lstDevicesByCc.get().stream()
							.filter(d -> d.getImeiOrMeid().equals(devObtained.getImeiOrMeid())).findFirst()
							.orElse(null);
					if (auxDevice == null) {
						devicesDbRepository.save(devObtained);
					}
				}
			} else {
				devicesDbRepository.saveAll(entryGrpDev.getValue());
			}
		}
	}

}
