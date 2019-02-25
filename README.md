# RGU-Hachathon-V
A.I. Recipe Bot - given user input create new recipe with particular steps to prepare it;
Dataset used -> https://www.kaggle.com/kanaryayi/recipe-ingredients-and-reviews 
since the 24 hour time limit, we used only file:"clean_recipes.csv". 
Once downloaded, change the whole path of the file in the Main.java class line 11;

The algorithm works the following way:
1. Take user input;
2. Check for occurrences of the given input(ingredients separated by comma, for example: tomato, chicken, cheese) within the whole dataset;
3. Make an optimal decision my taking into account the dataset we are using(e.g. prioritization of occurrences, time needed to prepare, total number of ingredients, etc); If the user's input is "tomato, cucumber", after checking the occurrences in the whole dataset for these ingredients the algorithm will prioritize,depending on how many of them are in a single recipe, the one that will be the closest to the given input. For instance, with the same input("tomato,cucumber"), a recipe that consists of ("tomato,cucumber, feta cheese") will have higher priority than a recipe that consists of ("sirloin steak","salt","red onion", "tomato").

There many other ways for the algorithm to be optimazed so it can become more accurate and come up with more common recipes such as adding additional data sets which will teach the algorithm what is a vegetable and what is meat(e.g. two datasets correspondingly each containing a huge amount of vegetables and meats) and using the reviews.csv file we can calculate which recipe with which particular combination of food is the best.

Most difficult part is making the algorithm create cooking directions for a recipe that are not odd and make complete sense, which in our case works sometimes;

4. Print data from the optimal decision;
5. Save the new recipe in the database;
6. Wait for user input(full cycle, now we are in the beginning again);
