/*
 * This file was automatically generated by EvoSuite
 * Sat Aug 20 13:15:22 CEST 2016
 */

package es.uca.webservices.analisis;

import static org.evosuite.runtime.EvoAssertions.assertThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.uca.webservices.analisis.io.BPELWriter;

@RunWith(EvoRunner.class) @EvoRunnerParameters(useVNET = true) 
public class BPELActivityAnalyzer_ESTest extends BPELActivityAnalyzer_ESTest_scaffolding {

  @Test
  public void test0()  throws Throwable  {
      BPELActivityAnalyzer bPELActivityAnalyzer0 = new BPELActivityAnalyzer("");
      // Undeclared exception!
      try { 
        bPELActivityAnalyzer0.getOutput();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         assertThrownBy("es.uca.webservices.analisis.BPELActivityAnalyzer", e);
      }
  }

  @Test
  public void test1()  throws Throwable  {
      BPELActivityAnalyzer bPELActivityAnalyzer0 = null;
      try {
        bPELActivityAnalyzer0 = new BPELActivityAnalyzer((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         assertThrownBy("es.uca.webservices.analisis.BPELActivityAnalyzer", e);
      }
  }

  @Test
  public void test2()  throws Throwable  {
      BPELActivityAnalyzer bPELActivityAnalyzer0 = new BPELActivityAnalyzer("[pp(T/");
      String string0 = bPELActivityAnalyzer0.readVariables();
      assertEquals("BPEL Composition not found.", string0);
  }

  @Test
  public void test3()  throws Throwable  {
      BPELActivityAnalyzer bPELActivityAnalyzer0 = new BPELActivityAnalyzer("[pp(T/");
      String string0 = bPELActivityAnalyzer0.readActivities();
      assertEquals("BPEL Composition not found.", string0);
  }

  @Test
  public void test4()  throws Throwable  {
      BPELActivityAnalyzer bPELActivityAnalyzer0 = new BPELActivityAnalyzer("=R3WgOO)!j0vV`$\"X");
      BPELWriter bPELWriter0 = bPELActivityAnalyzer0.getWriter();
      assertNull(bPELWriter0);
  }

  @Test
  public void test5()  throws Throwable  {
      BPELActivityAnalyzer bPELActivityAnalyzer0 = new BPELActivityAnalyzer("[pp(T/");
      // Undeclared exception!
      try { 
        bPELActivityAnalyzer0.getJSONOutput();
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         assertThrownBy("es.uca.webservices.analisis.BPELActivityAnalyzer", e);
      }
  }
}