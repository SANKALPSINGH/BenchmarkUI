package com.bsb.hike.qa.constants;

import java.util.HashSet;
import java.util.Set;


public class HikeConstants
{
    //IP Messages, SMS credits, Error sending messages
    public static final String STOPIC_MESSAGE = "s";

    //Typing notifications, delivery reports, message read reports
    public static final String STOPIC_UI = "u";

    //user joined hike.
    public static final String STOPIC_APP = "a";

    // sms to a msisdn
    public static final String STOPIC_SMS = "sms";

    public static final String HIKE_SYSTEM_TOPIC = "/topic/hike_im_system";

    public static final String REDIS_SHORT_CODE_PREFIX = "sc-";
    public static final String REDIS_MISSED_CALL_PREFIX = "mc-";
    public static final String REDIS_INVITERS_SUFFIX = "-inviters";
    public static final String REDIS_INVITES_SENT = "invites-sent";
    public static final String REDIS_INVITES_SENT_TODAY = "invites-sent-today-";
    public static final String REDIS_GROUP_PREFIX = "grp-";
    public static final String REDIS_GROUP_LEAD_HASH = "grp-lead";
    public static final String REDIS_GROUP_NAME = "groupname";
    public static final String REDIS_ICONS = "icons";
    public static final String REDIS_USER_CACHE_PREFIX = "uc:";
    public static final String REDIS_USER_CACHE_DEVICES_FIELD = "devices";
    public static final String REDIS_USER_CACHE_CONNECT_FIELD = "connect";


    public static final String MONGO_NON_HIKERS_COLLECTION = "nonhikers";
    public static final String MONGO_USERS_COLLECTION = "users";
    public static final String MONGO_GROUPS_COLLECTION = "groups";
    public static final String MONGO_ENCRYPTION_KEYS_COLLECTION = "encryption_keys";
    public static final String MONGO_SPAMTRAINING = "spamtraining";
    public static final String MONGO_REVERSE_ADDRESSBOOK_COLLECTION = "reverseab";
    public static final String MONGO_FAVORITES = "favorites";
    public static final String MONGO_DEVICES = "devices";
    public static final String MONGO_MUTED = "muted";
    public static final String MONGO_ACCOUNTS_COLLECTION = "accounts";
    public static final String MONGO_MESSAGES_COLLECTION = "messages";
    public static final String MONGO_RECHARGE_COLLECTION = "recharges";

    public static final String FAVORITE_STATE = "favorite_state";

    public static final String MONGO_CHAT_BACKGROUND_DATA_COLLECTION = "chat_background_data";
    public static final String MONGO_USER_CHAT_BACKGROUND_COLLECTION = "user_chat_background";
    public static final String MONGO_GROUP_CHAT_BACKGROUND_DATA = "cbg";

    public static final String TRAIDBLIST = "traidblist";

    public static final String BLOCKLIST = "blocklist";

    public static final String BLOCKEELIST = "blockeelist";

    public static final String OPERATORSHORTCODES = "operatorShortCodes";

    public static final String MONGO_QUERY_TYPE = "type";

    public static final String CODES = "codes";

    public static final String MONGO_ID = "_id";

    public static final String SHORTCODES = "shortcodes";

    public static final String OPERATOR_NAME = "operatorname";

    public static final String COMMON_BLOCKLIST = "common_blocklist";

    public static final String SPAM_TRAINING = "spamtraining";

    public static final String SMS_USER = "sms_user";

    public static final String SMS_KEYWORD = "*";

    public static final String OPT_OUT_MISSED_CALL_NUMBER = "+919266630849";
    public static final String OPT_IN_MISSED_CALL_NUMBER = "+919266630847";

    public static final int API_VERSION_ONE = 1;
    public static final int API_VERSION_TWO = 2;

    public static final String INVITE_MSG_STRING = "I'm using hike, an awesome new free messaging app! Download the app at http://get.hike.in to start messaging me for free!";

    public static final String RECIPIENT_ON_DND_STRING = "***We can't send your friend a message as this person is on the DND list. Why don't you have them opt-in to receive your messages? Just ask them to give a missed call or SMS 'optin' to %1$s. You'll get an extra 10 free SMS' per month for every person you have opt-in!\n" +
            "To send this information as a normal SMS, tap here.***";

    public static final String RECIPIENT_ON_DND_STRING_FIRST_MSG = "We sent the message.\n%1$s is on DND. To continue chatting with %1$s, have %1$s reply to opt-in to hike.\n\n" +
            "You'll get 10 extra free hike SMS per month if %1$s opts-in.";

    public static final String GROUP_RECIPIENT_ON_DND_STRING = "***We can't add your friend %1$s to group chat as this person is on the DND list. Why don't you have them opt-in to participate in group chat? Just ask them to give a missed call or SMS '*optin' to %2$s. You'll get an extra 10 free SMS' per month for every person you have opt-in!\n" +
            "To send this information to %3$s as a normal SMS, tap here.***";

    public static final String GROUP_RECIPIENTS_ON_DND_STRING = "***We can't add your friends %1$s to group chat as they are on the DND list. Why don't you have them opt-in to participate in group chat? Just ask them to give a missed call or SMS '*optin' to %2$s. You'll get an extra 10 free SMS' per month for every person you have opt-in!\n" +
            "To send this information to %3$s as a normal SMS, tap here.***";

    public static final String RECIPIENT_ON_DND_STRING_ALREADY_OPTOUT = "***We can't send your friend a message as this person is on the DND list. Why don't you have them opt-in to receive your messages? Just ask them to give a missed call or SMS 'optin' to %1$s. You'll be able to chat with him once your friend opt-in!\n" +
            "To send this information as a normal SMS, tap here.***";

    public static final String RECIPIENT_ON_DND_STRING_FIRST_MSG_ALREADY_OPTOUT = "We've sent your message.\n%1$s is on DND. To keep messaging %1$s through hike for free, have the person reply to your message to opt-in to hike\n";

    public static final String GROUP_LEAVE_SMS_STRING = "You've left the group chat.";

    public static final String GROUP_END_SMS_STRING = "The group chat has ended.";
    
    public static final String GROUP_LEFT_SMS_STRING = "%1$s has left this group chat.";
    
    public static final String GROUP_NEW_MEMBERS_SMS_STRING = "%1$s has been added to this group chat.";

    public static final int MISSED_CALL_OPTIN_NUMBER_TIMEOUT = 24 * 60 * 60 * 3; /* 3 days */

    public static final String MESSAGE_TO_SMS_USER = "Thanks for opting in to hike! You'll now be able to receive messages from friends and family on hike. To opt-out at anytime, call " + OPT_OUT_MISSED_CALL_NUMBER + ".";

    public static final String OPT_IN_HIKE_MESSAGE = "Great job! %1$s just opted into hike. You're a star. And as promised here are your extra 10 free SMS. Enjoy!";

    public static final String OPT_IN_AFTER_OPT_OUT_HIKE_MESSAGE = "Great job! %1$s just opted into hike. You're a star. You can chat with him now. Enjoy!";

    public static final String OPTOUT_MESSAGE_TO_SMS_USER = "You've opted out of hike. We're sad to see you go :(. You can opt in again at anytime by giving us a missed call on " + OPT_IN_MISSED_CALL_NUMBER;

    public static final String WEBSITE_OPTIN_SMS_MSG = "You're now verified! With hike you'll be able to message friends and family for free. Here's the link to download the app - http://get.hike.in/%1$s.";

    public static final String CAMPAIGN_OPTIN_SMS_MSG = "You have joined the Spam Free revolution! With hike you'll be able to message friends and family for free. Download- http://get.hike.in/%1$s/%2$s";

    public static final String STICKER_MESSAGE = "sent you a hike sticker";
    
    public static final String UPGRADE_TO_STICKER_BUILD = ".\r\nUpdate to latest version of hike to make messaging more fun and expressive with stickers. http://get.hike.in";
    
    public static final String UPGRADE_ANDROID_POPUP_SUPPORTED_ZOOKEEPER_UPDATE = "older_supported_android_update";
    
    public static final String UPGRADE_ANDROID_POPUP_UNSUPPORTED_ZOOKEEPER_MESSAGE = "older_unsupported_android_message";
    
    public static final String MSISDN = "msisdn";
    
    public static final String UID = "uid";
    
    public static final String RELEVANT_CHAT_SERVICE_ACTION = "action";

    public static final String OPT_IN_INVITEE = "opt_in";
    public static final String CONNECTED_ACCOUNTS = "accounts";

    public static final String SMS_ROUTE = "smsroute";

    public static final String GROUP_OWNER = "owner";
    public static final String GROUP_MSISDN = "msisdn";
    public static final String GROUP_UID = "uid";
    public static final String GROUP_NAME = "name";

    public static final String TD_HIKE = "TD-HIKE";
    public static final String HIKE = "+HIKE+";
    public static final String HIKE_TEAM = "+hike+";
    public static final String EMMA = "+hike1+";
    public static final String HIKE_DAILY = "+hike3+";
    

    public static final String BACKGROUND_MSISDN1 = "m1";
    public static final String BACKGROUND_MSISDN2 = "m2";
    public static final String BACKGROUND_ID = "bg_id";
    public static final String BACKGROUND_IMAGE = "img";
    public static final String BACKGROUND_CUSTOM = "custom";
    public static final String BACKGROUND_OWNER = "owner";
    public static final String BACKGROUND_STATE1 = "s1";
    public static final String BACKGROUND_STATE2 = "s2";
    

    public static final String CLIENT_ID_SEPARATOR = ":";

    public static final String GROUP_CHAT_NOT_SUPPORTED_UPGRADE = "You've been invited to a groupchat but you'll need to upgrade your app.  https://play.google.com/store/apps/details?id=com.bsb.hike";

    public static final String STATUS_UPDATE_NOT_SUPPORTED_UPGRADE = "I just posted a status update. To view it, update to hike 2.0 via the App Store or http://get.hike.in";
    
    public static final String CHAT_BACKGROUND_NOT_SUPPORTED_UPGRADE = "I just changed our chat theme :) Update to get chat themes - http://hike.in. They're awesome!";

    public static final String CHAT_BACKGROUND_MESSAGE_FOR_SMS_USER = "I just changed our chat theme :) Download hike messenger and get awesome new chat themes - http://hike.in";
    
    public static final String CHAT_BACKGROUND_PUSH_NOT_SUPPORTED_UPGRADE = "just changed the chat theme. Update the app to get awesome new chat themes! http://hike.in";
    
    public static final String HIKE_JMS_CLIENTID = "hike";
    public static final String HIKE_JMS_SMS_TOPIC_SUFFIX = "_durable_dest_sms";

    public static final String USER_ON_DND_MESSAGE = "Waiting for dnd users to join";

    // stickers related
    public static final String CATEGORY_ID = "catId";
    public static final String STICKER_ID = "stId";
    public static final Boolean STICKER_HIDE_SETTING = false;
    
    public static final Object ONHIKE = "onhike";
    public static final Object ONDND = "dnd";


    public static final String FILE_TRANSFER_URL_PREFIX = ". To view, go to ";

    public static final String FILE_TRANSFER_MESSAGE = "sent you a file";
    
    public static final String AUDIO_TRANSFER_MESSAGE = "sent you an audio file";
    
    public static final String VIDEO_TRANSFER_MESSAGE = "sent you a video";
    
    public static final String IMAGE_TRANSFER_MESSAGE = "sent you a photo";
    
    public static final String STICKER_SEND_MESSAGE = STICKER_MESSAGE+", view it at ";
    
    public static final String LOCATION_SHARING_MESSAGE = "shared a location";
    
    public static final String CONTACT_SHARING_MESSAGE = "shared a contact";
    
    public static final String HIKE_USER_JOINED_PUSH_MESSAGE = "Hi! I just joined hike!";
    
    public static final String HIKE_STATUS_UPDATE_SUPPORTED_MIN_APP_VERSION = "1.9";

    public static final String RECHARGE_RECEIPT = "Recharge status: %1$s. Hike did recharge using %2$s and recharge id is:%3$d.";

    public static final int MINIMUM_ALLOWED_CREDITS = 50;

    public static final int NEW_USER_BONUS = 250;

    public static final String NEW_USER_BONUS_MESSAGE = "Thanks for joining Hike.  Here's some more credits for being awesome!";

    public static final int TOPUP_BONUS = 50;

    public static final String TOP_UP_BONUS_MESSAGE = "We saw you were low on credits. Here's some more for being awesome!";

    public static final String ID = "id";
    
    public static final String NAME = "name";

    public static final String UPGRADE = "upgrade";

    public static final String PENDING_MESSAGE = "\r\n\r\nYou have %1$d unread messages from %2$s on hike.";
    
    public static final String RECHARGE_MSISDN = "rmsisdn";
    public static final String RECHARGE_AMOUNT = "amt";
    public static final String RECHARGE_STATUS = "s";
    public static final String RECHARGE_OPERATOR = "o";
    public static final String RECHARGE_PLAN = "p";
    public static final String RECHARGE_CIRCLE = "c";
    public static final String RECHARGE_CREATED_TS = "ts";
    public static final String RECHARGE_CHANGED_TS = "cts";
    public static final String RECHARGE_UID = "uid";
    public static final String RECHARGE_THIRD_PARTY_ID = "tpid";
    public static final String RECHARGE_THIRD_PARTY_TS = "tpts";
    public static final String RECHARGE_THIRD_PARTY_MESSAGE = "tpmsg";
    public static final String RECHARGE_THIRD_PARTY_TIMEDOUT = "tptimeout";
    public static final String REWARDS = "rewards2"; // New column for TTR2
    public static final String CLAIMABLE_AMOUNT = "tt";
    public static final String TOTAL_AMOUNT = "total";
    
    public static final int MIN_REDEEMABLE_TALK_TIME = 50;        
    
    public static final String RECHARGE_HIKE_BOT_MESSAGE = "Talk-time Rewards:\nYou can now earn Rs %1$d talk-time by successfully inviting friends to hike! For more info, see Rewards.";
    
    public static final String RECHARGE_HIKE_BOT_MESSAGE_MORE_THAN_Rs50 = "You've already earned Rs %1$d talk-time! Redeem it now under Rewards.";
    
    public static final String STATUS_UPDATE_PUSH_SETTING =  "pushsu";
    //public static final String CHAT_BACKGROUND_PUSH_SETTING =  "pushcbg";
    public static final String LOCALE =  "locale";
    public static final String LAST_SEEN_SETTING =  "lastseen";

    public static final String FirstApril_HIKE_BOT_MESSAGE = "Hey hiker! We're launching a new feature called hike2post today. With hike2post you can send messages to your friends via post and even send photos or videos. We'll package your message beautifully on your behalf and send it to your loved ones.\n\nYou can use hike2post from the Rewards option in the left menu.";

    public static final Integer CURRENT_ENCRYPTION_KEY_ID = 1;
    
    public static final Set<String> validAccountConfigsFormClient=new HashSet<String>();
    static
    {
        validAccountConfigsFormClient.add(STATUS_UPDATE_PUSH_SETTING);
        //validAccountConfigsFormClient.add(CHAT_BACKGROUND_PUSH_SETTING);
        validAccountConfigsFormClient.add(LOCALE);
        validAccountConfigsFormClient.add(LAST_SEEN_SETTING);
    }

    public static final String QUEUE_PREFIX = "/queue/";
    public static final String TOPIC_PREFIX = "/topic/";
    
    public static final String PROTIPS_COLLECTION = "protips";
    public static final String BOTMESSAGES_COLLECTION = "botmessages";
    
    public static final String PROTIPS_HISTORY_COLLECTION = "history";
    
    public static final long MILLISECONDS_IN_A_SECOND =  1000L;
    public static final long MILLISECONDS_IN_A_MINUTE =  60 * 1000L;
    public static final long MILLISECONDS_IN_A_HOUR =  60 * MILLISECONDS_IN_A_MINUTE;
    public static final long MILLISECONDS_IN_A_DAY =  24 * MILLISECONDS_IN_A_HOUR;
    public static final long MILLISECONDS_IN_A_WEEK = 7 * MILLISECONDS_IN_A_DAY;
    public static final long MILLISECONDS_IN_A_MONTH = 30 * MILLISECONDS_IN_A_WEEK;
    

    public static final String DSCTUTORIAL_ON = "on";
    public static final String DSCTUTORIAL_OFF = "off";
    public static final String DSCTUTORIAL_INDIAN = "indian";
    public static final String DSCTUTORIAL_INTERNATIONAL = "international";
    
    public static final long MAX_THUMBNAIL_LENGTH_NOKIA = 6000; // actuall size of thumbnail is 1124

    // Engagement Meter related
    public static final String ENGAGEMENT_METER_PREFIX = "em_";
    public static final String EM_SENT_MESSAGES = "sm";
    public static final String EM_RECEIVED_MESSAGES = "rm";
    public static final String EM_GC_STARTED = "gcs";
    public static final String EM_GC_SENT_MESSAGES = "gcsm";
    public static final String EM_GC_RECEIVED_MESSAGES = "gcrm";
    public static final String EM_FRIENDS = "f";
    public static final double EM_ONE_TO_ONE_MULTIPLIER = 1;
    public static final double EM_GC_MULTIPLIER = 0.2;
    public static final String SIGNUP_PUSH = "sendbot";
    
    //SMS related                                                                                 
    public static final String LAST_H2S_COUNT = "lh2s";                                                                                                                                                    
    public static final String H2S_SENT = "h2ss";
    public static final double H2S_COEFFICIENT = 2;
    public static final double H2S_EXPONENT = 0.56;
    public static final String H2S_JOINING_BONUS = "h2sjb";
    public static final String H2S_EARNED = "h2se";
    public static final String H2S_MANUAL_BUFFER = "h2smb";
    public static final int H2H_BENEFIT_COUNT = 20;
    public static final String HTTP_API_TOKEN = "7b155808505abdc9963e3abefd96f3ef-f1d844e00cb751be91ad0c030dfa4b20";
    
    public static final String RECURRING_CREDITS_KEY = "recurringCredits";
    
    public static final int INVITE_DUP_TTL = 7200;
    public static final int CHAT_BACKGROUND_DUP_TTL = 7200;

    public static final String PUSH_MESSAGE_ORIGINAL_TYPE = "originalType";

    public static final String DEVICES =  "devices";
    
    public static final String REDIS_PIN_QUEUE = "queue::pins";
    public static final String REDIS_INVITE_QUEUE = "queue::invites";
    public static final int THROTTLED_INVITEE_STORE_LIMIT = 600;
    public static final String THROTTLED_INVITES_LIST = "throttled_invites_";
    public static final String THROTTLED_INVITERS = "throttled_inviters";
    public static final String FROM_SCRIPT_INVITES = "from_script_invites"; 
    public static final int AYSNC_SMS_PACKETS_TIME_IN_SECONDS = 3;

    public static final String DELIM = ":";
}
