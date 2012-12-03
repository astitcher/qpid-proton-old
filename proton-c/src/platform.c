/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

#include "platform.h"
#include "util.h"
#include "proton/util.h" // for pn_fatal() ?should pn_fatal() be public?

/* Allow for systems that do not implement clock_gettime()*/
#ifdef USE_CLOCK_GETTIME
#include <time.h>
pn_timestamp_t pn_i_now(void)
{
  struct timespec now;
  if (clock_gettime(CLOCK_REALTIME, &now)) pn_fatal("clock_gettime() failed\n");
  return ((pn_timestamp_t)now.tv_sec) * 1000 + (now.tv_nsec / 1000000);
}
#else
#include <sys/time.h>
pn_timestamp_t pn_i_now(void)
{
  struct timeval now;
  if (gettimeofday(&now, NULL)) pn_fatal("gettimeofday failed\n");
  return ((pn_timestamp_t)now.tv_sec) * 1000 + (now.tv_usec / 1000);
}
#endif

#ifdef USE_UUID_GENERATE
#include <uuid/uuid.h>
#include <stdlib.h>
char* pn_i_genuuid(void) {
    char *generated = malloc(37*sizeof(char));
    uuid_t uuid;
    uuid_generate(uuid);
    uuid_unparse_lower(uuid, generated);
    return generated;
}
#elif USE_UUID_CREATE
#include <uuid.h>
char* pn_i_genuuid(void) {
    char *generated;
    uuid_t uuid;
    uint32_t rc;
    uuid_create(&uuid, &rc);
    uuid_to_string(&uuid, &generated, &rc);
    return pn_strdup(generated);
}
#else
#error "Don't know how to generate uuid strings on this platform"
#endif
