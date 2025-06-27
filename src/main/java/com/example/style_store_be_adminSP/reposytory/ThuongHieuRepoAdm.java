package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be.entity.ThuongHieu;
import com.example.style_store_be_adminSP.entity.ThuongHieuAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThuongHieuRepoAdm extends JpaRepository<ThuongHieuAdm,Long> {
    Page<ThuongHieuAdm> findByTenContainingIgnoreCase(String ten, Pageable pageable);

    Page<ThuongHieuAdm> findByNgayXoaIsNull(Pageable pageable);
    Page<ThuongHieuAdm> findByNgayXoaIsNotNull(Pageable pageable);

    Optional<ThuongHieuAdm> findByTen(String trim);
}
