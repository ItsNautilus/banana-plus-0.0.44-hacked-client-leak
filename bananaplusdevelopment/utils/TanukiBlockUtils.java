/*     */ package bananaplusdevelopment.utils;
/*     */ import java.util.ArrayList;
/*     */ import minegame159.meteorclient.mixininterface.IVec3d;
/*     */ import minegame159.meteorclient.utils.player.InvUtils;
/*     */ import minegame159.meteorclient.utils.player.Rotations;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class TanukiBlockUtils {
/*  19 */   private static final class_310 mc = class_310.method_1551();
/*  20 */   private static final class_243 hitPos = new class_243(0.0D, 0.0D, 0.0D);
/*     */   public static boolean place(class_2338 blockPos, class_1268 hand, int slot, boolean rotate, int priority, boolean swing, boolean checkEntity, boolean swap, boolean swapBack) {
/*     */     class_2338 neighbour;
/*  23 */     if (!checkEntity)
/*  24 */     { if (!mc.field_1687.method_8320(blockPos).method_26207().method_15800()) return false;
/*     */        }
/*  26 */     else if (slot == -1 || !canPlace(blockPos)) { return false; }
/*     */     
/*  28 */     class_2350 side = getPlaceSide(blockPos);
/*     */     
/*  30 */     class_243 hitPos = rotate ? new class_243(0.0D, 0.0D, 0.0D) : TanukiBlockUtils.hitPos;
/*     */     
/*  32 */     if (side == null) {
/*  33 */       side = class_2350.field_11036;
/*  34 */       neighbour = blockPos;
/*  35 */       ((IVec3d)hitPos).set(blockPos.method_10263() + 0.5D, blockPos.method_10264() + 0.5D, blockPos.method_10260() + 0.5D);
/*     */     } else {
/*     */       
/*  38 */       neighbour = blockPos.method_10093(side.method_10153());
/*  39 */       ((IVec3d)hitPos).set(neighbour.method_10263() + 0.5D + side.method_10148() * 0.5D, neighbour.method_10264() + 0.5D + side.method_10164() * 0.5D, neighbour.method_10260() + 0.5D + side.method_10165() * 0.5D);
/*     */     } 
/*     */     
/*  42 */     if (rotate) {
/*  43 */       class_2350 s = side;
/*  44 */       Rotations.rotate(Rotations.getYaw(hitPos), Rotations.getPitch(hitPos), priority, () -> place(slot, hitPos, hand, s, neighbour, swing, swap, swapBack));
/*     */     } else {
/*  46 */       place(slot, hitPos, hand, side, neighbour, swing, swap, swapBack);
/*     */     } 
/*  48 */     return true;
/*     */   }
/*     */   public static boolean place(class_2338 blockPos, class_1268 hand, int slot, boolean rotate, int priority, boolean checkEntity) {
/*  51 */     return place(blockPos, hand, slot, rotate, priority, true, checkEntity, true, true);
/*     */   }
/*     */   
/*     */   private static void place(int slot, class_243 hitPos, class_1268 hand, class_2350 side, class_2338 neighbour, boolean swing, boolean swap, boolean swapBack) {
/*  55 */     int preSlot = mc.field_1724.field_7514.field_7545;
/*  56 */     if (swap) InvUtils.swap(slot);
/*     */     
/*  58 */     boolean wasSneaking = mc.field_1724.field_3913.field_3903;
/*  59 */     mc.field_1724.field_3913.field_3903 = false;
/*     */     
/*  61 */     mc.field_1761.method_2896(mc.field_1724, mc.field_1687, hand, new class_3965(hitPos, side, neighbour, false));
/*  62 */     if (swing) { mc.field_1724.method_6104(hand); }
/*  63 */     else { mc.method_1562().method_2883((class_2596)new class_2879(hand)); }
/*     */     
/*  65 */     mc.field_1724.field_3913.field_3903 = wasSneaking;
/*     */     
/*  67 */     if (swapBack) InvUtils.swap(preSlot); 
/*     */   }
/*     */   
/*     */   public static boolean canPlace(class_2338 blockPos) {
/*  71 */     if (blockPos == null) return false;
/*     */ 
/*     */     
/*  74 */     if (class_1937.method_8518(blockPos)) return false;
/*     */ 
/*     */     
/*  77 */     if (!mc.field_1687.method_8320(blockPos).method_26207().method_15800()) return false;
/*     */ 
/*     */     
/*  80 */     return mc.field_1687.method_8628(class_2246.field_10340.method_9564(), blockPos, class_3726.method_16194());
/*     */   }
/*     */   
/*     */   public static boolean isClickable(class_2248 block) {
/*  84 */     boolean clickable = (block instanceof net.minecraft.class_2304 || block instanceof net.minecraft.class_2199 || block instanceof net.minecraft.class_2269 || block instanceof net.minecraft.class_2231 || block instanceof net.minecraft.class_2237 || block instanceof net.minecraft.class_2349 || block instanceof net.minecraft.class_2323 || block instanceof net.minecraft.class_2533);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     return clickable;
/*     */   }
/*     */   
/*     */   private static class_2350 getPlaceSide(class_2338 blockPos) {
/*  97 */     for (class_2350 side : class_2350.values()) {
/*  98 */       class_2338 neighbor = blockPos.method_10093(side);
/*  99 */       class_2350 side2 = side.method_10153();
/*     */       
/* 101 */       class_2680 state = mc.field_1687.method_8320(neighbor);
/*     */ 
/*     */       
/* 104 */       if (!state.method_26215() && !isClickable(state.method_26204()))
/*     */       {
/*     */         
/* 107 */         if (state.method_26227().method_15769())
/*     */         {
/* 109 */           return side2; } 
/*     */       }
/*     */     } 
/* 112 */     return null;
/*     */   }
/*     */   
/*     */   public static ArrayList<class_243> getAreaAsVec3ds(class_2338 centerPos, double l, double d, double h, boolean sphere) {
/* 116 */     ArrayList<class_243> cuboidBlocks = new ArrayList<>(); double i;
/* 117 */     for (i = centerPos.method_10263() - l; i < centerPos.method_10263() + l; i++) {
/* 118 */       double j; for (j = centerPos.method_10264() - d; j < centerPos.method_10264() + d; j++) {
/* 119 */         double k; for (k = centerPos.method_10260() - h; k < centerPos.method_10260() + h; k++) {
/* 120 */           class_243 pos = new class_243(Math.floor(i), Math.floor(j), Math.floor(k));
/* 121 */           cuboidBlocks.add(pos);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     if (sphere) {
/* 127 */       cuboidBlocks.removeIf(pos -> (pos.method_1022(blockPosToVec3d(centerPos)) > l));
/*     */     }
/*     */     
/* 130 */     return cuboidBlocks;
/*     */   }
/*     */   
/*     */   public static class_243 blockPosToVec3d(class_2338 blockPos) {
/* 134 */     return new class_243(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260());
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/TanukiBlockUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */