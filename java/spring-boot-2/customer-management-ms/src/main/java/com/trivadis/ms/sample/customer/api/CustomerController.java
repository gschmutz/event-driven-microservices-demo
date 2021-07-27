package com.trivadis.ms.sample.customer.api;

import com.google.common.base.Preconditions;
import com.trivadis.ms.sample.customer.model.CustomerDO;
import com.trivadis.ms.sample.customer.repository.CustomerRepository;
import com.trivadis.ms.sample.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RestController()
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;
    
    private void createCustomer(com.trivadis.ms.sample.customer.api.CustomerApi customerApi) throws ParseException {
        CustomerDO customerDO = com.trivadis.ms.sample.customer.api.CustomerConverter.convert(customerApi);
        customerService.createCustomer(customerDO);
        LOGGER.info("Customer created: " + customerDO);
    }
    
    private void modifyCustomer(com.trivadis.ms.sample.customer.api.CustomerApi customerApi) throws ParseException {
        CustomerDO customerDO = com.trivadis.ms.sample.customer.api.CustomerConverter.convert(customerApi);
        customerService.modifyCustomer(customerDO);
        LOGGER.info("Customer created: " + customerDO);
    }

    @RequestMapping(value= "/api/customers",
            method = RequestMethod.POST,
            consumes = "application/json") 
    @Transactional
    public void postCustomer(@RequestBody com.trivadis.ms.sample.customer.api.CustomerApi customerApi) throws ParseException {
        Preconditions.checkNotNull(customerApi);
        
        createCustomer(customerApi);
    }
    
    @RequestMapping(value= "/api/customer",
            method = RequestMethod.PUT,
            consumes = "application/json") 
    @Transactional
    public void putCustomer(@RequestBody com.trivadis.ms.sample.customer.api.CustomerApi customerApi) throws ParseException {
        Preconditions.checkNotNull(customerApi);
        Preconditions.checkNotNull(customerApi.getCustomerId());
        
        modifyCustomer(customerApi);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value= "/api/customer/{id}"
    )
    //@CrossOrigin(origins = "http://localhost:4200")
    public com.trivadis.ms.sample.customer.api.CustomerApi getCustomer(@PathVariable(value="id") String id)  {
        com.trivadis.ms.sample.customer.api.CustomerApi customer = new com.trivadis.ms.sample.customer.api.CustomerApi();
        CustomerDO customerDO = null;
        // trim leading and training double quote
        id = StringUtils.trimTrailingCharacter(StringUtils.trimLeadingCharacter(id, '"'),'"');
        
        //customersDO = customerRepository.findAll(); 
        
        if (id != null && id.length() > 0) {
        		customerDO = customerRepository.findById(id);
        }
        
        if(customerDO != null) {
        		customer = com.trivadis.ms.sample.customer.api.CustomerConverter.convert(customerDO);
        }
        return customer;
    }
    
    @RequestMapping(
            method = RequestMethod.GET,
            value= "/api/customers"
    )
    //@CrossOrigin(origins = "http://localhost:4200")
    public List<com.trivadis.ms.sample.customer.api.CustomerApi> getCustomers(@RequestParam(value="code", defaultValue="") String code,
                                                                         @RequestParam(value="name", defaultValue="") String name,
                                                                         @RequestParam(value="categoryName", defaultValue="") String categoryName)  {
        com.trivadis.ms.sample.customer.api.CustomerApi customer = new com.trivadis.ms.sample.customer.api.CustomerApi();
        List<CustomerDO> customersDO = new ArrayList<CustomerDO>();
        List<com.trivadis.ms.sample.customer.api.CustomerApi> customers = new ArrayList<com.trivadis.ms.sample.customer.api.CustomerApi>();
        Predicate<CustomerDO> pred = null;
        
        // trim leading and training double quote
//        id = StringUtils.trimTrailingCharacter(StringUtils.trimLeadingCharacter(id, '"'),'"');
        code = StringUtils.trimTrailingCharacter(StringUtils.trimLeadingCharacter(code, '"'),'"');
        name = StringUtils.trimTrailingCharacter(StringUtils.trimLeadingCharacter(name, '"'),'"');
        categoryName = StringUtils.trimTrailingCharacter(StringUtils.trimLeadingCharacter(categoryName, '"'),'"');
        
        //customersDO = customerRepository.findAll(); 
        
        if (name != null && name.length() > 0) {		
            	customersDO = customerRepository.findCustomersByNameRegex(name);        	
        } else if (code != null && code.length() > 0) {	
        	System.out.println (code);        	
//    			CustomerDO customerDO = customerRepository.findByCustomerCode(code);  
//    			if (customerDO != null) {
//    				customersDO.add(customerDO);
    			}
        		//customersDO.removeIf(p-> !(p.getCustomerCode().equals(code)));
//        } else {
//        		customersDO = customerRepository.findAll();   
//        }
        
        if (pred != null)
        		customersDO.removeIf(pred);
        
        for (CustomerDO customerDO : customersDO) {
        		customer = com.trivadis.ms.sample.customer.api.CustomerConverter.convert(customerDO);
        		customers.add(customer);
        }
        return customers;
    }


    
}