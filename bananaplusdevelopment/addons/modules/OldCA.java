/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.world.PlaySoundEvent;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.rendering.Renderer;
/*     */ import minegame159.meteorclient.rendering.ShapeMode;
/*     */ import minegame159.meteorclient.rendering.text.TextRenderer;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.ColorSetting;
/*     */ import minegame159.meteorclient.settings.DoubleSetting;
/*     */ import minegame159.meteorclient.settings.EnumSetting;
/*     */ import minegame159.meteorclient.settings.IntSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.utils.entity.EntityUtils;
/*     */ import minegame159.meteorclient.utils.entity.SortPriority;
/*     */ import minegame159.meteorclient.utils.entity.TargetUtils;
/*     */ import minegame159.meteorclient.utils.misc.Keybind;
/*     */ import minegame159.meteorclient.utils.misc.Vec3;
/*     */ import minegame159.meteorclient.utils.player.DamageCalcUtils;
/*     */ import minegame159.meteorclient.utils.player.PlayerUtils;
/*     */ import minegame159.meteorclient.utils.render.NametagUtils;
/*     */ import minegame159.meteorclient.utils.render.color.Color;
/*     */ import minegame159.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1511;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2879;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class OldCA extends Module {
/*     */   public enum RotationMode {
/*  49 */     Placing,
/*  50 */     Breaking,
/*  51 */     Both,
/*  52 */     None;
/*     */   }
/*     */   
/*     */   public enum CancelCrystalMode {
/*  56 */     Sound,
/*  57 */     Hit,
/*  58 */     None;
/*     */   }
/*     */   
/*  61 */   private final SettingGroup sgTarget = this.settings.createGroup("Target");
/*  62 */   private final SettingGroup sgPlace = this.settings.createGroup("Place");
/*  63 */   private final SettingGroup sgBreak = this.settings.createGroup("Break");
/*  64 */   private final SettingGroup sgPause = this.settings.createGroup("Pause");
/*  65 */   private final SettingGroup sgMisc = this.settings.createGroup("Misc");
/*  66 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*     */ 
/*     */ 
/*     */   
/*  70 */   private final Setting<Double> targetRange = this.sgTarget.add((Setting)(new DoubleSetting.Builder())
/*  71 */       .name("target-range")
/*  72 */       .description("The maximum range the entity can be to be targeted.")
/*  73 */       .defaultValue(7.0D)
/*  74 */       .min(0.0D)
/*  75 */       .sliderMax(10.0D)
/*  76 */       .build());
/*     */ 
/*     */   
/*  79 */   private final Setting<SortPriority> targetPriority = this.sgTarget.add((Setting)(new EnumSetting.Builder())
/*  80 */       .name("target-priority")
/*  81 */       .description("How to select the player to target.")
/*  82 */       .defaultValue((Enum)SortPriority.LowestHealth)
/*  83 */       .build());
/*     */ 
/*     */   
/*  86 */   private final Setting<Double> minDamage = this.sgTarget.add((Setting)(new DoubleSetting.Builder())
/*  87 */       .name("min-damage")
/*  88 */       .description("The minimum damage to deal.")
/*  89 */       .defaultValue(8.0D)
/*  90 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private final Setting<Integer> placeDelay = this.sgPlace.add((Setting)(new IntSetting.Builder())
/*  96 */       .name("place-delay")
/*  97 */       .description("The amount of delay in ticks before placing.")
/*  98 */       .defaultValue(2)
/*  99 */       .min(0)
/* 100 */       .sliderMax(10)
/* 101 */       .build());
/*     */ 
/*     */   
/* 104 */   private final Setting<Integer> placeRange = this.sgPlace.add((Setting)(new IntSetting.Builder())
/* 105 */       .name("place-range")
/* 106 */       .description("The radius in which crystals can be placed in.")
/* 107 */       .defaultValue(5)
/* 108 */       .min(0)
/* 109 */       .sliderMax(7)
/* 110 */       .build());
/*     */ 
/*     */   
/* 113 */   private final Setting<Integer> placeWallsRange = this.sgPlace.add((Setting)(new IntSetting.Builder())
/* 114 */       .name("place-walls-range")
/* 115 */       .description("The radius in which crystals can be placed through walls.")
/* 116 */       .defaultValue(3)
/* 117 */       .min(0)
/* 118 */       .sliderMax(7)
/* 119 */       .build());
/*     */ 
/*     */   
/* 122 */   private final Setting<Boolean> oldPlace = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/* 123 */       .name("1.12")
/* 124 */       .description("Won't place in one block holes for 1.12 crystal placements.")
/* 125 */       .defaultValue(false)
/* 126 */       .build());
/*     */ 
/*     */   
/* 129 */   private final Setting<Keybind> surroundBreak = this.sgPlace.add((Setting)(new KeybindSetting.Builder())
/* 130 */       .name("surround-break")
/* 131 */       .description("Forces you to place crystals next to the targets surround when the bind is held.")
/* 132 */       .defaultValue(Keybind.fromKey(-1))
/* 133 */       .build());
/*     */ 
/*     */   
/* 136 */   private final Setting<Boolean> facePlace = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/* 137 */       .name("face-place")
/* 138 */       .description("Will face-place when target is below a certain health or armor durability threshold.")
/* 139 */       .defaultValue(true)
/* 140 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> facePlaceHealth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> facePlaceDurability;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Keybind> forceFacePlace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> breakDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> breakRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> breakWallsRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<CancelCrystalMode> cancelCrystalMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> pauseAtHealth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> pauseOnEat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> pauseOnDrink;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> pauseOnMine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> autoSwitch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> antiWeakness;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<RotationMode> rotationMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> verticalRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> antiSuicide;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> maxSelfDamage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> swing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> render;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<ShapeMode> shapeMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> sideColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> lineColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> renderDamage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> damageScale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> damageColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> renderTimer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int placeDelayLeft;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int breakDelayLeft;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_1657 playerTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_2338 blockTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<class_2338, Double> crystalMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<Integer> removalQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OldCA() {
/* 356 */     super(AddModule.BANANAPLUS, "Old CA", "Automatically places and breaks crystals to damage other players."); Objects.requireNonNull(this.facePlace); this.facePlaceHealth = this.sgPlace.add((Setting)(new DoubleSetting.Builder()).name("face-place-health").description("The health required to face place.").defaultValue(8.0D).min(1.0D).max(36.0D).sliderMin(1.0D).sliderMax(36.0D).visible(this.facePlace::get).build()); Objects.requireNonNull(this.facePlace); this.facePlaceDurability = this.sgPlace.add((Setting)(new IntSetting.Builder()).name("face-place-durability").description("The durability threshold to face place.").defaultValue(2).min(1).max(100).sliderMin(0).sliderMax(100).visible(this.facePlace::get).build()); Objects.requireNonNull(this.facePlace); this.forceFacePlace = this.sgPlace.add((Setting)(new KeybindSetting.Builder()).name("force-face-place").description("Forces you to face place when the key is held.").defaultValue(Keybind.fromKey(-1)).visible(this.facePlace::get).build()); this.breakDelay = this.sgBreak.add((Setting)(new IntSetting.Builder()).name("break-delay").description("The amount of delay in ticks before breaking.").defaultValue(1).min(0).sliderMax(10).build()); this.breakRange = this.sgBreak.add((Setting)(new IntSetting.Builder()).name("break-range").description("The maximum range that crystals can be to be broken.").defaultValue(5).min(0).sliderMax(7).build()); this.breakWallsRange = this.sgBreak.add((Setting)(new IntSetting.Builder()).name("break-walls-range").description("The radius in which crystals can be broken through walls.").defaultValue(3).min(0).sliderMax(7).build()); this.cancelCrystalMode = this.sgBreak.add((Setting)(new EnumSetting.Builder()).name("cancel-mode").description("Mode to use for the crystals to be removed from the world.").defaultValue(CancelCrystalMode.Sound).build()); this.pauseAtHealth = this.sgPause.add((Setting)(new DoubleSetting.Builder()).name("pause-health").description("Pauses when you go below a certain health.").defaultValue(5.0D).min(0.0D).build()); this.pauseOnEat = this.sgPause.add((Setting)(new BoolSetting.Builder()).name("pause-on-eat").description("Pauses Crystal Aura while eating.").defaultValue(false).build()); this.pauseOnDrink = this.sgPause.add((Setting)(new BoolSetting.Builder()).name("pause-on-drink").description("Pauses Crystal Aura while drinking a potion.").defaultValue(false).build()); this.pauseOnMine = this.sgPause.add((Setting)(new BoolSetting.Builder()).name("pause-on-mine").description("Pauses Crystal Aura while mining blocks.").defaultValue(false).build()); this.autoSwitch = this.sgMisc.add((Setting)(new BoolSetting.Builder()).name("auto-switch").description("Switches to crystals automatically.").defaultValue(true).build()); this.antiWeakness = this.sgMisc.add((Setting)(new BoolSetting.Builder()).name("anti-weakness").description("Switches to tools to break crystals instead of your fist.").defaultValue(true).build()); this.rotationMode = this.sgMisc.add((Setting)(new EnumSetting.Builder()).name("rotation-mode").description("The method of rotating when using Crystal Aura.").defaultValue(RotationMode.Placing).build()); this.verticalRange = this.sgMisc.add((Setting)(new IntSetting.Builder()).name("vertical-range").description("The maximum vertical range for placing/breaking end crystals.").min(0).defaultValue(3).max(7).build()); this.antiSuicide = this.sgMisc.add((Setting)(new BoolSetting.Builder()).name("anti-suicide").description("Attempts to prevent you from killing yourself.").defaultValue(true).build()); this.maxSelfDamage = this.sgMisc.add((Setting)(new DoubleSetting.Builder()).name("max-self-damage").description("The maximum self-damage allowed.").defaultValue(8.0D).build()); this.swing = this.sgRender.add((Setting)(new BoolSetting.Builder()).name("swing").description("Renders your hand swinging client-side.").defaultValue(true).build()); this.render = this.sgRender.add((Setting)(new BoolSetting.Builder()).name("render").description("Renders the block under where it is placing a crystal.").defaultValue(true).build()); this.shapeMode = this.sgRender.add((Setting)(new EnumSetting.Builder()).name("shape-mode").description("How the shapes are rendered.").defaultValue((Enum)ShapeMode.Sides).build()); this.sideColor = this.sgRender.add((Setting)(new ColorSetting.Builder()).name("side-color").description("The side color.").defaultValue(new SettingColor(255, 255, 255, 75)).build()); this.lineColor = this.sgRender.add((Setting)(new ColorSetting.Builder()).name("line-color").description("The line color.").defaultValue(new SettingColor(255, 255, 255, 175)).build()); this.renderDamage = this.sgRender.add((Setting)(new BoolSetting.Builder()).name("damage-text").description("Renders text displaying the amount of damage the crystal will do.").defaultValue(true).build()); Objects.requireNonNull(this.renderDamage); this.damageScale = this.sgRender.add((Setting)(new DoubleSetting.Builder()).name("damage-text-scale").description("The scale of the damage text.").defaultValue(1.4D).min(0.0D).sliderMax(5.0D).visible(this.renderDamage::get).build()); Objects.requireNonNull(this.renderDamage);
/*     */     this.damageColor = this.sgRender.add((Setting)(new ColorSetting.Builder()).name("damage-color").description("The color of the damage text.").defaultValue(new SettingColor(255, 255, 255, 255)).visible(this.renderDamage::get).build());
/*     */     this.renderTimer = this.sgRender.add((Setting)(new IntSetting.Builder()).name("timer").description("The amount of time between changing the block render.").defaultValue(0).min(0).sliderMax(10).build());
/* 359 */     this.placeDelayLeft = ((Integer)this.placeDelay.get()).intValue();
/* 360 */     this.breakDelayLeft = ((Integer)this.breakDelay.get()).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     this.crystalMap = new HashMap<>();
/* 366 */     this.removalQueue = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 371 */     this.renderBlockPool = new Pool(() -> new RenderBlock());
/* 372 */     this.renderBlocks = new ArrayList<>();
/*     */ 
/*     */     
/* 375 */     this.broken = false;
/*     */   }
/*     */   private static final class_243 crystalPos = new class_243(0.0D, 0.0D, 0.0D); private static final class_243 hitPos = new class_243(0.0D, 0.0D, 0.0D); private final Pool<RenderBlock> renderBlockPool; private final List<RenderBlock> renderBlocks; private static final Vec3 renderPos = new Vec3(); private boolean broken;
/*     */   public void onActivate() {
/* 379 */     this.placeDelayLeft = 0;
/* 380 */     this.breakDelayLeft = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 385 */     for (RenderBlock renderBlock : this.renderBlocks) {
/* 386 */       this.renderBlockPool.free(renderBlock);
/*     */     }
/* 388 */     this.renderBlocks.clear();
/*     */   }
/*     */   
/*     */   @EventHandler(priority = 100)
/*     */   private void onTick(TickEvent.Pre event) {
/* 393 */     if (this.cancelCrystalMode.get() == CancelCrystalMode.Hit) {
/* 394 */       this.removalQueue.forEach(id -> this.mc.field_1687.method_2945(id.intValue()));
/* 395 */       this.removalQueue.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = 100)
/*     */   private void onPlaySound(PlaySoundEvent event) {
/* 401 */     if (event.sound.method_4774().method_14840().equals(class_3419.field_15245.method_14840()) && event.sound.method_4775().method_12832().equals("entity.generic.explode") && this.cancelCrystalMode.get() == CancelCrystalMode.Sound) {
/* 402 */       this.removalQueue.forEach(id -> this.mc.field_1687.method_2945(id.intValue()));
/* 403 */       this.removalQueue.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = 100)
/*     */   private void onTick(TickEvent.Post event) {
/* 410 */     for (Iterator<RenderBlock> it = this.renderBlocks.iterator(); it.hasNext(); ) {
/* 411 */       RenderBlock renderBlock = it.next();
/*     */       
/* 413 */       if (renderBlock.shouldRemove()) {
/* 414 */         it.remove();
/* 415 */         this.renderBlockPool.free(renderBlock);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 420 */     if (TargetUtils.isBadTarget(this.playerTarget, ((Double)this.targetRange.get()).doubleValue())) this.playerTarget = TargetUtils.getPlayerTarget(((Double)this.targetRange.get()).doubleValue(), (SortPriority)this.targetPriority.get()); 
/* 421 */     if (TargetUtils.isBadTarget(this.playerTarget, ((Double)this.targetRange.get()).doubleValue())) {
/*     */       return;
/*     */     }
/* 424 */     if (PlayerUtils.shouldPause(((Boolean)this.pauseOnMine.get()).booleanValue(), ((Boolean)this.pauseOnEat.get()).booleanValue(), ((Boolean)this.pauseOnDrink.get()).booleanValue()))
/* 425 */       return;  if (PlayerUtils.getTotalHealth() <= ((Double)this.pauseAtHealth.get()).doubleValue()) {
/*     */       return;
/*     */     }
/* 428 */     this.breakDelayLeft--;
/* 429 */     if (this.breakDelayLeft <= 0) {
/* 430 */       breakBest();
/* 431 */       if (this.broken) {
/*     */         return;
/*     */       }
/*     */     } 
/* 435 */     getAllValid();
/* 436 */     if (this.crystalMap.isEmpty()) {
/*     */       return;
/*     */     }
/* 439 */     findBestPos();
/* 440 */     if (this.blockTarget == null) {
/*     */       return;
/*     */     }
/*     */   }
/*     */   
/*     */   private void breakBest() {
/* 446 */     this.broken = false;
/* 447 */     Streams.stream(this.mc.field_1687.method_18112())
/* 448 */       .filter(this::validBreak)
/* 449 */       .max(Comparator.comparingDouble(o -> DamageCalcUtils.crystalDamage((class_1309)this.playerTarget, getCrystalPos(o.method_24515()))))
/* 450 */       .ifPresent(entity -> hitCrystal((class_1511)entity));
/*     */   }
/*     */   
/*     */   private void hitCrystal(class_1511 entity) {
/* 454 */     int preSlot = this.mc.field_1724.field_7514.field_7545;
/*     */ 
/*     */     
/* 457 */     int slot = this.mc.field_1724.field_7514.field_7545;
/*     */ 
/*     */     
/* 460 */     this.mc.field_1724.field_7514.field_7545 = slot;
/*     */     
/* 462 */     if (this.rotationMode.get() == RotationMode.Breaking || this.rotationMode.get() == RotationMode.Both) {
/* 463 */       float[] rotation = PlayerUtils.calculateAngle(entity.method_19538());
/* 464 */       Rotations.rotate(rotation[0], rotation[1], 30, () -> attackCrystal(entity));
/*     */     } else {
/* 466 */       attackCrystal(entity);
/*     */     } 
/*     */     
/* 469 */     this.mc.field_1724.field_7514.field_7545 = preSlot;
/*     */     
/* 471 */     this.removalQueue.add(Integer.valueOf(entity.method_5628()));
/* 472 */     this.broken = true;
/* 473 */     this.breakDelayLeft = ((Integer)this.breakDelay.get()).intValue();
/*     */   }
/*     */   
/*     */   private void attackCrystal(class_1511 entity) {
/* 477 */     this.mc.field_1761.method_2918((class_1657)this.mc.field_1724, (class_1297)entity);
/* 478 */     if (((Boolean)this.swing.get()).booleanValue()) { this.mc.field_1724.method_6104(class_1268.field_5808); }
/* 479 */     else { this.mc.field_1724.field_3944.method_2883((class_2596)new class_2879(class_1268.field_5808)); }
/*     */   
/*     */   }
/*     */   private boolean validBreak(class_1297 entity) {
/* 483 */     if (!(entity instanceof class_1511)) return false; 
/* 484 */     if (!entity.method_5805()) return false;
/*     */     
/* 486 */     if (PlayerUtils.canSeeEntity(entity))
/* 487 */     { if (PlayerUtils.distanceTo(entity) >= ((Integer)this.breakRange.get()).intValue()) return false;
/*     */        }
/* 489 */     else if (PlayerUtils.distanceTo(entity) >= ((Integer)this.breakWallsRange.get()).intValue()) { return false; }
/*     */ 
/*     */     
/* 492 */     if (DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, getCrystalPos(entity.method_24515())) >= ((Double)this.maxSelfDamage.get()).doubleValue()) return false; 
/* 493 */     if (((Boolean)this.antiSuicide.get()).booleanValue() && PlayerUtils.getTotalHealth() - DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, getCrystalPos(entity.method_24515())) <= 0.0D) return false; 
/* 494 */     return (shouldFacePlace() || ((Keybind)this.surroundBreak.get()).isPressed() || DamageCalcUtils.crystalDamage((class_1309)this.playerTarget, getCrystalPos(entity.method_24515())) >= ((Double)this.minDamage.get()).doubleValue());
/*     */   }
/*     */ 
/*     */   
/*     */   private void place(class_2350 direction, class_1268 hand) {
/* 499 */     this.mc.field_1724.field_3944.method_2883((class_2596)new class_2885(hand, new class_3965(hitPos, direction, this.blockTarget, false)));
/* 500 */     if (((Boolean)this.swing.get()).booleanValue()) { this.mc.field_1724.method_6104(hand); }
/* 501 */     else { this.mc.field_1724.field_3944.method_2883((class_2596)new class_2879(hand)); }
/*     */   
/*     */   }
/*     */   private boolean shouldFacePlace() {
/* 505 */     if (!((Boolean)this.facePlace.get()).booleanValue()) return false;
/*     */     
/* 507 */     if (EntityUtils.getTotalHealth(this.playerTarget) <= ((Double)this.facePlaceHealth.get()).doubleValue()) return true;
/*     */     
/* 509 */     for (class_1799 itemStack : this.playerTarget.method_5661()) {
/* 510 */       if (!itemStack.method_7960() && itemStack.method_7963() && (
/* 511 */         itemStack.method_7936() - itemStack.method_7919()) / itemStack.method_7936() * 100 <= ((Integer)this.facePlaceDurability.get()).intValue()) return true;
/*     */     
/*     */     } 
/* 514 */     return ((Keybind)this.forceFacePlace.get()).isPressed();
/*     */   }
/*     */   
/*     */   private void findBestPos() {
/* 518 */     double bestDamage = 0.0D;
/* 519 */     this.blockTarget = null;
/*     */     
/* 521 */     for (Map.Entry<class_2338, Double> blockPosDoubleEntry : this.crystalMap.entrySet()) {
/* 522 */       if (((Double)blockPosDoubleEntry.getValue()).doubleValue() > bestDamage) {
/* 523 */         bestDamage = ((Double)blockPosDoubleEntry.getValue()).doubleValue();
/* 524 */         if (((Double)blockPosDoubleEntry.getValue()).doubleValue() >= ((Double)this.minDamage.get()).doubleValue()) this.blockTarget = blockPosDoubleEntry.getKey(); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getAllValid() {
/* 530 */     this.crystalMap.clear();
/*     */     
/* 532 */     for (class_2338 blockPos : BlockUtils.getSphere(this.mc.field_1724.method_24515(), ((Integer)this.placeRange.get()).intValue(), ((Integer)this.verticalRange.get()).intValue())) {
/* 533 */       if (!validPlace(blockPos))
/* 534 */         continue;  this.crystalMap.put(blockPos, Double.valueOf(DamageCalcUtils.crystalDamage((class_1309)this.playerTarget, getCrystalPos(blockPos.method_10084()))));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean validPlace(class_2338 pos) {
/* 539 */     if (this.crystalMap.keySet().contains(pos)) return false;
/*     */ 
/*     */     
/* 542 */     class_2248 block = this.mc.field_1687.method_8320(pos).method_26204();
/* 543 */     if (block != class_2246.field_10540 && block != class_2246.field_9987) return false;
/*     */ 
/*     */     
/* 546 */     boolean canSee = (rayTrace(pos, false) != null);
/* 547 */     if (canSee)
/* 548 */     { if (PlayerUtils.distanceTo(pos) >= ((Integer)this.placeRange.get()).intValue()) return false;
/*     */        }
/* 550 */     else if (PlayerUtils.distanceTo(pos) >= ((Integer)this.placeWallsRange.get()).intValue()) { return false; }
/*     */ 
/*     */ 
/*     */     
/* 554 */     class_2338 crystalPos = pos.method_10084();
/* 555 */     if (notEmpty(crystalPos)) return false; 
/* 556 */     if (((Boolean)this.oldPlace.get()).booleanValue() && notEmpty(crystalPos.method_10084())) return false;
/*     */ 
/*     */     
/* 559 */     if (DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, getCrystalPos(crystalPos)) >= ((Double)this.maxSelfDamage.get()).doubleValue()) return false; 
/* 560 */     if (((Boolean)this.antiSuicide.get()).booleanValue() && PlayerUtils.getTotalHealth() - DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, getCrystalPos(crystalPos)) <= 0.0D) return false;
/*     */     
/* 562 */     if (shouldFacePlace()) {
/* 563 */       class_2338 targetHead = this.playerTarget.method_24515();
/*     */ 
/*     */       
/* 566 */       for (class_2350 direction : class_2350.values()) {
/* 567 */         if (direction != class_2350.field_11033 && direction != class_2350.field_11036)
/*     */         {
/*     */           
/* 570 */           if (pos.equals(targetHead.method_10093(direction))) return true; 
/*     */         }
/*     */       } 
/* 573 */     } else if (((Keybind)this.surroundBreak.get()).isPressed()) {
/* 574 */       class_2338 targetSurround = EntityUtils.getCityBlock(this.playerTarget);
/*     */       
/* 576 */       if (targetSurround != null)
/*     */       {
/* 578 */         for (class_2350 direction : class_2350.values()) {
/* 579 */           if (direction != class_2350.field_11033 && direction != class_2350.field_11036)
/*     */           {
/*     */             
/* 582 */             if (pos.equals(targetSurround.method_10074().method_10093(direction))) return true; 
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 587 */     return (DamageCalcUtils.crystalDamage((class_1309)this.playerTarget, getCrystalPos(crystalPos)) >= ((Double)this.minDamage.get()).doubleValue());
/*     */   }
/*     */ 
/*     */   
/*     */   private class_243 getCrystalPos(class_2338 blockPos) {
/* 592 */     ((IVec3d)crystalPos).set(blockPos.method_10263() + 0.5D, blockPos.method_10264() + 0.5D, blockPos.method_10260() + 0.5D);
/* 593 */     return crystalPos;
/*     */   }
/*     */   
/*     */   private boolean notEmpty(class_2338 pos) {
/* 597 */     return (!this.mc.field_1687.method_8320(pos).method_26215() || !this.mc.field_1687.method_8335(null, new class_238(pos.method_10263(), pos.method_10264(), pos.method_10260(), pos.method_10263() + 1.0D, pos.method_10264() + 2.0D, pos.method_10260() + 1.0D)).isEmpty());
/*     */   }
/*     */   
/*     */   private class_2350 rayTrace(class_2338 pos, boolean forceReturn) {
/* 601 */     class_243 eyesPos = new class_243(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + this.mc.field_1724.method_18381(this.mc.field_1724.method_18376()), this.mc.field_1724.method_23321());
/*     */     
/* 603 */     for (class_2350 direction : class_2350.values()) {
/*     */ 
/*     */       
/* 606 */       class_3959 raycastContext = new class_3959(eyesPos, new class_243(pos.method_10263() + 0.5D + direction.method_10163().method_10263() * 0.5D, pos.method_10264() + 0.5D + direction.method_10163().method_10264() * 0.5D, pos.method_10260() + 0.5D + direction.method_10163().method_10260() * 0.5D), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
/* 607 */       class_3965 result = this.mc.field_1687.method_17742(raycastContext);
/* 608 */       if (result != null && result.method_17783() == class_239.class_240.field_1332 && result.method_17777().equals(pos)) {
/* 609 */         return direction;
/*     */       }
/*     */     } 
/* 612 */     if (forceReturn) {
/* 613 */       if (pos.method_10264() > eyesPos.field_1351) {
/* 614 */         return class_2350.field_11033;
/*     */       }
/* 616 */       return class_2350.field_11036;
/*     */     } 
/* 618 */     return null;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(RenderEvent event) {
/* 623 */     if (!((Boolean)this.render.get()).booleanValue())
/*     */       return; 
/* 625 */     for (RenderBlock renderBlock : this.renderBlocks) {
/* 626 */       renderBlock.render3D();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender2D(Render2DEvent event) {
/* 632 */     if (!((Boolean)this.render.get()).booleanValue())
/*     */       return; 
/* 634 */     for (RenderBlock renderBlock : this.renderBlocks)
/* 635 */       renderBlock.render2D(); 
/*     */   }
/*     */   private class RenderBlock { private int x; private int y;
/*     */     private int z;
/*     */     private int timer;
/*     */     private double damage;
/*     */     
/*     */     private RenderBlock() {}
/*     */     
/*     */     public void reset(class_2338 pos) {
/* 645 */       this.x = class_3532.method_15375(pos.method_10263());
/* 646 */       this.y = class_3532.method_15375(pos.method_10264());
/* 647 */       this.z = class_3532.method_15375(pos.method_10260());
/* 648 */       this.timer = ((Integer)OldCA.this.renderTimer.get()).intValue();
/*     */     }
/*     */     
/*     */     public boolean shouldRemove() {
/* 652 */       if (this.timer <= 0) return true; 
/* 653 */       this.timer--;
/* 654 */       return false;
/*     */     }
/*     */     
/*     */     public void render3D() {
/* 658 */       Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, this.x, this.y, this.z, 1.0D, (Color)OldCA.this.sideColor.get(), (Color)OldCA.this.lineColor.get(), (ShapeMode)OldCA.this.shapeMode.get(), 0);
/*     */     }
/*     */     
/*     */     public void render2D() {
/* 662 */       if (((Boolean)OldCA.this.renderDamage.get()).booleanValue()) {
/* 663 */         OldCA.renderPos.set(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D);
/*     */         
/* 665 */         if (NametagUtils.to2D(OldCA.renderPos, ((Double)OldCA.this.damageScale.get()).doubleValue())) {
/* 666 */           NametagUtils.begin(OldCA.renderPos);
/* 667 */           TextRenderer.get().begin(1.0D, false, true);
/*     */           
/* 669 */           String damageText = String.valueOf(Math.round(this.damage * 100.0D) / 100.0D);
/*     */           
/* 671 */           double w = TextRenderer.get().getWidth(damageText) / 2.0D;
/*     */           
/* 673 */           TextRenderer.get().render(damageText, -w, 0.0D, (Color)OldCA.this.damageColor.get());
/*     */           
/* 675 */           TextRenderer.get().end();
/* 676 */           NametagUtils.end();
/*     */         } 
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInfoString() {
/* 684 */     if (this.playerTarget != null) return this.playerTarget.method_5820(); 
/* 685 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/OldCA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */