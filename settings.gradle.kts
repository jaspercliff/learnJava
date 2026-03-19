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