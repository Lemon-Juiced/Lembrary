package lemon_juice.item.tier;

import lemon_juice.Lembrary;
import lemon_juice.tag.TagRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.TierSortingRegistry;

import java.util.List;

public class LembraryTiers {
    /**
     * Vanilla Armor Tiers:
     * WOOD(0, 59, 2.0F, 0.0F, 15, () -> {return Ingredient.of(ItemTags.PLANKS);}),
     * STONE(1, 131, 4.0F, 1.0F, 5, () -> {return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);}),
     * IRON(2, 250, 6.0F, 2.0F, 14, () -> {return Ingredient.of(Items.IRON_INGOT);}),
     * DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> {return Ingredient.of(Items.DIAMOND);}),
     * GOLD(0, 32, 12.0F, 0.0F, 22, () -> {return Ingredient.of(Items.GOLD_INGOT);}),
     * NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> {return Ingredient.of(Items.NETHERITE_INGOT);});
     */

    public static final TierRecord necroniteTier = new TierRecord(5, -1, 12.0F, 5.0F, 25);
    public static final TierRecord blutsteinTier = new TierRecord(3, 250, 6.0F, 1.0F, 10);
    public static final TierRecord umbraliteTier = new TierRecord(5, -1, 14.0F, 6.0F, 24);
    public static final TierRecord zweiiteTier = new TierRecord(3, 250, 12.0F, 0.0F, 22);

    // This needs to be registered before UMBRALITE_TIER.
    public static final Tier NECRONITE_TIER = TierSortingRegistry.registerTier(
            new SimpleTier(necroniteTier.level(), necroniteTier.uses(), necroniteTier.speed(), necroniteTier.attackDamageBonus(), necroniteTier.enchantmentValue(),
                    TagRegistry.Blocks.NEEDS_NECRONITE_TOOL, () -> Ingredient.of(TagRegistry.Items.INGOTS_NECRONITE)), getResourceLocation("necronite"), List.of(Tiers.NETHERITE), List.of());

    public static final Tier BLUTSTEIN_TIER = TierSortingRegistry.registerTier(
            new SimpleTier(blutsteinTier.level(), blutsteinTier.uses(), blutsteinTier.speed(), blutsteinTier.attackDamageBonus(), blutsteinTier.enchantmentValue(),
                    BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(TagRegistry.Items.GEMS_BLUTSTEIN)), getResourceLocation("blutstein"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
    public static final Tier UMBRALITE_TIER = TierSortingRegistry.registerTier(
            new SimpleTier(umbraliteTier.level(), umbraliteTier.uses(), umbraliteTier.speed(), umbraliteTier.attackDamageBonus(), umbraliteTier.enchantmentValue(),
                    TagRegistry.Blocks.NEEDS_UMBRALITE_TOOL, () -> Ingredient.of(TagRegistry.Items.GEMS_UMBRALITE)), getResourceLocation("umbralite"), List.of(NECRONITE_TIER), List.of());
    public static final Tier ZWEIITE_TIER = TierSortingRegistry.registerTier(
            new SimpleTier(zweiiteTier.level(), zweiiteTier.uses(), zweiiteTier.speed(), zweiiteTier.attackDamageBonus(), zweiiteTier.enchantmentValue(),
                    BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(TagRegistry.Items.INGOTS_ZWEIITE)), getResourceLocation("zweiite"), List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));

    /**
     * Gets the <code>ResourceLocation</code> of a given material
     *
     * @param materialName The <code>String</code> of the material name
     * @return the <code>ResourceLocation</code> of a given material
     */
    private static ResourceLocation getResourceLocation(String materialName){
        return new ResourceLocation(Lembrary.MOD_ID, materialName);
    }
}
