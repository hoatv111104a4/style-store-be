package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be_adminSP.entity.HinhAnhMauSacAdm;
import com.example.style_store_be_adminSP.service.HinhAnhMauSacServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hinh-anh-mau-sac")
@CrossOrigin("*")
public class HinhAnhMauSacControllerAdm {
    @Autowired
    private HinhAnhMauSacServiceAdm hinhAnhMauSacService;

    @GetMapping("/all")
    public ResponseEntity<Page<HinhAnhMauSacAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<HinhAnhMauSacAdm> hinhAnhPage = hinhAnhMauSacService.getAll(page, size);
        return ResponseEntity.ok(hinhAnhPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<HinhAnhMauSacAdm>> searchByHinhAnh(
            @RequestParam String hinhAnh,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<HinhAnhMauSacAdm> hinhAnhPage = hinhAnhMauSacService.searchByHinhAnh(hinhAnh, page, size);
            return ResponseEntity.ok(hinhAnhPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @GetMapping("/active")
    public ResponseEntity<Page<HinhAnhMauSacAdm>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<HinhAnhMauSacAdm> hinhAnhPage = hinhAnhMauSacService.getActive(page, size);
        return ResponseEntity.ok(hinhAnhPage);
    }

    @GetMapping("/deleted")
    public ResponseEntity<Page<HinhAnhMauSacAdm>> getDeleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<HinhAnhMauSacAdm> hinhAnhPage = hinhAnhMauSacService.getDeleted(page, size);
        return ResponseEntity.ok(hinhAnhPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HinhAnhMauSacAdm> getOne(@PathVariable Long id) {
        HinhAnhMauSacAdm hinhAnh = hinhAnhMauSacService.getOne(id);
        return hinhAnh != null ? ResponseEntity.ok(hinhAnh) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody HinhAnhMauSacAdm hinhAnhMauSac) {
        try {
            hinhAnhMauSacService.add(hinhAnhMauSac);
            return ResponseEntity.status(201).build(); // 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody HinhAnhMauSacAdm hinhAnhMauSac) {
        try {
            hinhAnhMauSac.setId(id); // Gán ID từ path variable
            hinhAnhMauSacService.update(hinhAnhMauSac);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            hinhAnhMauSacService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
