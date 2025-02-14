package com.app.services;

import com.app.entites.Brand;
import com.app.exceptions.APIException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.payloads.BrandDTO;
import com.app.payloads.BrandResponse;
import com.app.repositories.BrandRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class BrandServiceImpl implements BrandService{
    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BrandDTO createBrand(Brand brand){
        Brand savedBrand = brandRepo.findByName(brand.getName());
        if(savedBrand != null){
            throw new APIException("Brand with the name '" + brand.getName() + "' already exists !!!");
        }

        savedBrand = brandRepo.save(brand);
        return modelMapper.map(savedBrand, BrandDTO.class);
    }

    @Override
    public BrandResponse getBrands(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder){
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Brand> pageBrands = brandRepo.findAll(pageDetails);
        List<Brand> brands = pageBrands.getContent();

        List<BrandDTO> brandDTOS = brands.stream().map(brand -> modelMapper.map(brand, BrandDTO.class)).toList();

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setContent(brandDTOS);
        brandResponse.setPageNumber(pageBrands.getNumber());
        brandResponse.setPageSize(pageBrands.getSize());
        brandResponse.setTotalElements(pageBrands.getTotalElements());
        brandResponse.setTotalPages(pageBrands.getTotalPages());
        brandResponse.setLastPage(pageBrands.isLast());

        return brandResponse;
    }

    @Override
    public BrandDTO updateBrand(Brand brand, Long brandId){
        Brand savedBrand = brandRepo.findById(brandId).orElseThrow(() -> new ResourceNotFoundException("Brand", "brandId", brandId));

        brand.setId(savedBrand.getId());

        savedBrand = brandRepo.save(brand);

        return modelMapper.map(savedBrand, BrandDTO.class);
    }

    @Override
    public String deleteBrand(Long brandId){
        brandRepo.deleteById(brandId);
        return "Brand with id: " + brandId + " deleted successfully !!!";
    }
}
