package com.example.templatemvvm

import android.content.Context
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.templatemvvm.ui.auth.signup.SignUpActivity
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class TestSignUpActivity {
    @get:Rule
    var activityActivityTestRule = ActivityTestRule(SignUpActivity::class.java)
    private lateinit var context: Context

    @Before
    fun init() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun emptyFiledDisableButton() {
        //type text email
        Espresso.onView(ViewMatchers.withId(R.id.input_email))
            .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard())
        //type text email
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
            .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard())
        //type text username
        Espresso.onView(ViewMatchers.withId(R.id.input_username))
            .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard())
        //check button is enable
        Espresso.onView(ViewMatchers.withId(R.id.btn_signup)).perform(ViewActions.click()).check(
            ViewAssertions.matches(
                Matchers.not(ViewMatchers.isEnabled())
            )
        )
    }

    @Test
    fun emptyPasswordAndUserNameDisableButton() {
        //type text email
        Espresso.onView(ViewMatchers.withId(R.id.input_email))
            .perform(ViewActions.typeText("bilel@gmail.com"), ViewActions.closeSoftKeyboard())
        //type text email
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
            .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard())
        //type text username
        Espresso.onView(ViewMatchers.withId(R.id.input_username))
            .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard())
        //check button is enable
        Espresso.onView(ViewMatchers.withId(R.id.btn_signup)).perform(ViewActions.click()).check(
            ViewAssertions.matches(
                Matchers.not(ViewMatchers.isEnabled())
            )
        )
    }


    @Test
    fun emptyEmailDisableButton() {
        //type text email
        Espresso.onView(ViewMatchers.withId(R.id.input_email))
            .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard())
        //type text email
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
            .perform(ViewActions.typeText("123456789"), ViewActions.closeSoftKeyboard())
        //type text username
        Espresso.onView(ViewMatchers.withId(R.id.input_username))
            .perform(ViewActions.typeText("username"), ViewActions.closeSoftKeyboard())
        //check button is enable
        Espresso.onView(ViewMatchers.withId(R.id.btn_signup)).perform(ViewActions.click()).check(
            ViewAssertions.matches(
                Matchers.not(ViewMatchers.isEnabled())
            )
        )
    }

    @Test
    fun fillFiledEnableButton() {
        //type text email
        Espresso.onView(ViewMatchers.withId(R.id.input_email))
            .perform(ViewActions.typeText("bilel@gmail.com"), ViewActions.closeSoftKeyboard())
        //type text email
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
            .perform(ViewActions.typeText("1234567890"), ViewActions.closeSoftKeyboard())
        //type text username
        Espresso.onView(ViewMatchers.withId(R.id.input_username))
            .perform(ViewActions.typeText("username"), ViewActions.closeSoftKeyboard())
        //check button is enable
        Espresso.onView(ViewMatchers.withId(R.id.btn_signup)).perform(ViewActions.click()).check(
            ViewAssertions.matches(
                ViewMatchers.isEnabled()
            )
        )
    }
}