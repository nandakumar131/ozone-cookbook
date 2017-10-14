# ozone-cookbook

This cookbook is intended to be used for understanding Apache Hadoop Ozone, which is currently under development (HDFS-7240).

## Introduction
Ozone is an object store for Apache Hadoop. The hierarchy of data inside ozone is a volume, bucket and a key. A volume is a collection of buckets. A bucket is a collection of keys. To write data into ozone, you need a volume, bucket and a key.
