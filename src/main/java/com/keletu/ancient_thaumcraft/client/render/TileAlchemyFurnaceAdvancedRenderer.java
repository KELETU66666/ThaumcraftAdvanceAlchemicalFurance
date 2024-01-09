package com.keletu.ancient_thaumcraft.client.render;

import com.keletu.ancient_thaumcraft.tile.TileAlchemyFurnaceAdvanced;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.obj.AdvancedModelLoader;
import thaumcraft.client.lib.obj.IModelCustom;

@SideOnly(value= Side.CLIENT)
public class TileAlchemyFurnaceAdvancedRenderer extends TileEntitySpecialRenderer<TileAlchemyFurnaceAdvanced> {
    private final IModelCustom model = AdvancedModelLoader.loadModel(FURNACE);
    private static final ResourceLocation FURNACE = new ResourceLocation("ancient_thaumcraft", "textures/models/adv_alch_furnace.obj");

    @Override
    public void render(TileAlchemyFurnaceAdvanced tile, double par2, double par4, double par6, float par8, int blockDamage, float partialTick) {
        int a;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2 + 0.5f, (float)par4, (float)par6 + 0.5f);
        GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (tile.heat <= 100) {
            this.bindTexture(new ResourceLocation("ancient_thaumcraft", "textures/models/alch_furnace.png"));
        } else {
            this.bindTexture(new ResourceLocation("ancient_thaumcraft", "textures/models/alch_furnace_on.png"));
        }
        this.model.renderPart("Base");
        if (tile.vis <= 0) {
            this.bindTexture(new ResourceLocation("ancient_thaumcraft", "textures/models/alch_furnace_tank.png"));
        } else {
            this.bindTexture(new ResourceLocation("ancient_thaumcraft", "textures/models/alch_furnace_tank_on.png"));
        }
        for (a = 0; a < 4; ++a) {
            GL11.glPushMatrix();
            GL11.glRotatef((float)(90 * a), 0.0f, 0.0f, 1.0f);
            this.model.renderPart("Tank");
            GL11.glPopMatrix();
        }
        if (tile.vis > 0) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.5f, -0.5f, 1.1f);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            GL11.glPopMatrix();
            float f = 1.0f - (float)tile.vis / (float)tile.maxVis;
            for (int a2 = 0; a2 < 4; ++a2) {
                GL11.glPushMatrix();
                GL11.glRotatef((float)(90 * a2), 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
                GL11.glTranslatef(0.85f, -1.8f, -1.4f);
                GL11.glScaled(0.3, 0.6, 1.0);
                GL11.glTranslatef(0.0f, 0.0f, -0.01f);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glRotatef((float)(90 * a2), 0.0f, 0.0f, -1.0f);
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glTranslatef(1.15f, 1.8f, -1.4f);
                GL11.glScaled(-0.3, -0.6, -1.0);
                GL11.glPopMatrix();
            }
        }
        if (tile.heat > 100) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, 1.0f);
            for (a = 0; a < 4; ++a) {
                GL11.glPushMatrix();
                GL11.glRotatef((float)(90 * a), 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(135.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-0.5f, 0.0f, -1.0f);
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
}
