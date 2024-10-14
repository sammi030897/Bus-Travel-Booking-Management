package com.booking.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.entity.BusOperator;
import com.booking.payload.BusOperatorDTO;
import com.booking.repository.BusOperatorRepository;
@Service
public class BusOperatorServiceImpl implements BusOperatorService {

    private final BusOperatorRepository busOperatorRepository;

    @Autowired
    public BusOperatorServiceImpl(BusOperatorRepository busOperatorRepository) {
        this.busOperatorRepository = busOperatorRepository;
    }

    @Override
    public BusOperatorDTO createBusOperator(BusOperatorDTO busOperatorDTO) {
        BusOperator busOperator = dtoToBusOperator(busOperatorDTO);

        busOperator.setCreatedAt(new Date());
        busOperator.setUpdatedAt(new Date());
        
        BusOperator savedUser = busOperatorRepository.save(busOperator);

        return busOperatorToDto(savedUser);
    }

    private BusOperator dtoToBusOperator(BusOperatorDTO busOperatorDTO) {
        BusOperator busOperator = new BusOperator();
        busOperator.setOperatorName(busOperatorDTO.getOperatorName());
        busOperator.setContactEmail(busOperatorDTO.getContactEmail());
        busOperator.setContactPhone(busOperatorDTO.getContactPhone());
        busOperator.setLogoUrl(busOperatorDTO.getLogoUrl());
       
        return busOperator;
    }

    private BusOperatorDTO busOperatorToDto(BusOperator busOperator) {
        BusOperatorDTO busOperatorDto = new BusOperatorDTO();
        busOperatorDto.setId(busOperator.getId());
        busOperatorDto.setOperatorName(busOperator.getOperatorName());
        busOperatorDto.setContactEmail(busOperator.getContactEmail());
        busOperatorDto.setContactPhone(busOperator.getContactPhone());
        busOperatorDto.setLogoUrl(busOperator.getLogoUrl());
        busOperatorDto.setCreatedAt(busOperator.getCreatedAt());
        busOperatorDto.setUpdatedAt(busOperator.getUpdatedAt());

        return busOperatorDto;
    }

}



