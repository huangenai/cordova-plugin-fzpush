
var exec = require('cordova/exec'),ApsNotification = function() {};

/**
    成功和失败的回调，各有一个字符串参数，分别是deviceToken和错误信息
**/
ApsNotification.prototype.registerRemoteApsNotification = function(success, fail) {
    return exec(success, fail, "ApsNotificationPlugin", "registerRemoteApsNotification", []);
};

ApsNotification.prototype.getCordovaIntent = function(successCallback, failureCallback) {
    return exec (
        successCallback,
        failureCallback,
        "ApsNotification",
        "getCordovaIntent",
        []
    );
};

module.exports = new ApsNotification();