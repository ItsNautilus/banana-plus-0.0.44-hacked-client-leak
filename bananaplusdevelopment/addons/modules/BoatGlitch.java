/*     */ package bananaplusdevelopment.addons.modules;
/*     */ 
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.entity.BoatMoveEvent;
/*     */ import minegame159.meteorclient.events.meteor.KeyEvent;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.modules.Module;
/*     */ import minegame159.meteorclient.systems.modules.Modules;
/*     */ import minegame159.meteorclient.utils.misc.input.KeyAction;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2824;
/*     */ 
/*     */ public class BoatGlitch extends Module {
/*  21 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  23 */   private final Setting<Boolean> toggleAfter = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  24 */       .name("toggle-after")
/*  25 */       .description("Disables the module when finished.")
/*  26 */       .defaultValue(true)
/*  27 */       .build());
/*     */ 
/*     */   
/*  30 */   private final Setting<Boolean> remount = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  31 */       .name("remount")
/*  32 */       .description("Remounts the boat when finished.")
/*  33 */       .defaultValue(true)
/*  34 */       .build());
/*     */ 
/*     */   
/*  37 */   private class_1297 boat = null;
/*  38 */   private int dismountTicks = 0;
/*  39 */   private int remountTicks = 0;
/*     */   private boolean dontPhase = true;
/*     */   private boolean boatPhaseEnabled;
/*     */   
/*     */   public BoatGlitch() {
/*  44 */     super(AddModule.BANANAMINUS, "boat-glitch", "Glitches your boat into the block beneath you.  Dismount to trigger.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  49 */     this.dontPhase = true;
/*  50 */     this.dismountTicks = 0;
/*  51 */     this.remountTicks = 0;
/*  52 */     this.boat = null;
/*  53 */     if (Modules.get().isActive(BoatPhase.class)) {
/*  54 */       this.boatPhaseEnabled = true;
/*  55 */       ((BoatPhase)Modules.get().get(BoatPhase.class)).toggle();
/*     */     } else {
/*     */       
/*  58 */       this.boatPhaseEnabled = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/*  64 */     if (this.boat != null) {
/*  65 */       this.boat.field_5960 = false;
/*  66 */       this.boat = null;
/*     */     } 
/*  68 */     if (this.boatPhaseEnabled && !Modules.get().isActive(BoatPhase.class)) {
/*  69 */       ((BoatPhase)Modules.get().get(BoatPhase.class)).toggle();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onBoatMove(BoatMoveEvent event) {
/*  75 */     if (this.dismountTicks == 0 && !this.dontPhase) {
/*  76 */       if (this.boat != event.boat) {
/*  77 */         if (this.boat != null) {
/*  78 */           this.boat.field_5960 = false;
/*     */         }
/*  80 */         if (this.mc.field_1724.method_5854() != null && event.boat == this.mc.field_1724.method_5854()) {
/*  81 */           this.boat = (class_1297)event.boat;
/*     */         } else {
/*     */           
/*  84 */           this.boat = null;
/*     */         } 
/*     */       } 
/*  87 */       if (this.boat != null) {
/*  88 */         this.boat.field_5960 = true;
/*  89 */         this.boat.field_5968 = 1.0F;
/*  90 */         this.dismountTicks = 5;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/*  97 */     if (this.dismountTicks > 0) {
/*  98 */       this.dismountTicks--;
/*  99 */       if (this.dismountTicks == 0) {
/* 100 */         if (this.boat != null) {
/* 101 */           this.boat.field_5960 = false;
/* 102 */           if (((Boolean)this.toggleAfter.get()).booleanValue() && !((Boolean)this.remount.get()).booleanValue()) {
/* 103 */             toggle();
/*     */           }
/* 105 */           else if (((Boolean)this.remount.get()).booleanValue()) {
/* 106 */             this.remountTicks = 5;
/*     */           } 
/*     */         } 
/* 109 */         this.dontPhase = true;
/*     */       } 
/*     */     } 
/* 112 */     if (this.remountTicks > 0) {
/* 113 */       this.remountTicks--;
/* 114 */       if (this.remountTicks == 0) {
/* 115 */         this.mc.method_1562().method_2883((class_2596)new class_2824(this.boat, class_1268.field_5808, false));
/* 116 */         if (((Boolean)this.toggleAfter.get()).booleanValue())
/* 117 */           toggle(); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onKey(KeyEvent event) {
/* 124 */     if (event.key == this.mc.field_1690.field_1832.method_1429().method_1444() && event.action == KeyAction.Press && 
/* 125 */       this.mc.field_1724.method_5854() != null && this.mc.field_1724.method_5854().method_5864().equals(class_1299.field_6121)) {
/* 126 */       this.dontPhase = false;
/* 127 */       this.boat = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/BoatGlitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */