package com.example.style_store_be_adminSP.reposytory;

import com.example.style_store_be.entity.ChatLieu;
import com.example.style_store_be_adminSP.entity.ChatLieuAdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatLieuRepoAdm extends JpaRepository<ChatLieuAdm,Long> {
    Page<ChatLieuAdm> findByNgayXoaIsNull(Pageable pageable);
    Page<ChatLieuAdm> findByNgayXoaIsNotNull(Pageable pageable);
    Page<ChatLieuAdm> findByTenContainingIgnoreCase(String ten, Pageable pageable);

    Optional<ChatLieuAdm> findByTen(String trim);
}
