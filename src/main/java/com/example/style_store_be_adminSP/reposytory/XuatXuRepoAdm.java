package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be.entity.XuatXu;
import com.example.style_store_be_adminSP.entity.XuatXuAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface XuatXuRepoAdm extends JpaRepository<XuatXuAdm,Long> {
    Page<XuatXuAdm> findByNgayXoaIsNull(Pageable pageable);
    Page<XuatXuAdm> findByNgayXoaIsNotNull(Pageable pageable);
    Page<XuatXuAdm> findByTenContainingIgnoreCase(String ten, Pageable pageable);
    Optional<XuatXuAdm> findByTen(String trim);
}
