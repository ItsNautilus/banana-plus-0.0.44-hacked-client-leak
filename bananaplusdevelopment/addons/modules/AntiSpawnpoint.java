/*    */ package bananaplusdevelopment.addons.modules;
/*    */ 
/*    */ import bananaplusdevelopment.addons.AddModule;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import minegame159.meteorclient.events.packets.PacketEvent;
/*    */ import minegame159.meteorclient.settings.BoolSetting;
/*    */ import minegame159.meteorclient.settings.Setting;
/*    */ import minegame159.meteorclient.settings.SettingGroup;
/*    */ import minegame159.meteorclient.systems.modules.Module;
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2885;
/*    */ 
/*    */ public class AntiSpawnpoint
/*    */   extends Module
/*    */ {
/* 18 */   private SettingGroup sgDefault = this.settings.getDefaultGroup();
/*    */   
/* 20 */   private Setting<Boolean> fakeUse = this.sgDefault.add((Setting)(new BoolSetting.Builder())
/* 21 */       .name("fake-use")
/* 22 */       .description("Fake using the bed or anchor.")
/* 23 */       .defaultValue(true)
/* 24 */       .build());
/*    */ 
/*    */   
/*    */   public AntiSpawnpoint() {
/* 28 */     super(AddModule.BANANAMINUS, "anti-spawnpoint", "Protects the player from losing the respawn point.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onSendPacket(PacketEvent.Send event) {
/* 33 */     if (this.mc.field_1687 == null)
/* 34 */       return;  if (!(event.packet instanceof class_2885)) {
/*    */       return;
/*    */     }
/* 37 */     class_2338 blockPos = ((class_2885)event.packet).method_12543().method_17777();
/* 38 */     boolean IsOverWorld = this.mc.field_1687.method_8597().method_29956();
/* 39 */     boolean IsNetherWorld = this.mc.field_1687.method_8597().method_29957();
/* 40 */     boolean BlockIsBed = this.mc.field_1687.method_8320(blockPos).method_26204() instanceof net.minecraft.class_2244;
/* 41 */     boolean BlockIsAnchor = this.mc.field_1687.method_8320(blockPos).method_26204().equals(class_2246.field_23152);
/*    */     
/* 43 */     if (((Boolean)this.fakeUse.get()).booleanValue()) {
/* 44 */       if (BlockIsBed && IsOverWorld) {
/* 45 */         this.mc.field_1724.method_6104(class_1268.field_5808);
/* 46 */         this.mc.field_1724.method_5814(blockPos.method_10263(), blockPos.method_10084().method_10264(), blockPos.method_10260());
/*    */       }
/* 48 */       else if (BlockIsAnchor && IsNetherWorld) {
/* 49 */         this.mc.field_1724.method_6104(class_1268.field_5808);
/*    */       } 
/*    */     }
/*    */     
/* 53 */     if ((BlockIsBed && IsOverWorld) || (BlockIsAnchor && IsNetherWorld))
/* 54 */       event.cancel(); 
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/AntiSpawnpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */