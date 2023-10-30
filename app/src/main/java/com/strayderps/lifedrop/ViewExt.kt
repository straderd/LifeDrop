package com.strayderps.lifedrop

import android.view.View
import android.view.animation.AnimationUtils
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

fun View.slideUp(animTime: Long, startOffset: Long) {
    val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slideUp)
}

fun View.slideDown(animTime: Long, startOffset: Long) {
    val slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slideDown)
}

fun View.scaleUp(animTime: Long, startOffset: Long) {
    val scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(scaleUp)
}

fun View.scaleDown(animTime: Long, startOffset: Long) {
    val scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(scaleDown)
}