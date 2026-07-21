# 007 - JPA dan Hibernate

## Gambaran Besar

Saat request datang:

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
Repository
   │
   ▼
Spring Data JPA
   │
   ▼
JPA
   │
   ▼
Hibernate
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

# Apa itu JPA?

JPA adalah singkatan dari:

```
Java Persistence API
```

JPA bukan ORM.

JPA adalah spesifikasi (API) yang mendefinisikan bagaimana object Java dipetakan ke database.

---

# Apa itu Hibernate?

Hibernate adalah implementasi dari JPA sekaligus ORM (Object Relational Mapping).

Hibernate bertugas:

- membuat SQL
- memetakan object ke tabel
- mengelola lifecycle entity
- melakukan dirty checking
- mengelola transaction

---

# Apa itu Spring Data JPA?

Spring Data JPA adalah library yang mempermudah penggunaan JPA.

Contoh:

```
repository.save(job)

repository.findById(id)

repository.findAll()
```

Developer tidak perlu membuat implementasi repository secara manual.

---

# Entity

Entity adalah representasi satu baris data di database.

```
Row Database

↓

Object Java
```

Contoh:

```
jobs
```

↓

```
Job
```

---

# Repository

Repository adalah pintu masuk menuju database.

```
JobRepository
```

hanya berupa interface:

```java
public interface JobRepository
        extends JpaRepository<Job, UUID> {
}
```

Spring akan membuat implementasinya secara otomatis saat aplikasi startup.

---

# Persistence Context

Entity yang diambil dari database akan dikelola oleh Hibernate.

```
findById()

↓

Managed Entity
```

Hibernate akan mengawasi perubahan object tersebut.

---

# Dirty Checking

Misalnya:

```java
Job job = repository.findById(id).orElseThrow();

job.setStatus(JobStatus.SUCCESS);
```

Developer tidak memanggil:

```java
repository.save(job);
```

Tetapi Hibernate tetap menghasilkan:

```sql
UPDATE jobs
SET status = 'SUCCESS'
WHERE id = ?
```

Perubahan object dideteksi secara otomatis.

---

# Lifecycle Entity

```
Transient

↓

Managed

↓

Detached

↓

Removed
```

## Transient

Object baru.

Belum dikenal Hibernate.

```
new Job()
```

---

## Managed

Object berada di dalam Persistence Context.

Hibernate mengawasi setiap perubahan.

---

## Detached

Object sudah tidak lagi diawasi Hibernate.

Perubahan tidak akan otomatis tersimpan.

---

## Removed

Entity akan dihapus ketika transaction selesai.

---

# Mengapa menggunakan EnumType.STRING?

Selalu gunakan:

```java
@Enumerated(EnumType.STRING)
```

Jangan menggunakan:

```java
EnumType.ORDINAL
```

Karena perubahan urutan enum dapat menyebabkan data di database menjadi salah.

---

# Ringkasan

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Spring Data JPA
      │
      ▼
Hibernate
      │
      ▼
JDBC
      │
      ▼
PostgreSQL
```

Hibernate bukan hanya membuat SQL, tetapi juga mengelola lifecycle object dan melakukan sinkronisasi perubahan object Java ke database secara otomatis.