/*     */ package bananaplusdevelopment.addons.modules;
/*     */ 
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BlockListSetting;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.modules.Module;
/*     */ import minegame159.meteorclient.utils.player.InvUtils;
/*     */ import minegame159.meteorclient.utils.player.PlayerUtils;
/*     */ import minegame159.meteorclient.utils.world.BlockUtils;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ 
/*     */ public class AntiCevBreaker extends Module {
/*  22 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  24 */   private final Setting<Boolean> placeThingsIn = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  25 */       .name("place-things-in")
/*  26 */       .description("Places things in you.")
/*  27 */       .defaultValue(false)
/*  28 */       .build());
/*     */ 
/*     */   
/*  31 */   private final Setting<Boolean> placeThingsTop = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  32 */       .name("place-things-top")
/*  33 */       .description("Places things above you.")
/*  34 */       .defaultValue(false)
/*  35 */       .build());
/*     */ 
/*     */   
/*  38 */   private final Setting<Boolean> placeThingsTop2 = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  39 */       .name("place-things-2-top")
/*  40 */       .description("Places things 2 blocks on top.")
/*  41 */       .defaultValue(true)
/*  42 */       .build());
/*     */ 
/*     */   
/*  45 */   private final Setting<Boolean> placeThingsTop3 = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  46 */       .name("place-things-3-top")
/*  47 */       .description("Places things 3 blocks on top.")
/*  48 */       .defaultValue(false)
/*  49 */       .build());
/*     */ 
/*     */   
/*  52 */   private final Setting<Boolean> onlyInHole = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  53 */       .name("only-in-hole")
/*  54 */       .description("Only functions when you are standing in a hole.")
/*  55 */       .defaultValue(true)
/*  56 */       .build());
/*     */ 
/*     */ 
/*     */   
/*  60 */   private final Setting<List<class_2248>> blocks = this.sgGeneral.add((Setting)(new BlockListSetting.Builder())
/*  61 */       .name("block")
/*  62 */       .description("What blocks to use for Anti Cev Breaker.")
/*  63 */       .defaultValue(Collections.singletonList(class_2246.field_10589))
/*  64 */       .filter(this::blockFilter)
/*  65 */       .build());
/*     */ 
/*     */   
/*     */   public AntiCevBreaker() {
/*  69 */     super(AddModule.BANANAPLUS, "anti-cev-breaker", "Places buttons,pressure plates, strings to prevent you getting memed on.");
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/*  74 */     if (((Boolean)this.onlyInHole.get()).booleanValue() && !PlayerUtils.isInHole(true)) {
/*     */       return;
/*     */     }
/*  77 */     class_2338 head = this.mc.field_1724.method_24515().method_10084();
/*     */ 
/*     */ 
/*     */     
/*  81 */     if (((Boolean)this.placeThingsIn.get()).booleanValue()) place(this.mc.field_1724.method_24515().method_10086(1)); 
/*  82 */     if (((Boolean)this.placeThingsTop.get()).booleanValue()) place(this.mc.field_1724.method_24515().method_10086(2)); 
/*  83 */     if (((Boolean)this.placeThingsTop2.get()).booleanValue()) place(this.mc.field_1724.method_24515().method_10086(3)); 
/*  84 */     if (((Boolean)this.placeThingsTop3.get()).booleanValue()) place(this.mc.field_1724.method_24515().method_10086(4)); 
/*     */   }
/*     */   
/*     */   private boolean blockFilter(class_2248 block) {
/*  88 */     return (block == class_2246.field_10397 || block == class_2246.field_10592 || block == class_2246.field_22130 || block == class_2246.field_10470 || block == class_2246.field_10026 || block == class_2246.field_10484 || block == class_2246.field_23863 || block == class_2246.field_10332 || block == class_2246.field_10158 || block == class_2246.field_22131 || block == class_2246.field_10278 || block == class_2246.field_10417 || block == class_2246.field_22100 || block == class_2246.field_10493 || block == class_2246.field_10553 || block == class_2246.field_10057 || block == class_2246.field_23864 || block == class_2246.field_10066 || block == class_2246.field_10494 || block == class_2246.field_22101 || block == class_2246.field_10589);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void place(class_2338 blockPos) {
/* 112 */     if (BlockUtils.place(blockPos, InvUtils.findInHotbar(itemStack -> ((List)this.blocks.get()).contains(class_2248.method_9503(itemStack.method_7909()))), 50, false));
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/AntiCevBreaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */