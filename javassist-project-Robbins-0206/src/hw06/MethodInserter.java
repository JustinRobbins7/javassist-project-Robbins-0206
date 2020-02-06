package hw06;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;
import javassist.NotFoundException;
import util.UtilFile;
import util.UtilMenu;

public class MethodInserter {
	
	static String WORK_DIR = System.getProperty("user.dir");
	static String OUTPUT_DIR = WORK_DIR + File.separator + "output";
	
	public static void main(String[] args) {
		String[] input;
		do {
			System.out.println("===================================================================");
			System.out.println("HW06 - Please enter a class, an method name, and a parameter index.");
			System.out.println("These inputs should be delimited by a ,                            ");
			System.out.println("===================================================================");

			input = UtilMenu.getArguments();
			if (input.length != 3) {
				System.out.println("[WRN] Invalid Input");
			}
		} while (input.length != 3);
		
		String classname = "target." + input[0];
		String mname = input[1];
		String pmindex = input[2];
		
		try {
	         ClassPool pool = ClassPool.getDefault();
	         CtClass cc = pool.get(classname);
	         CtMethod m = cc.getDeclaredMethod(mname);
	         String pblock = "{ "
	               + "System.out.println(\"[Inserted] " + classname + "'s param1: \" + $" + pmindex + "); "
	               + "}";
	         m.insertBefore(pblock);
	         
	         Loader load = new Loader(pool);
	         Class<?> c = load.loadClass(classname);
	         
	         Method m1 = c.getDeclaredMethod("main", new Class[] { String[].class });
	 		 m1.invoke(null, new Object[] { input });
	         
	 		cc.writeFile(OUTPUT_DIR);
	 		 
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
}
