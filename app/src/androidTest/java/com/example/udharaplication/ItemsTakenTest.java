package com.example.udharaplication;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class ItemsTakenTest {

    @Rule
    public ActivityTestRule<ItemsTaken> mActivityTestRule =
            new ActivityTestRule<ItemsTaken>(ItemsTaken.class);
    public ItemsTaken topicsActivity = null;

    @Before
    public void setUp() throws Exception {
        topicsActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void UiLoad() {

        assertNotNull(topicsActivity);
    }

    @Test
    public void RecyclerTest() {

        onView(withId(R.id.itemsRecycler)).check(matches(isDisplayed()));
        // perform click on view at 3rd position in RecyclerView
        onView(withId(R.id.itemsRecycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
    }


    @After
    public void tearDown() throws Exception {
        topicsActivity = null;
    }


}