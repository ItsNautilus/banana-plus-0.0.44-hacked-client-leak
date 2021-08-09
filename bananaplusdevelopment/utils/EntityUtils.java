/*     */ package bananaplusdevelopment.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import minegame159.meteorclient.utils.Utils;
/*     */ import minegame159.meteorclient.utils.player.PlayerUtils;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1934;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2586;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_3611;
/*     */ import net.minecraft.class_3612;
/*     */ import net.minecraft.class_640;
/*     */ 
/*     */ 
/*     */ public class EntityUtils
/*     */ {
/*     */   public static boolean isAttackable(class_1299<?> type) {
/*  25 */     return (type != class_1299.field_6083 && type != class_1299.field_6122 && type != class_1299.field_6089 && type != class_1299.field_6133 && type != class_1299.field_6052 && type != class_1299.field_6124 && type != class_1299.field_6135 && type != class_1299.field_6082 && type != class_1299.field_6064 && type != class_1299.field_6045 && type != class_1299.field_6127 && type != class_1299.field_6112 && type != class_1299.field_6103 && type != class_1299.field_6044 && type != class_1299.field_6144);
/*     */   }
/*     */   
/*     */   public static float getTotalHealth(class_1657 target) {
/*  29 */     return target.method_6032() + target.method_6067();
/*     */   }
/*     */   
/*     */   public static int getPing(class_1657 player) {
/*  33 */     if (Utils.mc.method_1562() == null) return 0;
/*     */     
/*  35 */     class_640 playerListEntry = Utils.mc.method_1562().method_2871(player.method_5667());
/*  36 */     if (playerListEntry == null) return 0; 
/*  37 */     return playerListEntry.method_2959();
/*     */   }
/*     */   
/*     */   public static class_1934 getGameMode(class_1657 player) {
/*  41 */     if (player == null) return class_1934.field_9219; 
/*  42 */     class_640 playerListEntry = Utils.mc.method_1562().method_2871(player.method_5667());
/*  43 */     if (playerListEntry == null) return class_1934.field_9219; 
/*  44 */     return playerListEntry.method_2958();
/*     */   }
/*     */   
/*     */   public static boolean isAboveWater(class_1297 entity) {
/*  48 */     class_2338.class_2339 blockPos = entity.method_24515().method_25503();
/*     */     
/*  50 */     for (int i = 0; i < 64; i++) {
/*  51 */       class_2680 state = Utils.mc.field_1687.method_8320((class_2338)blockPos);
/*     */       
/*  53 */       if (state.method_26207().method_15801())
/*     */         break; 
/*  55 */       class_3611 fluid = state.method_26227().method_15772();
/*  56 */       if (fluid == class_3612.field_15910 || fluid == class_3612.field_15909) {
/*  57 */         return true;
/*     */       }
/*     */       
/*  60 */       blockPos.method_10100(0, -1, 0);
/*     */     } 
/*     */     
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isInRenderDistance(class_1297 entity) {
/*  67 */     if (entity == null) return false; 
/*  68 */     return isInRenderDistance(entity.method_23317(), entity.method_23321());
/*     */   }
/*     */   
/*     */   public static boolean isInRenderDistance(class_2586 entity) {
/*  72 */     if (entity == null) return false; 
/*  73 */     return isInRenderDistance(entity.method_11016().method_10263(), entity.method_11016().method_10260());
/*     */   }
/*     */   
/*     */   public static boolean isInRenderDistance(class_2338 pos) {
/*  77 */     if (pos == null) return false; 
/*  78 */     return isInRenderDistance(pos.method_10263(), pos.method_10260());
/*     */   }
/*     */   
/*     */   public static boolean isInRenderDistance(double posX, double posZ) {
/*  82 */     double x = Math.abs((Utils.mc.field_1773.method_19418().method_19326()).field_1352 - posX);
/*  83 */     double z = Math.abs((Utils.mc.field_1773.method_19418().method_19326()).field_1350 - posZ);
/*  84 */     double d = ((Utils.mc.field_1690.field_1870 + 1) * 16);
/*     */     
/*  86 */     return (x < d && z < d);
/*     */   }
/*     */   
/*     */   public static List<class_2338> getSurroundBlocks(class_1657 player) {
/*  90 */     if (player == null) return null;
/*     */     
/*  92 */     List<class_2338> positions = new ArrayList<>();
/*     */ 
/*     */     
/*  95 */     for (class_2350 direction : class_2350.values()) {
/*  96 */       if (direction != class_2350.field_11036 && direction != class_2350.field_11033) {
/*     */         
/*  98 */         class_2338 pos = player.method_24515().method_10093(direction);
/*     */         
/* 100 */         if (Utils.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_10540) {
/* 101 */           positions.add(pos);
/*     */         }
/* 103 */         else if (Utils.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_23152) {
/* 104 */           positions.add(pos);
/*     */         }
/* 106 */         else if (Utils.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_22108) {
/* 107 */           positions.add(pos);
/*     */         }
/* 109 */         else if (Utils.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_22423) {
/* 110 */           positions.add(pos);
/*     */         }
/* 112 */         else if (Utils.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_10443) {
/* 113 */           positions.add(pos);
/*     */         }
/* 115 */         else if (Utils.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_22109) {
/* 116 */           positions.add(pos);
/*     */         }
/* 118 */         else if (Utils.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_10485) {
/* 119 */           positions.add(pos);
/*     */         }
/* 121 */         else if (Utils.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_10535) {
/* 122 */           positions.add(pos);
/*     */         }
/* 124 */         else if (Utils.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_10105) {
/* 125 */           positions.add(pos);
/*     */         }
/* 127 */         else if (Utils.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_10414) {
/* 128 */           positions.add(pos);
/*     */         } 
/*     */       } 
/* 131 */     }  return positions;
/*     */   }
/*     */   
/*     */   public static class_2338 getCityBlock(class_1657 player) {
/* 135 */     List<class_2338> posList = getSurroundBlocks(player);
/* 136 */     posList.sort(Comparator.comparingDouble(PlayerUtils::distanceTo));
/* 137 */     return posList.isEmpty() ? null : posList.get(0);
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/EntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */