import net.labymod.labygradle.common.extension.LabyModAnnotationProcessorExtension.ReferenceType

dependencies {
    labyProcessor()
    api(project(":api"))

    compileOnly("org.lwjgl:lwjgl-glfw:3.3.3")
}

labyModAnnotationProcessor {
    referenceType = ReferenceType.DEFAULT
}