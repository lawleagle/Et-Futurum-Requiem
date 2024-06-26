package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockBasalt extends BlockRotatedPillar implements ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	protected IIcon[] sideIcons;
	@SideOnly(Side.CLIENT)
	protected IIcon[] topIcons;
	private final String[] types = new String[]{"basalt", "polished_basalt"};

	public BlockBasalt() {
		super(Material.rock);
		setHardness(1.25F);
		setResistance(4.2F);
		setBlockName(Utils.getUnlocalisedName("basalt"));
		setBlockTextureName("basalt");
		Utils.setBlockSound(this, ModSounds.soundBasalt);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
		p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
		p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		sideIcons = new IIcon[2];
		topIcons = new IIcon[2];

		for (int i = 0; i < getTypes().length; ++i) {
			sideIcons[i] = p_149651_1_.registerIcon(getTypes()[i] + "_side");
			topIcons[i] = p_149651_1_.registerIcon(getTypes()[i] + "_top");
		}
	}

	@SideOnly(Side.CLIENT)
	protected IIcon getSideIcon(int p_150163_1_) {
		return this.sideIcons[p_150163_1_ % this.sideIcons.length];
	}

	@SideOnly(Side.CLIENT)
	protected IIcon getTopIcon(int p_150161_1_) {
		return this.topIcons[p_150161_1_ % this.topIcons.length];
	}

	@Override
	public IIcon[] getIcons() {
		return sideIcons;
	}

	@Override
	public String[] getTypes() {
		return types;
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return getTypes()[stack.getItemDamage() % getTypes().length];
	}
}
