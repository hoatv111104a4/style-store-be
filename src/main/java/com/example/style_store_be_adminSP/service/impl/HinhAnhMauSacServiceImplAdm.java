package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be.entity.MauSacSp;
import com.example.style_store_be_adminSP.entity.HinhAnhMauSacAdm;
import com.example.style_store_be_adminSP.reposytory.HinhAnhRepoAdm;
import com.example.style_store_be_adminSP.service.HinhAnhMauSacServiceAdm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class HinhAnhMauSacServiceImplAdm implements HinhAnhMauSacServiceAdm {

    private static final Logger logger = LoggerFactory.getLogger(HinhAnhMauSacServiceImplAdm.class);

    @Autowired
    private HinhAnhRepoAdm repository;

    @Value("${upload.dir:src/main/resources/static/uploads}")
    private String uploadDir;

    // Phương thức tiện ích để định dạng đường dẫn hình ảnh
    private String formatHinhAnh(String hinhAnh, LocalDateTime ngayXoa) {
        if (hinhAnh != null && !hinhAnh.isEmpty() && !hinhAnh.startsWith("/uploads/") && ngayXoa == null) {
            return "/uploads/" + hinhAnh;
        }
        return hinhAnh;
    }

    @Override
    public Page<HinhAnhMauSacAdm> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findAll(pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh(), h.getNgayXoa())));
        return pageResult;
    }

    @Override
    public Page<HinhAnhMauSacAdm> searchByHinhAnh(String hinhAnh, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findByHinhAnhContainingIgnoreCase(hinhAnh, pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh(), h.getNgayXoa())));
        return pageResult;
    }

    @Override
    public Page<HinhAnhMauSacAdm> getActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findByNgayXoaIsNull(pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh(), h.getNgayXoa())));
        return pageResult;
    }

    @Override
    public Page<HinhAnhMauSacAdm> getDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HinhAnhMauSacAdm> pageResult = repository.findByNgayXoaIsNotNull(pageable);
        pageResult.getContent().forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh(), h.getNgayXoa())));
        return pageResult;
    }

    @Override
    public HinhAnhMauSacAdm getOne(Long id) {
        HinhAnhMauSacAdm hinhAnh = repository.findById(id)
                .orElseThrow(() -> new HinhAnhMauSacNotFoundException("Hình ảnh màu sắc không tồn tại với id: " + id));
        hinhAnh.setHinhAnh(formatHinhAnh(hinhAnh.getHinhAnh(), hinhAnh.getNgayXoa()));
        return hinhAnh;
    }

    @Override
    public void add(HinhAnhMauSacAdm hinhAnhMauSac) {
        if (hinhAnhMauSac.getHinhAnh() == null || hinhAnhMauSac.getHinhAnh().isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn hình ảnh không được rỗng");
        }

        hinhAnhMauSac.setNgayTao(LocalDateTime.now());
        hinhAnhMauSac.setTrangThai(1);
        repository.save(hinhAnhMauSac);
        logger.info("Thêm mới hình ảnh thành công: {}", hinhAnhMauSac.getHinhAnh());
    }

    @Override
    public void update(HinhAnhMauSacAdm hinhAnhMauSac) {
        HinhAnhMauSacAdm existing = getOne(hinhAnhMauSac.getId());
        if (hinhAnhMauSac.getHinhAnh() != null && !hinhAnhMauSac.getHinhAnh().isEmpty()) {
            existing.setHinhAnh(hinhAnhMauSac.getHinhAnh());
        }

        existing.setMoTa(hinhAnhMauSac.getMoTa());
        existing.setMauSac(hinhAnhMauSac.getMauSac());
        existing.setTrangThai(hinhAnhMauSac.getTrangThai());
        existing.setNgaySua(LocalDateTime.now());
        repository.save(existing);
        logger.info("Cập nhật hình ảnh thành công với id: {}", existing.getId());
    }

    @Override
    public void delete(Long id) {
        HinhAnhMauSacAdm existing = getOne(id);
        existing.setNgayXoa(LocalDateTime.now());
        existing.setTrangThai(0);
        repository.save(existing);
        logger.info("Xóa mềm hình ảnh thành công với id: {}", id);
    }

    @Override
    public List<HinhAnhMauSacAdm> getByMauSacId(Long mauSacId) {
        List<HinhAnhMauSacAdm> hinhAnhList = repository.findByMauSacIdAndActive(mauSacId);
        hinhAnhList.forEach(h -> h.setHinhAnh(formatHinhAnh(h.getHinhAnh(), h.getNgayXoa())));
        return hinhAnhList;
    }

    @Override
    public String uploadImage(MultipartFile file, Long mauSacId) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File không được rỗng");
        }

        if (file.getSize() > 5 * 1024 * 1024) { // Giới hạn 5MB
            throw new IllegalArgumentException("File vượt quá kích thước cho phép");
        }

        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(".jpg", ".jpeg", ".png").contains(extension)) {
            throw new IllegalArgumentException("Định dạng file không được hỗ trợ");
        }

        try {
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new IllegalArgumentException("Tên file không hợp lệ");
            }

            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = originalFilename;

            File dest = new File(uploadDir + File.separator + uniqueFileName);
            int count = 1;
            while (dest.exists()) {
                uniqueFileName = baseName + "_" + count + fileExtension;
                dest = new File(uploadDir + File.separator + uniqueFileName);
                count++;
            }

            file.transferTo(dest);

            HinhAnhMauSacAdm entity = new HinhAnhMauSacAdm();
            entity.setHinhAnh(uniqueFileName);
            entity.setNgayTao(LocalDateTime.now());
            entity.setTrangThai(1);

            MauSacSp mauSac = new MauSacSp();
            mauSac.setId(mauSacId);
            entity.setMauSac(mauSac);

            repository.save(entity);
            logger.info("Tải lên thành công file: {}", uniqueFileName);

            return uniqueFileName;
        } catch (IOException e) {
            logger.error("Lỗi khi lưu file: {}", e.getMessage());
            throw new RuntimeException("Lỗi khi lưu file: " + e.getMessage());
        }
    }
}

// Ngoại lệ tùy chỉnh
class HinhAnhMauSacNotFoundException extends RuntimeException {
    public HinhAnhMauSacNotFoundException(String message) {
        super(message);
    }
}