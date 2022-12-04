package com.raassh.dicodingcomposefinal.data.model

import com.raassh.dicodingcomposefinal.R

enum class WatchStatus(val resId: Int) {
    UNTRACKED(R.string.untracked),
    WATCHING(R.string.watching),
    COMPLETED(R.string.completed),
    ON_HOLD(R.string.on_hold),
    DROPPED(R.string.dropped),
    PLAN_TO_WATCH(R.string.plan_to_watch)
}