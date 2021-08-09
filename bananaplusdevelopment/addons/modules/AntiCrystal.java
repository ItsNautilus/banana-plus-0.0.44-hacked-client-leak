/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BlockListSetting;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.EnumSetting;
/*     */ import minegame159.meteorclient.settings.KeybindSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.utils.misc.Keybind;
/*     */ import minegame159.meteorclient.utils.player.InvUtils;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2680;
/*     */ 
/*     */ public class AntiCrystal extends Module {
/*  22 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*     */   public enum Mode {
/*  25 */     normal,
/*  26 */     wide;
/*     */   }
/*     */   
/*  29 */   private final Setting<Mode> mode = this.sgGeneral.add((Setting)(new EnumSetting.Builder())
/*  30 */       .name("mode")
/*  31 */       .description("The mode at which AntiCrystal operates in. (normal, wide)")
/*  32 */       .defaultValue(Mode.normal)
/*  33 */       .build());
/*     */ 
/*     */   
/*  36 */   private final Setting<Keybind> wideKeybind = this.sgGeneral.add((Setting)(new KeybindSetting.Builder())
/*  37 */       .name("force-wide-keybind")
/*  38 */       .description("turns on wide surround when held")
/*  39 */       .defaultValue(Keybind.fromKey(-1))
/*  40 */       .build());
/*     */ 
/*     */   
/*  43 */   private final Setting<Boolean> antiFacePlace = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  44 */       .name("anti-face-place")
/*  45 */       .description("Places a block on top of the original surround blocks to prevent people from face-placing you.")
/*  46 */       .defaultValue(false)
/*  47 */       .build());
/*     */ 
/*     */   
/*  50 */   private final Setting<Keybind> antiFacePlaceKeybind = this.sgGeneral.add((Setting)(new KeybindSetting.Builder())
/*  51 */       .name("force-anti-face-place-keybind")
/*  52 */       .description("turns on double height")
/*  53 */       .defaultValue(Keybind.fromKey(-1))
/*  54 */       .build());
/*     */ 
/*     */   
/*  57 */   private final Setting<Boolean> onlyOnGround = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  58 */       .name("only-on-ground")
/*  59 */       .description("Works only when you standing on blocks.")
/*  60 */       .defaultValue(true)
/*  61 */       .build());
/*     */ 
/*     */   
/*  64 */   private final Setting<Boolean> onlyWhenSneaking = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  65 */       .name("only-when-sneaking")
/*  66 */       .description("Places blocks only after sneaking.")
/*  67 */       .defaultValue(false)
/*  68 */       .build());
/*     */ 
/*     */   
/*  71 */   private final Setting<Boolean> turnOff = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  72 */       .name("turn-off")
/*  73 */       .description("Toggles off when all blocks are placed.")
/*  74 */       .defaultValue(false)
/*  75 */       .build());
/*     */ 
/*     */   
/*  78 */   private final Setting<Boolean> center = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  79 */       .name("center")
/*  80 */       .description("Teleports you to the center of the block.")
/*  81 */       .defaultValue(true)
/*  82 */       .build());
/*     */ 
/*     */   
/*  85 */   private final Setting<Boolean> disableOnJump = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  86 */       .name("disable-on-jump")
/*  87 */       .description("Automatically disables when you jump.")
/*  88 */       .defaultValue(true)
/*  89 */       .build());
/*     */ 
/*     */   
/*  92 */   private final Setting<Boolean> disableOnYChange = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  93 */       .name("disable-on-y-change")
/*  94 */       .description("Automatically disables when your y level (step, jumping, atc).")
/*  95 */       .defaultValue(true)
/*  96 */       .build());
/*     */ 
/*     */   
/*  99 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 100 */       .name("rotate")
/* 101 */       .description("Automatically faces towards the obsidian being placed.")
/* 102 */       .defaultValue(true)
/* 103 */       .build());
/*     */ 
/*     */   
/* 106 */   private final Setting<List<class_2248>> blocks = this.sgGeneral.add((Setting)(new BlockListSetting.Builder())
/* 107 */       .name("block")
/* 108 */       .description("What blocks to use for Anti Crystal.")
/* 109 */       .defaultValue(Collections.singletonList(class_2246.field_10589))
/* 110 */       .filter(this::blockFilter)
/* 111 */       .build()); private final class_2338.class_2339 blockPos;
/*     */   private boolean return_;
/*     */   
/*     */   public AntiCrystal() {
/* 115 */     super(AddModule.BANANAPLUS, "Anti Crystal", "Stops End Crystals from doing damage to you.");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     this.blockPos = new class_2338.class_2339();
/*     */   } public void onActivate() {
/*     */     if (((Boolean)this.center.get()).booleanValue())
/*     */       PlayerUtils.centerPlayer(); 
/*     */   } @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 128 */     if ((((Boolean)this.disableOnJump.get()).booleanValue() && (this.mc.field_1690.field_1903.method_1434() || this.mc.field_1724.field_3913.field_3904)) || (((Boolean)this.disableOnYChange.get()).booleanValue() && this.mc.field_1724.field_6036 < this.mc.field_1724.method_23318())) {
/* 129 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/* 133 */     if (((Boolean)this.onlyOnGround.get()).booleanValue() && !this.mc.field_1724.method_24828())
/* 134 */       return;  if (((Boolean)this.onlyWhenSneaking.get()).booleanValue() && !this.mc.field_1690.field_1832.method_1434()) {
/*     */       return;
/*     */     }
/* 137 */     this.return_ = false;
/*     */ 
/*     */     
/* 140 */     boolean p2 = place(2, 0, 0);
/* 141 */     if (this.return_)
/* 142 */       return;  boolean p3 = place(-2, 0, 0);
/* 143 */     if (this.return_)
/* 144 */       return;  boolean p4 = place(0, 0, 2);
/* 145 */     if (this.return_)
/* 146 */       return;  boolean p5 = place(0, 0, -2);
/* 147 */     if (this.return_) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     boolean antiFacePlaced = false;
/* 152 */     if (((Boolean)this.antiFacePlace.get()).booleanValue() || ((Keybind)this.antiFacePlaceKeybind.get()).isPressed()) {
/* 153 */       boolean p6 = place(1, 1, 0);
/* 154 */       if (this.return_)
/* 155 */         return;  boolean p7 = place(-1, 1, 0);
/* 156 */       if (this.return_)
/* 157 */         return;  boolean p8 = place(0, 1, 1);
/* 158 */       if (this.return_)
/* 159 */         return;  boolean p9 = place(0, 1, -1);
/* 160 */       if (this.return_)
/*     */         return; 
/* 162 */       if (p6 && p7 && p8 && p9) antiFacePlaced = true;
/*     */     
/*     */     } 
/*     */     
/* 166 */     boolean widePlaced = false;
/* 167 */     if (this.mode.get() == Mode.wide || ((Keybind)this.wideKeybind.get()).isPressed()) {
/* 168 */       boolean p10 = place(1, 0, 1);
/* 169 */       if (this.return_)
/* 170 */         return;  boolean p11 = place(-1, 0, 1);
/* 171 */       if (this.return_)
/* 172 */         return;  boolean p12 = place(1, 0, -1);
/* 173 */       if (this.return_)
/* 174 */         return;  boolean p13 = place(-1, 0, -1);
/* 175 */       if (this.return_)
/*     */         return; 
/* 177 */       if (p10 && p11 && p12 && p13) widePlaced = true;
/*     */     
/*     */     } 
/*     */     
/* 181 */     if (((Boolean)this.turnOff.get()).booleanValue() && p2 && p3 && p4 && p5 && (
/* 182 */       antiFacePlaced || !((Boolean)this.antiFacePlace.get()).booleanValue() || !((Keybind)this.antiFacePlaceKeybind.get()).isPressed() || widePlaced || !((Keybind)this.wideKeybind.get()).isPressed())) toggle();
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean blockFilter(class_2248 block) {
/* 188 */     return (block == class_2246.field_10397 || block == class_2246.field_10592 || block == class_2246.field_22130 || block == class_2246.field_10470 || block == class_2246.field_10026 || block == class_2246.field_10484 || block == class_2246.field_23863 || block == class_2246.field_10332 || block == class_2246.field_10158 || block == class_2246.field_22131 || block == class_2246.field_10278 || block == class_2246.field_10417 || block == class_2246.field_22100 || block == class_2246.field_10493 || block == class_2246.field_10553 || block == class_2246.field_10057 || block == class_2246.field_23864 || block == class_2246.field_10066 || block == class_2246.field_10494 || block == class_2246.field_22101 || block == class_2246.field_10589);
/*     */   }
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
/*     */   private boolean place(int x, int y, int z) {
/* 212 */     setBlockPos(x, y, z);
/* 213 */     class_2680 blockState = this.mc.field_1687.method_8320((class_2338)this.blockPos);
/*     */     
/* 215 */     if (!blockState.method_26207().method_15800()) return true;
/*     */     
/* 217 */     if (BlockUtils.place((class_2338)this.blockPos, InvUtils.findInHotbar(itemStack -> ((List)this.blocks.get()).contains(class_2248.method_9503(itemStack.method_7909()))), ((Boolean)this.rotate.get()).booleanValue(), 100, true)) {
/* 218 */       this.return_ = true;
/*     */     }
/*     */     
/* 221 */     return false;
/*     */   }
/*     */   
/*     */   private void setBlockPos(int x, int y, int z) {
/* 225 */     this.blockPos.method_10102(this.mc.field_1724.method_23317() + x, this.mc.field_1724.method_23318() + y, this.mc.field_1724.method_23321() + z);
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/AntiCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */