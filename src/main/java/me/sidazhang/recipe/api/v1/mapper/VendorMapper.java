package me.sidazhang.recipe.api.v1.mapper;


import me.sidazhang.recipe.api.v1.DTO.VendorDTO;
import me.sidazhang.recipe.models.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO VendorToVendorDTO(Vendor vendor);

    Vendor VendorDTOToVendor(VendorDTO vendorDTO);
}
