package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be.entity.KichThuoc;
import com.example.style_store_be_adminSP.entity.KichThuocAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KichThuocRepoAdm extends JpaRepository<KichThuocAdm,Long> {
    Page<KichThuocAdm> findByNgayXoaIsNull(Pageable pageable);
    Page<KichThuocAdm> findByNgayXoaIsNotNull(Pageable pageable);

    Optional<KichThuocAdm> findByTen(String trim);

    Page<KichThuocAdm> findByTenContainingIgnoreCase(String ten, Pageable pageable);
}
