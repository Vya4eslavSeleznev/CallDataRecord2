package com.nexign.tariff.service;

import com.nexign.common.model.CallType;
import com.nexign.common.model.TariffInfoModel;
import com.nexign.common.model.TariffType;
import com.nexign.tariff.entity.Tariff;
import com.nexign.tariff.entity.TariffCallType;
import com.nexign.tariff.entity.TariffCallTypeCost;
import com.nexign.tariff.exception.TariffNotFoundException;
import com.nexign.tariff.model.TariffCallTypeModel;
import com.nexign.tariff.model.TariffCostModel;
import com.nexign.tariff.model.TariffModel;
import com.nexign.tariff.repository.TariffCallTypeCostRepository;
import com.nexign.tariff.repository.TariffCallTypeRepository;
import com.nexign.tariff.service.impl.TariffServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TariffServiceTest {

    @InjectMocks
    private TariffServiceImpl tariffService;

    @Mock
    private TariffCallTypeRepository callTypeRepository;

    @Mock
    private TariffCallTypeCostRepository callTypeCostRepository;

    private long tariffId;
    private CallType callType;
    private Tariff tariff;
    private TariffCallTypeCost  tariffCallTypeCost;
    private int interval = 100;
    private double price = 50;
    private int currencyId = 10;
    private TariffType tariffType;

    @BeforeEach
    public void init() {
        tariffId = 7L;
        interval = 100;
        price = 50;
        currencyId = 10;
        callType = CallType.INPUT;
        tariffType = TariffType.POSTPAID;
        tariffService = new TariffServiceImpl(callTypeRepository, callTypeCostRepository);
        tariff = new Tariff("Test");
        tariffCallTypeCost = new TariffCallTypeCost(
          new TariffCallType(tariff, callType), interval, price, currencyId, tariffType);
    }

    @Test
    public void should_get_tariff_info_with_null_result_from_repository_exception() {
        when(callTypeRepository.findByTariffIdAndCallType(tariffId, callType)).thenReturn(null);
        assertThrows(TariffNotFoundException.class, () -> tariffService.getTariffInfo(tariffId, callType));
    }

    @Test
    public void should_get_tariff_info_returned_list_of_tariff_info_models() throws TariffNotFoundException {
        TariffCallType expectedTariffCallType = new TariffCallType(tariff, callType);

        List<TariffCallTypeCost> expectedTariffCallTypeCosts = List.of(tariffCallTypeCost);

        when(callTypeRepository.findByTariffIdAndCallType(tariffId, callType)).thenReturn(expectedTariffCallType);
        when(callTypeCostRepository.findByTariffCallTypeId(expectedTariffCallType.getId()))
          .thenReturn(expectedTariffCallTypeCosts);

        List<TariffInfoModel> expectedModelList = List.of(
          new TariffInfoModel(interval, price, currencyId, TariffType.POSTPAID, callType)
        );

        List<TariffInfoModel> actualModelList = tariffService.getTariffInfo(tariffId, callType);

        verify(callTypeRepository, times(1)).findByTariffIdAndCallType(tariffId, callType);
        verify(callTypeCostRepository, times(1))
          .findByTariffCallTypeId(expectedTariffCallType.getId());

        assertEquals(expectedModelList.get(0).getInterval(), actualModelList.get(0).getInterval());
        assertEquals(expectedModelList.get(0).getCallType(), actualModelList.get(0).getCallType());
        assertEquals(expectedModelList.get(0).getCost(), actualModelList.get(0).getCost());
        assertEquals(expectedModelList.get(0).getTariffType(), actualModelList.get(0).getTariffType());
        assertEquals(expectedModelList.get(0).getCurrencyId(), actualModelList.get(0).getCurrencyId());
    }

    @Test
    public void should_save_tariff() {
        List<TariffCostModel> tariffCostModels = List.of(
          new TariffCostModel(interval, price, currencyId, tariffType)
        );

        List<TariffCallTypeModel> tariffCallTypeModels = List.of(
          new TariffCallTypeModel(callType, tariffCostModels)
        );

        when(callTypeCostRepository.save(any(TariffCallTypeCost.class))).thenReturn(tariffCallTypeCost);

        tariffService.saveTariff(new TariffModel("test", tariffCallTypeModels));
        verify(callTypeCostRepository, times(1)).save(any(TariffCallTypeCost.class));
    }
}
