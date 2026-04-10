rootProject.name = "learnJava"

include("base")
include("utils")
include("utils:caffeineDemo")
include("utils:guavaDemo")

// ############################# valkey
include("infra-redis")
// ####### valkey driver
include("infra-redis:jedisDemo")
// jedis - > valkey
include("infra-redis:valkeyJava")
include("infra-redis:lettuceDemo")
include("infra-redis:valkeyGlide")

include("foundation")
include("jvmDemo")
include("webDemo")

include("spiDemo")
include("spiDemo:storage-api")
include("spiDemo:storage-minio")
include("spiDemo:storage-rustfs")
include("spiDemo:business")
include("basic")
include("basic:foundation")