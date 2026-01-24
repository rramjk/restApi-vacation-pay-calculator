package com.neoflex.vacationcalc.service;

import com.neoflex.vacationcalc.configurations.CalculatorProperties;
import com.neoflex.vacationcalc.dto.VacationPayResponseDto;
import com.neoflex.vacationcalc.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static com.neoflex.vacationcalc.exceptions.ErrorCode.PARAMETER_START_DATE_OR_END_DATE_IS_INCORRECT;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {
    private final CalculatorProperties properties;

    @Override
    public VacationPayResponseDto calculate(BigDecimal averageSalary, Integer vacationDays, LocalDate startDate, LocalDate endDate) {
        if (isStartDateProvided(startDate)) {
            validateStartDateVacationParam(startDate, endDate);
            return calculateVacationPay(averageSalary, countPayableDays(vacationDays, startDate, endDate));
        } else {
            return calculateVacationPay(averageSalary, vacationDays);
        }
    }

    private VacationPayResponseDto calculateVacationPay(BigDecimal averageSalary, Integer vacationDays) {
        BigDecimal dailyRate = averageSalary.divide(properties.getAverageMonthDays(), 10, properties.getRoundingMode());
        BigDecimal vacationPay = dailyRate.multiply(BigDecimal.valueOf(vacationDays)).setScale(properties.getRoundingScale(), properties.getRoundingMode());
        return new VacationPayResponseDto(vacationPay);
    }

    private boolean isStartDateProvided(LocalDate startDate) {
        return startDate != null;
    }

    private void validateStartDateVacationParam(LocalDate startDate, LocalDate endDate) {
        if (endDate != null && startDate.isAfter(endDate)) {
            throw new BadRequestException(PARAMETER_START_DATE_OR_END_DATE_IS_INCORRECT.getErrorMessage());
        }
    }

    private Integer countPayableDays(Integer vacationDays, LocalDate startDate, LocalDate endDate) {
        int resultVacationDays = 0;
        if (endDate == null) {
            endDate = startDate.plusDays(vacationDays - 1L);
        }
        if (startDate.equals(endDate)) {
            return isNotHoliday(startDate) ? 1 : 0;
        }
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            if (isNotHoliday(currentDate)) {
                resultVacationDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return resultVacationDays;
    }

    private boolean isNotHoliday(LocalDate date) {
        return date.getDayOfWeek() != DayOfWeek.SATURDAY
                && date.getDayOfWeek() != DayOfWeek.SUNDAY
                && !properties.getHolidaysAsDates().contains(date);
    }

}
