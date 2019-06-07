# TFM - Plataforma de Facturación como Servicio - Microservicio de Usuarios
#### Back-end con Tecnologías de Código Abierto (SPRING)
#### [Máster en Ingeniería Web por la U.P.M.](http://miw.etsisi.upm.es)
[![Build Status]
[![Quality Gate]
> Proyecto Back-end completo para el uso de la tecnología Spring.  
> Api en acción: 
> Web en acción:
> Ejecución en local:
> * Se debe tener arrancado el motor de MongoDB: ``  
> * Ejecutar el **API** en linea de comando, mediante: `> mvn clean spring-boot:run`  

## Tecnologías necesarias
`Java` `Maven` `Spring` `Mongodb`

### Clonar el proyecto
 Clonar el repositorio en tu equipo, **mediante consola**:
```sh
> cd <folder path>
> git clone https://github.com/dlandyr/tfm-pwfs-usuarios
```
Importar el proyecto mediante **IntelliJ IDEA**
1. **Import Project**, y seleccionar la carpeta del proyecto.
1. Marcar **Create Project from external model**, elegir **Maven**.
1. **Next** … **Finish**.

## Presentación
Este proyecto pertenece al microservicio de Usuario que junto con otros forman parte del sistema "Plataforma Web de Facturación como
Servicio". Se parte de una versión inicial y se pretende ampliar el contenido usando Github como sistema de control de versiones. El 
objetivo princial de este TFM es desarrollar el back-end que permita gestionar un proceso de facturación mediante APIS utilizando 
los modulos principales del sistema: Clientes, Productos, Proveedores y Facturación

## Ecosistema
`Git` `GitHub` `Travis-CI` `Sonarclud` `Heroku` `mLab`
> Se utilizará un flujo de trabajo ramificado (_**Git Workflow**_).
> Las historias se organizan como un **proyecto** de tipo **Automated kanban**.
> Cada **historia** se dividirá en **tareas**, cada **tarea** será una **issue#**, que será el nombre de la **rama**.  
> **Se recomienda aportaciones frecuentes a la rama `develop`**

### Metodología de trabajo
:one: Organización de la **historia** y **tareas** en el proyecto de GitHub mediante **notas**. Elegir la **nota** a implementar, convertirla en **issue#** y configurarla  
:two: Mirar el estado del proyecto [![Build Status]()]() en [Travis-CI]()  
:three: Sincronizarse con las ramas remotas, 
```sh
> git fetch --all
```
Y si fuera necesario, actualizar la rama **develop** con la remota **origin/develop**:
```sh
> git checkout develop
> git pull origin develop
```
:four: Si se comienza la tarea, se crea la rama y se activa
```sh
> git checkout -b issue#xx
```
 Y si se continúa, y se necesitara actualizar la rama **issue#** con las nuevas incorporaciones de **develop**:
```sh
> git checkout issue#xx
> git merge -m "Merge develop into issue #xx" develop
```
:five: Programar la tarea o una parte de ella, lanzar **TODOS LOS TESTS** y asegurarse que no hay errores. Finalmente, sincronizarse con las ramas remotas:
 ```sh
> git fetch --all
```
Y si necesitamos actualizarnos, se repite el paso :four:  
:six: Actualizar **develop** con nuestro cambios:
```sh
> git checkout develop
> git merge --no-ff -m "Merge issue #xx into develop" issue#xx
```
:seven: Resolver los conflictos, observar el flujo de ramas, y si todo ha ido bien... subirlo 
```sh
> git push --all
 ```
:eight: Si la tarea continua, volver a activar la **rama issue#xx**:
```sh
> git checkout issue#xx
 ```

 ### Travis-CI
Integración continua con **Travis-CI**. Se despliega para pruebas con el servicio de BD de mongodb y ejecución de los test Unitarios y de Integración
```yaml
services:
  - mongodb
script:
- mvn org.jacoco:jacoco-maven-plugin:prepare-agent verify  #Test en el perfil "dev" y con cobertura
```

### Sonarcloud
En el la cuenta de **Sonarcloud**, en la página `-> My Account -> Security`, se ha generado una **API Key**.   
En la cuenta de **Travis-CI**, dentro del proyecto, en `-> More options -> Settings`, se ha creado una variable de entorno llamada `SONAR` cuyo contenido es la **API key** de **Sonar**.    
Se ha incorporado al fichero de `.travis.yml` el siguiente código:
```yml
# Sonarcloud
- mvn sonar:sonar -Dsonar.organization=miw-upm-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR
```

### Swagger
Se monta un cliente swagger para atacar al API: http://localhost:8080/api/v0/swagger-ui.html.
Para ello, se ha introducido una fichero de configuración [SwaggerConfig]()
```
@Configuration @EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
		...
    }
```

### Heroku & mLab
Se realiza un despliegue en **Heroku** con bases de datos de MongoDB en **mLab**.  
En la cuenta de **Heroku**, en la página `-> Account settings -> API Key`, se ha obtenido la `API KEY`.  
En la cuenta de **Travis-CI**, dentro del proyecto, en `-> More options -> Settings`, se ha creado una variable de entorno llamada `HEROKU` cuyo contenido es la **API key** de **Heroku**.  
Se incorpora el siguiente código en el fichero `.travis.yml`
```yaml
# Deploy 
deploy:
  provider: heroku
  api_key:
    secure: $HEROKU
  on:
    branch: master
```
La conexión entre **Heroku** y **mLab** se realiza automáticamente al añadir el **Add-ons**.

## Arquitectura


### Responsabilidades


## Autenticación
Se plantean mediante **Basic Auth** para logearse y obtener un **API Key** o **token** de tipo **JSON Web Tokens (JWT)**. Uso del **Bearer APIkEY** para el acceso a los recursos.  
Para obtener el **API Key** se accede al recurso: `POST \users\token`, enviando por **Basic auth** las credenciales, van en la cabecera de la petición.
Para el acceso a los recursos, se envia el **token** mediante **Bearer auth**, tambien en la cabecera de la petición.
> Authorization = Basic "user:pass"<sub>Base64</sub>  
> Authorization = Bearer "token"<sub>Base64</sub>  

Para asegurar los recursos, se plantea una filosofía distribuida, es decir, se establece sobre cada recursos (clase). Para ello, se anotará sobre cada clase los roles permitidos; modificando el rol sobre el método si éste, tuviese un rol diferente.  
```java
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
public class Clazz {
    //...
    @PreAuthorize("hasRole('ADMIN')")
    public void method(){...}
    //...
    public void method2(){...}
}
```
Existe un rol especial que se obtiene cuando se envía el usuario y contraseña por **Basic Auth** y es el rol de **authenticated**, sólo se utiliza para logearse.

![]()

## Tratamiento de errores
Se realiza un tratamiento de error centralizado.  
![]()

## API. Descripción genérica
[Heroku deploy]()

![]()

## DTOs
Son los objetos de transferencia del API, para recibir los datos (input) y enviar las respuestas (output).

* Los **input** se encargan de las validaciones de entrada mediante las anotaciones.  
* Los **output**. Se deben poder construir a partir de los **documentos**. Añadir la anotación `@JsonInclude(Include.NON_NULL)` para que no se devuelvan null en el Json.

![]()   

## Bases de datos
> Se dispone de un servicio para poblar la BD a partir de un fichero YML `db.yml`; se carga automáticamente al iniciar la aplicación en el perfil **dev**.  
> Existe el recurso `/admins/db` para poder borrar o poblar la BD a partir de un fichero yaml subido.  
> El servicio `DatabaseSeederService` nos permiter recargar el fichero `db.yml`.  
> Se debe intentar no abusar de la recarga del **yaml**, ya que ralentiza la ejecución de los tests.

:exclamation: **Si se crea un nuevo _documento_, se debe añadir el `deleteAll()` asociado al nuevo documento, dentro del método `deleteAllAndInitialize` de la clase `DatabaseSeederService`**

Los pasos a seguir para incluir un nuevo documento en la carga de datos a través del fichero `db.yml`:
1. Rellenar el YML con los datos del nuevo documento.  
1. Incluir en la clase `TpvGraph`, la **lista** del nuevo documento con **getters & setters**.  
1. Incluir en la clase `DatabaseSeederService`, en el médoto `seedDatabase`, el `saveAll` del repositorio del nuevo documento.

![]()

Fichero ** \*.yml** como ejemplo...
```yaml
```

## Persistencia PWFS
![]()
![]()

## Perfiles
![]()  
Si una propiedad se define en diferentes ficheros, predomina la definición mas específica.  
A cualquier clase se le puede poner la anotación `@Profile()`, con ello indicamos que sólo se configura en el perfil marcado.  
En el sistema PWFS, los **test** siempre se ejecutan en el perfil `dev`, y los `ApiLog` también el el perfil `dev`.  
Por defecto el perfil es `dev`, pero se puede indicar como una propiedad en **application.properties**: `spring.profiles.active=dev`.  
Sólamente en la rama `release-xx` cambiaremos este valor a `prod`
Para ejecutar en un perfil determinado localmente:
```sh
> mvn spring-boot:run
> mvn -Dspring.profiles.active=dev spring-boot:run

> mvn -Dspring.profiles.active=prod spring-boot:run
> java –jar –Dspring.profiles.active=prod release-1.0.0.jar 

``` 

# Metodología de trabajo

## Fase 1. Preparar el IDE
> Se debe utilizar `IntelliJ` & `Web Storm`.  
> Para `IntelliJ`, todo el código debe estar formateado mediante: `-> Code -> Reformat Code`, con los chekbox `[x]Optimize imports` y `[x]Rearrange entries` activados.  
> Para `Web Storm`, todo el código debe estar formateado mediante: `-> Code -> Reformat Code`, con los chekbox `[x]Optimize imports`, `[x]Rearrange entries` y `Cleanup code` activados.  

## Fase 2. Importar los proyectos
## Fase 2. Importar los proyectos de cada microservicio
* Microservicio de Clientes: https://github.com/
* Microservicio de Productos: https://github.com/
* Microservicio de Facturacion: https://github.com/


## Fase 3. Determinar y limitar el alcance de la práctica  

---

## Fase 4. Gestionar el desarrollo  

   
## Tareas transversales

