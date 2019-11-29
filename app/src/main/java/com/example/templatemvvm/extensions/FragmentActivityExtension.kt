package com.skydoves.githubfollows.extension

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

/**
 * Developed by skydoves on 2019-06-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

fun <T : ViewModel> FragmentActivity.vm(factory: ViewModelProvider.Factory, model: KClass<T>): T {
  return ViewModelProvider(this, factory).get(model.java)
}
