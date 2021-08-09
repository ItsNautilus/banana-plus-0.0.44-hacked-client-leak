/*     */ package bananaplusdevelopment.utils;
/*     */ import baritone.api.BaritoneAPI;
/*     */ import baritone.api.utils.Rotation;
/*     */ import minegame159.meteorclient.mixininterface.IVec3d;
/*     */ import minegame159.meteorclient.utils.misc.BaritoneUtils;
/*     */ import minegame159.meteorclient.utils.misc.Vector2;
/*     */ import minegame159.meteorclient.utils.world.BlockUtils;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2828;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class PlayerUtils {
/*  23 */   private static final class_310 mc = class_310.method_1551();
/*  24 */   private static final class_243 hitPos = new class_243(0.0D, 0.0D, 0.0D);
/*     */   
/*  26 */   private static final double diagonal = 1.0D / Math.sqrt(2.0D);
/*  27 */   private static final class_243 horizontalVelocity = new class_243(0.0D, 0.0D, 0.0D);
/*     */   
/*     */   public static boolean placeBlock(class_2338 blockPos, class_1268 hand) {
/*  30 */     return placeBlock(blockPos, hand, true);
/*     */   }
/*     */   
/*     */   public static boolean placeBlock(class_2338 blockPos, int slot, class_1268 hand) {
/*  34 */     if (slot == -1) return false;
/*     */     
/*  36 */     int preSlot = mc.field_1724.field_7514.field_7545;
/*  37 */     mc.field_1724.field_7514.field_7545 = slot;
/*     */     
/*  39 */     boolean a = placeBlock(blockPos, hand, true);
/*     */     
/*  41 */     mc.field_1724.field_7514.field_7545 = preSlot;
/*  42 */     return a;
/*     */   }
/*     */   
/*     */   public static boolean placeBlock(class_2338 blockPos, class_1268 hand, boolean swing) {
/*  46 */     if (!BlockUtils.canPlace(blockPos)) return false;  class_2350[] arrayOfClass_2350;
/*     */     int i;
/*     */     byte b;
/*  49 */     for (arrayOfClass_2350 = class_2350.values(), i = arrayOfClass_2350.length, b = 0; b < i; ) { class_2350 side = arrayOfClass_2350[b];
/*  50 */       class_2338 neighbor = blockPos.method_10093(side);
/*  51 */       class_2350 side2 = side.method_10153();
/*     */ 
/*     */       
/*  54 */       if (mc.field_1687.method_8320(neighbor).method_26215() || BlockUtils.isClickable(mc.field_1687.method_8320(neighbor).method_26204())) {
/*     */         b++; continue;
/*     */       } 
/*  57 */       ((IVec3d)hitPos).set(neighbor.method_10263() + 0.5D + side2.method_10163().method_10263() * 0.5D, neighbor.method_10264() + 0.5D + side2.method_10163().method_10264() * 0.5D, neighbor.method_10260() + 0.5D + side2.method_10163().method_10260() * 0.5D);
/*     */ 
/*     */       
/*  60 */       boolean wasSneaking = mc.field_1724.field_3913.field_3903;
/*  61 */       mc.field_1724.field_3913.field_3903 = false;
/*     */       
/*  63 */       mc.field_1761.method_2896(mc.field_1724, mc.field_1687, hand, new class_3965(hitPos, side2, neighbor, false));
/*  64 */       if (swing) mc.field_1724.method_6104(hand);
/*     */       
/*  66 */       mc.field_1724.field_3913.field_3903 = wasSneaking;
/*  67 */       return true; }
/*     */ 
/*     */ 
/*     */     
/*  71 */     ((IVec3d)hitPos).set((class_2382)blockPos);
/*     */     
/*  73 */     mc.field_1761.method_2896(mc.field_1724, mc.field_1687, hand, new class_3965(hitPos, class_2350.field_11036, blockPos, false));
/*  74 */     if (swing) mc.field_1724.method_6104(hand);
/*     */     
/*  76 */     return true;
/*     */   }
/*     */   
/*     */   public static class_243 getHorizontalVelocity(double bps) {
/*  80 */     float yaw = mc.field_1724.field_6031;
/*     */     
/*  82 */     if (BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
/*  83 */       Rotation target = BaritoneUtils.getTarget();
/*  84 */       if (target != null) yaw = target.getYaw();
/*     */     
/*     */     } 
/*  87 */     class_243 forward = class_243.method_1030(0.0F, yaw);
/*  88 */     class_243 right = class_243.method_1030(0.0F, yaw + 90.0F);
/*  89 */     double velX = 0.0D;
/*  90 */     double velZ = 0.0D;
/*     */     
/*  92 */     boolean a = false;
/*  93 */     if (mc.field_1724.field_3913.field_3910) {
/*  94 */       velX += forward.field_1352 / 20.0D * bps;
/*  95 */       velZ += forward.field_1350 / 20.0D * bps;
/*  96 */       a = true;
/*     */     } 
/*  98 */     if (mc.field_1724.field_3913.field_3909) {
/*  99 */       velX -= forward.field_1352 / 20.0D * bps;
/* 100 */       velZ -= forward.field_1350 / 20.0D * bps;
/* 101 */       a = true;
/*     */     } 
/*     */     
/* 104 */     boolean b = false;
/* 105 */     if (mc.field_1724.field_3913.field_3906) {
/* 106 */       velX += right.field_1352 / 20.0D * bps;
/* 107 */       velZ += right.field_1350 / 20.0D * bps;
/* 108 */       b = true;
/*     */     } 
/* 110 */     if (mc.field_1724.field_3913.field_3908) {
/* 111 */       velX -= right.field_1352 / 20.0D * bps;
/* 112 */       velZ -= right.field_1350 / 20.0D * bps;
/* 113 */       b = true;
/*     */     } 
/*     */     
/* 116 */     if (a && b) {
/* 117 */       velX *= diagonal;
/* 118 */       velZ *= diagonal;
/*     */     } 
/*     */     
/* 121 */     ((IVec3d)horizontalVelocity).setXZ(velX, velZ);
/* 122 */     return horizontalVelocity;
/*     */   }
/*     */   
/*     */   public static void centerPlayer() {
/* 126 */     double x = class_3532.method_15357(mc.field_1724.method_23317()) + 0.5D;
/* 127 */     double z = class_3532.method_15357(mc.field_1724.method_23321()) + 0.5D;
/* 128 */     mc.field_1724.method_5814(x, mc.field_1724.method_23318(), z);
/* 129 */     mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2829(mc.field_1724.method_23317(), mc.field_1724.method_23318(), mc.field_1724.method_23321(), mc.field_1724.method_24828()));
/*     */   }
/*     */   
/*     */   public static boolean canSeeEntity(class_1297 entity) {
/* 133 */     class_243 vec1 = new class_243(0.0D, 0.0D, 0.0D);
/* 134 */     class_243 vec2 = new class_243(0.0D, 0.0D, 0.0D);
/*     */     
/* 136 */     ((IVec3d)vec1).set(mc.field_1724.method_23317(), mc.field_1724.method_23318() + mc.field_1724.method_5751(), mc.field_1724.method_23321());
/* 137 */     ((IVec3d)vec2).set(entity.method_23317(), entity.method_23318(), entity.method_23321());
/* 138 */     boolean canSeeFeet = (mc.field_1687.method_17742(new class_3959(vec1, vec2, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)mc.field_1724)).method_17783() == class_239.class_240.field_1333);
/*     */     
/* 140 */     ((IVec3d)vec2).set(entity.method_23317(), entity.method_23318() + entity.method_5751(), entity.method_23321());
/* 141 */     boolean canSeeEyes = (mc.field_1687.method_17742(new class_3959(vec1, vec2, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)mc.field_1724)).method_17783() == class_239.class_240.field_1333);
/*     */     
/* 143 */     return (canSeeFeet || canSeeEyes);
/*     */   }
/*     */   
/*     */   public static float[] calculateAngle(class_243 target) {
/* 147 */     assert mc.field_1724 != null;
/* 148 */     class_243 eyesPos = new class_243(mc.field_1724.method_23317(), mc.field_1724.method_23318() + mc.field_1724.method_18381(mc.field_1724.method_18376()), mc.field_1724.method_23321());
/*     */     
/* 150 */     double dX = target.field_1352 - eyesPos.field_1352;
/* 151 */     double dY = (target.field_1351 - eyesPos.field_1351) * -1.0D;
/* 152 */     double dZ = target.field_1350 - eyesPos.field_1350;
/*     */     
/* 154 */     double dist = class_3532.method_15368(dX * dX + dZ * dZ);
/*     */     
/* 156 */     return new float[] { (float)class_3532.method_15338(Math.toDegrees(Math.atan2(dZ, dX)) - 90.0D), (float)class_3532.method_15338(Math.toDegrees(Math.atan2(dY, dist))) };
/*     */   }
/*     */   
/*     */   public static boolean shouldPause(boolean ifBreaking, boolean ifEating, boolean ifDrinking) {
/* 160 */     if (ifBreaking && mc.field_1761.method_2923()) return true; 
/* 161 */     if (ifEating && mc.field_1724.method_6115() && (mc.field_1724.method_6047().method_7909().method_19263() || mc.field_1724.method_6079().method_7909().method_19263())) return true; 
/* 162 */     return (ifDrinking && mc.field_1724.method_6115() && (mc.field_1724.method_6047().method_7909() instanceof net.minecraft.class_1812 || mc.field_1724.method_6079().method_7909() instanceof net.minecraft.class_1812));
/*     */   }
/*     */   
/*     */   public static boolean isMoving() {
/* 166 */     return (mc.field_1724.field_6250 != 0.0F || mc.field_1724.field_6212 != 0.0F);
/*     */   }
/*     */   
/*     */   public static boolean isSprinting() {
/* 170 */     return (mc.field_1724.method_5624() && (mc.field_1724.field_6250 != 0.0F || mc.field_1724.field_6212 != 0.0F));
/*     */   }
/*     */   
/*     */   public static Vector2 transformStrafe(double speed) {
/* 174 */     float forward = mc.field_1724.field_3913.field_3905;
/* 175 */     float side = mc.field_1724.field_3913.field_3907;
/* 176 */     float yaw = mc.field_1724.field_5982 + (mc.field_1724.field_6031 - mc.field_1724.field_5982) * mc.method_1488();
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (forward == 0.0F && side == 0.0F) return new Vector2(0.0D, 0.0D);
/*     */     
/* 182 */     if (forward != 0.0F) {
/* 183 */       if (side >= 1.0F) {
/* 184 */         yaw += ((forward > 0.0F) ? -45 : 45);
/* 185 */         side = 0.0F;
/*     */       
/*     */       }
/* 188 */       else if (side <= -1.0F) {
/* 189 */         yaw += ((forward > 0.0F) ? 45 : -45);
/* 190 */         side = 0.0F;
/*     */       } 
/*     */       
/* 193 */       if (forward > 0.0F) {
/* 194 */         forward = 1.0F;
/*     */       }
/* 196 */       else if (forward < 0.0F) {
/* 197 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/* 200 */     double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 201 */     double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
/*     */     
/* 203 */     double velX = forward * speed * mx + side * speed * mz;
/* 204 */     double velZ = forward * speed * mz - side * speed * mx;
/*     */     
/* 206 */     return new Vector2(velX, velZ);
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/PlayerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */