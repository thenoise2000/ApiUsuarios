
Api rest para logueo y registro de usuarios por tokens de accesos

Se desarrollo una API REST para el registro de usuarios con Spring Boot y empleando una DB en memoria H2.


Herramientas

- Java 11

- SpringBoot

- JWT

- Hibernate

- H2

- Maven

- Junit

- Swagger2

- Mapstruct



Para instalar

Clonamos o desacargamos el repositorio git clone https://github.com/thenoise2000/ApiUsuarios
Dirijase a la raiz del proyecto: cd ApiUsuarios
Compilamos: mvn clean install
Ejecutamos mvn spring-boot:run

Para comprobar que la app esta funcionando correctamente dirijase a la direccion web:
http://localhost:8080/api/swagger-ui/#/


Para desplegarlo ejecute

comando
  mvn spring-boot:run

Para utilizar esta api primero debera ingresar los datos de autenticacion en formato json al endpoint

POST http://localhost:8080/api/auth/register 

La app usa una base de datos en memoria H2, y tiene un registro previo de un usuario para poder autenticarse la primera vez y obtener un token.

Ingrese estos datos en formato json:

{
    "email":"juan@rodriguez.org",
    "password":"$Admin=25"
}

En la seccion de respuesta generara un token debes copiarlo y dirigirse a la seccion Authentication de postman, selecciona Type -> Bearer Token y luego en el campo Token de la derecha pega el token generado.

A continuacion escriba en la barra de direcciones de postman 

POST http://localhost:8080/api/manage/users  

 Antes debe incluir el token en el encabezado de la solicitud HTTP utilizando el siguiente formato:

Authorization: Bearer {token}

Si el token no es válido o ha expirado, la API devolverá un mensaje de error y un código de estado HTTP 401.


a continuacion un ejemplo:


Ingrese los datos del usuario de esta forma:

name: Nombre del usuario.
email: Correo electrónico del usuario. formato de ejemplo aaaaaaa@dominio.cl.
password: Contraseña del usuario entre 10 y 20 caracteres entre amyusculas minusculas caracteres especiales.
phones: Lista de objetos de teléfono que contiene los siguientes campos:
  number: Número telefonico.
  citycode: Código de ciudad del teléfono.
  countrycode: Código de país del teléfono.

La respuesta del endpoint seria de esta forma:

id: ID del usuario generado por el sistema. Se utiliza UUID.
created: Fecha de creación del usuario.
modified: Fecha de última modificación del usuario.
lastLogin: Fecha de último inicio de sesión del usuario.
token: Token de acceso a la API generado por el sistema. Se utiliza JWT.
isActive: Indica si el usuario sigue habilitado en el sistema.

```
En caso de éxito, el endpoint retorna el código de estado HTTP 201 (Creado) y los campos del usuario. 

Si el correo electrónico ya existe en la base de datos, retorna el código de estado HTTP 409 (Conflicto) y un mensaje de error indicando que el correo ya está registrado. 

Si el correo electrónico o la contraseña no cumplen con el formato requerido, retorna el código de estado HTTP 400 (Solicitud incorrecta) y un mensaje de error indicando el problema.


Para ejecutar tests, utilice el comando:

```bash
  mvn test
```




