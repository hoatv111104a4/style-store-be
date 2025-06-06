package com.example.style_store_be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "dia_chi_nhan")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaChiNhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang", nullable = false)
    private NguoiDung idKhachHang;

    @Column(name = "ma", nullable = false, length = 20)
    private String ma;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @Column(name = "ten_nguoi_nhan", nullable = false, length = 50)
    private String tenNguoiNhan;

    @Column(name = "so_dien_thoai", nullable = false, length = 15)
    private String soDienThoai;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "ngay_xoa")
    private Date ngayXoa;

    @Column(name = "trang_thai")
    private Integer trangThai;

}