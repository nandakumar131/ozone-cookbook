/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.ozone.client.cookbook;

import org.apache.hadoop.fs.StorageType;
import org.apache.hadoop.ozone.OzoneAcl;
import org.apache.hadoop.ozone.client.BucketArgs;
import org.apache.hadoop.ozone.client.ObjectStore;
import org.apache.hadoop.ozone.client.OzoneClient;
import org.apache.hadoop.ozone.client.OzoneClientFactory;
import org.apache.hadoop.ozone.client.OzoneVolume;

import java.io.IOException;
import java.util.Arrays;

/**
 * This class shows how to create an ozone bucket.
 * Buckets are created inside ozone volume.
 */
public final class CreateBucket {

  /**
   * Volume inside which the bucket has to be created.
   */
  private final String volumeName;
  /**
   * Name of the bucket to be created.
   */
  private final String bucketName;

  /**
   * Constructor to initialize {@link CreateBucket}.
   *
   * @param volumeName
   *        name of the volume
   * @param bucketName
   *        name of the bucket
   */
  public CreateBucket(String volumeName, String bucketName) {
    this.volumeName = volumeName;
    this.bucketName = bucketName;
  }

  /**
   * Creates bucket inside {@code volume}, with default value.
   * @throws IOException
   *         if bucket creation fails
   */
  public void execute()
      throws IOException {
    OzoneClient ozoneClient = OzoneClientFactory.getClient();
    ObjectStore objectStore = ozoneClient.getObjectStore();
    OzoneVolume volume = objectStore.getVolume(volumeName);
    volume.createBucket(bucketName);
    ozoneClient.close();
  }

  /**
   * Creates bucket inside {@code volume}, with specified storage type.
   *
   * @param storageType
   *        storage type of the bucket
   * @throws IOException
   *         if bucket creation fails
   */
  public void execute(String storageType)
      throws IOException {
    OzoneClient ozoneClient = OzoneClientFactory.getClient();
    ObjectStore objectStore = ozoneClient.getObjectStore();
    OzoneVolume volume = objectStore.getVolume(volumeName);
    BucketArgs bucketArgs = BucketArgs.newBuilder()
        .setStorageType(StorageType.valueOf(storageType))
        .build();
    volume.createBucket(bucketName, bucketArgs);
    ozoneClient.close();
  }

  /**
   * Creates bucket inside {@code volume}, with specified storage type and
   * bucket versioning.
   *
   * @param storageType
   *        storage type of the bucket
   * @param versioning
   *        {@code true} to enable bucket versioning
   * @throws IOException
   *         if bucket creation fails
   */
  public void execute(String storageType, String versioning)
      throws IOException {
    OzoneClient ozoneClient = OzoneClientFactory.getClient();
    ObjectStore objectStore = ozoneClient.getObjectStore();
    OzoneVolume volume = objectStore.getVolume(volumeName);
    BucketArgs bucketArgs = BucketArgs.newBuilder()
        .setStorageType(StorageType.valueOf(storageType))
        .setVersioning(Boolean.valueOf(versioning))
        .build();
    volume.createBucket(bucketName, bucketArgs);
    ozoneClient.close();
  }

  /**
   * Creates bucket inside {@code volume}, with specified storage type,
   * versioning and ACL.
   *
   * @param storageType
   *        storage type of the bucket
   * @param versioning
   *        {@code true} to enable bucket versioning
   * @param acl
   *        to be associated with the bucket
   * @throws IOException
   *         if bucket creation fails
   */
  public void execute(String storageType, String versioning, String acl)
      throws IOException {
    OzoneClient ozoneClient = OzoneClientFactory.getClient();
    ObjectStore objectStore = ozoneClient.getObjectStore();
    OzoneVolume volume = objectStore.getVolume(volumeName);
    BucketArgs bucketArgs = BucketArgs.newBuilder()
        .setStorageType(StorageType.valueOf(storageType))
        .setVersioning(Boolean.valueOf(versioning))
        .setAcls(Arrays.asList(OzoneAcl.parseAcl(acl)))
        .build();
    volume.createBucket(bucketName, bucketArgs);
    ozoneClient.close();
  }

  /**
   * Prints the usage.
   */
  private static void printUsage() {
    String className =  CreateVolume.class.getName();
    System.err.println("Usage: " + className
        + " <volume name> <bucket name> [<storage type> <versioning> <acl>]");
    System.err.println("Examples: ");
    System.err.println(className + " foo bar");
    System.err.println(className + " foo bar SSD");
    System.err.println(className + " foo bar SSD true");
    System.err.println(
        className + " foo bar SSD true user:dr.who:rw");
  }

  /**
   * Mani method to execute {@link CreateBucket}.
   *
   * @param args
   *        arguments for bucket creation
   * @throws IOException
   *         if bucket creation fails
   */
  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      System.err.println("Invalid argument.");
      printUsage();
      System.exit(1);
    }
    CreateBucket createBucket = new CreateBucket(args[0], args[1]);
    switch (args.length) {
      case 2:
        createBucket.execute();
        break;
      case 3:
        createBucket.execute(args[2]);
        break;
      case 4:
        createBucket.execute(args[2], args[3]);
        break;
      case 5:
        createBucket.execute(args[2], args[3], args[4]);
        break;
      default:
          printUsage();
    }

  }
}
