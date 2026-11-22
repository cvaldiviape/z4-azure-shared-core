# z4-shared-core

Librería con las respuestas HTTP y el manejo de errores comunes a los servicios Z4. No es un servicio: no arranca, no tiene puerto ni base de datos.

## Qué contiene

| #   | Clase                      | Qué hace                                                    |
|-----|----------------------------|-------------------------------------------------------------|
| 1   | `ResponseDto`              | Forma estándar de todas las respuestas JSON                 |
| 2   | `PageResponseDto`          | Forma estándar de una lista paginada                        |
| 3   | `ErrorCode`                | Interfaz que implementa el `ErrorCodeEnum` de cada servicio |
| 4   | `BusinessException`        | Error de negocio esperado, con su `ErrorCode`               |
| 5   | `CoreErrorCode`            | Errores comunes (`VALIDATION_ERROR`)                        |
| 6   | `Z4GlobalExceptionHandler` | Convierte las excepciones en `ResponseDto`                  |

`Z4CoreAutoConfiguration` registra el manejador de errores automáticamente en cualquier servicio web que agregue la dependencia.

## Instalar en local

Las librerías se compilan con Java 21. Si tu terminal usa otra versión (revisa con `java -version`), cámbiala primero:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

Después instala:

```bash
./mvnw install
```

Si te olvidas del `export`, Maven falla con `class file version 65.0 ... only recognizes class file versions up to 61.0`: 65 es Java 21 y 61 es Java 17.

Queda en `~/.m2/repository/com/z4greed/z4-shared-core`. Después de cambiar algo en la librería hay que volver a instalarla para que los servicios lo vean.

## Usar en un servicio

```xml
<dependency>
    <groupId>com.z4greed</groupId>
    <artifactId>z4-shared-core</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

El `ErrorCodeEnum` del servicio implementa `ErrorCode`:

```java
@Getter
@RequiredArgsConstructor
public enum ErrorCodeEnum implements ErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Order not found");

    private final HttpStatus httpStatus;
    private final String message;
}
```

Y lanza `throw new BusinessException(ErrorCodeEnum.ORDER_NOT_FOUND);`.

## Qué no entra aquí

Nada de negocio (entidades, enums de un servicio, reglas) ni configuración de Kafka o seguridad: esas viven en `z4-shared-kafka` y `z4-shared-security`.
