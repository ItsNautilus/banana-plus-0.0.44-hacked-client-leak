/*     */ package bananaplusdevelopment.addons.modules;
/*     */ 
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.entity.BoatMoveEvent;
/*     */ import minegame159.meteorclient.mixininterface.IVec3d;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.DoubleSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.modules.Module;
/*     */ import minegame159.meteorclient.systems.modules.Modules;
/*     */ import minegame159.meteorclient.utils.player.PlayerUtils;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1690;
/*     */ import net.minecraft.class_243;
/*     */ 
/*     */ public class BoatPhase
/*     */   extends Module {
/*  20 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  21 */   private final SettingGroup sgSpeeds = this.settings.createGroup("Speeds");
/*     */   
/*  23 */   private final Setting<Boolean> lockYaw = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  24 */       .name("lock-boat-yaw")
/*  25 */       .description("Locks boat yaw to the direction you're facing.")
/*  26 */       .defaultValue(true)
/*  27 */       .build());
/*     */ 
/*     */   
/*  30 */   private final Setting<Boolean> verticalControl = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  31 */       .name("vertical-control")
/*  32 */       .description("Whether or not space/ctrl can be used to move vertically.")
/*  33 */       .defaultValue(true)
/*  34 */       .build());
/*     */ 
/*     */   
/*  37 */   private final Setting<Boolean> adjustHorizontalSpeed = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  38 */       .name("adjust-horizontal-speed")
/*  39 */       .description("Whether or not horizontal speed is modified.")
/*  40 */       .defaultValue(false)
/*  41 */       .build());
/*     */ 
/*     */   
/*  44 */   private final Setting<Boolean> fall = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  45 */       .name("fall")
/*  46 */       .description("Toggles vertical glide.")
/*  47 */       .defaultValue(false)
/*  48 */       .build());
/*     */ 
/*     */   
/*  51 */   private final Setting<Double> horizontalSpeed = this.sgSpeeds.add((Setting)(new DoubleSetting.Builder())
/*  52 */       .name("horizontal-speed")
/*  53 */       .description("Horizontal speed in blocks per second.")
/*  54 */       .defaultValue(10.0D)
/*  55 */       .min(0.0D)
/*  56 */       .sliderMax(50.0D)
/*  57 */       .build());
/*     */ 
/*     */   
/*  60 */   private final Setting<Double> verticalSpeed = this.sgSpeeds.add((Setting)(new DoubleSetting.Builder())
/*  61 */       .name("vertical-speed")
/*  62 */       .description("Vertical speed in blocks per second.")
/*  63 */       .defaultValue(5.0D)
/*  64 */       .min(0.0D)
/*  65 */       .sliderMax(20.0D)
/*  66 */       .build());
/*     */ 
/*     */   
/*  69 */   private final Setting<Double> fallSpeed = this.sgSpeeds.add((Setting)(new DoubleSetting.Builder())
/*  70 */       .name("fall-speed")
/*  71 */       .description("How fast you fall in blocks per second.")
/*  72 */       .defaultValue(0.625D)
/*  73 */       .min(0.0D)
/*  74 */       .sliderMax(10.0D)
/*  75 */       .build());
/*     */ 
/*     */   
/*  78 */   private class_1690 boat = null;
/*     */   
/*     */   public BoatPhase() {
/*  81 */     super(AddModule.BANANAMINUS, "boat-phase", "Phase through blocks using a boat.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  86 */     this.boat = null;
/*  87 */     if (Modules.get().isActive(BoatGlitch.class)) ((BoatGlitch)Modules.get().get(BoatGlitch.class)).toggle();
/*     */   
/*     */   }
/*     */   
/*     */   public void onDeactivate() {
/*  92 */     if (this.boat != null) {
/*  93 */       this.boat.field_5960 = false;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onBoatMove(BoatMoveEvent event) {
/*  99 */     if (this.mc.field_1724.method_5854() != null && this.mc.field_1724.method_5854().method_5864().equals(class_1299.field_6121))
/* 100 */     { if (this.boat != this.mc.field_1724.method_5854()) {
/* 101 */         if (this.boat != null) {
/* 102 */           this.boat.field_5960 = false;
/*     */         }
/* 104 */         this.boat = (class_1690)this.mc.field_1724.method_5854();
/*     */       }  }
/* 106 */     else { this.boat = null; }
/*     */     
/* 108 */     if (this.boat != null) {
/* 109 */       class_243 vel; this.boat.field_5960 = true;
/* 110 */       this.boat.field_5968 = 1.0F;
/*     */       
/* 112 */       if (((Boolean)this.lockYaw.get()).booleanValue()) {
/* 113 */         this.boat.field_6031 = this.mc.field_1724.field_6031;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 118 */       if (((Boolean)this.adjustHorizontalSpeed.get()).booleanValue()) {
/* 119 */         vel = PlayerUtils.getHorizontalVelocity(((Double)this.horizontalSpeed.get()).doubleValue());
/*     */       } else {
/*     */         
/* 122 */         vel = this.boat.method_18798();
/*     */       } 
/*     */       
/* 125 */       double velX = vel.field_1352;
/* 126 */       double velY = 0.0D;
/* 127 */       double velZ = vel.field_1350;
/*     */       
/* 129 */       if (((Boolean)this.verticalControl.get()).booleanValue())
/* 130 */       { if (this.mc.field_1690.field_1903.method_1434()) velY += ((Double)this.verticalSpeed.get()).doubleValue() / 20.0D; 
/* 131 */         if (this.mc.field_1690.field_1867.method_1434()) { velY -= ((Double)this.verticalSpeed.get()).doubleValue() / 20.0D; }
/* 132 */         else if (((Boolean)this.fall.get()).booleanValue()) { velY -= ((Double)this.fallSpeed.get()).doubleValue() / 20.0D; }  }
/* 133 */       else if (((Boolean)this.fall.get()).booleanValue()) { velY -= ((Double)this.fallSpeed.get()).doubleValue() / 20.0D; }
/*     */       
/* 135 */       ((IVec3d)this.boat.method_18798()).set(velX, velY, velZ);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/BoatPhase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */