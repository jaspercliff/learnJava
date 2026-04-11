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

include("jvmDemo")
include("webDemo")


include("basic")
include("basic:foundation")
include("basic:spiDemo")
include("basic:spiDemo:storage-api")
include("basic:spiDemo:storage-minio")
include("basic:spiDemo:storage-rustfs")
include("basic:spiDemo:business")
include("basic:newFeature")
include("basic:newFeature:9")
include("basic:newFeature:9:moduleDemo")
include("basic:newFeature:9:moduleDemo:producer")
include("basic:newFeature:9:moduleDemo:consumer")
include("basic:newFeature:21")
include("basic:newFeature:8")
include("basic:juc")