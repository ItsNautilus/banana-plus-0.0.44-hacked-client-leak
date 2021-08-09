/*    */ package bananaplusdevelopment.addons.modules;
/*    */ 
/*    */ import bananaplusdevelopment.addons.AddModule;
/*    */ import bananaplusdevelopment.utils.ares.Timer;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import minegame159.meteorclient.events.world.TickEvent;
/*    */ import minegame159.meteorclient.settings.IntSetting;
/*    */ import minegame159.meteorclient.settings.Setting;
/*    */ import minegame159.meteorclient.settings.SettingGroup;
/*    */ import minegame159.meteorclient.systems.modules.Module;
/*    */ import minegame159.meteorclient.systems.modules.Modules;
/*    */ import minegame159.meteorclient.systems.modules.render.Freecam;
/*    */ 
/*    */ public class Twerk
/*    */   extends Module {
/* 16 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 18 */   private final Setting<Integer> twerkDelay = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/* 19 */       .name("Twerk Delay")
/* 20 */       .description("In ticks")
/* 21 */       .defaultValue(5)
/* 22 */       .sliderMin(1)
/* 23 */       .sliderMax(10)
/* 24 */       .build());
/*    */   
/*    */   private boolean hasTwerked = false;
/*    */   private Timer onTwerk;
/*    */   
/*    */   public Twerk() {
/* 30 */     super(AddModule.BANANAMINUS, "Twerk", "Twerk like the true queen Miley Cyrus");
/*    */ 
/*    */     
/* 33 */     this.onTwerk = new Timer();
/*    */   }
/*    */   public boolean doVanilla() {
/* 36 */     return (this.hasTwerked && !Modules.get().isActive(Freecam.class));
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Pre event) {
/* 42 */     if (!this.hasTwerked && !this.mc.field_1724.method_5715()) {
/*    */       
/* 44 */       this.onTwerk.reset();
/* 45 */       this.hasTwerked = true;
/*    */     } 
/*    */     
/* 48 */     if (this.onTwerk.passedTicks(((Integer)this.twerkDelay.get()).intValue()) && this.hasTwerked) {
/* 49 */       this.hasTwerked = false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDeactivate() {
/* 57 */     this.hasTwerked = false;
/* 58 */     this.onTwerk.reset();
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/Twerk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */