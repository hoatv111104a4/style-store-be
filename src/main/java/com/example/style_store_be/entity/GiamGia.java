package com.example.style_store_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "giam_gia")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private NguoiDung idNguoiDung;

    @Column(name = "ma", nullable = false)
    private String ma;

    @Column(name = "ten_dot_giam")
    private String tenDotGiam;

    @Column(name = "giam_gia", nullable = false)
    private Double giamGia;

    @Column(name = "giam_toi_da")
    private Double giamToiDa;

    @Column(name = "dieu_kien_giam", nullable = false)
    private Double dieuKienGiam;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "nguoi_tao", nullable = false)
    private String nguoiTao;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "ngay_xoa")
    private Date ngayXoa;

    @Column(name = "ngay_bat_dau", nullable = false)
    private Date ngayBatDau;

    @Column(name = "ngay_ket_thuc", nullable = false)
    private Date ngayKetThuc;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;
}