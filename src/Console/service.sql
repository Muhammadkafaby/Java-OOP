CREATE DATABASE db_service;
USE db_service;

CREATE TABLE pengguna (
    id INT PRIMARY KEY,
    nama_pengguna VARCHAR(255) NOT NULL,
    no_telepon VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL
);

CREATE TABLE kendaraan (
    id INT PRIMARY KEY,
    nomor_rangka VARCHAR(255) NOT NULL,
    nomor_plat VARCHAR(20) NOT NULL,
    tahun_pembuatan INT NOT NULL,
    merek_kendaraan VARCHAR(255) NOT NULL
);

CREATE TABLE motor (
    id INT PRIMARY KEY,
    nomor_rangka VARCHAR(255) NOT NULL,
    nomor_plat VARCHAR(20) NOT NULL,
    tahun_pembuatan INT NOT NULL,
    merek_kendaraan VARCHAR(255) NOT NULL,
    warna_motor VARCHAR(255) NOT NULL,
    jenis_motor VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES kendaraan(id)
);

CREATE TABLE mobil (
    id INT PRIMARY KEY,
    nomor_rangka VARCHAR(255) NOT NULL,
    nomor_plat VARCHAR(20) NOT NULL,
    tahun_pembuatan INT NOT NULL,
    merek_kendaraan VARCHAR(255) NOT NULL,
    warna_mobil VARCHAR(255) NOT NULL,
    jenis_transmisi VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES kendaraan(id)
);

CREATE TABLE riwayat_servis (
    id INT PRIMARY KEY,
    jenis_servis VARCHAR(255) NOT NULL,
    keterangan_servis TEXT NOT NULL,
    tanggal_mulai DATE NOT NULL,
    tanggal_selesai DATE NOT NULL,
    kilometer_akhir INT NOT NULL,
    biaya FLOAT NOT NULL,
    kendaraan_id INT,
    FOREIGN KEY (kendaraan_id) REFERENCES kendaraan(id)
);