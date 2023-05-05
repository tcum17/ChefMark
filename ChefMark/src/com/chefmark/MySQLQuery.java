package chefmark;

import java.sql.Connection;
import java.sql.Statement;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLQuery extends DBQuery{

    private DBConnection dbConnection;
    private Connection connection;
    private Statement statement;

    public MySQLQuery(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String url = dbConnection.getURL();
        String user = dbConnection.getUsername();
        String password = dbConnection.getPassword();

        connection = DriverManager.getConnection(url, user, password); // check if db is online

        statement = connection.createStatement();
    }

    public void create(User user) throws SQLException {
        //String query = "INSERT INTO USER VALUES('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getEmail() + "'";
        statement.execute("INSERT INTO USER VALUES('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getEmail() + "', '', '')");
    }

    public ResultSet read(User user) throws SQLException {
        String query = "SELECT * FROM USER WHERE USERNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        return statement.executeQuery();
        // String username = user.getUsername();
        // return executeQuery("SELECT * FROM USER WHERE USERNAME = '" + username + "'");
    }

    public void update(User user) throws SQLException {
        String query = "UPDATE USER SET userPassword=?, EMAIL=? WHERE USERNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getPassword());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getUsername());
        statement.execute();
    }

    public void updateServingSize(Recipe recipe, User user) throws SQLException {
        String query = "UPDATE RECIPE SET INGREDIENTS=?,TOTALWEIGHT=? WHERE RECIPENAME=? and USERNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, RecipeController.ingredientListToText(recipe.getIngredients()));
        statement.setDouble(2, recipe.getTotalWeight());
        statement.setString(3, recipe.getName());
        statement.setString(4, user.getUsername());
        statement.execute();
    }

    public void updateIsCustomRecipe(Recipe recipe, User user) throws SQLException {
        String query = "UPDATE RECIPE SET isCustom=? WHERE RECIPENAME=? and USERNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, recipe.getIsCustom());
        statement.setString(2, recipe.getName());
        statement.setString(3, user.getUsername());
        statement.execute();
    }

    public void deleteFromWeeklyPlan(WeeklyPlan weeklyPlan, Recipe recipe, User user, String day) throws SQLException {
        String query = "DELETE WEEKLYPLANITEM FROM WEEKLYPLANITEM JOIN RECIPE ON WEEKLYPLANITEM.RECIPEID = RECIPE.RECIPEID WHERE WEEKLYPLANITEM.DAYOFWEEK = ? AND RECIPE.RECIPENAME = ? AND RECIPE.USERNAME = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, day);
        statement.setString(2, recipe.getName());
        statement.setString(3, user.getUsername());
        statement.execute();
    }

    public ResultSet getRecipeListId(RecipeList recipeList, User user) throws SQLException {
        String query="SELECT RECIPELISTID FROM RECIPELIST,USER WHERE RECIPELIST.USERNAME=USER.USERNAME AND RECIPELIST.USERNAME=? AND RECIPELISTNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, recipeList.getName());
        return statement.executeQuery();
    }

    public ResultSet getWeeklyPlanId(WeeklyPlan weeklyPlan, User user) throws SQLException {
        String query = "SELECT WEEKLYPLANID FROM WEEKLYPLAN WHERE WEEKLYPLAN.USERNAME=? AND NAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, weeklyPlan.getName());
        return statement.executeQuery();
    }

    public void update (WeeklyPlan weeklyPlan, User user, int weeklyPlanId) throws SQLException {
        String query = "UPDATE WEEKLYPLAN,USER SET NAME=? WHERE WEEKLYPLAN.USERNAME=USER.USERNAME AND USER.USERNAME=? AND WEEKLYPLANID=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, weeklyPlan.getName());
        statement.setString(2, user.getUsername());
        statement.setInt(3, weeklyPlanId);
        statement.execute();
    }

    public void update(RecipeList recipeList, User user, int recipeListId) throws SQLException {
        String query = "UPDATE RECIPELIST,USER SET RECIPELISTNAME=? WHERE RECIPELIST.USERNAME=USER.USERNAME AND USER.USERNAME=? AND RECIPELISTID=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, recipeList.getName());
        statement.setString(2, user.getUsername());
        statement.setInt(3, recipeListId);
        statement.execute();
    }

    public void delete(User user) throws SQLException {
        delete(user.getPantry(), user);
        //for (int i = 0; i < user.getWeeklyPlans().size(); i++) {
        delete(new WeeklyPlan(), user);
        //}

        delete(new RecipeList(), user);
        deleteAllRecipe(new Recipe(), user);

        String query = "DELETE FROM USER WHERE USERNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.execute();
    }

    public void delete(RecipeList recipeList, User user) throws SQLException {
        String query = "DELETE FROM recipelistitem WHERE recipelistid IN (SELECT recipelistid FROM recipelist WHERE username=?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.execute();
        query = "DELETE FROM recipelist WHERE username=?";
        statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.execute();
    }

    public void deleteRecipeList(RecipeList recipeList, User user) throws SQLException {
        String query = "DELETE FROM recipelistitem WHERE recipelistid IN (SELECT recipelistid FROM recipelist WHERE username=?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.execute();
        query = "DELETE FROM recipelist WHERE username=? and recipelistname=?";
        statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, recipeList.getName());
        statement.execute();
    }

    public void deleteRecipeListItem(RecipeList recipeList, Recipe recipe, User user) throws SQLException {
        String query = "DELETE FROM recipelistitem WHERE recipeid IN (SELECT recipeid FROM recipe WHERE recipename = ? AND username = ?) AND recipelistid IN (SELECT recipelistid FROM recipelist WHERE recipelistname = ? AND username = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, recipe.getName());
        statement.setString(2, user.getUsername());
        statement.setString(3, recipeList.getName());
        statement.setString(4, user.getUsername());
        statement.execute();
    }

    public void deleteAllRecipe(Recipe recipe, User user) throws SQLException {
        String query = "DELETE FROM RECIPE WHERE USERNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.execute();
    }

    public void delete(WeeklyPlan weeklyPlan, User user) throws SQLException {
        String query = "DELETE FROM weeklyplanitem WHERE weeklyplanid = (SELECT weeklyplanid FROM weeklyplan WHERE username = ? AND name = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, weeklyPlan.getName());
        statement.execute();
        query = "DELETE FROM WEEKLYPLAN WHERE WEEKLYPLAN.USERNAME=?";
        statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.execute();
    }


    public void deleteWeeklyPlan(WeeklyPlan weeklyPlan, User user) throws SQLException {
        String query = "select weeklyplan.weeklyplanid from weeklyplanitem,weeklyplan where weeklyplan.weeklyplanid=weeklyplanitem.weeklyplanid and username=? and name=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, weeklyPlan.getName());
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
        query = "DELETE FROM WEEKLYPLANITEM WHERE WEEKLYPLANID =?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, rs.getInt(1));
        statement.execute();
        query = "DELETE FROM WEEKLYPLAN WHERE USERNAME=? AND NAME=?";
        statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, weeklyPlan.getName());
        statement.execute();
        }
    }

    public void create(Pantry pantry, User user) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM PANTRY WHERE PANTRY.USERNAME = '" + user.getUsername() + "'");
        if (!rs.next())
            statement.execute("INSERT INTO PANTRY (username) VALUES('" + user.getUsername() + "')");
        else {
            System.out.println("You already have a pantry");
        }
    }

    public ResultSet read(Pantry pantry, User user) throws SQLException {
        String username = user.getUsername();
        return executeQuery("select ingredientName, quantity, measure from ingredient,pantry,user where user.username=pantry.username and pantry.pantryID=ingredient.pantryID and user.username = '" + username + "'");
        //return executeQuery("SELECT PANTRYID, USER.USERNAME FROM PANTRY,USER WHERE PANTRY.USERNAME = USER.USERNAME AND USER.USERNAME='" + username + "'"); // not sure if this is ever needed
    }

    public ResultSet read(Ingredient ingredient, User user) throws SQLException {
        String username = user.getUsername();
        return executeQuery("SELECT INGREDIENTNAME, INGREDIENTID FROM INGREDIENT,USER,PANTRY WHERE USER.USERNAME = PANTRY.USERNAME AND PANTRY.PANTRYID = INGREDIENT.PANTRYID AND USER.USERNAME = '" + username + "' AND INGREDIENTNAME = '" + ingredient.getIngredientName() + "'");
    }

    public void update(Ingredient ingredient, User user) throws SQLException {
        String username = user.getUsername();
        statement.execute("UPDATE INGREDIENT,USER,PANTRY SET INGREDIENTNAME = '" + ingredient.getIngredientName() + "', QUANTITY = " + ingredient.getQuantity() + ", MEASURE = '" + ingredient.getMeasure() + "' WHERE USER.USERNAME=PANTRY.USERNAME AND INGREDIENT.PANTRYID=PANTRY.PANTRYID AND USER.USERNAME='" + username + "' AND INGREDIENTID=" + Integer.parseInt(ingredient.getIngredientID()));
    }

    public void delete(Ingredient ingredient, User user) throws SQLException {
        String username = user.getUsername();
        statement.execute("DELETE i FROM INGREDIENT i INNER JOIN PANTRY p ON i.PANTRYID = p.PANTRYID WHERE i.INGREDIENTNAME = '"+ ingredient.getIngredientName() + "' AND p.USERNAME = '" + username + "';");
    }

    // public void update(Pantry pantry, User user) throws SQLException{
    //     statement.execute("INSERT INTO PANTRY "); // maybe not needed
    // }

    public void create(Pantry pantry, Ingredient ingredient, User user) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT PANTRYID FROM PANTRY,USER WHERE PANTRY.USERNAME=USER.USERNAME AND USER.USERNAME = '" + user.getUsername() + "'");
        if (rs.next()) {
            statement.execute("INSERT INTO INGREDIENT (ingredientName, quantity, measure, pantryID) values('" + ingredient.getIngredientName() + "', " + ingredient.getQuantity() + ", '" + ingredient.getMeasure() + "', " + rs.getString(1) + ")");
        }
        else {
            System.out.println("Pantry could not be updated. Do you have one?");
        }
    }

    public ResultSet populateWeeklyPlan(WeeklyPlan weeklyPlan, User user) throws SQLException {
        String query = "SELECT * FROM WEEKLYPLAN WHERE username=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();
        //ResultSet rs = statement.executeQuery("SELECT * FROM WEEKLYPLAN,USER WHERE user.username=weeklyplan.username and user.USERNAME='" + user.getUsername() + "'");
        // if (rs.next()) {

        // }
        return rs;
    }

    public ResultSet populateCustomRecipe(Recipe recipe, User user) throws SQLException {
        String query = "select distinct recipeName, url, source, ingredients, dietLabels, healthLabels, calories, instructions, cautions, recipe.username from recipe where recipe.username=? and recipe.isCustom = 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();
        return rs;
    }

    public ResultSet read(WeeklyPlan weeklyPlan, User user) throws SQLException {
        return statement.executeQuery("SELECT * FROM WEEKLYPLAN WHERE USERNAME='" + user.getUsername() + "' AND NAME='" + weeklyPlan.getName() + "'");
    }

    public ResultSet read(WeeklyPlan weeklyPlan, User user, String day) throws SQLException {
        return statement.executeQuery("select distinct recipeName, url, source, ingredients, dietLabels, healthLabels, calories, instructions, cautions, recipe.username from recipe,weeklyplan,weeklyplanitem where recipe.username=weeklyplan.username and weeklyplan.weeklyPlanid=weeklyplanitem.weeklyplanid and recipe.username='" + user.getUsername() + "' and dayOfWeek='" + day + "' and recipe.recipeID=weeklyplanitem.recipeid and weeklyPlan.name='" + weeklyPlan.getName() + "'");
    }

    public void create(WeeklyPlan weeklyPlan, User user) throws SQLException {
        //check if it exists
        ResultSet rs = statement.executeQuery("SELECT WEEKLYPLANID FROM WEEKLYPLAN WHERE NAME='" + weeklyPlan.getName() + "' AND USERNAME = '" + user.getUsername() + "'");
        //String recipeIds = RecipeController.
        if (!rs.next()) {
            statement.execute("INSERT INTO WEEKLYPLAN (NAME, USERNAME) VALUES('" + weeklyPlan.getName() + "', '" + user.getUsername() + "')");
            //ResultSet rs2 = statement.executeQuery("SELECT WEEKLYPLANID FROM WEEKLYPLAN WHERE WEEKLYPLANNAME='" + weeklyPlan.getName() + "' AND USERNAME='" + user.getUsername() + "'");
            //statement.execute("INSERT INTO WEEKLYPLANITEM ()")
        }
        else {
            System.out.println("Weekly plan could not be created. Do you have one of the same name?");
        }
    }

    public void update(WeeklyPlan weeklyPlan, Recipe recipe, User user) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT WEEKLYPLANID FROM WEEKLYPLAN,USER WHERE WEEKLYPLANNAME='" + weeklyPlan.getName() + "' AND USERNAME = '" + user.getUsername() + "'");
        if (!rs.next()) {
            ResultSet rs2 = read(recipe, user);
            if (rs2.next()) {
                ResultSet rs3 = read(weeklyPlan, user);
                if (rs3.next()) {
                    //statement.execute("UPDATE WEEKLYPLANITEM ")
                }
            }
            //weeklyPlan.get -- ask group what we really want
            //statement.execute("UPDATE WEEKLYPLAN");
        }
    }

    public void create(WeeklyPlan weeklyPlan, Recipe recipe, User user, String day) throws SQLException {
        //ResultSet rs = statement.executeQuery("SELECT WEEKLYPLANID FROM WEEKLYPLAN,USER WHERE NAME='" + weeklyPlan.getName() + "' AND USER.USERNAME = '" + user.getUsername() + "'");
        //if (!rs.next()) {
            ResultSet rs2 = read(recipe, user);
            int recipeId = -1;
            if (rs2.next()) {
                recipeId=Integer.parseInt(rs2.getString(1));
            }
            ResultSet rs3 = read(weeklyPlan, user);
            if (rs3.next()) {
                statement.execute("INSERT INTO WEEKLYPLANITEM (dayOfWeek, recipeId, weeklyPlanId) values('" + day + "', " + recipeId + ", " + Integer.parseInt(rs3.getString(1)) + ")");
            }
            //weeklyPlan.get -- ask group what we really want
            //statement.execute("UPDATE WEEKLYPLAN");
        //}
    }

    public void create(Recipe recipe, User user) throws SQLException {
        String ingredients = RecipeController.ingredientListToText(recipe.getIngredients());
        String dietLabels = RecipeController.arrayListToText(recipe.getNutritionalFacts().getDietLables());
        String healthLabels = RecipeController.arrayListToText(recipe.getNutritionalFacts().getHealthLables());
        double calories = recipe.getNutritionalFacts().getCalories();
        double glycemicIndex = recipe.getNutritionalFacts().getGlycemicIndex();
        double recipeYield = recipe.getNutritionalFacts().getYeild();
        String instructions = RecipeController.arrayListToText(recipe.getInstructions().getInstructions());
        //instructions = instructions.replaceAll("|", "");
        String cautions = RecipeController.arrayListToText(recipe.getInstructions().getCautions());
        ResultSet rs = statement.executeQuery("SELECT RECIPEID FROM RECIPE WHERE RECIPENAME='" + recipe.getName() + "'");
        if (!rs.next()) {
            String query = "INSERT INTO RECIPE (recipeName, url, source, ingredients, totalWeight, dietLabels, healthLabels, calories, glycemicIndex, yield, instructions, cautions, username, isCustom) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getURL());
            statement.setString(3, recipe.getSource());
            statement.setString(4, ingredients);
            statement.setDouble(5, recipe.getTotalWeight());
            statement.setString(6, dietLabels);
            statement.setString(7, healthLabels);
            statement.setDouble(8, calories);
            statement.setDouble(9, glycemicIndex);
            statement.setDouble(10, recipeYield);
            statement.setString(11, instructions);
            statement.setString(12, cautions);
            statement.setString(13, user.getUsername());
            statement.setInt(14, 0);
            statement.execute();
            //statement.execute("INSERT INTO RECIPE (recipeName, url, source, ingredients, totalWeight, dietLabels, healthLabels, calories, glycemicIndex, yield, instructions, cautions, username)" +
            //"values('" + recipe.getName() + "', '" + recipe.getURL() + "', '" + recipe.getSource() + "', '" + ingredients + "', " + recipe.getTotalWeight() + ", '" + dietLabels + "', '" + healthLabels + "', " + calories + ", " + glycemicIndex + ", " + recipeYield + ", '" + instructions + "', '" + cautions + "', '" + user.getUsername() + "')");
        }
    }

    public void update(Recipe recipe, User user) throws SQLException {
        String ingredients = RecipeController.ingredientListToText(recipe.getIngredients());
        String dietLabels = RecipeController.arrayListToText(recipe.getNutritionalFacts().getDietLables());
        String healthLabels = RecipeController.arrayListToText(recipe.getNutritionalFacts().getHealthLables());
        double calories = recipe.getNutritionalFacts().getCalories();
        double glycemicIndex = recipe.getNutritionalFacts().getGlycemicIndex();
        double recipeYield = recipe.getNutritionalFacts().getYeild();
        String instructions = RecipeController.arrayListToText(recipe.getInstructions().getInstructions());
        String cautions = RecipeController.arrayListToText(recipe.getInstructions().getCautions());
        String dishType = RecipeController.arrayListToText(recipe.getRecipeDescriptors().getDishType());
        String mealType = RecipeController.arrayListToText(recipe.getRecipeDescriptors().getMealType());
        String query="UPDATE RECIPE SET recipeName = ?, url = ?, source = ?, ingredients = ? totalWeight = ?, dietLabels = ?, healthLabels = ?, calories = ?, glycemicIndex = ?, yield = ?, instructions = ?, cautions = ?, dishType = ?, mealType = ?, isCustom = ? where username=? and recipeName=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, recipe.getName());
        statement.setString(2, recipe.getURL());
        statement.setString(3, recipe.getSource());
        statement.setString(4, ingredients);
        statement.setDouble(5, recipe.getTotalWeight());
        statement.setString(6, dietLabels);
        statement.setString(7, healthLabels);
        statement.setDouble(8, calories);
        statement.setDouble(9, glycemicIndex);
        statement.setDouble(10, recipeYield);
        statement.setString(11, instructions);
        statement.setString(12, cautions);
        statement.setString(13, dishType);
        statement.setString(14, mealType);
        statement.setInt(15, recipe.getIsCustom());
        statement.setString(16, recipe.getName());
        statement.setString(17, user.getUsername());
        statement.execute();
        // statement.execute("UPDATE RECIPE SET recipeName = '" + recipe.getName() + "', url = '" + recipe.getURL() + "', source = '" + recipe.getSource() + "', ingredients = '" + ingredients + 
        // "', totalWeight = " + recipe.getTotalWeight() + ", dietLabels = '" + dietLabels + "', healthLabels = '" + healthLabels + "', calories = " + calories + 
        // ", glycemicIndex = " + glycemicIndex + ", yield = " + recipeYield + ", instructions = '" + instructions + "', cautions = '" + cautions + "', dishType = '" + dishType + 
        // "', mealType = '" + mealType + "', isCustom = " + recipe.getIsCustom() + " where username='" + user.getUsername() + "' and recipeName='" + recipe.getName() + "'");
    }

    public void createCustomRecipe(Recipe recipe, User user) throws SQLException {
        String query1 = "SELECT RECIPEID FROM RECIPE WHERE RECIPENAME=? AND USERNAME=?";
        PreparedStatement statement1 = connection.prepareStatement(query1);
        statement1.setString(1, recipe.getName());
        statement1.setString(2, user.getUsername());
        ResultSet rs = statement1.executeQuery();
        if (!rs.next()) { // The recipe does not exist
            String query = "INSERT INTO RECIPE (recipeName, ingredients, instructions, username, rating, isCustom) values(?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, recipe.getName());
            statement.setString(2, RecipeController.ingredientListToText(recipe.getIngredients()));
            statement.setString(3, RecipeController.arrayListToText(recipe.getInstructions().getInstructions()));
            statement.setString(4, user.getUsername());
            statement.setInt(5, recipe.getRating().getRating());
            statement.setInt(6, 1);
            statement.execute();
        } else {
            System.out.println("It looks like this recipe already exists.");
        }
    }

    public ResultSet read(Recipe recipe, User user) throws SQLException {
        String query = "SELECT recipeId, recipeName, url, source, ingredients, totalWeight, dietLabels, healthLabels, calories, glycemicIndex, yield, instructions, cautions, username from RECIPE where username=? and recipeName=?";
        //return statement.executeQuery("SELECT recipeId, recipeName, url, source, ingredients, totalWeight, dietLabels, healthLabels, calories, glycemicIndex, yield, instructions, cautions, username from RECIPE where username='" + user.getUsername() + "' and recipeName='" + recipe.getName() + "'");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, recipe.getName());
        return statement.executeQuery();
    
    
    }

    public ResultSet read(RecipeList recipeList, User user) throws SQLException {
        String query = "SELECT RECIPELISTID FROM RECIPELIST WHERE RECIPELISTNAME=? AND USERNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, recipeList.getName());
        statement.setString(2, user.getUsername());
        return statement.executeQuery();
    }

    public void delete(Recipe recipe, User user) throws SQLException {
        String query = "DELETE FROM RECIPE WHERE RECIPENAME=? AND USERNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, recipe.getName());
        statement.setString(2, user.getUsername());
        statement.execute();
    }

    public void create(RecipeList recipeList, User user) throws SQLException {
        String query = "INSERT INTO RECIPELIST (RECIPELISTNAME, USERNAME) values(?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, recipeList.getName());
        statement.setString(2, user.getUsername());
        statement.execute();
    }

    public ResultSet populateRecipeList(RecipeList recipeList, User user) throws SQLException {
        //String query = "SELECT RECIPELISTNAME, recipeName, url, source, ingredients, totalWeight, dietLabels, healthLabels, calories, glycemicIndex, yield, instructions, cautions, username FROM RECIPELIST WHERE USERNAME=?";
        String query = "SELECT RECIPELISTNAME FROM RECIPELIST WHERE USERNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        return statement.executeQuery();
    }

    public ResultSet getRecipeLists(RecipeList recipeList, User user) throws SQLException {
        //String query = "SELECT recipeName, url, source, ingredients, totalWeight, dietLabels, healthLabels, calories, glycemicIndex, yield, instructions, cautions, username FROM RECIPELIST,recipelistitem,recipe WHERE recipelist.username=? and recipelistitem.recipelistid=recipelist.recipelistid and recipelistitem.recipeid=recipe.recipeid and RECIPELISTNAME=?";
        String query="select distinct recipeName, url, source, ingredients, dietLabels, healthLabels, calories, instructions, cautions, recipe.username FROM RECIPELIST,recipelistitem,recipe WHERE recipelist.username=? and recipelistitem.recipelistid=recipelist.recipelistid and recipelistitem.recipeid=recipe.recipeid and RECIPELISTNAME=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, recipeList.getName());
        return statement.executeQuery();
    }

    public void create(RecipeList recipeList, Recipe recipe, User user) throws SQLException {

        if (recipe.getSource()==null)
            createCustomRecipe(recipe, user);
        else {
            recipe.getInstructions().getInstructions().add(recipe.getURL());
            create(recipe, user);
        }
        String query = "SELECT recipe.recipeid, recipelist.recipelistid from recipelist,recipe,user where user.username=? and user.username=recipe.username and recipelist.username=user.username and recipelist.recipelistname=? and recipe.recipename=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, recipeList.getName());
        statement.setString(3, recipe.getName());
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            int recipeId = rs.getInt(1);
            int recipeListId = rs.getInt(2);
            query = "INSERT INTO RECIPELISTITEM (recipeid, recipelistid) values(?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, recipeId);
            statement.setInt(2, recipeListId);
            statement.execute();
        }
    }

    // String query = "SELECT * FROM WEEKLYPLAN WHERE weeklyplan.USERNAME=?";
    // PreparedStatement statement = connection.prepareStatement(query);
    // statement.setString(1, user.getUsername());
    // ResultSet rs = statement.executeQuery();
    // //ResultSet rs = statement.executeQuery("SELECT * FROM WEEKLYPLAN,USER WHERE user.username=weeklyplan.username and user.USERNAME='" + user.getUsername() + "'");
    // if (rs.next()) {

    // }
    // return rs;

    



    public void delete(Pantry pantry, User user) throws SQLException {
        // String username = user.getUsername();
        // ResultSet rs = statement.executeQuery("select pantry.pantryID from pantry,user where pantry.username=user.username='" + username + "'");
        // String pantryID = "";
        String query = "DELETE FROM ingredient WHERE pantryid IN (SELECT pantryid FROM pantry WHERE username=?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.execute();
        query = "DELETE FROM PANTRY WHERE USERNAME=?";
        statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.execute();
        // if (rs.next()) {
        //     pantryID = rs.getString(1);
        //     statement.execute("DELETE FROM INGREDIENT WHERE PANTRYID = " + pantryID + "");
        //     statement.execute("DELETE FROM PANTRY WHERE PANTRYID = " + pantryID + "");
        // }
        // else {
        //     System.out.println("You don't have a pantry");
        // }
    }

    //need to make an update

    private ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void disconnect() throws SQLException {
        statement.close();
        connection.close();
    }

}
