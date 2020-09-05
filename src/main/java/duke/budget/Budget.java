package duke.budget;

public class Budget {

    //VARIABLES-----------------------------------------
    private double budgetSet;
    private double budgetRevised;
    private double budgetUsed = 0;
    private double budgetBalance = 0;
    private boolean isRevised = false;
    private boolean isOverBudget;

    private static double totalBudgetSet;
    private static double totalBudgetUsed = 0;
    private static double totalBudgetBalance = 0;
    private static boolean isTotalOverBudget;

    //CONSTRUCTORS--------------------------------------
    public Budget(double budgetSet) {
        this.budgetSet = budgetSet;
        this.budgetRevised = this.budgetSet;
        totalBudgetSet = totalBudgetSet + budgetSet;
    }

    public Budget() {}

    public Budget(double budgetSet, double budgetRevised,
                  double budgetUsed, double budgetBalance,
                  boolean isRevised, boolean isOverBudget) {
        this.budgetSet = budgetSet;
        this.budgetRevised = budgetRevised;
        this.budgetUsed = budgetUsed;
        this.budgetBalance = budgetBalance;
        this.isRevised = isRevised;
        this.isOverBudget = isOverBudget;
        totalBudgetSet = totalBudgetSet + budgetSet;
        totalBudgetUsed = totalBudgetUsed + budgetUsed;
        totalBudgetBalance = totalBudgetBalance + budgetBalance;
        isTotalOverBudget = totalBudgetBalance < 0;
    }

    //SET STATEMENTS------------------------------------
    public void setBudgetSet(double budgetSet) {
        this.budgetSet = budgetSet;
        this.budgetBalance = this.budgetSet - budgetUsed;
        this.isOverBudget = this.budgetBalance < 0;
        totalBudgetSet = totalBudgetSet + budgetSet;
    }

    public void setBudgetUsed(double budgetUsed) {
        this.budgetUsed = budgetUsed;
        this.budgetBalance = this.budgetRevised - budgetUsed;
        this.isOverBudget = this.budgetBalance < 0;
        totalBudgetUsed = totalBudgetUsed + budgetUsed;
        totalBudgetBalance = totalBudgetBalance + this.budgetBalance;
        isTotalOverBudget = totalBudgetBalance < 0;
    }

    private void transferBudgetIn(double balanceIn) {
        totalBudgetBalance = totalBudgetBalance - this.budgetBalance;
        this.budgetRevised = this.budgetRevised + balanceIn;
        if(budgetUsed != 0){
            this.budgetBalance = this.budgetRevised - budgetUsed;
            totalBudgetBalance = totalBudgetBalance + this.budgetBalance;
        }
        this.isOverBudget = this.budgetBalance < 0;
        this.isRevised = true;
    }

    public boolean transferBudgetOut(double balanceOut, Budget target) {
        if(this.budgetUsed == 0 && this.budgetRevised < balanceOut) {
                System.out.println("\tThere is insufficient budget set in the originating budget\n" +
                        "\tto effect the transfer.");
                return false;
        } else if (this.budgetUsed > 0 && this.budgetBalance < balanceOut) {
                System.out.println("\tThere is insufficient budget balance in the originating budget\n" +
                        "\tto effect the transfer.");
                return false;
        }

        totalBudgetBalance = totalBudgetBalance - this.budgetBalance;
        this.budgetRevised = this.budgetRevised - balanceOut;
        if(budgetUsed != 0){
            this.budgetBalance = this.budgetRevised - budgetUsed;
            totalBudgetBalance = totalBudgetBalance + this.budgetBalance;
        }
        this.isRevised = true;
        target.transferBudgetIn(balanceOut);
        System.out.println("\tThe budget transfer is successful.");
        return true;
    }

    public void deleteExistingBudget() {
        totalBudgetSet = totalBudgetSet - this.budgetSet;
        totalBudgetUsed = totalBudgetUsed - this.budgetUsed;
        totalBudgetBalance = totalBudgetBalance - this.budgetBalance;
    }


    //GET STATEMENTS------------------------------------
    public double getBudgetSet(){
        return this.budgetSet;
    }

    public double getBudgetRevised(){
        return this.budgetRevised;
    }

    public double getBudgetUsed(){
        return this.budgetUsed;
    }

    public double getBudgetBalance(){
        return this.budgetBalance;
    }

    public boolean getIsRevised(){
        return this.isRevised;
    }

    public boolean getIsOverBudget(){
        return this.isOverBudget;
    }

    public static double getTotalBudgetSet(){
        return totalBudgetSet;
    }

    public static double getTotalBudgetUsed(){
        return totalBudgetUsed;
    }

    public static double getTotalBudgetBalance(){
        return totalBudgetBalance;
    }

    public static boolean getIsTotalOverBudget(){
        return isTotalOverBudget;
    }

    public String getSaveText() {
        String text = this.budgetSet + "/" +
                this.budgetRevised + "/" +
                this.budgetUsed + "/" +
                this.budgetBalance + "/" +
                this.isRevised + "/" +
                this.isOverBudget;
        return text;
    }

    public String getWithinBudget() {
        if(this.getIsOverBudget()){
            return "\u2612\t$" + String.format("%,14.2f",
                    Math.abs(this.getBudgetBalance())) + " over budget.";
        } else if(this.getBudgetBalance() == 0) {
            return "\u2611\t$" + String.format("%,14.2f", 0.00) + " right on budget!";
        } else {
            return "\u2611\t$" + String.format("%,14.2f",
                    Math.abs(this.getBudgetBalance())) + " under budget.";
        }
    }

    public static void printBudgetReport(){
        String budgetReport = "$" + String.format("%,14.2f", Math.abs(totalBudgetBalance));
        if(isTotalOverBudget){
            System.out.println(String.format("%1$s%2$22s", "\t\t  a budget overrun of", budgetReport.toString()));
        } else if(totalBudgetBalance == 0) {
            System.out.println(String.format("%1$s%2$22s", "\t\t  a balanced budget balance of", budgetReport.toString()));
        } else {
            System.out.println(String.format("%1$s%2$22s", "\t\t  a healthy budget balance of", budgetReport.toString()));
        }
    }
}