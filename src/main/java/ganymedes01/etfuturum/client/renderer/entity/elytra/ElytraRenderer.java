package ganymedes01.etfuturum.client.renderer.entity.elytra;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.elytra.IClientElytraPlayer;
import ganymedes01.etfuturum.elytra.IElytraPlayer;
import ganymedes01.etfuturum.items.equipment.ItemArmorElytra;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ElytraRenderer extends ModelRenderer {
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");

    public AbstractClientPlayer player = null;
    public ModelBase model;

    public ModelRenderer leftWing;
    public ModelRenderer rightWing;

    public ElytraRenderer(ModelBase model, int i1, int i2) {
        super(model, 0, 0);

        leftWing = new ModelRenderer(model, 22, 0);
        leftWing.addBox(-10.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
        rightWing = new ModelRenderer(model, 22, 0);
        rightWing.mirror = true;
        rightWing.addBox(0.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);

        addChild(leftWing);
        addChild(rightWing);
    }

    @SideOnly(Side.CLIENT)
    public void render(float scale) {
//        ItemStack itemstack = ItemArmorElytra.getElytra((EntityLivingBase) entityIn);

        GL11.glPushAttrib(-1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE_ELYTRA);
        GL11.glPushMatrix();

        GL11.glTranslatef(0.0F, 0.0F, 0.0F);

        // SET ROTATION ANGLES
        float f = 0.2617994F; // 15 degrees in radians
        float f1 = -0.2617994F;
        float f2 = 0.0F;
        float f3 = 0.0F;

        if (player != null && player instanceof IElytraPlayer && ((IElytraPlayer) player).etfu$isElytraFlying()) {
            float f4 = 1.0F;

            if (player.motionY < 0.0D) {
                Vec3 vec3d = (Vec3.createVectorHelper(player.motionX, player.motionY, player.motionZ)).normalize();
                f4 = 1.0F - (float) Math.pow(-vec3d.yCoord, 1.5D);
            }

            f = f4 * 0.34906584F + (1.0F - f4) * f; // 20 degrees in radians
            f1 = f4 * -((float) Math.PI / 2F) + (1.0F - f4) * f1;
        }
        else if (player != null && player.isSneaking()) {
            f = ((float) Math.PI * 2F / 9F);
            f1 = -((float) Math.PI / 4F);
            f2 = 3.0F;
            f3 = 0.08726646F;
        }

        this.leftWing.rotationPointX = 5.0F;
        this.leftWing.rotationPointY = f2;

        if (player instanceof IClientElytraPlayer) {
            IClientElytraPlayer cep = (IClientElytraPlayer) player;
            cep.setRotateElytraX((float) (cep.getRotateElytraX() + (f - cep.getRotateElytraX()) * 0.1D));
            cep.setRotateElytraY((float) (cep.getRotateElytraY() + (f3 - cep.getRotateElytraY()) * 0.1D));
            cep.setRotateElytraZ((float) (cep.getRotateElytraZ() + (f1 - cep.getRotateElytraZ()) * 0.1D));
            this.leftWing.rotateAngleX = cep.getRotateElytraX();
            this.leftWing.rotateAngleY = cep.getRotateElytraY();
            this.leftWing.rotateAngleZ = cep.getRotateElytraZ();
        } else {
            this.leftWing.rotateAngleX = f;
            this.leftWing.rotateAngleZ = f1;
            this.leftWing.rotateAngleY = f3;
        }

        this.rightWing.rotationPointX = -this.leftWing.rotationPointX;
        this.rightWing.rotateAngleY = -this.leftWing.rotateAngleY;
        this.rightWing.rotationPointY = this.leftWing.rotationPointY;
        this.rightWing.rotateAngleX = this.leftWing.rotateAngleX;
        this.rightWing.rotateAngleZ = -this.leftWing.rotateAngleZ;

        // RENDER
//        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//        GL11.glDisable(GL11.GL_CULL_FACE);

        super.render(scale);

        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
}
