package chefmark;

import java.util.ArrayList;

public class Instructions {
    
    private ArrayList<String> instructions = new ArrayList<>();
    private ArrayList<String> cautions = new ArrayList<>();

    /**
     * Constructor
     * @param instructions list of strings for all the instructions
     * @param cautions list of warnings
     */
    public Instructions(ArrayList<String> instructions, ArrayList<String> cautions) {
        this.instructions = instructions;
        this.cautions = cautions;
    }

    /**
     * Blank Constructor
     */
    public Instructions() {}

    /**
     * 
     * @param instructions sets instructions
     */
    public void setInstructions(ArrayList<String> instructions)
    {
        this.instructions = instructions;
    }

    /**
     * 
     * @param cautions sets recipe cautions
     */
    public void setCautions(ArrayList<String> cautions)
    {
        this.cautions = cautions;
    }

    /**
     * 
     * @return array of instructions
     */
    public ArrayList<String> getInstructions() {
        return this.instructions;
    }

    /**
     * 
     * @return gets cautions
     */
    public ArrayList<String> getCautions() {
        return this.cautions;
    }

    /**
     * formats correctly to a string
     */
    public String toString() 
    {
        String IandC = "";
        if(instructions.size()>0) IandC += "Instructions: \n";
        for(int i=0; i<instructions.size();i++){
            IandC += i+1 + ": " + instructions.get(i) + "\n";
        }
        if(cautions.size()>0) IandC += "cautions: \n";
        for(int i=0; i<cautions.size();i++)
        {
            IandC += cautions.get(i) + "\n";
        }
        return IandC;
    }
}
