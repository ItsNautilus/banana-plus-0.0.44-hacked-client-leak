/*     */ package bananaplusdevelopment.addons.modules;
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
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ public class AutoHighway extends Module {
/*     */   private enum Direction {
/*  22 */     SOUTH,
/*  23 */     SOUTH_WEST,
/*  24 */     WEST,
/*  25 */     WEST_NORTH,
/*  26 */     NORTH,
/*  27 */     NORTH_EAST,
/*  28 */     EAST,
/*  29 */     EAST_SOUTH;
/*     */   }
/*     */   
/*  32 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  34 */   private final Setting<Boolean> disableOnJump = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  35 */       .name("disable-on-jump")
/*  36 */       .description("Automatically disables when you jump.")
/*  37 */       .defaultValue(true)
/*  38 */       .build());
/*     */ 
/*     */   
/*  41 */   private final Setting<Integer> size = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/*  42 */       .name("highway-size")
/*  43 */       .description("The size of highway.")
/*  44 */       .defaultValue(3)
/*  45 */       .min(3)
/*  46 */       .sliderMin(3)
/*  47 */       .max(7)
/*  48 */       .sliderMax(7)
/*  49 */       .build());
/*     */ 
/*     */   
/*  52 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  53 */       .name("rotate")
/*  54 */       .description("Automatically faces towards the obsidian being placed.")
/*  55 */       .defaultValue(true)
/*  56 */       .build());
/*     */   
/*     */   private Direction direction;
/*     */   
/*  60 */   private final class_2338.class_2339 blockPos = new class_2338.class_2339();
/*     */   private boolean return_;
/*     */   private int highwaySize;
/*     */   
/*     */   public AutoHighway() {
/*  65 */     super(AddModule.BANANAMINUS, "auto-highway", "Automatically build highway.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  70 */     this.direction = getDirection((class_1657)this.mc.field_1724);
/*  71 */     this.blockPos.method_10101((class_2382)this.mc.field_1724.method_24515());
/*  72 */     changeBlockPos(0, -1, 0);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/*  77 */     if (((Boolean)this.disableOnJump.get()).booleanValue() && this.mc.field_1690.field_1903.method_1434()) {
/*  78 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/*  82 */     if (!InvUtils.findInHotbar(new class_1792[] { class_1802.field_8281 }).found())
/*     */       return; 
/*  84 */     this.highwaySize = getSize();
/*     */     
/*  86 */     this.return_ = false;
/*     */     
/*  88 */     if (getDistance((class_1657)this.mc.field_1724) > 12)
/*     */       return; 
/*  90 */     if (this.direction == Direction.SOUTH) {
/*  91 */       if (this.highwaySize == 3) {
/*  92 */         boolean p1 = place(0, 0, 0);
/*  93 */         if (this.return_)
/*  94 */           return;  boolean p2 = place(1, 0, 0);
/*  95 */         if (this.return_)
/*  96 */           return;  boolean p3 = place(-1, 0, 0);
/*  97 */         if (this.return_)
/*  98 */           return;  boolean p4 = place(-2, 1, 0);
/*  99 */         if (this.return_)
/* 100 */           return;  boolean p5 = place(2, 1, 0);
/* 101 */         if (p1 && p2 && p3 && p4 && p5) nextLayer(); 
/* 102 */       } else if (this.highwaySize == 5) {
/* 103 */         boolean p1 = place(0, 0, 0);
/* 104 */         if (this.return_)
/* 105 */           return;  boolean p2 = place(1, 0, 0);
/* 106 */         if (this.return_)
/* 107 */           return;  boolean p3 = place(-1, 0, 0);
/* 108 */         if (this.return_)
/* 109 */           return;  boolean p4 = place(-2, 0, 0);
/* 110 */         if (this.return_)
/* 111 */           return;  boolean p5 = place(2, 0, 0);
/* 112 */         if (this.return_)
/* 113 */           return;  boolean p6 = place(-3, 1, 0);
/* 114 */         if (this.return_)
/* 115 */           return;  boolean p7 = place(3, 1, 0);
/* 116 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7) nextLayer(); 
/*     */       } else {
/* 118 */         boolean p1 = place(0, 0, 0);
/* 119 */         if (this.return_)
/* 120 */           return;  boolean p2 = place(1, 0, 0);
/* 121 */         if (this.return_)
/* 122 */           return;  boolean p3 = place(-1, 0, 0);
/* 123 */         if (this.return_)
/* 124 */           return;  boolean p4 = place(-2, 0, 0);
/* 125 */         if (this.return_)
/* 126 */           return;  boolean p5 = place(2, 0, 0);
/* 127 */         if (this.return_)
/* 128 */           return;  boolean p6 = place(-3, 0, 0);
/* 129 */         if (this.return_)
/* 130 */           return;  boolean p7 = place(3, 0, 0);
/* 131 */         if (this.return_)
/* 132 */           return;  boolean p8 = place(-4, 1, 0);
/* 133 */         if (this.return_)
/* 134 */           return;  boolean p9 = place(4, 1, 0);
/* 135 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9) nextLayer(); 
/*     */       } 
/* 137 */     } else if (this.direction == Direction.WEST) {
/* 138 */       if (this.highwaySize == 3) {
/* 139 */         boolean p1 = place(0, 0, 0);
/* 140 */         if (this.return_)
/* 141 */           return;  boolean p2 = place(0, 0, 1);
/* 142 */         if (this.return_)
/* 143 */           return;  boolean p3 = place(0, 0, -1);
/* 144 */         if (this.return_)
/* 145 */           return;  boolean p4 = place(0, 1, -2);
/* 146 */         if (this.return_)
/* 147 */           return;  boolean p5 = place(0, 1, 2);
/* 148 */         if (p1 && p2 && p3 && p4 && p5) nextLayer(); 
/* 149 */       } else if (this.highwaySize == 5) {
/* 150 */         boolean p1 = place(0, 0, 0);
/* 151 */         if (this.return_)
/* 152 */           return;  boolean p2 = place(0, 0, 1);
/* 153 */         if (this.return_)
/* 154 */           return;  boolean p3 = place(0, 0, -1);
/* 155 */         if (this.return_)
/* 156 */           return;  boolean p4 = place(0, 0, -2);
/* 157 */         if (this.return_)
/* 158 */           return;  boolean p5 = place(0, 0, 2);
/* 159 */         if (this.return_)
/* 160 */           return;  boolean p6 = place(0, 1, -3);
/* 161 */         if (this.return_)
/* 162 */           return;  boolean p7 = place(0, 1, 3);
/* 163 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7) nextLayer(); 
/*     */       } else {
/* 165 */         boolean p1 = place(0, 0, 0);
/* 166 */         if (this.return_)
/* 167 */           return;  boolean p2 = place(0, 0, 1);
/* 168 */         if (this.return_)
/* 169 */           return;  boolean p3 = place(0, 0, -1);
/* 170 */         if (this.return_)
/* 171 */           return;  boolean p4 = place(0, 0, -2);
/* 172 */         if (this.return_)
/* 173 */           return;  boolean p5 = place(0, 0, 2);
/* 174 */         if (this.return_)
/* 175 */           return;  boolean p6 = place(0, 0, -3);
/* 176 */         if (this.return_)
/* 177 */           return;  boolean p7 = place(0, 0, 3);
/* 178 */         if (this.return_)
/* 179 */           return;  boolean p8 = place(0, 1, -4);
/* 180 */         if (this.return_)
/* 181 */           return;  boolean p9 = place(0, 1, 4);
/* 182 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9) nextLayer(); 
/*     */       } 
/* 184 */     } else if (this.direction == Direction.NORTH) {
/* 185 */       if (this.highwaySize == 3) {
/* 186 */         boolean p1 = place(0, 0, 0);
/* 187 */         if (this.return_)
/* 188 */           return;  boolean p2 = place(1, 0, 0);
/* 189 */         if (this.return_)
/* 190 */           return;  boolean p3 = place(-1, 0, 0);
/* 191 */         if (this.return_)
/* 192 */           return;  boolean p4 = place(-2, 1, 0);
/* 193 */         if (this.return_)
/* 194 */           return;  boolean p5 = place(2, 1, 0);
/* 195 */         if (p1 && p2 && p3 && p4 && p5) nextLayer(); 
/* 196 */       } else if (this.highwaySize == 5) {
/* 197 */         boolean p1 = place(0, 0, 0);
/* 198 */         if (this.return_)
/* 199 */           return;  boolean p2 = place(1, 0, 0);
/* 200 */         if (this.return_)
/* 201 */           return;  boolean p3 = place(-1, 0, 0);
/* 202 */         if (this.return_)
/* 203 */           return;  boolean p4 = place(-2, 0, 0);
/* 204 */         if (this.return_)
/* 205 */           return;  boolean p5 = place(2, 0, 0);
/* 206 */         if (this.return_)
/* 207 */           return;  boolean p6 = place(-3, 1, 0);
/* 208 */         if (this.return_)
/* 209 */           return;  boolean p7 = place(3, 1, 0);
/* 210 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7) nextLayer(); 
/*     */       } else {
/* 212 */         boolean p1 = place(0, 0, 0);
/* 213 */         if (this.return_)
/* 214 */           return;  boolean p2 = place(1, 0, 0);
/* 215 */         if (this.return_)
/* 216 */           return;  boolean p3 = place(-1, 0, 0);
/* 217 */         if (this.return_)
/* 218 */           return;  boolean p4 = place(-2, 0, 0);
/* 219 */         if (this.return_)
/* 220 */           return;  boolean p5 = place(2, 0, 0);
/* 221 */         if (this.return_)
/* 222 */           return;  boolean p6 = place(-3, 0, 0);
/* 223 */         if (this.return_)
/* 224 */           return;  boolean p7 = place(3, 0, 0);
/* 225 */         if (this.return_)
/* 226 */           return;  boolean p8 = place(-4, 1, 0);
/* 227 */         if (this.return_)
/* 228 */           return;  boolean p9 = place(4, 1, 0);
/* 229 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9) nextLayer(); 
/*     */       } 
/* 231 */     } else if (this.direction == Direction.EAST) {
/* 232 */       if (this.highwaySize == 3) {
/* 233 */         boolean p1 = place(0, 0, 0);
/* 234 */         if (this.return_)
/* 235 */           return;  boolean p2 = place(0, 0, 1);
/* 236 */         if (this.return_)
/* 237 */           return;  boolean p3 = place(0, 0, -1);
/* 238 */         if (this.return_)
/* 239 */           return;  boolean p4 = place(0, 1, -2);
/* 240 */         if (this.return_)
/* 241 */           return;  boolean p5 = place(0, 1, 2);
/* 242 */         if (p1 && p2 && p3 && p4 && p5) nextLayer(); 
/* 243 */       } else if (this.highwaySize == 5) {
/* 244 */         boolean p1 = place(0, 0, 0);
/* 245 */         if (this.return_)
/* 246 */           return;  boolean p2 = place(0, 0, 1);
/* 247 */         if (this.return_)
/* 248 */           return;  boolean p3 = place(0, 0, -1);
/* 249 */         if (this.return_)
/* 250 */           return;  boolean p4 = place(0, 0, -2);
/* 251 */         if (this.return_)
/* 252 */           return;  boolean p5 = place(0, 0, 2);
/* 253 */         if (this.return_)
/* 254 */           return;  boolean p6 = place(0, 1, -3);
/* 255 */         if (this.return_)
/* 256 */           return;  boolean p7 = place(0, 1, 3);
/* 257 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7) nextLayer(); 
/*     */       } else {
/* 259 */         boolean p1 = place(0, 0, 0);
/* 260 */         if (this.return_)
/* 261 */           return;  boolean p2 = place(0, 0, 1);
/* 262 */         if (this.return_)
/* 263 */           return;  boolean p3 = place(0, 0, -1);
/* 264 */         if (this.return_)
/* 265 */           return;  boolean p4 = place(0, 0, -2);
/* 266 */         if (this.return_)
/* 267 */           return;  boolean p5 = place(0, 0, 2);
/* 268 */         if (this.return_)
/* 269 */           return;  boolean p6 = place(0, 0, -3);
/* 270 */         if (this.return_)
/* 271 */           return;  boolean p7 = place(0, 0, 3);
/* 272 */         if (this.return_)
/* 273 */           return;  boolean p8 = place(0, 1, -4);
/* 274 */         if (this.return_)
/* 275 */           return;  boolean p9 = place(0, 1, 4);
/* 276 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9) nextLayer(); 
/*     */       } 
/* 278 */     } else if (this.direction == Direction.EAST_SOUTH) {
/* 279 */       if (this.highwaySize == 3) {
/* 280 */         boolean p1 = place(0, 0, 0);
/* 281 */         if (this.return_)
/* 282 */           return;  boolean p2 = place(1, 0, -1);
/* 283 */         if (this.return_)
/* 284 */           return;  boolean p3 = place(-1, 0, 1);
/* 285 */         if (this.return_)
/* 286 */           return;  boolean p4 = place(1, 1, -2);
/* 287 */         if (this.return_)
/* 288 */           return;  boolean p5 = place(-2, 1, 1);
/* 289 */         if (this.return_)
/* 290 */           return;  boolean p6 = place(1, 0, 0);
/* 291 */         if (this.return_)
/* 292 */           return;  boolean p7 = place(0, 0, 1);
/* 293 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7) nextLayer(); 
/* 294 */       } else if (this.highwaySize == 5) {
/* 295 */         boolean p1 = place(0, 0, 0);
/* 296 */         if (this.return_)
/* 297 */           return;  boolean p2 = place(1, 0, -1);
/* 298 */         if (this.return_)
/* 299 */           return;  boolean p3 = place(-1, 0, 1);
/* 300 */         if (this.return_)
/* 301 */           return;  boolean p4 = place(2, 0, -2);
/* 302 */         if (this.return_)
/* 303 */           return;  boolean p5 = place(-2, 0, 2);
/* 304 */         if (this.return_)
/* 305 */           return;  boolean p6 = place(2, 1, -3);
/* 306 */         if (this.return_)
/* 307 */           return;  boolean p7 = place(-3, 1, 2);
/* 308 */         if (this.return_)
/* 309 */           return;  boolean p8 = place(1, 0, 0);
/* 310 */         if (this.return_)
/* 311 */           return;  boolean p9 = place(0, 0, 1);
/* 312 */         if (this.return_)
/* 313 */           return;  boolean p10 = place(2, 0, -1);
/* 314 */         if (this.return_)
/* 315 */           return;  boolean p11 = place(-1, 0, 2);
/* 316 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9 && p10 && p11) nextLayer(); 
/*     */       } else {
/* 318 */         boolean p1 = place(0, 0, 0);
/* 319 */         if (this.return_)
/* 320 */           return;  boolean p2 = place(1, 0, -1);
/* 321 */         if (this.return_)
/* 322 */           return;  boolean p3 = place(-1, 0, 1);
/* 323 */         if (this.return_)
/* 324 */           return;  boolean p4 = place(2, 0, -2);
/* 325 */         if (this.return_)
/* 326 */           return;  boolean p5 = place(-2, 0, 2);
/* 327 */         if (this.return_)
/* 328 */           return;  boolean p6 = place(3, 0, -3);
/* 329 */         if (this.return_)
/* 330 */           return;  boolean p7 = place(-3, 0, 3);
/* 331 */         if (this.return_)
/* 332 */           return;  boolean p8 = place(3, 1, -4);
/* 333 */         if (this.return_)
/* 334 */           return;  boolean p9 = place(-4, 1, 3);
/* 335 */         if (this.return_)
/* 336 */           return;  boolean p10 = place(1, 0, 0);
/* 337 */         if (this.return_)
/* 338 */           return;  boolean p11 = place(0, 0, 1);
/* 339 */         if (this.return_)
/* 340 */           return;  boolean p12 = place(2, 0, -1);
/* 341 */         if (this.return_)
/* 342 */           return;  boolean p13 = place(-1, 0, 2);
/* 343 */         if (this.return_)
/* 344 */           return;  boolean p14 = place(3, 0, -2);
/* 345 */         if (this.return_)
/* 346 */           return;  boolean p15 = place(-2, 0, 3);
/* 347 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9 && p10 && p11 && p12 && p13 && p14 && p15) nextLayer(); 
/*     */       } 
/* 349 */     } else if (this.direction == Direction.SOUTH_WEST) {
/* 350 */       if (this.highwaySize == 3) {
/* 351 */         boolean p1 = place(0, 0, 0);
/* 352 */         if (this.return_)
/* 353 */           return;  boolean p2 = place(-1, 0, -1);
/* 354 */         if (this.return_)
/* 355 */           return;  boolean p3 = place(1, 0, 1);
/* 356 */         if (this.return_)
/* 357 */           return;  boolean p4 = place(-1, 1, -2);
/* 358 */         if (this.return_)
/* 359 */           return;  boolean p5 = place(2, 1, 1);
/* 360 */         if (this.return_)
/* 361 */           return;  boolean p6 = place(-1, 0, 0);
/* 362 */         if (this.return_)
/* 363 */           return;  boolean p7 = place(0, 0, 1);
/* 364 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7) nextLayer(); 
/* 365 */       } else if (this.highwaySize == 5) {
/* 366 */         boolean p1 = place(0, 0, 0);
/* 367 */         if (this.return_)
/* 368 */           return;  boolean p2 = place(-1, 0, -1);
/* 369 */         if (this.return_)
/* 370 */           return;  boolean p3 = place(1, 0, 1);
/* 371 */         if (this.return_)
/* 372 */           return;  boolean p4 = place(-2, 0, -2);
/* 373 */         if (this.return_)
/* 374 */           return;  boolean p5 = place(2, 0, 2);
/* 375 */         if (this.return_)
/* 376 */           return;  boolean p6 = place(-2, 1, -3);
/* 377 */         if (this.return_)
/* 378 */           return;  boolean p7 = place(3, 1, 2);
/* 379 */         if (this.return_)
/* 380 */           return;  boolean p8 = place(-1, 0, 0);
/* 381 */         if (this.return_)
/* 382 */           return;  boolean p9 = place(0, 0, 1);
/* 383 */         if (this.return_)
/* 384 */           return;  boolean p10 = place(-2, 0, -1);
/* 385 */         if (this.return_)
/* 386 */           return;  boolean p11 = place(1, 0, 2);
/* 387 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9 && p10 && p11) nextLayer(); 
/*     */       } else {
/* 389 */         boolean p1 = place(0, 0, 0);
/* 390 */         if (this.return_)
/* 391 */           return;  boolean p2 = place(-1, 0, -1);
/* 392 */         if (this.return_)
/* 393 */           return;  boolean p3 = place(1, 0, 1);
/* 394 */         if (this.return_)
/* 395 */           return;  boolean p4 = place(-2, 0, -2);
/* 396 */         if (this.return_)
/* 397 */           return;  boolean p5 = place(2, 0, 2);
/* 398 */         if (this.return_)
/* 399 */           return;  boolean p6 = place(-3, 0, -3);
/* 400 */         if (this.return_)
/* 401 */           return;  boolean p7 = place(3, 0, 3);
/* 402 */         if (this.return_)
/* 403 */           return;  boolean p8 = place(-3, 1, -4);
/* 404 */         if (this.return_)
/* 405 */           return;  boolean p9 = place(4, 1, 3);
/* 406 */         if (this.return_)
/* 407 */           return;  boolean p10 = place(-1, 0, 0);
/* 408 */         if (this.return_)
/* 409 */           return;  boolean p11 = place(0, 0, 1);
/* 410 */         if (this.return_)
/* 411 */           return;  boolean p12 = place(-2, 0, -1);
/* 412 */         if (this.return_)
/* 413 */           return;  boolean p13 = place(1, 0, 2);
/* 414 */         if (this.return_)
/* 415 */           return;  boolean p14 = place(-3, 0, -2);
/* 416 */         if (this.return_)
/* 417 */           return;  boolean p15 = place(2, 0, 3);
/* 418 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9 && p10 && p11 && p12 && p13 && p14 && p15) nextLayer(); 
/*     */       } 
/* 420 */     } else if (this.direction == Direction.WEST_NORTH) {
/* 421 */       if (this.highwaySize == 3) {
/* 422 */         boolean p1 = place(0, 0, 0);
/* 423 */         if (this.return_)
/* 424 */           return;  boolean p2 = place(-1, 0, 1);
/* 425 */         if (this.return_)
/* 426 */           return;  boolean p3 = place(1, 0, -1);
/* 427 */         if (this.return_)
/* 428 */           return;  boolean p4 = place(-1, 1, 2);
/* 429 */         if (this.return_)
/* 430 */           return;  boolean p5 = place(2, 1, -1);
/* 431 */         if (this.return_)
/* 432 */           return;  boolean p6 = place(-1, 0, 0);
/* 433 */         if (this.return_)
/* 434 */           return;  boolean p7 = place(0, 0, -1);
/* 435 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7) nextLayer(); 
/* 436 */       } else if (this.highwaySize == 5) {
/* 437 */         boolean p1 = place(0, 0, 0);
/* 438 */         if (this.return_)
/* 439 */           return;  boolean p2 = place(-1, 0, 1);
/* 440 */         if (this.return_)
/* 441 */           return;  boolean p3 = place(1, 0, -1);
/* 442 */         if (this.return_)
/* 443 */           return;  boolean p4 = place(-2, 0, 2);
/* 444 */         if (this.return_)
/* 445 */           return;  boolean p5 = place(2, 0, -2);
/* 446 */         if (this.return_)
/* 447 */           return;  boolean p6 = place(-2, 1, 3);
/* 448 */         if (this.return_)
/* 449 */           return;  boolean p7 = place(3, 1, -2);
/* 450 */         if (this.return_)
/* 451 */           return;  boolean p8 = place(-1, 0, 0);
/* 452 */         if (this.return_)
/* 453 */           return;  boolean p9 = place(0, 0, -1);
/* 454 */         if (this.return_)
/* 455 */           return;  boolean p10 = place(-2, 0, 1);
/* 456 */         if (this.return_)
/* 457 */           return;  boolean p11 = place(1, 0, -2);
/* 458 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9 && p10 && p11) nextLayer(); 
/*     */       } else {
/* 460 */         boolean p1 = place(0, 0, 0);
/* 461 */         if (this.return_)
/* 462 */           return;  boolean p2 = place(-1, 0, 1);
/* 463 */         if (this.return_)
/* 464 */           return;  boolean p3 = place(1, 0, -1);
/* 465 */         if (this.return_)
/* 466 */           return;  boolean p4 = place(-2, 0, 2);
/* 467 */         if (this.return_)
/* 468 */           return;  boolean p5 = place(2, 0, -2);
/* 469 */         if (this.return_)
/* 470 */           return;  boolean p6 = place(-3, 0, 3);
/* 471 */         if (this.return_)
/* 472 */           return;  boolean p7 = place(3, 0, -3);
/* 473 */         if (this.return_)
/* 474 */           return;  boolean p8 = place(-3, 1, 4);
/* 475 */         if (this.return_)
/* 476 */           return;  boolean p9 = place(4, 1, -3);
/* 477 */         if (this.return_)
/* 478 */           return;  boolean p10 = place(-1, 0, 0);
/* 479 */         if (this.return_)
/* 480 */           return;  boolean p11 = place(0, 0, -1);
/* 481 */         if (this.return_)
/* 482 */           return;  boolean p12 = place(-2, 0, 1);
/* 483 */         if (this.return_)
/* 484 */           return;  boolean p13 = place(1, 0, -2);
/* 485 */         if (this.return_)
/* 486 */           return;  boolean p14 = place(-3, 0, 2);
/* 487 */         if (this.return_)
/* 488 */           return;  boolean p15 = place(2, 0, -3);
/* 489 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9 && p10 && p11 && p12 && p13 && p14 && p15) nextLayer(); 
/*     */       } 
/* 491 */     } else if (this.direction == Direction.NORTH_EAST) {
/* 492 */       if (this.highwaySize == 3) {
/* 493 */         boolean p1 = place(0, 0, 0);
/* 494 */         if (this.return_)
/* 495 */           return;  boolean p2 = place(1, 0, 1);
/* 496 */         if (this.return_)
/* 497 */           return;  boolean p3 = place(-1, 0, -1);
/* 498 */         if (this.return_)
/* 499 */           return;  boolean p4 = place(1, 1, 2);
/* 500 */         if (this.return_)
/* 501 */           return;  boolean p5 = place(-2, 1, -1);
/* 502 */         if (this.return_)
/* 503 */           return;  boolean p6 = place(1, 0, 0);
/* 504 */         if (this.return_)
/* 505 */           return;  boolean p7 = place(0, 0, -1);
/* 506 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7) nextLayer(); 
/* 507 */       } else if (this.highwaySize == 5) {
/* 508 */         boolean p1 = place(0, 0, 0);
/* 509 */         if (this.return_)
/* 510 */           return;  boolean p2 = place(1, 0, 1);
/* 511 */         if (this.return_)
/* 512 */           return;  boolean p3 = place(-1, 0, -1);
/* 513 */         if (this.return_)
/* 514 */           return;  boolean p4 = place(2, 0, 2);
/* 515 */         if (this.return_)
/* 516 */           return;  boolean p5 = place(-2, 0, -2);
/* 517 */         if (this.return_)
/* 518 */           return;  boolean p6 = place(2, 1, 3);
/* 519 */         if (this.return_)
/* 520 */           return;  boolean p7 = place(-3, 1, -2);
/* 521 */         if (this.return_)
/* 522 */           return;  boolean p8 = place(1, 0, 0);
/* 523 */         if (this.return_)
/* 524 */           return;  boolean p9 = place(0, 0, -1);
/* 525 */         if (this.return_)
/* 526 */           return;  boolean p10 = place(2, 0, 1);
/* 527 */         if (this.return_)
/* 528 */           return;  boolean p11 = place(-1, 0, -2);
/* 529 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9 && p10 && p11) nextLayer(); 
/*     */       } else {
/* 531 */         boolean p1 = place(0, 0, 0);
/* 532 */         if (this.return_)
/* 533 */           return;  boolean p2 = place(1, 0, 1);
/* 534 */         if (this.return_)
/* 535 */           return;  boolean p3 = place(-1, 0, -1);
/* 536 */         if (this.return_)
/* 537 */           return;  boolean p4 = place(2, 0, 2);
/* 538 */         if (this.return_)
/* 539 */           return;  boolean p5 = place(-2, 0, -2);
/* 540 */         if (this.return_)
/* 541 */           return;  boolean p6 = place(3, 0, 3);
/* 542 */         if (this.return_)
/* 543 */           return;  boolean p7 = place(-3, 0, -3);
/* 544 */         if (this.return_)
/* 545 */           return;  boolean p8 = place(3, 1, 4);
/* 546 */         if (this.return_)
/* 547 */           return;  boolean p9 = place(-4, 1, -3);
/* 548 */         if (this.return_)
/* 549 */           return;  boolean p10 = place(1, 0, 0);
/* 550 */         if (this.return_)
/* 551 */           return;  boolean p11 = place(0, 0, -1);
/* 552 */         if (this.return_)
/* 553 */           return;  boolean p12 = place(2, 0, 1);
/* 554 */         if (this.return_)
/* 555 */           return;  boolean p13 = place(-1, 0, -2);
/* 556 */         if (this.return_)
/* 557 */           return;  boolean p14 = place(3, 0, 2);
/* 558 */         if (this.return_)
/* 559 */           return;  boolean p15 = place(-2, 0, -3);
/* 560 */         if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9 && p10 && p11 && p12 && p13 && p14 && p15) nextLayer(); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getDistance(class_1657 player) {
/* 566 */     return (int)Math.round(player.method_5649(this.blockPos.method_10263(), (this.blockPos.method_10264() - player.method_5751()), this.blockPos.method_10260()));
/*     */   }
/*     */   
/*     */   private boolean place(int x, int y, int z) {
/* 570 */     class_2338 placePos = setBlockPos(x, y, z);
/* 571 */     class_2680 blockState = this.mc.field_1687.method_8320(placePos);
/*     */     
/* 573 */     if (!blockState.method_26207().method_15800()) return true;
/*     */     
/* 575 */     FindItemResult slot = InvUtils.find(new class_1792[] { class_1802.field_8281 });
/* 576 */     if (BlockUtils.place(placePos, slot, ((Boolean)this.rotate.get()).booleanValue(), 10, true)) {
/* 577 */       this.return_ = true;
/*     */     }
/*     */     
/* 580 */     return false;
/*     */   }
/*     */   
/*     */   private int getSize() {
/* 584 */     if (((Integer)this.size.get()).intValue() % 2 == 0) return ((Integer)this.size.get()).intValue() - 1; 
/* 585 */     return ((Integer)this.size.get()).intValue();
/*     */   }
/*     */   
/*     */   private void nextLayer() {
/* 589 */     if (this.direction == Direction.SOUTH) { changeBlockPos(0, 0, 1); }
/* 590 */     else if (this.direction == Direction.WEST) { changeBlockPos(-1, 0, 0); }
/* 591 */     else if (this.direction == Direction.NORTH) { changeBlockPos(0, 0, -1); }
/* 592 */     else if (this.direction == Direction.EAST) { changeBlockPos(1, 0, 0); }
/* 593 */     else if (this.direction == Direction.EAST_SOUTH) { changeBlockPos(1, 0, 1); }
/* 594 */     else if (this.direction == Direction.SOUTH_WEST) { changeBlockPos(-1, 0, 1); }
/* 595 */     else if (this.direction == Direction.WEST_NORTH) { changeBlockPos(-1, 0, -1); }
/* 596 */     else if (this.direction == Direction.NORTH_EAST) { changeBlockPos(1, 0, -1); }
/*     */   
/*     */   }
/*     */   private void changeBlockPos(int x, int y, int z) {
/* 600 */     this.blockPos.method_10103(this.blockPos.method_10263() + x, this.blockPos.method_10264() + y, this.blockPos.method_10260() + z);
/*     */   }
/*     */   private class_2338 setBlockPos(int x, int y, int z) {
/* 603 */     return new class_2338(this.blockPos.method_10263() + x, this.blockPos.method_10264() + y, this.blockPos.method_10260() + z);
/*     */   }
/*     */   
/*     */   private Direction getDirection(class_1657 player) {
/* 607 */     double yaw = player.field_6031;
/* 608 */     if (yaw == 0.0D) return Direction.SOUTH; 
/* 609 */     if (yaw < 0.0D) {
/* 610 */       yaw -= (class_3532.method_15384(yaw / 360.0D) * 360);
/* 611 */       if (yaw < -180.0D) {
/* 612 */         yaw = 360.0D + yaw;
/*     */       }
/*     */     } else {
/* 615 */       yaw -= (class_3532.method_15357(yaw / 360.0D) * 360);
/* 616 */       if (yaw > 180.0D) {
/* 617 */         yaw = -360.0D + yaw;
/*     */       }
/*     */     } 
/*     */     
/* 621 */     if (yaw >= 157.5D || yaw < -157.5D) return Direction.NORTH; 
/* 622 */     if (yaw >= -157.5D && yaw < -112.5D) return Direction.NORTH_EAST; 
/* 623 */     if (yaw >= -112.5D && yaw < -67.5D) return Direction.EAST; 
/* 624 */     if (yaw >= -67.5D && yaw < -22.5D) return Direction.EAST_SOUTH; 
/* 625 */     if ((yaw >= -22.5D && yaw <= 0.0D) || (yaw > 0.0D && yaw < 22.5D)) return Direction.SOUTH; 
/* 626 */     if (yaw >= 22.5D && yaw < 67.5D) return Direction.SOUTH_WEST; 
/* 627 */     if (yaw >= 67.5D && yaw < 112.5D) return Direction.WEST; 
/* 628 */     if (yaw >= 112.5D && yaw < 157.5D) return Direction.WEST_NORTH; 
/* 629 */     return Direction.SOUTH;
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/AutoHighway.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */