package com.bmc.cm.soaptest.wsclient;

import com.bmc.cm.soaptest.wsclient.jaxb.CountriesPort;
import com.bmc.cm.soaptest.wsclient.jaxb.CountriesPortService;
import com.bmc.cm.soaptest.wsclient.springws.CountryClient;
import com.bmc.cm.soaptest.wsdl.GetCountryRequest;
import com.bmc.cm.soaptest.wsdl.GetCountryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URL;

@SpringBootApplication
public class WsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsClientApplication.class, args);
	}

	@Bean
	CommandLineRunner lookup(
			@Value("${client}")String client,CountryClient quoteClient) {

		return args -> {
			String country = "Spain";

			if (args.length > 0) {
				country = args[0];
			}
			if (client.equals("ws")) {
				GetCountryResponse response = quoteClient.getCountry(country);
				System.err.println(response.getCountry().getCurrency());
			} else {
				URL url = new URL("http://localhost:8080/ws/countries.wsdl");
				CountriesPortService countriesPortService = new CountriesPortService(url);
				com.bmc.cm.soaptest.wsclient.jaxb.GetCountryRequest getCountryRequest = new com.bmc.cm.soaptest.wsclient.jaxb.GetCountryRequest();
				getCountryRequest.setName(country);
				CountriesPort a = countriesPortService.getCountriesPortSoap11();
				com.bmc.cm.soaptest.wsclient.jaxb.GetCountryResponse b =a.getCountry(getCountryRequest);
				System.out.println(b.getCountry().getCurrency());
			}
		};
	}
}
