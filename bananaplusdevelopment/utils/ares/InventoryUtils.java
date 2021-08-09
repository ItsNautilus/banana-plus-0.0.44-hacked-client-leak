/*    */ package bananaplusdevelopment.utils.ares;
/*    */ 
/*    */ import net.minecraft.class_1792;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1922;
/*    */ import net.minecraft.class_1935;
/*    */ import net.minecraft.class_2248;
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ 
/*    */ public class InventoryUtils
/*    */   implements Wrapper
/*    */ {
/*    */   public static int amountInInventory(class_1792 item) {
/* 15 */     int quantity = 0;
/*    */     
/* 17 */     for (int i = 0; i <= 44; i++) {
/* 18 */       class_1799 stackInSlot = MC.field_1724.field_7514.method_5438(i);
/* 19 */       if (stackInSlot.method_7909() == item) quantity += stackInSlot.method_7947();
/*    */     
/*    */     } 
/* 22 */     return quantity;
/*    */   }
/*    */   
/*    */   public static int amountInHotbar(class_1792 item) {
/* 26 */     int quantity = 0;
/*    */     
/* 28 */     for (int i = 0; i <= 9; i++) {
/* 29 */       class_1799 stackInSlot = MC.field_1724.field_7514.method_5438(i);
/* 30 */       if (stackInSlot.method_7909() == item) quantity += stackInSlot.method_7947(); 
/*    */     } 
/* 32 */     if (MC.field_1724.method_6079().method_7909() == item) quantity += MC.field_1724.method_6079().method_7947();
/*    */     
/* 34 */     return quantity;
/*    */   }
/*    */   public static int amountBlockInHotbar(class_2248 block) {
/* 37 */     return amountInHotbar((new class_1799((class_1935)block)).method_7909());
/*    */   }
/*    */   public static int findItem(class_1792 item) {
/* 40 */     int index = -1;
/* 41 */     for (int i = 0; i < 45; i++) {
/* 42 */       if (MC.field_1724.field_7514.method_5438(i).method_7909() == item) {
/* 43 */         index = i;
/*    */         break;
/*    */       } 
/*    */     } 
/* 47 */     return index;
/*    */   }
/*    */   
/*    */   public static int findBlock(class_2248 block) {
/* 51 */     return findItem((new class_1799((class_1935)block)).method_7909());
/*    */   }
/*    */   
/*    */   public static int findItemInHotbar(class_1792 item) {
/* 55 */     int index = -1;
/* 56 */     for (int i = 0; i < 9; i++) {
/* 57 */       if (MC.field_1724.field_7514.method_5438(i).method_7909() == item) {
/* 58 */         index = i;
/*    */         break;
/*    */       } 
/*    */     } 
/* 62 */     return index;
/*    */   }
/*    */   
/*    */   public static int findBlockInHotbar(class_2248 block) {
/* 66 */     return findItemInHotbar((new class_1799((class_1935)block)).method_7909());
/*    */   }
/*    */   
/*    */   public static int getBlockInHotbar() {
/* 70 */     for (int i = 0; i < 9; ) {
/* 71 */       if (MC.field_1724.field_7514
/* 72 */         .method_5438(i) == class_1799.field_8037 || 
/* 73 */         !(MC.field_1724.field_7514.method_5438(i).method_7909() instanceof net.minecraft.class_1747) || 
/* 74 */         !class_2248.method_9503(MC.field_1724.field_7514.method_5438(i).method_7909()).method_9564().method_26234((class_1922)MC.field_1687, new class_2338(0, 0, 0))) {
/*    */         i++; continue;
/*    */       } 
/* 77 */       return i;
/*    */     } 
/*    */     
/* 80 */     return -1;
/*    */   }
/*    */   
/*    */   public static int getBlank() {
/* 84 */     int index = -1;
/* 85 */     for (int i = 0; i < 45; i++) {
/* 86 */       if (MC.field_1724.field_7514.method_5438(i).method_7960()) {
/* 87 */         index = i;
/*    */         break;
/*    */       } 
/*    */     } 
/* 91 */     return index;
/*    */   }
/*    */   
/*    */   public static int getSlotIndex(int index) {
/* 95 */     return (index < 9) ? (index + 36) : index;
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/ares/InventoryUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */