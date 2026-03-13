package org.sms.schoolmanagementsystem.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sms.schoolmanagementsystem.dto.AddressDto;
import org.sms.schoolmanagementsystem.dto.AddressUpdateDto;
import org.sms.schoolmanagementsystem.entity.Address;
import org.sms.schoolmanagementsystem.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void testSaveAddress() {
        AddressUpdateDto dto = new AddressUpdateDto(6045, "Ankara", "Çankaya");

        Address toSave = new Address();
        toSave.setZipCode(6045);
        toSave.setCity("Ankara");
        toSave.setDefinition("Çankaya");

        Address persisted = new Address();
        persisted.setId(1);
        persisted.setZipCode(6045);
        persisted.setCity("Ankara");
        persisted.setDefinition("Çankaya");

        when(addressRepository.save(any(Address.class))).thenReturn(persisted);

        AddressDto savedDto = addressService.save(dto);

        assertNotNull(savedDto);
        assertEquals(1, savedDto.getId());
        assertEquals("Ankara", savedDto.getCity());
        assertEquals("Çankaya", savedDto.getDefinition());
        assertEquals(6045, savedDto.getZipCode());

        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testGetAddressById_ReturnsMappedDto_WhenAddressExists() {
        Address address = new Address();
        address.setId(10);
        address.setZipCode(35045);
        address.setCity("İzmir");
        address.setDefinition("Bornova");

        when(addressRepository.findById(10)).thenReturn(Optional.of(address));

        AddressDto result = addressService.getAddressById(10);

        assertNotNull(result);
        assertEquals(10, result.getId());
        assertEquals("İzmir", result.getCity());
        assertEquals("Bornova", result.getDefinition());
        assertEquals(35045, result.getZipCode());

        verify(addressRepository, times(1)).findById(10);
    }

    @Test
    void testGetAddressById_ReturnsNull_WhenAddressDoesNotExist() {
        when(addressRepository.findById(99)).thenReturn(Optional.empty());

        AddressDto result = addressService.getAddressById(99);

        assertNull(result);
        verify(addressRepository, times(1)).findById(99);
    }

    @Test
    void testUpdateAddress() {
        int id = 5;
        Address existing = new Address();
        existing.setId(id);
        existing.setZipCode(6045);
        existing.setCity("Ankara");
        existing.setDefinition("Çankaya");

        AddressUpdateDto updateDto = new AddressUpdateDto(34100, "İstanbul", "Kadıköy");

        Address updated = new Address();
        updated.setId(id);
        updated.setZipCode(34100);
        updated.setCity("İstanbul");
        updated.setDefinition("Kadıköy");

        when(addressRepository.findById(id)).thenReturn(Optional.of(existing));
        when(addressRepository.save(any(Address.class))).thenReturn(updated);

        AddressDto updatedDto = addressService.update(id, updateDto);

        assertNotNull(updatedDto);
        assertEquals(id, updatedDto.getId());
        assertEquals("İstanbul", updatedDto.getCity());
        assertEquals("Kadıköy", updatedDto.getDefinition());
        assertEquals(34100, updatedDto.getZipCode());

        verify(addressRepository, times(1)).findById(id);
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void testUpdateAddress_ThrowsException_WhenAddressNotFound() {
        int id = 999;
        AddressUpdateDto updateDto = new AddressUpdateDto(34100, "İstanbul", "Kadıköy");

        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> addressService.update(id, updateDto));

        verify(addressRepository, times(1)).findById(id);
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void testDeleteAddress_WhenAddressExists() {
        int id = 3;
        Address existing = new Address();
        existing.setId(id);

        when(addressRepository.findById(id)).thenReturn(Optional.of(existing));
        doNothing().when(addressRepository).delete(existing);

        addressService.delete(id);

        verify(addressRepository, times(1)).findById(id);
        verify(addressRepository, times(1)).delete(existing);
    }

    @Test
    void testDeleteAddress_WhenAddressDoesNotExist() {
        int id = 100;
        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        addressService.delete(id);

        verify(addressRepository, times(1)).findById(id);
        verify(addressRepository, never()).delete(any(Address.class));
    }

    @Test
    void testGetAllAddresses() {
        Address address1 = new Address();
        address1.setId(1);
        address1.setZipCode(6045);
        address1.setCity("Ankara");
        address1.setDefinition("Çankaya");

        Address address2 = new Address();
        address2.setId(2);
        address2.setZipCode(35045);
        address2.setCity("İzmir");
        address2.setDefinition("Bornova");

        when(addressRepository.findAll()).thenReturn(List.of(address1, address2));

        List<AddressDto> allAddresses = addressService.getAllAddresses();

        assertEquals(2, allAddresses.size());

        boolean foundAnkara = false;
        boolean foundIzmir = false;
        for (AddressDto a : allAddresses) {
            if (a.getCity().equals("Ankara") && a.getDefinition().equals("Çankaya") && a.getZipCode() == 6045) {
                foundAnkara = true;
            }
            if (a.getCity().equals("İzmir") && a.getDefinition().equals("Bornova") && a.getZipCode() == 35045) {
                foundIzmir = true;
            }
        }
        assertTrue(foundAnkara);
        assertTrue(foundIzmir);

        verify(addressRepository, times(1)).findAll();
    }
    @Test
    void testUDtoToEntity() {
        AddressUpdateDto dto = new AddressUpdateDto(6045, "Ankara", "Çankaya");
        Address address = addressService.uDtoToEntity(dto);
        assertNotNull(address);
        assertEquals("Ankara", address.getCity());
        assertEquals("Çankaya", address.getDefinition());
        assertEquals(6045, address.getZipCode());
    }

    @Test
    void testEntityToDto() {
        AddressUpdateDto dto = new AddressUpdateDto(6045, "Ankara", "Çankaya");
        Address address = addressService.uDtoToEntity(dto);
        AddressDto addressDto = addressService.entityToDto(address);
        assertNotNull(addressDto);
        assertEquals("Ankara", addressDto.getCity());
        assertEquals("Çankaya", addressDto.getDefinition());
        assertEquals(6045, addressDto.getZipCode());
    }
}
