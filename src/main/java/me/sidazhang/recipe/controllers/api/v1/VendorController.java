package me.sidazhang.recipe.controllers.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.sidazhang.recipe.api.v1.DTO.VendorDTO;
import me.sidazhang.recipe.api.v1.DTO.VendorListDTO;
import me.sidazhang.recipe.services.v1.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api("Vendor api")
@RestController
@RequestMapping({"/api/v1/vendor", "/api/v1/vendor/"})
public class VendorController {
    private VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation("View List of Vendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendorByDTO(id, vendorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}