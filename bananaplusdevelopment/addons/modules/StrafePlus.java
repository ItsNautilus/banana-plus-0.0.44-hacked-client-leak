/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import bananaplusdevelopment.utils.ares.ReflectionHelper;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.DoubleSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_317;
/*     */ 
/*     */ public class StrafePlus extends Module {
/*  15 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup(); private final Setting<Boolean> lowHop; private final Setting<Double> height; private final Setting<Boolean> speedBool;
/*     */   
/*     */   public StrafePlus() {
/*  18 */     super(AddModule.BANANAPLUS, "Strafe+", "Increase speed and control in air");
/*     */ 
/*     */     
/*  21 */     this.lowHop = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  22 */         .name("Low Hop")
/*     */         
/*  24 */         .defaultValue(false)
/*  25 */         .build());
/*     */ 
/*     */     
/*  28 */     this.height = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  29 */         .name("Height")
/*     */         
/*  31 */         .defaultValue(0.3D)
/*  32 */         .sliderMin(0.1D)
/*  33 */         .sliderMax(0.5D)
/*  34 */         .build());
/*     */ 
/*     */     
/*  37 */     this.speedBool = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  38 */         .name("Modify Speed")
/*     */         
/*  40 */         .defaultValue(true)
/*  41 */         .build());
/*     */ 
/*     */     
/*  44 */     this.speedVal = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  45 */         .name("Speed")
/*     */         
/*  47 */         .defaultValue(0.3199999928474426D)
/*  48 */         .min(0.20000000298023224D)
/*  49 */         .sliderMax(0.6000000238418579D)
/*  50 */         .build());
/*     */ 
/*     */     
/*  53 */     this.sprintBool = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  54 */         .name("Auto Sprint")
/*     */         
/*  56 */         .defaultValue(true)
/*  57 */         .build());
/*     */ 
/*     */     
/*  60 */     this.useTimer = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  61 */         .name("Use Timer+")
/*  62 */         .description("if the timer should be used")
/*  63 */         .defaultValue(false)
/*  64 */         .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     Objects.requireNonNull(this.useTimer); this.tps = this.sgGeneral.add((Setting)(new IntSetting.Builder()).name("TPS").defaultValue(20).min(1).sliderMax(160).visible(this.useTimer::get)
/*  74 */         .build());
/*     */   }
/*     */   private final Setting<Double> speedVal; private final Setting<Boolean> sprintBool; private final Setting<Boolean> useTimer; private final Setting<Integer> tps;
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/*  80 */     if (((Boolean)this.useTimer.get()).booleanValue()) {
/*  81 */       ReflectionHelper.setPrivateValue(class_317.class, ReflectionHelper.getPrivateValue(class_310.class, this.mc, new String[] { "renderTickCounter", "field_1728" }), Float.valueOf(1000.0F / ((Integer)this.tps.get()).intValue()), new String[] { "tickTime", "field_1968" });
/*     */     }
/*     */     else {
/*     */       
/*  85 */       ReflectionHelper.setPrivateValue(class_317.class, ReflectionHelper.getPrivateValue(class_310.class, this.mc, new String[] { "renderTickCounter", "field_1728" }), Float.valueOf(50.0F), new String[] { "tickTime", "field_1968" });
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPlayerMove(PlayerMoveEvent event) {
/*  91 */     if (this.mc.field_1724.field_3913.field_3905 != 0.0F || this.mc.field_1724.field_3913.field_3907 != 0.0F) {
/*  92 */       Double speed; if (((Boolean)this.sprintBool.get()).booleanValue()) {
/*  93 */         this.mc.field_1724.method_5728(true);
/*     */       }
/*     */       
/*  96 */       if (this.mc.field_1724.method_24828() && ((Boolean)this.lowHop.get()).booleanValue()) this.mc.field_1724.method_5762(0.0D, ((Double)this.height.get()).doubleValue(), 0.0D);
/*     */       
/*  98 */       if (this.mc.field_1724.method_24828()) {
/*     */         return;
/*     */       }
/* 101 */       if (!((Boolean)this.speedBool.get()).booleanValue())
/* 102 */       { speed = Double.valueOf(Math.sqrt((this.mc.field_1724.method_18798()).field_1352 * (this.mc.field_1724.method_18798()).field_1352 + (this.mc.field_1724.method_18798()).field_1350 * (this.mc.field_1724.method_18798()).field_1350)); }
/* 103 */       else { speed = (Double)this.speedVal.get(); }
/*     */       
/* 105 */       float yaw = this.mc.field_1724.field_6031;
/* 106 */       float forward = 1.0F;
/*     */       
/* 108 */       if (this.mc.field_1724.field_6250 < 0.0F)
/* 109 */       { yaw += 180.0F;
/* 110 */         forward = -0.5F; }
/* 111 */       else if (this.mc.field_1724.field_6250 > 0.0F) { forward = 0.5F; }
/*     */       
/* 113 */       if (this.mc.field_1724.field_6212 > 0.0F) yaw -= 90.0F * forward; 
/* 114 */       if (this.mc.field_1724.field_6212 < 0.0F) yaw += 90.0F * forward;
/*     */       
/* 116 */       yaw = (float)Math.toRadians(yaw);
/* 117 */       this.mc.field_1724.method_18800(-Math.sin(yaw) * speed.doubleValue(), (this.mc.field_1724.method_18798()).field_1351, Math.cos(yaw) * speed.doubleValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 123 */     ReflectionHelper.setPrivateValue(class_317.class, ReflectionHelper.getPrivateValue(class_310.class, this.mc, new String[] { "renderTickCounter", "field_1728" }), Float.valueOf(50.0F), new String[] { "tickTime", "field_1968" });
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/StrafePlus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */