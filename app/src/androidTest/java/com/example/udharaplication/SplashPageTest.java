package com.example.udharaplication;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class SplashPageTest {

    @Rule
    public ActivityTestRule<SplashPage> mActivityTestRule =
            new ActivityTestRule<SplashPage>(SplashPage.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void UiLoading() {
        //ui loading
        assertNotNull(mActivityTestRule.getActivity());

    }

    @Test
    public void testSetup() throws IOException {

      }


    @After
    public void tearDown() throws Exception {
    }

}