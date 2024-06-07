package com.duy.assignment.service.impl;

import com.duy.assignment.dto.BrandDTO;
import com.duy.assignment.entity.Brand;
import com.duy.assignment.mapper.BrandMapper;
import com.duy.assignment.repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplementTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandServiceImplement brandService;

    @Test
    void findAllBrands() {
        // Given
        List<Brand> entityList = new ArrayList<>();
        when(brandRepository.findAll()).thenReturn(entityList);

        List<BrandDTO> dtoList = new ArrayList<>();
        when(brandMapper.toDTOs(entityList)).thenReturn(dtoList);

        // When
        List<BrandDTO> result = brandService.findAllBrands();

        // Then
        assertSame(result, dtoList);
        verify(brandRepository).findAll();
        verify(brandMapper).toDTOs(entityList);

    }
}