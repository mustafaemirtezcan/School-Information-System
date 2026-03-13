package org.sms.schoolmanagementsystem.controller;

import org.sms.schoolmanagementsystem.dto.AddressDto;
import org.sms.schoolmanagementsystem.dto.AddressUpdateDto;

import java.util.List;

public interface IAddressController {
    AddressDto save(AddressUpdateDto addressUpdateDto);
    AddressDto update(int id, AddressUpdateDto addressUpdateDto);
    void delete(int id);
    AddressDto getAddressById(int id);
    List<AddressDto> getAllAddresses();
}
