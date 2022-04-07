java-backend-project

### Татьяна Шаблинская
### Junior Java developer
### Мой профиль в линкедине: [Профиль](https://www.linkedin.com/in/tatsiana-shablinskaya-546698216/)

В данном проекте были реализованы 4 сервиса и 1 доп.библиотека:
* auth-сервис аутентификации и авторизации
* legals-сервис добавления юр.лиц и получения информации
* employees-сервис добавления сотрудников юр.лиц и получения информации
* application-сервис обработки заявок от сотрудников юр.лиц

###### auth-service:
* Реализован эндпоинт регистрации пользователя
* Реализован эндпоинт авторизации по логину и паролю
* Эндпоинт для проверки текущих сессий пользователя
* Эндпоинт для логаута, завершающий все текущие сессии пользователя

###### legals-service
* Реализован эндпоинт для добавления юр.лица
* Эндпоинт для получения юр.лица по айди
*  Эндпоинт по просмотру полноценного списка юр.лиц по странично, согласно настроенной пагинации
*  Эндпоинт по поиску фирмы

###### employees-service
* Реализован эндпоинт для добавления сотрудника юр.лица
* Эндпоинт по просмотру деталей сотрудника компании по айди
* Эндпоинт по поиску сотрудника по УНП, Имени компании
* Эндпоинт по просмотру списка сотрудников компании


###### application-service
* Эндпоинт для загрузки файла заявки в .csv формате
* Эндпоинт по просмотру деталей заявления от сотрудника компании по айди
* Эндпоинт по просмотру списка заявок на конверсию сотрудников компании
* Эндпоинт для изменения статуса заявки 
* Эндпоинт по обработке заявки (изменить  статус заявления по заданным параментрам) - привязать заявление на конверсию к компании


###### common-library
* Сервис для авторизации при вызове эндпоинтов другими эндпоинтами

###### Покрытие тестами
. 100% покрытие эндпоинтов Интеграционными тестами
. 90% покрытие классов
