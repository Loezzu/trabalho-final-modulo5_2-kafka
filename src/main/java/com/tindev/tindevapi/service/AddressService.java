package com.tindev.tindevapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tindev.tindevapi.dto.address.AddressCreateDTO;
import com.tindev.tindevapi.dto.address.AddressDTO;
import com.tindev.tindevapi.dto.log.LogDTO;
import com.tindev.tindevapi.entities.AddressEntity;
import com.tindev.tindevapi.enums.TipoLog;
import com.tindev.tindevapi.exceptions.RegraDeNegocioException;
import com.tindev.tindevapi.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final LogService logService;

    public List<AddressDTO> listAddress(Integer id) throws RegraDeNegocioException, JsonProcessingException {
        if(id != null){
            AddressEntity address = addressRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
            logService.logPost(TipoLog.ADDRESS,"Address "+ address.getIdAddress() +" listed");
            return addressRepository.findById(id)
                    .stream().map(
                            addressEntity -> objectMapper.convertValue(
                                    addressEntity, AddressDTO.class))
                    .collect(Collectors.toList());
        }
        logService.logPost(TipoLog.ADDRESS,"Address listed");
        return addressRepository.findAll()
                .stream()
                .map(address -> objectMapper.convertValue(address, AddressDTO.class))
                .collect(Collectors.toList());
    }


    public AddressDTO createAddress(AddressCreateDTO addressCreateDTO) throws JsonProcessingException {
        AddressEntity addressEntity = objectMapper.convertValue(addressCreateDTO, AddressEntity.class);
        AddressEntity savedAddressEntity = addressRepository.save(addressEntity);
        logService.logPost(TipoLog.ADDRESS,"Address "+ addressEntity.getIdAddress() +" created");
        return objectMapper.convertValue(savedAddressEntity, AddressDTO.class);
    }

    public AddressDTO updateAddress(AddressCreateDTO addressCreateDTO, Integer idAddress) throws RegraDeNegocioException, JsonProcessingException {
        addressRepository.findById(idAddress).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
        AddressEntity addressEntity = objectMapper.convertValue(
               (addressRepository.findById(idAddress)), AddressEntity.class);
        addressEntity.setIdAddress(idAddress);
        addressEntity.setStreet(addressCreateDTO.getStreet());
        addressEntity.setNumber(addressCreateDTO.getNumber());
        addressEntity.setCity(addressCreateDTO.getCity());
        addressEntity.setCep(addressCreateDTO.getCep());

        logService.logPost(TipoLog.ADDRESS,"Address "+ addressEntity.getIdAddress() +" updated");
        return objectMapper.convertValue((addressRepository.save(addressEntity)), AddressDTO.class);
    }

    public void deleteAddress(Integer id) throws RegraDeNegocioException, JsonProcessingException {
       AddressEntity addressEntity = addressRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
        logService.logPost(TipoLog.ADDRESS,"Address "+ addressEntity.getIdAddress() +" deleted");
        addressRepository.deleteById(id);
    }

    public AddressDTO getLogedUserAddress() throws RegraDeNegocioException, JsonProcessingException {
        AddressEntity address = userService.getLogedUser().getAddress();
        logService.logPost(TipoLog.ADDRESS,"Address "+ address.getIdAddress() +" listed");
        return objectMapper.convertValue(address, AddressDTO.class);

    }

    public AddressDTO updateLogedUserAddress(AddressCreateDTO addressCreateDTO) throws RegraDeNegocioException, JsonProcessingException {
        AddressEntity address = userService.getLogedUser().getAddress();
        address.setStreet(addressCreateDTO.getStreet());
        address.setNumber(addressCreateDTO.getNumber());
        address.setCity(addressCreateDTO.getCity());
        address.setCep(addressCreateDTO.getCep());
        logService.logPost(TipoLog.ADDRESS,"Address "+ address.getIdAddress() +" updated");
        return objectMapper.convertValue(addressRepository.save(address), AddressDTO.class);
    }
}