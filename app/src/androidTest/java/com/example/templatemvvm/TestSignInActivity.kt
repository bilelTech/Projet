package com.example.templatemvvm

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.templatemvvm.ui.auth.signin.SignInActivity
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestSignInActivity {

    @get:Rule
    var activityActivityTestRule = ActivityTestRule(SignInActivity::class.java)
    private lateinit var context: Context

    @Before
    fun init() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun emptyFiledDisableButton() {
        //type text email
        onView(withId(R.id.input_email)).perform(typeText(""), closeSoftKeyboard())
        //type text email
        onView(withId(R.id.input_password)).perform(typeText(""), closeSoftKeyboard())
        //check button is enable
        onView(withId(R.id.btn_login)).perform(click()).check(matches(Matchers.not(isEnabled())))
    }

    @Test
    fun emptyEmailDisableButton() {
        //type text email
        onView(withId(R.id.input_email)).perform(typeText(""), closeSoftKeyboard())
        //type text email
        onView(withId(R.id.input_password)).perform(typeText("123456789"), closeSoftKeyboard())
        //check button is enable
        onView(withId(R.id.btn_login)).perform(click()).check(matches(Matchers.not(isEnabled())))
    }

    @Test
    fun emptyPasswordDisableButton() {
        //type text email
        onView(withId(R.id.input_email)).perform(typeText("bilel@gmail.com"), closeSoftKeyboard())
        //type text email
        onView(withId(R.id.input_password)).perform(typeText(""), closeSoftKeyboard())
        //check button is enable
        onView(withId(R.id.btn_login)).perform(click()).check(matches(Matchers.not(isEnabled())))
    }

    @Test
    fun fillFiledEnableButton() {
        //type text email
        onView(withId(R.id.input_email)).perform(typeText("bilel@gmail.com"), closeSoftKeyboard())
        //type text email
        onView(withId(R.id.input_password)).perform(typeText("123456789"), closeSoftKeyboard())
        //check button is enable
        onView(withId(R.id.btn_login)).perform(click()).check(matches((isEnabled())))
    }



}