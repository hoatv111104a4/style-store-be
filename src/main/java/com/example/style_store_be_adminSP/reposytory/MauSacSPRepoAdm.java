package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be.entity.MauSacSp;
import com.example.style_store_be_adminSP.entity.MauSacSpAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MauSacSPRepoAdm extends JpaRepository<MauSacSpAdm, Long> {
    Page<MauSacSpAdm> findByNgayXoaIsNull(Pageable pageable);

    Page<MauSacSpAdm> findByNgayXoaIsNotNull(Pageable pageable);

    Optional<MauSacSpAdm> findByTen(String trim);

    Page<MauSacSpAdm> findByTenContainingIgnoreCase(String ten, Pageable pageable);
}
