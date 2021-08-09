/*      */ package bananaplusdevelopment.addons.modules;
/*      */ import com.google.common.util.concurrent.AtomicDouble;
/*      */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*      */ import it.unimi.dsi.fastutil.ints.IntSet;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import meteordevelopment.orbit.EventHandler;
/*      */ import minegame159.meteorclient.events.entity.EntityAddedEvent;
/*      */ import minegame159.meteorclient.events.entity.EntityRemovedEvent;
/*      */ import minegame159.meteorclient.events.packets.PacketEvent;
/*      */ import minegame159.meteorclient.mixininterface.IVec3d;
/*      */ import minegame159.meteorclient.rendering.Renderer;
/*      */ import minegame159.meteorclient.rendering.ShapeMode;
/*      */ import minegame159.meteorclient.rendering.text.TextRenderer;
/*      */ import minegame159.meteorclient.settings.BoolSetting;
/*      */ import minegame159.meteorclient.settings.DoubleSetting;
/*      */ import minegame159.meteorclient.settings.EnumSetting;
/*      */ import minegame159.meteorclient.settings.IntSetting;
/*      */ import minegame159.meteorclient.settings.Setting;
/*      */ import minegame159.meteorclient.settings.SettingGroup;
/*      */ import minegame159.meteorclient.utils.entity.EntityUtils;
/*      */ import minegame159.meteorclient.utils.misc.Keybind;
/*      */ import minegame159.meteorclient.utils.player.DamageUtils;
/*      */ import minegame159.meteorclient.utils.player.FindItemResult;
/*      */ import minegame159.meteorclient.utils.player.InvUtils;
/*      */ import minegame159.meteorclient.utils.player.Rotations;
/*      */ import minegame159.meteorclient.utils.render.NametagUtils;
/*      */ import minegame159.meteorclient.utils.render.color.Color;
/*      */ import minegame159.meteorclient.utils.render.color.SettingColor;
/*      */ import minegame159.meteorclient.utils.world.BlockIterator;
/*      */ import net.minecraft.class_1268;
/*      */ import net.minecraft.class_1293;
/*      */ import net.minecraft.class_1297;
/*      */ import net.minecraft.class_1657;
/*      */ import net.minecraft.class_1792;
/*      */ import net.minecraft.class_1799;
/*      */ import net.minecraft.class_1802;
/*      */ import net.minecraft.class_1832;
/*      */ import net.minecraft.class_2246;
/*      */ import net.minecraft.class_2338;
/*      */ import net.minecraft.class_2350;
/*      */ import net.minecraft.class_238;
/*      */ import net.minecraft.class_2382;
/*      */ import net.minecraft.class_243;
/*      */ import net.minecraft.class_2596;
/*      */ import net.minecraft.class_2680;
/*      */ import net.minecraft.class_2818;
/*      */ import net.minecraft.class_3509;
/*      */ import net.minecraft.class_3532;
/*      */ import net.minecraft.class_3959;
/*      */ import net.minecraft.class_3965;
/*      */ 
/*      */ public class ExtraCA extends Module {
/*      */   public enum YawStepMode {
/*   58 */     Break,
/*   59 */     All;
/*      */   }
/*      */   
/*      */   public enum AutoSwitchMode {
/*   63 */     Normal,
/*   64 */     Silent,
/*   65 */     None;
/*      */   }
/*      */   
/*      */   public enum SupportMode {
/*   69 */     Disabled,
/*   70 */     Accurate,
/*   71 */     Fast;
/*      */   }
/*      */   
/*   74 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*   75 */   private final SettingGroup sgPlace = this.settings.createGroup("Place");
/*   76 */   private final SettingGroup sgFacePlace = this.settings.createGroup("Face Place");
/*   77 */   private final SettingGroup sgBreak = this.settings.createGroup("Break");
/*   78 */   private final SettingGroup sgPause = this.settings.createGroup("Pause");
/*   79 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*      */ 
/*      */ 
/*      */   
/*   83 */   private final Setting<Double> targetRange = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*   84 */       .name("target-range")
/*   85 */       .description("Range in which to target players.")
/*   86 */       .defaultValue(10.0D)
/*   87 */       .min(0.0D)
/*   88 */       .sliderMax(16.0D)
/*   89 */       .build());
/*      */ 
/*      */   
/*   92 */   private final Setting<Boolean> predictMovement = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*   93 */       .name("predict-movement")
/*   94 */       .description("Predicts target movement.")
/*   95 */       .defaultValue(false)
/*   96 */       .build());
/*      */ 
/*      */   
/*   99 */   private final Setting<Boolean> ignoreTerrain = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  100 */       .name("ignore-terrain")
/*  101 */       .description("Completely ignores terrain if it can be blown up by end crystals.")
/*  102 */       .defaultValue(true)
/*  103 */       .build());
/*      */ 
/*      */ 
/*      */   
/*  107 */   private final Setting<Double> minDamage = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  108 */       .name("min-damage")
/*  109 */       .description("Minimum damage the crystal needs to deal to your target.")
/*  110 */       .defaultValue(6.0D)
/*  111 */       .min(0.0D)
/*  112 */       .build());
/*      */ 
/*      */   
/*  115 */   private final Setting<Double> maxDamage = this.sgGeneral.add((Setting)(new DoubleSetting.Builder())
/*  116 */       .name("max-damage")
/*  117 */       .description("Maximum damage crystals can deal to yourself.")
/*  118 */       .defaultValue(6.0D)
/*  119 */       .min(0.0D)
/*  120 */       .max(36.0D)
/*  121 */       .sliderMax(36.0D)
/*  122 */       .build());
/*      */ 
/*      */   
/*  125 */   private final Setting<AutoSwitchMode> autoSwitch = this.sgGeneral.add((Setting)(new EnumSetting.Builder())
/*  126 */       .name("auto-switch")
/*  127 */       .description("Switches to crystals in your hotbar once a target is found.")
/*  128 */       .defaultValue(AutoSwitchMode.Normal)
/*  129 */       .build());
/*      */ 
/*      */   
/*  132 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  133 */       .name("rotate")
/*  134 */       .description("Rotates server-side towards the crystals being hit/placed.")
/*  135 */       .defaultValue(true)
/*  136 */       .build());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<YawStepMode> yawStepMode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Double> yawSteps;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> antiSuicide;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> doPlace;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Integer> placeDelay;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Double> placeRange;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Double> placeWallsRange;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> placement112;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<SupportMode> support;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Integer> supportDelay;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> facePlace;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Double> facePlaceHealth;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Double> facePlaceDurability;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> facePlaceArmor;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Keybind> forceFacePlace;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> doBreak;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Integer> breakDelay;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> smartDelay;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Integer> switchDelay;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Double> breakRange;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Double> breakWallsRange;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> onlyBreakOwn;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Integer> breakAttempts;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Integer> minimumCrystalAge;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> fastBreak;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> antiWeakness;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> eatPause;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> drinkPause;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> minePause;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> renderSwing;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> render;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> renderBreak;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<ShapeMode> shapeMode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<SettingColor> sideColor;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<SettingColor> lineColor;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Boolean> renderDamageText;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Double> damageTextScale;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Integer> renderTime;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Setting<Integer> renderBreakTime;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int breakTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int placeTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int switchTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final List<class_1657> targets;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class_243 vec3d;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class_243 playerEyePos;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Vec3 vec3;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class_2338.class_2339 blockPos;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class_238 box;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class_243 vec3dRayTraceEnd;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class_3959 raycastContext;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final IntSet placedCrystals;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean placing;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int placingTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class_2338.class_2339 placingCrystalBlockPos;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final IntSet removed;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Int2IntMap attemptedBreaks;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Int2IntMap waitingToExplode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double serverYaw;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class_1657 bestTarget;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double bestTargetDamage;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int bestTargetTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean didRotateThisTick;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLastRotationPos;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class_243 lastRotationPos;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double lastYaw;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double lastPitch;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lastRotationTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int breakRenderTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class_2338.class_2339 renderPos;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class_2338.class_2339 breakRenderPos;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double renderDamage;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExtraCA() {
/*  507 */     super(AddModule.BANANAPLUS, "Extra CA", "Automatically places and attacks crystals."); Objects.requireNonNull(this.rotate); this.yawStepMode = this.sgGeneral.add((Setting)(new EnumSetting.Builder()).name("yaw-steps-mode").description("When to run the yaw steps check.").defaultValue(YawStepMode.Break).visible(this.rotate::get).build()); Objects.requireNonNull(this.rotate); this.yawSteps = this.sgGeneral.add((Setting)(new DoubleSetting.Builder()).name("yaw-steps").description("Maximum number of degrees its allowed to rotate in one tick.").defaultValue(180.0D).min(1.0D).max(180.0D).sliderMin(1.0D).sliderMax(180.0D).visible(this.rotate::get).build()); this.antiSuicide = this.sgGeneral.add((Setting)(new BoolSetting.Builder()).name("anti-suicide").description("Will not place and break crystals if they will kill you.").defaultValue(true).build()); this.doPlace = this.sgPlace.add((Setting)(new BoolSetting.Builder()).name("place").description("If the CA should place crystals.").defaultValue(true).build()); this.placeDelay = this.sgPlace.add((Setting)(new IntSetting.Builder()).name("place-delay").description("The delay in ticks to wait to place a crystal after it's exploded.").defaultValue(0).min(0).sliderMin(0).sliderMax(20).build()); this.placeRange = this.sgPlace.add((Setting)(new DoubleSetting.Builder()).name("place-range").description("Range in which to place crystals.").defaultValue(4.5D).min(0.0D).sliderMax(6.0D).build()); this.placeWallsRange = this.sgPlace.add((Setting)(new DoubleSetting.Builder()).name("place-walls-range").description("Range in which to place crystals when behind blocks.").defaultValue(3.5D).min(0.0D).sliderMax(6.0D).build()); this.placement112 = this.sgPlace.add((Setting)(new BoolSetting.Builder()).name("1.12-placement").description("Uses 1.12 crystal placement.").defaultValue(false).build()); this.support = this.sgPlace.add((Setting)(new EnumSetting.Builder()).name("support").description("Places a support block in air if no other position have been found.").defaultValue(SupportMode.Disabled).build()); this.supportDelay = this.sgPlace.add((Setting)(new IntSetting.Builder()).name("support-delay").description("Delay in ticks after placing support block.").defaultValue(1).min(0).visible(() -> (this.support.get() != SupportMode.Disabled)).build()); this.facePlace = this.sgFacePlace.add((Setting)(new BoolSetting.Builder()).name("face-place").description("Will face-place when target is below a certain health or armor durability threshold.").defaultValue(true).build()); Objects.requireNonNull(this.facePlace); this.facePlaceHealth = this.sgFacePlace.add((Setting)(new DoubleSetting.Builder()).name("face-place-health").description("The health the target has to be at to start face placing.").defaultValue(8.0D).min(1.0D).sliderMin(1.0D).sliderMax(36.0D).visible(this.facePlace::get).build()); Objects.requireNonNull(this.facePlace); this.facePlaceDurability = this.sgFacePlace.add((Setting)(new DoubleSetting.Builder()).name("face-place-durability").description("The durability threshold percentage to be able to face-place.").defaultValue(2.0D).min(1.0D).sliderMin(1.0D).sliderMax(100.0D).visible(this.facePlace::get).build()); Objects.requireNonNull(this.facePlace); this.facePlaceArmor = this.sgFacePlace.add((Setting)(new BoolSetting.Builder()).name("face-place-missing-armor").description("Automatically starts face placing when a target misses a piece of armor.").defaultValue(false).visible(this.facePlace::get).build()); this.forceFacePlace = this.sgFacePlace.add((Setting)(new KeybindSetting.Builder()).name("force-face-place").description("Starts face place when this button is pressed.").defaultValue(Keybind.fromKey(-1)).build()); this.doBreak = this.sgBreak.add((Setting)(new BoolSetting.Builder()).name("break").description("If the CA should break crystals.").defaultValue(true).build()); this.breakDelay = this.sgBreak.add((Setting)(new IntSetting.Builder()).name("break-delay").description("The delay in ticks to wait to break a crystal after it's placed.").defaultValue(0).min(0).sliderMin(0).sliderMax(20).build()); this.smartDelay = this.sgBreak.add((Setting)(new BoolSetting.Builder()).name("smart-delay").description("Only breaks crystals when the target can receive damage.").defaultValue(false).build()); this.switchDelay = this.sgBreak.add((Setting)(new IntSetting.Builder()).name("switch-delay").description("The delay in ticks to wait to break a crystal after switching hotbar slot.").defaultValue(0).min(0).sliderMax(10).build()); this.breakRange = this.sgBreak.add((Setting)(new DoubleSetting.Builder()).name("break-range").description("Range in which to break crystals.").defaultValue(4.5D).min(0.0D).sliderMax(6.0D).build()); this.breakWallsRange = this.sgBreak.add((Setting)(new DoubleSetting.Builder()).name("break-walls-range").description("Range in which to break crystals when behind blocks.").defaultValue(3.0D).min(0.0D).sliderMax(6.0D).build()); this.onlyBreakOwn = this.sgBreak.add((Setting)(new BoolSetting.Builder()).name("only-own").description("Only breaks own crystals.").defaultValue(false).build()); this.breakAttempts = this.sgBreak.add((Setting)(new IntSetting.Builder()).name("break-attempts").description("How many times to hit a crystal before stopping to target it.").defaultValue(2).sliderMin(1).sliderMax(5).build()); this.minimumCrystalAge = this.sgBreak.add((Setting)(new IntSetting.Builder()).name("minimum-crystal-age").description("How many ticks the crystal needs to exist in a world before trying to attack it.").defaultValue(0).min(0).build()); this.fastBreak = this.sgBreak.add((Setting)(new BoolSetting.Builder()).name("fast-break").description("Ignores break delay and tries to break the crystal as soon as it's spawned in the world.").defaultValue(true).build()); this.antiWeakness = this.sgBreak.add((Setting)(new BoolSetting.Builder()).name("anti-weakness").description("Switches to tools with high enough damage to explode the crystal with weakness effect.").defaultValue(true).build()); this.eatPause = this.sgPause.add((Setting)(new BoolSetting.Builder()).name("pause-on-eat").description("Pauses Crystal Aura when eating.").defaultValue(true).build()); this.drinkPause = this.sgPause.add((Setting)(new BoolSetting.Builder()).name("pause-on-drink").description("Pauses Crystal Aura when drinking.").defaultValue(true).build()); this.minePause = this.sgPause.add((Setting)(new BoolSetting.Builder()).name("pause-on-mine").description("Pauses Crystal Aura when mining.").defaultValue(false).build()); this.renderSwing = this.sgRender.add((Setting)(new BoolSetting.Builder()).name("swing").description("Renders hand swinging client side.").defaultValue(true).build()); this.render = this.sgRender.add((Setting)(new BoolSetting.Builder()).name("render").description("Renders a block overlay over the block the crystals are being placed on.").defaultValue(true).build()); this.renderBreak = this.sgRender.add((Setting)(new BoolSetting.Builder()).name("break").description("Renders a block overlay over the block the crystals are broken on.").defaultValue(false).build()); this.shapeMode = this.sgRender.add((Setting)(new EnumSetting.Builder()).name("shape-mode").description("How the shapes are rendered.").defaultValue((Enum)ShapeMode.Both).build()); this.sideColor = this.sgRender.add((Setting)(new ColorSetting.Builder()).name("side-color").description("The side color of the block overlay.").defaultValue(new SettingColor(255, 255, 255, 45)).build()); this.lineColor = this.sgRender.add((Setting)(new ColorSetting.Builder()).name("line-color").description("The line color of the block overlay.").defaultValue(new SettingColor(255, 255, 255, 255)).build()); this.renderDamageText = this.sgRender.add((Setting)(new BoolSetting.Builder()).name("damage").description("Renders crystal damage text in the block overlay.").defaultValue(true).build()); Objects.requireNonNull(this.renderDamageText); this.damageTextScale = this.sgRender.add((Setting)(new DoubleSetting.Builder()).name("damage-scale").description("How big the damage text should be.").defaultValue(1.25D).min(1.0D).sliderMax(4.0D).visible(this.renderDamageText::get).build()); this.renderTime = this.sgRender.add((Setting)(new IntSetting.Builder()).name("render-time").description("How long to render for.").defaultValue(10).min(0).sliderMax(20).build()); Objects.requireNonNull(this.renderBreak); this.renderBreakTime = this.sgRender.add((Setting)(new IntSetting.Builder()).name("break-time").description("How long to render breaking for.").defaultValue(13).min(0).sliderMax(20).visible(this.renderBreak::get).build()); this.targets = new ArrayList<>(); this.vec3d = new class_243(0.0D, 0.0D, 0.0D); this.playerEyePos = new class_243(0.0D, 0.0D, 0.0D); this.vec3 = new Vec3(); this.blockPos = new class_2338.class_2339(); this.box = new class_238(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D); this.vec3dRayTraceEnd = new class_243(0.0D, 0.0D, 0.0D); this.placedCrystals = (IntSet)new IntOpenHashSet(); this.placingCrystalBlockPos = new class_2338.class_2339(); this.removed = (IntSet)new IntOpenHashSet();
/*      */     this.attemptedBreaks = (Int2IntMap)new Int2IntOpenHashMap();
/*      */     this.waitingToExplode = (Int2IntMap)new Int2IntOpenHashMap();
/*      */     this.lastRotationPos = new class_243(0.0D, 0.0D, 0.0D);
/*      */     this.renderPos = new class_2338.class_2339();
/*  512 */     this.breakRenderPos = new class_2338.class_2339(); } public void onActivate() { this.breakTimer = 0;
/*  513 */     this.placeTimer = 0;
/*      */     
/*  515 */     this.raycastContext = new class_3959(new class_243(0.0D, 0.0D, 0.0D), new class_243(0.0D, 0.0D, 0.0D), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
/*      */     
/*  517 */     this.placing = false;
/*  518 */     this.placingTimer = 0;
/*      */     
/*  520 */     this.serverYaw = this.mc.field_1724.field_6031;
/*      */     
/*  522 */     this.bestTargetDamage = 0.0D;
/*  523 */     this.bestTargetTimer = 0;
/*      */     
/*  525 */     this.lastRotationTimer = getLastRotationStopDelay();
/*      */     
/*  527 */     this.renderTimer = 0;
/*  528 */     this.breakRenderTimer = 0; }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeactivate() {
/*  533 */     this.targets.clear();
/*      */     
/*  535 */     this.placedCrystals.clear();
/*      */     
/*  537 */     this.attemptedBreaks.clear();
/*  538 */     this.waitingToExplode.clear();
/*      */     
/*  540 */     this.removed.clear();
/*      */     
/*  542 */     this.bestTarget = null;
/*      */   }
/*      */   
/*      */   private int getLastRotationStopDelay() {
/*  546 */     return Math.max(10, ((Integer)this.placeDelay.get()).intValue() / 2 + ((Integer)this.breakDelay.get()).intValue() / 2 + 10);
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler(priority = 100)
/*      */   private void onPreTick(TickEvent.Pre event) {
/*  552 */     this.didRotateThisTick = false;
/*  553 */     this.lastRotationTimer++;
/*      */ 
/*      */     
/*  556 */     if (this.placing) {
/*  557 */       if (this.placingTimer > 0) { this.placingTimer--; }
/*  558 */       else { this.placing = false; }
/*      */     
/*      */     }
/*      */     
/*  562 */     if (this.bestTargetTimer > 0) this.bestTargetTimer--; 
/*  563 */     this.bestTargetDamage = 0.0D;
/*      */ 
/*      */     
/*  566 */     if (this.breakTimer > 0) this.breakTimer--; 
/*  567 */     if (this.placeTimer > 0) this.placeTimer--; 
/*  568 */     if (this.switchTimer > 0) this.switchTimer--;
/*      */ 
/*      */     
/*  571 */     if (this.renderTimer > 0) this.renderTimer--; 
/*  572 */     if (this.breakRenderTimer > 0) this.breakRenderTimer--;
/*      */ 
/*      */     
/*  575 */     for (IntIterator it = this.waitingToExplode.keySet().iterator(); it.hasNext(); ) {
/*  576 */       int id = it.nextInt();
/*  577 */       int ticks = this.waitingToExplode.get(id);
/*      */       
/*  579 */       if (ticks > 3) {
/*  580 */         it.remove();
/*  581 */         this.removed.remove(id);
/*      */         continue;
/*      */       } 
/*  584 */       this.waitingToExplode.put(id, ticks + 1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  589 */     if (PlayerUtils.shouldPause(((Boolean)this.minePause.get()).booleanValue(), ((Boolean)this.eatPause.get()).booleanValue(), ((Boolean)this.drinkPause.get()).booleanValue())) {
/*      */       return;
/*      */     }
/*  592 */     ((IVec3d)this.playerEyePos).set((this.mc.field_1724.method_19538()).field_1352, (this.mc.field_1724.method_19538()).field_1351 + this.mc.field_1724.method_18381(this.mc.field_1724.method_18376()), (this.mc.field_1724.method_19538()).field_1350);
/*      */ 
/*      */     
/*  595 */     findTargets();
/*      */     
/*  597 */     if (this.targets.size() > 0) {
/*  598 */       if (!this.didRotateThisTick) doBreak(); 
/*  599 */       if (!this.didRotateThisTick) doPlace();
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler(priority = -866)
/*      */   private void onPreTickLast(TickEvent.Pre event) {
/*  606 */     if (((Boolean)this.rotate.get()).booleanValue() && this.lastRotationTimer < getLastRotationStopDelay() && !this.didRotateThisTick) {
/*  607 */       Rotations.rotate(this.isLastRotationPos ? Rotations.getYaw(this.lastRotationPos) : this.lastYaw, this.isLastRotationPos ? Rotations.getPitch(this.lastRotationPos) : this.lastPitch, -100, null);
/*      */     }
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   private void onEntityAdded(EntityAddedEvent event) {
/*  613 */     if (!(event.entity instanceof net.minecraft.class_1511))
/*      */       return; 
/*  615 */     if (this.placing && event.entity.method_24515().equals(this.placingCrystalBlockPos)) {
/*  616 */       this.placing = false;
/*  617 */       this.placingTimer = 0;
/*  618 */       this.placedCrystals.add(event.entity.method_5628());
/*      */     } 
/*      */     
/*  621 */     if (((Boolean)this.fastBreak.get()).booleanValue() && !this.didRotateThisTick) {
/*  622 */       double damage = getBreakDamage(event.entity, true);
/*  623 */       if (damage > ((Double)this.minDamage.get()).doubleValue()) doBreak(event.entity); 
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   private void onEntityRemoved(EntityRemovedEvent event) {
/*  629 */     if (event.entity instanceof net.minecraft.class_1511) {
/*  630 */       this.placedCrystals.remove(event.entity.method_5628());
/*  631 */       this.removed.remove(event.entity.method_5628());
/*  632 */       this.waitingToExplode.remove(event.entity.method_5628());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setRotation(boolean isPos, class_243 pos, double yaw, double pitch) {
/*  637 */     this.didRotateThisTick = true;
/*  638 */     this.isLastRotationPos = isPos;
/*      */     
/*  640 */     if (isPos) { ((IVec3d)this.lastRotationPos).set(pos.field_1352, pos.field_1351, pos.field_1350); }
/*      */     else
/*  642 */     { this.lastYaw = yaw;
/*  643 */       this.lastPitch = pitch; }
/*      */ 
/*      */     
/*  646 */     this.lastRotationTimer = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void doBreak() {
/*  652 */     if (!((Boolean)this.doBreak.get()).booleanValue() || this.breakTimer > 0 || this.switchTimer > 0)
/*      */       return; 
/*  654 */     double bestDamage = 0.0D;
/*  655 */     class_1297 crystal = null;
/*      */ 
/*      */     
/*  658 */     for (class_1297 entity : this.mc.field_1687.method_18112()) {
/*  659 */       double damage = getBreakDamage(entity, true);
/*      */       
/*  661 */       if (damage > bestDamage) {
/*  662 */         bestDamage = damage;
/*  663 */         crystal = entity;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  668 */     if (crystal != null) doBreak(crystal); 
/*      */   }
/*      */   
/*      */   private double getBreakDamage(class_1297 entity, boolean checkCrystalAge) {
/*  672 */     if (!(entity instanceof net.minecraft.class_1511)) return 0.0D;
/*      */ 
/*      */     
/*  675 */     if (((Boolean)this.onlyBreakOwn.get()).booleanValue() && !this.placedCrystals.contains(entity.method_5628())) return 0.0D;
/*      */ 
/*      */     
/*  678 */     if (this.removed.contains(entity.method_5628())) return 0.0D;
/*      */ 
/*      */     
/*  681 */     if (this.attemptedBreaks.get(entity.method_5628()) > ((Integer)this.breakAttempts.get()).intValue()) return 0.0D;
/*      */ 
/*      */     
/*  684 */     if (checkCrystalAge && entity.field_6012 < ((Integer)this.minimumCrystalAge.get()).intValue()) return 0.0D;
/*      */ 
/*      */     
/*  687 */     if (isOutOfRange(entity.method_19538(), entity.method_24515(), false)) return 0.0D;
/*      */ 
/*      */     
/*  690 */     this.blockPos.method_10101((class_2382)entity.method_24515()).method_10100(0, -1, 0);
/*  691 */     double selfDamage = DamageUtils.crystalDamage((class_1657)this.mc.field_1724, entity.method_19538(), ((Boolean)this.predictMovement.get()).booleanValue(), this.raycastContext, (class_2338)this.blockPos, ((Boolean)this.ignoreTerrain.get()).booleanValue());
/*  692 */     if (selfDamage > ((Double)this.maxDamage.get()).doubleValue() || (((Boolean)this.antiSuicide.get()).booleanValue() && selfDamage >= EntityUtils.getTotalHealth((class_1657)this.mc.field_1724))) return 0.0D;
/*      */ 
/*      */     
/*  695 */     double damage = getDamageToTargets(entity.method_19538(), (class_2338)this.blockPos, true, false);
/*  696 */     boolean facePlaced = ((((Boolean)this.facePlace.get()).booleanValue() && shouldFacePlace(entity.method_24515())) || ((Keybind)this.forceFacePlace.get()).isPressed());
/*      */     
/*  698 */     if (!facePlaced && damage < ((Double)this.minDamage.get()).doubleValue()) return 0.0D;
/*      */     
/*  700 */     return damage;
/*      */   }
/*      */ 
/*      */   
/*      */   private void doBreak(class_1297 crystal) {
/*  705 */     if (((Boolean)this.antiWeakness.get()).booleanValue()) {
/*  706 */       class_1293 weakness = this.mc.field_1724.method_6112(class_1294.field_5911);
/*  707 */       class_1293 strength = this.mc.field_1724.method_6112(class_1294.field_5910);
/*      */ 
/*      */       
/*  710 */       if (weakness != null && (strength == null || strength.method_5578() <= weakness.method_5578()))
/*      */       {
/*  712 */         if (!isValidWeaknessItem(this.mc.field_1724.method_6047())) {
/*      */           
/*  714 */           if (!InvUtils.swap(InvUtils.findInHotbar(this::isValidWeaknessItem).getSlot()))
/*      */             return; 
/*  716 */           this.switchTimer = 1;
/*      */           
/*      */           return;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  723 */     boolean attacked = true;
/*      */     
/*  725 */     if (((Boolean)this.rotate.get()).booleanValue()) {
/*  726 */       double yaw = Rotations.getYaw(crystal);
/*  727 */       double pitch = Rotations.getPitch(crystal, Target.Feet);
/*      */       
/*  729 */       if (doYawSteps(yaw, pitch)) {
/*  730 */         setRotation(true, crystal.method_19538(), 0.0D, 0.0D);
/*  731 */         Rotations.rotate(yaw, pitch, 50, () -> attackCrystal(crystal));
/*      */         
/*  733 */         this.breakTimer = ((Integer)this.breakDelay.get()).intValue();
/*      */       } else {
/*      */         
/*  736 */         attacked = false;
/*      */       } 
/*      */     } else {
/*      */       
/*  740 */       attackCrystal(crystal);
/*  741 */       this.breakTimer = ((Integer)this.breakDelay.get()).intValue();
/*      */     } 
/*      */     
/*  744 */     if (attacked) {
/*      */       
/*  746 */       this.removed.add(crystal.method_5628());
/*  747 */       this.attemptedBreaks.put(crystal.method_5628(), this.attemptedBreaks.get(crystal.method_5628()) + 1);
/*  748 */       this.waitingToExplode.put(crystal.method_5628(), 0);
/*      */ 
/*      */       
/*  751 */       this.breakRenderPos.method_10101((class_2382)crystal.method_24515().method_10074());
/*  752 */       this.breakRenderTimer = ((Integer)this.renderBreakTime.get()).intValue();
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isValidWeaknessItem(class_1799 itemStack) {
/*  757 */     if (!(itemStack.method_7909() instanceof class_1831) || itemStack.method_7909() instanceof net.minecraft.class_1794) return false;
/*      */     
/*  759 */     class_1832 material = ((class_1831)itemStack.method_7909()).method_8022();
/*  760 */     return (material == class_1834.field_8930 || material == class_1834.field_22033);
/*      */   }
/*      */ 
/*      */   
/*      */   private void attackCrystal(class_1297 entity) {
/*  765 */     this.mc.field_1724.field_3944.method_2883((class_2596)new class_2824(entity, this.mc.field_1724.method_5715()));
/*      */     
/*  767 */     class_1268 hand = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8301 }).getHand();
/*  768 */     if (hand == null) hand = class_1268.field_5808;
/*      */     
/*  770 */     if (((Boolean)this.renderSwing.get()).booleanValue()) { this.mc.field_1724.method_6104(hand); }
/*  771 */     else { this.mc.method_1562().method_2883((class_2596)new class_2879(hand)); }
/*      */   
/*      */   }
/*      */   @EventHandler
/*      */   private void onPacketSend(PacketEvent.Send event) {
/*  776 */     if (event.packet instanceof net.minecraft.class_2868) {
/*  777 */       this.switchTimer = ((Integer)this.switchDelay.get()).intValue();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void doPlace() {
/*  784 */     if (!((Boolean)this.doPlace.get()).booleanValue() || this.placeTimer > 0) {
/*      */       return;
/*      */     }
/*  787 */     if (!InvUtils.findInHotbar(new class_1792[] { class_1802.field_8301 }).found()) {
/*      */       return;
/*      */     }
/*  790 */     if (this.autoSwitch.get() == AutoSwitchMode.None && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301) {
/*      */       return;
/*      */     }
/*  793 */     for (class_1297 entity : this.mc.field_1687.method_18112()) {
/*  794 */       if (getBreakDamage(entity, false) > 0.0D) {
/*      */         return;
/*      */       }
/*      */     } 
/*  798 */     AtomicDouble bestDamage = new AtomicDouble(0.0D);
/*  799 */     AtomicReference<class_2338.class_2339> bestBlockPos = new AtomicReference<>(new class_2338.class_2339());
/*  800 */     AtomicBoolean isSupport = new AtomicBoolean((this.support.get() != SupportMode.Disabled));
/*      */ 
/*      */     
/*  803 */     BlockIterator.register((int)Math.ceil(((Double)this.placeRange.get()).doubleValue()), (int)Math.ceil(((Double)this.placeRange.get()).doubleValue()), (bp, blockState) -> {
/*      */           
/*  805 */           boolean hasBlock = (blockState.method_27852(class_2246.field_9987) || blockState.method_27852(class_2246.field_10540));
/*      */           if (!hasBlock && (!isSupport.get() || !blockState.method_26207().method_15800())) {
/*      */             return;
/*      */           }
/*      */           this.blockPos.method_10103(bp.method_10263(), bp.method_10264() + 1, bp.method_10260());
/*      */           if (!this.mc.field_1687.method_8320((class_2338)this.blockPos).method_26215()) {
/*      */             return;
/*      */           }
/*      */           if (((Boolean)this.placement112.get()).booleanValue()) {
/*      */             this.blockPos.method_10100(0, 1, 0);
/*      */             if (!this.mc.field_1687.method_8320((class_2338)this.blockPos).method_26215()) {
/*      */               return;
/*      */             }
/*      */           } 
/*      */           ((IVec3d)this.vec3d).set(bp.method_10263() + 0.5D, (bp.method_10264() + 1), bp.method_10260() + 0.5D);
/*      */           this.blockPos.method_10101((class_2382)bp).method_10100(0, 1, 0);
/*      */           if (isOutOfRange(this.vec3d, (class_2338)this.blockPos, true)) {
/*      */             return;
/*      */           }
/*      */           double selfDamage = DamageUtils.crystalDamage((class_1657)this.mc.field_1724, this.vec3d, ((Boolean)this.predictMovement.get()).booleanValue(), this.raycastContext, bp, ((Boolean)this.ignoreTerrain.get()).booleanValue());
/*      */           if (selfDamage > ((Double)this.maxDamage.get()).doubleValue() || (((Boolean)this.antiSuicide.get()).booleanValue() && selfDamage >= EntityUtils.getTotalHealth((class_1657)this.mc.field_1724)))
/*      */             return; 
/*  827 */           double damage = getDamageToTargets(this.vec3d, bp, false, (!hasBlock && this.support.get() == SupportMode.Fast));
/*      */           
/*  829 */           boolean facePlaced = ((((Boolean)this.facePlace.get()).booleanValue() && shouldFacePlace((class_2338)this.blockPos)) || ((Keybind)this.forceFacePlace.get()).isPressed());
/*      */           
/*      */           if (!facePlaced && damage < ((Double)this.minDamage.get()).doubleValue()) {
/*      */             return;
/*      */           }
/*      */           
/*      */           double x = bp.method_10263();
/*      */           
/*      */           double y = (bp.method_10264() + 1);
/*      */           double z = bp.method_10260();
/*      */           ((IBox)this.box).set(x, y, z, x + 1.0D, y + (((Boolean)this.placement112.get()).booleanValue() ? true : 2), z + 1.0D);
/*      */           if (intersectsWithEntities(this.box)) {
/*      */             return;
/*      */           }
/*      */           if (damage > bestDamage.get() || (isSupport.get() && hasBlock)) {
/*      */             bestDamage.set(damage);
/*      */             ((class_2338.class_2339)bestBlockPos.get()).method_10101((class_2382)bp);
/*      */           } 
/*      */           if (hasBlock) {
/*      */             isSupport.set(false);
/*      */           }
/*      */         });
/*  851 */     BlockIterator.after(() -> {
/*      */           if (bestDamage.get() == 0.0D) {
/*      */             return;
/*      */           }
/*      */           class_3965 result = getPlaceInfo(bestBlockPos.get());
/*      */           ((IVec3d)this.vec3d).set(result.method_17777().method_10263() + 0.5D + result.method_17780().method_10163().method_10263() * 1.0D / 2.0D, result.method_17777().method_10264() + 0.5D + result.method_17780().method_10163().method_10264() * 1.0D / 2.0D, result.method_17777().method_10260() + 0.5D + result.method_17780().method_10163().method_10260() * 1.0D / 2.0D);
/*      */           if (((Boolean)this.rotate.get()).booleanValue()) {
/*      */             double yaw = Rotations.getYaw(this.vec3d);
/*      */             double pitch = Rotations.getPitch(this.vec3d);
/*      */             if (this.yawStepMode.get() == YawStepMode.Break || doYawSteps(yaw, pitch)) {
/*      */               setRotation(true, this.vec3d, 0.0D, 0.0D);
/*      */               Rotations.rotate(yaw, pitch, 50, ());
/*      */               this.placeTimer += ((Integer)this.placeDelay.get()).intValue();
/*      */             } 
/*      */           } else {
/*      */             placeCrystal(result, bestDamage.get(), isSupport.get() ? bestBlockPos.get() : null);
/*      */             this.placeTimer += ((Integer)this.placeDelay.get()).intValue();
/*      */           } 
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class_3965 getPlaceInfo(class_2338 blockPos) {
/*  881 */     ((IVec3d)this.vec3d).set(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + this.mc.field_1724.method_18381(this.mc.field_1724.method_18376()), this.mc.field_1724.method_23321());
/*      */     
/*  883 */     for (class_2350 class_2350 : class_2350.values()) {
/*  884 */       ((IVec3d)this.vec3dRayTraceEnd).set(blockPos
/*  885 */           .method_10263() + 0.5D + class_2350.method_10163().method_10263() * 0.5D, blockPos
/*  886 */           .method_10264() + 0.5D + class_2350.method_10163().method_10264() * 0.5D, blockPos
/*  887 */           .method_10260() + 0.5D + class_2350.method_10163().method_10260() * 0.5D);
/*      */ 
/*      */       
/*  890 */       ((IRaycastContext)this.raycastContext).set(this.vec3d, this.vec3dRayTraceEnd, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
/*  891 */       class_3965 result = this.mc.field_1687.method_17742(this.raycastContext);
/*      */       
/*  893 */       if (result != null && result.method_17783() == class_239.class_240.field_1332 && result.method_17777().equals(blockPos)) {
/*  894 */         return result;
/*      */       }
/*      */     } 
/*      */     
/*  898 */     class_2350 side = (blockPos.method_10264() > this.vec3d.field_1351) ? class_2350.field_11033 : class_2350.field_11036;
/*  899 */     return new class_3965(this.vec3d, side, blockPos, false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void placeCrystal(class_3965 result, double damage, class_2338 supportBlock) {
/*  904 */     class_1792 targetItem = (supportBlock == null) ? class_1802.field_8301 : class_1802.field_8281;
/*      */     
/*  906 */     FindItemResult item = InvUtils.findInHotbar(new class_1792[] { targetItem });
/*  907 */     if (!item.found())
/*      */       return; 
/*  909 */     int prevSlot = this.mc.field_1724.field_7514.field_7545;
/*      */     
/*  911 */     if (this.autoSwitch.get() != AutoSwitchMode.None && !item.isOffhand()) {
/*  912 */       InvUtils.swap(item.getSlot());
/*      */     }
/*      */     
/*  915 */     class_1268 hand = item.getHand();
/*  916 */     if (hand == null) {
/*      */       return;
/*      */     }
/*  919 */     if (supportBlock == null) {
/*      */       
/*  921 */       this.mc.field_1724.field_3944.method_2883((class_2596)new class_2885(hand, result));
/*      */       
/*  923 */       if (((Boolean)this.renderSwing.get()).booleanValue()) { this.mc.field_1724.method_6104(hand); }
/*  924 */       else { this.mc.method_1562().method_2883((class_2596)new class_2879(hand)); }
/*      */       
/*  926 */       this.placing = true;
/*  927 */       this.placingTimer = 4;
/*  928 */       this.placingCrystalBlockPos.method_10101((class_2382)result.method_17777()).method_10100(0, 1, 0);
/*      */       
/*  930 */       this.renderTimer = ((Integer)this.renderTime.get()).intValue();
/*  931 */       this.renderPos.method_10101((class_2382)result.method_17777());
/*  932 */       this.renderDamage = damage;
/*      */     }
/*      */     else {
/*      */       
/*  936 */       BlockUtils.place(supportBlock, item, false, 0, ((Boolean)this.renderSwing.get()).booleanValue(), true, false);
/*  937 */       this.placeTimer += ((Integer)this.supportDelay.get()).intValue();
/*      */       
/*  939 */       if (((Integer)this.supportDelay.get()).intValue() == 0) placeCrystal(result, damage, (class_2338)null);
/*      */     
/*      */     } 
/*      */     
/*  943 */     if (this.autoSwitch.get() == AutoSwitchMode.Silent) {
/*  944 */       InvUtils.swap(prevSlot);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   private void onPacketSent(PacketEvent.Sent event) {
/*  952 */     if (event.packet instanceof class_2828) {
/*  953 */       this.serverYaw = ((class_2828)event.packet).method_12271((float)this.serverYaw);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean doYawSteps(double targetYaw, double targetPitch) {
/*  958 */     targetYaw = class_3532.method_15338(targetYaw) + 180.0D;
/*  959 */     double serverYaw = class_3532.method_15338(this.serverYaw) + 180.0D;
/*      */     
/*  961 */     if (distanceBetweenAngles(serverYaw, targetYaw) <= ((Double)this.yawSteps.get()).doubleValue()) return true;
/*      */     
/*  963 */     double delta = Math.abs(targetYaw - serverYaw);
/*  964 */     double yaw = this.serverYaw;
/*      */     
/*  966 */     if (serverYaw < targetYaw)
/*  967 */     { if (delta < 180.0D) { yaw += ((Double)this.yawSteps.get()).doubleValue(); }
/*  968 */       else { yaw -= ((Double)this.yawSteps.get()).doubleValue(); }
/*      */       
/*      */        }
/*  971 */     else if (delta < 180.0D) { yaw -= ((Double)this.yawSteps.get()).doubleValue(); }
/*  972 */     else { yaw += ((Double)this.yawSteps.get()).doubleValue(); }
/*      */ 
/*      */     
/*  975 */     setRotation(false, (class_243)null, yaw, targetPitch);
/*  976 */     Rotations.rotate(yaw, targetPitch, -100, null);
/*  977 */     return false;
/*      */   }
/*      */   
/*      */   private static double distanceBetweenAngles(double alpha, double beta) {
/*  981 */     double phi = Math.abs(beta - alpha) % 360.0D;
/*  982 */     return (phi > 180.0D) ? (360.0D - phi) : phi;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldFacePlace(class_2338 crystal) {
/*  989 */     for (class_1657 target : this.targets) {
/*  990 */       class_2338 pos = target.method_24515();
/*      */       
/*  992 */       if (crystal.method_10264() == pos.method_10264() + 1 && Math.abs(pos.method_10263() - crystal.method_10263()) <= 1 && Math.abs(pos.method_10260() - crystal.method_10260()) <= 1) {
/*  993 */         if (EntityUtils.getTotalHealth(target) <= ((Double)this.facePlaceHealth.get()).doubleValue()) return true;
/*      */         
/*  995 */         for (class_1799 itemStack : target.method_5661()) {
/*  996 */           if (itemStack == null || itemStack.method_7960()) {
/*  997 */             if (((Boolean)this.facePlaceArmor.get()).booleanValue()) return true; 
/*      */             continue;
/*      */           } 
/* 1000 */           if ((itemStack.method_7936() - itemStack.method_7919()) / itemStack.method_7936() * 100.0D <= ((Double)this.facePlaceDurability.get()).doubleValue()) return true;
/*      */         
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1006 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isOutOfRange(class_243 vec3d, class_2338 blockPos, boolean place) {
/* 1012 */     ((IRaycastContext)this.raycastContext).set(this.playerEyePos, vec3d, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
/*      */     
/* 1014 */     class_3965 result = this.mc.field_1687.method_17742(this.raycastContext);
/* 1015 */     boolean behindWall = (result == null || !result.method_17777().equals(blockPos));
/* 1016 */     double distance = this.mc.field_1724.method_19538().method_1022(vec3d);
/*      */     
/* 1018 */     return (distance > (behindWall ? (Double)(place ? this.placeWallsRange : this.breakWallsRange).get() : (Double)(place ? this.placeRange : this.breakRange).get()).doubleValue());
/*      */   }
/*      */   
/*      */   private class_1657 getNearestTarget() {
/* 1022 */     class_1657 nearestTarget = null;
/* 1023 */     double nearestDistance = Double.MAX_VALUE;
/*      */     
/* 1025 */     for (class_1657 target : this.targets) {
/* 1026 */       double distance = target.method_5858((class_1297)this.mc.field_1724);
/*      */       
/* 1028 */       if (distance < nearestDistance) {
/* 1029 */         nearestTarget = target;
/* 1030 */         nearestDistance = distance;
/*      */       } 
/*      */     } 
/*      */     
/* 1034 */     return nearestTarget;
/*      */   }
/*      */   
/*      */   private double getDamageToTargets(class_243 vec3d, class_2338 obsidianPos, boolean breaking, boolean fast) {
/* 1038 */     double damage = 0.0D;
/*      */     
/* 1040 */     if (fast) {
/* 1041 */       class_1657 target = getNearestTarget();
/* 1042 */       if (!((Boolean)this.smartDelay.get()).booleanValue() || !breaking || target.field_6235 <= 0) damage = DamageUtils.crystalDamage(target, vec3d, ((Boolean)this.predictMovement.get()).booleanValue(), this.raycastContext, obsidianPos, ((Boolean)this.ignoreTerrain.get()).booleanValue());
/*      */     
/*      */     } else {
/* 1045 */       for (class_1657 target : this.targets) {
/* 1046 */         if (((Boolean)this.smartDelay.get()).booleanValue() && breaking && target.field_6235 > 0)
/*      */           continue; 
/* 1048 */         double dmg = DamageUtils.crystalDamage(target, vec3d, ((Boolean)this.predictMovement.get()).booleanValue(), this.raycastContext, obsidianPos, ((Boolean)this.ignoreTerrain.get()).booleanValue());
/*      */ 
/*      */         
/* 1051 */         if (dmg > this.bestTargetDamage) {
/* 1052 */           this.bestTarget = target;
/* 1053 */           this.bestTargetDamage = dmg;
/* 1054 */           this.bestTargetTimer = 10;
/*      */         } 
/*      */         
/* 1057 */         damage += dmg;
/*      */       } 
/*      */     } 
/*      */     
/* 1061 */     return damage;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getInfoString() {
/* 1066 */     return (this.bestTarget != null && this.bestTargetTimer > 0) ? this.bestTarget.method_7334().getName() : null;
/*      */   }
/*      */   
/*      */   private void findTargets() {
/* 1070 */     this.targets.clear();
/*      */ 
/*      */     
/* 1073 */     for (class_1657 player : this.mc.field_1687.method_18456()) {
/* 1074 */       if (player.field_7503.field_7477 || player == this.mc.field_1724)
/*      */         continue; 
/* 1076 */       if (!player.method_29504() && player.method_5805() && Friends.get().shouldAttack(player) && player.method_5739((class_1297)this.mc.field_1724) <= ((Double)this.targetRange.get()).doubleValue()) {
/* 1077 */         this.targets.add(player);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1082 */     for (class_1657 player : FakePlayerManager.getPlayers()) {
/* 1083 */       if (!player.method_29504() && player.method_5805() && Friends.get().shouldAttack(player) && player.method_5739((class_1297)this.mc.field_1724) <= ((Double)this.targetRange.get()).doubleValue()) {
/* 1084 */         this.targets.add(player);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean intersectsWithEntities(class_238 box) {
/* 1091 */     int startX = class_3532.method_15357((box.field_1323 - 2.0D) / 16.0D);
/* 1092 */     int endX = class_3532.method_15357((box.field_1320 + 2.0D) / 16.0D);
/* 1093 */     int startZ = class_3532.method_15357((box.field_1321 - 2.0D) / 16.0D);
/* 1094 */     int endZ = class_3532.method_15357((box.field_1324 + 2.0D) / 16.0D);
/* 1095 */     class_631 class_631 = this.mc.field_1687.method_2935();
/*      */     
/* 1097 */     for (int x = startX; x <= endX; x++) {
/* 1098 */       for (int z = startZ; z <= endZ; z++) {
/* 1099 */         class_2818 chunk = class_631.method_12126(x, z, false);
/*      */         
/* 1101 */         if (chunk != null) {
/* 1102 */           class_3509[] arrayOfClass_3509 = chunk.method_12215();
/*      */           
/* 1104 */           int startY = class_3532.method_15357((box.field_1322 - 2.0D) / 16.0D);
/* 1105 */           int endY = class_3532.method_15357((box.field_1325 + 2.0D) / 16.0D);
/* 1106 */           startY = class_3532.method_15340(startY, 0, arrayOfClass_3509.length - 1);
/* 1107 */           endY = class_3532.method_15340(endY, 0, arrayOfClass_3509.length - 1);
/*      */           
/* 1109 */           for (int y = startY; y <= endY; y++) {
/* 1110 */             class_3509<class_1297> entitySection = arrayOfClass_3509[y];
/*      */             
/* 1112 */             for (class_1297 entity : entitySection) {
/* 1113 */               if (entity.method_5829().method_994(box) && !entity.method_7325() && !this.removed.contains(entity.method_5628())) {
/* 1114 */                 return true;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1122 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   private void onRender(RenderEvent event) {
/* 1129 */     if (this.renderTimer > 0 && ((Boolean)this.render.get()).booleanValue()) {
/* 1130 */       Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (class_2338)this.renderPos, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0);
/*      */     }
/*      */     
/* 1133 */     if (this.breakRenderTimer > 0 && ((Boolean)this.renderBreak.get()).booleanValue() && !this.mc.field_1687.method_8320((class_2338)this.breakRenderPos).method_26215()) {
/* 1134 */       int preSideA = ((SettingColor)this.sideColor.get()).a;
/* 1135 */       ((SettingColor)this.sideColor.get()).a -= 20;
/* 1136 */       ((SettingColor)this.sideColor.get()).validate();
/*      */       
/* 1138 */       int preLineA = ((SettingColor)this.lineColor.get()).a;
/* 1139 */       ((SettingColor)this.lineColor.get()).a -= 20;
/* 1140 */       ((SettingColor)this.lineColor.get()).validate();
/*      */       
/* 1142 */       Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, (class_2338)this.breakRenderPos, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0);
/*      */       
/* 1144 */       ((SettingColor)this.sideColor.get()).a = preSideA;
/* 1145 */       ((SettingColor)this.lineColor.get()).a = preLineA;
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   private void onRender2D(Render2DEvent event) {
/* 1151 */     if (!((Boolean)this.render.get()).booleanValue() || this.renderTimer <= 0 || !((Boolean)this.renderDamageText.get()).booleanValue())
/*      */       return; 
/* 1153 */     this.vec3.set(this.renderPos.method_10263() + 0.5D, this.renderPos.method_10264() + 0.5D, this.renderPos.method_10260() + 0.5D);
/*      */     
/* 1155 */     if (NametagUtils.to2D(this.vec3, ((Double)this.damageTextScale.get()).doubleValue())) {
/* 1156 */       NametagUtils.begin(this.vec3);
/* 1157 */       TextRenderer.get().begin(1.0D, false, true);
/*      */       
/* 1159 */       String text = String.format("%.1f", new Object[] { Double.valueOf(this.renderDamage) });
/* 1160 */       double w = TextRenderer.get().getWidth(text) / 2.0D;
/* 1161 */       TextRenderer.get().render(text, -w, 0.0D, (Color)this.lineColor.get(), true);
/*      */       
/* 1163 */       TextRenderer.get().end();
/* 1164 */       NametagUtils.end();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/ExtraCA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */