import java.util.ArrayList;

public class Instructions {
    
    private ArrayList<String> instructions = new ArrayList<>();
    private ArrayList<String> cautions = new ArrayList<>();

    public Instructions(ArrayList<String> instructions, ArrayList<String> cautions) {
        this.instructions = instructions;
        this.cautions = cautions;
    }

    public Instructions() {}

    public void setInstructions(ArrayList<String> instructions)
    {
        this.instructions = instructions;
    }

    public void setCautions(ArrayList<String> cautions)
    {
        this.cautions = cautions;
    }

    public ArrayList<String> getInstructions() {
        return this.instructions;
    }

    public ArrayList<String> getCautions() {
        return this.cautions;
    }

    public String toString() //TODO Update
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
