/*    */ package bananaplusdevelopment.utils.ares;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectionHelper
/*    */ {
/*    */   public static <T> T getPrivateValue(Class clazz, Object object, String... names) {
/* 12 */     Field field = getField(clazz, names);
/* 13 */     field.setAccessible(true);
/*    */     
/*    */     try {
/* 16 */       return (T)field.get(object);
/* 17 */     } catch (IllegalAccessException illegalAccessException) {
/*    */       
/* 19 */       return null;
/*    */     } 
/*    */   }
/*    */   public static <T> boolean setPrivateValue(Class clazz, Object object, T value, String... names) {
/* 23 */     Field field = getField(clazz, names);
/* 24 */     field.setAccessible(true);
/*    */     
/*    */     try {
/* 27 */       field.set(object, value);
/* 28 */     } catch (IllegalAccessException ignored) {
/* 29 */       return false;
/*    */     } 
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   public static Field getField(Class clazz, String... names) {
/* 35 */     Field field = null;
/*    */     
/* 37 */     for (String name : names) {
/* 38 */       if (field != null)
/*    */         break;  try {
/* 40 */         field = clazz.getDeclaredField(name);
/* 41 */       } catch (NoSuchFieldException noSuchFieldException) {}
/*    */     } 
/*    */ 
/*    */     
/* 45 */     return field;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T> T callPrivateMethod(Class<?> clazz, Object object, String[] names, Object... args) {
/* 50 */     Class<?>[] classes = new Class[0];
/* 51 */     for (int i = 0; i < args.length; ) { classes[i] = args[i].getClass(); i++; }
/*    */     
/* 53 */     Method method = getMethod(clazz, names, classes);
/* 54 */     method.setAccessible(true);
/*    */     
/*    */     try {
/* 57 */       return (T)method.invoke(object, args);
/* 58 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 59 */       e.printStackTrace();
/*    */       
/* 61 */       return null;
/*    */     } 
/*    */   }
/*    */   public static Method getMethod(Class<?> clazz, String[] names, Class<?>[] args) {
/* 65 */     Method method = null;
/*    */     
/* 67 */     for (String name : names) {
/* 68 */       if (method != null)
/*    */         break;  try {
/* 70 */         method = clazz.getDeclaredMethod(name, args);
/* 71 */       } catch (NoSuchMethodException noSuchMethodException) {}
/*    */     } 
/*    */ 
/*    */     
/* 75 */     return method;
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/ares/ReflectionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */