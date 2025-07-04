package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import com.example.style_store_be_adminSP.service.SanPhamCtServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin-san-pham-chi-tiet")
@CrossOrigin("*")
public class SanPhamCtControllerAdm {
    @Autowired
    private SanPhamCtServiceAdm sanPhamCtService;

    // Lấy danh sách sản phẩm chi tiết với phân trang
    @GetMapping
    public ResponseEntity<Page<SanPhamCtDTOAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCtService.findAll(pageable);
        return ResponseEntity.ok(result);
    }

    // Lấy danh sách sản phẩm chi tiết theo sanPhamId với phân trang
    @GetMapping("/san-pham/{sanPhamId}")
    public ResponseEntity<Page<SanPhamCtDTOAdm>> getBySanPhamId(
            @PathVariable Long sanPhamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (sanPhamId == null || sanPhamId <= 0 || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCtService.findBySanPhamId(sanPhamId, pageable);
        return ResponseEntity.ok(result);
    }

    // Lấy danh sách sản phẩm chi tiết theo trạng thái với phân trang
    @GetMapping("/trang-thai/{trangThai}")
    public ResponseEntity<Page<SanPhamCtDTOAdm>> getByTrangThai(
            @PathVariable Integer trangThai,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (trangThai == null || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCtService.findByTrangThai(trangThai, pageable);
        return ResponseEntity.ok(result);
    }

    // Tìm kiếm sản phẩm chi tiết theo tên sản phẩm với phân trang
    @GetMapping("/search")
    public ResponseEntity<Page<SanPhamCtDTOAdm>> searchByTen(
            @RequestParam String ten,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (ten == null || ten.trim().isEmpty() || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCtService.searchBySanPhamTen(ten, pageable);
        return ResponseEntity.ok(result);
    }

    // Lấy sản phẩm chi tiết theo ID
    @GetMapping("/{id}")
    public ResponseEntity<SanPhamCtDTOAdm> getById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Optional<SanPhamCtDTOAdm> sanPhamCt = sanPhamCtService.findById(id);
        return sanPhamCt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy sản phẩm chi tiết theo mã
    @GetMapping("/ma/{ma}")
    public ResponseEntity<SanPhamCtDTOAdm> getByMa(@PathVariable String ma) {
        if (ma == null || ma.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<SanPhamCtDTOAdm> sanPhamCt = sanPhamCtService.findByMa(ma);
        return sanPhamCt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Thêm mới sản phẩm chi tiết
    @PostMapping
    public ResponseEntity<?> add(@RequestBody SanPhamCtDTOAdm sanPhamCtDTO) {
        if (sanPhamCtDTO == null || sanPhamCtDTO.getSanPhamId() == null || sanPhamCtDTO.getMauSacId() == null ||
                sanPhamCtDTO.getGiaBan() == null || sanPhamCtDTO.getSoLuong() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Dữ liệu đầu vào không hợp lệ: Các trường bắt buộc không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        try {
            SanPhamCtDTOAdm created = sanPhamCtService.addSanPhamCt(sanPhamCtDTO);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Cập nhật sản phẩm chi tiết
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SanPhamCtDTOAdm sanPhamCtDTO) {
        if (id == null || id <= 0 || sanPhamCtDTO == null || sanPhamCtDTO.getSanPhamId() == null ||
                sanPhamCtDTO.getMauSacId() == null || sanPhamCtDTO.getGiaBan() == null ||
                sanPhamCtDTO.getSoLuong() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Dữ liệu đầu vào không hợp lệ: Các trường bắt buộc không được để trống");
            return ResponseEntity.badRequest().body(error);
        }
        try {
            SanPhamCtDTOAdm updated = sanPhamCtService.updateSanPhamCt(id, sanPhamCtDTO);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Xóa (soft delete) sản phẩm chi tiết
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (id == null || id <= 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "ID không hợp lệ");
            return ResponseEntity.badRequest().body(error);
        }
        try {
            sanPhamCtService.deleteSanPhamCt(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Lấy danh sách sản phẩm chi tiết theo mauSacId với phân trang
    @GetMapping("/mau-sac/{mauSacId}")
    public ResponseEntity<Page<SanPhamCtDTOAdm>> getByMauSacId(
            @PathVariable Long mauSacId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (mauSacId == null || mauSacId <= 0 || page < 0 || size <= 0) {
            return ResponseEntity.badRequest().body(null);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPhamCtDTOAdm> result = sanPhamCtService.findByMauSacId(mauSacId, pageable);
        return ResponseEntity.ok(result);
    }
}
