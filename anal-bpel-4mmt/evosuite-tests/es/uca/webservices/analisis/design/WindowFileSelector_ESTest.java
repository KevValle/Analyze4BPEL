/*
 * This file was automatically generated by EvoSuite
 * Sat Aug 20 13:21:00 CEST 2016
 */

package es.uca.webservices.analisis.design;

import static org.evosuite.runtime.EvoAssertions.assertThrownBy;
import static org.junit.Assert.fail;

import java.awt.HeadlessException;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(useVNET = true) 
public class WindowFileSelector_ESTest extends WindowFileSelector_ESTest_scaffolding {

  @Test
  public void test0()  throws Throwable  {
      WindowFileSelector windowFileSelector0 = null;
      try {
        windowFileSelector0 = new WindowFileSelector();
        fail("Expecting exception: HeadlessException");
      
      } catch(HeadlessException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         assertThrownBy("java.awt.GraphicsEnvironment", e);
      }
  }
}