# 003 - Exception Handling

## Tujuan

Memahami bagaimana Spring Boot menangani exception dan bagaimana kita membangun mekanisme exception handling yang konsisten untuk seluruh aplikasi.

---

## Konsep

Pada aplikasi Spring Boot, exception dapat terjadi di berbagai layer, misalnya:

- Validation
- Business Logic
- Database
- External Service (Kafka, Email, Redis, dll)

Tanpa penanganan yang baik, Spring Boot akan mengembalikan error bawaan yang tidak selalu informatif.

Karena itu kita membuat **Global Exception Handler** agar seluruh response error memiliki format yang konsisten.

---

## Alur Exception

```
Client
    │
    ▼
Controller
    │
    ▼
Service
    │
    ▼
Exception
    │
    ▼
DispatcherServlet
    │
    ▼
ExceptionHandlerExceptionResolver
    │
    ▼
GlobalExceptionHandler
    │
    ▼
HTTP Response
```

---

## Global Exception Handler

Spring menyediakan annotation:

```java
@RestControllerAdvice
```

Class yang menggunakan annotation ini akan didaftarkan sebagai Bean dan digunakan untuk menangani exception yang terjadi pada seluruh controller.

Contoh:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

}
```

---

## @ExceptionHandler

Method yang diberi annotation:

```java
@ExceptionHandler(...)
```

akan dipanggil ketika exception dengan tipe tersebut dilempar.

Contoh:

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiErrorResponse> handleValidationException(...) {

}
```

Artinya:

Jika terjadi `MethodArgumentNotValidException`, Spring akan memanggil method tersebut.

---

## Validation Exception

Request

```json
{
    "recipient": "",
    "subject": "",
    "body": ""
}
```

akan menghasilkan:

```
MethodArgumentNotValidException
```

Exception tersebut akan ditangani oleh:

```
GlobalExceptionHandler
```

dan diubah menjadi response:

```json
{
    "status": 400,
    "error": "Validation Failed",
    "errors": [
        ...
    ]
}
```

---

## Business Exception

Selain exception dari Spring, aplikasi juga dapat memiliki exception sendiri.

Contoh:

```
JobNotFoundException

DuplicateJobException

InvalidJobStatusException
```

Semua exception tersebut merupakan **Business Exception**.

---

## BusinessException

Untuk menghindari banyak handler, seluruh exception bisnis diturunkan dari satu parent class.

```
RuntimeException
        ▲
        │
BusinessException
        ▲
        │
 ┌──────┴──────────────┐
 │                     │
 ▼                     ▼
JobNotFoundException   DuplicateJobException
```

Dengan pendekatan ini, GlobalExceptionHandler cukup memiliki satu handler:

```java
@ExceptionHandler(BusinessException.class)
```

Semua turunan `BusinessException` akan otomatis ditangani.

---

## Kenapa BusinessException dibuat abstract?

BusinessException hanya merupakan fondasi.

Class ini tidak pernah dilempar secara langsung.

Yang digunakan adalah turunannya, misalnya:

- JobNotFoundException
- DuplicateJobException

Karena itu class dibuat:

```java
public abstract class BusinessException
```

---

## Kenapa menggunakan RuntimeException?

BusinessException diturunkan dari RuntimeException karena merupakan **unchecked exception**.

Dengan demikian service cukup melakukan:

```java
throw new JobNotFoundException(jobId);
```

tanpa harus mendeklarasikan `throws` pada setiap method.

---

## Struktur Response Error

Seluruh response error menggunakan format yang sama.

```json
{
    "timestamp": "...",
    "status": 404,
    "error": "Job not found",
    "path": "/jobs",
    "errors": []
}
```

Keuntungan:

- Konsisten
- Mudah dipahami client
- Mudah ditambah error code di masa depan

---

## Ringkasan

```
Controller
      │
      ▼
Service
      │
      ▼
throw BusinessException
      │
      ▼
GlobalExceptionHandler
      │
      ▼
ResponseEntity<ApiErrorResponse>
      │
      ▼
HTTP Response
```

---

## Yang Dipelajari

- RuntimeException
- Custom Exception
- BusinessException
- Inheritance (`extends`)
- `super`
- `abstract`
- `private`
- `protected`
- `final`
- Generic (`ResponseEntity<T>`)
- `@RestControllerAdvice`
- `@ExceptionHandler`
- Separation of Concerns