/*    */ package bananaplusdevelopment.addons.modules;
/*    */ 
/*    */ import bananaplusdevelopment.addons.AddModule;
/*    */ import bananaplusdevelopment.utils.ares.ReflectionHelper;
/*    */ import minegame159.meteorclient.systems.modules.Module;
/*    */ import net.minecraft.class_1140;
/*    */ import net.minecraft.class_1144;
/*    */ 
/*    */ public class ReloadSoundSystem
/*    */   extends Module {
/*    */   public ReloadSoundSystem() {
/* 12 */     super(AddModule.BANANAMINUS, "Reload Sounds", "Reloads Minecraft's sound system");
/*    */   }
/*    */ 
/*    */   
/*    */   public void onActivate() {
/* 17 */     class_1140 soundSystem = (class_1140)ReflectionHelper.getPrivateValue(class_1144.class, this.mc.method_1483(), new String[] { "soundSystem", "field_5590" });
/* 18 */     soundSystem.method_4837();
/* 19 */     toggle();
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/ReloadSoundSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */