package me.sidazhang.recipe.bootstrap;

import me.sidazhang.recipe.models.Category;
import me.sidazhang.recipe.models.Customer;
import me.sidazhang.recipe.models.Vendor;
import me.sidazhang.recipe.repositories.CategoryRepository;
import me.sidazhang.recipe.repositories.v1.CustomerRepository;
import me.sidazhang.recipe.repositories.v1.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApiBootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;


    public ApiBootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {

        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");
        vendorRepository.save(vendor2);

    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setCategoryName("Fruits");

        Category dried = new Category();
        dried.setCategoryName("Dried");

        Category fresh = new Category();
        fresh.setCategoryName("Fresh");

        Category exotic = new Category();
        exotic.setCategoryName("Exotic");

        Category nuts = new Category();
        nuts.setCategoryName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories Loaded: " + categoryRepository.count());
    }

    private void loadCustomers() {

        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2l);
        customer2.setFirstname("Sam");
        customer2.setLastname("Axe");

        customerRepository.save(customer2);

        System.out.println("Customers Loaded: " + customerRepository.count());
    }
}