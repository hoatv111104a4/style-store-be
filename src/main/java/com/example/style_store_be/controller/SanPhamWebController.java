package com.example.style_store_be.controller;

import com.example.style_store_be.dto.request.ApiResponse;
import com.example.style_store_be.entity.ChiTietSanPham;
import com.example.style_store_be.service.SanPhamWebService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web-san-pham")
public class SanPhamWebController {
    private final SanPhamWebService sanPhamWebService;

    public SanPhamWebController(SanPhamWebService sanPhamWebService) {
        this.sanPhamWebService = sanPhamWebService;
    }

    @GetMapping("/hien-thi")
    Page<ChiTietSanPham> getPageChiTietSanPham(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "12") int size){
        Pageable pageable = PageRequest.of(page,size);
        return sanPhamWebService.getPageChiTietSanPham(pageable);
    }
}
