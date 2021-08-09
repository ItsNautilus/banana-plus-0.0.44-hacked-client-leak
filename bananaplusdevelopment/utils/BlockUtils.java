/*     */ package bananaplusdevelopment.utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.MeteorClient;
/*     */ import minegame159.meteorclient.mixininterface.IVec3d;
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
/*     */ import net.minecraft.class_2879;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3191;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3726;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class BlockUtils {
/*  26 */   private static final class_310 mc = class_310.method_1551();
/*  27 */   private static final class_243 hitPos = new class_243(0.0D, 0.0D, 0.0D);
/*     */   
/*  29 */   private static final ArrayList<class_2338> blocks = new ArrayList<>();
/*     */   
/*  31 */   public static final Map<Integer, class_3191> breakingBlocks = new HashMap<>();
/*     */   
/*     */   public static void init() {
/*  34 */     MeteorClient.EVENT_BUS.subscribe(BlockUtils.class);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onGameLeft(GameLeftEvent event) {
/*  39 */     breakingBlocks.clear();
/*     */   }
/*     */   public static boolean place(class_2338 blockPos, class_1268 hand, int slot, boolean rotate, int priority, boolean swing, boolean checkEntities, boolean swap, boolean swapBack) {
/*     */     class_2338 neighbour;
/*  43 */     if (slot == -1 || !canPlace(blockPos, checkEntities)) return false;
/*     */     
/*  45 */     class_2350 side = getPlaceSide(blockPos);
/*     */     
/*  47 */     class_243 hitPos = rotate ? new class_243(0.0D, 0.0D, 0.0D) : BlockUtils.hitPos;
/*     */     
/*  49 */     if (side == null) {
/*  50 */       side = class_2350.field_11036;
/*  51 */       neighbour = blockPos;
/*  52 */       ((IVec3d)hitPos).set(blockPos.method_10263() + 0.5D, blockPos.method_10264() + 0.5D, blockPos.method_10260() + 0.5D);
/*     */     } else {
/*  54 */       neighbour = blockPos.method_10093(side.method_10153());
/*     */       
/*  56 */       ((IVec3d)hitPos).set(neighbour.method_10263() + 0.5D + side.method_10148() * 0.5D, neighbour.method_10264() + 0.6D + side.method_10164() * 0.5D, neighbour.method_10260() + 0.5D + side.method_10165() * 0.5D);
/*     */     } 
/*     */     
/*  59 */     if (rotate)
/*  60 */     { class_2350 s = side;
/*  61 */       Rotations.rotate(Rotations.getYaw(hitPos), Rotations.getPitch(hitPos), priority, () -> place(slot, hitPos, hand, s, neighbour, swing, swap, swapBack)); }
/*  62 */     else { place(slot, hitPos, hand, side, neighbour, swing, swap, swapBack); }
/*     */     
/*  64 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean place(class_2338 blockPos, class_1268 hand, int slot, boolean rotate, int priority, boolean checkEntities) {
/*  68 */     return place(blockPos, hand, slot, rotate, priority, true, checkEntities, true, true);
/*     */   }
/*     */   
/*     */   private static void place(int slot, class_243 hitPos, class_1268 hand, class_2350 side, class_2338 neighbour, boolean swing, boolean swap, boolean swapBack) {
/*  72 */     int preSlot = mc.field_1724.field_7514.field_7545;
/*  73 */     if (swap) mc.field_1724.field_7514.field_7545 = slot;
/*     */     
/*  75 */     boolean wasSneaking = mc.field_1724.field_3913.field_3903;
/*  76 */     mc.field_1724.field_3913.field_3903 = false;
/*     */     
/*  78 */     mc.field_1761.method_2896(mc.field_1724, mc.field_1687, hand, new class_3965(hitPos, side, neighbour, false));
/*  79 */     if (swing) { mc.field_1724.method_6104(hand); }
/*  80 */     else { mc.method_1562().method_2883((class_2596)new class_2879(hand)); }
/*     */     
/*  82 */     mc.field_1724.field_3913.field_3903 = wasSneaking;
/*     */     
/*  84 */     if (swapBack) mc.field_1724.field_7514.field_7545 = preSlot; 
/*     */   }
/*     */   
/*     */   public static boolean canPlace(class_2338 blockPos, boolean checkEntities) {
/*  88 */     if (blockPos == null) return false;
/*     */ 
/*     */     
/*  91 */     if (class_1937.method_8518(blockPos)) return false;
/*     */ 
/*     */     
/*  94 */     if (!mc.field_1687.method_8320(blockPos).method_26207().method_15800()) return false;
/*     */ 
/*     */     
/*  97 */     return (!checkEntities || mc.field_1687.method_8628(class_2246.field_10340.method_9564(), blockPos, class_3726.method_16194()));
/*     */   }
/*     */   
/*     */   public static boolean canPlace(class_2338 blockPos) {
/* 101 */     return canPlace(blockPos, true);
/*     */   }
/*     */   
/*     */   public static boolean isClickable(class_2248 block) {
/* 105 */     boolean clickable = (block instanceof net.minecraft.class_2304 || block instanceof net.minecraft.class_2199 || block instanceof net.minecraft.class_2269 || block instanceof net.minecraft.class_2231 || block instanceof net.minecraft.class_2237 || block instanceof net.minecraft.class_2349 || block instanceof net.minecraft.class_2323 || block instanceof net.minecraft.class_2428 || block instanceof net.minecraft.class_2533);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     return clickable;
/*     */   }
/*     */   
/*     */   private static class_2350 getPlaceSide(class_2338 blockPos) {
/* 119 */     for (class_2350 side : class_2350.values()) {
/* 120 */       class_2338 neighbor = blockPos.method_10093(side);
/* 121 */       class_2350 side2 = side.method_10153();
/*     */       
/* 123 */       class_2680 state = mc.field_1687.method_8320(neighbor);
/*     */ 
/*     */       
/* 126 */       if (!state.method_26215() && !isClickable(state.method_26204()))
/*     */       {
/*     */         
/* 129 */         if (state.method_26227().method_15769())
/*     */         {
/* 131 */           return side2; } 
/*     */       }
/*     */     } 
/* 134 */     return null;
/*     */   }
/*     */   
/*     */   public static List<class_2338> getSphere(class_2338 centerPos, int radius, int height) {
/* 138 */     blocks.clear();
/*     */     
/* 140 */     for (int i = centerPos.method_10263() - radius; i < centerPos.method_10263() + radius; i++) {
/* 141 */       for (int j = centerPos.method_10264() - height; j < centerPos.method_10264() + height; j++) {
/* 142 */         for (int k = centerPos.method_10260() - radius; k < centerPos.method_10260() + radius; k++) {
/* 143 */           class_2338 pos = new class_2338(i, j, k);
/* 144 */           if (distanceBetween(centerPos, pos) <= radius && !blocks.contains(pos)) blocks.add(pos);
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/* 149 */     return blocks;
/*     */   }
/*     */   
/*     */   public static double distanceBetween(class_2338 blockPos1, class_2338 blockPos2) {
/* 153 */     double d = (blockPos1.method_10263() - blockPos2.method_10263());
/* 154 */     double e = (blockPos1.method_10264() - blockPos2.method_10264());
/* 155 */     double f = (blockPos1.method_10260() - blockPos2.method_10260());
/* 156 */     return class_3532.method_15368(d * d + e * e + f * f);
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/BlockUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */