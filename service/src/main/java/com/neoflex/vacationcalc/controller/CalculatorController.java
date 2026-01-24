package com.neoflex.vacationcalc.controller;

import com.neoflex.vacationcalc.dto.VacationPayResponseDto;
import com.neoflex.vacationcalc.rest.CalculatorRestApi;
import com.neoflex.vacationcalc.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@Validated
@RestController
@RequiredArgsConstructor
public class CalculatorController implements CalculatorRestApi {
    private final CalculatorService calculatorService;

    @Override
    public VacationPayResponseDto calculate(BigDecimal averageSalary, Integer vacationDays, LocalDate startDate, LocalDate endDate) {
        return calculatorService.calculate(averageSalary, vacationDays, startDate, endDate);
    }
}
