package ganymedes01.etfuturum.client.renderer.entity.elytra;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.elytra.IClientElytraPlayer;
import ganymedes01.etfuturum.elytra.IElytraPlayer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class ModelElytra extends ModelBase {

	public ElytraRenderer elytraRenderer;

	public ModelElytra() {
		elytraRenderer = new ElytraRenderer(this, 22, 0);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		elytraRenderer.player = (AbstractClientPlayer) entityIn;
		elytraRenderer.render(scale);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are
	 * used for animating the movement of arms and legs, where par1 represents
	 * the time(so that arms and legs swing back and forth) and par2 represents
	 * how "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		float f = 0.2617994F; // 15 degrees in radians
		float f1 = -0.2617994F;
		float f2 = 0.0F;
		float f3 = 0.0F;

		if (entityIn instanceof IElytraPlayer && ((IElytraPlayer) entityIn).etfu$isElytraFlying()) {
			float f4 = 1.0F;

			if (entityIn.motionY < 0.0D) {
				Vec3 vec3d = (Vec3.createVectorHelper(entityIn.motionX, entityIn.motionY, entityIn.motionZ)).normalize();
				f4 = 1.0F - (float) Math.pow(-vec3d.yCoord, 1.5D);
			}

			f = f4 * 0.34906584F + (1.0F - f4) * f; // 20 degrees in radians
			f1 = f4 * -((float) Math.PI / 2F) + (1.0F - f4) * f1;
		} else if (entityIn.isSneaking()) {
			f = ((float) Math.PI * 2F / 9F);
			f1 = -((float) Math.PI / 4F);
			f2 = 3.0F;
			f3 = 0.08726646F;
		}

		this.elytraRenderer.leftWing.rotationPointX = 5.0F;
		this.elytraRenderer.leftWing.rotationPointY = f2;

		if (entityIn instanceof IClientElytraPlayer) {
			IClientElytraPlayer cep = (IClientElytraPlayer) entityIn;
			cep.setRotateElytraX((float) (cep.getRotateElytraX() + (f - cep.getRotateElytraX()) * 0.1D));
			cep.setRotateElytraY((float) (cep.getRotateElytraY() + (f3 - cep.getRotateElytraY()) * 0.1D));
			cep.setRotateElytraZ((float) (cep.getRotateElytraZ() + (f1 - cep.getRotateElytraZ()) * 0.1D));
			this.elytraRenderer.leftWing.rotateAngleX = cep.getRotateElytraX();
			this.elytraRenderer.leftWing.rotateAngleY = cep.getRotateElytraY();
			this.elytraRenderer.leftWing.rotateAngleZ = cep.getRotateElytraZ();
		} else {
			this.elytraRenderer.leftWing.rotateAngleX = f;
			this.elytraRenderer.leftWing.rotateAngleZ = f1;
			this.elytraRenderer.leftWing.rotateAngleY = f3;
		}

		this.elytraRenderer.rightWing.rotationPointX = -this.elytraRenderer.leftWing.rotationPointX;
		this.elytraRenderer.rightWing.rotateAngleY = -this.elytraRenderer.leftWing.rotateAngleY;
		this.elytraRenderer.rightWing.rotationPointY = this.elytraRenderer.leftWing.rotationPointY;
		this.elytraRenderer.rightWing.rotateAngleX = this.elytraRenderer.leftWing.rotateAngleX;
		this.elytraRenderer.rightWing.rotateAngleZ = -this.elytraRenderer.leftWing.rotateAngleZ;
	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third
	 * float params here are the same second and third as in the
	 * setRotationAngles method.
	 */
	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
		super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
	}
}