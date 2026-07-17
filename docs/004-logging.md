# 004 - Logging

## Tujuan

Memahami mekanisme logging di Spring Boot dan menerapkan logging sebagai alat observasi aplikasi.

---

## Kenapa Logging Penting?

Logging membantu kita mengetahui apa yang sedang terjadi di dalam aplikasi.

Contohnya:

- Request masuk
- Proses bisnis dimulai
- Proses selesai
- Terjadi error

Tanpa logging, proses debugging menjadi jauh lebih sulit, terutama di lingkungan production.

---

## Arsitektur Logging Spring Boot

```
Application
      │
      ▼
SLF4J (API)
      │
      ▼
Logback (Implementation)
      │
      ▼
Console / File
```

### SLF4J

SLF4J (Simple Logging Facade for Java) adalah **API logging**.

Application tidak berkomunikasi langsung dengan Logback, tetapi melalui SLF4J.

### Logback

Logback adalah implementasi logging bawaan Spring Boot yang bertugas mencetak log ke console atau file.

---

## Membuat Logger

Setiap class yang membutuhkan logging biasanya memiliki logger sendiri.

```java
private static final Logger log =
        LoggerFactory.getLogger(NotificationService.class);
```

Keterangan:

- `private` → hanya digunakan di dalam class.
- `static` → hanya dibuat satu kali untuk setiap class.
- `final` → logger tidak dapat diganti.
- `LoggerFactory` → membuat object Logger.
- `NotificationService.class` → nama class yang akan muncul pada log.

---

## Logging Level

Spring Boot menyediakan beberapa level logging.

| Level | Kegunaan                                                 |
| ----- | -------------------------------------------------------- |
| TRACE | Informasi sangat detail untuk debugging framework.       |
| DEBUG | Informasi debugging aplikasi.                            |
| INFO  | Informasi proses bisnis normal.                          |
| WARN  | Kondisi yang tidak ideal tetapi aplikasi masih berjalan. |
| ERROR | Terjadi kegagalan atau exception.                        |

---

## Placeholder Logging

Disarankan menggunakan placeholder.

Benar:

```java
log.info("Recipient: {}", request.recipient());
```

Hindari:

```java
log.info("Recipient: " + request.recipient());
```

Placeholder membuat proses formatting log lebih efisien dan menjadi standar yang digunakan pada ekosistem Java.

---

## Konfigurasi Logging

Logging dapat dikonfigurasi melalui `application.yml`.

Contoh:

```yaml
logging:
  level:
    root: INFO

  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{36} - %msg%n"
```

Keuntungan:

- Mengubah level log tanpa mengubah kode.
- Mengubah format output log.
- Dapat dibedakan antara environment Development dan Production.

---

## Best Practices

- Gunakan `Logger` pada setiap class yang membutuhkan logging.
- Gunakan placeholder (`{}`) daripada string concatenation.
- Gunakan level log yang sesuai (`INFO`, `WARN`, `ERROR`).
- Jangan menggunakan `System.out.println()` untuk logging aplikasi.
- Simpan konfigurasi logging di `application.yml`.

---

## Ringkasan

```
Application
      │
      ▼
SLF4J
      │
      ▼
Logback
      │
      ▼
Console / File
```

Logging merupakan salah satu fondasi observability pada aplikasi backend dan akan menjadi dasar ketika nanti mempelajari distributed tracing, monitoring, dan centralized logging.