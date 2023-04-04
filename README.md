# README - Creación de Usuarios con Spring Boot

Este proyecto es una continuacion de las practicas anteriores, cuyo enfoque ha sido dedicado a la creación de usuarios (Metodo POST) implementando Spring Boot, busqueda de datos en una base de datos local para obtener un listado de localidades que concuerde con la busqueda (Metodo GET), y finalmente acceso a una api para obtener la informacion del tiempo en una de las localidades buscadas. 
A continuación se detalla cómo ejecutar y utilizar esta aplicación.

## Requisitos previos

- Java 8 o superior instalado
- Maven instalado

## Ejecución del servidor Spring Boot:
- Descargue o clone este repositorio en su máquina local.
- Abra una terminal o línea de comandos en la carpeta raíz del proyecto.
- Ejecute el siguiente comando: mvn spring-boot:run
- El servidor Spring Boot se lanzará en el puerto 8080.



# Logica BACKEND:
- Se utiliza Service y Controller para interactuar entre si y realizar las operaciones pedidas por el usuario.
- e ha incluido un Archivo LOG donde se registran todas las acciones que realiza el servidor backend.
- Se ha incluido gestion de errores creando una pagina de error personalizada.
- Se utiliza Thymeleaf para enviar informacion de vuelta desde backend y mostrarla en el html.



# METODO POST

## Crear Usuarios (Create an account, http://localhost:8080/FormularioSignUp.html)

En esta parte de la practica se ha deseado implementar un forms con el metodo POST cuyo objetivo es que el usuario introduzca el nombre de usuario que desea crear, la contraseña que va a tener y como opcional su localidad.

**Seguridad**
Se ha implementado seguridad tanto en el HTML del forms como en el backend, de tal forma que solo es posible crear un usuario formado por letras y numeros, solo se pueden añadir al campo de localidad caracteres, nada de letras.
Tambien se comprueba tanto en frontend como en backend que las contraseñas aportadas por el usuario sean iguales.

Para añadir seguridad extra, en caso de que alguien consiguiera acceder a la base de datos de usuarios, las contraseñas son guardadas encriptadas de tal forma que es imposible saber cual es la contraseña de los ususarios.

Por su seguridad, los parametros enviados en el input viajan dentro del protocolo html y no se ven estos parametros en la barra de busqueda de la url, blindandose contra hacker que puedan estar observando el trafico de la red wifi.


**Verificacion y envio de confirmacion para el usuario**

Se utiliza thymeleaf para devolver informacion al usuario tras el post de creacion de usuario con informacion sobre como ha ido.
De esta forma se consigue informar si ha habido un error debido a introducir los parametros mal en los inputs, ya sea introducir caracteres especiales o no aptos en el username o localidad, introducir contraseñas que no sean iguales (lo cual se adviere al usuario mediante un texto en el html que cambia utilizando js segun sea el estado de estos inputs).

Tambien se advierte si no se ha podido crear debido a que ya se encuentra un usuario con ese username creado, y finalmente si todo ha ido bien y ha sido creado sin problemas tambien sera informado de ello.




# METODO GET

## Weather Program (http://localhost:8080/api.html)

Se ha obtenido una base de datos con la ubicacion en latitud y longitud de todas las localidades españolas.
En el apartado para ver el tiempo ("api/html", ultima opcion del menu) se ha incluido un input por el cual el usuario introduce las primeras letras de la localidad que desea buscar. Al pulsar el boton buscar, se activa el metodo GET el cual enviará a BACKEND la informacion que se desea buscar.

**Seguridad**
Lo primero que se hace, tanto en FrontEnd como en Backend, es comprobar que este input esta compuesto solo por letras, nada de numeros ni caracteres especiales.
En caso de encontrarlos, se omite la busqueda y se informa al usuario de que debe quitar aquellos caracteres que no sean letras.

**Verificacion de datos y envio**

Si el input es correcto y solo hay letras, se procede a buscar a traves de nuestra base de datos todas las concurrencias.
En caso de que no haya localidades que empiecen por los caracteres indicados, se devolvera una lista vacia e informará que no se han encontrado resultados.

En caso positivo de encontrar resultados, se devolverá una lista con todos los resultados obtenidos. Para ver el tiempo en estas localidades, el usuario podrá clicar encima del nombre y esto activará el fetch a una api publica para obtener la informacion del tiempo segun la latitud y longitud de estas localidades.