package ir.niv.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform