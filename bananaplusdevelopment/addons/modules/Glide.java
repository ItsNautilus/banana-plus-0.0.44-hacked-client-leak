/*     */ package bananaplusdevelopment.addons.modules;
/*     */ 
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import java.util.ArrayList;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.DoubleSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.modules.Module;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_3614;
/*     */ import net.minecraft.class_4970;
/*     */ 
/*     */ public class Glide
/*     */   extends Module
/*     */ {
/*  23 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  25 */   private final Setting<Double> fallSpeed = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  26 */       .name("fall-speed")
/*  27 */       .description("Fall Speed")
/*  28 */       .defaultValue(0.125D)
/*  29 */       .min(0.005D)
/*  30 */       .sliderMax(0.25D)
/*  31 */       .build());
/*     */ 
/*     */   
/*  34 */   private final Setting<Double> moveSpeed = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  35 */       .name("move-speed")
/*  36 */       .description("Horizontal movement factor.")
/*  37 */       .defaultValue(1.2D)
/*  38 */       .min(0.75D)
/*  39 */       .sliderMax(5.0D)
/*  40 */       .build());
/*     */ 
/*     */   
/*  43 */   private final Setting<Double> minHeight = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  44 */       .name("min-height")
/*  45 */       .description("Won't glide when you are too close to the ground.")
/*  46 */       .defaultValue(0.0D)
/*  47 */       .min(0.0D)
/*  48 */       .sliderMax(2.0D)
/*  49 */       .build());
/*     */ 
/*     */   
/*     */   public Glide() {
/*  53 */     super(AddModule.BANANAMINUS, "glide", "Makes you glide down slowly when falling.");
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/*  58 */     class_243 v = this.mc.field_1724.method_18798();
/*     */     
/*  60 */     if (this.mc.field_1724.method_24828() || this.mc.field_1724.method_5799() || this.mc.field_1724.method_5771() || this.mc.field_1724
/*  61 */       .method_6101() || v.field_1351 >= 0.0D) {
/*     */       return;
/*     */     }
/*  64 */     if (((Double)this.minHeight.get()).doubleValue() > 0.0D) {
/*     */       
/*  66 */       class_238 box = this.mc.field_1724.method_5829();
/*  67 */       box = box.method_991(box.method_989(0.0D, -((Double)this.minHeight.get()).doubleValue(), 0.0D));
/*  68 */       if (!this.mc.field_1687.method_18026(box)) {
/*     */         return;
/*     */       }
/*  71 */       class_2338 min = new class_2338(new class_243(box.field_1323, box.field_1322, box.field_1321));
/*     */       
/*  73 */       class_2338 max = new class_2338(new class_243(box.field_1320, box.field_1325, box.field_1324));
/*     */ 
/*     */       
/*  76 */       Stream<class_2338> stream = StreamSupport.stream(getAllInBox(min, max).spliterator(), true);
/*     */ 
/*     */       
/*  79 */       if (stream.map(this::getState).map(class_4970.class_4971::method_26207)
/*  80 */         .anyMatch(class_3614::method_15797)) {
/*     */         return;
/*     */       }
/*     */     } 
/*  84 */     this.mc.field_1724.method_18800(v.field_1352, Math.max(v.field_1351, -((Double)this.fallSpeed.get()).doubleValue()), v.field_1350);
/*  85 */     this.mc.field_1724.field_6281 = (float)(this.mc.field_1724.field_6281 * ((Double)this.moveSpeed.get()).doubleValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<class_2338> getAllInBox(class_2338 from, class_2338 to) {
/*  90 */     ArrayList<class_2338> blocks = new ArrayList<>();
/*     */ 
/*     */     
/*  93 */     class_2338 min = new class_2338(Math.min(from.method_10263(), to.method_10263()), Math.min(from.method_10264(), to.method_10264()), Math.min(from.method_10260(), to.method_10260()));
/*     */     
/*  95 */     class_2338 max = new class_2338(Math.max(from.method_10263(), to.method_10263()), Math.max(from.method_10264(), to.method_10264()), Math.max(from.method_10260(), to.method_10260()));
/*     */     
/*  97 */     for (int x = min.method_10263(); x <= max.method_10263(); x++) {
/*  98 */       for (int y = min.method_10264(); y <= max.method_10264(); y++) {
/*  99 */         for (int z = min.method_10260(); z <= max.method_10260(); z++)
/* 100 */           blocks.add(new class_2338(x, y, z)); 
/*     */       } 
/* 102 */     }  return blocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2680 getState(class_2338 pos) {
/* 107 */     return this.mc.field_1687.method_8320(pos);
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/Glide.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */