package org.sms.schoolmanagementsystem.controller.impl;

import jakarta.validation.Valid;
import org.sms.schoolmanagementsystem.controller.IAddressController;
import org.sms.schoolmanagementsystem.dto.AddressDto;
import org.sms.schoolmanagementsystem.dto.AddressUpdateDto;
import org.sms.schoolmanagementsystem.services.IAddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
public class AddressControllerImpl implements IAddressController {
    private final IAddressService addressService;
    public AddressControllerImpl(IAddressService addressService) {
        this.addressService = addressService;
    }
    @PostMapping("/save")
    @Override
    public AddressDto save(@RequestBody @Valid AddressUpdateDto addressUpdateDto) {
        return addressService.save(addressUpdateDto);
    }
    @PostMapping("/update/{id}")
    @Override
    public AddressDto update(@PathVariable int id, @RequestBody @Valid AddressUpdateDto addressUpdateDto) {
        return addressService.update(id, addressUpdateDto);
    }
    @PostMapping("/delete/{id}")
    @Override
    public void delete(@PathVariable  int id) {
        addressService.delete(id);
    }
    @GetMapping("/list/{id}")
    @Override
    public AddressDto getAddressById(@PathVariable int id) {
        return addressService.getAddressById(id);
    }
    @GetMapping("/list")
    @Override
    public List<AddressDto> getAllAddresses() {
        return addressService.getAllAddresses();
    }
}
