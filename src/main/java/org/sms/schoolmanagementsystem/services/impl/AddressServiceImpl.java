package org.sms.schoolmanagementsystem.services.impl;

import jakarta.transaction.Transactional;
import org.sms.schoolmanagementsystem.dto.AddressDto;
import org.sms.schoolmanagementsystem.dto.AddressUpdateDto;
import org.sms.schoolmanagementsystem.entity.Address;
import org.sms.schoolmanagementsystem.repository.AddressRepository;
import org.sms.schoolmanagementsystem.services.IAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressServiceImpl implements IAddressService {
    private final AddressRepository addressRepository;
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDto save(AddressUpdateDto addressUpdateDto) {
        Address address = uDtoToEntity(addressUpdateDto);
        address = addressRepository.save(address);
        return entityToDto(address);
    }
    @Override
    public AddressDto update(int id, AddressUpdateDto addressUpdateDto) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(optionalAddress.isPresent()) {
            Address newAddress=uDtoToEntity(addressUpdateDto);
            newAddress.setId(id);
            Address updatedAddress = addressRepository.save(newAddress);
            return entityToDto(updatedAddress);
        } else {
            throw new RuntimeException("Address with id " + id + " not found.");
        }
    }
    @Override
    public void delete(int id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        optionalAddress.ifPresent(addressRepository::delete);
    }
    @Override
    public AddressDto getAddressById(int id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(optionalAddress.isPresent()) {
            return entityToDto(optionalAddress.get());
        }
        return null;
    }
    @Override
    public List<AddressDto> getAllAddresses() {
        List<AddressDto> addressDtos = new ArrayList<>();
        for(Address address : addressRepository.findAll()) {
            AddressDto addressDto = entityToDto(address);
            addressDtos.add(addressDto);
        }
        return addressDtos;
    }

    public Address uDtoToEntity(AddressUpdateDto addressUpdateDto) {
        Address address = new Address();
        BeanUtils.copyProperties(addressUpdateDto, address);
        return address;
    }

    public AddressDto entityToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        BeanUtils.copyProperties(address, addressDto);
        return addressDto;
    }
}
