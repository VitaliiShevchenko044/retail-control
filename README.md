RETAIL CONTROL — це система обліку товарів для магазину.

Retail Control — це Java-програма для обліку товарів у магазині, з підтримкою сканування штрихкодів, 
завантаження зображень товарів, перегляду й редагування даних через веб-інтерфейс. 
Дані зберігаються в базі PostgreSQL. Програма підходить для невеликих роздрібних торгових точок.

Технології:

  - Java 21
  - Spring Boot
  - Spring Data JPA
  - PostgreSQL
  - Thymeleaf
  - Maven
  - HTML/CSS
  - JavaScript 

Основні можливості:

  - Додавання, редагування, видалення товарів
  - Завантаження зображень для товарів
  - Пошук товарів за штрихкодом
  - Сканування штрихкодів через Bluetooth-сканер
  - Підтримка PostgreSQL
  - Простий веб-інтерфейс для користувача

Як запустити:
  1. Клонуйте репозиторій:
      git clone https://github.com/VitaliiShevchenko044/retail-control.git
  3. Створіть базу даних PostgreSQL;
  4. Налаштуйте файл application.properties:
      spring.datasource.url=jdbc:postgresql://localhost:5432/retail_control
      spring.datasource.username=postgres
      spring.datasource.password=your_password
  5. Зберіть проект і запустіть його за допомогою Maven.
     
Контакти:  
  - GitHub: https://github.com/VitaliiShevchenko044
  - Email: v.shevchenko044@gmail.com
