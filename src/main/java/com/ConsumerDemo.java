package com;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {
    public static void main(String[] args) {
        System.setProperty("java.security.krb5.conf", "/Users/hdb-dsj-003/Documents/IdeaProjects/JavaKBKafka/kafka_kb_conf/krb5.conf");
        System.setProperty("java.security.auth.login.config", "/Users/hdb-dsj-003/Documents/IdeaProjects/JavaKBKafka/kafka_kb_conf/jaas.conf");
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");

        Properties props = new Properties();
        props.put("bootstrap.servers", "bigdata-dev-kafka-01:9092,bigdata-dev-kafka-02:9092,bigdata-dev-kafka-03:9092");
        props.put("group.id", "kb_test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", 1000);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.kerberos.service.name", "kafka");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("test"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value() + "\n");
        }
    }
}
