package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        loadData();
    }

    private static void loadData(){
        final String BASE_PATH = "C:\\Users\\Peter Boncheff\\Desktop\\";
        final String CLEAN_RECIPES = BASE_PATH + "clean_recipes.csv";
        final String CLEAN_REVIEWS = BASE_PATH + "clean_reviews.csv";
        final String RECIPES = BASE_PATH + "recipes.csv";
        final String REVIEWS = BASE_PATH + "reviews.csv";
        String[] filenames = {CLEAN_RECIPES,CLEAN_REVIEWS ,RECIPES, REVIEWS};
        File file;
        for (String filename : filenames) {
            file = new File(filename);
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String data = scanner.next();
                    String[] values = data.split(",");
                    System.out.println(data);
                }
                scanner.close();
                System.out.println("\n\n\n\n\n\n");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
