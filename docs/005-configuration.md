# 005 - Configuration

## Tujuan

Memahami bagaimana Spring Boot membaca konfigurasi aplikasi dan mengubahnya menjadi object Java yang dapat digunakan melalui Dependency Injection.

---

## Konsep

Spring Boot memisahkan **konfigurasi** dari **kode aplikasi**.

Konfigurasi disimpan pada file `application.yml`, kemudian di-bind menjadi object Java menggunakan `@ConfigurationProperties`.

```
application.yml
        │
        ▼
Environment
        │
        ▼
@ConfigurationProperties
        │
        ▼
Binder
        │
        ▼
Java Object (Bean)
        │
        ▼
Dependency Injection
```

---

## application.yml

Contoh konfigurasi:

```yaml
app:
  notification:
    max-retry: 3
    default-sender: noreply@asyncjob.com
```

Spring Boot akan membaca file ini saat aplikasi dijalankan.

---

## @ConfigurationProperties

```java
@ConfigurationProperties(prefix = "app.notification")
public class NotificationProperties {

    private int maxRetry;

    private String defaultSender;

    // getter & setter
}
```

Keterangan:

- `prefix` menentukan bagian konfigurasi yang akan dibaca.
- Spring melakukan binding secara otomatis berdasarkan nama property.

Contoh mapping:

| YAML           | Java          |
| -------------- | ------------- |
| max-retry      | maxRetry      |
| default-sender | defaultSender |

Fitur ini disebut **Relaxed Binding**.

---

## Enable Configuration Properties

Agar Spring membuat Bean tersebut:

```java
@EnableConfigurationProperties(NotificationProperties.class)
```

Spring kemudian akan:

1. Membuat object `NotificationProperties`.
2. Mengisi nilainya dari `application.yml`.
3. Menyimpannya di `ApplicationContext`.

---

## Dependency Injection

Bean konfigurasi dapat langsung di-inject.

```java
@Service
public class NotificationService {

    private final NotificationProperties properties;

    public NotificationService(NotificationProperties properties) {
        this.properties = properties;
    }

}
```

Tidak perlu membaca file YAML secara manual.

---

# Auto Configuration

Spring Boot memiliki fitur **Auto Configuration**.

Saat aplikasi dijalankan, Spring akan:

1. Memeriksa dependency pada classpath.
2. Membaca konfigurasi dari `application.yml`.
3. Mengevaluasi berbagai kondisi (`@Conditional...`).
4. Membuat Bean yang diperlukan secara otomatis.

Diagram:

```
Dependency
        │
        ▼
Auto Configuration
        │
        ▼
Conditional Evaluation
        │
        ▼
Register Bean
        │
        ▼
ApplicationContext
```

---

## Contoh

Jika project memiliki:

- `spring-boot-starter-data-jpa`
- PostgreSQL Driver
- konfigurasi `spring.datasource`

maka Spring Boot akan otomatis membuat:

- DataSource
- Hikari Connection Pool
- Transaction Manager

tanpa perlu menulis konfigurasi Java.

Demikian pula untuk komponen lain seperti:

- ObjectMapper
- Validator
- TaskExecutor
- DispatcherServlet

---

## Conditional Auto Configuration

Auto Configuration menggunakan berbagai kondisi, misalnya:

- `@ConditionalOnClass`
- `@ConditionalOnProperty`
- `@ConditionalOnMissingBean`

Contoh:

```
Jika ObjectMapper belum dibuat oleh developer,
maka Spring Boot akan membuat ObjectMapper bawaan.
```

Sebaliknya, jika developer sudah membuat Bean sendiri, maka Spring Boot tidak akan membuat Bean default.

---

## Best Practices

- Simpan seluruh konfigurasi aplikasi di `application.yml`.
- Gunakan `@ConfigurationProperties` untuk konfigurasi yang memiliki banyak field.
- Hindari membaca file konfigurasi secara manual.
- Gunakan Dependency Injection untuk mengakses konfigurasi.
- Manfaatkan Auto Configuration sebelum membuat konfigurasi sendiri.

---

## Ringkasan

```
application.yml
        │
        ▼
Environment
        │
        ▼
@ConfigurationProperties
        │
        ▼
Binder
        │
        ▼
Bean
        │
        ▼
Dependency Injection
```

Spring Boot menggunakan mekanisme binding dan auto configuration untuk mengurangi boilerplate code. Developer cukup mendefinisikan konfigurasi dan dependency, sedangkan proses pembuatan object dilakukan secara otomatis oleh framework.