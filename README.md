# Gestión de Parqueadero de Motos

Sistema de consola en Java diseñado para administrar el ingreso, salida y facturación de un parqueadero exclusivo para motocicletas. Implementa una arquitectura por capas y desarrollo guiado por pruebas (TDD) para garantizar la exactitud de las reglas de negocio.

## Características Principales

### Control de Capacidad

Límite estricto de **23 espacios disponibles simultáneamente**.

### Tarifa Dinámica

Cobro automatizado de **40 pesos por minuto de estadía**.

### Cobro Mínimo

Toda moto que ingrese pagará como mínimo el equivalente a **1 minuto**, independientemente del tiempo real de su estadía si esta es menor.

### Reporte Diario

Registro acumulado del total de motos atendidas y del dinero total recaudado durante la jornada.

## Estructura del Proyecto

El código está organizado bajo el dominio `edu.unilibre`, aplicando separación de responsabilidades en tres paquetes lógicos principales:

### pqdatos

Contiene las entidades base del dominio.

#### Moto.java

Almacena los atributos de la motocicleta:

* Cédula del propietario
* Placa
* Marca
* Hora de entrada

La hora de entrada se registra automáticamente mediante `LocalDateTime`.

### pqgestion

Centraliza las operaciones y reglas del parqueadero.

#### GestorParqueadero.java

Administra la lista en memoria de motos activas y se encarga de:

* Validar los cupos disponibles.
* Registrar el ingreso de motocicletas.
* Calcular el tiempo de estadía.
* Procesar la salida de las motocicletas.
* Calcular la facturación.
* Aplicar el cobro mínimo.
* Acumular las estadísticas diarias.

### pqinterfaz

Maneja la interacción con el usuario final.

#### MenuParqueadero.java

Interfaz de consola que captura las entradas del sistema mediante menús interactivos y muestra los resultados generados por el gestor.

## Tecnologías Utilizadas

| Tecnología             | Descripción                                     |
| ---------------------- | ----------------------------------------------- |
| Java                   | Lenguaje principal de desarrollo                |
| JUnit 5                | Framework utilizado para las pruebas unitarias  |
| IntelliJ IDEA          | IDE recomendado para el desarrollo              |
| Arquitectura por capas | Modelo utilizado para separar responsabilidades |
| TDD                    | Metodología de desarrollo guiado por pruebas    |

## Cobertura de Pruebas (TDD)

El repositorio incluye una suite de pruebas automatizadas en `GestorParqueaderoTest.java`, encargada de verificar la estabilidad y exactitud de la lógica central del sistema.

Las pruebas cubren escenarios como:

* Ingreso exitoso de vehículos.
* Bloqueo automático al intentar superar la capacidad de **23 motos**.
* Cálculo exacto de tarifas mediante la simulación de tiempos de estadía específicos.
* Aplicación del cobro mínimo para salidas inmediatas.
* Manejo defensivo contra la inyección de datos nulos al crear objetos.
* Consistencia matemática en la sumatoria del reporte gerencial.

## Ejecución de Pruebas

Para ejecutar las pruebas automatizadas, abre el archivo:

```text
GestorParqueaderoTest.java
```

Posteriormente, presiona las flechas verdes de ejecución que aparecen en el margen izquierdo de IntelliJ IDEA junto a la declaración de la clase.

También es posible ejecutar las pruebas de manera individual para verificar cada regla de negocio por separado.

## Reglas de Negocio

| Regla                    |    Valor |
| ------------------------ | -------: |
| Capacidad máxima         | 23 motos |
| Tarifa por minuto        |  $40 COP |
| Tiempo mínimo facturable | 1 minuto |
| Cobro mínimo             |  $40 COP |
