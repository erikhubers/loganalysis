/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.loganalysis.parser;

import com.android.loganalysis.item.LogcatItem;
import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Functional tests for {@link LogcatParser}
 */
public class LogcatParserFuncTest extends TestCase
{

    /**
     * A test that is intended to force Brillopad to parse a logcat. The purpose of this is to
     * assist a developer in checking why a given logcat file might not be parsed correctly by
     * Brillopad.
     */
    public void testParse()
    {
        BufferedReader logcatReader = null;
        //        try {
        //            logcatReader = Arrays.asList(StringUtils.splitString(LOGCAT,"\n"));
        //        } catch (FileNotFoundException e) {
        //            fail(String.format("File not found at %s", LOGCAT_PATH));
        //        }
        LogcatItem logcat = null;
        try
        {
            long start = System.currentTimeMillis();
            logcat = new LogcatParser().parse(Arrays.asList(StringUtils.split(LOGCAT, "\n")));
            long stop = System.currentTimeMillis();
            System.out.println(String.format("Logcat took %d ms to parse.", stop - start));
        }
        finally
        {
            if (logcatReader != null)
            {
                try
                {
                    logcatReader.close();
                }
                catch (IOException e)
                {
                    // Ignore
                }
            }
        }

        assertNotNull(logcat);
        assertNotNull(logcat.getStartTime());
        assertNotNull(logcat.getStopTime());

        System.out.println(String.format("Stats for logcat:\n" +
                        "  Start time: %s\n" +
                        "  Stop time: %s\n" +
                        "  %d ANR(s), %d Java Crash(es), %d Native Crash(es)",
                logcat.getStartTime().toString(),
                logcat.getStopTime().toString(),
                logcat.getAnrs().size(),
                logcat.getJavaCrashes().size(),
                logcat.getNativeCrashes().size()));
    }

    //region
    public static final String LOGCAT = "--------- beginning of system\n" +
            "06-20 15:40:41.755   500  8065 I ActivityManager: START u0 {flg=0x14000000 cmp=xxx.xxx.app/.LaunchActivity (has extras)} from uid 10076\n" +
            "06-20 15:41:38.836   500   521 I ActivityManager: Force stopping xxx.xxx.app appid=10076 user=-1: installPackageLI\n" +
            "06-20 15:41:38.837   500   521 I ActivityManager: Killing 8008:xxx.xxx.app/u0a76 (adj 0): stop xxx.xxx.app\n" +
            "06-20 15:41:38.837   500   521 W ActivityManager: Scheduling restart of crashed service xxx.xxx.app/.OnAppKilledService in 1000ms\n" +
            "06-20 15:41:38.837   500   521 W ActivityManager: Scheduling restart of crashed service xxx.xxx.app/.KeepAliveService in 11000ms\n" +
            "06-20 15:41:38.840   500   521 W ActivityManager: Force removing ActivityRecord{70cd8b5 u0 xxx.xxx.app/.LaunchActivity t57}: app died, no saved state\n" +
            "06-20 15:41:38.884   500   538 I PackageManager: Update package xxx.xxx.app code path from /data/app/xxx.xxx.app-ghSdoSwX-fhIOxPe2Z5uIA== to /data/app/xxx.xxx.app-UnGOwZMBaW2NVqR-T7y_VA==; Retain data and using new\n" +
            "06-20 15:41:38.884   500   538 I PackageManager: Update package xxx.xxx.app resource path from /data/app/xxx.xxx.app-ghSdoSwX-fhIOxPe2Z5uIA== to /data/app/xxx.xxx.app-UnGOwZMBaW2NVqR-T7y_VA==; Retain data and using new\n" +
            "06-20 15:41:38.888   500   521 I ActivityManager:   Force stopping service ServiceRecord{6dcd6c3 u0 xxx.xxx.app/.KeepAliveService}\n" +
            "06-20 15:41:38.888   500   521 I ActivityManager:   Force stopping service ServiceRecord{a000733 u0 xxx.xxx.app/.OnAppKilledService}\n" +
            "06-20 15:41:38.911   500   533 W ActivityManager: setHasOverlayUi called on unknown pid: 8008\n" +
            "06-20 15:41:38.924   500   538 D PackageManager: Instant App installer not found with android.intent.action.INSTALL_INSTANT_APP_PACKAGE\n" +
            "06-20 15:41:38.924   500   538 D PackageManager: Clear ephemeral installer activity\n" +
            "06-20 15:41:38.995   500   538 I ActivityManager: Force stopping xxx.xxx.app appid=10076 user=0: pkg removed\n" +
            "06-20 15:41:39.017   500  2180 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_REMOVED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 15:41:39.041   500  2180 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_ADDED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 15:41:39.052   500  2180 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_REPLACED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 15:41:39.053   500   520 D AutofillManagerServiceImpl: Reset component for user 0 ()\n" +
            "06-20 15:41:39.053   500   520 D FieldClassificationStrategy: reset(): service is not bound. Do nothing.\n" +
            "06-20 15:41:39.053   500   524 D AutofillUI: destroySaveUiUiThread(): already destroyed\n" +
            "06-20 15:41:39.053   500   520 W VoiceInteractionManagerService: no available voice recognition services found for user 0\n" +
            "06-20 15:41:39.059   500   500 I Telecom : DefaultDialerCache: Refreshing default dialer for user 0: now com.android.dialer: DDC.oR@ALk\n" +
            "06-20 15:41:39.337   500  7197 I ActivityManager: START u0 {act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10000000 cmp=xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity} from uid 0\n" +
            "06-20 15:41:39.367   500   525 I ActivityManager: Start proc 8141:xxx.xxx.app/u0a76 for activity xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity\n" +
            "06-20 15:41:39.492   500   520 I UsageStatsService: User[0] Flushing usage stats to disk\n" +
            "06-20 15:41:40.726  8141  8167 E ActivityThread: Failed to find provider info for com.google.android.gms.phenotype\n" +
            "06-20 15:41:42.585   500  2180 D AutofillManagerServiceImpl: Reset component for user 0 ()\n" +
            "06-20 15:46:05.352   500   521 I ActivityManager: Force stopping xxx.xxx.app appid=10076 user=-1: installPackageLI\n" +
            "06-20 15:46:05.352   500   521 I ActivityManager: Killing 8141:xxx.xxx.app/u0a76 (adj 0): stop xxx.xxx.app\n" +
            "06-20 15:46:05.352   500   521 W ActivityManager: Scheduling restart of crashed service xxx.xxx.app/.OnAppKilledService in 1000ms\n" +
            "06-20 15:46:05.353   500   521 W ActivityManager: Force removing ActivityRecord{d8e9d72 u0 xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity t58}: app died, no saved state\n" +
            "06-20 15:46:05.459   500   538 I PackageManager: Update package xxx.xxx.app code path from /data/app/xxx.xxx.app-UnGOwZMBaW2NVqR-T7y_VA== to /data/app/xxx.xxx.app-diBM_L1XeQq4Hhh9VMfO1A==; Retain data and using new\n" +
            "06-20 15:46:05.459   500   538 I PackageManager: Update package xxx.xxx.app resource path from /data/app/xxx.xxx.app-UnGOwZMBaW2NVqR-T7y_VA== to /data/app/xxx.xxx.app-diBM_L1XeQq4Hhh9VMfO1A==; Retain data and using new\n" +
            "06-20 15:46:05.467   500   521 I ActivityManager:   Force stopping service ServiceRecord{abad6fc u0 xxx.xxx.app/.OnAppKilledService}\n" +
            "06-20 15:46:05.480   500   524 W Looper  : Slow dispatch took 127ms android.ui h=com.android.server.am.ActivityManagerService$UiHandler c=null m=53\n" +
            "06-20 15:46:05.481   500   533 W ActivityManager: setHasOverlayUi called on unknown pid: 8141\n" +
            "06-20 15:46:05.485   500   531 W Looper  : Slow dispatch took 115ms android.fg h=android.os.Handler c=null m=613\n" +
            "06-20 15:46:05.494   500   538 D PackageManager: Instant App installer not found with android.intent.action.INSTALL_INSTANT_APP_PACKAGE\n" +
            "06-20 15:46:05.494   500   538 D PackageManager: Clear ephemeral installer activity\n" +
            "06-20 15:46:05.550   500   538 I ActivityManager: Force stopping xxx.xxx.app appid=10076 user=0: pkg removed\n" +
            "06-20 15:46:05.579   500  2180 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_REMOVED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 15:46:05.616   500  2045 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_ADDED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 15:46:05.621   500   524 D AutofillUI: destroySaveUiUiThread(): already destroyed\n" +
            "06-20 15:46:05.621   500   500 I Telecom : DefaultDialerCache: Refreshing default dialer for user 0: now com.android.dialer: DDC.oR@AL4\n" +
            "06-20 15:46:05.668   500  8120 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_REPLACED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 15:46:05.673   500   520 D AutofillManagerServiceImpl: Reset component for user 0 ()\n" +
            "06-20 15:46:05.673   500   520 D FieldClassificationStrategy: reset(): service is not bound. Do nothing.\n" +
            "06-20 15:46:05.673   500   520 W VoiceInteractionManagerService: no available voice recognition services found for user 0\n" +
            "06-20 15:46:05.760   500   500 W Looper  : Slow dispatch took 137ms main h=com.android.server.notification.NotificationManagerService$WorkerHandler c=null m=3\n" +
            "06-20 15:46:06.079   500  8120 I ActivityManager: START u0 {act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10000000 cmp=xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity} from uid 0\n" +
            "06-20 15:46:06.118   500   525 I ActivityManager: Start proc 8229:xxx.xxx.app/u0a76 for activity xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity\n" +
            "06-20 15:46:08.463  8229  8256 E ActivityThread: Failed to find provider info for com.google.android.gms.phenotype\n" +
            "06-20 15:46:10.127   500  2180 D AutofillManagerServiceImpl: Reset component for user 0 ()\n" +
            "06-20 15:47:52.427   500   531 W Looper  : Slow dispatch took 200ms android.fg h=android.os.Handler c=com.android.server.Watchdog$HandlerChecker@887d16a m=0\n" +
            "06-20 15:55:03.190   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 15:55:11.132   500   597 W NetworkStats: problem registering for global alert: java.lang.IllegalStateException: command '92 bandwidth setglobalalert 2097152' failed with '400 92 Bandwidth command failed'\n" +
            "06-20 15:56:31.230   500   532 I EntropyMixer: Writing entropy...\n" +
            "06-20 15:56:31.784   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 15:56:31.785   500   527 I BatteryStatsImpl: Resetting battery stats: level=100 status=5 dischargeLevel=100 lowAmount=0 highAmount=0\n" +
            "06-20 15:56:31.789   500   521 I BinderCallsStatsService: Resetting stats\n" +
            "06-20 15:56:33.794   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 15:59:14.213   500   500 W Looper  : Slow delivery took 321ms main h=android.os.Handler c=com.android.server.-$$Lambda$BatteryService$2x73lvpB0jctMSVP4qb9sHAqRPw@c3d4732 m=0\n" +
            "06-20 15:59:14.213   500   500 W Looper  : Drained\n" +
            "06-20 15:59:17.459   500   500 W Looper  : Slow dispatch took 2201ms main h=android.app.ActivityThread$H c=android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA@474bc83 m=0\n" +
            "06-20 15:59:22.922   500   531 W Looper  : Slow dispatch took 398ms android.fg h=android.os.Handler c=com.android.server.Watchdog$HandlerChecker@887d16a m=0\n" +
            "06-20 15:59:27.269   500   532 I EntropyMixer: Writing entropy...\n" +
            "06-20 15:59:36.242   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 15:59:36.243   500   527 I BatteryStatsImpl: Resetting battery stats: level=100 status=5 dischargeLevel=100 lowAmount=0 highAmount=0\n" +
            "06-20 15:59:36.257   500   521 I BinderCallsStatsService: Resetting stats\n" +
            "06-20 15:59:38.272   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 16:00:07.245   500   521 I ActivityManager: Force stopping xxx.xxx.app appid=10076 user=-1: installPackageLI\n" +
            "06-20 16:00:07.245   500   521 I ActivityManager: Killing 8229:xxx.xxx.app/u0a76 (adj 0): stop xxx.xxx.app\n" +
            "06-20 16:00:07.245   500   521 W ActivityManager: Scheduling restart of crashed service xxx.xxx.app/.OnAppKilledService in 1000ms\n" +
            "06-20 16:00:07.246   500   521 W ActivityManager: Force removing ActivityRecord{72f899e u0 xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity t59}: app died, no saved state\n" +
            "06-20 16:00:07.389   500   538 I PackageManager: Update package xxx.xxx.app code path from /data/app/xxx.xxx.app-diBM_L1XeQq4Hhh9VMfO1A== to /data/app/xxx.xxx.app-jbcQADT5zIP8sfsYmvPkMg==; Retain data and using new\n" +
            "06-20 16:00:07.389   500   538 I PackageManager: Update package xxx.xxx.app resource path from /data/app/xxx.xxx.app-diBM_L1XeQq4Hhh9VMfO1A== to /data/app/xxx.xxx.app-jbcQADT5zIP8sfsYmvPkMg==; Retain data and using new\n" +
            "06-20 16:00:07.393   500   521 I ActivityManager:   Force stopping service ServiceRecord{900a2c8 u0 xxx.xxx.app/.OnAppKilledService}\n" +
            "06-20 16:00:07.394   500   524 W Looper  : Slow dispatch took 148ms android.ui h=com.android.server.am.ActivityManagerService$UiHandler c=null m=53\n" +
            "06-20 16:00:07.395   500   533 W ActivityManager: setHasOverlayUi called on unknown pid: 8229\n" +
            "06-20 16:00:07.423   500   538 D PackageManager: Instant App installer not found with android.intent.action.INSTALL_INSTANT_APP_PACKAGE\n" +
            "06-20 16:00:07.423   500   538 D PackageManager: Clear ephemeral installer activity\n" +
            "06-20 16:00:07.691   500   538 I ActivityManager: Force stopping xxx.xxx.app appid=10076 user=0: pkg removed\n" +
            "06-20 16:00:07.756   500  7273 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_REMOVED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 16:00:07.762   500   524 D AutofillUI: destroySaveUiUiThread(): already destroyed\n" +
            "06-20 16:00:07.762   500   520 D AutofillManagerServiceImpl: Reset component for user 0 ()\n" +
            "06-20 16:00:07.762   500   520 D FieldClassificationStrategy: reset(): service is not bound. Do nothing.\n" +
            "06-20 16:00:07.762   500   520 W VoiceInteractionManagerService: no available voice recognition services found for user 0\n" +
            "06-20 16:00:07.875   500   500 W Looper  : Slow dispatch took 111ms main h=android.app.ActivityThread$H c=android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA@10cd68e m=0\n" +
            "06-20 16:00:07.892   500  7273 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_ADDED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 16:00:07.912   500   500 I Telecom : DefaultDialerCache: Refreshing default dialer for user 0: now com.android.dialer: DDC.oR@AMM\n" +
            "06-20 16:00:07.912   500   500 W Looper  : Slow delivery took 200ms main h=com.android.server.media.MediaRouterService$UserHandler c=android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA@8e0ea8 m=0\n" +
            "06-20 16:00:07.925   500  8320 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_REPLACED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 16:00:07.981   500   500 W Looper  : Drained\n" +
            "06-20 16:00:08.778   500   811 I ActivityManager: START u0 {act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10000000 cmp=xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity} from uid 0\n" +
            "06-20 16:00:08.810   500   525 I ActivityManager: Start proc 8341:xxx.xxx.app/u0a76 for activity xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity\n" +
            "06-20 16:00:10.138  8341  8364 E ActivityThread: Failed to find provider info for com.google.android.gms.phenotype\n" +
            "06-20 16:00:12.079   500   811 D AutofillManagerServiceImpl: Reset component for user 0 ()\n" +
            "06-20 16:04:05.221   500   532 I EntropyMixer: Writing entropy...\n" +
            "06-20 16:04:05.568   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 16:04:05.595   500   527 I BatteryStatsImpl: Resetting battery stats: level=100 status=5 dischargeLevel=100 lowAmount=0 highAmount=0\n" +
            "06-20 16:04:05.597   500   521 I BinderCallsStatsService: Resetting stats\n" +
            "06-20 16:04:07.660   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 16:06:05.219   500   520 I UsageStatsService: User[0] Flushing usage stats to disk\n" +
            "06-20 16:08:20.016   500   521 I ActivityManager: Force stopping xxx.xxx.app appid=10076 user=-1: installPackageLI\n" +
            "06-20 16:08:20.017   500   521 I ActivityManager: Killing 8341:xxx.xxx.app/u0a76 (adj 0): stop xxx.xxx.app\n" +
            "06-20 16:08:20.017   500   521 W ActivityManager: Scheduling restart of crashed service xxx.xxx.app/.OnAppKilledService in 1000ms\n" +
            "06-20 16:08:20.017   500   521 W ActivityManager: Force removing ActivityRecord{7f90f1c u0 xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity t60}: app died, no saved state\n" +
            "06-20 16:08:20.034   500   538 I PackageManager: Update package xxx.xxx.app code path from /data/app/xxx.xxx.app-jbcQADT5zIP8sfsYmvPkMg== to /data/app/xxx.xxx.app-34J2t7kkA5yktBgLXNGn7A==; Retain data and using new\n" +
            "06-20 16:08:20.034   500   538 I PackageManager: Update package xxx.xxx.app resource path from /data/app/xxx.xxx.app-jbcQADT5zIP8sfsYmvPkMg== to /data/app/xxx.xxx.app-34J2t7kkA5yktBgLXNGn7A==; Retain data and using new\n" +
            "06-20 16:08:20.036   500   521 I ActivityManager:   Force stopping service ServiceRecord{ed5e157 u0 xxx.xxx.app/.OnAppKilledService}\n" +
            "06-20 16:08:20.070   500   533 W ActivityManager: setHasOverlayUi called on unknown pid: 8341\n" +
            "06-20 16:08:20.073   500   538 D PackageManager: Instant App installer not found with android.intent.action.INSTALL_INSTANT_APP_PACKAGE\n" +
            "06-20 16:08:20.073   500   538 D PackageManager: Clear ephemeral installer activity\n" +
            "06-20 16:08:20.126   500   538 I ActivityManager: Force stopping xxx.xxx.app appid=10076 user=0: pkg removed\n" +
            "06-20 16:08:20.173   500   559 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_REMOVED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 16:08:20.189   500   520 D AutofillManagerServiceImpl: Reset component for user 0 ()\n" +
            "06-20 16:08:20.189   500   520 D FieldClassificationStrategy: reset(): service is not bound. Do nothing.\n" +
            "06-20 16:08:20.190   500   520 W VoiceInteractionManagerService: no available voice recognition services found for user 0\n" +
            "06-20 16:08:20.192   500   524 D AutofillUI: destroySaveUiUiThread(): already destroyed\n" +
            "06-20 16:08:20.221   500   500 I Telecom : DefaultDialerCache: Refreshing default dialer for user 0: now com.android.dialer: DDC.oR@AMs\n" +
            "06-20 16:08:20.225   500   559 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_ADDED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 16:08:20.227   500   559 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_REPLACED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 16:08:20.873   500   811 I ActivityManager: START u0 {act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10000000 cmp=xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity} from uid 0\n" +
            "06-20 16:08:20.900   500   525 I ActivityManager: Start proc 8443:xxx.xxx.app/u0a76 for activity xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity\n" +
            "06-20 16:08:22.135  8443  8466 E ActivityThread: Failed to find provider info for com.google.android.gms.phenotype\n" +
            "06-20 16:08:23.654   500  7197 D AutofillManagerServiceImpl: Reset component for user 0 ()\n" +
            "06-20 16:08:30.250   500  7197 I ActivityManager: START u0 {flg=0x14000000 cmp=xxx.xxx.app/.MainActivity (has extras)} from uid 10076\n" +
            "06-20 16:08:32.360   500  8422 W NotificationService: Toast already killed. pkg=xxx.xxx.app callback=android.app.ITransientNotification$Stub$Proxy@e99f90d\n" +
            "06-20 16:08:33.753   500   533 W WindowManager: Unable to start animation, surface is null or no children.\n" +
            "06-20 16:08:43.368   500   500 W WindowManager: removeWindowToken: Attempted to remove non-existing token: android.os.Binder@4e5ca77\n" +
            "06-20 16:09:41.338   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 16:14:49.222   500   532 I EntropyMixer: Writing entropy...\n" +
            "06-20 16:14:49.342   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 16:14:49.344   500   527 I BatteryStatsImpl: Resetting battery stats: level=100 status=5 dischargeLevel=100 lowAmount=0 highAmount=0\n" +
            "06-20 16:14:49.347   500   521 I BinderCallsStatsService: Resetting stats\n" +
            "06-20 16:14:51.351   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 16:15:06.995   500   521 I ActivityManager: Force stopping xxx.xxx.app appid=10076 user=-1: installPackageLI\n" +
            "06-20 16:15:06.995   500   521 I ActivityManager: Killing 8443:xxx.xxx.app/u0a76 (adj 0): stop xxx.xxx.app\n" +
            "06-20 16:15:06.996   500   521 W ActivityManager: Scheduling restart of crashed service xxx.xxx.app/.KeepAliveService in 1000ms\n" +
            "06-20 16:15:06.996   500   521 W ActivityManager: Scheduling restart of crashed service xxx.xxx.app/.OnAppKilledService in 11000ms\n" +
            "06-20 16:15:06.996   500   521 W ActivityManager: Force removing ActivityRecord{c447668 u0 xxx.xxx.app/.MainActivity t62}: app died, no saved state\n" +
            "06-20 16:15:07.017   500   538 I PackageManager: Update package xxx.xxx.app code path from /data/app/xxx.xxx.app-34J2t7kkA5yktBgLXNGn7A== to /data/app/xxx.xxx.app-EllB6uUZ1roch6JM14fEsQ==; Retain data and using new\n" +
            "06-20 16:15:07.017   500   538 I PackageManager: Update package xxx.xxx.app resource path from /data/app/xxx.xxx.app-34J2t7kkA5yktBgLXNGn7A== to /data/app/xxx.xxx.app-EllB6uUZ1roch6JM14fEsQ==; Retain data and using new\n" +
            "06-20 16:15:07.018   500   521 I ActivityManager:   Force stopping service ServiceRecord{cc4df12 u0 xxx.xxx.app/.KeepAliveService}\n" +
            "06-20 16:15:07.018   500   521 I ActivityManager:   Force stopping service ServiceRecord{8234343 u0 xxx.xxx.app/.OnAppKilledService}\n" +
            "06-20 16:15:07.054   500   533 W ActivityManager: setHasOverlayUi called on unknown pid: 8443\n" +
            "06-20 16:15:07.078   500   538 D PackageManager: Instant App installer not found with android.intent.action.INSTALL_INSTANT_APP_PACKAGE\n" +
            "06-20 16:15:07.078   500   538 D PackageManager: Clear ephemeral installer activity\n" +
            "06-20 16:15:07.139   500   538 I ActivityManager: Force stopping xxx.xxx.app appid=10076 user=0: pkg removed\n" +
            "06-20 16:15:07.168   500  8422 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_REMOVED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 16:15:07.183   500  1970 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_ADDED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 16:15:07.190   500  1970 W BroadcastQueue: Background execution not allowed: receiving Intent { act=android.intent.action.PACKAGE_REPLACED dat=package:xxx.xxx.app flg=0x4000010 (has extras) } to com.android.musicfx/.Compatibility$Receiver\n" +
            "06-20 16:15:07.210   500   520 D AutofillManagerServiceImpl: Reset component for user 0 ()\n" +
            "06-20 16:15:07.210   500   520 D FieldClassificationStrategy: reset(): service is not bound. Do nothing.\n" +
            "06-20 16:15:07.210   500   520 W VoiceInteractionManagerService: no available voice recognition services found for user 0\n" +
            "06-20 16:15:07.730   500   524 W Looper  : Slow delivery took 530ms android.ui h=com.android.server.am.ActivityManagerService$UiHandler c=null m=53\n" +
            "06-20 16:15:07.730   500   524 D AutofillUI: destroySaveUiUiThread(): already destroyed\n" +
            "06-20 16:15:07.730   500   524 W Looper  : Drained\n" +
            "06-20 16:15:07.731   500   500 I Telecom : DefaultDialerCache: Refreshing default dialer for user 0: now com.android.dialer: DDC.oR@AM0\n" +
            "06-20 16:15:07.731   500   500 W Looper  : Slow dispatch took 528ms main h=android.app.ActivityThread$H c=android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA@7158a73 m=0\n" +
            "06-20 16:15:07.731   500   500 W Looper  : Slow delivery took 565ms main h=com.android.server.media.MediaRouterService$UserHandler c=android.app.-$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA@c4d2d30 m=0\n" +
            "06-20 16:15:07.739   500   500 W Looper  : Drained\n" +
            "06-20 16:15:08.189   500  8320 I ActivityManager: START u0 {act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10000000 cmp=xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity} from uid 0\n" +
            "06-20 16:15:08.231   500   525 I ActivityManager: Start proc 8561:xxx.xxx.app/u0a76 for activity xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity\n" +
            "06-20 16:15:10.230  8561  8584 E ActivityThread: Failed to find provider info for com.google.android.gms.phenotype\n" +
            "06-20 16:15:14.018   500   559 D AutofillManagerServiceImpl: Reset component for user 0 ()\n" +
            "06-20 16:18:55.464   500  1970 I ActivityManager: START u0 {flg=0x14000000 cmp=xxx.xxx.app/.MainActivity (has extras)} from uid 10076\n" +
            "06-20 16:18:57.272   500  8320 W NotificationService: Toast already killed. pkg=xxx.xxx.app callback=android.app.ITransientNotification$Stub$Proxy@43a79d0\n" +
            "06-20 16:18:58.969   500   533 W WindowManager: Unable to start animation, surface is null or no children.\n" +
            "06-20 16:19:08.279   500   500 W WindowManager: removeWindowToken: Attempted to remove non-existing token: android.os.Binder@771be0\n" +
            "06-20 16:20:04.019   500  8320 I ActivityManager: START u0 {flg=0x14000000 cmp=xxx.xxx.app/.LaunchActivity (has extras)} from uid 10076\n" +
            "06-20 16:20:07.808   500  1970 I ActivityManager: START u0 {flg=0x14000000 cmp=xxx.xxx.app/.MainActivity (has extras)} from uid 10076\n" +
            "06-20 16:20:10.290   500  8320 W NotificationService: Toast already killed. pkg=xxx.xxx.app callback=android.app.ITransientNotification$Stub$Proxy@a4f03f1\n" +
            "06-20 16:20:11.093   500  1970 I WindowManager: WIN DEATH: Window{1ec1080 u0 PopupWindow:63563dc}\n" +
            "06-20 16:20:11.095   500  2180 I ActivityManager: Process xxx.xxx.app (pid 8561) has died: fore TOP \n" +
            "06-20 16:20:11.096   500  2180 W ActivityManager: Scheduling restart of crashed service xxx.xxx.app/.KeepAliveService in 1000ms\n" +
            "06-20 16:20:11.097   500  2180 W ActivityManager: Scheduling restart of crashed service xxx.xxx.app/.OnAppKilledService in 11000ms\n" +
            "06-20 16:20:11.104   500  1727 I WindowManager: WIN DEATH: Window{3b82ce4 u0 xxx.xxx.app/xxx.xxx.app.MainActivity}\n" +
            "06-20 16:20:11.116   500   533 W ActivityManager: setHasOverlayUi called on unknown pid: 8561\n" +
            "06-20 16:20:11.310   500   533 W WindowManager: Unable to start animation, surface is null or no children.\n" +
            "06-20 16:20:12.111   500   525 I ActivityManager: Start proc 8634:xxx.xxx.app/u0a76 for service xxx.xxx.app/.KeepAliveService\n" +
            "06-20 16:20:13.015  8634  8662 E ActivityThread: Failed to find provider info for com.google.android.gms.phenotype\n" +
            "06-20 16:20:16.865   500  8632 I ActivityManager: START u0 {act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10200000 cmp=xxx.xxx.app/xxx.android.core.pl.activities.LaunchActivity bnds=[20,256][130,383]} from uid 10026\n" +
            "06-20 16:20:21.288   500   500 W WindowManager: removeWindowToken: Attempted to remove non-existing token: android.os.Binder@a3e9916\n" +
            "06-20 16:20:25.158   500  8632 W NotificationService: Toast already killed. pkg=xxx.xxx.app callback=android.app.ITransientNotification$Stub$Proxy@211cce2\n" +
            "06-20 16:20:27.530   500  8632 I ActivityManager: START u0 {flg=0x14000000 cmp=xxx.xxx.app/.MainActivity (has extras)} from uid 10076\n" +
            "06-20 16:20:36.163   500   500 W WindowManager: removeWindowToken: Attempted to remove non-existing token: android.os.Binder@bca3332\n" +
            "06-20 16:25:03.202   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 16:25:11.144   500   597 W NetworkStats: problem registering for global alert: java.lang.IllegalStateException: command '93 bandwidth setglobalalert 2097152' failed with '400 93 Bandwidth command failed'\n" +
            "06-20 16:28:20.063   500   520 I UsageStatsService: User[0] Flushing usage stats to disk\n" +
            "06-20 16:35:27.754   500   559 I ActivityManager: START u0 {flg=0x14000000 cmp=xxx.xxx.app/.LaunchActivity (has extras)} from uid 10076\n" +
            "06-20 16:55:03.195   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 16:55:11.130   500   597 W NetworkStats: problem registering for global alert: java.lang.IllegalStateException: command '94 bandwidth setglobalalert 2097152' failed with '400 94 Bandwidth command failed'\n" +
            "06-20 16:55:27.733   500   520 I UsageStatsService: User[0] Flushing usage stats to disk\n" +
            "06-20 17:25:03.197   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 17:25:09.738   500   532 I EntropyMixer: Writing entropy...\n" +
            "06-20 17:25:13.891   500   525 I ActivityManager: Start proc 8770:com.android.providers.calendar/u0a6 for content provider com.android.providers.calendar/.CalendarProvider2\n" +
            "06-20 17:25:13.898   500   520 I UsageStatsService: User[0] Rolling over usage stats\n" +
            "06-20 17:25:13.898   500   520 I UsageStatsService: User[0] Flushing usage stats to disk\n" +
            "06-20 17:25:13.924   500   520 I UsageStatsService: User[0] Rollover scheduled @ 2019-06-21 17:25:13(1561130713898)\n" +
            "06-20 17:25:13.924   500   520 I UsageStatsService: User[0] Flushing usage stats to disk\n" +
            "06-20 17:25:13.933   500   520 I UsageStatsService: User[0] Rolling over usage stats complete. Took 35 milliseconds\n" +
            "06-20 17:25:21.949   500   597 W NetworkStats: problem registering for global alert: java.lang.IllegalStateException: command '95 bandwidth setglobalalert 2097152' failed with '400 95 Bandwidth command failed'\n" +
            "06-20 17:25:21.949   500   525 I ActivityManager: Start proc 8790:com.android.dialer/u0a12 for service com.android.dialer/com.android.voicemail.impl.StatusCheckJobService\n" +
            "06-20 17:25:21.994   500   559 I ActivityManager: Killing 7055:com.android.externalstorage/u0a29 (adj 906): empty for 10411s\n" +
            "06-20 17:26:00.002   500   500 I ActivityManager: Killing 6967:com.android.settings/1000 (adj 906): empty for 10449s\n" +
            "06-20 17:26:00.026   500   533 W ActivityManager: setHasOverlayUi called on unknown pid: 6967\n" +
            "06-20 17:42:00.507   500   520 I ProcessStatsService: Pruning old procstats: /data/system/procstats/state-2019-05-27-19-20-00.bin\n" +
            "06-20 17:45:13.983   500   520 I UsageStatsService: User[0] Flushing usage stats to disk\n" +
            "06-20 17:55:03.200   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 17:55:11.131   500   597 W NetworkStats: problem registering for global alert: java.lang.IllegalStateException: command '96 bandwidth setglobalalert 2097152' failed with '400 96 Bandwidth command failed'\n" +
            "06-20 18:24:53.221   500   532 I EntropyMixer: Writing entropy...\n" +
            "06-20 18:24:53.239   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 18:24:53.239   500   527 I BatteryStatsImpl: Resetting battery stats: level=100 status=5 dischargeLevel=100 lowAmount=0 highAmount=0\n" +
            "06-20 18:24:53.244   500   521 I BinderCallsStatsService: Resetting stats\n" +
            "06-20 18:24:55.246   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n" +
            "06-20 18:25:03.205   500   527 W BatteryExternalStatsWorker: timeout reading telephony stats\n";
    //endregion
}

