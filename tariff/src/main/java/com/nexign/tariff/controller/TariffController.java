package com.nexign.tariff.controller;

import com.nexign.tariff.entity.CallType;
import com.nexign.tariff.exception.TariffNotFoundException;
import com.nexign.tariff.model.TariffByParametersModel;
import com.nexign.tariff.model.TariffForHrsModel;
import com.nexign.tariff.model.TariffModel;
import com.nexign.tariff.service.TariffService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tariff")
@AllArgsConstructor
public class TariffController {

    private TariffService tariffService;

    @PostMapping
    public void addTariff(@RequestBody TariffModel tariffModel) {
        tariffService.saveTariff(tariffModel);
    }

    @GetMapping("/{tariffId}/{callType}")
    public ResponseEntity<List<TariffForHrsModel>> getTariff(@PathVariable long tariffId, @PathVariable CallType callType) {
        try {
            List<TariffForHrsModel> modelList = tariffService.getTariffInfo(tariffId, callType);
            return new ResponseEntity<>(modelList, HttpStatus.OK);
        }
        catch(TariffNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
