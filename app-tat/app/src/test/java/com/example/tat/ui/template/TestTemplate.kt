package com.example.tat.ui.template

class TestTemplate {
    private var preConditions: MutableList<Runnable> = mutableListOf()
    private var test: MutableList<Runnable> = mutableListOf()
    private var postConditions: MutableList<Runnable> = mutableListOf()

    fun addPrecondition(runnable: Runnable): TestTemplate {
        preConditions.add(runnable)
        return this
    }

    fun addTest(runnable: Runnable): TestTemplate {
        preConditions.add(runnable)
        return this
    }

    fun addPostCondition(runnable: Runnable): TestTemplate {
        preConditions.add(runnable)
        return this
    }

    fun execute() {
        preConditions.forEach { it.run() }
        test.forEach { it.run() }
        postConditions.forEach { it.run() }
    }
}