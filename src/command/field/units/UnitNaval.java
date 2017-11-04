package command.field.units;

import command.field.Player;

public class UnitNaval extends Unit {
    UnitNaval(Player _owner) {
        super(_owner);
        unitCost = 10;
        hp = 90;
        damage = 25;
        unitType = UnitType.NAVAL;
    }
    
    public void move() {
    
    }
}
