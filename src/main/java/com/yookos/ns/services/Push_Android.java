package com.yookos.ns.services;

/**
 * Created by jome on 2016/02/08.
 */

public class Push_Android {
//    String googleServerKey="AIzaSyBTZyyJ8wpRc3Hhd21C0wMnVCetGzwFah8";
//    String googleSenderId="54750361051";
//    public Result sendAndroidPushNotification(NotificationEvent notification){
//        try {
//            Message message = buildPushMessage(notification);
//            Sender sender = new Sender(googleServerKey);
//            String pushRegistrationId = notification.getTargetUser().getPushRegistrationId();
//
//            if(null == pushRegistrationId || pushRegistrationId.trim().equals("")) {
//                //fail gracefully, please.
//                LOG.error(":: invalid registration_id - empty string");
//                throw new IllegalArgumentException("invalid registration ID");
//            }
//
//            if((yookoreNotification.getActorUser().getUsername() != null) &&
//                    yookoreNotification.getActorUser().getUsername().toLowerCase().equals("pastorchrislive")) {
//                LOG.warn(String.format("sending PastorChrisLive push to [%s]: %s", yookoreNotification.getTargetUser().getUsername(), pushRegistrationId));
//            } else {
//                LOG.warn(String.format("sending push [%s]: %s", yookoreNotification.getTargetUser().getUsername(), pushRegistrationId));
//            }
//
//            return sender.send(message, pushRegistrationId, 1);
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return null;
//    }
//
//    private static Message buildPushMessage(NotificationEvent yookoreNotification) throws IOException {
//            Message.Builder builder = new Message.Builder();
//            builder.addData("type", yookoreNotification.getAction().name());
//            builder.addData("actor", yookoreNotification.getActorUser().getUserId());
//            builder.addData("payload", yookoreNotification.getExtraInfo().get("payload").toString());
//            builder.addData("fullNames", yookoreNotification.getActorUser().getFullNames());
//            addExtraInfo(yookoreNotification, builder);
//            return builder.build();
//        return null;
//    }
}
