package com.satya.smartmealplanner.presentation.search

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchQueryHandler(
    coroutineScope: CoroutineScope,
    private val onSearchTriggered: (query: String, isVeg: Boolean) -> Unit
) {
    data class SearchInput(val query: String, val isVeg: Boolean)

    private val _searchInput = MutableStateFlow(SearchInput("", false))
    val searchInput: StateFlow<SearchInput> = _searchInput.asStateFlow()

    init {
        coroutineScope.launch {
            _searchInput
                .debounce(1000)
                .distinctUntilChanged()
                .filter { it.query.length >= 3 }
                .collectLatest { input ->
                    onSearchTriggered(input.query, input.isVeg)
                }
        }
    }

    fun onQueryChange(query: String, isVeg: Boolean) {
        _searchInput.value = SearchInput(query, isVeg)
    }
}
