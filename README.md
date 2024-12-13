# Library Management Backend

Library Management adalah aplikasi backend yang digunakan untuk mengelola data buku, kategori, dan perpustakaan menggunakan framework Spring Boot.

## Prasyarat
Pastikan Anda memiliki perangkat lunak berikut yang sudah terpasang di sistem Anda:

- **Java Development Kit (JDK)** versi 11 atau lebih baru
- **Maven** versi terbaru
- **PostgreSQL** sebagai database
- **Git** (opsional, untuk meng-clone repository)

## Konfigurasi Aplikasi
### Application Properties
Konfigurasi untuk aplikasi ini terdapat di file `application.properties`. Berikut adalah konfigurasi default:

```properties
# Application Configuration
spring.application.name=library-management
server.port=8086

# Database Configuration
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/library_management
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA Configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

### Mengubah Konfigurasi
- Ubah `spring.datasource.url`, `spring.datasource.username`, dan `spring.datasource.password` sesuai dengan konfigurasi database lokal Anda.
- Jika ingin menginisialisasi database dengan struktur baru setiap kali aplikasi dijalankan, ubah `spring.jpa.hibernate.ddl-auto=update` menjadi `create-drop`.

## Langkah-langkah Menjalankan Aplikasi

### 1. Clone Repository
Jika Anda belum memiliki kode sumber, clone repository menggunakan perintah berikut:
```bash
git clone <repository-url>
cd library-management
```

### 2. Build Aplikasi
Jalankan perintah berikut untuk membangun aplikasi menggunakan Maven:
```bash
mvn clean install
```

### 3. Jalankan Database PostgreSQL
Pastikan PostgreSQL sudah berjalan, dan database `library_management` sudah dibuat. Jika belum, buat database dengan perintah berikut di PostgreSQL:
```sql
CREATE DATABASE library_management;
```

### 4. Jalankan Aplikasi
Gunakan perintah berikut untuk menjalankan aplikasi:
```bash
mvn spring-boot:run
```
Aplikasi akan berjalan di `http://localhost:8086` secara default.

### 5. Mengakses Endpoint API
Berikut adalah beberapa endpoint utama yang disediakan oleh aplikasi ini:

- **Books API**: `/api/v1/books`
- **Categories API**: `/api/v1/categories`
- **Libraries API**: `/api/v1/libraries`

Anda dapat menggunakan alat seperti Postman atau cURL untuk mengakses API ini.

## Teknologi yang Digunakan
- **Spring Boot**: Framework utama untuk aplikasi backend.
- **Spring Data JPA**: Untuk mengelola komunikasi dengan database.
- **PostgreSQL**: Sebagai database relasional.
- **Maven**: Untuk manajemen dependensi dan build.

## Pengembangan dan Kontribusi
Jika Anda ingin berkontribusi dalam pengembangan aplikasi ini:

1. Fork repository ini.
2. Buat branch baru untuk fitur atau bugfix Anda.
3. Kirimkan pull request dengan deskripsi yang jelas.

## Lisensi
Aplikasi ini menggunakan lisensi [MIT](LICENSE).
