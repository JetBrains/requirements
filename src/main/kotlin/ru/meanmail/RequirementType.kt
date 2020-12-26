package ru.meanmail

enum class RequirementType(val kind: String) {
    NAME("package"),
    URL("package"),
    REFERENCE("file"),
    CONSTRAINT("file"),
    EDITABLE("url"),
    PATH("path")
}
