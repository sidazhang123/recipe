package me.sidazhang.recipe.services.v1;


import me.sidazhang.recipe.api.v1.DTO.CustomerDTO;
import me.sidazhang.recipe.api.v1.mapper.CustomerMapper;
import me.sidazhang.recipe.bootstrap.ApiBootstrap;
import me.sidazhang.recipe.models.Customer;
import me.sidazhang.recipe.repositories.CategoryRepository;
import me.sidazhang.recipe.repositories.v1.CustomerRepository;
import me.sidazhang.recipe.repositories.v1.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by jt on 10/3/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @Before
    public void setUp() {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().spliterator().getExactSizeIfKnown());

        //setup data for testing
        ApiBootstrap bootstrap = new ApiBootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run(); //load data

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstName() {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.findById(id).get();
        assertNotNull(originalCustomer);
        //save original first name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstname());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstname())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastname()));
    }

    @Test
    public void patchCustomerUpdateLastName() {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.findById(id).get();
        assertNotNull(originalCustomer);

        //save original first/last name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastname());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstname()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastname())));
    }

    private Long getCustomerIdValue() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);

        System.out.println("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }
}