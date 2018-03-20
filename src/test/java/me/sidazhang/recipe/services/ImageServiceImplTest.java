package me.sidazhang.recipe.services;

import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    private RecipeReactiveRepository recipeReactiveRepository;

    private ImageService imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeReactiveRepository);
    }

    @Test
    public void saveImageFile() throws Exception {
        //given
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId("1");
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.justOrEmpty(recipeOptional));
        when(recipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.justOrEmpty(recipeOptional));
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile("1", multipartFile).block();

        //then
        verify(recipeReactiveRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }

    private byte[] extractBytes(String ImageName) throws IOException, NullPointerException {
        // open image
        ClassLoader classLoader = getClass().getClassLoader();
        File imgPath = new File(classLoader.getResource(ImageName).getFile());
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }



    @Test
    public void testRenderImage() throws Exception {
        Recipe recipe = new Recipe();
        byte[] bytes = extractBytes("static/images/sushi.jpg");
        Byte[] bytes1 = new Byte[bytes.length];
        Arrays.setAll(bytes1, n -> bytes[n]);
        recipe.setImage(bytes1);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.justOrEmpty(optionalRecipe));


        assertArrayEquals(bytes, imageService.renderImage("1").getBody());

    }

}