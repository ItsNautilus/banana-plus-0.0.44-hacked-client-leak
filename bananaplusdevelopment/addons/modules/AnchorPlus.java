/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.mixin.AbstractBlockAccessor;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.DoubleSetting;
/*     */ import minegame159.meteorclient.settings.IntSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ public class AnchorPlus extends Module {
/*  17 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  19 */   private final Setting<Integer> maxHeight = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/*  20 */       .name("max-height")
/*  21 */       .description("The maximum height Anchor will work at.")
/*  22 */       .defaultValue(10)
/*  23 */       .min(0)
/*  24 */       .max(255)
/*  25 */       .sliderMax(20)
/*  26 */       .build());
/*     */ 
/*     */   
/*  29 */   private final Setting<Integer> minPitch = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/*  30 */       .name("min-pitch")
/*  31 */       .description("The minimum pitch at which anchor will work.")
/*  32 */       .defaultValue(-90)
/*  33 */       .min(-90)
/*  34 */       .max(90)
/*  35 */       .sliderMin(-90)
/*  36 */       .sliderMax(90)
/*  37 */       .build());
/*     */ 
/*     */   
/*  40 */   private final Setting<Boolean> cancelMove = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  41 */       .name("cancel-jump-in-hole")
/*  42 */       .description("Prevents you from jumping when Anchor is active and Min Pitch is met.")
/*  43 */       .defaultValue(false)
/*  44 */       .build());
/*     */ 
/*     */   
/*  47 */   private final Setting<Boolean> pull = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  48 */       .name("pull")
/*  49 */       .description("The pull strength of Anchor.")
/*  50 */       .defaultValue(false)
/*  51 */       .build());
/*     */ 
/*     */ 
/*     */   
/*  55 */   private final Setting<Double> pullSpeed = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  56 */       .name("pull-speed")
/*  57 */       .description("How fast to pull towards the hole in blocks per second.")
/*  58 */       .defaultValue(0.3D)
/*  59 */       .min(0.0D)
/*  60 */       .sliderMax(5.0D)
/*  61 */       .build());
/*     */ 
/*     */   
/*  64 */   private final Setting<Boolean> whileJumping = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  65 */       .name("while-jumping")
/*  66 */       .description("Should anchor be active while jump is held.")
/*  67 */       .defaultValue(true)
/*  68 */       .build());
/*     */ 
/*     */ 
/*     */   
/*  72 */   private final class_2338.class_2339 blockPos = new class_2338.class_2339();
/*     */   private boolean wasInHole;
/*     */   private boolean foundHole;
/*     */   private int holeX;
/*     */   private int holeZ;
/*     */   public boolean cancelJump;
/*     */   public boolean controlMovement;
/*     */   public double deltaX;
/*     */   public double deltaZ;
/*     */   
/*     */   public AnchorPlus() {
/*  83 */     super(AddModule.BANANAPLUS, "anchor+", "Helps you get into holes by stopping your movement completely over a hole.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  88 */     this.wasInHole = false;
/*  89 */     this.holeX = this.holeZ = 0;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPreTick(TickEvent.Pre event) {
/*  94 */     this.cancelJump = (this.foundHole && ((Boolean)this.cancelMove.get()).booleanValue() && this.mc.field_1724.field_5965 >= ((Integer)this.minPitch.get()).intValue());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPostTick(TickEvent.Post event) {
/*  99 */     if (!((Boolean)this.whileJumping.get()).booleanValue() && 
/* 100 */       this.mc.field_1690.field_1903.method_1434()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 105 */     this.controlMovement = false;
/*     */     
/* 107 */     int x = class_3532.method_15357(this.mc.field_1724.method_23317());
/* 108 */     int y = class_3532.method_15357(this.mc.field_1724.method_23318());
/* 109 */     int z = class_3532.method_15357(this.mc.field_1724.method_23321());
/*     */     
/* 111 */     if (isHole(x, y, z)) {
/* 112 */       this.wasInHole = true;
/* 113 */       this.holeX = x;
/* 114 */       this.holeZ = z;
/*     */       
/*     */       return;
/*     */     } 
/* 118 */     if (this.wasInHole && this.holeX == x && this.holeZ == z)
/* 119 */       return;  if (this.wasInHole) this.wasInHole = false;
/*     */     
/* 121 */     if (this.mc.field_1724.field_5965 < ((Integer)this.minPitch.get()).intValue())
/*     */       return; 
/* 123 */     this.foundHole = false;
/* 124 */     double holeX = 0.0D;
/* 125 */     double holeZ = 0.0D;
/*     */     
/* 127 */     int i = 0;
/* 128 */     y--;
/* 129 */     for (; i < ((Integer)this.maxHeight.get()).intValue() && y > 0 && isAir(x, y, z); i++) {
/*     */       
/* 131 */       if (isHole(x, y, z)) {
/* 132 */         this.foundHole = true;
/* 133 */         holeX = x + 0.5D;
/* 134 */         holeZ = z + 0.5D;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 139 */     if (this.foundHole) {
/* 140 */       this.controlMovement = true;
/* 141 */       this.deltaX = Utils.clamp(holeX - this.mc.field_1724.method_23317(), -0.05D, 0.05D);
/* 142 */       this.deltaZ = Utils.clamp(holeZ - this.mc.field_1724.method_23321(), -0.05D, 0.05D);
/*     */       
/* 144 */       ((IVec3d)this.mc.field_1724.method_18798()).set(this.deltaX, (this.mc.field_1724.method_18798()).field_1351 - (((Boolean)this.pull.get()).booleanValue() ? ((Double)this.pullSpeed.get()).doubleValue() : 0.0D), this.deltaZ);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isHole(int x, int y, int z) {
/* 149 */     return (isHoleBlock(x, y - 1, z) && 
/* 150 */       isHoleBlock(x + 1, y, z) && 
/* 151 */       isHoleBlock(x - 1, y, z) && 
/* 152 */       isHoleBlock(x, y, z + 1) && 
/* 153 */       isHoleBlock(x, y, z - 1));
/*     */   }
/*     */   
/*     */   private boolean isHoleBlock(int x, int y, int z) {
/* 157 */     this.blockPos.method_10103(x, y, z);
/* 158 */     class_2248 block = this.mc.field_1687.method_8320((class_2338)this.blockPos).method_26204();
/* 159 */     return (block == class_2246.field_9987 || block == class_2246.field_10540 || block == class_2246.field_23152 || block == class_2246.field_22109 || block == class_2246.field_22423 || block == class_2246.field_10443 || block == class_2246.field_22108 || block == class_2246.field_10535 || block == class_2246.field_10414 || block == class_2246.field_10105);
/*     */   }
/*     */   
/*     */   private boolean isAir(int x, int y, int z) {
/* 163 */     this.blockPos.method_10103(x, y, z);
/* 164 */     return !((AbstractBlockAccessor)this.mc.field_1687.method_8320((class_2338)this.blockPos).method_26204()).isCollidable();
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/AnchorPlus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */