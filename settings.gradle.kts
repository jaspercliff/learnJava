rootProject.name = "learnJava"

include("base")
include("utils")
include("utils:caffeineDemo")
include("utils:guavaDemo")

include("infra-redis")
include("infra-redis:jedisDemo")
// jedis - > valkey
include("infra-redis:valkeyJava")
include("infra-redis:valkeyGlide")
include("infra-redis:lettuceDemo")
