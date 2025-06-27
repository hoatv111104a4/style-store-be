package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be_adminSP.entity.HinhAnhMauSacAdm;
import com.example.style_store_be_adminSP.reposytory.HinhAnhRepoAdm;
import com.example.style_store_be_adminSP.service.HinhAnhMauSacServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class HinhAnhMauSacServiceImplAdm implements HinhAnhMauSacServiceAdm {
    @Autowired
    private HinhAnhRepoAdm repository;

    @Override
    public Page<HinhAnhMauSacAdm> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    @Override
    public Page<HinhAnhMauSacAdm> searchByHinhAnh(String hinhAnh, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Giả sử bạn thêm phương thức findByHinhAnhContainingIgnoreCase trong repository
        return repository.findByHinhAnhContainingIgnoreCase(hinhAnh, pageable);
    }

    @Override
    public Page<HinhAnhMauSacAdm> getActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByNgayXoaIsNull(pageable);
    }

    @Override
    public Page<HinhAnhMauSacAdm> getDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByNgayXoaIsNotNull(pageable);
    }

    @Override
    public HinhAnhMauSacAdm getOne(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hình ảnh màu sắc không tồn tại với id: " + id));
    }

    @Override
    public void add(HinhAnhMauSacAdm hinhAnhMauSac) {
        if (hinhAnhMauSac.getHinhAnh() == null || hinhAnhMauSac.getHinhAnh().isEmpty()) {
            throw new RuntimeException("Đường dẫn hình ảnh không được rỗng");
        }
        hinhAnhMauSac.setNgayTao(LocalDateTime.now());
        hinhAnhMauSac.setTrangThai(1); // Mặc định trạng thái hoạt động
        repository.save(hinhAnhMauSac);
    }

    @Override
    public void update(HinhAnhMauSacAdm hinhAnhMauSac) {
        HinhAnhMauSacAdm existing = getOne(hinhAnhMauSac.getId());
        if (hinhAnhMauSac.getHinhAnh() != null && !hinhAnhMauSac.getHinhAnh().isEmpty()) {
            existing.setHinhAnh(hinhAnhMauSac.getHinhAnh());
        }
        existing.setMoTa(hinhAnhMauSac.getMoTa());
        existing.setMauSac(hinhAnhMauSac.getMauSac());
        existing.setTrangThai(hinhAnhMauSac.getTrangThai());
        existing.setNgaySua(LocalDateTime.now());
        repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        HinhAnhMauSacAdm existing = getOne(id);
        existing.setNgayXoa(LocalDateTime.now());
        existing.setTrangThai(0); // Giả sử 0 là không hoạt động
        repository.save(existing);
    }
}
