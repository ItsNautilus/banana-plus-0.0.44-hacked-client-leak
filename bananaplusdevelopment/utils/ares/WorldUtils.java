/*     */ package bananaplusdevelopment.utils.ares;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2828;
/*     */ import net.minecraft.class_2848;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class WorldUtils implements Wrapper {
/*     */   public static boolean placeBlockMainHand(class_2338 pos) {
/*  22 */     return placeBlockMainHand(pos, Boolean.valueOf(true));
/*     */   }
/*     */   public static boolean placeBlockMainHand(class_2338 pos, Boolean rotate) {
/*  25 */     return placeBlockMainHand(pos, rotate, Boolean.valueOf(true));
/*     */   }
/*     */   public static boolean placeBlockMainHand(class_2338 pos, Boolean rotate, Boolean airPlace) {
/*  28 */     return placeBlockMainHand(pos, rotate, airPlace, Boolean.valueOf(false));
/*     */   }
/*     */   public static boolean placeBlockMainHand(class_2338 pos, Boolean rotate, Boolean airPlace, Boolean ignoreEntity) {
/*  31 */     return placeBlockMainHand(pos, rotate, airPlace, ignoreEntity, null);
/*     */   }
/*     */   public static boolean placeBlockMainHand(class_2338 pos, Boolean rotate, Boolean airPlace, Boolean ignoreEntity, class_2350 overrideSide) {
/*  34 */     return placeBlock(class_1268.field_5808, pos, rotate, airPlace, ignoreEntity, overrideSide);
/*     */   }
/*     */   public static boolean placeBlockNoRotate(class_1268 hand, class_2338 pos) {
/*  37 */     return placeBlock(hand, pos, Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public static boolean placeBlock(class_1268 hand, class_2338 pos) {
/*  41 */     placeBlock(hand, pos, Boolean.valueOf(true), Boolean.valueOf(false));
/*  42 */     return true;
/*     */   }
/*     */   public static boolean placeBlock(class_1268 hand, class_2338 pos, Boolean rotate) {
/*  45 */     placeBlock(hand, pos, rotate, Boolean.valueOf(false));
/*  46 */     return true;
/*     */   }
/*     */   public static boolean placeBlock(class_1268 hand, class_2338 pos, Boolean rotate, Boolean airPlace) {
/*  49 */     placeBlock(hand, pos, rotate, airPlace, Boolean.valueOf(false));
/*  50 */     return true;
/*     */   }
/*     */   public static boolean placeBlock(class_1268 hand, class_2338 pos, Boolean rotate, Boolean airPlace, Boolean ignoreEntity) {
/*  53 */     placeBlock(hand, pos, rotate, airPlace, ignoreEntity, null);
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean placeBlock(class_1268 hand, class_2338 pos, Boolean rotate, Boolean airPlace, Boolean ignoreEntity, class_2350 overrideSide) {
/*  59 */     if (ignoreEntity.booleanValue()) {
/*  60 */       if (!MC.field_1687.method_8320(pos).method_26207().method_15800())
/*  61 */         return false; 
/*  62 */     } else if (!MC.field_1687.method_8320(pos).method_26207().method_15800() || !MC.field_1687.method_8628(class_2246.field_10540.method_9564(), pos, class_3726.method_16194())) {
/*  63 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  67 */     class_243 eyesPos = new class_243(MC.field_1724.method_23317(), MC.field_1724.method_23318() + MC.field_1724.method_18381(MC.field_1724.method_18376()), MC.field_1724.method_23321());
/*     */     
/*  69 */     class_243 hitVec = null;
/*  70 */     class_2338 neighbor = null;
/*  71 */     class_2350 side2 = null;
/*     */     
/*  73 */     if (overrideSide != null) {
/*  74 */       neighbor = pos.method_10093(overrideSide.method_10153());
/*  75 */       side2 = overrideSide;
/*     */     } 
/*     */     
/*  78 */     for (class_2350 side : class_2350.values()) {
/*  79 */       if (overrideSide == null) {
/*  80 */         neighbor = pos.method_10093(side);
/*  81 */         side2 = side.method_10153();
/*     */ 
/*     */         
/*  84 */         if (MC.field_1687.method_8320(neighbor).method_26215() || MC.field_1687.method_8320(neighbor).method_26204() instanceof net.minecraft.class_2404) {
/*  85 */           neighbor = null;
/*  86 */           side2 = null;
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*  91 */       hitVec = (new class_243(neighbor.method_10263(), neighbor.method_10264(), neighbor.method_10260())).method_1031(0.5D, 0.5D, 0.5D).method_1019((new class_243(side2.method_23955())).method_1021(0.5D));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (airPlace.booleanValue()) {
/*  97 */       if (hitVec == null) hitVec = new class_243(pos.method_10263(), pos.method_10264(), pos.method_10260()); 
/*  98 */       if (neighbor == null) neighbor = pos; 
/*  99 */       if (side2 == null) side2 = class_2350.field_11036; 
/* 100 */     } else if (hitVec == null || neighbor == null || side2 == null) {
/* 101 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 105 */     double diffX = hitVec.field_1352 - eyesPos.field_1352;
/* 106 */     double diffY = hitVec.field_1351 - eyesPos.field_1351;
/* 107 */     double diffZ = hitVec.field_1350 - eyesPos.field_1350;
/*     */     
/* 109 */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/*     */     
/* 111 */     float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
/* 112 */     float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     float[] rotations = { MC.field_1724.field_6031 + class_3532.method_15393(yaw - MC.field_1724.field_6031), MC.field_1724.field_5965 + class_3532.method_15393(pitch - MC.field_1724.field_5965) };
/*     */     
/* 120 */     if (rotate.booleanValue()) MC.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2831(rotations[0], rotations[1], MC.field_1724.method_24828()));
/*     */     
/* 122 */     MC.field_1724.field_3944.method_2883((class_2596)new class_2848((class_1297)MC.field_1724, class_2848.class_2849.field_12979));
/* 123 */     MC.field_1761.method_2896(MC.field_1724, MC.field_1687, hand, new class_3965(hitVec, side2, neighbor, false));
/* 124 */     MC.field_1724.method_6104(hand);
/* 125 */     MC.field_1724.field_3944.method_2883((class_2596)new class_2848((class_1297)MC.field_1724, class_2848.class_2849.field_12984));
/*     */     
/* 127 */     return true;
/*     */   }
/*     */   
/* 130 */   public static final List<class_2248> NONSOLID_BLOCKS = Arrays.asList(new class_2248[] { class_2246.field_10124, class_2246.field_10164, class_2246.field_10382, class_2246.field_10479, class_2246.field_10597, class_2246.field_10376, class_2246.field_10238, class_2246.field_10477, class_2246.field_10214, class_2246.field_10036, class_2246.field_10243 });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canReplace(class_2338 pos) {
/* 136 */     return (NONSOLID_BLOCKS.contains(MC.field_1687.method_8320(pos).method_26204()) && MC.field_1687.method_8335(null, new class_238(pos)).stream().noneMatch(class_1297::method_5863));
/*     */   }
/*     */   
/*     */   public static void moveEntityWithSpeed(class_1297 entity, double speed, boolean shouldMoveY) {
/* 140 */     float yaw = (float)Math.toRadians(MC.field_1724.field_6031);
/*     */     
/* 142 */     double motionX = 0.0D;
/* 143 */     double motionY = 0.0D;
/* 144 */     double motionZ = 0.0D;
/*     */     
/* 146 */     if (MC.field_1724.field_3913.field_3910) {
/* 147 */       motionX = -(class_3532.method_15374(yaw) * speed);
/* 148 */       motionZ = class_3532.method_15362(yaw) * speed;
/* 149 */     } else if (MC.field_1724.field_3913.field_3909) {
/* 150 */       motionX = class_3532.method_15374(yaw) * speed;
/* 151 */       motionZ = -(class_3532.method_15362(yaw) * speed);
/*     */     } 
/*     */     
/* 154 */     if (MC.field_1724.field_3913.field_3908) {
/* 155 */       motionZ = class_3532.method_15374(yaw) * speed;
/* 156 */       motionX = class_3532.method_15362(yaw) * speed;
/* 157 */     } else if (MC.field_1724.field_3913.field_3906) {
/* 158 */       motionZ = -(class_3532.method_15374(yaw) * speed);
/* 159 */       motionX = -(class_3532.method_15362(yaw) * speed);
/*     */     } 
/*     */     
/* 162 */     if (shouldMoveY) {
/* 163 */       if (MC.field_1724.field_3913.field_3904) {
/* 164 */         motionY = speed;
/* 165 */       } else if (MC.field_1724.field_3913.field_3903) {
/* 166 */         motionY = -speed;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 171 */     if (MC.field_1724.field_3913.field_3910 && MC.field_1724.field_3913.field_3908) {
/* 172 */       motionX = class_3532.method_15362(yaw) * speed - class_3532.method_15374(yaw) * speed;
/* 173 */       motionZ = class_3532.method_15362(yaw) * speed + class_3532.method_15374(yaw) * speed;
/* 174 */     } else if (MC.field_1724.field_3913.field_3908 && MC.field_1724.field_3913.field_3909) {
/* 175 */       motionX = class_3532.method_15362(yaw) * speed + class_3532.method_15374(yaw) * speed;
/* 176 */       motionZ = -(class_3532.method_15362(yaw) * speed) + class_3532.method_15374(yaw) * speed;
/* 177 */     } else if (MC.field_1724.field_3913.field_3909 && MC.field_1724.field_3913.field_3906) {
/* 178 */       motionX = -(class_3532.method_15362(yaw) * speed) + class_3532.method_15374(yaw) * speed;
/* 179 */       motionZ = -(class_3532.method_15362(yaw) * speed) - class_3532.method_15374(yaw) * speed;
/* 180 */     } else if (MC.field_1724.field_3913.field_3906 && MC.field_1724.field_3913.field_3910) {
/* 181 */       motionX = -(class_3532.method_15362(yaw) * speed) - class_3532.method_15374(yaw) * speed;
/* 182 */       motionZ = class_3532.method_15362(yaw) * speed - class_3532.method_15374(yaw) * speed;
/*     */     } 
/*     */     
/* 185 */     entity.method_18800(motionX, motionY, motionZ);
/*     */   }
/*     */   
/*     */   public static List<class_2338> getAllInBox(int x1, int y1, int z1, int x2, int y2, int z2) {
/* 189 */     List<class_2338> list = new ArrayList<>();
/*     */     
/* 191 */     for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); ) { for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); ) { for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); ) { list.add(new class_2338(x, y, z)); z++; }  y++; }  x++; }
/* 192 */      return list;
/*     */   }
/*     */   
/*     */   public static List<class_2338> getBlocksInReachDistance() {
/* 196 */     List<class_2338> cube = new ArrayList<>();
/* 197 */     for (int x = -4; x <= 4; x++) {
/* 198 */       for (int y = -4; y <= 4; y++) {
/* 199 */         for (int z = -4; z <= 4; z++)
/* 200 */           cube.add(MC.field_1724.method_24515().method_10069(x, y, z)); 
/*     */       } 
/* 202 */     }  return (List<class_2338>)cube.stream().filter(pos -> (MC.field_1724.method_5707(new class_243(pos.method_10263() + 0.5D, pos.method_10264() + 0.5D, pos.method_10260())) <= 18.0625D)).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public static double[] calculateLookAt(double px, double py, double pz, class_1657 me) {
/* 207 */     double dirx = me.method_23317() - px;
/* 208 */     double diry = me.method_23318() + me.method_18381(me.method_18376()) - py;
/* 209 */     double dirz = me.method_23321() - pz;
/*     */     
/* 211 */     double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
/*     */     
/* 213 */     dirx /= len;
/* 214 */     diry /= len;
/* 215 */     dirz /= len;
/*     */     
/* 217 */     double pitch = Math.asin(diry);
/* 218 */     double yaw = Math.atan2(dirz, dirx);
/*     */ 
/*     */     
/* 221 */     pitch = pitch * 180.0D / Math.PI;
/* 222 */     yaw = yaw * 180.0D / Math.PI;
/*     */     
/* 224 */     yaw += 90.0D;
/*     */     
/* 226 */     return new double[] { yaw, pitch };
/*     */   }
/*     */ 
/*     */   
/*     */   public static void rotate(float yaw, float pitch) {
/* 231 */     MC.field_1724.field_6031 = yaw;
/* 232 */     MC.field_1724.field_5965 = pitch;
/*     */   }
/*     */   
/*     */   public static void rotate(double[] rotations) {
/* 236 */     MC.field_1724.field_6031 = (float)rotations[0];
/* 237 */     MC.field_1724.field_5965 = (float)rotations[1];
/*     */   }
/*     */   
/*     */   public static void lookAtBlock(class_2338 blockToLookAt) {
/* 241 */     rotate(calculateLookAt(blockToLookAt.method_10263(), blockToLookAt.method_10264(), blockToLookAt.method_10260(), (class_1657)MC.field_1724));
/*     */   }
/*     */   
/*     */   public static String vectorToString(class_243 vector, boolean... includeY) {
/* 245 */     boolean reallyIncludeY = (includeY.length <= 0 || includeY[0]);
/* 246 */     StringBuilder builder = new StringBuilder();
/* 247 */     builder.append('(');
/* 248 */     builder.append((int)Math.floor(vector.field_1352));
/* 249 */     builder.append(", ");
/* 250 */     if (reallyIncludeY) {
/* 251 */       builder.append((int)Math.floor(vector.field_1351));
/* 252 */       builder.append(", ");
/*     */     } 
/* 254 */     builder.append((int)Math.floor(vector.field_1350));
/* 255 */     builder.append(")");
/* 256 */     return builder.toString();
/*     */   }
/*     */   
/*     */   public static boolean isBot(class_1297 entity) {
/* 260 */     return (entity instanceof class_1657 && entity.method_5756((class_1657)MC.field_1724) && !entity.method_24828() && !entity.method_5863());
/*     */   }
/*     */   
/*     */   public static void fakeJump() {
/* 264 */     MC.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(MC.field_1724.method_23317(), MC.field_1724.method_23318() + 0.4D, MC.field_1724.method_23321(), true));
/* 265 */     MC.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(MC.field_1724.method_23317(), MC.field_1724.method_23318() + 0.75D, MC.field_1724.method_23321(), true));
/* 266 */     MC.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(MC.field_1724.method_23317(), MC.field_1724.method_23318() + 1.01D, MC.field_1724.method_23321(), true));
/* 267 */     MC.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(MC.field_1724.method_23317(), MC.field_1724.method_23318() + 1.15D, MC.field_1724.method_23321(), true));
/*     */   }
/*     */   
/*     */   public static class_2338 roundBlockPos(class_243 vec) {
/* 271 */     return new class_2338(vec.field_1352, (int)Math.round(vec.field_1351), vec.field_1350);
/*     */   }
/*     */   
/*     */   public static void snapPlayer() {
/* 275 */     class_2338 lastPos = MC.field_1724.method_24828() ? roundBlockPos(MC.field_1724.method_19538()) : MC.field_1724.method_24515();
/* 276 */     snapPlayer(lastPos);
/*     */   }
/*     */   public static void snapPlayer(class_2338 lastPos) {
/* 279 */     double xPos = (MC.field_1724.method_19538()).field_1352;
/* 280 */     double zPos = (MC.field_1724.method_19538()).field_1350;
/*     */     
/* 282 */     if (Math.abs(lastPos.method_10263() + 0.5D - (MC.field_1724.method_19538()).field_1352) >= 0.2D) {
/* 283 */       int xDir = (lastPos.method_10263() + 0.5D - (MC.field_1724.method_19538()).field_1352 > 0.0D) ? 1 : -1;
/* 284 */       xPos += 0.3D * xDir;
/*     */     } 
/*     */     
/* 287 */     if (Math.abs(lastPos.method_10260() + 0.5D - (MC.field_1724.method_19538()).field_1350) >= 0.2D) {
/* 288 */       int zDir = (lastPos.method_10260() + 0.5D - (MC.field_1724.method_19538()).field_1350 > 0.0D) ? 1 : -1;
/* 289 */       zPos += 0.3D * zDir;
/*     */     } 
/*     */     
/* 292 */     MC.field_1724.method_18800(0.0D, 0.0D, 0.0D);
/* 293 */     MC.field_1724.method_5814(xPos, MC.field_1724.method_23318(), zPos);
/* 294 */     MC.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(MC.field_1724.method_23317(), MC.field_1724.method_23318(), MC.field_1724.method_23321(), MC.field_1724.method_24828()));
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/ares/WorldUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */