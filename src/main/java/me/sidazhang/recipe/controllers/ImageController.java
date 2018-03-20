package me.sidazhang.recipe.controllers;

import me.sidazhang.recipe.services.ImageService;
import me.sidazhang.recipe.services.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @RequestMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/imageuploadform";
    }

    @RequestMapping(value = "recipe/{id}/image", method = RequestMethod.POST)
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) throws Exception {
        imageService.saveImageFile(id, file).block();
        return "redirect:/recipe/" + id + "/show";
    }

    @RequestMapping(value = "recipe/{id}/showimage", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> renderImage(@PathVariable String id) throws Exception {
        return imageService.renderImage(id);
    }


}
