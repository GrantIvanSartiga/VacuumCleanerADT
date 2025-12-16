public class VacuumCleaner {
    /* 1. Creator - used to initialized your object
       2. Transformer
       3. Observer
       4. Inp/Out

       Object - real world objects, Chair(attributes, behavior)/(data fields, operations or functions)

       functions
       1. on and off
       2. change suction level
       3. can charge when battery low
       4. throw dirt away when dirt level is high

     */

    //datafields
    ;
    private int suctionLevel, batteryLevel, dirtLevel;
    private boolean isCharging;
    public static final int MAX_DIRT = 100;

    //operations or functionality
    VacuumCleaner() {
        this.suctionLevel = 0;
        this.batteryLevel = 100;
        this.dirtLevel = 0;
        this.isCharging = false;
        System.out.println("OBJECT IS CREATED....");
    }

    //Observers(accessor or getter)

    public boolean getPowerState(){
        return(suctionLevel>=1 &&
                batteryLevel>0 &&
                dirtLevel < 100 &&
                !isCharging);
    }

    public int getSuctionLevel () {
        return suctionLevel;
    }

    public int getDirtLevel() {
        return dirtLevel;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public boolean isCharging() {
        return isCharging;
    }

    public int getMaxDirt(){
        return MAX_DIRT;
    }
    public void addSuctionLevel () {
        if (suctionLevel < 3) {
            suctionLevel ++;
            System.out.println("Suction Level: "+ suctionLevel);
        } else{
            System.out.println("Suction Level: 3");
        }
    }

    public void minusSuctionLevel () {
        if (suctionLevel > 0) {
            suctionLevel --;
            System.out.println("Suction Level: "+ suctionLevel);

        } else {
            System.out.println("Suction Level: 0");
        }
    }

    public boolean useVacuum(){
        if(!getPowerState()){
            System.out.println("Vacuum cannot be used, check battery level, dirt level, or if the vacuum is charged");
            return false;
        }

        int Rate = suctionLevel * 2;
        batteryLevel -= Rate;
        dirtLevel += Rate;

        if(batteryLevel <= 0){
            batteryLevel = 0;
        }
        if (dirtLevel >= MAX_DIRT) {
            dirtLevel = MAX_DIRT;
        }
        return true;
    }

    public void emptyDust(){
        this.dirtLevel = 0;
        System.out.println("Dust container is emptied");
    }

    public void startCharging(){
        if(batteryLevel <100){
            this.isCharging = true;
            System.out.println("Vacuum Cleaner is charging");

        }else {
            System.out.println("Vacuum Cleaner is not charging");
        }
    }

    public void completeCharge(){
        this.batteryLevel = 100;
        this.isCharging = false;
        System.out.println("Charging Complete!");
    }



}
