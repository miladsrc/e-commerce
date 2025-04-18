package com.example.bussinessshope.business.service;

import com.example.bussinessshope.business.dto.BusinessCreateDto;
import com.example.bussinessshope.business.dto.BusinessResponseDto;
import com.example.bussinessshope.business.dto.BusinessUpdateDto;
import com.example.bussinessshope.business.entity.BusinessEntity;
import com.example.bussinessshope.business.repository.BusinessRepository;
import com.example.bussinessshope.shared.exception.specified.BusinessNotFoundException;
import com.example.bussinessshope.shared.exception.specified.UserIsNotOwnerOfBusiness;
import com.example.bussinessshope.shared.exception.specified.UserNotFoundException;
import com.example.bussinessshope.user.entity.UserEntity;
import com.example.bussinessshope.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BusinessService(
            BusinessRepository businessRepository,
            UserRepository userRepository, ModelMapper modelMapper) {

        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<BusinessResponseDto> getBusinessByUserId(
            Long id) {

        return businessRepository.findAllByUserId(id).stream()
                .map(businessEntity -> Optional.ofNullable(businessEntity)
                        .map(business -> modelMapper.map(business, BusinessResponseDto.class))
                        .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + id)))
                .collect(Collectors.toList());
    }

    public BusinessResponseDto createBusiness(
            BusinessCreateDto businessCreateDto,
            String username) {

        UserEntity user = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        BusinessEntity businessEntity = modelMapper.map(businessCreateDto, BusinessEntity.class);
        businessEntity.setUser(user);
        BusinessEntity savedBusiness = businessRepository.save(businessEntity);
        return modelMapper.map(savedBusiness, BusinessResponseDto.class);
    }

    public BusinessResponseDto getBusinessById(
            Long userId,
            Long businessId) {

        BusinessEntity businessEntity = businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException("Business doesn't exist!"));
        Long businessUserId = businessEntity.getUser().getId();
        if (!businessUserId.equals(userId)){
            throw new UserIsNotOwnerOfBusiness("User is not owner of business ");
        }

        return modelMapper.map(businessEntity, BusinessResponseDto.class);
    }

    @Transactional
    public BusinessResponseDto updateBusiness(
            BusinessUpdateDto businessUpdateDto,
            long businessId,
            long userId) {

        BusinessEntity businessEntity = businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found!"));
        if (!businessEntity.getUser().getId().equals(userId)) {
            throw new UserIsNotOwnerOfBusiness("User is not the owner of the business.");
        }
        if (!Objects.equals(businessEntity.getName(), businessUpdateDto.getName())) {
            businessEntity.setName(businessUpdateDto.getName());
        }
        if (!Objects.equals(businessEntity.getEmail(), businessUpdateDto.getEmail())) {
            businessEntity.setEmail(businessUpdateDto.getEmail());
        }
        if (!Objects.equals(businessEntity.getPhoneNumber(), businessUpdateDto.getPhoneNumber())) {
            businessEntity.setPhoneNumber(businessUpdateDto.getPhoneNumber());
        }
        if (!Objects.equals(businessEntity.getAddressList(), businessUpdateDto.getAddressList())) {
            businessEntity.setAddressList(businessUpdateDto.getAddressList());
        }
        if (!Objects.equals(businessEntity.getProductList(), businessUpdateDto.getProductList())) {
            businessEntity.setProductList(businessUpdateDto.getProductList());
        }
        BusinessEntity updatedBusiness = businessRepository.save(businessEntity);
        return modelMapper.map(updatedBusiness, BusinessResponseDto.class);
    }


    @Transactional
    public void deleteBusiness(long userId, long businessId){

        BusinessEntity businessEntity = businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessNotFoundException("Business not found!"));
        if (!businessEntity.getUser().getId().equals(userId)) {
            throw new UserIsNotOwnerOfBusiness("User is not the owner of the business.");
        }
        businessRepository.softDeleteBusinessEntityById(businessId);
    }

    public List<BusinessResponseDto> getAllBusiness(){

        return businessRepository.findAll().stream()
                .map(business -> modelMapper.map(business, BusinessResponseDto.class))
                .collect(Collectors.toList());
    }
}
