/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.packets.PacketEvent;
/*     */ import minegame159.meteorclient.rendering.Renderer;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.utils.render.color.Color;
/*     */ import minegame159.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_2626;
/*     */ import net.minecraft.class_2637;
/*     */ import net.minecraft.class_2672;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2818;
/*     */ import net.minecraft.class_3610;
/*     */ 
/*     */ public class NewChunks extends Module {
/*  23 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  25 */   private final Setting<Boolean> remove = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  26 */       .name("remove")
/*  27 */       .description("Removes the cached chunks when disabling the module.")
/*  28 */       .defaultValue(true)
/*  29 */       .build());
/*     */ 
/*     */   
/*  32 */   private final Setting<SettingColor> newChunksColor = this.sgGeneral.add((Setting)(new ColorSetting.Builder())
/*  33 */       .name("new-chunks-color")
/*  34 */       .description("Color of the chunks that are (most likely) completely new.")
/*  35 */       .defaultValue(new SettingColor(204, 153, 217))
/*  36 */       .build());
/*     */ 
/*     */   
/*  39 */   private final Setting<SettingColor> oldChunksColor = this.sgGeneral.add((Setting)(new ColorSetting.Builder())
/*  40 */       .name("old-chunks-color")
/*  41 */       .description("Color of the chunks that have (most likely) been loaded before.")
/*  42 */       .defaultValue(new SettingColor(230, 51, 51))
/*  43 */       .build());
/*     */ 
/*     */   
/*  46 */   private Set<class_1923> newChunks = Collections.synchronizedSet(new HashSet<>());
/*  47 */   private Set<class_1923> oldChunks = Collections.synchronizedSet(new HashSet<>());
/*  48 */   private static final class_2350[] searchDirs = new class_2350[] { class_2350.field_11034, class_2350.field_11043, class_2350.field_11039, class_2350.field_11035, class_2350.field_11036 };
/*     */   
/*     */   public NewChunks() {
/*  51 */     super(AddModule.BANANAMINUS, "new-chunks", "Detects completely new chunks using certain traits of them");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/*  56 */     if (((Boolean)this.remove.get()).booleanValue()) {
/*  57 */       this.newChunks.clear();
/*  58 */       this.oldChunks.clear();
/*     */     } 
/*  60 */     super.onDeactivate();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(RenderEvent event) {
/*  65 */     if (((SettingColor)this.newChunksColor.get()).a > 3) {
/*  66 */       synchronized (this.newChunks) {
/*  67 */         for (class_1923 c : this.newChunks) {
/*  68 */           if (this.mc.method_1560().method_24515().method_19771((class_2382)c.method_8323(), 1024.0D)) {
/*  69 */             drawBoxOutline(new class_238(c.method_8323(), c.method_8323().method_10069(16, 0, 16)), (Color)this.newChunksColor.get());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  75 */     if (((SettingColor)this.oldChunksColor.get()).a > 3) {
/*  76 */       synchronized (this.oldChunks) {
/*  77 */         for (class_1923 c : this.oldChunks) {
/*  78 */           if (this.mc.method_1560().method_24515().method_19771((class_2382)c.method_8323(), 1024.0D)) {
/*  79 */             drawBoxOutline(new class_238(c.method_8323(), c.method_8323().method_10069(16, 0, 16)), (Color)this.oldChunksColor.get());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawBoxOutline(class_238 box, Color color) {
/*  87 */     Renderer.boxWithLines(Renderer.NORMAL, Renderer.LINES, box.field_1323, box.field_1322, box.field_1321, box.field_1320, box.field_1325, box.field_1324, new Color(0, 0, 0, 0), color, ShapeMode.Lines, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onReadPacket(PacketEvent.Receive event) {
/*  95 */     if (event.packet instanceof class_2637) {
/*  96 */       class_2637 packet = (class_2637)event.packet;
/*     */       
/*  98 */       packet.method_30621((pos, state) -> {
/*     */             if (!state.method_26227().method_15769() && !state.method_26227().method_15771()) {
/*     */               class_1923 chunkPos = new class_1923(pos);
/*     */ 
/*     */               
/*     */               for (class_2350 dir : searchDirs) {
/*     */                 if (this.mc.field_1687.method_8320(pos.method_10093(dir)).method_26227().method_15771() && !this.oldChunks.contains(chunkPos)) {
/*     */                   this.newChunks.add(chunkPos);
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           });
/* 112 */     } else if (event.packet instanceof class_2626) {
/* 113 */       class_2626 packet = (class_2626)event.packet;
/*     */       
/* 115 */       if (!packet.method_11308().method_26227().method_15769() && !packet.method_11308().method_26227().method_15771()) {
/* 116 */         class_1923 chunkPos = new class_1923(packet.method_11309());
/*     */         
/* 118 */         for (class_2350 dir : searchDirs) {
/* 119 */           if (this.mc.field_1687.method_8320(packet.method_11309().method_10093(dir)).method_26227().method_15771() && !this.oldChunks.contains(chunkPos)) {
/* 120 */             this.newChunks.add(chunkPos);
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/* 127 */     } else if (event.packet instanceof class_2672 && this.mc.field_1687 != null) {
/* 128 */       class_2672 packet = (class_2672)event.packet;
/*     */       
/* 130 */       class_1923 pos = new class_1923(packet.method_11523(), packet.method_11524());
/*     */       
/* 132 */       if (!this.newChunks.contains(pos) && this.mc.field_1687.method_2935().method_12246(packet.method_11523(), packet.method_11524()) == null) {
/* 133 */         class_2818 chunk = new class_2818((class_1937)this.mc.field_1687, pos, null);
/* 134 */         chunk.method_12224(null, packet.method_11521(), new class_2487(), packet.method_11526());
/*     */         
/* 136 */         for (int x = 0; x < 16; x++) {
/* 137 */           for (int y = 0; y < this.mc.field_1687.method_8322(); y++) {
/* 138 */             for (int z = 0; z < 16; z++) {
/* 139 */               class_3610 fluid = chunk.method_12234(x, y, z);
/*     */               
/* 141 */               if (!fluid.method_15769() && !fluid.method_15771()) {
/* 142 */                 this.oldChunks.add(pos);
/*     */                 return;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/NewChunks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */