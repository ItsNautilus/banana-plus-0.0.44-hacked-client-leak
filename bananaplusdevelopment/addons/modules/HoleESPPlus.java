/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import java.util.ArrayList;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.render.RenderEvent;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.mixin.AbstractBlockAccessor;
/*     */ import minegame159.meteorclient.rendering.DrawMode;
/*     */ import minegame159.meteorclient.rendering.MeshBuilder;
/*     */ import minegame159.meteorclient.rendering.ShapeMode;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.ColorSetting;
/*     */ import minegame159.meteorclient.settings.DoubleSetting;
/*     */ import minegame159.meteorclient.settings.EnumSetting;
/*     */ import minegame159.meteorclient.settings.IntSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.modules.Modules;
/*     */ import minegame159.meteorclient.utils.misc.Pool;
/*     */ import minegame159.meteorclient.utils.render.color.Color;
/*     */ import minegame159.meteorclient.utils.render.color.SettingColor;
/*     */ import minegame159.meteorclient.utils.world.BlockIterator;
/*     */ import minegame159.meteorclient.utils.world.Dir;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_290;
/*     */ 
/*     */ public class HoleESPPlus extends Module {
/*  30 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  31 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*     */ 
/*     */ 
/*     */   
/*  35 */   private final Setting<Integer> horizontalRadius = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/*  36 */       .name("horizontal-radius")
/*  37 */       .description("Horizontal radius in which to search for holes.")
/*  38 */       .defaultValue(10)
/*  39 */       .min(0)
/*  40 */       .sliderMax(32)
/*  41 */       .build());
/*     */ 
/*     */   
/*  44 */   private final Setting<Integer> verticalRadius = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/*  45 */       .name("vertical-radius")
/*  46 */       .description("Vertical radius in which to search for holes.")
/*  47 */       .defaultValue(5)
/*  48 */       .min(0)
/*  49 */       .sliderMax(32)
/*  50 */       .build());
/*     */ 
/*     */   
/*  53 */   private final Setting<Integer> holeHeight = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/*  54 */       .name("min-height")
/*  55 */       .description("Minimum hole height required to be rendered.")
/*  56 */       .defaultValue(3)
/*  57 */       .min(1)
/*  58 */       .build());
/*     */ 
/*     */   
/*  61 */   private final Setting<Boolean> doubles = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  62 */       .name("doubles")
/*  63 */       .description("Highlights double holes that can be stood across.")
/*  64 */       .defaultValue(true)
/*  65 */       .build());
/*     */ 
/*     */   
/*  68 */   private final Setting<Boolean> ignoreOwn = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  69 */       .name("ignore-own")
/*  70 */       .description("Ignores rendering the hole you are currently standing in.")
/*  71 */       .defaultValue(false)
/*  72 */       .build());
/*     */ 
/*     */   
/*  75 */   private final Setting<Boolean> webs = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  76 */       .name("webs")
/*  77 */       .description("Whether to show holes that have webs inside of them.")
/*  78 */       .defaultValue(false)
/*  79 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private final Setting<ShapeMode> shapeMode = this.sgRender.add((Setting)(new EnumSetting.Builder())
/*  85 */       .name("shape-mode")
/*  86 */       .description("How the shapes are rendered.")
/*  87 */       .defaultValue((Enum)ShapeMode.Both)
/*  88 */       .build());
/*     */ 
/*     */   
/*  91 */   private final Setting<Double> height = this.sgRender.add((Setting)(new DoubleSetting.Builder())
/*  92 */       .name("height")
/*  93 */       .description("The height of rendering.")
/*  94 */       .defaultValue(0.0D)
/*  95 */       .min(0.0D)
/*  96 */       .build());
/*     */ 
/*     */   
/*  99 */   private final Setting<Boolean> topQuad = this.sgRender.add((Setting)(new BoolSetting.Builder())
/* 100 */       .name("top-quad")
/* 101 */       .description("Whether to render a quad at the top of the hole.")
/* 102 */       .defaultValue(false)
/* 103 */       .build());
/*     */ 
/*     */   
/* 106 */   private final Setting<Boolean> bottomQuad = this.sgRender.add((Setting)(new BoolSetting.Builder())
/* 107 */       .name("bottom-quad")
/* 108 */       .description("Whether to render a quad at the bottom of the hole.")
/* 109 */       .defaultValue(false)
/* 110 */       .build());
/*     */ 
/*     */   
/* 113 */   private final Setting<SettingColor> bedrockColorTop = this.sgRender.add((Setting)(new ColorSetting.Builder())
/* 114 */       .name("bedrock-top")
/* 115 */       .description("The top color for holes that are completely bedrock.")
/* 116 */       .defaultValue(new SettingColor(100, 255, 0, 180))
/* 117 */       .build());
/*     */ 
/*     */   
/* 120 */   private final Setting<SettingColor> bedrockColorBottom = this.sgRender.add((Setting)(new ColorSetting.Builder())
/* 121 */       .name("bedrock-bottom")
/* 122 */       .description("The bottom color for holes that are completely bedrock.")
/* 123 */       .defaultValue(new SettingColor(100, 255, 0, 0))
/* 124 */       .build());
/*     */ 
/*     */   
/* 127 */   private final Setting<SettingColor> obsidianColorTop = this.sgRender.add((Setting)(new ColorSetting.Builder())
/* 128 */       .name("obsidian-top")
/* 129 */       .description("The top color for holes that are completely obsidian.")
/* 130 */       .defaultValue(new SettingColor(255, 0, 0, 180))
/* 131 */       .build());
/*     */ 
/*     */   
/* 134 */   private final Setting<SettingColor> obsidianColorBottom = this.sgRender.add((Setting)(new ColorSetting.Builder())
/* 135 */       .name("obsidian-bottom")
/* 136 */       .description("The bottom color for holes that are completely obsidian.")
/* 137 */       .defaultValue(new SettingColor(255, 0, 0, 0))
/* 138 */       .build());
/*     */ 
/*     */   
/* 141 */   private final Setting<SettingColor> mixedColorTop = this.sgRender.add((Setting)(new ColorSetting.Builder())
/* 142 */       .name("mixed-top")
/* 143 */       .description("The top color for holes that have mixed bedrock and obsidian.")
/* 144 */       .defaultValue(new SettingColor(255, 127, 0, 180))
/* 145 */       .build());
/*     */ 
/*     */   
/* 148 */   private final Setting<SettingColor> mixedColorBottom = this.sgRender.add((Setting)(new ColorSetting.Builder())
/* 149 */       .name("mixed-bottom")
/* 150 */       .description("The bottom color for holes that have mixed bedrock and obsidian.")
/* 151 */       .defaultValue(new SettingColor(255, 127, 0, 0))
/* 152 */       .build());
/*     */ 
/*     */   
/* 155 */   private final MeshBuilder LINES = new MeshBuilder(16384);
/* 156 */   private final MeshBuilder SIDES = new MeshBuilder(16384);
/*     */   
/* 158 */   private final Pool<Hole> holePool = new Pool(() -> new Hole());
/* 159 */   private final List<Hole> holes = new ArrayList<>();
/*     */   
/* 161 */   private final byte NULL = 0;
/*     */   public HoleESPPlus() {
/* 163 */     super(AddModule.BANANAPLUS, "hole-esp+", "Displays holes that you will take less damage in.");
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 168 */     for (Hole hole : this.holes) this.holePool.free(hole); 
/* 169 */     this.holes.clear();
/*     */     
/* 171 */     BlockIterator.register(((Integer)this.horizontalRadius.get()).intValue(), ((Integer)this.verticalRadius.get()).intValue(), (blockPos, blockState) -> { if (!validHole(blockPos))
/*     */             return;  int bedrock = 0; int obsidian = 0; int crying_obi = 0; int netherite_block = 0; int respawn_anchor = 0; int ender_chest = 0; int ancient_debris = 0; int enchanting_table = 0; int anvil = 0; int anvilC = 0; int anvilD = 0; class_2350 air = null; for (class_2350 direction : class_2350.values()) { if (direction != class_2350.field_11036) { class_2680 state = this.mc.field_1687.method_8320(blockPos.method_10093(direction)); if (state.method_26204() == class_2246.field_9987) { bedrock++; }
/*     */               else if (state.method_26204() == class_2246.field_10540) { obsidian++; }
/*     */               else if (state.method_26204() == class_2246.field_23152) { respawn_anchor++; }
/*     */               else if (state.method_26204() == class_2246.field_22108)
/*     */               { netherite_block++; }
/*     */               else if (state.method_26204() == class_2246.field_22423)
/*     */               { crying_obi++; }
/*     */               else if (state.method_26204() == class_2246.field_10443)
/*     */               { ender_chest++; }
/*     */               else if (state.method_26204() == class_2246.field_22109)
/*     */               { ancient_debris++; }
/*     */               else if (state.method_26204() == class_2246.field_10485)
/*     */               { enchanting_table++; }
/*     */               else if (state.method_26204() == class_2246.field_10535)
/*     */               { anvil++; }
/*     */               else if (state.method_26204() == class_2246.field_10105)
/*     */               { anvilC++; }
/*     */               else if (state.method_26204() == class_2246.field_10414)
/*     */               { anvilD++; }
/*     */               else
/*     */               { if (direction == class_2350.field_11033)
/*     */                   return;  if (validHole(blockPos.method_10093(direction)) && air == null) {
/*     */                   for (class_2350 dir : class_2350.values()) {
/*     */                     if (dir != direction.method_10153() && dir != class_2350.field_11036) {
/*     */                       class_2680 blockState1 = this.mc.field_1687.method_8320(blockPos.method_10093(direction).method_10093(dir)); if (blockState1.method_26204() == class_2246.field_9987) {
/*     */                         bedrock++;
/*     */                       } else if (blockState1.method_26204() == class_2246.field_10540) {
/*     */                         obsidian++;
/*     */                       } else if (blockState1.method_26204() == class_2246.field_23152) {
/*     */                         respawn_anchor++;
/*     */                       } else if (blockState1.method_26204() == class_2246.field_22108) {
/*     */                         netherite_block++;
/*     */                       } else if (blockState1.method_26204() == class_2246.field_22423) {
/*     */                         crying_obi++;
/*     */                       } else if (blockState1.method_26204() == class_2246.field_10443) {
/*     */                         ender_chest++;
/*     */                       } else if (blockState1.method_26204() == class_2246.field_22109) {
/*     */                         ancient_debris++;
/*     */                       } else if (blockState1.method_26204() == class_2246.field_10485) {
/*     */                         enchanting_table++;
/*     */                       } else if (blockState1.method_26204() == class_2246.field_10535) {
/*     */                         anvil++;
/*     */                       } else if (blockState1.method_26204() == class_2246.field_10105) {
/*     */                         anvilC++;
/*     */                       } else if (blockState1.method_26204() == class_2246.field_10414) {
/*     */                         anvilD++;
/*     */                       } else {
/*     */                         return;
/*     */                       } 
/*     */                     } 
/*     */                   }  air = direction;
/*     */                 }  }
/*     */                }
/*     */              }
/*     */            if (obsidian + respawn_anchor + netherite_block + crying_obi + ender_chest + ancient_debris + enchanting_table + anvil == 5 && air == null) {
/*     */             this.holes.add(((Hole)this.holePool.get()).set(blockPos, (obsidian == 5) ? Hole.Type.Obsidian : ((respawn_anchor == 5) ? Hole.Type.Respawn_anchor : ((netherite_block == 5) ? Hole.Type.Netherite_block : ((crying_obi == 5) ? Hole.Type.Crying_obi : ((ender_chest == 5) ? Hole.Type.Ender_chest : ((ancient_debris == 5) ? Hole.Type.Ancient_debris : ((enchanting_table == 5) ? Hole.Type.Enchanting_table : ((anvil == 5) ? Hole.Type.Anvil : ((anvilC == 5) ? Hole.Type.Chipped_anvil : ((anvilD == 5) ? Hole.Type.Damaged_anvil : Hole.Type.MixedNoBed))))))))), (byte)0));
/*     */           } else if (obsidian + bedrock + respawn_anchor + netherite_block + crying_obi + ender_chest + ancient_debris + enchanting_table + anvil == 5 && air == null) {
/*     */             this.holes.add(((Hole)this.holePool.get()).set(blockPos, (obsidian == 5) ? Hole.Type.Obsidian : ((respawn_anchor == 5) ? Hole.Type.Respawn_anchor : ((netherite_block == 5) ? Hole.Type.Netherite_block : ((crying_obi == 5) ? Hole.Type.Crying_obi : ((ender_chest == 5) ? Hole.Type.Ender_chest : ((ancient_debris == 5) ? Hole.Type.Ancient_debris : ((enchanting_table == 5) ? Hole.Type.Enchanting_table : ((anvil == 5) ? Hole.Type.Anvil : ((anvilC == 5) ? Hole.Type.Chipped_anvil : ((anvilD == 5) ? Hole.Type.Damaged_anvil : ((bedrock == 5) ? Hole.Type.Bedrock : Hole.Type.Mixed)))))))))), (byte)0));
/*     */           } else if (obsidian + bedrock + respawn_anchor + netherite_block + crying_obi + ender_chest + ancient_debris + enchanting_table + anvil == 8 && ((Boolean)this.doubles.get()).booleanValue() && air != null) {
/*     */             this.holes.add(((Hole)this.holePool.get()).set(blockPos, (obsidian == 8) ? Hole.Type.Obsidian : ((respawn_anchor == 8) ? Hole.Type.Respawn_anchor : ((netherite_block == 8) ? Hole.Type.Netherite_block : ((crying_obi == 8) ? Hole.Type.Crying_obi : ((ender_chest == 8) ? Hole.Type.Ender_chest : ((ancient_debris == 8) ? Hole.Type.Ancient_debris : ((enchanting_table == 8) ? Hole.Type.Enchanting_table : ((anvil == 8) ? Hole.Type.Anvil : ((anvilC == 5) ? Hole.Type.Chipped_anvil : ((anvilD == 5) ? Hole.Type.Damaged_anvil : ((bedrock == 8) ? Hole.Type.Bedrock : ((bedrock == 1) ? Hole.Type.Mixed : Hole.Type.Mixed))))))))))), Dir.get(air)));
/*     */           } 
/* 233 */         }); } private boolean validHole(class_2338 pos) { if (((Boolean)this.ignoreOwn.get()).booleanValue() && this.mc.field_1724.method_24515().equals(pos)) return false;
/*     */     
/* 235 */     if (!((Boolean)this.webs.get()).booleanValue() && this.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_10343) return false;
/*     */     
/* 237 */     if (((AbstractBlockAccessor)this.mc.field_1687.method_8320(pos).method_26204()).isCollidable()) return false;
/*     */     
/* 239 */     for (int i = 0; i < ((Integer)this.holeHeight.get()).intValue(); i++) {
/* 240 */       if (((AbstractBlockAccessor)this.mc.field_1687.method_8320(pos.method_10086(i)).method_26204()).isCollidable()) return false;
/*     */     
/*     */     } 
/* 243 */     return true; }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(RenderEvent event) {
/* 248 */     this.LINES.begin(event, DrawMode.Lines, class_290.field_1576);
/* 249 */     this.SIDES.begin(event, DrawMode.Triangles, class_290.field_1576);
/*     */     
/* 251 */     for (Hole hole : this.holes) hole.render(this.LINES, this.SIDES, (ShapeMode)this.shapeMode.get(), ((Double)this.height.get()).doubleValue(), ((Boolean)this.topQuad.get()).booleanValue(), ((Boolean)this.bottomQuad.get()).booleanValue());
/*     */     
/* 253 */     this.LINES.end();
/* 254 */     this.SIDES.end();
/*     */   }
/*     */   
/*     */   private static class Hole {
/* 258 */     public class_2338.class_2339 blockPos = new class_2338.class_2339();
/*     */     public byte exclude;
/*     */     public Type type;
/*     */     
/*     */     public Hole set(class_2338 blockPos, Type type, byte exclude) {
/* 263 */       this.blockPos.method_10101((class_2382)blockPos);
/* 264 */       this.exclude = exclude;
/* 265 */       this.type = type;
/*     */       
/* 267 */       return this;
/*     */     }
/*     */     
/*     */     public Color getTopColor() {
/* 271 */       switch (this.type) {
/*     */         case Obsidian:
/*     */         case Crying_obi:
/*     */         case Netherite_block:
/*     */         case Respawn_anchor:
/*     */         case Ender_chest:
/*     */         case Ancient_debris:
/*     */         case Enchanting_table:
/*     */         case Anvil:
/*     */         case Chipped_anvil:
/*     */         case Damaged_anvil:
/*     */         case MixedNoBed:
/* 283 */           return (Color)((HoleESPPlus)Modules.get().get(HoleESPPlus.class)).obsidianColorTop.get();
/* 284 */         case Bedrock: return (Color)((HoleESPPlus)Modules.get().get(HoleESPPlus.class)).bedrockColorTop.get();
/*     */       } 
/* 286 */       return (Color)((HoleESPPlus)Modules.get().get(HoleESPPlus.class)).mixedColorTop.get();
/*     */     }
/*     */ 
/*     */     
/*     */     public Color getBottomColor() {
/* 291 */       switch (this.type) {
/*     */         case Obsidian:
/*     */         case Crying_obi:
/*     */         case Netherite_block:
/*     */         case Respawn_anchor:
/*     */         case Ender_chest:
/*     */         case Ancient_debris:
/*     */         case Enchanting_table:
/*     */         case Anvil:
/*     */         case Chipped_anvil:
/*     */         case Damaged_anvil:
/*     */         case MixedNoBed:
/* 303 */           return (Color)((HoleESPPlus)Modules.get().get(HoleESPPlus.class)).obsidianColorBottom.get();
/* 304 */         case Bedrock: return (Color)((HoleESPPlus)Modules.get().get(HoleESPPlus.class)).bedrockColorBottom.get();
/* 305 */       }  return (Color)((HoleESPPlus)Modules.get().get(HoleESPPlus.class)).mixedColorBottom.get();
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(MeshBuilder lines, MeshBuilder sides, ShapeMode mode, double height, boolean topQuad, boolean bottomQuad) {
/* 310 */       int x = this.blockPos.method_10263();
/* 311 */       int y = this.blockPos.method_10264();
/* 312 */       int z = this.blockPos.method_10260();
/*     */       
/* 314 */       Color top = getTopColor();
/* 315 */       Color bottom = getBottomColor();
/*     */       
/* 317 */       int originalTopA = top.a;
/* 318 */       int originalBottompA = bottom.a;
/*     */       
/* 320 */       if (mode != ShapeMode.Lines) {
/* 321 */         top.a = originalTopA / 2;
/* 322 */         bottom.a = originalBottompA / 2;
/*     */         
/* 324 */         if (Dir.is(this.exclude, (byte)2) && topQuad) sides.quad(x, y + height, z, x, y + height, (z + 1), (x + 1), y + height, (z + 1), (x + 1), y + height, z, top); 
/* 325 */         if (Dir.is(this.exclude, (byte)4) && bottomQuad) sides.quad(x, y, z, x, y, (z + 1), (x + 1), y, (z + 1), (x + 1), y, z, bottom);
/*     */         
/* 327 */         if (Dir.is(this.exclude, (byte)8)) sides.verticalGradientQuad(x, y + height, z, (x + 1), y + height, z, (x + 1), y, z, x, y, z, top, bottom); 
/* 328 */         if (Dir.is(this.exclude, (byte)16)) sides.verticalGradientQuad(x, y + height, (z + 1), (x + 1), y + height, (z + 1), (x + 1), y, (z + 1), x, y, (z + 1), top, bottom);
/*     */         
/* 330 */         if (Dir.is(this.exclude, (byte)32)) sides.verticalGradientQuad(x, y + height, z, x, y + height, (z + 1), x, y, (z + 1), x, y, z, top, bottom); 
/* 331 */         if (Dir.is(this.exclude, (byte)64)) sides.verticalGradientQuad((x + 1), y + height, z, (x + 1), y + height, (z + 1), (x + 1), y, (z + 1), (x + 1), y, z, top, bottom);
/*     */         
/* 333 */         top.a = originalTopA;
/* 334 */         bottom.a = originalBottompA;
/*     */       } 
/* 336 */       if (mode != ShapeMode.Sides) {
/* 337 */         if (Dir.is(this.exclude, (byte)32) && Dir.is(this.exclude, (byte)8)) lines.line(x, y, z, x, y + height, z, bottom, top); 
/* 338 */         if (Dir.is(this.exclude, (byte)32) && Dir.is(this.exclude, (byte)16)) lines.line(x, y, (z + 1), x, y + height, (z + 1), bottom, top); 
/* 339 */         if (Dir.is(this.exclude, (byte)64) && Dir.is(this.exclude, (byte)8)) lines.line((x + 1), y, z, (x + 1), y + height, z, bottom, top); 
/* 340 */         if (Dir.is(this.exclude, (byte)64) && Dir.is(this.exclude, (byte)16)) lines.line((x + 1), y, (z + 1), (x + 1), y + height, (z + 1), bottom, top);
/*     */         
/* 342 */         if (Dir.is(this.exclude, (byte)8)) lines.line(x, y, z, (x + 1), y, z, bottom); 
/* 343 */         if (Dir.is(this.exclude, (byte)8)) lines.line(x, y + height, z, (x + 1), y + height, z, top); 
/* 344 */         if (Dir.is(this.exclude, (byte)16)) lines.line(x, y, (z + 1), (x + 1), y, (z + 1), bottom); 
/* 345 */         if (Dir.is(this.exclude, (byte)16)) lines.line(x, y + height, (z + 1), (x + 1), y + height, (z + 1), top);
/*     */         
/* 347 */         if (Dir.is(this.exclude, (byte)32)) lines.line(x, y, z, x, y, (z + 1), bottom); 
/* 348 */         if (Dir.is(this.exclude, (byte)32)) lines.line(x, y + height, z, x, y + height, (z + 1), top); 
/* 349 */         if (Dir.is(this.exclude, (byte)64)) lines.line((x + 1), y, z, (x + 1), y, (z + 1), bottom); 
/* 350 */         if (Dir.is(this.exclude, (byte)64)) lines.line((x + 1), y + height, z, (x + 1), y + height, (z + 1), top); 
/*     */       } 
/*     */     }
/*     */     private Hole() {}
/*     */     
/* 355 */     public enum Type { Bedrock,
/* 356 */       Obsidian,
/* 357 */       Crying_obi,
/* 358 */       Netherite_block,
/* 359 */       Respawn_anchor,
/* 360 */       Ender_chest,
/* 361 */       Ancient_debris,
/* 362 */       Enchanting_table,
/* 363 */       Anvil,
/* 364 */       Chipped_anvil,
/* 365 */       Damaged_anvil,
/* 366 */       MixedNoBed,
/* 367 */       Mixed; } } public enum Type { Bedrock, Obsidian, Crying_obi, Netherite_block, Respawn_anchor, Ender_chest, Ancient_debris, Enchanting_table, Anvil, Chipped_anvil, Damaged_anvil, MixedNoBed, Mixed; }
/*     */ 
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/HoleESPPlus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */