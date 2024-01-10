package com.keletu.ancient_thaumcraft.client.render;

import com.keletu.ancient_thaumcraft.tile.TileAlchemyFurnaceAdvanced;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.client.lib.obj.AdvancedModelLoader;
import thaumcraft.client.lib.obj.IModelCustom;

@SideOnly(value= Side.CLIENT)
public class TileAlchemyFurnaceAdvancedRenderer
extends TileEntitySpecialRenderer<TileAlchemyFurnaceAdvanced> {
    private final IModelCustom model = AdvancedModelLoader.loadModel(FURNACE);
    private static final ResourceLocation FURNACE = new ResourceLocation("ancient_thaumcraft", "textures/models/adv_alch_furnace.obj");

    @Override
    public void render(TileAlchemyFurnaceAdvanced tile, double x, double y, double z, float par8, int blockDamage, float partialTick) {
        super.render(tile, x, y, z,par8, blockDamage, partialTick);
        int a;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5f, (float)y, (float)z + 0.5f);
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
            renderQuadFromIcon(BlocksTC.fluxGoo.getDefaultState(), 190, 0.0f);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            float f = 1.0f - (float)tile.vis / (float)tile.maxVis;
            for (int a2 = 0; a2 < 4; ++a2) {
                GL11.glPushMatrix();
                GL11.glPushMatrix();
                GL11.glRotatef((float)(90 * a2), 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
                GL11.glTranslatef(0.85f, -1.8f, -1.25f);
                GL11.glScaled(0.4, 0.8, 0.4);
                //this.renderQuadFromIcon(tile.getBlockType().getDefaultState(), 150, 0.0f);
                GL11.glTranslatef(0.0f, 0.0f, -0.01f);
                this.renderQuadFromIcon(BlocksTC.fluxGoo.getDefaultState(), 190, f);
                GL11.glPopMatrix();
                //GL11.glPushMatrix();
                //GL11.glRotatef((float)(90 * a2), 0.0f, 0.0f, -1.0f);
                //GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                //GL11.glTranslatef(1.15f, 1.8f, -1.4f);
                //GL11.glScaled(-0.6, -0.6, -0.6);
                //this.renderQuadFromIcon(tile.getBlockType().getDefaultState(), 150, 0.0f);
                //GL11.glTranslatef(0.0f, 0.0f, 0.01f);
                //this.renderQuadFromIcon(BlocksTC.fluxGoo.getDefaultState(), 190, f);
                //GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
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
                GL11.glPushMatrix();
                //GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
                GL11.glTranslated(0, -1, -1);
                GL11.glScaled(1, 0.5, 1);
                this.renderQuadFromIcon(Blocks.FIRE.getDefaultState(), 220, 1.0f - Math.min(1.0f, (float)tile.heat / (float)tile.maxPower));
                GL11.glPopMatrix();
                GL11.glTranslatef(0.0f, 0.0f, 0.05f);
                //this.renderQuadFromIcon(tile.getBlockType().getDefaultState(), 150, 0.0f);
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    public void renderQuadFromIcon(IBlockState state, int brightness, float width) {
        Minecraft mc = Minecraft.getMinecraft();
        BlockRendererDispatcher dispatcher = mc.getBlockRendererDispatcher();
        BlockModelShapes shapes = dispatcher.getBlockModelShapes();
        IBakedModel thisBlock = shapes.getModelForState(state);
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        dispatcher.getBlockModelRenderer().renderModelBrightnessColor(state, thisBlock, 1f, 1f, 1f, 1f);

    }
}
