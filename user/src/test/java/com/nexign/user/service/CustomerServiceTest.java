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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerService customerService;

    private static String phone;
    private static long tariffId;
    private static Customer expectedCustomer;

    @BeforeAll
    public static void init() {
        phone = "89997776655";
        tariffId = 7L;

        expectedCustomer = new Customer(
          new UserCredential(Role.USER, "pwd", "usrnm"),
          phone,
          tariffId
        );
    }

    @Test
    public void find_by_phone_number_with_not_null_customer() throws CustomerNotFoundException {
        FindByPhoneModel expectedModel = new FindByPhoneModel(
          expectedCustomer.getUserCredential().getId(),
          expectedCustomer.getTariffId()
        );

        doReturn(expectedCustomer).when(customerRepository).findByPhoneNumber(phone);
        doReturn(expectedModel).when(customerService).findByPhoneNumber(phone);

        Customer actualCustomer = customerRepository.findByPhoneNumber(phone);
        FindByPhoneModel actualModel = customerService.findByPhoneNumber(phone);

        verify(customerRepository, times(1)).findByPhoneNumber(phone);
        verify(customerService, times(1)).findByPhoneNumber(phone);

        assertEquals(expectedCustomer, actualCustomer);
        assertEquals(expectedModel, actualModel);
    }

    @Test
    public void find_by_phone_number_with_null_customer() throws CustomerNotFoundException {
        doThrow(new CustomerNotFoundException()).when(customerService).findByPhoneNumber(phone);
        assertThrows(CustomerNotFoundException.class, () -> customerService.findByPhoneNumber(phone));
    }

    @Test
    public void save_customer_test() {
        long expectedUserId = expectedCustomer.getUserCredential().getId();

        CreateProfileModel cpm = new CreateProfileModel(
          phone,
          expectedCustomer.getUserCredential().getPassword(),
          expectedCustomer.getUserCredential().getUsername(),
          expectedCustomer.getUserCredential().getRole(),
          expectedCustomer.getTariffId()
        );

        doReturn(expectedCustomer).when(customerRepository).save(expectedCustomer);
        doReturn(expectedUserId).when(customerService).saveCustomer(cpm);

        Customer actualCustomer = customerRepository.save(expectedCustomer);
        long actualUserId = customerService.saveCustomer(cpm);

        verify(customerRepository, times(1)).save(expectedCustomer);
        verify(customerService, times(1)).saveCustomer(cpm);

        assertEquals(expectedCustomer, actualCustomer);
        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    public void get_user_phones() {
        Customer newCustomer = new Customer(
          new UserCredential(Role.USER, "qqq", "qwerty"),
          "89996665544",
          tariffId
        );

        List<Long> idList = List.of(1L, 2L, 3L, 4L, 5L);
        List<Customer> expectedCustomers = List.of(expectedCustomer, newCustomer);
        List<UserPhoneNumberModel> expectedList =
          expectedCustomers
          .stream()
          .map(c -> new UserPhoneNumberModel(c.getUserCredential().getId(), c.getPhoneNumber()))
          .collect(Collectors.toList());

        doReturn(expectedCustomers).when(customerRepository).findByUserIds(idList);
        doReturn(expectedList).when(customerService).getPhoneNumbers(idList);

        List<Customer> actualCustomers = customerRepository.findByUserIds(idList);
        List<UserPhoneNumberModel> actualList = customerService.getPhoneNumbers(idList);

        verify(customerRepository, times(1)).findByUserIds(idList);
        verify(customerService, times(1)).getPhoneNumbers(idList);

        assertEquals(expectedCustomers, actualCustomers);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void change_tariff_test() {
        ChangeTariffModel ctm = new ChangeTariffModel(
          phone, tariffId
        );

        long customerId = 8L;

        doReturn(expectedCustomer).when(customerRepository).findByPhoneNumber(ctm.getPhoneNumber());
        Customer actualCustomer = customerRepository.findByPhoneNumber(ctm.getPhoneNumber());
        verify(customerRepository, times(1)).findByPhoneNumber(ctm.getPhoneNumber());
        assertEquals(expectedCustomer, actualCustomer);

//        doReturn(customerId).when(customerRepository).save(actualCustomer).getId();
//        long actualIdFromRepository = customerRepository.save(actualCustomer).getId();
//        verify(customerRepository, times(1)).save(actualCustomer).getId();
//        assertEquals(customerId, actualIdFromRepository);

        doReturn(customerId).when(customerService).changeTariff(ctm);
        long actualUserIdFromService = customerService.changeTariff(ctm);
        verify(customerService, times(1)).changeTariff(ctm);
        assertEquals(customerId, actualUserIdFromService);
    }
}
