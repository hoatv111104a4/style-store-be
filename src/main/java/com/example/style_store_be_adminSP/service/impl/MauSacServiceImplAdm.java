package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be_adminSP.entity.MauSacSpAdm;
import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSP.reposytory.MauSacSPRepoAdm;
import com.example.style_store_be_adminSP.service.ICommonServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Service
public class MauSacServiceImplAdm implements ICommonServiceAdm<MauSacSpAdm> {
    @Autowired
    private MauSacSPRepoAdm mauSacSPRepository;

    @Override
    public Page<MauSacSpAdm> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mauSacSPRepository.findAll(pageable);
    }

    @Override
    public MauSacSpAdm getOne(Long id) {
        return mauSacSPRepository.findById(id).orElse(null);
    }

    @Override
    public SanPhamAdm add(MauSacSpAdm object) {
        validate(object);
        if (object.getMa() == null || object.getMa().trim().isEmpty()) {
            object.setMa("MS-" + UUID.randomUUID().toString().substring(0, 8));
        }

        object.setTrangThai(1); // Mặc định là đang hoạt động
        object.setNgayTao(LocalDateTime.now());
        object.setNgaySua(null);
        object.setNgayXoa(null);

        mauSacSPRepository.save(object);
        return null;
    }

    @Override
    public void update(MauSacSpAdm object) {
        if (object.getId() == null) {
            throw new RuntimeException("ID không được để trống khi cập nhật");
        }
        validate(object);

        MauSacSpAdm existing = mauSacSPRepository.findById(object.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Màu Sắc với id: " + object.getId()));

        existing.setTen(object.getTen());
        existing.setMoTa(object.getMoTa());
        existing.setNgaySua(LocalDateTime.now());

        mauSacSPRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new RuntimeException("ID không được để trống khi xóa");
        }
        MauSacSpAdm existing = mauSacSPRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Màu Sắc với id: " + id));

        if (existing.getTrangThai() == 1) {
            existing.setNgayXoa(LocalDateTime.now());
            existing.setTrangThai(0);
        } else {
            existing.setNgayXoa(null);
            existing.setTrangThai(1);
            existing.setNgaySua(LocalDateTime.now());
        }

        mauSacSPRepository.save(existing);
    }

    public Page<MauSacSpAdm> getActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mauSacSPRepository.findByNgayXoaIsNull(pageable);
    }

    public Page<MauSacSpAdm> getDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mauSacSPRepository.findByNgayXoaIsNotNull(pageable);
    }
    public Page<MauSacSpAdm> searchByName(String ten, int page, int size) {
        if (ten == null || ten.trim().isEmpty()) {
            throw new RuntimeException("Tên chất liệu tìm kiếm không được để trống");
        }
        Pageable pageable = PageRequest.of(page, size);
        return mauSacSPRepository.findByTenContainingIgnoreCase(ten.trim(), pageable);
    }

    private void validate(MauSacSpAdm object) {
        if (object.getTen() == null || object.getTen().trim().isEmpty()) {
            throw new RuntimeException("Tên màu sắc không được để trống");
        }

        if (object.getTen().length() > 50) {
            throw new RuntimeException("Tên màu sắc không được vượt quá 50 ký tự");
        }
        if (!object.getTen().trim().matches("^[\\p{L}\\s]+$")) {
            throw new RuntimeException("Tên màu sắc chỉ được chứa chữ cái và khoảng trắng, không chứa số hoặc ký tự đặc biệt");
        }
        Optional<MauSacSpAdm> existing = mauSacSPRepository.findByTen(object.getTen().trim());
        if (existing.isPresent()) {
            // Nếu đang cập nhật thì phải bỏ qua chính nó
            if (object.getId() == null || !existing.get().getId().equals(object.getId())) {
                throw new RuntimeException("Màu sắc đã tồn tại");
            }
        }
    }
}
