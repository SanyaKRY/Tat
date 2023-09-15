package com.example.tat.ui.poll

import com.example.tat.ui.model.User

class UserPoll {

    private var users : ArrayList<User> = arrayListOf(
        User("admin", "password")
    )

    fun take(): User {
        return users[0]
    }

    fun realise(user: User) {
        users.add(user)
    }
}

