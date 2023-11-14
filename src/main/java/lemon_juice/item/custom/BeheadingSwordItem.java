package lemon_juice.item.custom;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class BeheadingSwordItem extends SwordItem {
    public BeheadingSwordItem(Tier tier, int attackDamage, float speed, Properties properties) {
        super(tier, attackDamage + 2, speed - 1.0F, properties);
    }
}
