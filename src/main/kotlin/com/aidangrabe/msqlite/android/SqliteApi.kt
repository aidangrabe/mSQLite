package com.aidangrabe.msqlite.android

/**
 *
 */
class SqliteApi(val packageName: String, val databaseName: String) {

    fun listTables(): List<String> {
        val tables = exec(".tables")
        return parseTables(tables)
    }

    fun exec(command: String): String {
        return Adb.exec(
                "exec-out", "run-as", packageName,
                "sqlite3", "-html", "-header", "databases/$databaseName",
                command
        )
    }

    private fun parseTables(input: String): List<String> {
        // error with package name
        if (input.startsWith("run-as: Package ")) {
            return listOf("Error: Package name does not exist")
        }
        return input.split("\\s+".toRegex())
                .filter(String::isNotEmpty)
    }

}