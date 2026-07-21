# Lifecycle Spring vs Hibernate

Salah satu cara termudah memahami Spring Boot dan Hibernate adalah dengan melihat lifecycle object yang mereka kelola.

```
                         APPLICATION START
                                │
                                ▼
                  SpringApplication.run(args)
                                │
                                ▼
                  Component Scan + Auto Configuration
                                │
                                ▼
                    ApplicationContext (IoC Container)
                                │
       ┌────────────────────────┼────────────────────────┐
       │                        │                        │
       ▼                        ▼                        ▼
 JobController         NotificationService     JobRepository (Proxy)
       │                        │                        │
       └────────────────────────┼────────────────────────┘
                                │
                     Bean siap menerima request
═══════════════════════════════════════════════════════════════════════

                      HTTP REQUEST MASUK

═══════════════════════════════════════════════════════════════════════
                                │
                                ▼
                     DispatcherServlet
                                │
                                ▼
                      JobController
                                │
                                ▼
                   NotificationService
                                │
                                ▼
                     JobRepository.save()
                                │
                                ▼
                         EntityManager
                                │
                                ▼
                           Hibernate
                                │
                                ▼
                     Persistence Context
                                │
                    +---------------------+
                    |  Job (Managed)      |
                    |  User (Managed)     |
                    |  Order (Managed)    |
                    +---------------------+
                                │
                    Dirty Checking / Flush
                                │
                                ▼
                             JDBC Driver
                                │
                                ▼
                           PostgreSQL
═══════════════════════════════════════════════════════════════════════

                      REQUEST SELESAI

═══════════════════════════════════════════════════════════════════════
                                │
                                ▼
                Transaction Commit / Rollback
                                │
                                ▼
                 Persistence Context dibersihkan
                                │
                                ▼
                  Entity menjadi Detached
                                │
                                ▼
                    Menunggu request berikutnya
```

---

## Yang perlu diperhatikan

Ada dua lifecycle yang berjalan secara bersamaan.

### Lifecycle Bean Spring

```
Application Start

↓

Bean dibuat

↓

Disimpan di ApplicationContext

↓

Dipakai berkali-kali oleh setiap request

↓

Application Stop
```

Bean biasanya hanya dibuat **satu kali** (Singleton).

Contohnya:

- Controller
- Service
- Repository
- Configuration
- Properties

---

### Lifecycle Entity Hibernate

```
Request datang

↓

Entity dibuat / di-load

↓

Managed

↓

Dirty Checking

↓

Commit

↓

Detached

↓

Garbage Collected
```

Entity **tidak hidup selama aplikasi berjalan**.

Entity hanya hidup selama proses persistence.

---

## Perbedaan utama

| Spring Boot                     | Hibernate                     |
| ------------------------------- | ----------------------------- |
| Mengelola Bean                  | Mengelola Entity              |
| ApplicationContext              | Persistence Context           |
| Dependency Injection            | Object Relational Mapping     |
| Singleton (umumnya)             | Per Request / Per Transaction |
| Controller, Service, Repository | Job, User, Order              |

---

## Kesimpulan

Spring Boot dan Hibernate saling bekerja sama, tetapi memiliki tanggung jawab yang berbeda.

Spring Boot bertugas mengelola object aplikasi (Bean), sedangkan Hibernate bertugas mengelola object yang merepresentasikan data di database (Entity).

Memahami perbedaan ini akan membuat konsep seperti Dependency Injection, Repository, Transaction, Persistence Context, dan Dirty Checking menjadi jauh lebih mudah dipahami.