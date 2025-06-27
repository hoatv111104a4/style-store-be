package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be.entity.SanPham;
import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSP.service.impl.SanPhamAdminServiceImplAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin-san-pham")
@CrossOrigin("*")
public class SanPhamAdminControllerAdm {

    @Autowired
    private SanPhamAdminServiceImplAdm sanPhamAdminService;

    @GetMapping("/all")
    public ResponseEntity<Page<SanPhamAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<SanPhamAdm> sanPhamPage = sanPhamAdminService.getAll(page, size);
        return ResponseEntity.ok(sanPhamPage);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<SanPhamAdm>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<SanPhamAdm> sanPhamPage = sanPhamAdminService.getActive(page, size);
        return ResponseEntity.ok(sanPhamPage);
    }

    @GetMapping("/deleted")
    public ResponseEntity<Page<SanPhamAdm>> getDeleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<SanPhamAdm> sanPhamPage = sanPhamAdminService.getDeleted(page, size);
        return ResponseEntity.ok(sanPhamPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SanPhamAdminServiceImplAdm.SanPhamWithQuantity>> searchWithTotalQuantity(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<SanPhamAdminServiceImplAdm.SanPhamWithQuantity> sanPhamPage = sanPhamAdminService.searchSanPhamWithTotalQuantity(search, page, size);
        return ResponseEntity.ok(sanPhamPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamAdm> getOne(@PathVariable Long id) {
        SanPhamAdm sanPham = sanPhamAdminService.getOne(id);
        return sanPham != null ? ResponseEntity.ok(sanPham) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SanPhamAdm> create(@RequestBody SanPhamAdm sanPham) {
        try {
            SanPhamAdm createdSanPham = sanPhamAdminService.add(sanPham);
            return ResponseEntity.status(201).body(createdSanPham);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody SanPhamAdm sanPham) {
        try {
            sanPham.setId(id); // Gán lại ID cho chắc chắn
            sanPhamAdminService.update(sanPham);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            sanPhamAdminService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
