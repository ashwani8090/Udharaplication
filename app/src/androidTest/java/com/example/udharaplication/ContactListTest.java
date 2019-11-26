package com.example.udharaplication;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ContactListTest {

    @Rule
    public ActivityTestRule<ContactList> mActivityTestRule =
            new ActivityTestRule<ContactList>(ContactList.class);
    public ContactList topicsActivity = null;

    @Before
    public void setUp() throws Exception {
        topicsActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void UiLoad() {

        assertNotNull(topicsActivity);
    }



    @After
    public void tearDown() throws Exception {
        topicsActivity = null;
    }
}