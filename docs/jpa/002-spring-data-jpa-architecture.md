# Arsitektur Spring Data JPA

Sebelum memahami Entity dan Repository, kita perlu mengetahui bagaimana seluruh komponen bekerja sama.

Banyak developer mengira:

```
Controller
    ↓
Repository
    ↓
Database
```

Padahal alurnya jauh lebih panjang.

```
HTTP Request
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
JobRepository (Interface)
      │
      ▼
Spring Data JPA
      │
      ▼
JPA (Specification)
      │
      ▼
Hibernate (Implementation)
      │
      ▼
JDBC
      │
      ▼
PostgreSQL Driver
      │
      ▼
PostgreSQL
```

---

# Siapa yang membuat object-object tersebut?

Saat aplikasi dijalankan, Spring Boot akan melakukan proses startup.

```
SpringApplication.run()

        │
        ▼
Component Scan
```

Spring akan mencari annotation seperti:

```
@RestController

@Service

@Repository

@Configuration

@Component
```

Kemudian Spring membuat object (Bean) untuk masing-masing class tersebut.

Contohnya:

```
JobController

↓

NotificationService

↓

NotificationProperties
```

Semua object tersebut disimpan di dalam:

```
ApplicationContext
```

atau sering disebut **Spring IoC Container**.

---

# Bagaimana Repository dibuat?

Repository berbeda dengan Controller atau Service.

Kita hanya membuat:

```java
public interface JobRepository
        extends JpaRepository<Job, UUID> {
}
```

Tidak ada implementasi.

Saat startup:

```
Spring Boot

↓

Spring Data JPA

↓

Repository Factory

↓

Membuat JobRepository Proxy
```

Kurang lebih seperti ini (pseudo code):

```java
class JobRepositoryImpl
        implements JobRepository {

    ...

}
```

Implementasi tersebut dibuat secara otomatis menggunakan proxy.

Karena itulah kita dapat melakukan:

```java
private final JobRepository repository;
```

padahal kita tidak pernah membuat class implementasinya.

---

# Siapa yang membuat Entity?

Jawabannya:

**Developer.**

Entity hanyalah object Java biasa (POJO).

Contoh:

```java
Job job = new Job();
```

Hibernate **tidak membuat Entity**.

Hibernate hanya mengelola Entity setelah Entity masuk ke Persistence Context.

---

# Apa yang terjadi saat save()?

Misalnya:

```java
jobRepository.save(job);
```

Alurnya adalah:

```
NotificationService

↓

JobRepository

↓

Spring Data JPA

↓

EntityManager (JPA)

↓

Hibernate

↓

JDBC

↓

INSERT INTO jobs(...)
```

Hibernate membaca annotation pada Entity.

Misalnya:

```java
@Entity

@Table(name = "jobs")

@Column(name = "created_at")
```

Kemudian Hibernate membangun SQL yang sesuai.

Developer tidak pernah menulis SQL INSERT secara manual.

---

# Apa yang terjadi saat findById()?

Misalnya:

```java
Job job =
    repository.findById(id)
              .orElseThrow();
```

Alurnya:

```
Repository

↓

Hibernate

↓

SELECT ...

↓

Job Object

↓

Persistence Context
```

Entity yang dihasilkan sekarang menjadi:

```
Managed Entity
```

Hibernate mulai mengawasi object tersebut.

---

# Apa yang terjadi saat object berubah?

Misalnya:

```java
job.setStatus(JobStatus.SUCCESS);
```

Hibernate mendeteksi perubahan.

```
Persistence Context

↓

Dirty Checking

↓

UPDATE jobs...
```

Developer tidak perlu memanggil:

```java
repository.save(job);
```

selama Entity masih berada di dalam Persistence Context dan Transaction masih aktif.

---

# Ringkasan Lifecycle Request

```
Client

↓

DispatcherServlet

↓

JobController

↓

NotificationService

↓

JobRepository

↓

Spring Data JPA

↓

Hibernate

↓

JDBC

↓

PostgreSQL
```

Sedangkan object yang dibuat saat startup adalah:

```
ApplicationContext

├── JobController

├── NotificationService

├── NotificationProperties

├── JobRepository (Proxy)

└── EntityManagerFactory
```

Entity (`Job`) **tidak dibuat saat startup**.

Entity dibuat ketika:

- developer memanggil `new Job()`, atau
- Hibernate mengubah hasil query database menjadi object Java.