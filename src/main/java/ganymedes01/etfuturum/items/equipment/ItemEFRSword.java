package ganymedes01.etfuturum.items.equipment;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityItemUninflammable;
import ganymedes01.etfuturum.items.ItemUninflammable;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemEFRSword extends ItemSword implements IConfigurable {

    public ItemEFRSword(ToolMaterial material, int durabilityOverride) {
        super(material);
        this.setMaxDamage(durabilityOverride > -1 ? durabilityOverride : material.getMaxUses());
        this.setUnlocalizedName(Utils.getUnlocalisedName("netherite_sword"));
        this.setTextureName("netherite_sword");
        this.setCreativeTab(ConfigurationHandler.enableNetherite ? EtFuturum.creativeTabItems : null);
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {        
        return ModItems.netherite_ingot == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
    
    @Override
    public boolean isEnabled() {
        return ConfigurationHandler.enableNetherite;
    }
    
    public boolean hasCustomEntity(ItemStack stack)
    {
        return getUnlocalizedName().contains("netherite") && !ConfigurationHandler.enableNetheriteFlammable;
    }
    
    public Entity createEntity(World world, Entity location, ItemStack itemstack)
    {
        if(!getUnlocalizedName().contains("netherite"))
            return null;
        return ItemUninflammable.createUninflammableItem(world, location);
    }

}
