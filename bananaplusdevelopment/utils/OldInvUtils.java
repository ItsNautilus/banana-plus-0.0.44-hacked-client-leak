/*     */ package bananaplusdevelopment.utils;
/*     */ 
/*     */ import java.util.function.Predicate;
/*     */ import minegame159.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1713;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ 
/*     */ public class OldInvUtils
/*     */ {
/*  13 */   private static final Action ACTION = new Action();
/*     */   
/*  15 */   private static final FindItemResult findItemResult = new FindItemResult();
/*     */ 
/*     */ 
/*     */   
/*     */   public static Action move() {
/*  20 */     ACTION.type = class_1713.field_7790;
/*  21 */     ACTION.two = true;
/*  22 */     return ACTION;
/*     */   }
/*     */   
/*     */   public static Action click() {
/*  26 */     ACTION.type = class_1713.field_7790;
/*  27 */     return ACTION;
/*     */   }
/*     */   
/*     */   public static Action quickMove() {
/*  31 */     ACTION.type = class_1713.field_7794;
/*  32 */     return ACTION;
/*     */   }
/*     */   
/*     */   public static Action drop() {
/*  36 */     ACTION.type = class_1713.field_7795;
/*  37 */     ACTION.data = 1;
/*  38 */     return ACTION;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_1268 getHand(class_1792 item) {
/*  44 */     class_1268 hand = class_1268.field_5808;
/*  45 */     if (Utils.mc.field_1724.method_6079().method_7909() == item) hand = class_1268.field_5810; 
/*  46 */     return hand;
/*     */   }
/*     */   
/*     */   public static class_1268 getHand(Predicate<class_1799> isGood) {
/*  50 */     class_1268 hand = null;
/*  51 */     if (isGood.test(Utils.mc.field_1724.method_6047())) { hand = class_1268.field_5808; }
/*  52 */     else if (isGood.test(Utils.mc.field_1724.method_6079())) { hand = class_1268.field_5810; }
/*     */     
/*  54 */     return hand;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FindItemResult findItemWithCount(class_1792 item) {
/*  60 */     findItemResult.slot = -1;
/*  61 */     findItemResult.count = 0;
/*     */     
/*  63 */     for (int i = 0; i < Utils.mc.field_1724.field_7514.method_5439(); i++) {
/*  64 */       class_1799 itemStack = Utils.mc.field_1724.field_7514.method_5438(i);
/*     */       
/*  66 */       if (itemStack.method_7909() == item) {
/*  67 */         if (!findItemResult.found()) findItemResult.slot = i; 
/*  68 */         findItemResult.count += itemStack.method_7947();
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     return findItemResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int findItemInHotbar(Predicate<class_1799> isGood) {
/*  79 */     return findItem(isGood, 0, 8);
/*     */   }
/*     */   
/*     */   public static int findItemInHotbar(class_1792 item) {
/*  83 */     return findItemInHotbar(itemStack -> (itemStack.method_7909() == item));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findItemInInventory(Predicate<class_1799> isGood) {
/*  88 */     return findItem(isGood, 9, 35);
/*     */   }
/*     */   
/*     */   public static int findItemInInventory(class_1792 item) {
/*  92 */     return findItemInInventory(itemStack -> (itemStack.method_7909() == item));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findItemInWhole(Predicate<class_1799> isGood) {
/*  97 */     return findItem(isGood, 0, 35);
/*     */   }
/*     */   
/*     */   public static int findItemInWhole(class_1792 item) {
/* 101 */     return findItemInWhole(itemStack -> (itemStack.method_7909() == item));
/*     */   }
/*     */   
/*     */   private static int findItem(Predicate<class_1799> isGood, int startSlot, int endSlot) {
/* 105 */     for (int i = startSlot; i <= endSlot; i++) {
/* 106 */       if (isGood.test(Utils.mc.field_1724.field_7514.method_5438(i))) return i; 
/*     */     } 
/* 108 */     return -1;
/*     */   }
/*     */   
/*     */   public static class FindItemResult { public int slot;
/*     */     public int count;
/*     */     
/*     */     public boolean found() {
/* 115 */       return (this.slot != -1);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class Action {
/* 120 */     private class_1713 type = null;
/*     */     private boolean two = false;
/* 122 */     private int from = -1;
/* 123 */     private int to = -1;
/* 124 */     private int data = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isRecursive = false;
/*     */ 
/*     */ 
/*     */     
/*     */     public Action fromId(int id) {
/* 133 */       this.from = id;
/* 134 */       return this;
/*     */     }
/*     */     
/*     */     public Action from(int index) {
/* 138 */       return fromId(SlotUtils.indexToId(index));
/*     */     }
/*     */     
/*     */     public Action fromHotbar(int i) {
/* 142 */       return from(0 + i);
/*     */     }
/*     */     
/*     */     public Action fromOffhand() {
/* 146 */       return from(45);
/*     */     }
/*     */     
/*     */     public Action fromMain(int i) {
/* 150 */       return from(9 + i);
/*     */     }
/*     */     
/*     */     public Action fromArmor(int i) {
/* 154 */       return from(36 + 3 - i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void toId(int id) {
/* 160 */       this.to = id;
/* 161 */       run();
/*     */     }
/*     */     
/*     */     public void to(int index) {
/* 165 */       toId(SlotUtils.indexToId(index));
/*     */     }
/*     */     
/*     */     public void toHotbar(int i) {
/* 169 */       to(0 + i);
/*     */     }
/*     */     
/*     */     public void toOffhand() {
/* 173 */       to(45);
/*     */     }
/*     */     
/*     */     public void toMain(int i) {
/* 177 */       to(9 + i);
/*     */     }
/*     */     
/*     */     public void toArmor(int i) {
/* 181 */       to(36 + 3 - i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void slotId(int id) {
/* 187 */       this.from = this.to = id;
/* 188 */       run();
/*     */     }
/*     */     
/*     */     public void slot(int index) {
/* 192 */       slotId(SlotUtils.indexToId(index));
/*     */     }
/*     */     
/*     */     public void slotHotbar(int i) {
/* 196 */       slot(0 + i);
/*     */     }
/*     */     
/*     */     public void slotOffhand() {
/* 200 */       slot(45);
/*     */     }
/*     */     
/*     */     public void slotMain(int i) {
/* 204 */       slot(9 + i);
/*     */     }
/*     */     
/*     */     public void slotArmor(int i) {
/* 208 */       slot(36 + 3 - i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void run() {
/* 214 */       boolean hadEmptyCursor = Utils.mc.field_1724.field_7514.method_7399().method_7960();
/*     */       
/* 216 */       if (this.type != null && this.from != -1 && this.to != -1) {
/* 217 */         click(this.from);
/* 218 */         if (this.two) click(this.to);
/*     */       
/*     */       } 
/* 221 */       class_1713 preType = this.type;
/* 222 */       boolean preTwo = this.two;
/* 223 */       int preFrom = this.from;
/* 224 */       int preTo = this.to;
/*     */       
/* 226 */       this.type = null;
/* 227 */       this.two = false;
/* 228 */       this.from = -1;
/* 229 */       this.to = -1;
/* 230 */       this.data = 0;
/*     */       
/* 232 */       if (!this.isRecursive && hadEmptyCursor && preType == class_1713.field_7790 && preTwo && preFrom != -1 && preTo != -1 && !Utils.mc.field_1724.field_7514.method_7399().method_7960()) {
/* 233 */         this.isRecursive = true;
/* 234 */         OldInvUtils.click().slotId(preFrom);
/* 235 */         this.isRecursive = false;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void click(int id) {
/* 240 */       Utils.mc.field_1761.method_2906(Utils.mc.field_1724.field_7512.field_7763, id, this.data, this.type, (class_1657)Utils.mc.field_1724);
/*     */     }
/*     */     
/*     */     private Action() {}
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/OldInvUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */