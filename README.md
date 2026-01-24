# neoflex-vacation-pay-calculate-service
Приложение "Калькулятор отпускных".  
Микросервис на SpringBoot + Java 11 c одним API:  
GET "/calculate"  
  
Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.  
Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных с учётом праздников и выходных.  
  
Проверяться будет чистота кода, структура проекта, название полей\классов, правильность использования паттернов. Желательно написание юнит-тестов, проверяющих расчет.  

# API requests
Простой запрос:  
[http://localhost:8080/calculate?averageSalary=30500.00&vacationDays=30](http://localhost:8080/calculate?averageSalary=30500.00&vacationDays=30)
  
Запрос с указанием точного дня ухода в отпуск:  
[http://localhost:8080/calculate?averageSalary=120000&vacationDays=10&startDate=2024-05-01&endDate=2024-05-10](http://localhost:8080/calculate?averageSalary=120000&vacationDays=10&startDate=2024-05-01&endDate=2024-05-10)  
