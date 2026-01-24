package com.neoflex.vacationcalc.service;

import com.neoflex.vacationcalc.configurations.CalculatorProperties;
import com.neoflex.vacationcalc.dto.VacationPayResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceImplTest {

    @Mock
    private CalculatorProperties properties;

    private CalculatorServiceImpl service;

    @BeforeEach
    void setUp() {
        when(properties.getAverageMonthDays()).thenReturn(new BigDecimal("29.3"));
        when(properties.getRoundingMode()).thenReturn(RoundingMode.UP);
        when(properties.getRoundingScale()).thenReturn(2);

        service = new CalculatorServiceImpl(properties);
    }

    @Test
    void calculate_withoutDates_shouldReturnAverageSalaryTimesVacationDays() {
        BigDecimal averageSalary = new BigDecimal("100000");
        int vacationDays = 10;

        VacationPayResponseDto response = service.calculate(averageSalary, vacationDays, null, null);

        assertEquals(new BigDecimal("34129.70"), response.getVacationPay());

        verify(properties, never()).getHolidaysAsDates();
    }

    @Test
    void calculate_withStartDateOnly_shouldComputeEndDateAndExcludeWeekendsAndHolidays() {
        BigDecimal averageSalary = new BigDecimal("100000");
        int vacationDays = 10;
        LocalDate startDate = LocalDate.of(2026, 5, 14); // Thu
        when(properties.getHolidaysAsDates()).thenReturn(Set.of(LocalDate.of(2026, 5, 20)));
        VacationPayResponseDto response = service.calculate(averageSalary, vacationDays, startDate, null);
        assertEquals(new BigDecimal("20477.82"), response.getVacationPay());
        verify(properties, atLeastOnce()).getHolidaysAsDates();
    }

    @Test
    void calculate_withStartAndEnd_shouldCountPayableDaysInRange() {
        BigDecimal averageSalary = new BigDecimal("100000");
        int vacationDays = 999;
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 1, 10);
        when(properties.getHolidaysAsDates()).thenReturn(Set.of(LocalDate.of(2026, 1, 1)));
        VacationPayResponseDto response = service.calculate(averageSalary, vacationDays, startDate, endDate);

        assertEquals(new BigDecimal("20477.82"), response.getVacationPay());
        verify(properties, atLeastOnce()).getHolidaysAsDates();
    }
}
