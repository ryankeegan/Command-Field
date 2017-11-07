package command.field.units;

import command.field.Player;

public class UnitGeneral extends Unit {
    UnitGeneral(Player _owner) {
        super(_owner);
        unitCost = 5;
        hp = 75;
        damage = 20;
        unitType = UnitType.GENERAL;
        unitIcon = "«";
    }
    
    public void move() {
    
    }
}
