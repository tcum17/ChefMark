package chefmark;
public class Ingredient {
    private String ingredientName;
    private int ingredientID;
    private float quantity;
    // NOTE: we don't know all of the measures that API returns
    // private enum measure {
    // G, KG, MG, MICROG, KCAL, CUP,
    // OZ, L, ML, TSP, TBSP, DROP,
    // PN, DS, SSP, CSP, PT, QT, GAL
    // }
    private String measure;
    private float weight;
    private String foodCategory;
}
