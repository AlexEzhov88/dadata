Чтобы запустить проект в idea community выбери RUN/DEBUG => Edit Configuration => Active profiles:

введи строку без кавычек "--spring.profiles.active=dev"
введи строку без кавычек "--spring.profiles.active=test"

Откройте "Edit Configurations..." для вашего Spring Boot приложения.
В поле "Environment variables" добавьте DADATA_API_TOKEN=your_token_here.
Примените изменения и запустите приложение.

###

POST http://localhost:8080/api/v2/suggest/fio-suggestions
Content-Type: application/json
Accept: application/json
Authorization: Token 4677795b826cf780cd8540ef4a016cef7dab0038

{
  "query": "Иван",
  "count": 1
}

<> 2024-03-15T164716.200.json
{
  "suggestions": [
    {
      "fullName": "Иван",
      "gender": "MALE"
    }
  ]
}


###
POST http://localhost:8080/api/v2/suggest/address-suggestions
Content-Type: application/json
Accept: application/json
Authorization: Token 4677795b826cf780cd8540ef4a016cef7dab0038

{
  "query": "Москва",
  "count": 5
}

<> 2024-03-15T164720.200.json
{
  "suggestions": [
    {
      "value": "г Москва",
      "country": "Россия",
      "city_with_type": "Москва",
      "postal_code": "101000"
    },
    {
      "value": "Тверская обл, пгт Пено, деревня Москва",
      "country": "Россия",
      "city_with_type": "Пено",
      "postal_code": "172796"
    },
    {
      "value": "Московская обл, г Ногинск, тер. СНТ Вечерняя Москва",
      "country": "Россия",
      "city_with_type": "Ногинск",
      "postal_code": "142460"
    },
    {
      "value": "Кировская обл, Верхошижемский р-н, деревня Москва",
      "country": "Россия",
      "city_with_type": "",
      "postal_code": "613310"
    },
    {
      "value": "Псковская обл, Порховский р-н, деревня Москва",
      "country": "Россия",
      "city_with_type": "",
      "postal_code": "182650"
    }
  ]
}
###