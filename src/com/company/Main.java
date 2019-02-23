package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static final String BASE_PATH = "C:\\Users\\Peter Boncheff\\Desktop\\";
    private static HashMap<String, HashMap<String, String[]>> cleanRecipes = new HashMap<>();
    // hashmap - recipe name, String(ingredients, directions);

    public static void main(String[] args) {

        loadDataCleanRecipes();
        printData();
    }

    private static void loadDataCleanRecipes() {

        final String CLEAN_RECIPES = BASE_PATH + "clean_recipes.csv";
        //Recipe Name;Review Count;Recipe Photo;Author;Prepare Time;Cook Time;Total Time;Ingredients;Directions;RecipeID
        File file = new File(CLEAN_RECIPES);
        final int GET_RECIPE_NAME = 1, GET_INGREDIENTS = 8, GET_DIRECTIONS = 9;
        // 1 = first column in dataset, 8 = eighth column in dataset, 9 = ninth column in dataset;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine(), output = "";
                String recipeName = "", ingredient = "", directions = "";
                int semicolonCounter = 0; //used to determine the columns of the dataset;
                for (int i = 0; i < data.length(); i++){
                   output += data.charAt(i);
                   if(data.charAt(i) == ';'){
                       semicolonCounter++;
                       if(semicolonCounter == GET_RECIPE_NAME) recipeName = output;
                       if(semicolonCounter == GET_INGREDIENTS) ingredient = output;
                       if(semicolonCounter == GET_DIRECTIONS) directions = output;
                       output = "";
                   }
                }
                final String[] INGREDIENTS = ingredient.split(",");
                final HashMap<String, String[]> DIRECTION_AND_INGREDIENTS = new HashMap<>();
                DIRECTION_AND_INGREDIENTS.put(directions,INGREDIENTS);
                cleanRecipes.put(recipeName,DIRECTION_AND_INGREDIENTS);
            }
            scanner.close();
            System.out.println("\n\n\n\n\n\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void printData(){
        for(Map.Entry<String,HashMap<String,String[]>> entry : cleanRecipes.entrySet()){
            System.out.println("Recipe name: " + entry.getKey());
            for(Map.Entry<String,String[]> inner : entry.getValue().entrySet()){
                System.out.println("Recipe directions: " + inner.getKey());
                System.out.println("Recipe ingredients: ");
                for(int i = 0; i < inner.getValue().length; i++){
                    System.out.println(inner.getValue()[i]);
                }
            }
            System.out.println("\n-   -   -   -   -   -   -   -\n");
        }
    }

    private static void asd() {
        final String CLEAN_REVIEWS = BASE_PATH + "clean_reviews.csv";
        final String RECIPES = BASE_PATH + "recipes.csv", REVIEWS = BASE_PATH + "reviews.csv";
    }
}
