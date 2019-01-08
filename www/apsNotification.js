
var exec = require('cordova/exec'),fzPush = function() {};
fzPush.prototype.getCordovaIntent = function(successCallback, failureCallback) {
    return exec (
        successCallback,
        failureCallback,
        "ApsNotification",
        "getCordovaIntent",
        []
    );
};

fzPush.prototype.registerRemoteApsNotification = function(success, fail) {
    return exec(success, fail, "ApsNotificationPlugin", "registerRemoteApsNotification", []);
};

module.exports = new fzPush();
