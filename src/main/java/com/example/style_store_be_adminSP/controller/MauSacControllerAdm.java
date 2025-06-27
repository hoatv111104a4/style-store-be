package com.example.style_store_be_adminSP.controller;

import com.example.style_store_be.entity.MauSacSp;
import com.example.style_store_be_adminSP.entity.MauSacSpAdm;
import com.example.style_store_be_adminSP.service.impl.MauSacServiceImplAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mau-sac")
@CrossOrigin("*")
public class MauSacControllerAdm {
    @Autowired
    private MauSacServiceImplAdm mauSacService;

    @GetMapping("/all")
    public ResponseEntity<Page<MauSacSpAdm>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MauSacSpAdm> mauSacPage = mauSacService.getAll(page, size);
        return ResponseEntity.ok(mauSacPage);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<MauSacSpAdm>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MauSacSpAdm> mauSacPage = mauSacService.getActive(page, size);
        return ResponseEntity.ok(mauSacPage);
    }

    @GetMapping("/deleted")
    public ResponseEntity<Page<MauSacSpAdm>> getDeleted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MauSacSpAdm> mauSacPage = mauSacService.getDeleted(page, size);
        return ResponseEntity.ok(mauSacPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MauSacSpAdm> getOne(@PathVariable Long id) {
        MauSacSpAdm mauSac = mauSacService.getOne(id);
        return mauSac != null ? ResponseEntity.ok(mauSac) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody MauSacSpAdm mauSac) {
        try {
            mauSacService.add(mauSac);
            return ResponseEntity.status(201).build(); // 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody MauSacSpAdm mauSac) {
        try {
            mauSac.setId(id); // Gán lại ID cho chắc chắn
            mauSacService.update(mauSac);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            mauSacService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    @GetMapping("/search")
    public ResponseEntity<Page<MauSacSpAdm>> searchByName(
            @RequestParam String ten,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<MauSacSpAdm> chatLieuPage = mauSacService.searchByName(ten, page, size);
            return ResponseEntity.ok(chatLieuPage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }
}
