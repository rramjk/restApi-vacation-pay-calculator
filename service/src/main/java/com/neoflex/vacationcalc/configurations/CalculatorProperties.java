package com.neoflex.vacationcalc.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Configuration
@ConfigurationProperties(value = "calculator-neoflex")
public class CalculatorProperties {
    private Set<String> holidays;
    private BigDecimal averageMonthDays;
    private RoundingMode roundingMode;
    private Integer roundingScale;

    private Set<LocalDate> holidaysCache;

    @PostConstruct
    void init() {
        int yearNow = LocalDate.now().getYear();
        this.holidaysCache = holidays == null
                ? Set.of()
                : holidays
                .stream()
                .map(LocalDate::parse)
                .map(date -> actualizeYear(date, yearNow))
                .collect(Collectors.toUnmodifiableSet());
    }

    public Set<LocalDate> getHolidaysAsDates() {
        return holidaysCache;
    }

    private LocalDate actualizeYear(LocalDate date, int yearNow) {
        if (date.getYear() != yearNow) {
            date = LocalDate.of(yearNow, date.getMonth(), date.getDayOfMonth());
        }

        return date;
    }
}
