package lemon_juice.tag;

import lemon_juice.Lembrary;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagRegistry {
    public static void init() {
        Items.init();
    }

    private TagRegistry() {}

    public static class Items {
        private static void init() {}
        private Items() {}

        public static final TagKey<Item> GEMS_BLUTSTEIN = forgeTag("gems/blutstein");
        public static final TagKey<Item> GEMS_UMBRALITE = forgeTag("gems/umbralite");

        public static final TagKey<Item> INGOTS_NECRONITE = forgeTag("ingots/necronite");
        public static final TagKey<Item> INGOTS_ZWEIITE = forgeTag("ingots/zweiite");

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> NEEDS_NECRONITE_TOOL = forgeTag("needs_necronite_tool");
        public static final TagKey<Block> NEEDS_UMBRALITE_TOOL = tag("needs_umbralite_tool");

        public static final TagKey<Block> WITH_TUNNELER = BlockTags.create(new ResourceLocation("minecraft:mineable/tunneler"));

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Lembrary.MOD_ID, name));
        }
        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }
}
