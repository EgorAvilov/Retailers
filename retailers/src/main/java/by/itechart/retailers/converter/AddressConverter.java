package by.itechart.retailers.converter;

import by.itechart.retailers.dto.AddressDto;
import by.itechart.retailers.entity.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressConverter {
    public AddressDto entityToDto(Address address) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(address, AddressDto.class);
    }

    public Address dtoToEntity(AddressDto addressDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(addressDto, Address.class);
    }

    public List<AddressDto> entityToDto(List<Address> addresses) {
        return addresses.stream()
                        .map(this::entityToDto)
                        .collect(Collectors.toList());
    }

    public List<Address> dtoToEntity(List<AddressDto> addressDtos) {
        return addressDtos.stream()
                          .map(this::dtoToEntity)
                          .collect(Collectors.toList());
    }
}
