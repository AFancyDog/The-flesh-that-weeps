package dev.afancydog.fleshweep;


import com.mojang.blaze3d.systems.RenderSystem;
import dev.afancydog.fleshweep.screen.DryingRackScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;



public class DryingRackScreen extends HandledScreen<DryingRackScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(TheFleshThatWeeps.MOD_ID,"textures/gui/drying_rack_gui.png");

    @Override
    protected void init() {
        super.init();

    }

    public DryingRackScreen(DryingRackScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth)/2;
        int y = (height - backgroundHeight)/2;


        context.drawTexture(TEXTURE,x,y,0,0,backgroundWidth,backgroundHeight);

        renderProgressArrow(context,x,y);

    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()){
            context.drawTexture(TEXTURE,x+75,y+31, 176,1,17,handler.getScaledProgress());
        }
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        drawMouseoverTooltip(context,mouseX,mouseY);
        super.render(context, mouseX, mouseY, delta);
    }
}
