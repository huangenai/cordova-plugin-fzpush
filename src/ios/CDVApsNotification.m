/*
 * JBoss, Home of Professional Open Source.
 * Copyright Red Hat, Inc., and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
#import "CDVApsNotification.h"
#import <Cordova/CDVAppDelegate.h>

@interface CDVAppDelegate (apsNotification)
@end
@implementation CDVAppDelegate (apsNotification)
- (id) getCommandInstance:(NSString*)className
{
    return [self.viewController getCommandInstance:className];
}
- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    CDVApsNotification *plugin = [self getCommandInstance:@"ApsNotificationPlugin"];
    [plugin didRegisterForRemoteNotificationsWithDeviceToken:deviceToken];
}
- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {
    CDVApsNotification *plugin = [self getCommandInstance:@"ApsNotificationPlugin"];
    [plugin didFailToRegisterForRemoteNotificationsWithError:error];
}
@end


@interface CDVApsNotification()
@property (nonatomic,copy) NSString *callbackId;//用于返回deviceToken时回调
@end
@implementation CDVApsNotification

//供js调用，注册aps
- (void)registerRemoteApsNotification:(CDVInvokedUrlCommand *)command
{
    self.callbackId = command.callbackId;
    
#if __IPHONE_OS_VERSION_MAX_ALLOWED >= 80000
    if ([[UIApplication sharedApplication] respondsToSelector:@selector(registerUserNotificationSettings:)]) {
        UIUserNotificationSettings* notificationSettings = [UIUserNotificationSettings settingsForTypes:UIUserNotificationTypeAlert | UIUserNotificationTypeBadge | UIUserNotificationTypeSound categories:nil];
        [[UIApplication sharedApplication] registerUserNotificationSettings:notificationSettings];
        [[UIApplication sharedApplication] registerForRemoteNotifications];
    } else {
        [[UIApplication sharedApplication] registerForRemoteNotificationTypes: (UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeSound | UIRemoteNotificationTypeAlert)];
    }
    
#else
    [[UIApplication sharedApplication] registerForRemoteNotificationTypes: (UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeSound | UIRemoteNotificationTypeAlert)];
#endif
    
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_NO_RESULT];
    [pluginResult setKeepCallback:@YES];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

//供appDelegate回调使用
- (void)didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    
    NSString *deviceTokenStr = [deviceToken description];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:deviceTokenStr];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.callbackId];
}

//供appDelegate回调使用
- (void)didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
    NSString *errorMessage = [NSString stringWithFormat:@"注册aps失败- %@", [error localizedDescription]];
    CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:errorMessage];
    [self.commandDelegate sendPluginResult:commandResult callbackId:self.callbackId];
}

@end
