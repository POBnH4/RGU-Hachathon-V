package com.company;

import java.io.File;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

    	Runnable runGUI = new Runnable()
        {
            @Override
            public void run()
            {
                createAndShowGUI();
            }
        };
        java.awt.EventQueue.invokeLater(runGUI);
        //loadData();
    }

    private static void createAndShowGUI()
    {
        AppGUI f = new AppGUI();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.initGUI(); // initialise AdminGUI
        f.setVisible(true); // make frame visible
    }
    
    private static void loadData(){
        final String BASE_PATH = "C:\\Users\\Philip Tsvetanov\\Desktop\\recipe-ingredients-and-reviews\\";
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
