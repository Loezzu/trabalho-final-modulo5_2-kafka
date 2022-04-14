package com.tindev.tindevapi;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tindev.tindevapi.dto.address.AddressCreateDTO;
import com.tindev.tindevapi.entities.AddressEntity;
import com.tindev.tindevapi.repository.AddressRepository;
import com.tindev.tindevapi.exceptions.RegraDeNegocioException;
import com.tindev.tindevapi.service.AddressService;
import com.tindev.tindevapi.service.LogService;
import com.tindev.tindevapi.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AddressTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private LogService logService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateAddress() throws JsonProcessingException {
        AddressEntity addressEntity = getAddressEntity();
        AddressCreateDTO addressCreateDTO = getAddressCreate();
        when(objectMapper.convertValue(addressCreateDTO, AddressEntity.class)).thenReturn(addressEntity);
        when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
        addressService.createAddress(addressCreateDTO);
        verify(addressRepository, times(1)).save(any(AddressEntity.class));
    }

    @Test
    public void testExceptionListAddress(){
        assertThrows(RegraDeNegocioException.class, () -> {
            addressService.listAddress(10000);
        });
    }


    @Test
    public void testDeleteAddress() throws RegraDeNegocioException, JsonProcessingException {
        when(addressRepository.findById(anyInt())).thenReturn(Optional.ofNullable(getAddressEntity()));
        addressService.deleteAddress(1);
        verify(addressRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void deveAtualizarEndereco() throws RegraDeNegocioException, JsonProcessingException {

        AddressEntity addressEntity = getAddressEntity();
        AddressCreateDTO addressCreateDTO = getAddressCreate();

        when(addressRepository.findById(anyInt())).thenReturn(Optional.ofNullable(addressEntity));
        when(objectMapper.convertValue(eq(addressRepository.findById(anyInt())), eq(AddressEntity.class))).thenReturn(addressEntity);

        addressService.updateAddress(addressCreateDTO, 10);

        verify(addressRepository,times(1)).save(addressEntity);
    }



    private AddressEntity getAddressEntity(){
        return AddressEntity.builder()
                .idAddress(1)
                .street("rua admin")
                .number(123)
                .city("São Paulo")
                .cep("01234567")
                .build();
    }

    private AddressCreateDTO getAddressCreate(){
        AddressCreateDTO addressCreateDTO = new AddressCreateDTO();
        addressCreateDTO.setStreet("rua admin");
        addressCreateDTO.setNumber(123);
        addressCreateDTO.setCity("São Paulo");
        addressCreateDTO.setCep("01234567");
        return addressCreateDTO;
    }



}
