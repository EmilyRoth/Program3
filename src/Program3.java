
//Name: (Fill this in)
//EID: (Fill this in)


public class Program3 {

    EconomyCalculator calculator;
    VibraniumOreScenario vibraniumScenario;    

    public Program3() {
        this.calculator = null;
        this.vibraniumScenario = null;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 1.
     */
    public void initialize(EconomyCalculator ec) {
        this.calculator = ec;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 2.
     */
    public void initialize(VibraniumOreScenario vs) {
        this.vibraniumScenario = vs;
    }

    /*
     * This method returns an integer that is maximum possible gain in the Wakandan economy
     * given a certain amount of Vibranium
     */
    //TODO: Complete this function
    public int computeGain() {
        int project = calculator.getNumProjects();
        int vibranium = calculator.getNumVibranium();
        int [] [] table = new int[project+1][vibranium+1];

        // fill in all [0, h]
        table[0][0] = calculator.calculateGain(0,0);
        for(int h = 1; h<=vibranium; h++){
            table[0][h] = calculator.calculateGain(0, h);
        }

        // fill in all [i, 0]
        for(int i = 1; i<project; i++){
            table[i][0] = table[i-1][0] + calculator.calculateGain(i, 0);
        }

        for(int r = 1; r<project; r++){
            //int maxGain = 0;
            //int maxGainIndex = 0;
            for(int c = 0; c<=vibranium; c++){
                int maxGain = 0;
                for(int g = 0; g <= c; g++){
                    int gain = table[r-1][c-g] + calculator.calculateGain(r, g);
                    if(gain > maxGain)
                        maxGain = gain;
                }
                table[r][c] = maxGain;
            }
        }
        // traceback in table to compute gain
        return table[project-1][vibranium];
    }


    /*
     * This method returns an integer that is the maximum possible dollar value that a thief 
     * could steal given the weight and volume capacity of his/her bag by using the 
     * VibraniumOreScenario instance.
     */
     //TODO: Complete this method
     public int computeLoss() {
        int capacity = vibraniumScenario.getVolumeCapacity();
        int maxWeight = vibraniumScenario.getWeightCapacity();
        int oreNum = vibraniumScenario.getNumOres();
        int costs[][][] = new int[oreNum+1][maxWeight+1][capacity+1];
        for(int c = 0; c <=capacity;  c++){
            costs[0][0][c] = 0;
        }
        for(int n = 0; n<=oreNum; n++){
            costs[n][0][0] = 0;
        }

        for(int m = 0; m<=maxWeight; m++){
            costs[0][m][0] = 0;
        }


        for(int i = 1; i<=oreNum; i++){
            for(int w = 0; w<=maxWeight; w++){
                for(int h = 0; h<=capacity; h++){
                    int oreSize = vibraniumScenario.getVibraniumOre(i-1).getVolume();
                    int oreWeight = vibraniumScenario.getVibraniumOre(i-1).getWeight();
                    int oreValue = vibraniumScenario.getVibraniumOre(i-1).getPrice();
                    if(oreWeight > w){
                        costs[i][w][h] = costs[i-1][w][h];
                    }else{
                        if(oreSize > h){
                            costs[i][w][h] = costs[i-1][w][h];
                        }else{
                            int option = oreValue+costs[i-1][w-oreWeight][h-oreSize];
                            costs[i][w][h] = Math.max(costs[i-1][w][h], option);
                        }
                    }
                }
            }
        }
        return costs[oreNum][maxWeight][capacity];
     }
}


