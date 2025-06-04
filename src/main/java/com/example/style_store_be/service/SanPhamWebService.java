package com.example.style_store_be.service;

import com.example.style_store_be.entity.ChiTietSanPham;
import com.example.style_store_be.repository.SanPhamWebRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SanPhamWebService {
    private final SanPhamWebRepo sanPhamWebRepo;

    public SanPhamWebService(SanPhamWebRepo sanPhamWebRepo) {
        this.sanPhamWebRepo = sanPhamWebRepo;
    }

    public Page<ChiTietSanPham> getPageChiTietSanPham(Pageable pageable) {
        return sanPhamWebRepo.findAll(pageable);
    }
}
