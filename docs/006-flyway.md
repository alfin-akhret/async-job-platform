# 006 - Database Migration dengan Flyway

## Apa itu Flyway?

Flyway adalah tool untuk mengelola perubahan struktur database (database schema) secara terstruktur dan terdokumentasi.

Daripada mengubah database secara manual menggunakan pgAdmin atau DBeaver, setiap perubahan disimpan dalam bentuk migration file.

Contoh:

```
V1__create_jobs_table.sql
V2__add_retry_count.sql
V3__add_updated_at.sql
```

Ketika aplikasi dijalankan, Flyway akan memeriksa migration mana saja yang belum pernah dijalankan, kemudian mengeksekusinya secara otomatis.

---

## Bagaimana Flyway bekerja?

Saat Spring Boot startup:

```
Spring Boot
      │
      ▼
Flyway
      │
      ▼
Membaca folder migration
      │
      ▼
Membaca flyway_schema_history
      │
      ▼
Migration baru?
      │
      ├── Ya → Jalankan
      │
      └── Tidak → Skip
```

Semua migration yang berhasil dijalankan akan dicatat pada tabel:

```
flyway_schema_history
```

---

## Penomoran Migration

Migration harus diberi nomor berurutan.

Contoh:

```
V1__create_jobs_table.sql
V2__add_retry_count.sql
V3__add_updated_at.sql
```

Huruf `V` berarti **Version**.

---

## Mengapa migration tidak boleh diubah?

Flyway menghitung checksum setiap migration.

Contoh:

```
V1
↓

Checksum:
-1585118087
```

Checksum disimpan di database.

Jika isi file diubah:

```
Database :
-1585118087

File :
-2040266899
```

Flyway akan menghentikan startup karena menganggap migration telah dimodifikasi.

Hal ini mencegah database setiap developer menjadi berbeda.

---

## Best Practice

Migration yang sudah dijalankan **tidak boleh diedit**.

Jika ingin mengubah schema:

❌ Salah

```
Edit V1
```

✅ Benar

```
V4__add_scheduled_at.sql
```

---


## Ringkasan

```
Developer
      │
      ▼
Migration SQL
      │
      ▼
Flyway
      │
      ▼
PostgreSQL
      │
      ▼
flyway_schema_history
```