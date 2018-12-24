package com.bancodebogota.ptdo.inventariotablets.shedule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bancodebogota.ptdo.inventariotablets.service.DevicesService;

@Component("taskComponent")
public class TaskComponent {

	@Autowired
	DevicesService deviceService;

	private static final Log log = LogFactory.getLog(TaskComponent.class);

	@Scheduled(cron = "0 37 16 * * *")
	public void doTask() {
		try {
			log.info("TIME IS " + new Date());
			deviceService.getDevicesToDataBase();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
