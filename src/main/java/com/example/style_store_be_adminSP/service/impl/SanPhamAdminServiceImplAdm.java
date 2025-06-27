package com.example.style_store_be_adminSP.service.impl;

import com.example.style_store_be_adminSP.entity.SanPhamAdm;
import com.example.style_store_be_adminSP.reposytory.SanPhamRepoAdm;
import com.example.style_store_be_adminSP.service.ICommonServiceAdm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class SanPhamAdminServiceImplAdm implements ICommonServiceAdm<SanPhamAdm> {

    private static final Logger logger = LoggerFactory.getLogger(SanPhamAdminServiceImplAdm.class);

    @Autowired
    private SanPhamRepoAdm sanPhamAdminRepository;

    /**
     * Lấy danh sách sản phẩm với phân trang
     *
     * @param page Số trang (bắt đầu từ 0)
     * @param size Số lượng bản ghi mỗi trang
     * @return Trang chứa danh sách sản phẩm
     */
    @Override
    public Page<SanPhamAdm> getAll(int page, int size) {
        logger.info("Lấy danh sách sản phẩm, trang: {}, kích thước: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamAdminRepository.findAll(pageable);
    }

    /**
     * Lấy một sản phẩm theo ID
     *
     * @param id ID của sản phẩm
     * @return Sản phẩm nếu tìm thấy, ném ngoại lệ nếu không tìm thấy
     * @throws SanPhamException nếu sản phẩm không tồn tại
     */
    @Override
    public SanPhamAdm getOne(Long id) {
        logger.info("Lấy sản phẩm với ID: {}", id);
        return sanPhamAdminRepository.findById(id)
                .orElseThrow(() -> new SanPhamException("Không tìm thấy sản phẩm với id: " + id));
    }

    /**
     * Thêm một sản phẩm mới
     *
     * @param object Sản phẩm cần thêm
     * @return Sản phẩm đã được lưu
     * @throws SanPhamException nếu dữ liệu không hợp lệ
     */
    @Override
    public SanPhamAdm add(SanPhamAdm object) {
        logger.info("Thêm sản phẩm mới: {}", object.getTen());
        validate(object);
        if (object.getMa() == null || object.getMa().trim().isEmpty()) {
            object.setMa("SP-" + UUID.randomUUID().toString().substring(0, 8));
        }
        object.setTrangThai(1); // Mặc định là hoạt động
        object.setNgayTao(LocalDateTime.now());
        object.setNgaySua(null);
        object.setNgayXoa(null);
        return sanPhamAdminRepository.save(object);
    }

    /**
     * Cập nhật thông tin sản phẩm
     *
     * @param object Sản phẩm chứa thông tin cập nhật
     * @throws SanPhamException nếu ID không hợp lệ hoặc sản phẩm không tồn tại
     */
    @Override
    public void update(SanPhamAdm object) {
        logger.info("Cập nhật sản phẩm với ID: {}", object.getId());
        if (object.getId() == null) {
            throw new SanPhamException("ID không được để trống khi cập nhật");
        }
        validate(object);
        SanPhamAdm existing = sanPhamAdminRepository.findById(object.getId())
                .orElseThrow(() -> new SanPhamException("Không tìm thấy sản phẩm với id: " + object.getId()));
        existing.setTen(object.getTen());
        existing.setMa(object.getMa()); // Cho phép cập nhật mã nếu cần
        existing.setNgaySua(LocalDateTime.now());
        sanPhamAdminRepository.save(existing);
    }

    /**
     * Xóa mềm sản phẩm (chuyển trạng thái sang 0 và cập nhật ngày xóa)
     *
     * @param id ID của sản phẩm cần xóa
     * @throws SanPhamException nếu ID không hợp lệ hoặc sản phẩm không tồn tại
     */
    @Override
    public void delete(Long id) {
        logger.info("Xóa mềm sản phẩm với ID: {}", id);
        if (id == null) {
            throw new SanPhamException("ID không được để trống khi xóa");
        }
        SanPhamAdm existing = sanPhamAdminRepository.findById(id)
                .orElseThrow(() -> new SanPhamException("Không tìm thấy sản phẩm với id: " + id));
        if (existing.getTrangThai() == 1) {
            existing.setNgayXoa(LocalDateTime.now());
            existing.setTrangThai(0);
            sanPhamAdminRepository.save(existing);
        } else {
            logger.warn("Sản phẩm với ID: {} đã ở trạng thái xóa", id);
        }
    }

    /**
     * Khôi phục sản phẩm đã xóa (chuyển trạng thái về 1 và xóa ngày xóa)
     *
     * @param id ID của sản phẩm cần khôi phục
     * @throws SanPhamException nếu ID không hợp lệ hoặc sản phẩm không tồn tại
     */
    public void restore(Long id) {
        logger.info("Khôi phục sản phẩm với ID: {}", id);
        SanPhamAdm existing = sanPhamAdminRepository.findById(id)
                .orElseThrow(() -> new SanPhamException("Không tìm thấy sản phẩm với id: " + id));
        if (existing.getTrangThai() == 0) {
            existing.setNgayXoa(null);
            existing.setTrangThai(1);
            existing.setNgaySua(LocalDateTime.now());
            sanPhamAdminRepository.save(existing);
        } else {
            logger.warn("Sản phẩm với ID: {} đã ở trạng thái hoạt động", id);
        }
    }

    /**
     * Lấy danh sách sản phẩm đang hoạt động với phân trang
     *
     * @param page Số trang
     * @param size Số lượng bản ghi mỗi trang
     * @return Trang chứa danh sách sản phẩm đang hoạt động
     */
    public Page<SanPhamAdm> getActive(int page, int size) {
        logger.info("Lấy danh sách sản phẩm hoạt động, trang: {}, kích thước: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamAdminRepository.findByNgayXoaIsNull(pageable);
    }

    /**
     * Lấy danh sách sản phẩm đã xóa với phân trang
     *
     * @param page Số trang
     * @param size Số lượng bản ghi mỗi trang
     * @return Trang chứa danh sách sản phẩm đã xóa
     */
    public Page<SanPhamAdm> getDeleted(int page, int size) {
        logger.info("Lấy danh sách sản phẩm đã xóa, trang: {}, kích thước: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamAdminRepository.findByNgayXoaIsNotNull(pageable);
    }

    /**
     * Tìm kiếm sản phẩm kèm tổng số lượng với phân trang
     *
     * @param search Chuỗi tìm kiếm
     * @param page   Số trang
     * @param size   Số lượng bản ghi mỗi trang
     * @return Trang chứa danh sách sản phẩm và tổng số lượng
     */
    public Page<SanPhamWithQuantity> searchSanPhamWithTotalQuantity(String search, int page, int size) {
        logger.info("Tìm kiếm sản phẩm với từ khóa: {}, trang: {}, kích thước: {}", search, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> result = sanPhamAdminRepository.findSanPhamWithTotalQuantity(search, pageable);
        List<SanPhamWithQuantity> sanPhamWithQuantities = result.getContent().stream()
                .map(obj -> {
                    SanPhamAdm sanPham = (SanPhamAdm) obj[0];
                    Long totalQuantity = (Long) obj[1];
                    return new SanPhamWithQuantity(sanPham, totalQuantity != null ? totalQuantity : 0);
                })
                .collect(Collectors.toList());
        return new PageImpl<>(sanPhamWithQuantities, pageable, result.getTotalElements());
    }

    /**
     * Xác thực dữ liệu sản phẩm
     *
     * @param object Sản phẩm cần xác thực
     * @throws SanPhamException nếu dữ liệu không hợp lệ
     */
    private void validate(SanPhamAdm object) {
        if (object.getTen() == null || object.getTen().trim().isEmpty()) {
            throw new SanPhamException("Tên sản phẩm không được để trống");
        }
        if (!object.getTen().trim().matches("^[\\p{L}\\s]+$")) {
            throw new SanPhamException("Tên sản phẩm chỉ được chứa chữ cái và khoảng trắng, không chứa số hoặc ký tự đặc biệt");
        }
        if (object.getTen().length() > 50) {
            throw new SanPhamException("Tên sản phẩm không được vượt quá 50 ký tự");
        }
        if (object.getTrangThai() != null && (object.getTrangThai() != 0 && object.getTrangThai() != 1)) {
            throw new SanPhamException("Trạng thái không hợp lệ");
        }
        Optional<SanPhamAdm> existing = sanPhamAdminRepository.findByTen(object.getTen().trim());
        if (existing.isPresent() && (object.getId() == null || !existing.get().getId().equals(object.getId()))) {
            throw new SanPhamException("Tên sản phẩm đã tồn tại");
        }
    }

    /**
     * Lớp ngoại lệ tùy chỉnh cho các lỗi liên quan đến sản phẩm
     */
    public static class SanPhamException extends RuntimeException {
        public SanPhamException(String message) {
            super(message);
        }
    }

    /**
     * Lớp để chứa thông tin sản phẩm và tổng số lượng
     */
    public static class SanPhamWithQuantity {
        private final SanPhamAdm sanPham;
        private final long totalQuantity;

        public SanPhamWithQuantity(SanPhamAdm sanPham, long totalQuantity) {
            this.sanPham = sanPham;
            this.totalQuantity = totalQuantity;
        }

        public SanPhamAdm getSanPham() {
            return sanPham;
        }

        public long getTotalQuantity() {
            return totalQuantity;
        }
    }
}
