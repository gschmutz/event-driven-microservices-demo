package com.trivadis.ms.sample.customer.api;

import com.trivadis.ms.sample.customer.model.AddressDO;
import com.trivadis.ms.sample.customer.model.CustomerDO;

import java.util.ArrayList;

public class CustomerConverter {
	
	public static com.trivadis.ms.sample.customer.api.CustomerApi convert (CustomerDO customer) {
		com.trivadis.ms.sample.customer.api.CustomerApi value = new com.trivadis.ms.sample.customer.api.CustomerApi();
		
		value.setCustomerId(customer.getId());
		value.setFirstName(customer.getFirstName());
		value.setLastName(customer.getLastName());
		value.setTitle(customer.getTitle());
		value.setEmailAddress(customer.getEmailAddress());
		
		com.trivadis.ms.sample.customer.api.AddressApi valueAddress;
		if (customer.getAddresses() != null) {
			for (AddressDO address : customer.getAddresses()) {
				valueAddress = new com.trivadis.ms.sample.customer.api.AddressApi();
				valueAddress.setStreet(address.getStreet());
				valueAddress.setNumber(address.getNumber());
				valueAddress.setPostcode(address.getPostcode());
				valueAddress.setCity(address.getCity());
				valueAddress.setCountry(address.getCountry());
				
				if (value.getAddresses() == null) { value.setAddresses(new ArrayList<>()); }
				value.getAddresses().add(valueAddress);
			}
		}
		
		return value;
	}
	
	public static CustomerDO convert (com.trivadis.ms.sample.customer.api.CustomerApi customer) {
		CustomerDO value = new CustomerDO();
		
		value.setId(customer.getCustomerId());
		value.setFirstName(customer.getFirstName());
		value.setLastName(customer.getLastName());
		value.setTitle(customer.getTitle());
		value.setEmailAddress(customer.getEmailAddress());

		AddressDO valueAddress;
		if (customer.getAddresses() != null) { 
			for (com.trivadis.ms.sample.customer.api.AddressApi address : customer.getAddresses()) {
				valueAddress = new AddressDO();
				valueAddress.setStreet(address.getStreet());
				valueAddress.setNumber(address.getNumber());
				valueAddress.setPostcode(address.getPostcode());
				valueAddress.setCity(address.getCity());
				valueAddress.setCountry(address.getCountry());
				
				if (value.getAddresses() == null) { value.setAddresses(new ArrayList<>()); }
				value.getAddresses().add(valueAddress);
			}
		}
		
		return value;
	}
	
}
