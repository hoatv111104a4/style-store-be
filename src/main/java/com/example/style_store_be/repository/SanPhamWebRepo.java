package com.example.style_store_be.repository;

import com.example.style_store_be.dto.SanPhamWebDto;
import com.example.style_store_be.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SanPhamWebRepo extends JpaRepository<ChiTietSanPham,Long> {

    @Query("SELECT new com.example.style_store_be.dto.SanPhamWebDto(" +
            "c.id, c.ma, c.giaNhap, c.giaBan, c.soLuong, c.ngayTao, c.ngaySua, c.ngayXoa, c.trangThai, c.moTa, " +
            "c.sanPham.id, c.sanPham.ten, c.mauSac.id, c.mauSac.ten, c.thuongHieu.id, c.thuongHieu.ten, " +
            "c.kichThuoc.id, c.kichThuoc.ten, c.xuatXu.id, c.xuatXu.ten, c.chatLieu.id, c.chatLieu.ten, " +
            "c.hinhAnhSp.id, c.hinhAnhSp.hinhAnh, " +
            "MAX(c.giaBanGoc), MAX(g.giamGia)) " +
            "FROM ChiTietSanPham c " +
            "LEFT JOIN c.dotGiamGias g WITH g.trangThai = 1 " +
            "WHERE (:tenSanPham IS NULL OR c.sanPham.ten LIKE %:tenSanPham%) " +
            "AND (:thuongHieuId IS NULL OR c.thuongHieu.id = :thuongHieuId) " +
            "AND (:mauSacId IS NULL OR c.mauSac.id = :mauSacId) " +
            "AND (:chatLieuId IS NULL OR c.chatLieu.id = :chatLieuId) " +
            "AND (:kichThuocId IS NULL OR c.kichThuoc.id = :kichThuocId) " +
            "AND (:minPrice IS NULL OR c.giaBan >= :minPrice) " +
            "AND (:maxPrice IS NULL OR c.giaBan <= :maxPrice) " +
            "AND (:sanPhamId IS NULL OR c.sanPham.id = :sanPhamId) " +
            "AND c.trangThai = 1 " +
            "GROUP BY c.id, c.ma, c.giaNhap, c.giaBan, c.soLuong, c.ngayTao, c.ngaySua, c.ngayXoa, c.trangThai, c.moTa, " +
            "c.sanPham.id, c.sanPham.ten, c.mauSac.id, c.mauSac.ten, c.thuongHieu.id, c.thuongHieu.ten, " +
            "c.kichThuoc.id, c.kichThuoc.ten, c.xuatXu.id, c.xuatXu.ten, c.chatLieu.id, c.chatLieu.ten, " +
            "c.hinhAnhSp.id, c.hinhAnhSp.hinhAnh " +
            "ORDER BY c.ngayTao DESC")
    Page<SanPhamWebDto> findByFilters(
            @Param("tenSanPham") String tenSanPham,
            @Param("thuongHieuId") Long thuongHieuId,
            @Param("mauSacId") Long mauSacId,
            @Param("chatLieuId") Long chatLieuId,
            @Param("kichThuocId") Long kichThuocId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("sanPhamId") Long sanPhamId,
            Pageable pageable);


    @Query("SELECT new com.example.style_store_be.dto.SanPhamWebDto(" +
            "c.id, c.ma, c.giaNhap, c.giaBan, c.soLuong, c.ngayTao, c.ngaySua, c.ngayXoa, c.trangThai, c.moTa, " +
            "c.sanPham.id, c.sanPham.ten, c.mauSac.id, c.mauSac.ten, c.thuongHieu.id, c.thuongHieu.ten, " +
            "c.kichThuoc.id, c.kichThuoc.ten, c.xuatXu.id, c.xuatXu.ten, c.chatLieu.id, c.chatLieu.ten, " +
            "c.hinhAnhSp.id, c.hinhAnhSp.hinhAnh, " +
            "MAX(c.giaBanGoc), MAX(g.giamGia)) " +
            "FROM ChiTietSanPham c " +
            "LEFT JOIN c.dotGiamGias g WITH g.trangThai = 1 " +
            "WHERE (:tenSanPham IS NULL OR c.sanPham.ten LIKE %:tenSanPham%) " +
            "AND (:thuongHieuId IS NULL OR c.thuongHieu.id = :thuongHieuId) " +
            "AND (:mauSacId IS NULL OR c.mauSac.id = :mauSacId) " +
            "AND (:chatLieuId IS NULL OR c.chatLieu.id = :chatLieuId) " +
            "AND (:kichThuocId IS NULL OR c.kichThuoc.id = :kichThuocId) " +
            "AND (:minPrice IS NULL OR c.giaBan >= :minPrice) " +
            "AND (:maxPrice IS NULL OR c.giaBan <= :maxPrice) " +
            "AND (:sanPhamId IS NULL OR c.sanPham.id = :sanPhamId) " +
            "AND c.trangThai = 1 " +
            "GROUP BY c.id, c.ma, c.giaNhap, c.giaBan, c.soLuong, c.ngayTao, c.ngaySua, c.ngayXoa, c.trangThai, c.moTa, " +
            "c.sanPham.id, c.sanPham.ten, c.mauSac.id, c.mauSac.ten, c.thuongHieu.id, c.thuongHieu.ten, " +
            "c.kichThuoc.id, c.kichThuoc.ten, c.xuatXu.id, c.xuatXu.ten, c.chatLieu.id, c.chatLieu.ten, " +
            "c.hinhAnhSp.id, c.hinhAnhSp.hinhAnh")
    List<SanPhamWebDto> findByFiltersNoPaging(
            @Param("tenSanPham") String tenSanPham,
            @Param("thuongHieuId") Long thuongHieuId,
            @Param("mauSacId") Long mauSacId,
            @Param("chatLieuId") Long chatLieuId,
            @Param("kichThuocId") Long kichThuocId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("sanPhamId") Long sanPhamId,
            Sort sort
    );
}
