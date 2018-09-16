package me.sidazhang.recipe.services.v1;

import me.sidazhang.recipe.api.v1.DTO.CustomerDTO;
import me.sidazhang.recipe.api.v1.mapper.CustomerMapper;
import me.sidazhang.recipe.exceptions.ResourceNotFoundException;
import me.sidazhang.recipe.models.Customer;
import me.sidazhang.recipe.repositories.v1.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String BASE_URL = "/api/v1/customer/";
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)

                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setUrl(BASE_URL + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return customerMapper.customerToCustomerDTO(customer.get());
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        return saveCusRetDTO(customer);
    }

    //extracted
    private CustomerDTO saveCusRetDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        savedCustomerDTO.setUrl(BASE_URL + savedCustomer.getId());
        return savedCustomerDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return saveCusRetDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
//getCustomerById-> map to DTO -> traverse incoming DTO, filter out not null pairs ->update DTO, save
        return null;
    }

    @Override
    public void deleteCustomerById(Long id) {
        if (!customerRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException();
        }
        customerRepository.deleteById(id);
    }
}
