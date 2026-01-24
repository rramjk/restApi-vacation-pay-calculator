package com.neoflex.vacationcalc.rest;

import com.neoflex.vacationcalc.dto.VacationPayResponseDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface CalculatorRestApi {

    @GetMapping("/calculate")
    VacationPayResponseDto calculate(
            @RequestParam(value = "averageSalary")
            @DecimalMin(value = "0.01", message = "Минимальное значение средней зарплаты - 0.01")
            BigDecimal averageSalary,
            @RequestParam(value = "vacationDays")
            @Min(value = 1, message = "Минимальное количество дней отпуска - 1")
            Integer vacationDays,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate);
}
