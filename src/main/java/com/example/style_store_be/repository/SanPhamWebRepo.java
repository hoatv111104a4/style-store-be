package com.example.style_store_be.repository;

import com.example.style_store_be.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SanPhamWebRepo extends JpaRepository<ChiTietSanPham,Long> {
}
