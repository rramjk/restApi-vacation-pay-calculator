package com.neoflex.vacationcalc.service;

import com.neoflex.vacationcalc.dto.VacationPayResponseDto;
import com.neoflex.vacationcalc.exceptions.BadRequestException;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CalculatorService {

    /**
     * Рассчитывает сумму отпускных.
     * Параметры поддерживают следующие сценарии:
     * Без дат {@code averageSalary + vacationDays}. В этом случае количество оплачиваемых дней
     * принимается равным {@code vacationDays}.
     * С датой начала: {@code averageSalary + vacationDays + startDate}. В этом случае
     * {@code endDate} вычисляется как {@code startDate + (vacationDays - 1)} и рассчитывается количество
     * оплачиваемых дней в диапазоне с учётом выходных и праздников.
     * С точным периодом: {@code averageSalary + vacationDays + startDate + endDate}. В этом случае
     * рассчитывается количество оплачиваемых дней в диапазоне {@code [startDate; endDate]} с учётом выходных
     * и праздников. Параметр {@code vacationDays} используется как обязательный параметр контракта и может не
     * участвовать в вычислении количества оплачиваемых дней при наличии {@code endDate}.
     *
     * @param averageSalary средняя зарплата за 12 месяцев; должна быть больше 0
     * @param vacationDays  количество дней отпуска; обязательный параметр; должно быть не меньше 1
     * @param startDate     дата начала отпуска (опционально). Если задана, расчёт выполняется по датам с учётом праздников
     *                      и выходных
     * @param endDate       дата окончания отпуска (опционально). Если не задана при наличии {@code startDate},
     *                      вычисляется из {@code vacationDays} как {@code startDate + (vacationDays - 1)}
     * @return DTO с рассчитанной суммой отпускных
     * @throws BadRequestException если период дат некорректен (например, {@code startDate > endDate})
     *                                                    или входные параметры не удовлетворяют требованиям
     */
    VacationPayResponseDto calculate(BigDecimal averageSalary,
                                     Integer vacationDays,
                                     LocalDate startDate,
                                     LocalDate endDate);
}
