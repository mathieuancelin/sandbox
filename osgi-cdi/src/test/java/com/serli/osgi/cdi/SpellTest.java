package com.serli.osgi.cdi;

import com.serli.osgi.cdi.gui.SpellCheckerGui;
import com.serli.osgi.cdi.services.DictionaryService;
import com.serli.osgi.cdi.services.SpellCheckerService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mathieuancelin
 */
public class SpellTest {

    private static Weld weld;

    private static WeldContainer container;

    private DictionaryService dict;

    private SpellCheckerService spell;

    @BeforeClass
    public static void setupClass() throws Exception {
        SpellTest.weld = new Weld();
        SpellTest.container = SpellTest.weld.initialize();
    }

    @Test
    public void injectTest() {
        dict = SpellTest.container.instance().select(DictionaryService.class).get();
        spell = SpellTest.container.instance().select(SpellCheckerService.class).get();
        Assert.assertTrue(dict.checkWord("hello"));
        Assert.assertTrue(dict.checkWord("world"));
        Assert.assertTrue(dict.checkWord("how"));
        Assert.assertTrue(dict.checkWord("are"));
        Assert.assertTrue(dict.checkWord("you"));
        Assert.assertTrue(dict.checkWord("guys"));
        Assert.assertFalse(dict.checkWord("stuff"));
        Assert.assertFalse(dict.checkWord("blah"));
        Assert.assertTrue(spell.check("hello world how are you kjhkjh").size() == 1);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        SpellTest.weld.shutdown();
    }

}