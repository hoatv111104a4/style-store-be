package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSP.entity.SanPhamCtAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChiTietSPRepoAdm extends JpaRepository<SanPhamCtAdm,Long> {
    Optional<SanPhamCtAdm> findByMa(String ma);

    // Tìm kiếm sản phẩm chi tiết theo tên sản phẩm (like query) với phân trang
    @Query("SELECT spct FROM SanPhamCtAdm spct WHERE spct.sanPham.ten LIKE %:ten% AND spct.trangThai = 1")
    Page<SanPhamCtAdm> findBySanPhamTenContaining(@Param("ten") String ten, Pageable pageable);


    Page<SanPhamCtAdm> findByTrangThai(Integer trangThai, Pageable pageable);


    boolean existsByMa(String ma);


    Page<SanPhamCtAdm> findBySanPhamId(Long sanPhamId, Pageable pageable);


    Page<SanPhamCtAdm> findByMauSacId(Long mauSacId, Pageable pageable);
}
