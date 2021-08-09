/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import bananaplusdevelopment.utils.ares.InventoryUtils;
/*     */ import bananaplusdevelopment.utils.ares.Timer;
/*     */ import bananaplusdevelopment.utils.ares.WorldUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.packets.PacketEvent;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.EnumSetting;
/*     */ import minegame159.meteorclient.settings.IntSetting;
/*     */ import minegame159.meteorclient.settings.KeybindSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.modules.Module;
/*     */ import minegame159.meteorclient.systems.modules.Modules;
/*     */ import minegame159.meteorclient.systems.modules.movement.Blink;
/*     */ import minegame159.meteorclient.utils.misc.Keybind;
/*     */ import minegame159.meteorclient.utils.player.ChatUtils;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2620;
/*     */ import net.minecraft.class_746;
/*     */ 
/*     */ public class SurroundPlus extends Module {
/*     */   public static SurroundPlus INSTANCE;
/*  33 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup(); private final Setting<Mode> mode; private final Setting<Keybind> wideKeybind; private final Setting<Keybind> widePlusKeybind; private final Setting<Boolean> doubleHeight; private final Setting<Keybind> doubleHeightKeybind; private final Setting<Primary> primary; private final Setting<Integer> delay; private final Setting<Boolean> onlyGround; private final Setting<Boolean> stayOn; private final Setting<Boolean> snap; private final Setting<Integer> centerDelay; private final Setting<Boolean> placeOnCrystal; private final Setting<Boolean> rotate; private final Setting<Boolean> air; private final Setting<Boolean> allBlocks; private final Setting<Boolean> notifyBreak; private class_2338 lastPos; private int ticks; private boolean hasCentered; private Timer onGroundCenter;
/*     */   private class_2338 prevBreakPos;
/*     */   
/*  36 */   public SurroundPlus() { super(AddModule.BANANAPLUS, "surround+", "Surround :)");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     this.mode = this.sgGeneral.add((Setting)(new EnumSetting.Builder())
/*  44 */         .name("Mode")
/*  45 */         .description("The mode at which Surround operates in.")
/*  46 */         .defaultValue(Mode.Normal)
/*  47 */         .build());
/*     */ 
/*     */     
/*  50 */     this.wideKeybind = this.sgGeneral.add((Setting)(new KeybindSetting.Builder())
/*  51 */         .name("force-russian-keybind")
/*  52 */         .description("turns on Russian surround when held")
/*  53 */         .defaultValue(Keybind.fromKey(-1))
/*  54 */         .build());
/*     */ 
/*     */     
/*  57 */     this.widePlusKeybind = this.sgGeneral.add((Setting)(new KeybindSetting.Builder())
/*  58 */         .name("force-russian+-keybind")
/*  59 */         .description("turns on russian+ when held")
/*  60 */         .defaultValue(Keybind.fromKey(-1))
/*  61 */         .build());
/*     */ 
/*     */     
/*  64 */     this.doubleHeight = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  65 */         .name("double-height")
/*  66 */         .description("Places obsidian on top of the original surround blocks to prevent people from face-placing you.")
/*  67 */         .defaultValue(false)
/*  68 */         .build());
/*     */ 
/*     */     
/*  71 */     this.doubleHeightKeybind = this.sgGeneral.add((Setting)(new KeybindSetting.Builder())
/*  72 */         .name("double-height-keybind")
/*  73 */         .description("turns on double height")
/*  74 */         .defaultValue(Keybind.fromKey(-1))
/*  75 */         .build());
/*     */ 
/*     */     
/*  78 */     this.primary = this.sgGeneral.add((Setting)(new EnumSetting.Builder())
/*  79 */         .name("Primary block")
/*  80 */         .description("Primary block to use.")
/*  81 */         .defaultValue(Primary.Obsidian)
/*  82 */         .build());
/*     */ 
/*     */     
/*  85 */     this.delay = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/*  86 */         .name("Delay")
/*     */         
/*  88 */         .defaultValue(0)
/*  89 */         .sliderMin(0)
/*  90 */         .sliderMax(10)
/*  91 */         .build());
/*     */ 
/*     */     
/*  94 */     this.onlyGround = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  95 */         .name("Only On Ground")
/*     */         
/*  97 */         .defaultValue(false)
/*  98 */         .build());
/*     */ 
/*     */     
/* 101 */     this.stayOn = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 102 */         .name("Blinkers")
/* 103 */         .description("Surround stays on when you are in blink")
/* 104 */         .defaultValue(false)
/* 105 */         .build());
/*     */ 
/*     */     
/* 108 */     this.snap = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 109 */         .name("Center")
/*     */         
/* 111 */         .defaultValue(true)
/* 112 */         .build());
/*     */ 
/*     */     
/* 115 */     this.centerDelay = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/* 116 */         .name("Center Delay")
/*     */         
/* 118 */         .defaultValue(0)
/* 119 */         .sliderMin(0)
/* 120 */         .sliderMax(10)
/* 121 */         .build());
/*     */ 
/*     */     
/* 124 */     this.placeOnCrystal = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 125 */         .name("Place on Crystal")
/*     */         
/* 127 */         .defaultValue(true)
/* 128 */         .build());
/*     */ 
/*     */     
/* 131 */     this.rotate = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 132 */         .name("Rotate")
/*     */         
/* 134 */         .defaultValue(false)
/* 135 */         .build());
/*     */ 
/*     */     
/* 138 */     this.air = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 139 */         .name("AirPlace")
/*     */         
/* 141 */         .defaultValue(true)
/* 142 */         .build());
/*     */ 
/*     */     
/* 145 */     this.allBlocks = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 146 */         .name("Blastproof blocks only")
/*     */         
/* 148 */         .defaultValue(true)
/* 149 */         .build());
/*     */ 
/*     */     
/* 152 */     this.notifyBreak = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 153 */         .name("notify-break")
/* 154 */         .description("Notifies you about who is breaking your surround.")
/* 155 */         .defaultValue(false)
/* 156 */         .build());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     this.lastPos = new class_2338(0, -100, 0);
/* 162 */     this.ticks = 0;
/*     */     
/* 164 */     this.hasCentered = false;
/* 165 */     this.onGroundCenter = new Timer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     this.timeToStart = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     this.doSnap = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     this.prevBreakingPlayer = null; }
/*     */   public enum Mode {
/*     */     Normal, Russian, RussianPlus; }
/*     */   public enum Primary {
/* 353 */     Obsidian, EnderChest, CryingObsidian, NetheriteBlock, AncientDebris, RespawnAnchor, Anvil; } private static final Timer surroundInstanceDelay = new Timer(); int timeToStart; boolean doSnap; class_1657 prevBreakingPlayer; public static void setSurroundWait(int timeToStart) { INSTANCE.timeToStart = timeToStart; } public static void toggleCenter(boolean doSnap) { INSTANCE.doSnap = doSnap; } @EventHandler public void onBreakPacket(PacketEvent.Receive event) { if (((Boolean)this.notifyBreak.get()).booleanValue())
/* 354 */     { assert this.mc.field_1687 != null;
/* 355 */       assert this.mc.field_1724 != null;
/* 356 */       if (event.packet instanceof class_2620)
/* 357 */       { class_2620 bbpp = (class_2620)event.packet;
/* 358 */         class_2338 bbp = bbpp.method_11277();
/*     */         
/* 360 */         class_1657 breakingPlayer = (class_1657)this.mc.field_1687.method_8469(bbpp.method_11280());
/* 361 */         class_2338 playerBlockPos = this.mc.field_1724.method_24515();
/*     */         
/* 363 */         if (bbpp.method_11278() > 0)
/* 364 */           return;  if (bbp.equals(this.prevBreakPos))
/* 365 */           return;  if (breakingPlayer == this.prevBreakingPlayer)
/* 366 */           return;  if (breakingPlayer.equals(this.mc.field_1724))
/*     */           return; 
/* 368 */         if (bbp.equals(playerBlockPos.method_10095())) {
/* 369 */           notifySurroundBreak(class_2350.field_11043, breakingPlayer);
/* 370 */         } else if (bbp.equals(playerBlockPos.method_10078())) {
/* 371 */           notifySurroundBreak(class_2350.field_11034, breakingPlayer);
/* 372 */         } else if (bbp.equals(playerBlockPos.method_10072())) {
/* 373 */           notifySurroundBreak(class_2350.field_11035, breakingPlayer);
/* 374 */         } else if (bbp.equals(playerBlockPos.method_10067())) {
/* 375 */           notifySurroundBreak(class_2350.field_11039, breakingPlayer);
/*     */         } 
/*     */         
/* 378 */         this.prevBreakingPlayer = breakingPlayer;
/*     */         
/* 380 */         this.prevBreakPos = bbp; }  }  }
/*     */   @EventHandler private void onTick(TickEvent.Pre event) { if (this.onGroundCenter.passedTicks(((Integer)this.centerDelay.get()).intValue()) && ((Boolean)this.snap.get()).booleanValue() && this.doSnap && !this.hasCentered && this.mc.field_1724.method_24828()) { WorldUtils.snapPlayer(this.lastPos); this.hasCentered = true; }  if (!this.hasCentered && !this.mc.field_1724.method_24828()) this.onGroundCenter.reset();  class_2338 roundedPos = WorldUtils.roundBlockPos(this.mc.field_1724.method_19538()); if (((Boolean)this.onlyGround.get()).booleanValue() && !this.mc.field_1724.method_24828() && roundedPos.method_10264() <= this.lastPos.method_10264()) this.lastPos = WorldUtils.roundBlockPos(this.mc.field_1724.method_19538());  if (surroundInstanceDelay.passedMillis(this.timeToStart) && (this.mc.field_1724.method_24828() || !((Boolean)this.onlyGround.get()).booleanValue())) { if (((Integer)this.delay.get()).intValue() != 0 && this.ticks++ % ((Integer)this.delay.get()).intValue() != 0) return;  if (!((Blink)Modules.get().get(Blink.class)).isActive() || !((Boolean)this.stayOn.get()).booleanValue()) { class_746 class_746 = this.mc.field_1724; class_2338 locRounded = WorldUtils.roundBlockPos(class_746.method_19538()); if (!this.lastPos.equals(class_746.method_24828() ? locRounded : class_746.method_24515())) { if (((Boolean)this.onlyGround.get()).booleanValue() || (class_746.method_19538()).field_1351 > this.lastPos.method_10264() + 1.5D || ((Math.floor((class_746.method_19538()).field_1352) != this.lastPos.method_10263() || Math.floor((class_746.method_19538()).field_1350) != this.lastPos.method_10260()) && (class_746.method_19538()).field_1351 > this.lastPos.method_10264() + 0.75D) || (!this.mc.field_1687.method_8320(this.lastPos).method_26207().method_15800() && class_746.method_24515() != this.lastPos)) { toggle(); return; }  if (!((Boolean)this.onlyGround.get()).booleanValue() && locRounded.method_10264() <= this.lastPos.method_10264()) this.lastPos = locRounded;  }  }  int obbyIndex = findBlock(); if (obbyIndex == -1) return;  int prevSlot = this.mc.field_1724.field_7514.field_7545; if (needsToPlace()) { for (class_2338 pos : getPositions()) { if (this.mc.field_1687.method_8320(pos).method_26207().method_15800()) this.mc.field_1724.field_7514.field_7545 = obbyIndex;  if (WorldUtils.placeBlockMainHand(pos, (Boolean)this.rotate.get(), (Boolean)this.air.get(), (Boolean)this.placeOnCrystal.get())) if (((Integer)this.delay.get()).intValue() != 0) { this.mc.field_1724.field_7514.field_7545 = prevSlot; return; }   }  this.mc.field_1724.field_7514.field_7545 = prevSlot; }  }  }
/*     */   private boolean needsToPlace() { return anyAir(new class_2338[] { this.lastPos.method_10074(), this.lastPos.method_10095(), this.lastPos.method_10078(), this.lastPos.method_10072(), this.lastPos.method_10067(), this.lastPos.method_10095().method_10084(), this.lastPos.method_10078().method_10084(), this.lastPos.method_10072().method_10084(), this.lastPos.method_10067().method_10084(), this.lastPos.method_10076(2), this.lastPos.method_10089(2), this.lastPos.method_10077(2), this.lastPos.method_10088(2), this.lastPos.method_10095().method_10078(), this.lastPos.method_10078().method_10072(), this.lastPos.method_10072().method_10067(), this.lastPos.method_10067().method_10095() }); }
/*     */   private List<class_2338> getPositions() { List<class_2338> positions = new ArrayList<>(); if (!((Boolean)this.onlyGround.get()).booleanValue()) add(positions, this.lastPos.method_10074());  add(positions, this.lastPos.method_10095()); add(positions, this.lastPos.method_10078()); add(positions, this.lastPos.method_10072()); add(positions, this.lastPos.method_10067()); if (((Boolean)this.doubleHeight.get()).booleanValue() || ((Keybind)this.doubleHeightKeybind.get()).isPressed()) { add(positions, this.lastPos.method_10095().method_10084()); add(positions, this.lastPos.method_10078().method_10084()); add(positions, this.lastPos.method_10072().method_10084()); add(positions, this.lastPos.method_10067().method_10084()); }  if (this.mode.get() != Mode.Normal || ((Keybind)this.wideKeybind.get()).isPressed() || ((Keybind)this.widePlusKeybind.get()).isPressed()) { if (this.mc.field_1687.method_8320(this.lastPos.method_10095()).method_26204() != class_2246.field_9987) add(positions, this.lastPos.method_10076(2));  if (this.mc.field_1687.method_8320(this.lastPos.method_10078()).method_26204() != class_2246.field_9987) add(positions, this.lastPos.method_10089(2));  if (this.mc.field_1687.method_8320(this.lastPos.method_10072()).method_26204() != class_2246.field_9987) add(positions, this.lastPos.method_10077(2));  if (this.mc.field_1687.method_8320(this.lastPos.method_10067()).method_26204() != class_2246.field_9987) add(positions, this.lastPos.method_10088(2));  }  if (this.mode.get() == Mode.RussianPlus || ((Keybind)this.widePlusKeybind.get()).isPressed()) { if (this.mc.field_1687.method_8320(this.lastPos.method_10095()).method_26204() != class_2246.field_9987 || this.mc.field_1687.method_8320(this.lastPos.method_10078()).method_26204() != class_2246.field_9987) add(positions, this.lastPos.method_10095().method_10078());  if (this.mc.field_1687.method_8320(this.lastPos.method_10078()).method_26204() != class_2246.field_9987 || this.mc.field_1687.method_8320(this.lastPos.method_10072()).method_26204() != class_2246.field_9987) add(positions, this.lastPos.method_10078().method_10072());  if (this.mc.field_1687.method_8320(this.lastPos.method_10072()).method_26204() != class_2246.field_9987 || this.mc.field_1687.method_8320(this.lastPos.method_10067()).method_26204() != class_2246.field_9987) add(positions, this.lastPos.method_10072().method_10067());  if (this.mc.field_1687.method_8320(this.lastPos.method_10067()).method_26204() != class_2246.field_9987 || this.mc.field_1687.method_8320(this.lastPos.method_10095()).method_26204() != class_2246.field_9987) add(positions, this.lastPos.method_10067().method_10095());  }  return positions; }
/*     */   private void add(List<class_2338> list, class_2338 pos) { if (this.mc.field_1687.method_8320(pos).method_26215() && allAir(new class_2338[] { pos.method_10095(), pos.method_10078(), pos.method_10072(), pos.method_10067(), pos.method_10084(), pos.method_10074() }) && !((Boolean)this.air.get()).booleanValue()) list.add(pos.method_10074());  list.add(pos); }
/*     */   private boolean allAir(class_2338... pos) { return Arrays.<class_2338>stream(pos).allMatch(blockPos -> this.mc.field_1687.method_8320(blockPos).method_26215()); }
/* 386 */   private boolean anyAir(class_2338... pos) { return Arrays.<class_2338>stream(pos).anyMatch(blockPos -> this.mc.field_1687.method_8320(blockPos).method_26215()); } private class_2248 primaryBlock() { class_2248 index = null; if (this.primary.get() == Primary.Obsidian) { index = class_2246.field_10540; } else if (this.primary.get() == Primary.EnderChest) { index = class_2246.field_10443; } else if (this.primary.get() == Primary.CryingObsidian) { index = class_2246.field_22423; } else if (this.primary.get() == Primary.NetheriteBlock) { index = class_2246.field_22108; } else if (this.primary.get() == Primary.AncientDebris) { index = class_2246.field_22109; } else if (this.primary.get() == Primary.RespawnAnchor) { index = class_2246.field_23152; } else if (this.primary.get() == Primary.Anvil) { index = class_2246.field_10535; }  return index; } private int findBlock() { int index = InventoryUtils.findBlockInHotbar(primaryBlock()); if (index == -1 && ((Boolean)this.allBlocks.get()).booleanValue()) { if (index == -1) index = InventoryUtils.findBlockInHotbar(class_2246.field_10540);  if (index == -1) index = InventoryUtils.findBlockInHotbar(class_2246.field_22423);  if (index == -1) index = InventoryUtils.findBlockInHotbar(class_2246.field_22108);  if (index == -1) index = InventoryUtils.findBlockInHotbar(class_2246.field_22109);  if (index == -1) index = InventoryUtils.findBlockInHotbar(class_2246.field_10443);  if (index == -1) index = InventoryUtils.findBlockInHotbar(class_2246.field_23152);  if (index == -1) index = InventoryUtils.findBlockInHotbar(class_2246.field_10535);  }  return index; } public void onActivate() { this.lastPos = this.mc.field_1724.method_24828() ? WorldUtils.roundBlockPos(this.mc.field_1724.method_19538()) : this.mc.field_1724.method_24515(); } private void notifySurroundBreak(class_2350 direction, class_1657 player) { switch (direction) {
/*     */       case field_11043:
/* 388 */         ChatUtils.warning("Your north surround block is being broken by " + player.method_5820(), new Object[0]);
/*     */         break;
/*     */       
/*     */       case field_11034:
/* 392 */         ChatUtils.warning("Your east surround block is being broken by " + player.method_5820(), new Object[0]);
/*     */         break;
/*     */       
/*     */       case field_11035:
/* 396 */         ChatUtils.warning("Your south surround block is being broken by " + player.method_5820(), new Object[0]);
/*     */         break;
/*     */       
/*     */       case field_11039:
/* 400 */         ChatUtils.warning("Your west surround block is being broken by " + player.method_5820(), new Object[0]);
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 408 */     this.ticks = 0;
/* 409 */     this.doSnap = true;
/* 410 */     this.timeToStart = 0;
/* 411 */     this.hasCentered = false;
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/SurroundPlus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */