enum class RequirementType(val kind: String) {
    NAME("package"),
    URL("package"),
    REFERENCE("file"),
    EDITABLE("url"),
    PATH("path")
}
