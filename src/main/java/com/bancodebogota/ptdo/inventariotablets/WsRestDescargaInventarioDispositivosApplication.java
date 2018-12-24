package com.bancodebogota.ptdo.inventariotablets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author drojas6
 *
 */
@SpringBootApplication
@EnableScheduling
public class WsRestDescargaInventarioDispositivosApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(WsRestDescargaInventarioDispositivosApplication.class, args);
	}
}
