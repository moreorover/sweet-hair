package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.CustomerDtoBase;
import martin.sweethair.dto.full.CustomerDtoFull;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.Customer;
import martin.sweethair.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private final ModelMapper modelMapper;

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerDtoBase save(CustomerDtoBase customerDtoBase) {
        Customer save = customerRepository.save(modelMapper.map(customerDtoBase, Customer.class));
        customerDtoBase.setId(save.getId());
        return customerDtoBase;
    }

    @Transactional(readOnly = true)
    public Set<CustomerDtoFull> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDtoFull.class))
                .collect(Collectors.toSet());
    }

    public CustomerDtoFull getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No supplier found with ID -> " + id));
        return modelMapper.map(customer, CustomerDtoFull.class);
    }

    @Transactional
    public CustomerDtoBase updateCustomer(CustomerDtoBase customerDtoBase) {
        if( customerDtoBase.getId() > 0L) {
            Customer customer = modelMapper.map(customerDtoBase, Customer.class);

            customerRepository.save(customer);
            return modelMapper.map(customer, CustomerDtoBase.class);
        } else {
            throw new SpringDataException("No payload must contain valid ID.");
        }

    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
