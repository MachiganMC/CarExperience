function toggleCategoryView(togglerEl, selectorToToggle) {
    $(selectorToToggle).toggle();
    togglerEl = $(togglerEl);
    if (togglerEl.text() === "-") {
        togglerEl.text("+");
    } else {
        togglerEl.text("-");
    }
}
