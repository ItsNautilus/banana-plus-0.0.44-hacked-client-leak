/*      */ package bananaplusdevelopment.addons.modules;
/*      */ import bananaplusdevelopment.utils.OldInvUtils;
/*      */ import bananaplusdevelopment.utils.PlayerUtils;
/*      */ import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import meteordevelopment.orbit.EventHandler;
/*      */ import minegame159.meteorclient.rendering.Renderer;
/*      */ import minegame159.meteorclient.rendering.ShapeMode;
/*      */ import minegame159.meteorclient.rendering.text.TextRenderer;
/*      */ import minegame159.meteorclient.settings.BoolSetting;
/*      */ import minegame159.meteorclient.settings.ColorSetting;
/*      */ import minegame159.meteorclient.settings.DoubleSetting;
/*      */ import minegame159.meteorclient.settings.EnumSetting;
/*      */ import minegame159.meteorclient.settings.IntSetting;
/*      */ import minegame159.meteorclient.settings.Setting;
/*      */ import minegame159.meteorclient.settings.SettingGroup;
/*      */ import minegame159.meteorclient.systems.friends.Friends;
/*      */ import minegame159.meteorclient.utils.misc.Vec3;
/*      */ import minegame159.meteorclient.utils.player.DamageCalcUtils;
/*      */ import minegame159.meteorclient.utils.render.NametagUtils;
/*      */ import minegame159.meteorclient.utils.render.color.Color;
/*      */ import minegame159.meteorclient.utils.render.color.SettingColor;
/*      */ import net.minecraft.class_1268;
/*      */ import net.minecraft.class_1297;
/*      */ import net.minecraft.class_1299;
/*      */ import net.minecraft.class_1309;
/*      */ import net.minecraft.class_1511;
/*      */ import net.minecraft.class_1657;
/*      */ import net.minecraft.class_1799;
/*      */ import net.minecraft.class_1802;
/*      */ import net.minecraft.class_1937;
/*      */ import net.minecraft.class_2246;
/*      */ import net.minecraft.class_2338;
/*      */ import net.minecraft.class_2350;
/*      */ import net.minecraft.class_2382;
/*      */ import net.minecraft.class_239;
/*      */ import net.minecraft.class_243;
/*      */ import net.minecraft.class_2596;
/*      */ import net.minecraft.class_2879;
/*      */ import net.minecraft.class_3532;
/*      */ import net.minecraft.class_3959;
/*      */ import net.minecraft.class_3965;
/*      */ 
/*      */ public class MonkeCA extends Module {
/*      */   public enum Mode {
/*   50 */     Safe,
/*   51 */     Suicide;
/*      */   }
/*      */   
/*      */   public enum TargetMode {
/*   55 */     MostDamage,
/*   56 */     HighestXDamages;
/*      */   }
/*      */   
/*      */   public enum RotationMode {
/*   60 */     Place,
/*   61 */     Break,
/*   62 */     Both,
/*   63 */     None;
/*      */   }
/*      */   
/*      */   public enum SwitchMode {
/*   67 */     Auto,
/*   68 */     Spoof,
/*   69 */     None;
/*      */   }
/*      */   
/*   72 */   private final SettingGroup sgPlace = this.settings.createGroup("Place");
/*   73 */   private final SettingGroup sgBreak = this.settings.createGroup("Break");
/*   74 */   private final SettingGroup sgTarget = this.settings.createGroup("Target");
/*   75 */   private final SettingGroup sgPause = this.settings.createGroup("Pause");
/*   76 */   private final SettingGroup sgRotations = this.settings.createGroup("Rotations");
/*   77 */   private final SettingGroup sgMisc = this.settings.createGroup("Misc");
/*   78 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*      */ 
/*      */ 
/*      */   
/*   82 */   private final Setting<Integer> placeDelay = this.sgPlace.add((Setting)(new IntSetting.Builder())
/*   83 */       .name("place-delay")
/*   84 */       .description("The amount of delay in ticks before placing.")
/*   85 */       .defaultValue(2)
/*   86 */       .min(0)
/*   87 */       .sliderMax(10)
/*   88 */       .build());
/*      */ 
/*      */   
/*   91 */   private final Setting<Mode> placeMode = this.sgPlace.add((Setting)(new EnumSetting.Builder())
/*   92 */       .name("place-mode")
/*   93 */       .description("The placement mode for crystals.")
/*   94 */       .defaultValue(Mode.Safe)
/*   95 */       .build());
/*      */ 
/*      */   
/*   98 */   private final Setting<Double> placeRange = this.sgPlace.add((Setting)(new DoubleSetting.Builder())
/*   99 */       .name("place-range")
/*  100 */       .description("The radius in which crystals can be placed in.")
/*  101 */       .defaultValue(4.5D)
/*  102 */       .min(0.0D)
/*  103 */       .sliderMax(7.0D)
/*  104 */       .build());
/*      */ 
/*      */   
/*  107 */   private final Setting<Double> placeWallsRange = this.sgPlace.add((Setting)(new DoubleSetting.Builder())
/*  108 */       .name("place-walls-range")
/*  109 */       .description("The radius in which crystals can be placed through walls.")
/*  110 */       .defaultValue(3.0D)
/*  111 */       .min(0.0D)
/*  112 */       .sliderMax(7.0D)
/*  113 */       .build());
/*      */ 
/*      */   
/*  116 */   private final Setting<Boolean> place = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  117 */       .name("place")
/*  118 */       .description("Allows Crystal Aura to place crystals.")
/*  119 */       .defaultValue(true)
/*  120 */       .build());
/*      */ 
/*      */   
/*  123 */   private final Setting<Boolean> multiPlace = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  124 */       .name("multi-place")
/*  125 */       .description("Allows Crystal Aura to place multiple crystals.")
/*  126 */       .defaultValue(false)
/*  127 */       .build());
/*      */ 
/*      */   
/*  130 */   private final Setting<Boolean> rayTrace = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  131 */       .name("ray-trace")
/*  132 */       .description("Whether or not to place through walls.")
/*  133 */       .defaultValue(false)
/*  134 */       .build());
/*      */ 
/*      */   
/*  137 */   private final Setting<Double> minDamage = this.sgPlace.add((Setting)(new DoubleSetting.Builder())
/*  138 */       .name("min-damage")
/*  139 */       .description("The minimum damage the crystal will place.")
/*  140 */       .defaultValue(5.5D)
/*  141 */       .build());
/*      */ 
/*      */   
/*  144 */   private final Setting<Double> minHealth = this.sgPlace.add((Setting)(new DoubleSetting.Builder())
/*  145 */       .name("min-health")
/*  146 */       .description("The minimum health you have to be for it to place.")
/*  147 */       .defaultValue(15.0D)
/*  148 */       .build());
/*      */ 
/*      */   
/*  151 */   private final Setting<Boolean> surroundBreak = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  152 */       .name("surround-break")
/*  153 */       .description("Places a crystal next to a surrounded player and keeps it there so they cannot use Surround again.")
/*  154 */       .defaultValue(false)
/*  155 */       .build());
/*      */ 
/*      */   
/*  158 */   private final Setting<Boolean> surroundHold = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  159 */       .name("surround-hold")
/*  160 */       .description("Places a crystal next to a player so they cannot use Surround.")
/*  161 */       .defaultValue(false)
/*  162 */       .build());
/*      */ 
/*      */   
/*  165 */   private final Setting<Boolean> oldPlace = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  166 */       .name("1.12-place")
/*  167 */       .description("Won't place in one block holes to help compatibility with some servers.")
/*  168 */       .defaultValue(false)
/*  169 */       .build());
/*      */ 
/*      */   
/*  172 */   private final Setting<Boolean> facePlace = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  173 */       .name("face-place")
/*  174 */       .description("Will face-place when target is below a certain health or armor durability threshold.")
/*  175 */       .defaultValue(true)
/*  176 */       .build());
/*      */ 
/*      */   
/*  179 */   private final Setting<Boolean> spamFacePlace = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  180 */       .name("spam-face-place")
/*  181 */       .description("Places faster when someone is below the face place health (Requires Smart Delay).")
/*  182 */       .defaultValue(false)
/*  183 */       .build());
/*      */ 
/*      */   
/*  186 */   private final Setting<Double> facePlaceHealth = this.sgPlace.add((Setting)(new DoubleSetting.Builder())
/*  187 */       .name("face-place-health")
/*  188 */       .description("The health required to face-place.")
/*  189 */       .defaultValue(8.0D)
/*  190 */       .min(1.0D)
/*  191 */       .max(36.0D)
/*  192 */       .build());
/*      */ 
/*      */   
/*  195 */   private final Setting<Double> facePlaceDurability = this.sgPlace.add((Setting)(new DoubleSetting.Builder())
/*  196 */       .name("face-place-durability")
/*  197 */       .description("The durability threshold to be able to face-place.")
/*  198 */       .defaultValue(2.0D)
/*  199 */       .min(1.0D)
/*  200 */       .max(100.0D)
/*  201 */       .sliderMax(100.0D)
/*  202 */       .build());
/*      */ 
/*      */   
/*  205 */   private final Setting<Boolean> support = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  206 */       .name("support")
/*  207 */       .description("Places a block in the air and crystals on it. Helps with killing players that are flying.")
/*  208 */       .defaultValue(false)
/*  209 */       .build());
/*      */ 
/*      */   
/*  212 */   private final Setting<Integer> supportDelay = this.sgPlace.add((Setting)(new IntSetting.Builder())
/*  213 */       .name("support-delay")
/*  214 */       .description("The delay between support blocks being placed.")
/*  215 */       .defaultValue(5)
/*  216 */       .min(0)
/*  217 */       .sliderMax(10)
/*  218 */       .build());
/*      */ 
/*      */   
/*  221 */   private final Setting<Boolean> supportBackup = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  222 */       .name("support-backup")
/*  223 */       .description("Makes it so support only works if there are no other options.")
/*  224 */       .defaultValue(true)
/*  225 */       .build());
/*      */ 
/*      */   
/*  228 */   private final Setting<Boolean> placeSync = this.sgPlace.add((Setting)(new BoolSetting.Builder())
/*  229 */       .name("placement-sync")
/*  230 */       .description("Waits until the tried crystal placement spawns before another place.")
/*  231 */       .defaultValue(false)
/*  232 */       .build());
/*      */ 
/*      */   
/*  235 */   private final Setting<Integer> syncTimeout = this.sgPlace.add((Setting)(new IntSetting.Builder())
/*  236 */       .name("sync-timeout")
/*  237 */       .description("How many ticks to wait before considering the place attempt dead.")
/*  238 */       .defaultValue(20)
/*  239 */       .min(0)
/*  240 */       .max(60)
/*  241 */       .sliderMax(60)
/*  242 */       .build());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  247 */   private final Setting<Integer> breakDelay = this.sgBreak.add((Setting)(new IntSetting.Builder())
/*  248 */       .name("break-delay")
/*  249 */       .description("The amount of delay in ticks before breaking.")
/*  250 */       .defaultValue(1)
/*  251 */       .min(0)
/*  252 */       .sliderMax(10)
/*  253 */       .build());
/*      */ 
/*      */   
/*  256 */   private final Setting<Mode> breakMode = this.sgBreak.add((Setting)(new EnumSetting.Builder())
/*  257 */       .name("break-mode")
/*  258 */       .description("The type of break mode for crystals.")
/*  259 */       .defaultValue(Mode.Safe)
/*  260 */       .build());
/*      */ 
/*      */   
/*  263 */   private final Setting<Double> breakRange = this.sgBreak.add((Setting)(new DoubleSetting.Builder())
/*  264 */       .name("break-range")
/*  265 */       .description("The maximum range that crystals can be to be broken.")
/*  266 */       .defaultValue(5.0D)
/*  267 */       .min(0.0D)
/*  268 */       .sliderMax(7.0D)
/*  269 */       .build());
/*      */ 
/*      */   
/*  272 */   private final Setting<Boolean> ignoreWalls = this.sgBreak.add((Setting)(new BoolSetting.Builder())
/*  273 */       .name("ray-trace")
/*  274 */       .description("Whether or not to break through walls.")
/*  275 */       .defaultValue(false)
/*  276 */       .build());
/*      */ 
/*      */   
/*  279 */   private final Setting<Boolean> removeCrystals = this.sgBreak.add((Setting)(new BoolSetting.Builder())
/*  280 */       .name("fast-hit")
/*  281 */       .description("Removes end crystals from the world as soon as it is hit. May cause desync on strict anticheats.")
/*  282 */       .defaultValue(true)
/*  283 */       .build());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  288 */   private final Setting<Object2BooleanMap<class_1299<?>>> entities = this.sgTarget.add((Setting)(new EntityTypeListSetting.Builder())
/*  289 */       .name("entities")
/*  290 */       .description("The entities to attack.")
/*  291 */       .defaultValue((Object2BooleanMap)Utils.asObject2BooleanOpenHashMap((Object[])new class_1299[] { class_1299.field_6097
/*  292 */           })).onlyAttackable()
/*  293 */       .build());
/*      */ 
/*      */   
/*  296 */   private final Setting<Double> targetRange = this.sgTarget.add((Setting)(new DoubleSetting.Builder())
/*  297 */       .name("target-range")
/*  298 */       .description("The maximum range the entity can be to be targeted.")
/*  299 */       .defaultValue(7.0D)
/*  300 */       .min(0.0D)
/*  301 */       .sliderMax(10.0D)
/*  302 */       .build());
/*      */ 
/*      */   
/*  305 */   private final Setting<TargetMode> targetMode = this.sgTarget.add((Setting)(new EnumSetting.Builder())
/*  306 */       .name("target-mode")
/*  307 */       .description("The way you target multiple targets.")
/*  308 */       .defaultValue(TargetMode.HighestXDamages)
/*  309 */       .build());
/*      */ 
/*      */   
/*  312 */   private final Setting<Integer> numberOfDamages = this.sgTarget.add((Setting)(new IntSetting.Builder())
/*  313 */       .name("number-of-damages")
/*  314 */       .description("The number to replace 'x' with in HighestXDamages.")
/*  315 */       .defaultValue(3)
/*  316 */       .min(2)
/*  317 */       .sliderMax(10)
/*  318 */       .build());
/*      */ 
/*      */   
/*  321 */   private final Setting<Boolean> multiTarget = this.sgTarget.add((Setting)(new BoolSetting.Builder())
/*  322 */       .name("multi-targeting")
/*  323 */       .description("Will calculate damage for all entities and pick a block based on target mode.")
/*  324 */       .defaultValue(false)
/*  325 */       .build());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  330 */   private final Setting<Boolean> pauseOnEat = this.sgPause.add((Setting)(new BoolSetting.Builder())
/*  331 */       .name("pause-on-eat")
/*  332 */       .description("Pauses Crystal Aura while eating.")
/*  333 */       .defaultValue(false)
/*  334 */       .build());
/*      */ 
/*      */   
/*  337 */   private final Setting<Boolean> pauseOnDrink = this.sgPause.add((Setting)(new BoolSetting.Builder())
/*  338 */       .name("pause-on-drink")
/*  339 */       .description("Pauses Crystal Aura while drinking a potion.")
/*  340 */       .defaultValue(false)
/*  341 */       .build());
/*      */ 
/*      */   
/*  344 */   private final Setting<Boolean> pauseOnMine = this.sgPause.add((Setting)(new BoolSetting.Builder())
/*  345 */       .name("pause-on-mine")
/*  346 */       .description("Pauses Crystal Aura while mining blocks.")
/*  347 */       .defaultValue(false)
/*  348 */       .build());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  353 */   private final Setting<RotationMode> rotationMode = this.sgRotations.add((Setting)(new EnumSetting.Builder())
/*  354 */       .name("rotation-mode")
/*  355 */       .description("The method of rotating when using Crystal Aura.")
/*  356 */       .defaultValue(RotationMode.Place)
/*  357 */       .build());
/*      */ 
/*      */   
/*  360 */   private final Setting<Boolean> strictLook = this.sgRotations.add((Setting)(new BoolSetting.Builder())
/*  361 */       .name("strict-look")
/*  362 */       .description("Looks at exactly where you're placing.")
/*  363 */       .defaultValue(true)
/*  364 */       .build());
/*      */ 
/*      */   
/*  367 */   private final Setting<Boolean> resetRotations = this.sgRotations.add((Setting)(new BoolSetting.Builder())
/*  368 */       .name("reset-rotations")
/*  369 */       .description("Resets rotations once Crystal Aura is disabled.")
/*  370 */       .defaultValue(false)
/*  371 */       .build());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  376 */   private final Setting<SwitchMode> switchMode = this.sgMisc.add((Setting)(new EnumSetting.Builder())
/*  377 */       .name("switch-mode")
/*  378 */       .description("How to switch items.")
/*  379 */       .defaultValue(SwitchMode.Auto)
/*  380 */       .build());
/*      */ 
/*      */   
/*  383 */   private final Setting<Boolean> switchBack = this.sgMisc.add((Setting)(new BoolSetting.Builder())
/*  384 */       .name("switch-back")
/*  385 */       .description("Switches back to your previous slot when disabling Crystal Aura.")
/*  386 */       .defaultValue(true)
/*  387 */       .build());
/*      */ 
/*      */   
/*  390 */   private final Setting<Double> verticalRange = this.sgMisc.add((Setting)(new DoubleSetting.Builder())
/*  391 */       .name("vertical-range")
/*  392 */       .description("The maximum vertical range for placing/breaking end crystals. May kill performance if this value is higher than 3.")
/*  393 */       .min(0.0D)
/*  394 */       .defaultValue(3.0D)
/*  395 */       .max(7.0D)
/*  396 */       .build());
/*      */ 
/*      */   
/*  399 */   private final Setting<Double> maxDamage = this.sgMisc.add((Setting)(new DoubleSetting.Builder())
/*  400 */       .name("max-damage")
/*  401 */       .description("The maximum self-damage allowed.")
/*  402 */       .defaultValue(3.0D)
/*  403 */       .build());
/*      */ 
/*      */   
/*  406 */   private final Setting<Boolean> smartDelay = this.sgMisc.add((Setting)(new BoolSetting.Builder())
/*  407 */       .name("smart-delay")
/*  408 */       .description("Reduces crystal consumption when doing large amounts of damage. (Can tank performance on lower-end PCs).")
/*  409 */       .defaultValue(false)
/*  410 */       .build());
/*      */ 
/*      */   
/*  413 */   private final Setting<Double> healthDifference = this.sgMisc.add((Setting)(new DoubleSetting.Builder())
/*  414 */       .name("damage-increase")
/*  415 */       .description("The damage increase for smart delay to work.")
/*  416 */       .defaultValue(5.0D)
/*  417 */       .min(0.0D)
/*  418 */       .max(20.0D)
/*  419 */       .build());
/*      */ 
/*      */   
/*  422 */   private final Setting<Boolean> antiWeakness = this.sgMisc.add((Setting)(new BoolSetting.Builder())
/*  423 */       .name("anti-weakness")
/*  424 */       .description("Switches to tools to break crystals instead of your fist.")
/*  425 */       .defaultValue(true)
/*  426 */       .build());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  431 */   private final Setting<Boolean> swing = this.sgRender.add((Setting)(new BoolSetting.Builder())
/*  432 */       .name("swing")
/*  433 */       .description("Renders your swing client-side.")
/*  434 */       .defaultValue(true)
/*  435 */       .build());
/*      */ 
/*      */   
/*  438 */   private final Setting<Boolean> render = this.sgRender.add((Setting)(new BoolSetting.Builder())
/*  439 */       .name("render")
/*  440 */       .description("Renders the block under where it is placing a crystal.")
/*  441 */       .defaultValue(true)
/*  442 */       .build());
/*      */ 
/*      */   
/*  445 */   private final Setting<ShapeMode> shapeMode = this.sgRender.add((Setting)(new EnumSetting.Builder())
/*  446 */       .name("shape-mode")
/*  447 */       .description("How the shapes are rendered.")
/*  448 */       .defaultValue((Enum)ShapeMode.Lines)
/*  449 */       .build());
/*      */ 
/*      */   
/*  452 */   private final Setting<SettingColor> sideColor = this.sgRender.add((Setting)(new ColorSetting.Builder())
/*  453 */       .name("side-color")
/*  454 */       .description("The side color.")
/*  455 */       .defaultValue(new SettingColor(255, 255, 255, 75))
/*  456 */       .build());
/*      */ 
/*      */   
/*  459 */   private final Setting<SettingColor> lineColor = this.sgRender.add((Setting)(new ColorSetting.Builder())
/*  460 */       .name("line-color")
/*  461 */       .description("The line color.")
/*  462 */       .defaultValue(new SettingColor(255, 255, 255, 255))
/*  463 */       .build());
/*      */ 
/*      */   
/*  466 */   private final Setting<Boolean> renderDamage = this.sgRender.add((Setting)(new BoolSetting.Builder())
/*  467 */       .name("render-damage")
/*  468 */       .description("Renders the damage of the crystal where it is placing.")
/*  469 */       .defaultValue(true)
/*  470 */       .build());
/*      */ 
/*      */   
/*  473 */   private final Setting<Integer> roundDamage = this.sgRender.add((Setting)(new IntSetting.Builder())
/*  474 */       .name("round-damage")
/*  475 */       .description("Round damage to x decimal places.")
/*  476 */       .defaultValue(2)
/*  477 */       .min(0)
/*  478 */       .max(3)
/*  479 */       .sliderMax(3)
/*  480 */       .build());
/*      */ 
/*      */   
/*  483 */   private final Setting<Double> damageScale = this.sgRender.add((Setting)(new DoubleSetting.Builder())
/*  484 */       .name("damage-scale")
/*  485 */       .description("The scale of the damage text.")
/*  486 */       .defaultValue(1.4D)
/*  487 */       .min(0.0D)
/*  488 */       .sliderMax(5.0D)
/*  489 */       .build());
/*      */ 
/*      */   
/*  492 */   private final Setting<SettingColor> damageColor = this.sgRender.add((Setting)(new ColorSetting.Builder())
/*  493 */       .name("damage-color")
/*  494 */       .description("The color of the damage text.")
/*  495 */       .defaultValue(new SettingColor(255, 255, 255, 255))
/*  496 */       .build());
/*      */ 
/*      */   
/*  499 */   private final Setting<Integer> renderTimer = this.sgRender.add((Setting)(new IntSetting.Builder())
/*  500 */       .name("timer")
/*  501 */       .description("The amount of time between changing the block render.")
/*  502 */       .defaultValue(0)
/*  503 */       .min(0)
/*  504 */       .sliderMax(10)
/*  505 */       .build()); private int preSlot; private int placeDelayLeft; private int breakDelayLeft; private int placeSyncLeft; private class_243 bestBlock; private double bestDamage; private double lastDamage; private class_1511 heldCrystal; private class_1511 triedCrystal; private class_1309 target; private boolean locked; private boolean canSupport; private boolean triedPlace; private int supportSlot; private int supportDelayLeft; private final Map<class_1511, List<Double>> crystalMap; private final List<Double> crystalList; private final List<Integer> removalQueue; private class_1511 bestBreak; private final Pool<RenderBlock> renderBlockPool; private final List<RenderBlock> renderBlocks;
/*      */   private boolean broken;
/*      */   
/*      */   public MonkeCA() {
/*  509 */     super(AddModule.BANANAPLUS, "Monke CA", "Automatically places and breaks crystals to damage other players.");
/*      */ 
/*      */ 
/*      */     
/*  513 */     this.placeDelayLeft = ((Integer)this.placeDelay.get()).intValue();
/*  514 */     this.breakDelayLeft = ((Integer)this.breakDelay.get()).intValue();
/*  515 */     this.placeSyncLeft = ((Integer)this.syncTimeout.get()).intValue();
/*      */     
/*  517 */     this.bestDamage = 0.0D;
/*  518 */     this.lastDamage = 0.0D;
/*  519 */     this.heldCrystal = null;
/*  520 */     this.triedCrystal = null;
/*      */     
/*  522 */     this.locked = false;
/*      */ 
/*      */     
/*  525 */     this.supportSlot = 0;
/*  526 */     this.supportDelayLeft = ((Integer)this.supportDelay.get()).intValue();
/*  527 */     this.crystalMap = new HashMap<>();
/*  528 */     this.crystalList = new ArrayList<>();
/*  529 */     this.removalQueue = new ArrayList<>();
/*  530 */     this.bestBreak = null;
/*      */     
/*  532 */     this.renderBlockPool = new Pool(() -> new RenderBlock());
/*  533 */     this.renderBlocks = new ArrayList<>();
/*      */     
/*  535 */     this.broken = false;
/*      */   }
/*      */   
/*      */   public void onActivate() {
/*  539 */     this.preSlot = -1;
/*  540 */     this.placeDelayLeft = 0;
/*  541 */     this.breakDelayLeft = 0;
/*  542 */     this.placeSyncLeft = 0;
/*  543 */     this.heldCrystal = null;
/*  544 */     this.triedCrystal = null;
/*  545 */     this.locked = false;
/*  546 */     this.broken = false;
/*  547 */     this.triedPlace = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDeactivate() {
/*  552 */     assert this.mc.field_1724 != null;
/*  553 */     if (((Boolean)this.switchBack.get()).booleanValue() && this.preSlot != -1) this.mc.field_1724.field_7514.field_7545 = this.preSlot; 
/*  554 */     for (RenderBlock renderBlock : this.renderBlocks) {
/*  555 */       this.renderBlockPool.free(renderBlock);
/*      */     }
/*  557 */     this.renderBlocks.clear();
/*  558 */     if (this.target != null && ((Boolean)this.resetRotations.get()).booleanValue() && (
/*  559 */       this.rotationMode.get() == RotationMode.Both || this.rotationMode.get() == RotationMode.Place || this.rotationMode.get() == RotationMode.Break)) {
/*  560 */       RotationUtils.packetRotate(this.mc.field_1724.field_6031, this.mc.field_1724.field_5965);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   private void onEntityRemoved(EntityRemovedEvent event) {
/*  567 */     if (this.heldCrystal == null)
/*  568 */       return;  if (event.entity.method_24515().equals(this.heldCrystal.method_24515())) {
/*  569 */       this.heldCrystal = null;
/*  570 */       this.locked = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   private void onEntitySpawned(EntityAddedEvent event) {
/*  576 */     if (((Boolean)this.placeSync.get()).booleanValue() && 
/*  577 */       event.entity instanceof class_1511) {
/*  578 */       class_1511 crystal = (class_1511)event.entity;
/*  579 */       if (crystal.method_24515().equals(this.triedCrystal.method_24515())) {
/*  580 */         this.triedPlace = false;
/*  581 */         this.triedCrystal = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler(priority = 100)
/*      */   private void onTick(TickEvent.Post event) {
/*  589 */     this.removalQueue.forEach(id -> this.mc.field_1687.method_2945(id.intValue()));
/*  590 */     this.removalQueue.clear();
/*      */   }
/*      */   
/*      */   @EventHandler(priority = 100)
/*      */   private void onTick(SendMovementPacketsEvent.Pre event) {
/*  595 */     for (Iterator<RenderBlock> it = this.renderBlocks.iterator(); it.hasNext(); ) {
/*  596 */       RenderBlock renderBlock = it.next();
/*      */       
/*  598 */       if (renderBlock.shouldRemove()) {
/*  599 */         it.remove();
/*  600 */         this.renderBlockPool.free(renderBlock);
/*      */       } 
/*      */     } 
/*      */     
/*  604 */     this.placeDelayLeft--;
/*  605 */     this.breakDelayLeft--;
/*  606 */     this.supportDelayLeft--;
/*  607 */     this.placeSyncLeft--;
/*  608 */     if (this.target == null) {
/*  609 */       this.heldCrystal = null;
/*  610 */       this.locked = false;
/*      */     } 
/*      */     
/*  613 */     if ((this.mc.field_1724.method_6115() && (this.mc.field_1724.method_6047().method_7909().method_19263() || this.mc.field_1724.method_6079().method_7909().method_19263()) && ((Boolean)this.pauseOnEat.get()).booleanValue()) || (this.mc.field_1761
/*  614 */       .method_2923() && ((Boolean)this.pauseOnMine.get()).booleanValue()) || (this.mc.field_1724
/*  615 */       .method_6115() && (this.mc.field_1724.method_6047().method_7909() instanceof net.minecraft.class_1812 || this.mc.field_1724.method_6079().method_7909() instanceof net.minecraft.class_1812) && ((Boolean)this.pauseOnDrink.get()).booleanValue())) {
/*      */       return;
/*      */     }
/*      */     
/*  619 */     if (this.locked && this.heldCrystal != null && ((!((Boolean)this.surroundBreak.get()).booleanValue() && this.target
/*  620 */       .method_24515().method_10262(new class_2382(this.heldCrystal.method_23317(), this.heldCrystal.method_23318(), this.heldCrystal.method_23321())) == 4.0D) || (!((Boolean)this.surroundHold.get()).booleanValue() && this.target
/*  621 */       .method_24515().method_10262(new class_2382(this.heldCrystal.method_23317(), this.heldCrystal.method_23318(), this.heldCrystal.method_23321())) == 2.0D))) {
/*  622 */       this.heldCrystal = null;
/*  623 */       this.locked = false;
/*      */     } 
/*  625 */     if (this.heldCrystal != null && this.mc.field_1724.method_5739((class_1297)this.heldCrystal) > ((Double)this.breakRange.get()).doubleValue()) {
/*  626 */       this.heldCrystal = null;
/*  627 */       this.locked = false;
/*      */     } 
/*  629 */     boolean isThere = false;
/*  630 */     if (this.heldCrystal != null) {
/*  631 */       for (class_1297 entity : this.mc.field_1687.method_18112()) {
/*  632 */         if (entity instanceof class_1511 && 
/*  633 */           this.heldCrystal != null && entity.method_24515().equals(this.heldCrystal.method_24515())) {
/*  634 */           isThere = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  638 */       if (!isThere) {
/*  639 */         this.heldCrystal = null;
/*  640 */         this.locked = false;
/*      */       } 
/*      */     } 
/*  643 */     boolean shouldFacePlace = false;
/*  644 */     if (getTotalHealth((class_1657)this.mc.field_1724) <= ((Double)this.minHealth.get()).doubleValue() && this.placeMode.get() != Mode.Suicide)
/*  645 */       return;  if (this.target != null && this.heldCrystal != null && this.placeDelayLeft <= 0 && this.mc.field_1687.method_17742(new class_3959(this.target.method_19538(), this.heldCrystal.method_19538(), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.target)).method_17783() == class_239.class_240.field_1333)
/*  646 */       this.locked = false; 
/*  647 */     if (this.heldCrystal == null) this.locked = false; 
/*  648 */     if (this.locked && !((Boolean)this.facePlace.get()).booleanValue())
/*      */       return; 
/*  650 */     if (!((Boolean)this.multiTarget.get()).booleanValue()) {
/*  651 */       findTarget();
/*  652 */       if (this.target == null)
/*  653 */         return;  if (this.breakDelayLeft <= 0) {
/*  654 */         singleBreak();
/*      */       }
/*  656 */     } else if (this.breakDelayLeft <= 0) {
/*  657 */       multiBreak();
/*      */     } 
/*      */ 
/*      */     
/*  661 */     if (this.broken) {
/*  662 */       this.broken = false;
/*      */       
/*      */       return;
/*      */     } 
/*  666 */     if (!((Boolean)this.smartDelay.get()).booleanValue() && this.placeDelayLeft > 0 && ((!((Boolean)this.surroundHold.get()).booleanValue() && this.target != null && (!((Boolean)this.surroundBreak.get()).booleanValue() || !isSurrounded(this.target))) || this.heldCrystal != null) && !((Boolean)this.spamFacePlace.get()).booleanValue())
/*  667 */       return;  if (this.switchMode.get() == SwitchMode.None && this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301)
/*  668 */       return;  if (((Boolean)this.place.get()).booleanValue()) {
/*  669 */       if (this.target == null)
/*  670 */         return;  if (!((Boolean)this.multiPlace.get()).booleanValue() && getCrystalStream().count() > 0L)
/*  671 */         return;  if (((Boolean)this.surroundHold.get()).booleanValue() && this.heldCrystal == null) {
/*  672 */         int i = OldInvUtils.findItemInHotbar(class_1802.field_8301);
/*  673 */         if ((i != -1 && i < 9) || this.mc.field_1724.method_6079().method_7909() == class_1802.field_8301) {
/*  674 */           this.bestBlock = findOpen(this.target);
/*  675 */           if (this.bestBlock != null) {
/*  676 */             doHeldCrystal();
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*  681 */       if (((Boolean)this.surroundBreak.get()).booleanValue() && this.heldCrystal == null && isSurrounded(this.target)) {
/*  682 */         int i = OldInvUtils.findItemInHotbar(class_1802.field_8301);
/*  683 */         if ((i != -1 && i < 9) || this.mc.field_1724.method_6079().method_7909() == class_1802.field_8301) {
/*  684 */           this.bestBlock = findOpenSurround(this.target);
/*  685 */           if (this.bestBlock != null) {
/*  686 */             doHeldCrystal();
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/*  691 */       int slot = OldInvUtils.findItemInHotbar(class_1802.field_8301);
/*  692 */       if ((slot == -1 || slot > 9) && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301) {
/*      */         return;
/*      */       }
/*  695 */       findValidBlocks(this.target);
/*  696 */       if (this.bestBlock == null) {
/*  697 */         findFacePlace(this.target);
/*      */       }
/*  699 */       if (this.bestBlock == null)
/*  700 */         return;  if (((Boolean)this.facePlace.get()).booleanValue() && Math.sqrt(this.target.method_5707(this.bestBlock)) <= 2.0D) {
/*  701 */         if ((this.target.method_6032() + this.target.method_6067()) < ((Double)this.facePlaceHealth.get()).doubleValue()) {
/*  702 */           shouldFacePlace = true;
/*      */         } else {
/*  704 */           Iterable<class_1799> armourItems = this.target.method_5661();
/*  705 */           for (class_1799 itemStack : armourItems) {
/*  706 */             if (itemStack != null && 
/*  707 */               !itemStack.method_7960() && (itemStack.method_7936() - itemStack.method_7919()) / itemStack.method_7936() * 100.0D <= ((Double)this.facePlaceDurability.get()).doubleValue()) {
/*  708 */               shouldFacePlace = true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*  713 */       if (this.bestBlock != null && ((this.bestDamage >= ((Double)this.minDamage.get()).doubleValue() && !this.locked) || shouldFacePlace)) {
/*  714 */         if (this.switchMode.get() != SwitchMode.None) doSwitch(); 
/*  715 */         if (this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301)
/*  716 */           return;  if (!((Boolean)this.smartDelay.get()).booleanValue()) {
/*  717 */           this.placeDelayLeft = ((Integer)this.placeDelay.get()).intValue();
/*  718 */           placeBlock(this.bestBlock, getHand());
/*  719 */         } else if (((Boolean)this.smartDelay.get()).booleanValue() && (this.placeDelayLeft <= 0 || this.bestDamage - this.lastDamage > ((Double)this.healthDifference.get()).doubleValue() || (((Boolean)this.spamFacePlace
/*  720 */           .get()).booleanValue() && shouldFacePlace))) {
/*  721 */           this.lastDamage = this.bestDamage;
/*  722 */           placeBlock(this.bestBlock, getHand());
/*  723 */           if (this.placeDelayLeft <= 0) this.placeDelayLeft = 10; 
/*      */         } 
/*      */       } 
/*  726 */       if (this.switchMode.get() == SwitchMode.Spoof && this.preSlot != this.mc.field_1724.field_7514.field_7545 && this.preSlot != -1)
/*  727 */         this.mc.field_1724.field_7514.field_7545 = this.preSlot; 
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   private void onRender(RenderEvent event) {
/*  733 */     if (!((Boolean)this.render.get()).booleanValue())
/*      */       return; 
/*  735 */     for (RenderBlock renderBlock : this.renderBlocks) {
/*  736 */       renderBlock.render3D();
/*      */     }
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   private void onRender2D(Render2DEvent event) {
/*  742 */     if (!((Boolean)this.render.get()).booleanValue())
/*      */       return; 
/*  744 */     for (RenderBlock renderBlock : this.renderBlocks) {
/*  745 */       renderBlock.render2D();
/*      */     }
/*      */   }
/*      */   
/*      */   private Stream<class_1297> getCrystalStream() {
/*  750 */     return Streams.stream(this.mc.field_1687.method_18112())
/*  751 */       .filter(entity -> entity instanceof class_1511)
/*  752 */       .filter(entity -> (entity.method_5739((class_1297)this.mc.field_1724) <= ((Double)this.breakRange.get()).doubleValue()))
/*  753 */       .filter(class_1297::method_5805)
/*  754 */       .filter(entity -> shouldBreak((class_1511)entity))
/*  755 */       .filter(entity -> (!((Boolean)this.ignoreWalls.get()).booleanValue() || this.mc.field_1724.method_6057(entity)))
/*  756 */       .filter(entity -> isSafe(entity.method_19538()));
/*      */   }
/*      */   
/*      */   private void singleBreak() {
/*  760 */     assert this.mc.field_1724 != null;
/*  761 */     assert this.mc.field_1687 != null;
/*  762 */     getCrystalStream().max(Comparator.comparingDouble(o -> DamageCalcUtils.crystalDamage(this.target, o.method_19538())))
/*  763 */       .ifPresent(entity -> hitCrystal((class_1511)entity));
/*      */   }
/*      */   
/*      */   private void multiBreak() {
/*  767 */     assert this.mc.field_1687 != null;
/*  768 */     assert this.mc.field_1724 != null;
/*  769 */     this.crystalMap.clear();
/*  770 */     this.crystalList.clear();
/*  771 */     getCrystalStream().forEach(entity -> {
/*      */           for (class_1297 target : this.mc.field_1687.method_18112()) {
/*      */             if (target != this.mc.field_1724 && ((Object2BooleanMap)this.entities.get()).getBoolean(target.method_5864()) && this.mc.field_1724.method_5739(target) <= ((Double)this.targetRange.get()).doubleValue() && target.method_5805() && target instanceof class_1309 && (!(target instanceof class_1657) || Friends.get().shouldAttack((class_1657)target))) {
/*      */               this.crystalList.add(Double.valueOf(DamageCalcUtils.crystalDamage((class_1309)target, entity.method_19538())));
/*      */             }
/*      */           } 
/*      */           
/*      */           if (!this.crystalList.isEmpty()) {
/*      */             this.crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
/*      */             
/*      */             this.crystalMap.put((class_1511)entity, new ArrayList<>(this.crystalList));
/*      */             this.crystalList.clear();
/*      */           } 
/*      */         });
/*  785 */     class_1511 crystal = findBestCrystal(this.crystalMap);
/*  786 */     if (crystal != null) {
/*  787 */       hitCrystal(crystal);
/*      */     }
/*      */   }
/*      */   
/*      */   private class_1511 findBestCrystal(Map<class_1511, List<Double>> map) {
/*  792 */     this.bestDamage = 0.0D;
/*  793 */     double currentDamage = 0.0D;
/*  794 */     if (this.targetMode.get() == TargetMode.HighestXDamages) {
/*  795 */       for (Map.Entry<class_1511, List<Double>> entry : map.entrySet()) {
/*  796 */         for (int i = 0; i < ((List)entry.getValue()).size() && i < ((Integer)this.numberOfDamages.get()).intValue(); i++) {
/*  797 */           currentDamage += ((Double)((List<Double>)entry.getValue()).get(i)).doubleValue();
/*      */         }
/*  799 */         if (this.bestDamage < currentDamage) {
/*  800 */           this.bestDamage = currentDamage;
/*  801 */           this.bestBreak = entry.getKey();
/*      */         } 
/*  803 */         currentDamage = 0.0D;
/*      */       } 
/*  805 */     } else if (this.targetMode.get() == TargetMode.MostDamage) {
/*  806 */       for (Map.Entry<class_1511, List<Double>> entry : map.entrySet()) {
/*  807 */         for (int i = 0; i < ((List)entry.getValue()).size(); i++) {
/*  808 */           currentDamage += ((Double)((List<Double>)entry.getValue()).get(i)).doubleValue();
/*      */         }
/*  810 */         if (this.bestDamage < currentDamage) {
/*  811 */           this.bestDamage = currentDamage;
/*  812 */           this.bestBreak = entry.getKey();
/*      */         } 
/*  814 */         currentDamage = 0.0D;
/*      */       } 
/*      */     } 
/*  817 */     return this.bestBreak;
/*      */   }
/*      */   
/*      */   private void hitCrystal(class_1511 entity) {
/*  821 */     assert this.mc.field_1724 != null;
/*  822 */     assert this.mc.field_1687 != null;
/*  823 */     assert this.mc.field_1761 != null;
/*  824 */     int preSlot = this.mc.field_1724.field_7514.field_7545;
/*  825 */     if (this.mc.field_1724.method_6088().containsKey(class_1294.field_5911) && ((Boolean)this.antiWeakness.get()).booleanValue()) {
/*  826 */       for (int i = 0; i < 9; i++) {
/*  827 */         if (this.mc.field_1724.field_7514.method_5438(i).method_7909() instanceof net.minecraft.class_1829 || this.mc.field_1724.field_7514.method_5438(i).method_7909() instanceof net.minecraft.class_1743) {
/*  828 */           this.mc.field_1724.field_7514.field_7545 = i;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*  833 */     if (this.rotationMode.get() == RotationMode.Break || this.rotationMode.get() == RotationMode.Both) {
/*  834 */       float[] rotation = PlayerUtils.calculateAngle(entity.method_19538());
/*  835 */       Rotations.rotate(rotation[0], rotation[1], 30, () -> attackCrystal(entity, preSlot));
/*      */     } else {
/*  837 */       attackCrystal(entity, preSlot);
/*      */     } 
/*      */     
/*  840 */     this.broken = true;
/*  841 */     this.breakDelayLeft = ((Integer)this.breakDelay.get()).intValue();
/*      */   }
/*      */   
/*      */   private void attackCrystal(class_1511 entity, int preSlot) {
/*  845 */     this.mc.field_1761.method_2918((class_1657)this.mc.field_1724, (class_1297)entity);
/*  846 */     if (((Boolean)this.removeCrystals.get()).booleanValue()) this.removalQueue.add(Integer.valueOf(entity.method_5628())); 
/*  847 */     if (((Boolean)this.swing.get()).booleanValue()) { this.mc.field_1724.method_6104(getHand()); }
/*  848 */     else { this.mc.field_1724.field_3944.method_2883((class_2596)new class_2879(getHand())); }
/*  849 */      this.mc.field_1724.field_7514.field_7545 = preSlot;
/*  850 */     if (this.heldCrystal != null && entity.method_24515().equals(this.heldCrystal.method_24515())) {
/*  851 */       this.heldCrystal = null;
/*  852 */       this.locked = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void findTarget() {
/*  857 */     assert this.mc.field_1687 != null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  866 */     Optional<class_1309> livingEntity = Streams.stream(this.mc.field_1687.method_18112()).filter(class_1297::method_5805).filter(entity -> (entity != this.mc.field_1724)).filter(entity -> (!(entity instanceof class_1657) || Friends.get().shouldAttack((class_1657)entity))).filter(entity -> entity instanceof class_1309).filter(entity -> ((Object2BooleanMap)this.entities.get()).getBoolean(entity.method_5864())).filter(entity -> (entity.method_5739((class_1297)this.mc.field_1724) <= ((Double)this.targetRange.get()).doubleValue() * 2.0D)).min(Comparator.comparingDouble(o -> o.method_5739((class_1297)this.mc.field_1724))).map(entity -> (class_1309)entity);
/*  867 */     if (!livingEntity.isPresent()) {
/*  868 */       this.target = null;
/*      */       return;
/*      */     } 
/*  871 */     this.target = livingEntity.get();
/*      */   }
/*      */   
/*      */   private void doSwitch() {
/*  875 */     assert this.mc.field_1724 != null;
/*  876 */     if (this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301) {
/*  877 */       int slot = OldInvUtils.findItemInHotbar(class_1802.field_8301);
/*  878 */       if (slot != -1 && slot < 9) {
/*  879 */         this.preSlot = this.mc.field_1724.field_7514.field_7545;
/*  880 */         this.mc.field_1724.field_7514.field_7545 = slot;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doHeldCrystal() {
/*  886 */     assert this.mc.field_1724 != null;
/*  887 */     if (this.switchMode.get() != SwitchMode.None) doSwitch(); 
/*  888 */     if (this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6079().method_7909() != class_1802.field_8301)
/*  889 */       return;  this.bestDamage = DamageCalcUtils.crystalDamage(this.target, this.bestBlock.method_1031(0.0D, 1.0D, 0.0D));
/*  890 */     this.heldCrystal = new class_1511((class_1937)this.mc.field_1687, this.bestBlock.field_1352, this.bestBlock.field_1351 + 1.0D, this.bestBlock.field_1350);
/*  891 */     this.locked = true;
/*  892 */     if (!((Boolean)this.smartDelay.get()).booleanValue()) {
/*  893 */       this.placeDelayLeft = ((Integer)this.placeDelay.get()).intValue();
/*      */     } else {
/*  895 */       this.lastDamage = this.bestDamage;
/*  896 */       if (this.placeDelayLeft <= 0) this.placeDelayLeft = 10; 
/*      */     } 
/*  898 */     placeBlock(this.bestBlock, getHand());
/*      */   }
/*      */   
/*      */   private void placeBlock(class_243 block, class_1268 hand) {
/*  902 */     assert this.mc.field_1724 != null;
/*  903 */     assert this.mc.field_1761 != null;
/*  904 */     assert this.mc.field_1687 != null;
/*  905 */     if (this.mc.field_1687.method_22347(new class_2338(block))) {
/*  906 */       PlayerUtils.placeBlock(new class_2338(block), this.supportSlot, class_1268.field_5808);
/*  907 */       this.supportDelayLeft = ((Integer)this.supportDelay.get()).intValue();
/*      */     } 
/*      */     
/*  910 */     if (((Boolean)this.placeSync.get()).booleanValue()) {
/*  911 */       if (this.placeSyncLeft <= 0) {
/*  912 */         this.triedPlace = false;
/*  913 */         this.triedCrystal = null;
/*  914 */         this.placeSyncLeft = ((Integer)this.syncTimeout.get()).intValue();
/*      */       } 
/*  916 */       if (this.triedPlace) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/*  921 */     class_2338 blockPos = new class_2338(block);
/*  922 */     class_2350 direction = rayTraceCheck(blockPos, true);
/*  923 */     if (this.rotationMode.get() == RotationMode.Place || this.rotationMode.get() == RotationMode.Both) {
/*  924 */       float[] rotation = PlayerUtils.calculateAngle(((Boolean)this.strictLook.get()).booleanValue() ? new class_243(blockPos.method_10263() + 0.5D + direction.method_10163().method_10263() * 1.0D / 2.0D, blockPos
/*  925 */             .method_10264() + 0.5D + direction.method_10163().method_10264() * 1.0D / 2.0D, blockPos
/*  926 */             .method_10260() + 0.5D + direction.method_10163().method_10260() * 1.0D / 2.0D) : block.method_1031(0.5D, 1.0D, 0.5D));
/*  927 */       Rotations.rotate(rotation[0], rotation[1], 25, () -> { this.mc.field_1761.method_2896(this.mc.field_1724, this.mc.field_1687, hand, new class_3965(this.mc.field_1724.method_19538(), direction, blockPos, false)); if (((Boolean)this.swing.get()).booleanValue()) { this.mc.field_1724.method_6104(hand); }
/*      */             else
/*      */             { this.mc.field_1724.field_3944.method_2883((class_2596)new class_2879(hand)); }
/*      */           
/*      */           });
/*      */     } else {
/*  933 */       this.mc.field_1761.method_2896(this.mc.field_1724, this.mc.field_1687, hand, new class_3965(this.mc.field_1724.method_19538(), direction, new class_2338(block), false));
/*  934 */       if (((Boolean)this.swing.get()).booleanValue()) { this.mc.field_1724.method_6104(hand); }
/*  935 */       else { this.mc.field_1724.field_3944.method_2883((class_2596)new class_2879(hand)); }
/*      */     
/*      */     } 
/*  938 */     if (((Boolean)this.render.get()).booleanValue()) {
/*  939 */       RenderBlock renderBlock = (RenderBlock)this.renderBlockPool.get();
/*  940 */       renderBlock.reset(block);
/*  941 */       renderBlock.damage = DamageCalcUtils.crystalDamage(this.target, this.bestBlock.method_1031(0.5D, 1.0D, 0.5D));
/*  942 */       this.renderBlocks.add(renderBlock);
/*      */     } 
/*      */     
/*  945 */     if (((Boolean)this.placeSync.get()).booleanValue()) {
/*  946 */       this.triedCrystal = new class_1511((class_1937)this.mc.field_1687, this.bestBlock.field_1352, this.bestBlock.field_1351 + 1.0D, this.bestBlock.field_1350);
/*  947 */       this.triedPlace = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void findValidBlocks(class_1309 target) {
/*  952 */     assert this.mc.field_1724 != null;
/*  953 */     assert this.mc.field_1687 != null;
/*  954 */     this.bestBlock = new class_243(0.0D, 0.0D, 0.0D);
/*  955 */     this.bestDamage = 0.0D;
/*  956 */     class_243 bestSupportBlock = new class_243(0.0D, 0.0D, 0.0D);
/*  957 */     double bestSupportDamage = 0.0D;
/*  958 */     class_2338 playerPos = this.mc.field_1724.method_24515();
/*  959 */     this.canSupport = false;
/*  960 */     this.crystalMap.clear();
/*  961 */     this.crystalList.clear();
/*  962 */     if (((Boolean)this.support.get()).booleanValue()) {
/*  963 */       for (int i = 0; i < 9; i++) {
/*  964 */         if (this.mc.field_1724.field_7514.method_5438(i).method_7909() == class_1802.field_8281) {
/*  965 */           this.canSupport = true;
/*  966 */           this.supportSlot = i;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*  972 */     for (class_243 pos : TanukiBlockUtils.getAreaAsVec3ds(playerPos, ((Double)this.placeRange.get()).doubleValue(), ((Double)this.placeRange.get()).doubleValue(), ((Double)this.verticalRange.get()).doubleValue(), true)) {
/*  973 */       if (isValid(new class_2338(pos)) && getDamagePlace((new class_2338(pos)).method_10084()) && (
/*  974 */         !((Boolean)this.oldPlace.get()).booleanValue() || isEmpty(new class_2338(pos.method_1031(0.0D, 2.0D, 0.0D)))) && (
/*  975 */         !((Boolean)this.rayTrace.get()).booleanValue() || pos.method_1022(new class_243(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + this.mc.field_1724.method_18381(this.mc.field_1724.method_18376()), this.mc.field_1724.method_23321())) <= ((Double)this.placeWallsRange.get()).doubleValue() || rayTraceCheck(new class_2338(pos), false) != null)) {
/*  976 */         if (!((Boolean)this.multiTarget.get()).booleanValue()) {
/*  977 */           if (isEmpty(new class_2338(pos)) && bestSupportDamage < DamageCalcUtils.crystalDamage(target, pos.method_1031(0.5D, 1.0D, 0.5D))) {
/*  978 */             bestSupportBlock = pos;
/*  979 */             bestSupportDamage = DamageCalcUtils.crystalDamage(target, pos.method_1031(0.5D, 1.0D, 0.5D)); continue;
/*  980 */           }  if (!isEmpty(new class_2338(pos)) && this.bestDamage < DamageCalcUtils.crystalDamage(target, pos.method_1031(0.5D, 1.0D, 0.5D))) {
/*  981 */             this.bestBlock = pos;
/*  982 */             this.bestDamage = DamageCalcUtils.crystalDamage(target, this.bestBlock.method_1031(0.5D, 1.0D, 0.5D));
/*      */           }  continue;
/*      */         } 
/*  985 */         for (class_1297 entity : this.mc.field_1687.method_18112()) {
/*  986 */           if (entity != this.mc.field_1724 && ((Object2BooleanMap)this.entities.get()).getBoolean(entity.method_5864()) && this.mc.field_1724.method_5739(entity) <= ((Double)this.targetRange.get()).doubleValue() && entity
/*  987 */             .method_5805() && entity instanceof class_1309 && (!(entity instanceof class_1657) || 
/*  988 */             Friends.get().shouldAttack((class_1657)entity))) {
/*  989 */             this.crystalList.add(Double.valueOf(DamageCalcUtils.crystalDamage((class_1309)entity, pos.method_1031(0.5D, 1.0D, 0.5D))));
/*      */           }
/*      */         } 
/*  992 */         if (!this.crystalList.isEmpty()) {
/*  993 */           this.crystalList.sort(Comparator.comparingDouble(Double::doubleValue));
/*  994 */           this.crystalMap.put(new class_1511((class_1937)this.mc.field_1687, pos.field_1352, pos.field_1351, pos.field_1350), new ArrayList<>(this.crystalList));
/*  995 */           this.crystalList.clear();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1003 */     if (((Boolean)this.multiTarget.get()).booleanValue())
/* 1004 */     { class_1511 entity = findBestCrystal(this.crystalMap);
/* 1005 */       if (entity != null && this.bestDamage > ((Double)this.minDamage.get()).doubleValue()) {
/* 1006 */         this.bestBlock = entity.method_19538();
/*      */       } else {
/* 1008 */         this.bestBlock = null;
/*      */       }
/*      */        }
/* 1011 */     else if (this.bestDamage < ((Double)this.minDamage.get()).doubleValue()) { this.bestBlock = null; }
/*      */     
/* 1013 */     if (((Boolean)this.support.get()).booleanValue() && (this.bestBlock == null || (this.bestDamage < bestSupportDamage && !((Boolean)this.supportBackup.get()).booleanValue()))) {
/* 1014 */       this.bestBlock = bestSupportBlock;
/*      */     }
/*      */   }
/*      */   
/*      */   private void findFacePlace(class_1309 target) {
/* 1019 */     assert this.mc.field_1687 != null;
/* 1020 */     assert this.mc.field_1724 != null;
/* 1021 */     class_2338 targetBlockPos = target.method_24515();
/* 1022 */     if (this.mc.field_1687.method_8320(targetBlockPos.method_10069(1, 1, 0)).method_26215() && Math.sqrt(this.mc.field_1724.method_24515().method_10262((class_2382)targetBlockPos.method_10069(1, 1, 0))) <= ((Double)this.placeRange.get()).doubleValue() && 
/* 1023 */       getDamagePlace(targetBlockPos.method_10069(1, 1, 0))) {
/* 1024 */       this.bestBlock = target.method_19538().method_1031(1.0D, 0.0D, 0.0D);
/* 1025 */     } else if (this.mc.field_1687.method_8320(targetBlockPos.method_10069(-1, 1, 0)).method_26215() && Math.sqrt(this.mc.field_1724.method_24515().method_10262((class_2382)targetBlockPos.method_10069(-1, 1, 0))) <= ((Double)this.placeRange.get()).doubleValue() && 
/* 1026 */       getDamagePlace(targetBlockPos.method_10069(-1, 1, 0))) {
/* 1027 */       this.bestBlock = target.method_19538().method_1031(-1.0D, 0.0D, 0.0D);
/* 1028 */     } else if (this.mc.field_1687.method_8320(targetBlockPos.method_10069(0, 1, 1)).method_26215() && Math.sqrt(this.mc.field_1724.method_24515().method_10262((class_2382)targetBlockPos.method_10069(0, 1, 1))) <= ((Double)this.placeRange.get()).doubleValue() && 
/* 1029 */       getDamagePlace(targetBlockPos.method_10069(0, 1, 1))) {
/* 1030 */       this.bestBlock = target.method_19538().method_1031(0.0D, 0.0D, 1.0D);
/* 1031 */     } else if (this.mc.field_1687.method_8320(targetBlockPos.method_10069(0, 1, -1)).method_26215() && Math.sqrt(this.mc.field_1724.method_24515().method_10262((class_2382)targetBlockPos.method_10069(0, 1, -1))) <= ((Double)this.placeRange.get()).doubleValue() && 
/* 1032 */       getDamagePlace(targetBlockPos.method_10069(0, 1, -1))) {
/* 1033 */       this.bestBlock = target.method_19538().method_1031(0.0D, 0.0D, -1.0D);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean getDamagePlace(class_2338 pos) {
/* 1038 */     assert this.mc.field_1724 != null;
/* 1039 */     return (this.placeMode.get() == Mode.Suicide || (DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, new class_243(pos.method_10263() + 0.5D, pos.method_10264(), pos.method_10260() + 0.5D)) <= ((Double)this.maxDamage.get()).doubleValue() && 
/* 1040 */       getTotalHealth((class_1657)this.mc.field_1724) - DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, new class_243(pos.method_10263() + 0.5D, pos.method_10264(), pos.method_10260() + 0.5D)) >= ((Double)this.minHealth.get()).doubleValue()));
/*      */   }
/*      */   
/*      */   private class_243 findOpen(class_1309 target) {
/* 1044 */     assert this.mc.field_1724 != null;
/* 1045 */     int x = 0;
/* 1046 */     int z = 0;
/* 1047 */     if (isValid(target.method_24515().method_10069(1, -1, 0)) && 
/* 1048 */       Math.sqrt(this.mc.field_1724.method_24515().method_10262(new class_2382(target.method_24515().method_10263() + 1, target.method_24515().method_10264() - 1, target.method_24515().method_10260()))) < ((Double)this.placeRange.get()).doubleValue()) {
/* 1049 */       x = 1;
/* 1050 */     } else if (isValid(target.method_24515().method_10069(-1, -1, 0)) && 
/* 1051 */       Math.sqrt(this.mc.field_1724.method_24515().method_10262(new class_2382(target.method_24515().method_10263() - 1, target.method_24515().method_10264() - 1, target.method_24515().method_10260()))) < ((Double)this.placeRange.get()).doubleValue()) {
/* 1052 */       x = -1;
/* 1053 */     } else if (isValid(target.method_24515().method_10069(0, -1, 1)) && 
/* 1054 */       Math.sqrt(this.mc.field_1724.method_24515().method_10262(new class_2382(target.method_24515().method_10263(), target.method_24515().method_10264() - 1, target.method_24515().method_10260() + 1))) < ((Double)this.placeRange.get()).doubleValue()) {
/* 1055 */       z = 1;
/* 1056 */     } else if (isValid(target.method_24515().method_10069(0, -1, -1)) && 
/* 1057 */       Math.sqrt(this.mc.field_1724.method_24515().method_10262(new class_2382(target.method_24515().method_10263(), target.method_24515().method_10264() - 1, target.method_24515().method_10260() - 1))) < ((Double)this.placeRange.get()).doubleValue()) {
/* 1058 */       z = -1;
/*      */     } 
/* 1060 */     if (x != 0 || z != 0) {
/* 1061 */       return new class_243(target.method_24515().method_10263() + 0.5D + x, (target.method_24515().method_10264() - 1), target.method_24515().method_10260() + 0.5D + z);
/*      */     }
/* 1063 */     return null;
/*      */   }
/*      */   
/*      */   private class_243 findOpenSurround(class_1309 target) {
/* 1067 */     assert this.mc.field_1724 != null;
/* 1068 */     assert this.mc.field_1687 != null;
/*      */     
/* 1070 */     int x = 0;
/* 1071 */     int z = 0;
/* 1072 */     if (validSurroundBreak(target, 2, 0)) {
/* 1073 */       x = 2;
/* 1074 */     } else if (validSurroundBreak(target, -2, 0)) {
/* 1075 */       x = -2;
/* 1076 */     } else if (validSurroundBreak(target, 0, 2)) {
/* 1077 */       z = 2;
/* 1078 */     } else if (validSurroundBreak(target, 0, -2)) {
/* 1079 */       z = -2;
/*      */     } 
/* 1081 */     if (x != 0 || z != 0) {
/* 1082 */       return new class_243(target.method_24515().method_10263() + 0.5D + x, (target.method_24515().method_10264() - 1), target.method_24515().method_10260() + 0.5D + z);
/*      */     }
/* 1084 */     return null;
/*      */   }
/*      */   
/*      */   private boolean isValid(class_2338 blockPos) {
/* 1088 */     assert this.mc.field_1687 != null;
/* 1089 */     return (((this.canSupport && isEmpty(blockPos) && blockPos.method_10264() - this.target.method_24515().method_10264() == -1 && this.supportDelayLeft <= 0) || this.mc.field_1687.method_8320(blockPos).method_26204() == class_2246.field_9987 || this.mc.field_1687
/* 1090 */       .method_8320(blockPos).method_26204() == class_2246.field_10540) && 
/* 1091 */       isEmpty(blockPos.method_10069(0, 1, 0)));
/*      */   }
/*      */   
/*      */   private class_2350 rayTraceCheck(class_2338 pos, boolean forceReturn) {
/* 1095 */     class_243 eyesPos = new class_243(this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318() + this.mc.field_1724.method_18381(this.mc.field_1724.method_18376()), this.mc.field_1724.method_23321());
/* 1096 */     for (class_2350 direction : class_2350.values()) {
/*      */ 
/*      */       
/* 1099 */       class_3959 raycastContext = new class_3959(eyesPos, new class_243(pos.method_10263() + 0.5D + direction.method_10163().method_10263() * 0.5D, pos.method_10264() + 0.5D + direction.method_10163().method_10264() * 0.5D, pos.method_10260() + 0.5D + direction.method_10163().method_10260() * 0.5D), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
/* 1100 */       class_3965 result = this.mc.field_1687.method_17742(raycastContext);
/* 1101 */       if (result != null && result.method_17783() == class_239.class_240.field_1332 && result.method_17777().equals(pos)) {
/* 1102 */         return direction;
/*      */       }
/*      */     } 
/* 1105 */     if (forceReturn) {
/* 1106 */       if (pos.method_10264() > eyesPos.field_1351) {
/* 1107 */         return class_2350.field_11033;
/*      */       }
/* 1109 */       return class_2350.field_11036;
/*      */     } 
/* 1111 */     return null;
/*      */   }
/*      */   
/*      */   private boolean validSurroundBreak(class_1309 target, int x, int z) {
/* 1115 */     assert this.mc.field_1687 != null;
/* 1116 */     assert this.mc.field_1724 != null;
/* 1117 */     class_243 crystalPos = new class_243(target.method_24515().method_10263() + 0.5D, target.method_24515().method_10264(), target.method_24515().method_10260() + 0.5D);
/* 1118 */     return (isValid(target.method_24515().method_10069(x, -1, z)) && this.mc.field_1687.method_8320(target.method_24515().method_10069(x / 2, 0, z / 2)).method_26204() != class_2246.field_9987 && 
/* 1119 */       isSafe(crystalPos.method_1031(x, 0.0D, z)) && 
/* 1120 */       Math.sqrt(this.mc.field_1724.method_24515().method_10262(new class_2382(target.method_24515().method_10263() + x, target.method_24515().method_10264() - 1, target.method_24515().method_10260() + z))) < ((Double)this.placeRange.get()).doubleValue() && this.mc.field_1687
/* 1121 */       .method_17742(new class_3959(target.method_19538(), target.method_19538().method_1031(x, 0.0D, z), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)target)).method_17783() != class_239.class_240.field_1333);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isSafe(class_243 crystalPos) {
/* 1126 */     assert this.mc.field_1724 != null;
/* 1127 */     return (this.breakMode.get() != Mode.Safe || (getTotalHealth((class_1657)this.mc.field_1724) - DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, crystalPos) > ((Double)this.minHealth.get()).doubleValue() && 
/* 1128 */       DamageCalcUtils.crystalDamage((class_1309)this.mc.field_1724, crystalPos) < ((Double)this.maxDamage.get()).doubleValue()));
/*      */   }
/*      */   
/*      */   private float getTotalHealth(class_1657 target) {
/* 1132 */     return target.method_6032() + target.method_6067();
/*      */   }
/*      */   
/*      */   private boolean isEmpty(class_2338 pos) {
/* 1136 */     assert this.mc.field_1687 != null;
/* 1137 */     return (this.mc.field_1687.method_8320(pos).method_26215() && this.mc.field_1687.method_8335(null, new class_238(pos.method_10263(), pos.method_10264(), pos.method_10260(), pos.method_10263() + 1.0D, pos.method_10264() + 2.0D, pos.method_10260() + 1.0D)).isEmpty());
/*      */   }
/*      */   
/* 1140 */   private static final Vec3 pos = new Vec3();
/*      */   private class RenderBlock { private int x; private int y; private int z;
/*      */     private int timer;
/*      */     private double damage;
/*      */     
/*      */     private RenderBlock() {}
/*      */     
/*      */     public void reset(class_243 pos) {
/* 1148 */       this.x = class_3532.method_15357(pos.method_10216());
/* 1149 */       this.y = class_3532.method_15357(pos.method_10214());
/* 1150 */       this.z = class_3532.method_15357(pos.method_10215());
/* 1151 */       this.timer = ((Integer)MonkeCA.this.renderTimer.get()).intValue();
/*      */     }
/*      */     
/*      */     public boolean shouldRemove() {
/* 1155 */       if (this.timer <= 0) return true; 
/* 1156 */       this.timer--;
/* 1157 */       return false;
/*      */     }
/*      */     
/*      */     public void render3D() {
/* 1161 */       Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, this.x, this.y, this.z, 1.0D, (Color)MonkeCA.this.sideColor.get(), (Color)MonkeCA.this.lineColor.get(), (ShapeMode)MonkeCA.this.shapeMode.get(), 0);
/*      */     }
/*      */     
/*      */     public void render2D() {
/* 1165 */       if (((Boolean)MonkeCA.this.renderDamage.get()).booleanValue()) {
/* 1166 */         MonkeCA.pos.set(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D);
/*      */         
/* 1168 */         if (NametagUtils.to2D(MonkeCA.pos, ((Double)MonkeCA.this.damageScale.get()).doubleValue())) {
/* 1169 */           NametagUtils.begin(MonkeCA.pos);
/* 1170 */           TextRenderer.get().begin(1.0D, false, true);
/*      */           
/* 1172 */           String damageText = String.valueOf(Math.round(this.damage));
/*      */           
/* 1174 */           switch (((Integer)MonkeCA.this.roundDamage.get()).intValue()) {
/*      */             case 0:
/* 1176 */               damageText = String.valueOf(Math.round(this.damage));
/*      */               break;
/*      */             case 1:
/* 1179 */               damageText = String.valueOf(Math.round(this.damage * 10.0D) / 10.0D);
/*      */               break;
/*      */             case 2:
/* 1182 */               damageText = String.valueOf(Math.round(this.damage * 100.0D) / 100.0D);
/*      */               break;
/*      */             case 3:
/* 1185 */               damageText = String.valueOf(Math.round(this.damage * 1000.0D) / 1000.0D);
/*      */               break;
/*      */           } 
/*      */           
/* 1189 */           double w = TextRenderer.get().getWidth(damageText) / 2.0D;
/*      */           
/* 1191 */           TextRenderer.get().render(damageText, -w, 0.0D, (Color)MonkeCA.this.damageColor.get());
/*      */           
/* 1193 */           TextRenderer.get().end();
/* 1194 */           NametagUtils.end();
/*      */         } 
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   private boolean shouldBreak(class_1511 entity) {
/* 1201 */     assert this.mc.field_1687 != null;
/* 1202 */     return (this.heldCrystal == null || (!((Boolean)this.surroundHold.get()).booleanValue() && !((Boolean)this.surroundBreak.get()).booleanValue()) || (this.placeDelayLeft <= 0 && (!this.heldCrystal.method_24515().equals(entity.method_24515()) || this.mc.field_1687.method_17742(new class_3959(this.target.method_19538(), this.heldCrystal.method_19538(), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.target)).method_17783() == class_239.class_240.field_1333 || (this.target
/* 1203 */       .method_5739((class_1297)this.heldCrystal) > 1.5D && !isSurrounded(this.target)))));
/*      */   }
/*      */   
/*      */   private boolean isSurrounded(class_1309 target) {
/* 1207 */     assert this.mc.field_1687 != null;
/* 1208 */     return (!this.mc.field_1687.method_8320(target.method_24515().method_10069(1, 0, 0)).method_26215() && 
/* 1209 */       !this.mc.field_1687.method_8320(target.method_24515().method_10069(-1, 0, 0)).method_26215() && 
/* 1210 */       !this.mc.field_1687.method_8320(target.method_24515().method_10069(0, 0, 1)).method_26215() && 
/* 1211 */       !this.mc.field_1687.method_8320(target.method_24515().method_10069(0, 0, -1)).method_26215());
/*      */   }
/*      */   
/*      */   public class_1268 getHand() {
/* 1215 */     assert this.mc.field_1724 != null;
/* 1216 */     class_1268 hand = class_1268.field_5808;
/* 1217 */     if (this.mc.field_1724.method_6047().method_7909() != class_1802.field_8301 && this.mc.field_1724.method_6079().method_7909() == class_1802.field_8301) {
/* 1218 */       hand = class_1268.field_5810;
/*      */     }
/* 1220 */     return hand;
/*      */   }
/*      */   
/*      */   public class_1657 getPlayerTarget() {
/* 1224 */     if (this.target instanceof class_1657) {
/* 1225 */       return (class_1657)this.target;
/*      */     }
/* 1227 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getBestDamage() {
/* 1232 */     return this.bestDamage;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getInfoString() {
/* 1237 */     if (this.target != null && this.target instanceof class_1657) return this.target.method_5820(); 
/* 1238 */     if (this.target != null) return this.target.method_5864().method_5897().getString(); 
/* 1239 */     return null;
/*      */   }
/*      */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/MonkeCA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */