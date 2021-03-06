package com.example.backeventplanner.facade.customer;

import com.example.backeventplanner.annotation.Facade;
import com.example.backeventplanner.controller.customer.models.CustomerRequestModel;
import com.example.backeventplanner.controller.customer.models.CustomerResponseModel;
import com.example.backeventplanner.converter.customer.CustomerConverterImpl;
import com.example.backeventplanner.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Facade
public class CustomerFacade {

    private final CustomerConverterImpl customerConverterImpl;
    private final CustomerService customerService;


    @Autowired
    public CustomerFacade(CustomerConverterImpl customerConverterImpl, CustomerService customerService) {
        this.customerConverterImpl = customerConverterImpl;
        this.customerService = customerService;
    }

    public Boolean create(CustomerRequestModel requestModel) {
        CustomerDTO customerDTO = customerConverterImpl.dtoFromRequest(requestModel);
        CustomerDTO dtoReturned = customerService.create(customerDTO);
        boolean check = dtoReturned.getId() != null;
        return check;
    }

    public CustomerResponseModel getById(Long id) {
        CustomerDTO byId = customerService.getById(id);
        return customerConverterImpl.responseFromDto(byId);
    }

    public ArrayList<CustomerResponseModel> getAll() {
        ArrayList<CustomerDTO> all = customerService.getAll();
        List<CustomerResponseModel> collect = all.stream()
                .map(each -> customerConverterImpl.responseFromDto(each))
                .collect(Collectors.toList());
        return (ArrayList<CustomerResponseModel>) collect;
    }

    /*This method updates only info about customer
     * its not update username, password & role*/

    public CustomerResponseModel updateById(Long id, CustomerRequestModel requestModel) {
        CustomerDTO customerDTO = customerConverterImpl.dtoFromRequest(requestModel);
        CustomerDTO updateById = customerService.updateById(id, customerDTO);
        return customerConverterImpl.responseFromDto(updateById);
    }

    public void deleteById(Long id) {
        customerService.deleteById(id);
    }
}
