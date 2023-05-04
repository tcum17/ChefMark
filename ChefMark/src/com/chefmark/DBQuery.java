package chefmark;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public abstract class DBQuery {
    private DBConnection dbConnection;
    private Connection connection;
    private Statement statement;

    public void connect() throws SQLException{}
    public void disconnect() throws SQLException{}

    public void create(User user) throws SQLException{}
    public ResultSet read(User user) throws SQLException{
        ResultSet rs = null;
        return rs;
    }
    public void update(User user) throws SQLException{}
    public void delete(User user) throws SQLException{}
    public void create(Pantry pantry, User user) throws SQLException{}
    public void create(Pantry pantry, Ingredient ingredient, User user) throws SQLException{}
    public void delete(Pantry pantry, User user) throws SQLException {}
    public void update(Ingredient ingredient, User user) throws SQLException{}
    public void delete(Ingredient ingredient, User user) throws SQLException {}
    public void create(WeeklyPlan weeklyPlan, User user) throws SQLException {}
    public void create(Recipe recipe, User user) throws SQLException {}
    public void delete(Recipe recipe, User user) throws SQLException {}
    public void create(WeeklyPlan weeklyPlan, Recipe recipe, User user, String day) throws SQLException {}
    public void create(RecipeList recipeList, User user) throws SQLException {}
    public void update(RecipeList recipeList, User user, int recipeListId) throws SQLException {}
    public void deleteWeeklyPlan(WeeklyPlan weeklyPlan, User user) throws SQLException {}
    public void deleteFromWeeklyPlan(WeeklyPlan weeklyPlan, Recipe recipe, User user, String day) throws SQLException {}
    public void createCustomRecipe(Recipe recipe, User user) throws SQLException {}
    public void updateServingSize(Recipe recipe, User user) throws SQLException {}
    public void create(RecipeList recipeList, Recipe recipe, User user) throws SQLException {}
    public void update (WeeklyPlan weeklyPlan, User user, int weeklyPlanId) throws SQLException {}
    public ResultSet getWeeklyPlanId(WeeklyPlan weeklyPlan, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public void deleteRecipeList(RecipeList recipeList, User user) throws SQLException {}
    public void deleteAllRecipe(Recipe recipe, User user) throws SQLException {}
    public void deleteRecipeListItem(RecipeList recipeList, Recipe recipe, User user) throws SQLException {}
    public void delete(RecipeList recipeList, User user) throws SQLException {}
    public ResultSet getRecipeListId(RecipeList recipeList, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public ResultSet getRecipeLists(RecipeList recipeList, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public ResultSet populateRecipeList(RecipeList recipeList, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public ResultSet populateCustomRecipe(Recipe recipe, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public ResultSet populateWeeklyPlan(WeeklyPlan weeklyPlan, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public ResultSet read(Pantry pantry, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public ResultSet read(Ingredient ingredient, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public ResultSet read(Recipe recipe, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public ResultSet read(WeeklyPlan weeklyPlan, User user, String day) throws SQLException {
        ResultSet rs = null;
        return rs;
    }
    public ResultSet read(WeeklyPlan weeklyPlan, User user) throws SQLException {
        ResultSet rs = null;
        return rs;
    }


}