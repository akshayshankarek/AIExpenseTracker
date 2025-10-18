package com.example.expensehome.addexpense

internal interface AddExpenseContract {
    data class ViewState(
        val title: String,
        val amount: String,
        val category: String,
        val isSaving: Boolean,
        val isSuggestionLoading: Boolean
    ){
        companion object {
            val Default = ViewState(
                title = "",
                amount = "",
                category = "",
                isSaving = false,
                isSuggestionLoading = false
            )
        }
    }
}