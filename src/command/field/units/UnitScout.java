package command.field.units;

import command.field.Player;

public class UnitScout extends Unit {
    UnitScout(Player _owner) {
        super(_owner);
        unitCost = 5;
        hp = 50;
        damage = 15;
        unitType = UnitType.SCOUT;
        unitIcon = "<";
    }
    
    public void move() {
    
    }
}
