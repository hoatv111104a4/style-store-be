package com.example.style_store_be_adminSP.entity;

import com.example.style_store_be.entity.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "san_pham_ct")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamCtAdm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_hinh_anh_mau_sac", nullable = true)
    private HinhAnhMauSacAdm hinhAnhMauSac;

    @ManyToOne
    @JoinColumn(name = "id_san_pham", nullable = false)
    private SanPhamAdm sanPham;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac", nullable = false)
    private MauSacSpAdm mauSac;

    @ManyToOne
    @JoinColumn(name = "id_thuong_hieu", nullable = false)
    private ThuongHieuAdm thuongHieu;

    @ManyToOne
    @JoinColumn(name = "id_size", nullable = false)
    private KichThuocAdm kichThuoc;

    @ManyToOne
    @JoinColumn(name = "id_xuat_xu", nullable = false)
    private XuatXuAdm xuatXu;

    @ManyToOne
    @JoinColumn(name = "id_chat_lieu", nullable = false)
    private ChatLieuAdm chatLieu;

    @Column(name = "ma", nullable = false, length = 20)
    private String ma;

    @Column(name = "gia_nhap", precision = 10, scale = 2)
    private BigDecimal giaNhap;

    @Column(name = "gia_ban", nullable = false, precision = 10, scale = 2)
    private BigDecimal giaBan;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "ngay_sua")
    private LocalDateTime ngaySua;

    @Column(name = "ngay_xoa")
    private LocalDateTime ngayXoa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;
}
