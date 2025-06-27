package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be.entity.ThuongHieu;
import com.example.style_store_be_adminSP.entity.ThuongHieuAdm;
import com.example.style_store_be_adminSP.service.impl.ThuongHieuServiceImplAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/thuong-hieu")
@CrossOrigin("*")
public class ThuongHieuControllerAdm {

    @Autowired
    private ThuongHieuServiceImplAdm thuongHieuService;

    @GetMapping("/all")
    public ResponseEntity<Page<ThuongHieuAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ThuongHieuAdm> thuongHieuPage = thuongHieuService.getAll(page, size);
        return ResponseEntity.ok(thuongHieuPage);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<ThuongHieuAdm>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ThuongHieuAdm> thuongHieuPage = thuongHieuService.getActive(page, size);
        return ResponseEntity.ok(thuongHieuPage);
    }

    @GetMapping("/deleted")
    public ResponseEntity<Page<ThuongHieuAdm>> getDeleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ThuongHieuAdm> thuongHieuPage = thuongHieuService.getDeleted(page, size);
        return ResponseEntity.ok(thuongHieuPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThuongHieuAdm> getOne(@PathVariable Long id) {
        ThuongHieuAdm thuongHieu = thuongHieuService.getOne(id);
        return thuongHieu != null ? ResponseEntity.ok(thuongHieu) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ThuongHieuAdm thuongHieu) {
        try {
            thuongHieuService.add(thuongHieu);
            return ResponseEntity.status(201).build(); // 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ThuongHieuAdm thuongHieu) {
        try {
            thuongHieu.setId(id); // Gán lại ID cho chắc chắn
            thuongHieuService.update(thuongHieu);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            thuongHieuService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ThuongHieuAdm>> searchByName(
            @RequestParam String ten,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ThuongHieuAdm> chatLieuPage = thuongHieuService.searchByName(ten, page, size);
            return ResponseEntity.ok(chatLieuPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }
}
