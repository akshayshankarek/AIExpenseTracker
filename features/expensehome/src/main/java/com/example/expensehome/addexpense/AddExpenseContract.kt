package com.example.expensehome.addexpense

internal interface AddExpenseContract {
    data class ViewState(
        val title: String,
        val amount: String,
        val category: String,
        val isSaving: Boolean,
        val isSuggestionLoading: Boolean,
        val addExpenseType: ADD_EXPENSE_TYPE
    ){
        companion object {
            val Default = ViewState(
                title = "",
                amount = "",
                category = "",
                isSaving = false,
                isSuggestionLoading = false,
                addExpenseType = ADD_EXPENSE_TYPE.DEFAULT
            )
        }
    }

    enum class ADD_EXPENSE_TYPE {
        DEFAULT,
        SCAN
    }
}