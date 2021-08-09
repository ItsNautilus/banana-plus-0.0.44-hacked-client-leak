/*     */ package bananaplusdevelopment.utils;
/*     */ 
/*     */ import minegame159.meteorclient.mixin.CreativeInventoryScreenAccessor;
/*     */ import minegame159.meteorclient.mixin.HorseScreenHandlerAccessor;
/*     */ import minegame159.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_1492;
/*     */ import net.minecraft.class_1496;
/*     */ import net.minecraft.class_1501;
/*     */ import net.minecraft.class_1703;
/*     */ import net.minecraft.class_1707;
/*     */ import net.minecraft.class_1761;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlotUtils
/*     */ {
/*     */   public static final int HOTBAR_START = 0;
/*     */   public static final int HOTBAR_END = 8;
/*     */   public static final int OFFHAND = 45;
/*     */   public static final int MAIN_START = 9;
/*     */   public static final int MAIN_END = 35;
/*     */   public static final int ARMOR_START = 36;
/*     */   public static final int ARMOR_END = 39;
/*     */   
/*     */   public static int indexToId(int i) {
/*  30 */     if (Utils.mc.field_1724 == null) return -1; 
/*  31 */     class_1703 handler = Utils.mc.field_1724.field_7512;
/*     */     
/*  33 */     if (handler instanceof net.minecraft.class_1723) return survivalInventory(i); 
/*  34 */     if (handler instanceof net.minecraft.class_481.class_483) return creativeInventory(i); 
/*  35 */     if (handler instanceof class_1707) return genericContainer(i, ((class_1707)handler).method_17388()); 
/*  36 */     if (handler instanceof net.minecraft.class_1714) return craftingTable(i); 
/*  37 */     if (handler instanceof net.minecraft.class_3858) return furnace(i); 
/*  38 */     if (handler instanceof net.minecraft.class_3705) return furnace(i); 
/*  39 */     if (handler instanceof net.minecraft.class_3706) return furnace(i); 
/*  40 */     if (handler instanceof net.minecraft.class_1716) return generic3x3(i); 
/*  41 */     if (handler instanceof net.minecraft.class_1718) return enchantmentTable(i); 
/*  42 */     if (handler instanceof net.minecraft.class_1708) return brewingStand(i); 
/*  43 */     if (handler instanceof net.minecraft.class_1728) return villager(i); 
/*  44 */     if (handler instanceof net.minecraft.class_1704) return beacon(i); 
/*  45 */     if (handler instanceof net.minecraft.class_1706) return anvil(i); 
/*  46 */     if (handler instanceof net.minecraft.class_1722) return hopper(i); 
/*  47 */     if (handler instanceof net.minecraft.class_1733) return genericContainer(i, 3); 
/*  48 */     if (handler instanceof net.minecraft.class_1724) return horse(handler, i); 
/*  49 */     if (handler instanceof net.minecraft.class_3910) return cartographyTable(i); 
/*  50 */     if (handler instanceof net.minecraft.class_3803) return grindstone(i); 
/*  51 */     if (handler instanceof net.minecraft.class_3916) return lectern(); 
/*  52 */     if (handler instanceof net.minecraft.class_1726) return loom(i); 
/*  53 */     if (handler instanceof net.minecraft.class_3971) return stonecutter(i);
/*     */     
/*  55 */     return -1;
/*     */   }
/*     */   
/*     */   private static int survivalInventory(int i) {
/*  59 */     if (isHotbar(i)) return 36 + i; 
/*  60 */     if (isArmor(i)) return 5 + i - 36; 
/*  61 */     return i;
/*     */   }
/*     */   
/*     */   private static int creativeInventory(int i) {
/*  65 */     if (!(Utils.mc.field_1755 instanceof net.minecraft.class_481) || ((CreativeInventoryScreenAccessor)Utils.mc.field_1755).getSelectedTab() != class_1761.field_7918.method_7741()) return -1; 
/*  66 */     return survivalInventory(i);
/*     */   }
/*     */   
/*     */   private static int genericContainer(int i, int rows) {
/*  70 */     if (isHotbar(i)) return (rows + 3) * 9 + i; 
/*  71 */     if (isMain(i)) return rows * 9 + i - 9; 
/*  72 */     return -1;
/*     */   }
/*     */   
/*     */   private static int craftingTable(int i) {
/*  76 */     if (isHotbar(i)) return 37 + i; 
/*  77 */     if (isMain(i)) return i + 1; 
/*  78 */     return -1;
/*     */   }
/*     */   
/*     */   private static int furnace(int i) {
/*  82 */     if (isHotbar(i)) return 30 + i; 
/*  83 */     if (isMain(i)) return 3 + i - 9; 
/*  84 */     return -1;
/*     */   }
/*     */   
/*     */   private static int generic3x3(int i) {
/*  88 */     if (isHotbar(i)) return 36 + i; 
/*  89 */     if (isMain(i)) return i; 
/*  90 */     return -1;
/*     */   }
/*     */   
/*     */   private static int enchantmentTable(int i) {
/*  94 */     if (isHotbar(i)) return 29 + i; 
/*  95 */     if (isMain(i)) return 2 + i - 9; 
/*  96 */     return -1;
/*     */   }
/*     */   
/*     */   private static int brewingStand(int i) {
/* 100 */     if (isHotbar(i)) return 32 + i; 
/* 101 */     if (isMain(i)) return 5 + i - 9; 
/* 102 */     return -1;
/*     */   }
/*     */   
/*     */   private static int villager(int i) {
/* 106 */     if (isHotbar(i)) return 30 + i; 
/* 107 */     if (isMain(i)) return 3 + i - 9; 
/* 108 */     return -1;
/*     */   }
/*     */   
/*     */   private static int beacon(int i) {
/* 112 */     if (isHotbar(i)) return 28 + i; 
/* 113 */     if (isMain(i)) return 1 + i - 9; 
/* 114 */     return -1;
/*     */   }
/*     */   
/*     */   private static int anvil(int i) {
/* 118 */     if (isHotbar(i)) return 30 + i; 
/* 119 */     if (isMain(i)) return 3 + i - 9; 
/* 120 */     return -1;
/*     */   }
/*     */   
/*     */   private static int hopper(int i) {
/* 124 */     if (isHotbar(i)) return 32 + i; 
/* 125 */     if (isMain(i)) return 5 + i - 9; 
/* 126 */     return -1;
/*     */   }
/*     */   
/*     */   private static int horse(class_1703 handler, int i) {
/* 130 */     class_1496 entity = ((HorseScreenHandlerAccessor)handler).getEntity();
/*     */     
/* 132 */     if (entity instanceof class_1501) {
/* 133 */       int strength = ((class_1501)entity).method_6803();
/* 134 */       if (isHotbar(i)) return 2 + 3 * strength + 28 + i; 
/* 135 */       if (isMain(i)) return 2 + 3 * strength + 1 + i - 9;
/*     */     
/* 137 */     } else if (entity instanceof net.minecraft.class_1498 || entity instanceof net.minecraft.class_1506 || entity instanceof net.minecraft.class_1507) {
/* 138 */       if (isHotbar(i)) return 29 + i; 
/* 139 */       if (isMain(i)) return 2 + i - 9;
/*     */     
/* 141 */     } else if (entity instanceof class_1492) {
/* 142 */       boolean chest = ((class_1492)entity).method_6703();
/* 143 */       if (isHotbar(i)) return (chest ? 44 : 29) + i; 
/* 144 */       if (isMain(i)) return (chest ? 17 : 2) + i - 9;
/*     */     
/*     */     } 
/* 147 */     return -1;
/*     */   }
/*     */   
/*     */   private static int cartographyTable(int i) {
/* 151 */     if (isHotbar(i)) return 30 + i; 
/* 152 */     if (isMain(i)) return 3 + i - 9; 
/* 153 */     return -1;
/*     */   }
/*     */   
/*     */   private static int grindstone(int i) {
/* 157 */     if (isHotbar(i)) return 30 + i; 
/* 158 */     if (isMain(i)) return 3 + i - 9; 
/* 159 */     return -1;
/*     */   }
/*     */   
/*     */   private static int lectern() {
/* 163 */     return -1;
/*     */   }
/*     */   
/*     */   private static int loom(int i) {
/* 167 */     if (isHotbar(i)) return 31 + i; 
/* 168 */     if (isMain(i)) return 4 + i - 9; 
/* 169 */     return -1;
/*     */   }
/*     */   
/*     */   private static int stonecutter(int i) {
/* 173 */     if (isHotbar(i)) return 29 + i; 
/* 174 */     if (isMain(i)) return 2 + i - 9; 
/* 175 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isHotbar(int i) {
/* 181 */     return (i >= 0 && i <= 8);
/*     */   }
/*     */   
/*     */   private static boolean isMain(int i) {
/* 185 */     return (i >= 9 && i <= 35);
/*     */   }
/*     */   
/*     */   private static boolean isArmor(int i) {
/* 189 */     return (i >= 36 && i <= 39);
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/SlotUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */