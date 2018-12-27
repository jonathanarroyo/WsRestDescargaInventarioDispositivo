package com.bancodebogota.ptdo.inventariotablets.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bancodebogota.ptdo.inventariotablets.model.Device;
import com.bancodebogota.ptdo.inventariotablets.service.DevicesService;

/**
 * @author drojas6
 *
 */
@RestController
@RequestMapping("/devices")
public class DevicesController {

	/**
	 * 
	 */
	@Autowired
	DevicesService devicesService;

	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @throws URISyntaxException
	 */
	@RequestMapping(value = "/getDevicesFiltered", method = RequestMethod.GET)
	public List<Device> getOfficeAdvicerState()
			throws FileNotFoundException, IOException, GeneralSecurityException, URISyntaxException {
		return devicesService.getDevicesFilteredByUserDevice();
	}

}
