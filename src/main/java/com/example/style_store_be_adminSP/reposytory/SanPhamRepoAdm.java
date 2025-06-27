package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be.entity.SanPham;
import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SanPhamRepoAdm extends JpaRepository<SanPhamAdm,Long> {
    Page<SanPhamAdm> findByNgayXoaIsNull(Pageable pageable);
    Page<SanPhamAdm> findByNgayXoaIsNotNull(Pageable pageable);
    Optional<SanPhamAdm> findByTen(String ten);

    @Query("SELECT sp AS sanPham, " +
            "COALESCE(SUM(CASE WHEN ctsp.trangThai = 1 THEN ctsp.soLuong ELSE 0 END), 0) AS totalQuantity " +
            "FROM SanPhamAdm sp " +
            "LEFT JOIN ChiTietSanPham ctsp ON sp.id = ctsp.sanPham.id " +
            "WHERE (:search IS NULL OR LOWER(sp.ten) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "GROUP BY sp")
    Page<Object[]> findSanPhamWithTotalQuantity(@Param("search") String search, Pageable pageable);
}
