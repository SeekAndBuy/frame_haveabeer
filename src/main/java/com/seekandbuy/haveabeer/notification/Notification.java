package com.seekandbuy.haveabeer.notification;

import java.util.List;

public abstract class Notification<X, T> {
	public SendEmail sendMail = new SendEmail();
	public abstract void sendNotification(T t, List<X> x); 
}