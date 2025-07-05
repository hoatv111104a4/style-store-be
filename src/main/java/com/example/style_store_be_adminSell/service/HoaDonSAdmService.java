package com.example.style_store_be_adminSell.service;

import com.example.style_store_be_adminSP.dto.SanPhamCtDTOAdm;
import com.example.style_store_be_adminSell.dto.HoaDonSAdmDto;
import com.example.style_store_be_adminSell.entity.HoaDonSAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HoaDonSAdmService {

    Page<HoaDonSAdmDto> findAll(Pageable pageable);

    Page<HoaDonSAdmDto> findByTrangThai(Integer trangThai, Pageable pageable);

    HoaDonSAdm findOne(String ma);

    HoaDonSAdmDto addHoaDon(HoaDonSAdmDto hoaDon);

    HoaDonSAdm findHoaDonById(Long id);
}
