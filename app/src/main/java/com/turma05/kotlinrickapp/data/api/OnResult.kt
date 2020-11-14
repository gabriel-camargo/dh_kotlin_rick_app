package com.turma05.kotlinrickapp.data.api

interface OnResult<T> {
    fun onSucess(result: T)
    fun onFailure()
}