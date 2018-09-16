package me.sidazhang.recipe.repositories.v1;


import me.sidazhang.recipe.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
