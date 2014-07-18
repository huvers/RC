package com.hbar.finance.mathengine;

import com.wolfram.jlink.*;

public class MathematicaSampleProgram {

    public static void main(String[] argv) {

        KernelLink ml = null;

        try {
        	/***
        	 * 
        	 * The location of the JLink installation directory can be explicitly
				specified by setting
				the JLINK_LIB_DIR environment variable in the Windows System control
				panel
				or by setting the Java system property "com.wolfram.jlink.libdir",
				i.e.,
        	 * 
        	 * 
        	 * 
        	 */
			String jLinkDir = "C:\\Program Files\\Wolfram Research\\Mathematica\\9.0\\SystemFiles\\Links\\JLink";
			System.setProperty("com.wolfram.jlink.libdir", jLinkDir);

			ml = MathLinkFactory.createKernelLink(argv);
        } catch (MathLinkException e) {
            System.out.println("Fatal error opening link: " + e.getMessage());
            return;
        }

        try {
            // Get rid of the initial InputNamePacket the kernel will send
            // when it is launched.
/*            ml.discardAnswer();

            ml.evaluate("<<MyPackage.m");
            ml.discardAnswer();

            ml.evaluate("2+2");
            ml.waitForAnswer();

            int result = ml.getInteger();
            System.out.println("2 + 2 = " + result);
*/
            // Here's how to send the same input, but not as a string:
/*            ml.putFunction("EvaluatePacket", 1);
            ml.putFunction("Plus", 2);
            ml.
            ml.put(3);
            ml.put(3);
            ml.endPacket();
            ml.waitForAnswer();
            result = ml.getInteger();
            System.out.println("3 + 3 = " + result);
*/
            // If you want the result back as a string, use evaluateToInputForm
            // or evaluateToOutputForm. The second arg for either is the
            // requested page width for formatting the string. Pass 0 for
            // PageWidth->Infinity. These methods get the result in one
            // step--no need to call waitForAnswer.
/*            String strResult = ml.evaluateToOutputForm("4+4", 0);
            System.out.println("4 + 4 = " + strResult);
*/
            
            // Get rid of the initial InputNamePacket the kernel will send
            // when it is launched.
            ml.discardAnswer();

            ml.evaluate("Needs[\"CompiledFunctionTools`\"]");
            ml.discardAnswer();

            String strResult = 
                        ml.evaluateToOutputForm(
                           "CompilePrint[Compile[{x},x+Sin[x]]]", 0);
            System.out.println(
                           "Instructions for function Compile[{x},x+Sin[x]]: " 
                                + strResult);

			ml.putFunction("EvaluatePacket", 1);
			ml.putFunction("CompilePrint", 1);
			ml.putFunction("Compile", 2);
			ml.putFunction("List", 1);
			ml.putSymbol("x");
			ml.putFunction("Plus", 2);
			ml.putSymbol("x");
			ml.putFunction("Sin", 1);
			ml.putSymbol("x");
			ml.endPacket();
            ml.waitForAnswer();
            Expr expr=ml.getExpr();
            
            
            strResult = ml.getString();
            System.out.println("Now conbining function call from pieces: " + strResult);  
            
            ml.evaluate("NIntegrate[x^2 + y^2, {x,-1,1}, {y,-1,1}]");
            ml.waitForAnswer();
            double doubleResult1 = ml.getDouble();
            System.out.println("result: " + doubleResult1);
            
            String result = ml.evaluateToOutputForm("Integrate[x^2, x]", 72);
            System.out.println(result);
            
            result = ml.evaluateToOutputForm("x^2", 72);
            
            System.out.println(result);
            
            
        } catch (MathLinkException e) {
            System.out.println("MathLinkException occurred: " + e.getMessage());
        } finally {
            ml.close();
        }
    }
}