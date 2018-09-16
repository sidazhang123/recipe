package me.sidazhang.recipe.services.v1;


import me.sidazhang.recipe.api.v1.DTO.VendorDTO;

import java.util.List;

public interface VendorService {
    VendorDTO getVendorById(Long id);

    List<VendorDTO> getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    void deleteVendorById(Long id);
}
