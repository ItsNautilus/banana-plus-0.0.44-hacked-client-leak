/*     */ package bananaplusdevelopment.addons.modules;
/*     */ 
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.modules.Module;
/*     */ import minegame159.meteorclient.utils.player.FindItemResult;
/*     */ import minegame159.meteorclient.utils.player.InvUtils;
/*     */ import minegame159.meteorclient.utils.player.PlayerUtils;
/*     */ import minegame159.meteorclient.utils.world.BlockUtils;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ 
/*     */ public class AutoWither extends Module {
/*  21 */   private SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  23 */   private Setting<Boolean> rotate = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  24 */       .name("rotate")
/*  25 */       .description("Whether or not to rotate while building")
/*  26 */       .defaultValue(true)
/*  27 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AutoWither() {
/*  33 */     super(AddModule.BANANAMINUS, "auto-wither", "Automatically builds withers.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/*  40 */     if (!hasEnoughMaterials()) {
/*  41 */       error("(default)Not enough resources in hotbar", new Object[0]);
/*  42 */       toggle();
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  48 */     class_2350 dir = getDirection(this.mc.field_1773.method_19418().method_19330() % 360.0F);
/*     */ 
/*     */ 
/*     */     
/*  52 */     PlayerUtils.centerPlayer();
/*     */ 
/*     */ 
/*     */     
/*  56 */     class_2338 blockPos = this.mc.field_1724.method_24515();
/*  57 */     blockPos = blockPos.method_10093(dir);
/*     */     
/*  59 */     if (!isValidSpawn(blockPos, dir)) {
/*  60 */       error("(default)Unable to spawn wither, obstructed by non air blocks", new Object[0]);
/*  61 */       toggle();
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  67 */     info("(default)Spawning wither", new Object[0]);
/*  68 */     spawnWither(blockPos, dir);
/*  69 */     toggle();
/*     */   }
/*     */   
/*     */   private boolean hasEnoughMaterials() {
/*  73 */     if ((InvUtils.find(new class_1792[] { class_1802.field_8067 }).getCount() < 4 && InvUtils.find(new class_1792[] { class_1802.field_21999 }).getCount() < 4) || 
/*  74 */       InvUtils.find(new class_1792[] { class_1802.field_8791 }).getCount() < 3) {
/*  75 */       return false;
/*     */     }
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   private class_2350 getDirection(float yaw) {
/*  81 */     if (yaw < 0.0F) yaw += 360.0F;
/*     */     
/*  83 */     if (yaw >= 315.0F || yaw < 45.0F) return class_2350.field_11035; 
/*  84 */     if (yaw < 135.0F) return class_2350.field_11039; 
/*  85 */     if (yaw < 225.0F) return class_2350.field_11043; 
/*  86 */     return class_2350.field_11034;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isValidSpawn(class_2338 blockPos, class_2350 direction) {
/*  95 */     if (blockPos.method_10264() > 252) return false;
/*     */ 
/*     */     
/*  98 */     int widthX = 0;
/*  99 */     int widthZ = 0;
/*     */     
/* 101 */     if (direction == class_2350.field_11034 || direction == class_2350.field_11039) widthZ = 1; 
/* 102 */     if (direction == class_2350.field_11043 || direction == class_2350.field_11035) widthX = 1;
/*     */ 
/*     */ 
/*     */     
/* 106 */     for (int x = blockPos.method_10263() - widthX; x <= blockPos.method_10263() + widthX; x++) {
/* 107 */       for (int z = blockPos.method_10260() - widthZ; z <= blockPos.method_10260(); z++) {
/* 108 */         for (int y = blockPos.method_10264(); y <= blockPos.method_10264() + 2; y++) {
/* 109 */           if (this.mc.field_1687.method_8320(new class_2338(x, y, z)).method_26204() != class_2246.field_10124) return false;
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void spawnWither(class_2338 blockPos, class_2350 direction) {
/* 121 */     FindItemResult findSoulSand = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8067 });
/* 122 */     if (!findSoulSand.found()) InvUtils.findInHotbar(new class_1792[] { class_1802.field_21999 });
/*     */ 
/*     */     
/* 125 */     FindItemResult findWitherSkull = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8791 });
/*     */     
/* 127 */     BlockUtils.place(blockPos, findSoulSand, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/* 128 */     BlockUtils.place(blockPos.method_10084(), findSoulSand, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/*     */     
/* 130 */     if (direction == class_2350.field_11034 || direction == class_2350.field_11039) {
/* 131 */       BlockUtils.place(blockPos.method_10084().method_10095(), findSoulSand, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/* 132 */       BlockUtils.place(blockPos.method_10084().method_10072(), findSoulSand, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/*     */       
/* 134 */       BlockUtils.place(blockPos.method_10084().method_10084(), findWitherSkull, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/* 135 */       BlockUtils.place(blockPos.method_10084().method_10084().method_10095(), findWitherSkull, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/* 136 */       BlockUtils.place(blockPos.method_10084().method_10084().method_10072(), findWitherSkull, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/*     */     }
/* 138 */     else if (direction == class_2350.field_11043 || direction == class_2350.field_11035) {
/* 139 */       BlockUtils.place(blockPos.method_10084().method_10078(), findSoulSand, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/* 140 */       BlockUtils.place(blockPos.method_10084().method_10067(), findSoulSand, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/*     */       
/* 142 */       BlockUtils.place(blockPos.method_10084().method_10084(), findWitherSkull, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/* 143 */       BlockUtils.place(blockPos.method_10084().method_10084().method_10078(), findWitherSkull, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/* 144 */       BlockUtils.place(blockPos.method_10084().method_10084().method_10067(), findWitherSkull, ((Boolean)this.rotate.get()).booleanValue(), -50, true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/AutoWither.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */