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
    private boolean powerState;
    private int suctionLevel, batteryLevel;
    private float dirtLevel;

    //operations or functionality
    VacuumCleaner(){
        suctionLevel = 1; //lowest suction
        powerState = false; // off
        batteryLevel = 100; // fully charged
        dirtLevel = 0; //vacuum cleaner dirt is 0


        System.out.println("OBJECT IS CREATED....");
    }

    public int getSuctionLevel () {
        return suctionLevel;
    }

    public boolean getPowerState () {
        return powerState;
    }

    public void powerStateOn () {
        powerState = true;
    }

    public void powerStateOff () {
        powerState = false;
    }

    public void addSuctionLevel (int amount) {
        suctionLevel += amount;
    }

    public void minusSuctionLevel (int amount) {
        suctionLevel -= amount;
    }





}
