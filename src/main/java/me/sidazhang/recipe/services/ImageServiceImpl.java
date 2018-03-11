package me.sidazhang.recipe.services;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.repositories.RecipeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) throws Exception {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            try {
                byte[] img = file.getBytes();
                Byte[] ByteObj = new Byte[img.length];
                Arrays.setAll(ByteObj, n -> img[n]);
                recipe.setImage(ByteObj);
                recipeRepository.save(recipe);
            } catch (IOException e) {
                log.warn("Failed to convert Image to byte array");
            }

        } else {
            throw new Exception("Cannot find recipe by this id");
        }

    }

    @Override
    @ResponseBody
    public ResponseEntity<byte[]> renderImage(Long recipeId) throws Exception {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            Byte[] bytes = recipe.getImage();
            if (bytes == null) {
                return ResponseEntity.notFound().build();
            }
            byte[] bytes1 = new byte[bytes.length];
            int i = 0;
            for (Byte b : recipe.getImage())
                bytes1[i++] = b;
            return ResponseEntity.ok().contentLength(bytes.length).body(bytes1);
        } else {
            throw new Exception("Recipe not found");
        }
    }
}
