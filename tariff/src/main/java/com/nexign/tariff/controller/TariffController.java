package com.nexign.tariff.controller;

import com.nexign.tariff.model.TariffModel;
import com.nexign.tariff.service.TariffService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tariff")
@AllArgsConstructor
public class TariffController {

    private TariffService tariffService;

    @PostMapping
    public void addTariff(@RequestBody TariffModel tariffModel) {
        tariffService.saveTariff(tariffModel);
    }

    @GetMapping
    public void getTariff() {

    }
}
