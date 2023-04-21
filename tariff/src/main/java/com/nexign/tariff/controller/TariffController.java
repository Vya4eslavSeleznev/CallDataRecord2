package com.nexign.tariff.controller;

import com.nexign.tariff.model.TariffModel;
import com.nexign.tariff.service.TariffService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tariff")
@AllArgsConstructor
public class TariffController {

    private TariffService tariffService;

    @PostMapping("/create")
    public void addTariff(@RequestBody TariffModel tariffModel) {
        tariffService.saveTariff(tariffModel);
    }
}
