package com.example.style_store_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_tao", nullable = false)
    private NguoiDung idNguoiTao;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_xuat", nullable = false)
    private NguoiDung idNguoiXuat;

    @ManyToOne
    @JoinColumn(name = "id_thanh_toan", nullable = false)
    private PtThanhToan idThanhToan;

    @Column(name = "ma", length = 225)
    private String ma;

    @Column(name = "nguoi_dat_hang", length = 225)
    private String nguoiDatHang;

    @Column(name = "nguoi_nhan_hang", length = 225)
    private String nguoiNhanHang;

    @Column(name = "dia_chi_nhan_hang")
    private String diaChiNhanHang;

    @Column(name = "tong_so_luong_sp", nullable = false)
    private Integer tongSoLuongSp;


    @Column(name = "tong_tien", nullable = false, precision = 12, scale = 2)
    private double tongTien;

    @Column(name = "tien_thue", nullable = false, precision = 10, scale = 2)
    private double tienThue;

    @Column(name = "ngay_dat")
    private Date ngayDat;

    @Column(name = "ngay_nhan")
    private Date ngayNhan;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "ngay_xoa")
    private Date ngayXoa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "mo_ta")
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private NguoiDung idKhachHang;

}