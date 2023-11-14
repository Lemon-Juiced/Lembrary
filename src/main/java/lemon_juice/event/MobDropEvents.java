package lemon_juice.event;

import lemon_juice.item.custom.BeheadingSwordItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class MobDropEvents {
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event){
        var entity = event.getEntity();
        var level = event.getEntity().level();

        if(!level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) return;

        var drops = event.getDrops();
        var attacker = event.getSource().getEntity();
        int mobHeadDropChance = 10; // Out of 100
        Random random = new Random();

        // If the Player kills a mob, check if the player killed the correct mob with a BeheadingSwordItem, then run drops (if the probability works out)
        if(attacker instanceof Player playerEntity){
            Item itemInHand = playerEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (itemInHand instanceof BeheadingSwordItem){
                if(entity instanceof Creeper && random.nextInt(0, 100) < mobHeadDropChance)
                    drops.add(new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.CREEPER_HEAD)));

                if((entity instanceof Piglin || entity instanceof PiglinBrute) && random.nextInt(0, 100) < mobHeadDropChance)
                    drops.add(new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.PIGLIN_HEAD)));

                if(entity instanceof Skeleton && random.nextInt(0, 100) < mobHeadDropChance)
                    drops.add(new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.SKELETON_SKULL)));

                if(entity instanceof WitherSkeleton && random.nextInt(0, 100) < mobHeadDropChance)
                    drops.add(new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.WITHER_SKELETON_SKULL)));

                if(entity instanceof Zombie && random.nextInt(0, 100) < mobHeadDropChance)
                    drops.add(new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.ZOMBIE_HEAD)));
            }
        }
    }
}
