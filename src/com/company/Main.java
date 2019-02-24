package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.JFrame;

public class Main {

    private static final String BASE_PATH = "C:\\Users\\Philip Tsvetanov\\Desktop\\recipe-ingredients-and-reviews\\";
    //private static final String BASE_PATH = "C:\\Users\\Peter Boncheff\\Desktop\\";
    private static HashMap<String, HashMap<String, String[]>> cleanRecipes = new HashMap<>();
    // hashmap cleanRecipes - (String) recipe name, hashmap( (String)directions, String[ingredients]);

    public static void main(String[] args) {
        Runnable runGUI = () -> createAndShowGUI();
        java.awt.EventQueue.invokeLater(runGUI);
    }

    private static void createAndShowGUI() {
        AppGUI f = new AppGUI();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.initGUI(); // initialise AdminGUI
        f.setVisible(true); // make frame visible
    }

    // red cabbage, carrot, tomato, red onion
    static String[] callBackEnd(String inputString) {
        //Heap maxHeap = new Heap(4);
        //maxHeap.print();
        loadDataCleanRecipes();
        //printData();
        String[] temp = inputString.split(",");
        List<String> userInput = new ArrayList<>(Arrays.asList(temp));
        String[] uInput = new String[0];
        try {
            uInput = (String[]) userInput.toArray();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        String[] optimized = optimize(checkForOccurrences(userInput), userInput);
        //printTwo(checkForOccurrences(list));
        HashMap<String, String[]> newRecipe = new HashMap<>();
        String recipe = printNewRecipe(optimized);

        newRecipe.put(recipe, uInput);
        saveToDatabase("", newRecipe); //TODO ask user for name;

        final String[] finalResult = new String[2]; //0 -> for recipe directions, 1 -> for recipe ingredients;
        finalResult[0] = recipe;
        finalResult[1] = inputString.replaceAll("\\s+","");
        return finalResult;
    }

    private static String getToString(String[] ingredients) {
        String output = "";
        for (String item : ingredients) {
            output += item + "\n";
        }

        return output;
    }

    private static String printNewRecipe(String[] newRecipe) {
        StringBuilder newRecipeSteps = new StringBuilder();
        for (int i = 0; i < newRecipe.length; i++) {
            if (!newRecipe[i].isEmpty()) {
                newRecipe[i] = newRecipe[i].replace('*', ' ').trim();
                String stepI = (i + 1) + ". " + newRecipe[i] + ".\n";
                System.out.println(stepI);
                newRecipeSteps.append(stepI).append("\n");
            }
        }
        return newRecipeSteps.toString();
    }

    private static String[] optimize(HashMap<String, Integer> map, List<String> userInput) {
        //Heap heap = new Heap(map.size());
        final int DOUBLE = 2;
        String[] newRecipe = new String[userInput.size() * DOUBLE];
        for (int i = 0; i < newRecipe.length; i++) {
            String step = getMax(map);
            newRecipe[i] = step;
        }
        return newRecipe;
    }

    private static String getMax(HashMap<String, Integer> map) {
        String recipeMax = ""; // recipe name with highest priority;
        int max = Integer.MIN_VALUE;
        //TODO add additional data and check;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                recipeMax = entry.getKey();
            }
        }
        map.remove(recipeMax); // remove the one with the biggest
        // priority so we can use the one after it;
        return recipeMax;
    }

    private static void loadDataCleanRecipes() {

        final String CLEAN_RECIPES = BASE_PATH + "clean_recipes.csv";
        //Recipe Name;Review Count;Recipe Photo;Author;Prepare Time;Cook Time;Total Time;Ingredients;Directions;RecipeID
        File file = new File(CLEAN_RECIPES);
        final int GET_RECIPE_NAME = 1, GET_INGREDIENTS = 8, GET_DIRECTIONS = 9;
        // 1 = first column in dataset, 8 = eighth column in dataset, 9 = ninth column in dataset;e
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine(), output = "";
                String recipeName = "", ingredient = "", directions = "";
                int semicolonCounter = 0; //used to determine the columns of the dataset;
                for (int i = 0; i < data.length(); i++) {
                    output += data.charAt(i);
                    if (data.charAt(i) == ';') {
                        semicolonCounter++;
                        if (semicolonCounter == GET_RECIPE_NAME) recipeName = output;
                        if (semicolonCounter == GET_INGREDIENTS) ingredient = output;
                        if (semicolonCounter == GET_DIRECTIONS) directions = output;
                        output = "";
                    }
                }
                final String[] INGREDIENTS = ingredient.split(",");
                final HashMap<String, String[]> DIRECTION_AND_INGREDIENTS = new HashMap<>();
                DIRECTION_AND_INGREDIENTS.put(directions, INGREDIENTS);
                cleanRecipes.put(recipeName, DIRECTION_AND_INGREDIENTS);
            }
            scanner.close();
            System.out.println("\n\n\n\n\n\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static HashMap<String, Integer> checkForOccurrences(List<String> userInput) {
        HashMap<String, Integer> occurrences = new HashMap<>(); //int is for number of

        // ingredients that are the same as the ones the user has;
        for (Map.Entry<String, HashMap<String, String[]>> entry : cleanRecipes.entrySet()) {
            for (Map.Entry<String, String[]> inner : entry.getValue().entrySet()) {
                boolean containsIngredients = false;
                int occurrenceOfIngredientsCounter = 0;
                for (int i = 0; i < inner.getValue().length; i++) {
                    if (userInput.contains(inner.getValue()[i])) {
                        containsIngredients = true;
                        occurrenceOfIngredientsCounter++;
                    }
                }
                if (containsIngredients) {
                    String[] steps = inner.getKey().split("\\.");
                    ArrayList<String> list = new ArrayList<>(Arrays.asList(steps));
                    for (String sentence : list) {
                        String[] wordsSpliter = sentence.split(" ");
                        ArrayList<String> words = new ArrayList<>(Arrays.asList(wordsSpliter));
                        if (Collections.disjoint(words, userInput)) {
                            occurrences.put(sentence, occurrenceOfIngredientsCounter);
                            // for additional optimization - make occurrences.put(Arraylist
                            // of sentences for just one recipe in case the next sentence
                            // of the directions(steps) contains more instructions, depending
                            // on priority;
                        }
                    }
                }
                if (containsIngredients && occurrences.isEmpty()) {
                    final String COULD_NOT_FIND = "Mix them together in a form of salad";
                    occurrences.put(COULD_NOT_FIND, occurrenceOfIngredientsCounter);
                }
            }
        }
        return occurrences;
    }


    private static void saveToDatabase(String recipeName, HashMap<String, String[]> mapWithDirectionAndIngredients) {
        cleanRecipes.put(recipeName, mapWithDirectionAndIngredients);
        System.out.println("added to data base" + recipeName +
                "\n " + Arrays.deepToString(new HashMap[]{mapWithDirectionAndIngredients}));
    }

    private static void printData() {
        for (Map.Entry<String, HashMap<String, String[]>> entry : cleanRecipes.entrySet()) {
            System.out.println("Recipe name: " + entry.getKey());
            for (Map.Entry<String, String[]> inner : entry.getValue().entrySet()) {
                System.out.println("Recipe directions: " + inner.getKey());
                System.out.println("Recipe ingredients: ");
                for (int i = 0; i < inner.getValue().length; i++) {
                    System.out.println(inner.getValue()[i]);
                }
            }
            System.out.println("\n-   -   -   -   -   -   -   -\n");
        }
    }


    private static void printTwo(HashMap<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    private static void additionalOptimization() {
        final String CLEAN_REVIEWS = BASE_PATH + "clean_reviews.csv";
        final String RECIPES = BASE_PATH + "recipes.csv", REVIEWS = BASE_PATH + "reviews.csv";
    }
}
