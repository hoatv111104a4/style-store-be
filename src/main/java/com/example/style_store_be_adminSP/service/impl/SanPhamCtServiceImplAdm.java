package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import com.example.style_store_be_adminSP.entity.HinhAnhMauSacAdm;
import com.example.style_store_be_adminSP.entity.SanPhamCtAdm;
import com.example.style_store_be_adminSP.reposytory.*;
import com.example.style_store_be_adminSP.service.SanPhamCtServiceAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Service
public class SanPhamCtServiceImplAdm implements SanPhamCtServiceAdm {
    @Autowired
    private ChiTietSPRepoAdm sanPhamCtRepository;

    @Autowired
    private SanPhamRepoAdm sanPhamRepository;

    @Autowired
    private MauSacSPRepoAdm mauSacRepository;

    @Autowired
    private ThuongHieuRepoAdm thuongHieuRepository;

    @Autowired
    private KichThuocRepoAdm kichThuocRepository;

    @Autowired
    private XuatXuRepoAdm xuatXuRepository;

    @Autowired
    private ChatLieuRepoAdm chatLieuRepository;

    @Autowired
    private HinhAnhRepoAdm hinhAnhMauSacRepository;

    @Override
    public SanPhamCtDTOAdm addSanPhamCt(SanPhamCtDTOAdm sanPhamCtDTO) {
        SanPhamCtAdm sanPhamCt = mapToEntity(sanPhamCtDTO);
        validateSanPhamCt(sanPhamCt);

        // Tự động gán hình ảnh dựa trên màu sắc
        if (sanPhamCt.getMauSac() != null) {
            Optional<HinhAnhMauSacAdm> hinhAnhMauSac = hinhAnhMauSacRepository.findFirstByMauSacId(sanPhamCt.getMauSac().getId());
            hinhAnhMauSac.ifPresent(sanPhamCt::setHinhAnhMauSac);
        }

        String ma = "SPCT-" + UUID.randomUUID().toString().substring(0, 8);
        while (sanPhamCtRepository.existsByMa(ma)) {
            ma = "SPCT-" + UUID.randomUUID().toString().substring(0, 8);
        }
        sanPhamCt.setMa(ma);
        sanPhamCt.setNgayTao(LocalDateTime.now());
        sanPhamCt.setTrangThai(1); // Mặc định là hoạt động
        SanPhamCtAdm saved = sanPhamCtRepository.save(sanPhamCt);
        return mapToDTO(saved);
    }

    @Override
    public SanPhamCtDTOAdm updateSanPhamCt(Long id, SanPhamCtDTOAdm sanPhamCtDTO) {
        SanPhamCtAdm existing = sanPhamCtRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm chi tiết không tồn tại"));
        SanPhamCtAdm updated = mapToEntity(sanPhamCtDTO);
        validateSanPhamCt(updated);

        // Tự động gán hình ảnh dựa trên màu sắc
        if (updated.getMauSac() != null) {
            Optional<HinhAnhMauSacAdm> hinhAnhMauSac = hinhAnhMauSacRepository.findFirstByMauSacId(updated.getMauSac().getId());
            hinhAnhMauSac.ifPresent(updated::setHinhAnhMauSac);
        }

        if (!existing.getMa().equals(updated.getMa()) && sanPhamCtRepository.existsByMa(updated.getMa())) {
            throw new IllegalArgumentException("Mã sản phẩm chi tiết đã tồn tại");
        }
        existing.setHinhAnhMauSac(updated.getHinhAnhMauSac());
        existing.setSanPham(updated.getSanPham());
        existing.setMauSac(updated.getMauSac());
        existing.setThuongHieu(updated.getThuongHieu());
        existing.setKichThuoc(updated.getKichThuoc());
        existing.setXuatXu(updated.getXuatXu());
        existing.setChatLieu(updated.getChatLieu());
        existing.setMa(updated.getMa());
        existing.setGiaNhap(updated.getGiaNhap());
        existing.setGiaBan(updated.getGiaBan());
        existing.setSoLuong(updated.getSoLuong());
        existing.setMoTa(updated.getMoTa());
        existing.setTrangThai(updated.getTrangThai());
        existing.setNgaySua(LocalDateTime.now());
        SanPhamCtAdm saved = sanPhamCtRepository.save(existing);
        return mapToDTO(saved);
    }

    // Các phương thức khác giữ nguyên
    @Override
    public void deleteSanPhamCt(Long id) {
        SanPhamCtAdm sanPhamCt = sanPhamCtRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm chi tiết không tồn tại"));
        sanPhamCt.setTrangThai(0); // Soft delete
        sanPhamCt.setNgayXoa(LocalDateTime.now());
        sanPhamCtRepository.save(sanPhamCt);
    }

    @Override
    public Optional<SanPhamCtDTOAdm> findById(Long id) {
        return sanPhamCtRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public Optional<SanPhamCtDTOAdm> findByMa(String ma) {
        return sanPhamCtRepository.findByMa(ma).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> searchBySanPhamTen(String ten, Pageable pageable) {
        return sanPhamCtRepository.findBySanPhamTenContaining(ten, pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> findByTrangThai(Integer trangThai, Pageable pageable) {
        return sanPhamCtRepository.findByTrangThai(trangThai, pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> findAll(Pageable pageable) {
        return sanPhamCtRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> findBySanPhamId(Long sanPhamId, Pageable pageable) {
        return sanPhamCtRepository.findBySanPhamId(sanPhamId, pageable).map(this::mapToDTO);
    }

    @Override
    public Page<SanPhamCtDTOAdm> findByMauSacId(Long mauSacId, Pageable pageable) {
        return sanPhamCtRepository.findByMauSacId(mauSacId, pageable).map(this::mapToDTO);
    }

    private void validateSanPhamCt(SanPhamCtAdm sanPhamCt) {
        if (sanPhamCt.getSanPham() == null || !sanPhamRepository.existsById(sanPhamCt.getSanPham().getId())) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại");
        }
        if (sanPhamCt.getMauSac() == null || !mauSacRepository.existsById(sanPhamCt.getMauSac().getId())) {
            throw new IllegalArgumentException("Màu sắc không tồn tại");
        }
        if (sanPhamCt.getThuongHieu() == null || !thuongHieuRepository.existsById(sanPhamCt.getThuongHieu().getId())) {
            throw new IllegalArgumentException("Thương hiệu không tồn tại");
        }
        if (sanPhamCt.getKichThuoc() == null || !kichThuocRepository.existsById(sanPhamCt.getKichThuoc().getId())) {
            throw new IllegalArgumentException("Kích thước không tồn tại");
        }
        if (sanPhamCt.getXuatXu() == null || !xuatXuRepository.existsById(sanPhamCt.getXuatXu().getId())) {
            throw new IllegalArgumentException("Xuất xứ không tồn tại");
        }
        if (sanPhamCt.getChatLieu() == null || !chatLieuRepository.existsById(sanPhamCt.getChatLieu().getId())) {
            throw new IllegalArgumentException("Chất liệu không tồn tại");
        }
        if (sanPhamCt.getGiaBan() == null || sanPhamCt.getGiaBan().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Giá bán phải lớn hơn 0");
        }
        if (sanPhamCt.getSoLuong() == null || sanPhamCt.getSoLuong() < 0) {
            throw new IllegalArgumentException("Số lượng không được nhỏ hơn 0");
        }
    }

    private SanPhamCtAdm mapToEntity(SanPhamCtDTOAdm dto) {
        SanPhamCtAdm entity = new SanPhamCtAdm();
        entity.setId(dto.getId());
        if (dto.getHinhAnhMauSacId() != null) {
            entity.setHinhAnhMauSac(hinhAnhMauSacRepository.findById(dto.getHinhAnhMauSacId())
                    .orElseThrow(() -> new IllegalArgumentException("Hình ảnh màu sắc không tồn tại")));
        }
        entity.setSanPham(sanPhamRepository.findById(dto.getSanPhamId())
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại")));
        entity.setMauSac(mauSacRepository.findById(dto.getMauSacId())
                .orElseThrow(() -> new IllegalArgumentException("Màu sắc không tồn tại")));
        entity.setThuongHieu(thuongHieuRepository.findById(dto.getThuongHieuId())
                .orElseThrow(() -> new IllegalArgumentException("Thương hiệu không tồn tại")));
        entity.setKichThuoc(kichThuocRepository.findById(dto.getKichThuocId())
                .orElseThrow(() -> new IllegalArgumentException("Kích thước không tồn tại")));
        entity.setXuatXu(xuatXuRepository.findById(dto.getXuatXuId())
                .orElseThrow(() -> new IllegalArgumentException("Xuất xứ không tồn tại")));
        entity.setChatLieu(chatLieuRepository.findById(dto.getChatLieuId())
                .orElseThrow(() -> new IllegalArgumentException("Chất liệu không tồn tại")));
        entity.setMa(dto.getMa());
        entity.setGiaNhap(dto.getGiaNhap());
        entity.setGiaBan(dto.getGiaBan());
        entity.setSoLuong(dto.getSoLuong());
        entity.setMoTa(dto.getMoTa());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNgayTao(dto.getNgayTao());
        entity.setNgaySua(dto.getNgaySua());
        entity.setNgayXoa(dto.getNgayXoa());
        return entity;
    }

    private SanPhamCtDTOAdm mapToDTO(SanPhamCtAdm entity) {
        SanPhamCtDTOAdm dto = new SanPhamCtDTOAdm();
        dto.setId(entity.getId());
        dto.setHinhAnhMauSacId(entity.getHinhAnhMauSac() != null ? entity.getHinhAnhMauSac().getId() : null);
        dto.setSanPhamId(entity.getSanPham().getId());
        dto.setMauSacId(entity.getMauSac().getId());
        dto.setThuongHieuId(entity.getThuongHieu().getId());
        dto.setKichThuocId(entity.getKichThuoc().getId());
        dto.setXuatXuId(entity.getXuatXu().getId());
        dto.setChatLieuId(entity.getChatLieu().getId());
        dto.setMa(entity.getMa());
        dto.setGiaNhap(entity.getGiaNhap());
        dto.setGiaBan(entity.getGiaBan());
        dto.setSoLuong(entity.getSoLuong());
        dto.setMoTa(entity.getMoTa());
        dto.setTrangThai(entity.getTrangThai());
        dto.setNgayTao(entity.getNgayTao());
        dto.setNgaySua(entity.getNgaySua());
        dto.setNgayXoa(entity.getNgayXoa());
        // Điền thông tin bổ sung
        dto.setTenSanPham(entity.getSanPham().getTen());
        dto.setTenMauSac(entity.getMauSac().getTen());
        dto.setTenThuongHieu(entity.getThuongHieu().getTen());
        dto.setTenKichThuoc(entity.getKichThuoc().getTen());
        dto.setTenXuatXu(entity.getXuatXu().getTen());
        dto.setTenChatLieu(entity.getChatLieu().getTen());
        dto.setUrlHinhAnhMauSac(entity.getHinhAnhMauSac() != null ? entity.getHinhAnhMauSac().getHinhAnh() : null);
        return dto;
    }

}
