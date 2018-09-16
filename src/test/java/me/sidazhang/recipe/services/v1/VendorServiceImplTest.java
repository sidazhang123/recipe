package me.sidazhang.recipe.services.v1;


import me.sidazhang.recipe.api.v1.DTO.VendorDTO;
import me.sidazhang.recipe.api.v1.DTO.VendorListDTO;
import me.sidazhang.recipe.api.v1.mapper.VendorMapper;
import me.sidazhang.recipe.exceptions.ResourceNotFoundException;
import me.sidazhang.recipe.models.Vendor;
import me.sidazhang.recipe.repositories.v1.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class VendorServiceImplTest {

    private static final String NAME_1 = "My Vendor";
    private static final long ID_1 = 1L;
    private static final String NAME_2 = "My Vendor";
    private static final long ID_2 = 1L;

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void getVendorById() {
        //given
        Vendor vendor = getVendor1();

        //mockito BDD syntax
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());

        //JUnit Assert that with matchers
        assertThat(vendorDTO.getName(), is(equalTo(NAME_1)));
    }


    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() {
        //given
        //mockito BBD syntax since mockito 1.10.0
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());

    }

    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(getVendor1(), getVendor2());
        given(vendorRepository.findAll()).willReturn(vendors);

        //when
        VendorListDTO vendorListDTO = new VendorListDTO(vendorService.getAllVendors());

        //then
        then(vendorRepository).should(times(1)).findAll();
        assertThat(vendorListDTO.getVendors().size(), is(equalTo(2)));
    }

    @Test
    public void createNewVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor vendor = getVendor1();

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when
        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);

        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedVendorDTO.getUrl(), containsString("1"));

    }

    @Test
    public void saveVendorByDTO() {

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor vendor = getVendor1();

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when
        VendorDTO savedVendorDTO = vendorService.saveVendorByDTO(ID_1, vendorDTO);

        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedVendorDTO.getUrl(), containsString("1"));
    }

    @Test
    public void patchVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);

        Vendor vendor = getVendor1();

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when

        VendorDTO savedVendorDTO = vendorService.patchVendor(ID_1, vendorDTO);

        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(savedVendorDTO.getUrl(), containsString("1"));
    }

    @Test
    public void deleteVendorById() {

        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("john");

        when(vendorRepository.findById(any())).thenReturn(Optional.of(vendor));


        //when
        vendorService.deleteVendorById(1L);

        //then
        then(vendorRepository).should().deleteById(anyLong());
    }

    private Vendor getVendor1() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_1);
        vendor.setId(ID_1);
        return vendor;
    }

    private Vendor getVendor2() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME_2);
        vendor.setId(ID_2);
        return vendor;
    }
}