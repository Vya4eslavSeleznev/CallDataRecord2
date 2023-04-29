package com.nexign.user.service;

import com.nexign.common.model.ChangeTariffModel;
import com.nexign.common.model.CreateProfileModel;
import com.nexign.common.model.Role;
import com.nexign.common.model.UserPhoneNumberModel;
import com.nexign.user.entity.Customer;
import com.nexign.user.entity.UserCredential;
import com.nexign.user.exception.CustomerNotFoundException;
import com.nexign.user.model.FindByPhoneModel;
import com.nexign.user.repository.CustomerRepository;
import com.nexign.user.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private static CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private String phone;
    private long tariffId;
    private Customer expectedCustomer;

    @BeforeEach
    public void init() {
        phone = "89997776655";
        tariffId = 7L;

        expectedCustomer = new Customer(
          new UserCredential(Role.USER, "pwd", "usrnm"),
          phone,
          tariffId
        );

        customerService = new CustomerServiceImpl(customerRepository, passwordEncoder);
    }

    @Test
    public void should_find_by_phone_number_with_not_null_customer_customer_returned() throws CustomerNotFoundException {
        FindByPhoneModel expectedModel = new FindByPhoneModel(
          expectedCustomer.getUserCredential().getId(),
          expectedCustomer.getTariffId()
        );

        when(customerRepository.findByPhoneNumber(phone)).thenReturn(expectedCustomer);

        FindByPhoneModel actualModel = customerService.findByPhoneNumber(phone);

        verify(customerRepository, times(1)).findByPhoneNumber(phone);
        assertEquals(expectedModel.getTariffId(), actualModel.getTariffId());
        assertEquals(expectedModel.getUserId(), actualModel.getUserId());
    }

    @Test
    public void should_find_by_phone_number_with_null_customer_exception() {
        when(customerRepository.findByPhoneNumber(phone)).thenReturn(null);
        assertThrows(CustomerNotFoundException.class, () -> customerService.findByPhoneNumber(phone));
    }

    @Test
    public void should_save_customer_user_id_returned() {
        long expectedUserId = expectedCustomer.getUserCredential().getId();

        CreateProfileModel cpm = new CreateProfileModel(
          phone,
          expectedCustomer.getUserCredential().getPassword(),
          expectedCustomer.getUserCredential().getUsername(),
          expectedCustomer.getUserCredential().getRole(),
          expectedCustomer.getTariffId()
        );

        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);

        long actualUserId = customerService.saveCustomer(cpm);

        verify(customerRepository, times(1)).save(any(Customer.class));

        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    public void should_get_user_phones_list_returned() {
        List<Long> idList = List.of(1L, 2L, 3L, 4L, 5L);
        List<Customer> expectedCustomers = List.of(expectedCustomer);

        when(customerRepository.findByUserIds(idList)).thenReturn(expectedCustomers);

        List<UserPhoneNumberModel> expectedList =
          expectedCustomers
            .stream()
            .map(c -> new UserPhoneNumberModel(c.getUserCredential().getId(), c.getPhoneNumber()))
            .collect(Collectors.toList());

        List<UserPhoneNumberModel> actualList = customerService.getPhoneNumbers(idList);

        verify(customerRepository, times(1)).findByUserIds(idList);

        assertEquals(expectedList.get(0).getPhoneNumber(), actualList.get(0).getPhoneNumber());
        assertEquals(expectedList.get(0).getId(), actualList.get(0).getId());
    }

    @Test
    public void should_change_tariff_customer_id_returned() {
        ChangeTariffModel ctm = new ChangeTariffModel(
          phone, tariffId
        );

        long expectedCustomerId = 0L;

        when(customerRepository.findByPhoneNumber(ctm.getPhoneNumber())).thenReturn(expectedCustomer);
        when(customerRepository.save(any(Customer.class))).thenReturn(expectedCustomer);

        long actualCustomerId = customerService.changeTariff(ctm);

        verify(customerRepository, times(1)).findByPhoneNumber(ctm.getPhoneNumber());
        verify(customerRepository, times(1)).save(any(Customer.class));

        assertEquals(expectedCustomerId, actualCustomerId);
    }
}
