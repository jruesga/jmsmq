/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class net_sf_jmsmq_MSMQQueueFactory */

#ifndef _Included_net_sf_jmsmq_MSMQQueueFactory
#define _Included_net_sf_jmsmq_MSMQQueueFactory
#ifdef __cplusplus
extern "C" {
#endif
#undef net_sf_jmsmq_MSMQQueueFactory_serialVersionUID
#define net_sf_jmsmq_MSMQQueueFactory_serialVersionUID -1219962587011417073i64
/*
 * Class:     net_sf_jmsmq_MSMQQueueFactory
 * Method:    jniMQCreateQueue
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueueFactory_jniMQCreateQueue
  (JNIEnv *, jobject, jstring, jint);

/*
 * Class:     net_sf_jmsmq_MSMQQueueFactory
 * Method:    jniMQLookupQueue
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueueFactory_jniMQLookupQueue
  (JNIEnv *, jobject, jstring);

/*
 * Class:     net_sf_jmsmq_MSMQQueueFactory
 * Method:    jniMQEnumPrivateQueue
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_net_sf_jmsmq_MSMQQueueFactory_jniMQEnumPrivateQueue
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
