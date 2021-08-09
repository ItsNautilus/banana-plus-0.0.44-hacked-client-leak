/*    */ package bananaplusdevelopment.addons.modules;
/*    */ 
/*    */ import bananaplusdevelopment.addons.AddModule;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import minegame159.meteorclient.events.world.TickEvent;
/*    */ import minegame159.meteorclient.mixininterface.IVec3d;
/*    */ import minegame159.meteorclient.settings.BoolSetting;
/*    */ import minegame159.meteorclient.settings.Setting;
/*    */ import minegame159.meteorclient.settings.SettingGroup;
/*    */ import minegame159.meteorclient.systems.modules.Module;
/*    */ import net.minecraft.class_243;
/*    */ 
/*    */ public class Gravity extends Module {
/*    */   private final SettingGroup sgGeneral;
/*    */   
/*    */   public Gravity() {
/* 17 */     super(AddModule.BANANAMINUS, "gravity", "Modifies gravity.");
/*    */ 
/*    */     
/* 20 */     this.sgGeneral = this.settings.getDefaultGroup();
/*    */     
/* 22 */     this.dolphin = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 23 */         .name("dolphin")
/* 24 */         .description("Disable underwater gravity.")
/* 25 */         .defaultValue(true)
/* 26 */         .build());
/*    */ 
/*    */     
/* 29 */     this.moon = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 30 */         .name("moon")
/* 31 */         .description("Tired of being on earth?")
/* 32 */         .defaultValue(true)
/* 33 */         .build());
/*    */   }
/*    */   private final Setting<Boolean> dolphin; private final Setting<Boolean> moon;
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 38 */     if (this.mc.field_1690.field_1832.method_1434())
/*    */       return; 
/* 40 */     if (this.mc.field_1724.method_5799()) {
/* 41 */       if (((Boolean)this.dolphin.get()).booleanValue()) {
/* 42 */         class_243 velocity = this.mc.field_1724.method_18798();
/* 43 */         ((IVec3d)velocity).set(velocity.field_1352, 0.002D, velocity.field_1350);
/*    */       }
/*    */     
/*    */     }
/* 47 */     else if (((Boolean)this.moon.get()).booleanValue()) {
/* 48 */       class_243 velocity = this.mc.field_1724.method_18798();
/* 49 */       ((IVec3d)velocity).set(velocity.field_1352, velocity.field_1351 + 0.0568000030517578D, velocity.field_1350);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/Gravity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */