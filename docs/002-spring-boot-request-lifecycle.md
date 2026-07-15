# 002 - Spring Boot Request Lifecycle

## Tujuan

Memahami bagaimana sebuah HTTP request diproses oleh Spring Boot hingga menjadi HTTP response.

---

## Arsitektur

```
                 HTTP Request
                       │
                       ▼
              Embedded Tomcat
                       │
                       ▼
            DispatcherServlet
                       │
                       ▼
              Handler Mapping
                       │
                       ▼
              JobController
                       │
        JSON → CreateJobRequest
                       │
                       ▼
          NotificationService
                       │
               Business Logic
                       │
                       ▼
         CreateJobResponse
                       │
       CreateJobResponse → JSON
                       │
                       ▼
              HTTP Response
```

---

## Penjelasan

### 1. Embedded Tomcat

Tomcat menerima HTTP Request dari client.

Contoh:

```
POST /jobs
```

Tomcat merupakan web server yang berjalan di dalam aplikasi Spring Boot.

---

### 2. DispatcherServlet

DispatcherServlet adalah Front Controller pada Spring MVC.

Tugasnya:

- menerima seluruh request
- mencari controller yang sesuai
- menjalankan controller
- mengembalikan response

Semua request HTTP melewati DispatcherServlet.

---

### 3. Handler Mapping

Spring mencari method yang cocok berdasarkan annotation.

Contoh:

```java
@RestController
@RequestMapping("/jobs")
public class JobController {

    @PostMapping
    public CreateJobResponse createJob(...) {
    }

}
```

Request:

```
POST /jobs
```

akan dipetakan ke method `createJob()`.

---

### 4. RequestBody Deserialization

Jackson mengubah JSON menjadi object Java.

JSON

```json
{
    "type":"EMAIL",
    "recipient":"alfin@example.com"
}
```

menjadi

```java
CreateJobRequest
```

melalui annotation

```java
@RequestBody
```

---

### 5. Controller

Controller bertugas:

- menerima request
- memanggil service
- mengembalikan response

Controller sebaiknya tidak berisi business logic.

---

### 6. Service

Service berisi business logic aplikasi.

Contoh:

- generate UUID
- validasi bisnis
- publish Kafka
- simpan database

---

### 7. Response Serialization

Object Java

```java
CreateJobResponse
```

diubah kembali menjadi JSON oleh Jackson.

---

### 8. HTTP Response

Spring mengirim response kembali ke client.

Contoh:

```
HTTP/1.1 201 Created
```

Body:

```json
{
    "jobId":"...",
    "status":"PENDING"
}
```

---

# Bean yang Terlibat

Saat aplikasi startup, Spring membuat Bean berikut:

```
ApplicationContext

├── JobController
├── NotificationService
├── DispatcherServlet
├── ObjectMapper
├── HandlerMapping
└── ...
```

Bean tersebut digunakan kembali untuk setiap request.

Sedangkan object berikut **bukan Bean**:

- CreateJobRequest
- CreateJobResponse
- UUID

Object tersebut dibuat baru setiap request.

---

# Ringkasan

```
Client
    │
    ▼
Tomcat
    │
    ▼
DispatcherServlet
    │
    ▼
HandlerMapping
    │
    ▼
Controller
    │
    ▼
Service
    │
    ▼
Response DTO
    │
    ▼
JSON
    │
    ▼
Client
```

---

# Yang Dipelajari

- Embedded Tomcat
- DispatcherServlet
- Spring MVC
- Handler Mapping
- Jackson Serialization
- Jackson Deserialization
- Controller
- Service
- Response DTO
- HTTP Request Lifecycle