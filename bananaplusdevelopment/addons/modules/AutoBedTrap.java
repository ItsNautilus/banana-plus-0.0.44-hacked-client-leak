/*     */ package bananaplusdevelopment.addons.modules;
/*     */ 
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.IntSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.modules.Module;
/*     */ import minegame159.meteorclient.utils.player.FindItemResult;
/*     */ import minegame159.meteorclient.utils.player.InvUtils;
/*     */ import minegame159.meteorclient.utils.world.BlockUtils;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2244;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class AutoBedTrap extends Module {
/*  23 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  25 */   private final Setting<Integer> bpt = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/*  26 */       .name("blocks-per-tick")
/*  27 */       .description("How many blocks to place per tick")
/*  28 */       .defaultValue(2)
/*  29 */       .min(1)
/*  30 */       .sliderMax(8)
/*  31 */       .build());
/*     */ 
/*     */   
/*  34 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  35 */       .name("rotate")
/*  36 */       .description("Rotates when placing")
/*  37 */       .defaultValue(true)
/*  38 */       .build());
/*     */   
/*     */   class_2338 bed1;
/*     */   
/*     */   class_2350 bed2direction;
/*     */   class_2338 bed2;
/*  44 */   int cap = 0;
/*     */   boolean bed;
/*     */   
/*     */   public AutoBedTrap() {
/*  48 */     super(AddModule.BANANAMINUS, "auto-bed-trap", "Automatically places obsidian around bed");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  53 */     this.cap = 0;
/*  54 */     this.bed1 = null;
/*  55 */     if (this.mc.field_1765 == null) {
/*  56 */       error("Not looking at a bed. Disabling.", new Object[0]);
/*  57 */       toggle();
/*     */     } 
/*  59 */     this.bed1 = (this.mc.field_1765.method_17783() == class_239.class_240.field_1332) ? ((class_3965)this.mc.field_1765).method_17777() : null;
/*  60 */     if (this.bed1 == null || !(this.mc.field_1687.method_8320(this.bed1).method_26204() instanceof class_2244)) {
/*  61 */       error("Not looking at a bed. Disabling.", new Object[0]);
/*  62 */       toggle();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/*  68 */     this.bed2direction = class_2244.method_24163(this.mc.field_1687.method_8320(this.bed1));
/*  69 */     if (this.bed2direction == class_2350.field_11034) {
/*  70 */       this.bed2 = this.bed1.method_10089(1);
/*  71 */     } else if (this.bed2direction == class_2350.field_11043) {
/*  72 */       this.bed2 = this.bed1.method_10076(1);
/*  73 */     } else if (this.bed2direction == class_2350.field_11035) {
/*  74 */       this.bed2 = this.bed1.method_10077(1);
/*  75 */     } else if (this.bed2direction == class_2350.field_11039) {
/*  76 */       this.bed2 = this.bed1.method_10088(1);
/*     */     } 
/*     */     
/*  79 */     placeTickAround(this.bed1);
/*  80 */     placeTickAround(this.bed2);
/*     */   }
/*     */   
/*     */   public void placeTickAround(class_2338 block) {
/*  84 */     for (class_2338 b : new class_2338[] { block
/*  85 */         .method_10084(), block.method_10067(), block
/*  86 */         .method_10095(), block.method_10072(), block
/*  87 */         .method_10078(), block.method_10074() }) {
/*     */       
/*  89 */       if (this.cap >= ((Integer)this.bpt.get()).intValue()) {
/*  90 */         this.cap = 0;
/*     */         
/*     */         return;
/*     */       } 
/*  94 */       FindItemResult findBlock = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8281 });
/*  95 */       if (!findBlock.found()) {
/*  96 */         error("No specified blocks found. Disabling.", new Object[0]);
/*  97 */         toggle();
/*     */       } 
/*     */ 
/*     */       
/* 101 */       if (BlockUtils.place(b, findBlock, ((Boolean)this.rotate.get()).booleanValue(), 10, false)) {
/* 102 */         this.cap++;
/* 103 */         if (this.cap >= ((Integer)this.bpt.get()).intValue()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/* 108 */     this.cap = 0;
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/AutoBedTrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */