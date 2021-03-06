package com.recipes.controller;

import com.recipes.model.Interaction;
import com.recipes.model.Recipe;
import com.recipes.service.InteractionService;
import com.recipes.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Slf4j
@CrossOrigin(origins = "http://www.appspot.com", maxAge = 3600)
@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    @Autowired
    private InteractionService interactionService ;

    @RequestMapping(value = "/recipe/{recipeId}",method = RequestMethod.GET)
    public ResponseEntity<List<Interaction>> findByRecipeId(@PathVariable Long recipeId) {
        List<Interaction> interactions=interactionService.findByRecipeId(recipeId);
        if(interactions!=null){
            interactions.stream().forEach(this::addSelfLink);
            return ResponseEntity.ok(interactions);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @RequestMapping(value = "/user/{userId}",method = RequestMethod.GET)
    public ResponseEntity<List<Interaction>> findByUserId(@PathVariable Long userId) {
        List<Interaction> interactions=interactionService.findByUserId(userId);
        if(interactions!=null){
            interactions.stream().forEach(this::addSelfLink);
            return ResponseEntity.ok(interactions);
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @RequestMapping(value = "/search/{searchType}",method = RequestMethod.GET)
    public ResponseEntity<List<Interaction>> search(@PathVariable String searchType, @RequestParam String searchQuery) {
        List<Interaction> interactions=interactionService.search(searchType,searchQuery);
        if(interactions!=null){
            interactions.stream().forEach(this::addSelfLink);
            return ResponseEntity.ok(interactions);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    private void addSelfLink(Interaction interaction){
        Link selfLink = linkTo(InteractionController.class).slash("/recipe/"+interaction.getRecipeId()).withSelfRel();
        interaction.add(selfLink);
    }
}
