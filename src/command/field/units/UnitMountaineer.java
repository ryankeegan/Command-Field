package command.field.units;

import command.field.Player;

public class UnitMountaineer extends Unit {
    UnitMountaineer(Player _owner) {
        super(_owner);
        unitCost = 10;
        hp = 100;
        damage = 20;
        unitType = UnitType.MOUNTAINEER;
    }
    
    public void move() {
    
    }
}
