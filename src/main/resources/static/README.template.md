# Автор: @MD@author.personal.last-name@MD@ @MD@author.personal.first-name@MD@ @MD@author.personal.middle-name@MD@ @MD@author.addition.university.group@MD@


# Лабораторная работа №4 (*[github](https://github.com/1unee/laboratoy-work-4.git)*)


Условие лабораторной работы представлено в директории `src/main/resources/static/Лабораторная_модуль_4_Java_0.docx`.

Проект представляет собой CRUD-сервис для сущности пользователя.


## Фичи:
1) __JWT авторизация__ (*use-case смотри ниже*);
2) __Кеширование эндпоинта__ `getAll` в `UserController` (*чтобы хоть как-то оптимизировать получение сразу всех пользователей*);
3) __Автогенерация API документации__ с помощью `springdoc-openapi` (*ознакомиться тут => __[OPEN API DOCS](http://localhost:@MD@server.port@MD@/user-api/swagger-ui/index.html#/)__*);
3) __Автогенерация README файла__ на основе properties.yml с помощью `ReadmeFileGeneratorUtil`;
4) __Docker-file__.


## Use-case JWT авторизации
1) Обращаемся [по этому адресу](http://localhost:@MD@server.port@MD@@MD@spring.mvc.servlet.path@MD@/auth/@MD@server.api.version.auth@MD@/sign-in?addAdminRole=false) с __POST__ запросом (*в request body передаем `username` и `password` - __{
   "username": "Igor",
   "password": "12345"
   }__ *);
   ![img.png](src/main/resources/static/readme_images/img.png)
2) В ответ получаем `AuthorizationDto`, где будет лежать сгенерированный JWT токен;
   ![img_1.png](src/main/resources/static/readme_images/img_1.png)
3) Используем сгенерированный JWT токен для взаимодействия с сервисом
   ![img_2.png](src/main/resources/static/readme_images/img_2.png)

## WARNING FOR USE-CASE
### __Замечание 1__:
Скриншоты выше приведены для конкретных настроек в `application.yml` (актуальные могут отличаться).
### __Замечание 2__:
По дефолту при регистрации нового пользователя (*и его дальнейшей автоматической авторизации*) выдается только роль `USER` (*админку можно выдать дополнительной переменной запроса `addAdminRole`*), поэтому, соответсвенно, пинать можно только разрешенные эндпоинты.

