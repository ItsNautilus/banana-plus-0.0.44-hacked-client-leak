/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import bananaplusdevelopment.utils.EntityUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.DoubleSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.modules.Modules;
/*     */ import minegame159.meteorclient.systems.modules.world.InstaMine;
/*     */ import minegame159.meteorclient.utils.entity.SortPriority;
/*     */ import minegame159.meteorclient.utils.entity.TargetUtils;
/*     */ import minegame159.meteorclient.utils.player.FindItemResult;
/*     */ import minegame159.meteorclient.utils.player.InvUtils;
/*     */ import minegame159.meteorclient.utils.player.Rotations;
/*     */ import minegame159.meteorclient.utils.world.BlockUtils;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2846;
/*     */ 
/*     */ public class AutoCityPlus extends Module {
/*  29 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  31 */   private final Setting<Double> targetRange = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  32 */       .name("target-range")
/*  33 */       .description("The radius in which players get targeted.")
/*  34 */       .defaultValue(4.0D)
/*  35 */       .min(0.0D)
/*  36 */       .sliderMax(5.0D)
/*  37 */       .build());
/*     */ 
/*     */   
/*  40 */   private final Setting<Boolean> support = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  41 */       .name("support")
/*  42 */       .description("If there is no block below a city block it will place one before mining.")
/*  43 */       .defaultValue(true)
/*  44 */       .build());
/*     */ 
/*     */   
/*  47 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  48 */       .name("rotate")
/*  49 */       .description("Automatically rotates you towards the city block.")
/*  50 */       .defaultValue(true)
/*  51 */       .build());
/*     */ 
/*     */   
/*  54 */   private final Setting<Boolean> insta = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  55 */       .name("Instant")
/*  56 */       .description("Instamine their surround.")
/*  57 */       .defaultValue(false)
/*  58 */       .build());
/*     */ 
/*     */   
/*  61 */   private final Setting<Boolean> selfToggle = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  62 */       .name("self-toggle")
/*  63 */       .description("Automatically toggles off after activation.")
/*  64 */       .defaultValue(true)
/*  65 */       .build());
/*     */   
/*     */   private class_1657 target;
/*     */   private class_2338 blockPosTarget;
/*     */   private boolean sentMessage;
/*     */   
/*     */   public AutoCityPlus() {
/*  72 */     super(AddModule.BANANAPLUS, "auto-city+", "Automatically cities a target by mining the nearest obsidian next to them. (more then obi)");
/*     */   }
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/*  76 */     if (TargetUtils.isBadTarget(this.target, ((Double)this.targetRange.get()).doubleValue())) {
/*  77 */       class_1657 search = TargetUtils.getPlayerTarget(((Double)this.targetRange.get()).doubleValue(), SortPriority.LowestDistance);
/*  78 */       if (search != this.target) this.sentMessage = false; 
/*  79 */       this.target = search;
/*     */     } 
/*     */     
/*  82 */     if (TargetUtils.isBadTarget(this.target, ((Double)this.targetRange.get()).doubleValue())) {
/*  83 */       this.target = null;
/*  84 */       this.blockPosTarget = null;
/*  85 */       if (((Boolean)this.selfToggle.get()).booleanValue()) toggle();
/*     */       
/*     */       return;
/*     */     } 
/*  89 */     this.blockPosTarget = EntityUtils.getCityBlock(this.target);
/*     */     
/*  91 */     if (this.blockPosTarget == null) {
/*  92 */       if (((Boolean)this.selfToggle.get()).booleanValue()) {
/*  93 */         error("No target block found... disabling.", new Object[0]);
/*  94 */         toggle();
/*     */       } 
/*  96 */       this.target = null;
/*     */       
/*     */       return;
/*     */     } 
/* 100 */     if (PlayerUtils.distanceTo(this.blockPosTarget) > this.mc.field_1761.method_2904() && ((Boolean)this.selfToggle.get()).booleanValue()) {
/* 101 */       error("Target block out of reach... disabling.", new Object[0]);
/* 102 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     if (!this.sentMessage) {
/* 107 */       info("Attempting to city %s.", new Object[] { this.target.method_5820() });
/* 108 */       this.sentMessage = true;
/*     */     } 
/*     */     
/* 111 */     FindItemResult pickaxe = InvUtils.find(itemStack -> (itemStack.method_7909() == class_1802.field_8377 || itemStack.method_7909() == class_1802.field_22024));
/*     */     
/* 113 */     if (!pickaxe.isHotbar()) {
/* 114 */       if (((Boolean)this.selfToggle.get()).booleanValue()) {
/* 115 */         error("No pickaxe found... disabling.", new Object[0]);
/* 116 */         toggle();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 121 */     if (((Boolean)this.support.get()).booleanValue()) {
/* 122 */       BlockUtils.place(this.blockPosTarget.method_10087(1), InvUtils.findInHotbar(new class_1792[] { class_1802.field_8281 }, ), ((Boolean)this.rotate.get()).booleanValue(), 0, true);
/*     */     }
/*     */     
/* 125 */     InvUtils.swap(pickaxe.getSlot());
/*     */     
/* 127 */     if (((Boolean)this.rotate.get()).booleanValue()) { Rotations.rotate(Rotations.getYaw(this.blockPosTarget), Rotations.getPitch(this.blockPosTarget), () -> mine(this.blockPosTarget)); }
/* 128 */     else { mine(this.blockPosTarget); }
/*     */     
/* 130 */     if (((Boolean)this.insta.get()).booleanValue()) ((InstaMine)Modules.get().get(InstaMine.class)).isActive();
/*     */     
/* 132 */     if (((Boolean)this.selfToggle.get()).booleanValue()) toggle(); 
/*     */   }
/*     */   
/*     */   private void mine(class_2338 blockPos) {
/* 136 */     this.mc.method_1562().method_2883((class_2596)new class_2846(class_2846.class_2847.field_12968, blockPos, class_2350.field_11036));
/* 137 */     this.mc.field_1724.method_6104(class_1268.field_5808);
/* 138 */     this.mc.method_1562().method_2883((class_2596)new class_2846(class_2846.class_2847.field_12973, blockPos, class_2350.field_11036));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInfoString() {
/* 143 */     if (this.target != null) return this.target.method_5820(); 
/* 144 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/AutoCityPlus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */