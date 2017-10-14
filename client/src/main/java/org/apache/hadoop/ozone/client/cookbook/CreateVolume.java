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

import org.apache.hadoop.ozone.OzoneAcl;
import org.apache.hadoop.ozone.client.ObjectStore;
import org.apache.hadoop.ozone.client.OzoneClient;
import org.apache.hadoop.ozone.client.OzoneClientFactory;
import org.apache.hadoop.ozone.client.VolumeArgs;

import java.io.IOException;
import java.util.Arrays;

/**
 * This class shows how to create an ozone volume.
 */
public final class CreateVolume {

  /**
   * Name of the volume to be created.
   */
  private final String volumeName;

  /**
   * Constructor to initialize {@link CreateVolume}.
   *
   * @param volumeName
   *        name of the volume
   */
  public CreateVolume(String volumeName) {
    this.volumeName = volumeName;
  }

  /**
   * Creates volume with default values.
   *
   * @throws IOException
   *         if volume creation fails
   */
  public void execute()
      throws IOException {
    OzoneClient ozoneClient = OzoneClientFactory.getClient();
    ObjectStore objectStore = ozoneClient.getObjectStore();
    objectStore.createVolume(volumeName);
    ozoneClient.close();
  }

  /**
   * Creates the volume with specified quota.
   *
   * @param quota
   *        volume quota
   *
   * @throws IOException
   *         if volume creation fails
   */
  public void execute(String quota)
      throws IOException {
    OzoneClient ozoneClient = OzoneClientFactory.getClient();
    ObjectStore objectStore = ozoneClient.getObjectStore();
    VolumeArgs volumeArgs = VolumeArgs.newBuilder()
        .setQuota(quota)
        .build();
    objectStore.createVolume(volumeName, volumeArgs);
    ozoneClient.close();
  }

  /**
   * Creates the volume with specified quota and owner.
   *
   * @param quota
   *        volume quota
   *
   * @param owner
   *        volume owner
   *
   * @throws IOException
   *         if volume creation fails
   */
  public void execute(String quota, String owner)
      throws IOException {
    OzoneClient ozoneClient = OzoneClientFactory.getClient();
    ObjectStore objectStore = ozoneClient.getObjectStore();
    VolumeArgs volumeArgs = VolumeArgs.newBuilder()
        .setQuota(quota)
        .setOwner(owner)
        .build();
    objectStore.createVolume(volumeName, volumeArgs);
    ozoneClient.close();
  }

  /**
   * Creates the volume with specified quota and owner with the ACL set.
   *
   * @param quota
   *        volume quota
   *
   * @param owner
   *        volume owner
   *
   * @param acl
   *        acl to be set
   *
   * @throws IOException
   *         if volume creation fails
   */
  public void execute(String quota, String owner, String acl)
      throws IOException {
    OzoneClient ozoneClient = OzoneClientFactory.getClient();
    ObjectStore objectStore = ozoneClient.getObjectStore();
    VolumeArgs volumeArgs = VolumeArgs.newBuilder()
        .setQuota(quota)
        .setOwner(owner)
        .setAcls(Arrays.asList(OzoneAcl.parseAcl(acl)))
        .build();
    objectStore.createVolume(volumeName, volumeArgs);
    ozoneClient.close();
  }

  /**
   * Prints the usage.
   */
  private static void printUsage() {
    String className =  CreateVolume.class.getName();
    System.err.println("Usage: " + className
        +  " <volume name> [<quota> <owner> <acl>]");
    System.err.println("Examples: ");
    System.err.println(className + " foo");
    System.err.println(className + " foo \"50 GB\"");
    System.err.println(className + " foo \"50 GB\" \"dr.strange\"");
    System.err.println(
        className + " foo \"50 GB\" \"dr.strange\" \"user:dr.who:rw\"");
  }

  /**
   * Mani method to execute {@link CreateVolume}.
   *
   * @param args
   *        arguments for volume creation
   *
   * @throws IOException
   *         if volume creation fails
   */
  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.err.println("Invalid argument.");
      printUsage();
      System.exit(1);
    }
    CreateVolume createVolume = new CreateVolume(args[0]);
    switch (args.length) {
    case 1:
      createVolume.execute();
      break;
    case 2:
      createVolume.execute(args[1]);
      break;
    case 3:
      createVolume.execute(args[1], args[2]);
      break;
    case 4:
      createVolume.execute(args[1], args[2], args[3]);
      break;
    default:
      printUsage();
    }

  }
}
