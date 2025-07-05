package com.example.style_store_be_adminSell.repository;

import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaDonSAdmRepo extends JpaRepository<HoaDonSAdm, Long> {

    boolean existsByMa(String ma);

    Page<HoaDonSAdm> findByTrangThai(Integer trangThai, Pageable pageable);

    HoaDonSAdm findByMa(String ma);


}
