/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.DoubleSetting;
/*     */ import minegame159.meteorclient.settings.IntSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.modules.Module;
/*     */ import minegame159.meteorclient.utils.player.ChatUtils;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2374;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2680;
/*     */ 
/*     */ public class MonkeWalk extends Module {
/*  23 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  25 */   private final Setting<Double> activationWindow = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  26 */       .name("activation-window")
/*  27 */       .description("The area above the target Y level at which pull activates.")
/*  28 */       .min(0.2D)
/*  29 */       .max(5.0D)
/*  30 */       .sliderMin(0.2D)
/*  31 */       .sliderMax(5.0D)
/*  32 */       .defaultValue(0.5D)
/*  33 */       .build());
/*     */ 
/*     */   
/*  36 */   private final Setting<Integer> driftToHeight = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/*  37 */       .name("drift-to-height")
/*  38 */       .description("Y level to find blocks to drift onto.")
/*  39 */       .min(0)
/*  40 */       .max(256)
/*  41 */       .sliderMin(0)
/*  42 */       .sliderMax(256)
/*  43 */       .defaultValue(5)
/*  44 */       .build());
/*     */ 
/*     */   
/*  47 */   private final Setting<Double> horizontalPullStrength = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  48 */       .name("horizontal-pull")
/*  49 */       .description("The horizontal speed/strength at which you drift to the goal block.")
/*  50 */       .min(0.1D)
/*  51 */       .max(10.0D)
/*  52 */       .sliderMin(0.1D)
/*  53 */       .sliderMax(10.0D)
/*  54 */       .defaultValue(1.0D)
/*  55 */       .build());
/*     */ 
/*     */   
/*  58 */   private final Setting<Double> verticalPullStrength = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  59 */       .name("vertical-pull")
/*  60 */       .description("The vertical speed/strength at which you drift to the goal block.")
/*  61 */       .min(0.1D)
/*  62 */       .max(10.0D)
/*  63 */       .sliderMin(0.1D)
/*  64 */       .sliderMax(10.0D)
/*  65 */       .defaultValue(1.0D)
/*  66 */       .build());
/*     */ 
/*     */   
/*  69 */   private final Setting<Integer> searchRadius = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/*  70 */       .name("search-radius")
/*  71 */       .description("The radius at which tanuki mode searches for blocks (odd numbers only).")
/*  72 */       .min(3)
/*  73 */       .max(15)
/*  74 */       .sliderMin(3)
/*  75 */       .sliderMax(15)
/*  76 */       .defaultValue(3)
/*  77 */       .build());
/*     */ 
/*     */   
/*  80 */   private final Setting<Boolean> updatePositionFailsafe = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  81 */       .name("failsafe")
/*  82 */       .description("Updates your position to the top of the target block if you miss the jump.")
/*  83 */       .defaultValue(true)
/*  84 */       .build());
/*     */ 
/*     */   
/*  87 */   private final Setting<Double> failsafeWindow = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  88 */       .name("failsafe-window")
/*  89 */       .description("Window below the target block to fall to trigger failsafe.")
/*  90 */       .min(0.01D)
/*  91 */       .max(1.0D)
/*  92 */       .sliderMin(0.01D)
/*  93 */       .sliderMax(1.0D)
/*  94 */       .defaultValue(0.1D)
/*  95 */       .build());
/*     */ 
/*     */   
/*  98 */   private final Setting<Double> successfulLandingMargin = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  99 */       .name("landing-margin")
/* 100 */       .description("The distance from a landing block to be considered a successful landing.")
/* 101 */       .min(0.01D)
/* 102 */       .max(10.0D)
/* 103 */       .sliderMin(0.01D)
/* 104 */       .sliderMax(10.0D)
/* 105 */       .defaultValue(1.0D)
/* 106 */       .build()); private final class_2338.class_2339 blockPos;
/*     */   private final ArrayList<class_2338> validBlocks;
/*     */   
/*     */   public MonkeWalk() {
/* 110 */     super(AddModule.BANANAPLUS, "Monke Walk", "Makes moving on bedrock easier.");
/*     */ 
/*     */     
/* 113 */     this.blockPos = new class_2338.class_2339(0, 0, 0);
/* 114 */     this.validBlocks = new ArrayList<>();
/* 115 */     this.sortedBlocks = new TreeMap<>();
/* 116 */     this.playerHorizontalPos = new class_2338.class_2339();
/*     */   }
/*     */   private final TreeMap<Double, class_2338> sortedBlocks; private final class_2338.class_2339 playerHorizontalPos;
/*     */   private boolean successfulLanding;
/*     */   
/*     */   public void onActivate() {
/* 122 */     if (((Integer)this.searchRadius.get()).intValue() % 2 == 0) {
/* 123 */       ChatUtils.info("%d is not valid for radius, rounding up", new Object[] { this.searchRadius.get() });
/* 124 */       this.searchRadius.set(Integer.valueOf(((Integer)this.searchRadius.get()).intValue() + 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 131 */     if (this.mc.field_1724.method_23318() > ((Integer)this.driftToHeight.get()).intValue() + ((Double)this.activationWindow.get()).doubleValue())
/*     */       return; 
/* 133 */     class_243 targetPos = findNearestBlock(this.mc.field_1724.method_23317(), ((Integer)this.driftToHeight.get()).intValue() - 1, this.mc.field_1724.method_23321());
/*     */     
/* 135 */     if (targetPos == null)
/*     */       return; 
/* 137 */     if (this.mc.field_1724.method_23318() == targetPos.method_10214() + 1.0D)
/*     */       return; 
/* 139 */     if (this.mc.field_1690.field_1903.method_1434())
/*     */       return; 
/* 141 */     if (((Boolean)this.updatePositionFailsafe.get()).booleanValue() && !this.successfulLanding && 
/* 142 */       this.mc.field_1724.method_23318() < ((Integer)this.driftToHeight.get()).intValue() - ((Double)this.failsafeWindow.get()).doubleValue()) {
/* 143 */       this.mc.field_1724.method_5814(targetPos.method_10216(), targetPos.method_10214() + 1.0D, targetPos.method_10215());
/*     */     }
/*     */ 
/*     */     
/* 147 */     class_243 normalizedDirection = targetPos.method_1020(this.mc.field_1724.method_19538()).method_1029();
/*     */     
/* 149 */     ((IVec3d)this.mc.field_1724.method_18798()).set(
/* 150 */         (this.mc.field_1724.method_18798()).field_1352 + normalizedDirection.field_1352 * ((Double)this.horizontalPullStrength.get()).doubleValue() * this.mc.method_1488(), 
/* 151 */         (this.mc.field_1724.method_18798()).field_1351 + normalizedDirection.field_1351 * ((Double)this.verticalPullStrength.get()).doubleValue() * this.mc.method_1488(), 
/* 152 */         (this.mc.field_1724.method_18798()).field_1350 + normalizedDirection.field_1350 * ((Double)this.horizontalPullStrength.get()).doubleValue() * this.mc.method_1488());
/*     */ 
/*     */     
/* 155 */     this.successfulLanding = this.mc.field_1724.method_19538().method_24802((class_2374)targetPos, ((Double)this.successfulLandingMargin.get()).doubleValue());
/*     */   }
/*     */ 
/*     */   
/*     */   private class_243 findNearestBlock(double x, int y, double z) {
/* 160 */     this.validBlocks.clear();
/* 161 */     this.sortedBlocks.clear();
/*     */     
/* 163 */     this.playerHorizontalPos.method_10102(x, y, z);
/*     */     
/* 165 */     int rad = ((Integer)this.searchRadius.get()).intValue() - 1;
/* 166 */     for (int i = 0; i < ((Integer)this.searchRadius.get()).intValue(); i++) {
/* 167 */       for (int j = 0; j < ((Integer)this.searchRadius.get()).intValue(); j++) {
/* 168 */         class_2680 bs = this.mc.field_1687.method_8320((class_2338)this.blockPos.method_10102(x - (rad / 2 - i), y, z - (rad / 2 - j)));
/* 169 */         if (!bs.method_26215() && bs.method_26204() != class_2246.field_10164 && bs.method_26204() != class_2246.field_10382) {
/* 170 */           this.validBlocks.add(this.blockPos.method_25503());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     this.validBlocks.forEach(blockPos -> this.sortedBlocks.put(Double.valueOf(blockPos.method_10268(x, y, z, true)), blockPos));
/*     */ 
/*     */ 
/*     */     
/* 179 */     Map.Entry<Double, class_2338> firstEntry = this.sortedBlocks.firstEntry();
/*     */     
/* 181 */     if (firstEntry == null) {
/* 182 */       return null;
/*     */     }
/* 184 */     return class_243.method_24955((class_2382)firstEntry.getValue());
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/MonkeWalk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */