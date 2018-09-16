package me.sidazhang.recipe.api.v1.mapper;

import me.sidazhang.recipe.api.v1.DTO.CustomerDTO;
import me.sidazhang.recipe.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
