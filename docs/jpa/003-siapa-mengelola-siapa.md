# Siapa Mengelola Siapa?

Salah satu kesalahan yang paling sering terjadi adalah menganggap Spring Boot mengelola semua object di aplikasi.

Padahal ada dua "dunia" yang berbeda.

```
                    Spring Boot

                         │

        +----------------+----------------+
        |                                 |
        ▼                                 ▼

ApplicationContext                 Hibernate
(Spring Container)             (JPA Implementation)

        │                                 │

        │                                 ▼

        │                       Persistence Context

        │                                 │

        ▼                                 ▼

Controller                      Job Entity

Service                         User Entity

Repository                      Order Entity

Configuration                   Product Entity

Properties
```

---

## Dunia Spring

Spring bertanggung jawab mengelola **Bean**.

Contohnya:

```
@RestController

@Service

@Repository

@Configuration

@Component

@ConfigurationProperties
```

Saat startup:

```
SpringApplication.run()

↓

Component Scan

↓

Instantiate Bean

↓

ApplicationContext
```

Misalnya:

```
ApplicationContext

├── JobController

├── NotificationService

├── NotificationProperties

├── JobRepository (Proxy)

└── EntityManager
```

Bean ini biasanya hanya dibuat **satu kali** selama aplikasi berjalan (Singleton).

---

## Dunia Hibernate

Hibernate tidak mengelola Bean.

Hibernate hanya mengelola **Entity**.

Misalnya:

```
Job

User

Order

Invoice
```

Entity mulai dikelola ketika:

```
repository.findById()

atau

repository.save()
```

Saat itu Entity masuk ke:

```
Persistence Context
```

dan statusnya menjadi:

```
Managed
```

---

## Contoh Nyata

Misalnya ada request:

```
POST /jobs
```

Yang terjadi:

```
Spring

↓

NotificationController

↓

NotificationService

↓

JobRepository
```

Kemudian:

```
Hibernate

↓

new Job()

↓

INSERT INTO jobs
```

Controller, Service, dan Repository tetap hidup di dalam Spring Container.

Sedangkan object `Job` hanya hidup selama transaction berlangsung.

---

## Umur Object Berbeda

Bean Spring:

```
Application Start

↓

Created

↓

Digunakan berkali-kali

↓

Application Stop
```

Entity Hibernate:

```
new Job()

↓

save()

↓

Managed

↓

Detached

↓

Garbage Collected
```

Entity biasanya memiliki umur yang jauh lebih pendek dibanding Bean.

---

## Analogi

Bayangkan sebuah restoran.

```
Spring Boot

↓

Manajer Restoran
```

Manajer mengatur:

- Kasir
- Pelayan
- Koki

Mereka bekerja setiap hari.

Sedangkan Hibernate seperti:

```
Gudang Bahan Makanan
```

Gudang hanya mengelola:

- Tomat
- Ayam
- Daging
- Beras

Ketika pesanan datang:

```
Pelayan

↓

Koki

↓

Mengambil bahan dari gudang

↓

Memasak

↓

Selesai
```

Pelayan bukan bagian dari gudang.

Gudang juga tidak mengatur pelayan.

Persis seperti:

```
Spring
```

dan

```
Hibernate
```

---

# Ringkasan

Spring mengelola **Bean**.

```
Controller

Service

Repository

Configuration

Properties
```

Hibernate mengelola **Entity**.

```
Job

Order

User

Invoice
```

Keduanya bekerja sama, tetapi memiliki tanggung jawab yang berbeda.

Spring fokus pada **Dependency Injection** dan lifecycle Bean.

Hibernate fokus pada **Persistence**, **Entity Lifecycle**, dan sinkronisasi object Java dengan database.