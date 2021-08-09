/*    */ package bananaplusdevelopment.addons.modules;
/*    */ import bananaplusdevelopment.addons.AddModule;
/*    */ import bananaplusdevelopment.utils.EntityUtils;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import minegame159.meteorclient.events.render.RenderEvent;
/*    */ import minegame159.meteorclient.events.world.TickEvent;
/*    */ import minegame159.meteorclient.rendering.Renderer;
/*    */ import minegame159.meteorclient.rendering.ShapeMode;
/*    */ import minegame159.meteorclient.settings.ColorSetting;
/*    */ import minegame159.meteorclient.settings.EnumSetting;
/*    */ import minegame159.meteorclient.settings.Setting;
/*    */ import minegame159.meteorclient.settings.SettingGroup;
/*    */ import minegame159.meteorclient.systems.modules.Module;
/*    */ import minegame159.meteorclient.utils.entity.SortPriority;
/*    */ import minegame159.meteorclient.utils.entity.TargetUtils;
/*    */ import minegame159.meteorclient.utils.render.color.Color;
/*    */ import minegame159.meteorclient.utils.render.color.SettingColor;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ public class CityESPPlus extends Module {
/* 22 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*    */ 
/*    */ 
/*    */   
/* 26 */   private final Setting<ShapeMode> shapeMode = this.sgRender.add((Setting)(new EnumSetting.Builder())
/* 27 */       .name("shape-mode")
/* 28 */       .description("How the shapes are rendered.")
/* 29 */       .defaultValue((Enum)ShapeMode.Both)
/* 30 */       .build());
/*    */ 
/*    */   
/* 33 */   private final Setting<SettingColor> sideColor = this.sgRender.add((Setting)(new ColorSetting.Builder())
/* 34 */       .name("side-color")
/* 35 */       .description("The side color of the rendering.")
/* 36 */       .defaultValue(new SettingColor(230, 0, 255, 3))
/* 37 */       .build());
/*    */ 
/*    */   
/* 40 */   private final Setting<SettingColor> lineColor = this.sgRender.add((Setting)(new ColorSetting.Builder())
/* 41 */       .name("line-color")
/* 42 */       .description("The line color of the rendering.")
/* 43 */       .defaultValue(new SettingColor(230, 0, 255, 255))
/* 44 */       .build());
/*    */   
/*    */   private class_2338 target;
/*    */   
/*    */   public CityESPPlus() {
/* 49 */     super(AddModule.BANANAPLUS, "city-esp+", "Displays blocks that can be broken in order to city another player (more then obi)");
/*    */   }
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 53 */     class_1657 targetEntity = TargetUtils.getPlayerTarget((this.mc.field_1761.method_2904() + 2.0F), SortPriority.LowestDistance);
/*    */     
/* 55 */     if (TargetUtils.isBadTarget(targetEntity, (this.mc.field_1761.method_2904() + 2.0F))) {
/* 56 */       this.target = null;
/*    */     } else {
/* 58 */       this.target = EntityUtils.getCityBlock(targetEntity);
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onRender(RenderEvent event) {
/* 64 */     if (this.target == null)
/* 65 */       return;  Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, this.target, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0);
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/CityESPPlus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */